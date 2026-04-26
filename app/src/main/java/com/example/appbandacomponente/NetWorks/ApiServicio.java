package com.example.appbandacomponente.NetWorks;

import com.example.appbandacomponente.Models.CredencialesLogin;
import com.example.appbandacomponente.Models.Evento;
import com.example.appbandacomponente.Models.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Introduccion explicativa:
 * Definicion de las rutas de la API de Spring Boot.
 * Permite realizar peticiones HTTP de forma sencilla y manual.
 */
public interface ApiServicio {

    @POST("api/usuarios/login")
    Call<Usuario> realizarLogin(@Body CredencialesLogin credenciales);
    @GET("api/eventos/banda/{idBanda}")
    Call<List<Evento>> obtenerEventosPorBanda(@Path("idBanda") int idBanda);
}