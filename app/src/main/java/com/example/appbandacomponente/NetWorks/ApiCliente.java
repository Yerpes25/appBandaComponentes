package com.example.appbandacomponente.NetWorks;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Clase cliente encargada de configurar y establecer la conexion HTTP con el servidor backend.
 * Utiliza la libreria Retrofit bajo el patron Singleton para centralizar las peticiones de red
 * y proporcionar una instancia unica de ApiServicio en toda la aplicacion.
 */
public class ApiCliente {
    private static Retrofit retrofit = null;

    // Recuerda usar 10.0.2.2 para el emulador o la IP de tu PC para movil real
    // private static final String URL_BASE = "http://192.168.1.23:8080/";
    // CORRECTO (Con la barra al final)
    //private static final String URL_BASE = "https://app-9f547ff2-15e5-49e5-8761-17b3eba05bb3.cleverapps.io/";
    private static final String URL_BASE = "http://192.168.1.24:8080/";

    public static ApiServicio obtenerInstancia() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiServicio.class);
    }
}