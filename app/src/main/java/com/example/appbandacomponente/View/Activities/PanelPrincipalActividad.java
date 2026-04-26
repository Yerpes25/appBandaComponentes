package com.example.appbandacomponente.View.Activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.appbandacomponente.R;
import com.example.appbandacomponente.View.Fragments.InicioPanelFragmento;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

/**
 * Introduccion explicativa:
 * Actividad contenedora maestra. Mantiene la cabecera y el pie de pagina fijos,
 * y se encarga de intercambiar los fragmentos centrales segun la navegacion del usuario.
 */
public class PanelPrincipalActividad extends AppCompatActivity {

    private BottomNavigationView barraNavegacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel_principal);

        barraNavegacion = findViewById(R.id.barra_inferior);

        // Cargamos el panel principal por defecto al abrir la actividad
        if (savedInstanceState == null) {
            cargarFragmento(new InicioPanelFragmento());
        }

        // Configuramos los clics en los botones de abajo
        barraNavegacion.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragmentoSeleccionado = null;

                // Usamos if-else para identificar que boton se pulso segun su ID del menu XML
                int id = item.getItemId();

                if (id == R.id.nav_panel) {
                    fragmentoSeleccionado = new InicioPanelFragmento();
                } else if (id == R.id.nav_marchas) {
                    // TODO: Cambiar por MarchasFragmento cuando lo crees
                } else if (id == R.id.nav_info) {
                    // TODO: Cambiar por InfoFragmento cuando lo crees
                } else if (id == R.id.nav_insignias) {
                    // TODO: Cambiar por InsigniasFragmento cuando lo crees
                } else if (id == R.id.nav_chat) {
                    // TODO: Cambiar por ChatFragmento cuando lo crees
                }

                if (fragmentoSeleccionado != null) {
                    cargarFragmento(fragmentoSeleccionado);
                    return true;
                }
                return false;
            }
        });

        // 2. DESPUÉS: Le decimos a la barra que seleccione "Panel" por defecto.
        // Esto hará que el icono de Panel se ilumine, y automáticamente lanzará
        // el código de arriba para cargar el InicioPanelFragmento.
        if (savedInstanceState == null) {
            barraNavegacion.setSelectedItemId(R.id.nav_panel);
        }
    }

    /**
     * Metodo auxiliar para reemplazar el contenido del FrameLayout central por un nuevo fragmento.
     */
    private void cargarFragmento(Fragment fragmento) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenedor_fragmentos, fragmento)
                .commit();
    }
}