package com.example.appbandacomponente.Models;

/**
 * Introduccion explicativa:
 * Modelo de datos para el Usuario en la aplicacion Android.
 * Contiene exactamente los mismos atributos que el servidor para evitar fallos de lectura.
 */
public class Usuario {

    private Integer idUsuario;
    private Banda banda;
    private String nombre;
    private String apellidos;
    private String dni;
    private String telefono;
    private String email;
    private String fNacimiento;
    private String direccion;
    private String fAlta;
    private Boolean activo;
    private String fotoPerfil;
    private String biografia;
    private String password;
    private String contactEmerg;
    private String tallaChaqueta;
    private String tallaPantalon;
    private String tallaGorra;
    private String tallaCamisa;
    private String rolApp;
    private String ultimoAcceso;
    private Boolean aprobado;

    public Usuario() {
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Banda getBanda() {
        return banda;
    }

    public void setBanda(Banda banda) {
        this.banda = banda;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getfNacimiento() {
        return fNacimiento;
    }

    public void setfNacimiento(String fNacimiento) {
        this.fNacimiento = fNacimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getfAlta() {
        return fAlta;
    }

    public void setfAlta(String fAlta) {
        this.fAlta = fAlta;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContactEmerg() {
        return contactEmerg;
    }

    public void setContactEmerg(String contactEmerg) {
        this.contactEmerg = contactEmerg;
    }

    public String getTallaChaqueta() {
        return tallaChaqueta;
    }

    public void setTallaChaqueta(String tallaChaqueta) {
        this.tallaChaqueta = tallaChaqueta;
    }

    public String getTallaPantalon() {
        return tallaPantalon;
    }

    public void setTallaPantalon(String tallaPantalon) {
        this.tallaPantalon = tallaPantalon;
    }

    public String getTallaGorra() {
        return tallaGorra;
    }

    public void setTallaGorra(String tallaGorra) {
        this.tallaGorra = tallaGorra;
    }

    public String getTallaCamisa() {
        return tallaCamisa;
    }

    public void setTallaCamisa(String tallaCamisa) {
        this.tallaCamisa = tallaCamisa;
    }

    public String getRolApp() {
        return rolApp;
    }

    public void setRolApp(String rolApp) {
        this.rolApp = rolApp;
    }

    public String getUltimoAcceso() {
        return ultimoAcceso;
    }

    public void setUltimoAcceso(String ultimoAcceso) {
        this.ultimoAcceso = ultimoAcceso;
    }

    public Boolean getAprobado() {
        return aprobado;
    }

    public void setAprobado(Boolean aprobado) {
        this.aprobado = aprobado;
    }
}