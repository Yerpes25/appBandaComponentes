package com.example.appbandacomponente.Models;
/**
 * Introduccion explicativa:
 * Clase que representa a un usuario (musico) dentro de la banda.
 * Contiene la informacion personal y el rol que desempeña en la aplicacion.
 */
public class Usuario {

    private Integer idUsuario;
    private String nombre;
    private String apellidos;
    private String email;
    private String cargo;
    private String identificadorUsuario;
    private Banda banda;

    public Usuario() {}

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public String getIdentificadorUsuario() { return identificadorUsuario; }
    public void setIdentificadorUsuario(String identificadorUsuario) { this.identificadorUsuario = identificadorUsuario; }

    public Banda getBanda() { return banda; }
    public void setBanda(Banda banda) { this.banda = banda; }
}