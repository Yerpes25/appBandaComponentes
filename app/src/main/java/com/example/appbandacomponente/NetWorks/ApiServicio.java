package com.example.appbandacomponente.NetWorks;

import com.example.appbandacomponente.Models.Asistencia;
import com.example.appbandacomponente.Models.CredencialesLogin;
import com.example.appbandacomponente.Models.Evento;
import com.example.appbandacomponente.Models.TablonAnuncio;
import com.example.appbandacomponente.Models.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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
    @PUT("api/usuarios/actualizar/{id}")
    Call<Usuario> actualizarUsuario(@Path("id") int id, @Body Usuario usuario);

    @GET("api/anuncios/banda/{idBanda}")
    Call<List<TablonAnuncio>> obtenerNoticiasPorBanda(@Path("idBanda") int idBanda);

    @POST("api/asistencias/votar")
    Call<Void> enviarVotoAsistencia(
            @retrofit2.http.Query("idUsuario") int idUsuario,
            @retrofit2.http.Query("idEvento") int idEvento,
            @retrofit2.http.Query("estado") String estado,
            @retrofit2.http.Query("observacion") String observacion // Nuevo campo
    );

    @GET("api/asistencias/usuario/{idUsuario}")
    Call<List<Asistencia>> obtenerAsistenciasPorUsuario(@Path("idUsuario") int idUsuario);
}