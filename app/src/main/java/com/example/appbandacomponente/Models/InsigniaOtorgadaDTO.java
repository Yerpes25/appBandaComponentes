package com.example.appbandacomponente.Models;

/**
 * Objeto de Transferencia de Datos (DTO) que representa una insignia otorgada a un usuario.
 * Contiene la informacion basica de la insignia conseguida, como el titulo, el icono visual,
 * la meta alcanzada y la fecha en la que se le concedio.
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