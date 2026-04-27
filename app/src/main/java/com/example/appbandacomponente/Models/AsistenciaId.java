/**
 * Clase que representa el identificador compuesto para la asistencia.
 * Mapea la clave primaria compuesta enviada por la API de Spring Boot.
 */
package com.example.appbandacomponente.Models;

public class AsistenciaId {
    private Integer idUsuario;
    private Integer idEvento;

    public AsistenciaId() {}

    public AsistenciaId(Integer idUsuario, Integer idEvento) {
        this.idUsuario = idUsuario;
        this.idEvento = idEvento;
    }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public Integer getIdEvento() { return idEvento; }
    public void setIdEvento(Integer idEvento) { this.idEvento = idEvento; }
}