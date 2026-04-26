package com.example.appbandacomponente.Utilities;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Introduccion explicativa:
 * Clase encargada de manejar la sesion del usuario de forma local.
 * Utiliza SharedPreferences para recordar quien ha iniciado sesion
 * y evitar pedir las credenciales cada vez que se abre la app.
 */
public class GestorSesion {

    private static final String NOMBRE_PREFERENCIAS = "SesionBandaApp";
    private static final String CLAVE_ID_USUARIO = "idUsuario";
    private static final String CLAVE_NOMBRE = "nombreUsuario";
    private static final String CLAVE_CARGO = "cargoUsuario";

    private SharedPreferences preferencias;
    private SharedPreferences.Editor editor;

    public GestorSesion(Context contexto) {
        preferencias = contexto.getSharedPreferences(NOMBRE_PREFERENCIAS, Context.MODE_PRIVATE);
        editor = preferencias.edit();
    }

    /**
     * Guarda los datos basicos del usuario tras un login exitoso.
     */
    public void crearSesion(int idUsuario, String nombre, String cargo) {
        editor.putInt(CLAVE_ID_USUARIO, idUsuario);
        editor.putString(CLAVE_NOMBRE, nombre);
        editor.putString(CLAVE_CARGO, cargo);
        editor.apply(); // Guarda de forma asincrona (mas rapido)
    }

    /**
     * Comprueba si el usuario ya inicio sesion anteriormente.
     */
    public boolean estaLogueado() {
        return preferencias.getInt(CLAVE_ID_USUARIO, -1) != -1;
    }

    /**
     * Borra los datos al cerrar sesion.
     */
    public void cerrarSesion() {
        editor.clear();
        editor.apply();
    }

    public String obtenerNombreUsuario() {
        return preferencias.getString(CLAVE_NOMBRE, "Músico");
    }
}