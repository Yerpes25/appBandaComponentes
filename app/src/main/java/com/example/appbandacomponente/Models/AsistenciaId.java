package com.example.appbandacomponente.Models;

/**
 * Modelo que representa la clave primaria compuesta para la entidad Asistencia.
 * Sirve para vincular de forma unica a un usuario con un evento especifico.
 */
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