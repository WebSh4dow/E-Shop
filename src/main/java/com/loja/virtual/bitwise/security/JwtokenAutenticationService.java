package com.loja.virtual.bitwise.security;

import com.loja.virtual.bitwise.config.ApplicationContextLoad;
import com.loja.virtual.bitwise.model.Usuario;
import com.loja.virtual.bitwise.repository.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;


public class JwtokenAutenticationService {

    private static final long TEMPO_EXPIRACAO = 959990000;

    private static final String SECRET = "0d36a254b74f8f4daff27c6c46ae6cb4";

    private static final String PREFIXO_TOKEN = "Bearer";

    private static final String HEADER = "Authorization";



    public void addAuthentication(HttpServletResponse response, String login) throws Exception {
        String JWT = Jwts.builder()
                .setSubject(login)
                .setExpiration(new Date(System.currentTimeMillis() + TEMPO_EXPIRACAO))
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();

        String token = PREFIXO_TOKEN + " " + JWT;

        response.addHeader(HEADER, token);
        filterCors(response);

        response.getWriter().write("{\"Authorization\": \"" + token + "\"}");
    }

    public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader(HEADER);

        if (token != null) {
            String cleaningToken = token.replace(PREFIXO_TOKEN, "").trim();

            String user = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(cleaningToken)
                    .getBody().getSubject();

            if (user != null) {
                Usuario usuario = ApplicationContextLoad
                        .getApplicationContext()
                        .getBean(UsuarioRepository.class).findUsuarioByLogin(user);

                if (usuario != null) {
                    return new UsernamePasswordAuthenticationToken(
                            usuario.getLogin(),
                            usuario.getPassword(),
                            usuario.getAuthorities());
                }
            }

        }

        filterCors(response);
        return null;
    }

    private void filterCors(HttpServletResponse response) {

        if (response.getHeader("Access-Control-Allow-Origin") == null)
            response.addHeader("Access-Control-Allow-Origin", "*");

        if (response.getHeader("Access-Control-Allow-Headers") == null)
            response.addHeader("Access-Control-Allow-Headers", "*");

        if (response.getHeader("Access-Control-Request-Headers") == null)
            response.addHeader("Access-Control-Request-Headers", "*");

        if (response.getHeader("Access-Control-Allow-Methods") == null)
            response.addHeader("Access-Control-Allow-Methods", "*");

    }
}
