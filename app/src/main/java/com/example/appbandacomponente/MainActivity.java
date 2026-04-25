package com.example.appbandacomponente;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// IMPORTANTE: Estos imports dependen de que ya hayas creado estas clases
// Si te salen en rojo, es porque todavia no hemos creado los archivos en esas carpetas
import com.example.appbandacomponente.Models.CredencialesLogin;
import com.example.appbandacomponente.Models.Usuario;
import com.example.appbandacomponente.NetWorks.ApiCliente;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Actividad principal que se lanza al abrir la aplicacion.
 * Actualmente contiene una prueba de conexion directa con la API de Spring Boot.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // --- INICIO DE LA PRUEBA DE CONEXION ---
        Log.d("PRUEBA_RED", "Iniciando prueba de conexion con Spring Boot...");

        // 1. Creamos unas credenciales falsas
        CredencialesLogin credencialesPrueba = new CredencialesLogin("usuario_prueba", "1234");

        // 2. Hacemos la llamada manual usando el cliente
        Call<Usuario> llamada = ApiCliente.obtenerInstancia().realizarLogin(credencialesPrueba);

        llamada.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                Log.d("PRUEBA_RED", "¡EXITO! El servidor ha respondido. Codigo HTTP: " + response.code());

                if (response.isSuccessful()) {
                    Log.d("PRUEBA_RED", "Ademas, el login ha sido correcto.");
                } else {
                    Log.d("PRUEBA_RED", "Las credenciales son falsas, pero la conexion funciona perfecto.");
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("PRUEBA_RED", "¡ERROR FATAL! No se pudo conectar al servidor.");
                Log.e("PRUEBA_RED", "Motivo: " + t.getMessage());
            }
        });
        // --- FIN DE LA PRUEBA DE CONEXION ---
    }
}