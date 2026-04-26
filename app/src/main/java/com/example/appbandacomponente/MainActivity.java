package com.example.appbandacomponente;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appbandacomponente.NetWorks.ApiCliente;
import com.example.appbandacomponente.Models.CredencialesLogin;
import com.example.appbandacomponente.Models.Usuario;
import com.example.appbandacomponente.Utilities.GestorSesion;
import com.example.appbandacomponente.View.Activities.PanelPrincipalActividad;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Introduccion explicativa:
 * Pantalla principal de inicio de sesion.
 * Captura los datos introducidos por el componente, los envia a la API
 * y, si son correctos, guarda la sesion y permite el paso.
 */
public class MainActivity extends AppCompatActivity {

    private EditText campoUsuario;
    private EditText campoContrasena;
    private Button botonEntrar;

    private GestorSesion gestorSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Asegurate de que tu XML de login se llama asi o cambialo

        // Inicializamos el gestor de sesion
        gestorSesion = new GestorSesion(this);

        // --- INICIO DE LA MAGIA: COMPROBACIÓN DE SESIÓN ---
        if (gestorSesion.estaLogueado()) {
            // Si ya está logueado, saltamos directo al panel
            Intent intencion = new Intent(MainActivity.this, PanelPrincipalActividad.class);
            startActivity(intencion);
            finish(); // Matamos el login
            return; // El "return" hace que el código de abajo no se ejecute, ahorrando tiempo
        }

        // Si no está logueado, cargamos la pantalla normal de login
        setContentView(R.layout.activity_main);

        // 1. Vinculamos el codigo Java con los elementos de tu diseño XML
        campoUsuario = findViewById(R.id.entradaUsuario);
        campoContrasena = findViewById(R.id.entradaContrasena);
        botonEntrar = findViewById(R.id.botonEntrar);

        // 2. Le decimos al boton que debe hacer al ser pulsado
        botonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Capturamos el texto de las cajas
                String usuarioTexto = campoUsuario.getText().toString().trim();
                String contrasenaTexto = campoContrasena.getText().toString().trim();

                // Comprobamos que no esten vacios
                if (usuarioTexto.isEmpty() || contrasenaTexto.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Llamamos a la funcion que habla con el servidor
                procesarLogin(usuarioTexto, contrasenaTexto);
            }
        });
    }

    private void procesarLogin(String usuario, String contrasena) {
        // Desactivamos el boton temporalmente para que no le den dos veces
        botonEntrar.setEnabled(false);
        botonEntrar.setText("Conectando...");

        CredencialesLogin credenciales = new CredencialesLogin(usuario, contrasena);
        Call<Usuario> llamada = ApiCliente.obtenerInstancia().realizarLogin(credenciales);

        llamada.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                // Volvemos a activar el boton
                botonEntrar.setEnabled(true);
                botonEntrar.setText("Entrar");

                if (response.isSuccessful() && response.body() != null) {
                    // ¡LOGIN CORRECTO! El servidor nos devuelve los datos del musico
                    Usuario usuarioServidor = response.body();

                    int idBanda = -1; // Valor por defecto si no tiene banda
                    if (usuarioServidor.getBanda() != null) {
                        idBanda = usuarioServidor.getBanda().getIdBanda();
                    }

                    // Guardamos la sesion para que no tenga que volver a meter la contraseña
                    gestorSesion.crearSesion(
                            usuarioServidor.getIdUsuario(),
                            usuarioServidor.getNombre(),
                            usuarioServidor.getCargo(),
                            usuarioServidor.getBanda().getIdBanda()
                    );

                    Toast.makeText(MainActivity.this, "¡Bienvenido, " + usuarioServidor.getNombre() + "!", Toast.LENGTH_LONG).show();

                    Intent intencion = new Intent(MainActivity.this, PanelPrincipalActividad.class);
                    startActivity(intencion);
                    finish();

                } else {
                    // El servidor respondio pero dijo "No Autorizado" (Credenciales malas)
                    Toast.makeText(MainActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                // Error grave (sin internet, servidor apagado...)
                botonEntrar.setEnabled(true);
                botonEntrar.setText("Entrar");
                Toast.makeText(MainActivity.this, "Error de conexion con el servidor", Toast.LENGTH_LONG).show();
            }
        });
    }
}