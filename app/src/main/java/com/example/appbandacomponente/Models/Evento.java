package com.example.appbandacomponente.Models;

/**
 * Modelo que representa un evento organizado por la banda, como un ensayo o un concierto.
 * Almacena los detalles clave del evento, incluyendo su titulo, tipo, fecha, horario,
 * ubicacion y si requiere confirmacion previa de asistencia por parte de los musicos.
 */
public class Evento {

    private Integer idEvento;
    private String tipo;
    private String fHora;
    private String direccion;
    private String titulo;
    private String horaFin;
    private Banda banda;
    private Boolean requiereConf;

    public Evento() {
    }

    public Boolean getRequiereConf() {
        return requiereConf;
    }

    public void setRequiereConf(Boolean requiereConf) {
        this.requiereConf = requiereConf;
    }

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getfHora() {
        return fHora;
    }

    public void setfHora(String fHora) {
        this.fHora = fHora;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public Banda getBanda() {
        return banda;
    }

    public void setBanda(Banda banda) {
        this.banda = banda;
    }
}