package com.example.appbandacomponente.Models;

public class ComponenteDTO {
    private String nombreCompleto;
    private String fotoPerfil;
    private String instrumentoYVoz;
    private String cargo;

    // Genera los Getters y Setters iguales a los de Spring Boot
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getInstrumentoYVoz() {
        return instrumentoYVoz;
    }

    public void setInstrumentoYVoz(String instrumentoYVoz) {
        this.instrumentoYVoz = instrumentoYVoz;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}