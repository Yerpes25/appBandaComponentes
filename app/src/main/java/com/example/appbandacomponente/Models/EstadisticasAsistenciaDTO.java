package com.example.appbandacomponente.Models;

/**
 * Objeto de Transferencia de Datos (DTO) que encapsula las estadisticas de asistencia.
 * Almacena la cantidad de eventos asistidos, el total de eventos y el porcentaje de asistencia.
 */
public class EstadisticasAsistenciaDTO {
    private long asistidos;
    private long totales;
    private int porcentaje;

    public long getAsistidos() { return asistidos; }
    public void setAsistidos(long asistidos) { this.asistidos = asistidos; }
    public long getTotales() { return totales; }
    public void setTotales(long totales) { this.totales = totales; }
    public int getPorcentaje() { return porcentaje; }
    public void setPorcentaje(int porcentaje) { this.porcentaje = porcentaje; }
}