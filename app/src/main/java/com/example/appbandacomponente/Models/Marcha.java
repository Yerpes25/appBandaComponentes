package com.example.appbandacomponente.Models;

/**
 * Modelo que representa una marcha musical dentro del repertorio de la banda.
 * Almacena los datos de la pieza, incluyendo su nombre, autor, fecha de montaje,
 * categoria y su estado actual de ensayo o interpretacion.
 */
public class Marcha {

    private Integer idMarcha;
    private Banda banda;
    private String nombre;
    private String autor;
    private String tiempo;
    private String fMontaje; // Recibido como String para facilitar el manejo manual
    private String categoria;
    private String estado;

    public Marcha() {}

    public Integer getIdMarcha() { return idMarcha; }
    public void setIdMarcha(Integer idMarcha) { this.idMarcha = idMarcha; }

    public Banda getBanda() { return banda; }
    public void setBanda(Banda banda) { this.banda = banda; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getTiempo() { return tiempo; }
    public void setTiempo(String tiempo) { this.tiempo = tiempo; }

    public String getfMontaje() { return fMontaje; }
    public void setfMontaje(String fMontaje) { this.fMontaje = fMontaje; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}