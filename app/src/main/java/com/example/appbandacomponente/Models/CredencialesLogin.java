package com.example.appbandacomponente.Models;
/**
 * Introduccion explicativa:
 * Clase auxiliar utilizada para el transporte de datos durante el inicio de sesion.
 * Empaqueta el nombre de usuario y la contraseña para enviarlos a la API.
 */
public class CredencialesLogin {
    private String usuario;
    private String contrasena;

    public CredencialesLogin(String usuario, String contrasena) {
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
}