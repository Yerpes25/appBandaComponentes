package com.example.appbandacomponente.Utilities;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Clase encargada de manejar la sesion del usuario de forma local.
 * Utiliza SharedPreferences para recordar quien ha iniciado sesion
 * y guardar todos sus datos personales para mostrarlos en el perfil.
 */
public class GestorSesion {

    private static final String NOMBRE_PREFERENCIAS = "SesionBandaApp";
    private static final String CLAVE_ID_USUARIO = "idUsuario";
    private static final String CLAVE_NOMBRE = "nombreUsuario";
    private static final String CLAVE_APELLIDOS = "apellidosUsuario";
    private static final String CLAVE_DNI = "dniUsuario";
    private static final String CLAVE_ID_BANDA = "idBanda";
    private static final String CLAVE_ROL = "rolUsuario";
    private static final String CLAVE_FOTO = "fotoUsuario";
    private static final String CLAVE_TELEFONO = "telefono";
    private static final String CLAVE_EMAIL = "email";
    private static final String CLAVE_PASSWORD = "password";
    private static final String CLAVE_BIOGRAFIA = "biografia";
    private static final String CLAVE_CONTACTO_EMERGENCIA = "contactoEmergencia";
    private static final String CLAVE_TALLA_CAMISA = "tallaCamisa";
    private static final String CLAVE_TALLA_PANTALON = "tallaPantalon";
    private static final String CLAVE_TALLA_GORRA = "tallaGorra";
    private static final String CLAVE_TALLA_CHAQUETA = "tallaChaqueta";
    private static final String CLAVE_F_NACIMIENTO = "fechaNacimiento";
    private static final String CLAVE_DIRECCION = "direccion";

    private SharedPreferences preferencias;
    private SharedPreferences.Editor editor;

    public GestorSesion(Context contexto) {
        preferencias = contexto.getSharedPreferences(NOMBRE_PREFERENCIAS, Context.MODE_PRIVATE);
        editor = preferencias.edit();
    }

    public void crearSesion(int idUsuario, String nombre, int idBanda, String rol, String foto) {
        editor.putInt(CLAVE_ID_USUARIO, idUsuario);
        editor.putString(CLAVE_NOMBRE, nombre);
        editor.putInt(CLAVE_ID_BANDA, idBanda);
        editor.putString(CLAVE_ROL, rol);
        editor.putString(CLAVE_FOTO, foto);
        editor.apply();
    }

    public void guardarDatosExtra(String apellidos, String dni, String telefono, String email, String password, String biografia,
                                  String contactoEmergencia, String tallaCamisa, String tallaPantalon,
                                  String tallaGorra, String tallaChaqueta, String fNacimiento, String direccion) {
        editor.putString(CLAVE_APELLIDOS, apellidos);
        editor.putString(CLAVE_DNI, dni);
        editor.putString(CLAVE_TELEFONO, telefono);
        editor.putString(CLAVE_EMAIL, email);
        editor.putString(CLAVE_PASSWORD, password);
        editor.putString(CLAVE_BIOGRAFIA, biografia);
        editor.putString(CLAVE_CONTACTO_EMERGENCIA, contactoEmergencia);
        editor.putString(CLAVE_TALLA_CAMISA, tallaCamisa);
        editor.putString(CLAVE_TALLA_PANTALON, tallaPantalon);
        editor.putString(CLAVE_TALLA_GORRA, tallaGorra);
        editor.putString(CLAVE_TALLA_CHAQUETA, tallaChaqueta);
        editor.putString(CLAVE_F_NACIMIENTO, fNacimiento);
        editor.putString(CLAVE_DIRECCION, direccion);
        editor.apply();
    }

    public void actualizarDatosPersonales(String nombre, String apellidos, String dni, String email, String telefono,
                                          String contactoEmergencia, String direccion, String tallaCamisa,
                                          String tallaChaqueta, String tallaPantalon, String tallaGorra) {
        editor.putString(CLAVE_NOMBRE, nombre);
        editor.putString(CLAVE_APELLIDOS, apellidos);
        editor.putString(CLAVE_DNI, dni);
        editor.putString(CLAVE_EMAIL, email);
        editor.putString(CLAVE_TELEFONO, telefono);
        editor.putString(CLAVE_CONTACTO_EMERGENCIA, contactoEmergencia);
        editor.putString(CLAVE_DIRECCION, direccion);
        editor.putString(CLAVE_TALLA_CAMISA, tallaCamisa);
        editor.putString(CLAVE_TALLA_CHAQUETA, tallaChaqueta);
        editor.putString(CLAVE_TALLA_PANTALON, tallaPantalon);
        editor.putString(CLAVE_TALLA_GORRA, tallaGorra);
        editor.apply();
    }

    public boolean estaLogueado() {
        return preferencias.getInt(CLAVE_ID_USUARIO, -1) != -1;
    }

    public void cerrarSesion() {
        editor.clear();
        editor.apply();
    }

    public int obtenerIdUsuario() {
        return preferencias.getInt(CLAVE_ID_USUARIO, -1);
    }

    public String obtenerNombreUsuario() {
        return preferencias.getString(CLAVE_NOMBRE, "");
    }

    public String obtenerApellidosUsuario() {
        return preferencias.getString(CLAVE_APELLIDOS, "");
    }

    public String obtenerDni() {
        return preferencias.getString(CLAVE_DNI, "");
    }

    public String obtenerTelefono() {
        return preferencias.getString(CLAVE_TELEFONO, "");
    }

    public String obtenerEmail() {
        return preferencias.getString(CLAVE_EMAIL, "");
    }

    public String obtenerContactoEmergencia() {
        return preferencias.getString(CLAVE_CONTACTO_EMERGENCIA, "");
    }

    public String obtenerTallaCamisa() {
        return preferencias.getString(CLAVE_TALLA_CAMISA, "");
    }

    public String obtenerTallaPantalon() {
        return preferencias.getString(CLAVE_TALLA_PANTALON, "");
    }

    public String obtenerTallaGorra() {
        return preferencias.getString(CLAVE_TALLA_GORRA, "");
    }

    public String obtenerTallaChaqueta() {
        return preferencias.getString(CLAVE_TALLA_CHAQUETA, "");
    }

    public String obtenerDireccion() {
        return preferencias.getString(CLAVE_DIRECCION, "");
    }

    public int obtenerIdBanda() {
        return preferencias.getInt(CLAVE_ID_BANDA, -1);
    }

    public String obtenerRol() {
        return preferencias.getString(CLAVE_ROL, "");
    }
}