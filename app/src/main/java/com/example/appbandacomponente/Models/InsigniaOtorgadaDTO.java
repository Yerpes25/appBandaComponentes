package com.example.appbandacomponente.Models;

/**
 * Objeto que recibe los datos de la insignia obtenida desde el servidor.
 */
public class InsigniaOtorgadaDTO {

    private String titulo;
    private String icono;
    private Integer meta;
    private String fechaOtorgada; // En Android lo recibimos como String para evitar errores de parseo

    public InsigniaOtorgadaDTO() {}

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getIcono() { return icono; }
    public void setIcono(String icono) { this.icono = icono; }

    public Integer getMeta() { return meta; }
    public void setMeta(Integer meta) { this.meta = meta; }

    public String getFechaOtorgada() { return fechaOtorgada; }
    public void setFechaOtorgada(String fechaOtorgada) { this.fechaOtorgada = fechaOtorgada; }
}