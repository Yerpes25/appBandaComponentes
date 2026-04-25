package com.example.appbandacomponente.Models;

/**
 * Introduccion explicativa:
 * Esta clase representa a una banda de musica en la aplicacion movil.
 * Almacena los datos básicos de la agrupacion y su identificador unico de acceso.
 */
public class Banda {

    private Integer idBanda;
    private String nombre;
    private String identificadorBanda;
    private String fotoPortada;
    private String colorPrimario;

    public Banda() {}

    public Integer getIdBanda() { return idBanda; }
    public void setIdBanda(Integer idBanda) { this.idBanda = idBanda; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getIdentificadorBanda() { return identificadorBanda; }
    public void setIdentificadorBanda(String identificadorBanda) { this.identificadorBanda = identificadorBanda; }

    public String getFotoPortada() { return fotoPortada; }
    public void setFotoPortada(String fotoPortada) { this.fotoPortada = fotoPortada; }

    public String getColorPrimario() { return colorPrimario; }
    public void setColorPrimario(String colorPrimario) { this.colorPrimario = colorPrimario; }
}