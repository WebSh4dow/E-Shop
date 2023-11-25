package com.loja.virtual.bitwise.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
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

        response.getWriter().write("{\"Authorization\": \"" + token + "\"}");
    }
}
