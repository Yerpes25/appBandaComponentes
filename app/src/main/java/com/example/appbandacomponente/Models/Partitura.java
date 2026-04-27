package com.example.appbandacomponente.Models;

/**
 * Modelo que representa el archivo digital de una marcha musical.
 * Contiene las rutas hacia los archivos PDF o Audio alojados en el servidor.
 */
public class Partitura {
    private Integer idPartituras;
    private String rutaPDF;
    private String rutaAudio;

    public Partitura() {
    }

    public Integer getIdPartituras() {
        return idPartituras;
    }

    public void setIdPartituras(Integer idPartituras) {
        this.idPartituras = idPartituras;
    }

    public String getRutaPDF() {
        return rutaPDF;
    }

    public void setRutaPDF(String rutaPDF) {
        this.rutaPDF = rutaPDF;
    }

    public String getRutaAudio() {
        return rutaAudio;
    }

    public void setRutaAudio(String rutaAudio) {
        this.rutaAudio = rutaAudio;
    }
}
