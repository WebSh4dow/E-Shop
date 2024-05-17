package com.loja.virtual.bitwise.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@SequenceGenerator(name = "seq_pessoa", sequenceName = "seq_pessoa", initialValue = 1, allocationSize = 1)
@Getter
@Setter
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "tipoPessoa")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PessoaFisica.class, name = "pessoaFisica"),
        @JsonSubTypes.Type(value = PessoaJuridica.class, name = "pessoaJuridica")
})
public abstract class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_pessoa")
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "nome é um campo obrigatório")
    private String nome;

    @Column(nullable = false)
    @Email
    private String email;

    @Column
    private String tipoPessoa;


    @Column(nullable = false)
    @NotBlank(message = "telefone é um campo obrigatório")
    private String telefone;

    @OneToMany(mappedBy = "pessoa", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Endereco> enderecos = new ArrayList<Endereco>();

    @ManyToOne
   // @JsonIdentityInfo(generator= ObjectIdGenerators.UUIDGenerator.class, property="id")
    @JoinColumn(name = "empresa_id", nullable = true,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_id_fk"))
    private Pessoa empresa;


    @JsonCreator
    public Pessoa(@JsonProperty("id") Long id,
                  @JsonProperty("nome") String nome,
                  @JsonProperty("email") String email,
                  @JsonProperty("tipoPessoa") String tipoPessoa,
                  @JsonProperty("telefone") String telefone,
                  @JsonProperty("enderecos") List<Endereco> enderecos,
                  @JsonProperty("empresa") Pessoa empresa) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.tipoPessoa = tipoPessoa;
        this.telefone = telefone;
        this.enderecos = enderecos;
        this.empresa = empresa;
    }


    public String getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(String tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }


    public Pessoa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Pessoa empresa) {
        this.empresa = empresa;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pessoa pessoa = (Pessoa) o;
        return Objects.equals(id, pessoa.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
