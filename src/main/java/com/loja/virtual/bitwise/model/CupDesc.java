package com.loja.virtual.bitwise.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "cup_desc")
@SequenceGenerator(name = "seq_cup_desc", sequenceName = "seq_cup_desc",allocationSize = 1, initialValue = 1)
public class CupDesc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_cup_desc")
    private Long id;
    @Column(nullable = false)
    private String codigoDesc;

    private BigDecimal valorRealDesc;

    private BigDecimal valorPorcentDesc;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dataValidadeCupom;

    @ManyToOne(targetEntity = Pessoa.class)
    @JoinColumn(name = "empresa_id", nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_id_fk"))
    private Pessoa empresa;


    public void setEmpresa(Pessoa empresa) {
        this.empresa = empresa;
    }

    public Pessoa getEmpresa() {
        return empresa;
    }

    public String getCodigoDesc() {
        return codigoDesc;
    }

    public void setCodigoDesc(String codigoDesc) {
        this.codigoDesc = codigoDesc;
    }

    public BigDecimal getValorRealDesc() {
        return valorRealDesc;
    }

    public void setValorRealDesc(BigDecimal valorRealDesc) {
        this.valorRealDesc = valorRealDesc;
    }

    public BigDecimal getValorPorcentDesc() {
        return valorPorcentDesc;
    }

    public void setValorPorcentDesc(BigDecimal valorPorcentDesc) {
        this.valorPorcentDesc = valorPorcentDesc;
    }

    public Date getDataValidadeCupom() {
        return dataValidadeCupom;
    }

    public void setDataValidadeCupom(Date dataValidadeCupom) {
        this.dataValidadeCupom = dataValidadeCupom;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CupDesc cupDesc = (CupDesc) o;
        return Objects.equals(id, cupDesc.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
