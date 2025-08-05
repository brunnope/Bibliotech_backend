package com.bibliotech.bibliotech.entity.dto;

public class IdentificadorDTO {

    //pode ser email ou matrícula
    String identificador;

    public IdentificadorDTO(String identificador) {
        this.identificador = identificador;
    }

    public String getIdentificador() {
        return identificador;
    }
    public void setIdentificador(String identificador) {}
}
