package com.example.appbandacomponente.Models;

/**
 * Modelo que representa un anuncio publicado en el tablon de la banda.
 * Almacena el titulo, el cuerpo del mensaje y la fecha en la que expira dicho aviso.
 */
public class TablonAnuncio {
    private Integer idAnuncios;
    private String titulo;
    private String mensaje;
    private String fechaExpira;

    // Constructores, Getters y Setters
    public TablonAnuncio() {
    }

    public String getFechaExpira() {
        return fechaExpira;
    }

    public void setFechaExpira(String fechaExpira) {
        this.fechaExpira = fechaExpira;
    }

    public Integer getIdAnuncios() {
        return idAnuncios;
    }

    public void setIdAnuncios(Integer idAnuncios) {
        this.idAnuncios = idAnuncios;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}