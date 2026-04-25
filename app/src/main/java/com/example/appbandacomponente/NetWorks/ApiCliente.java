package com.example.appbandacomponente.NetWorks;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Clase encargada de configurar y proporcionar la conexion unica (Singleton)
 * con la API REST de Spring Boot para toda la aplicacion.
 */
public class ApiCliente {

    private static Retrofit retrofit = null;
    // Pon aqui la IP local de tu ordenador si pruebas en emulador (ej. 10.0.2.2)
    // o la IP de tu servidor si ya esta subida.
    private static final String URL_BASE = "http://192.168.1.100:8080/";

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