package com.example.appbandacomponente.NetWorks;

import com.example.appbandacomponente.Models.CredencialesLogin;
import com.example.appbandacomponente.Models.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Introduccion explicativa:
 * Definicion de las rutas de la API de Spring Boot.
 * Permite realizar peticiones HTTP de forma sencilla y manual.
 */
public interface ApiServicio {

    @POST("api/usuarios/login")
    Call<Usuario> realizarLogin(@Body CredencialesLogin credenciales);
}