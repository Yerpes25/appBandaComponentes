package com.example.appbandacomponente.Models;

/**
 * Modelo que representa el registro de asistencia a un evento (ensayo o concierto).
 * Almacena el estado de confirmacion y cualquier observacion relevante.
 */
public class Asistencia {

    private AsistenciaId id;

    private String estado;
    private String observacion;

    public Asistencia() {
    }

    public AsistenciaId getId() {
        return id;
    }

    public void setId(AsistenciaId id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}