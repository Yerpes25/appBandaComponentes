package com.example.appbandacomponente.Models;

/**
 * Objeto de Transferencia de Datos (DTO) que representa a un componente de la banda de forma resumida.
 * Facilita el envio unicamente de los datos visuales importantes para el perfil,
 * como el nombre completo, foto, instrumento o voz y su cargo dentro de la agrupacion.
 */
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