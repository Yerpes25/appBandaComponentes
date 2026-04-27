/**
 * Modelo que representa la asistencia de un músico a un evento.
 * Contiene el estado de la confirmacion y las posibles observaciones del motivo.
 */
package com.example.appbandacomponente.Models;

public class Asistencia {

    /* * Identificador compuesto que vincula al usuario con el evento.
     * Debe coincidir con el nombre del atributo en el Backend para que GSON lo asimile bien.
     */
    private AsistenciaId id;

    private String estado;
    private String observacion;

    public Asistencia() {}

    public AsistenciaId getId() { return id; }
    public void setId(AsistenciaId id) { this.id = id; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }
}