package com.example.appbandacomponente.Models;

/**
 * Introduccion explicativa:
 * Clase auxiliar utilizada para el transporte de datos durante el inicio de sesion.
 * Empaqueta el correo y la clave para enviarlos a la API de Spring Boot.
 * ATENCION: Los nombres de las variables (correo y clave) DEBEN coincidir
 * exactamente con los que espera el servidor.
 */
public class CredencialesLogin {

    private String correo; // Antes se llamaba 'usuario'
    private String clave;  // Antes se llamaba 'contrasena'

    public CredencialesLogin(String correo, String clave) {
        this.correo = correo;
        this.clave = clave;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}