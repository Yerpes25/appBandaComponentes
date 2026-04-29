package com.example.appbandacomponente.Models;

/**
 * Modelo que representa una partitura musical.
 * Almacena las rutas de acceso a los archivos multimedia asociados,
 * como el documento en formato PDF y el archivo de audio de referencia.
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
