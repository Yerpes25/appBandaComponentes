package com.example.appbandacomponente.Models;

/**
 * Modelo que representa un anuncio o noticia en la aplicacion movil.
 * Mapea los datos enviados por la API de Spring Boot correspondientes a TablonAnuncio.
 */
public class TablonAnuncio {
    private Integer idAnuncios;
    private String titulo;
    private String mensaje;

    // Constructores, Getters y Setters
    public TablonAnuncio() {
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