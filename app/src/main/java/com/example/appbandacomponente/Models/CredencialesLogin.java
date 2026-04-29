package com.example.appbandacomponente.Models;

/**
 * Modelo auxiliar que representa las credenciales de inicio de sesion de un usuario.
 * Se utiliza para encapsular el correo electronico y la clave que se envian al servidor
 * durante el proceso de autenticacion.
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