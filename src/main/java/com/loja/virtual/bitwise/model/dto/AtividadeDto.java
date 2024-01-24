package com.loja.virtual.bitwise.model.dto;

import java.io.Serializable;

public class AtividadeDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String text;

    private String code;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
