package com.example.appbandacomponente.View.Activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.appbandacomponente.R;
import com.example.appbandacomponente.Utilities.GestorSesion;
import com.example.appbandacomponente.View.Fragments.ChatFragment;
import com.example.appbandacomponente.View.Fragments.InfoBandaFragment;
import com.example.appbandacomponente.View.Fragments.InicioPanelFragment;
import com.example.appbandacomponente.View.Fragments.InsigniasFragment;
import com.example.appbandacomponente.View.Fragments.MarchasFragment;
import com.example.appbandacomponente.View.Fragments.PerfilFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

/**
 * Actividad principal que sirve como contenedor y eje de navegacion de la aplicacion movil.
 * Gestiona el intercambio de fragmentos mediante una barra de navegacion inferior (BottomNavigationView)
 * y proporciona acceso al perfil de usuario y al control de sesiones.
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
            cargarFragmento(new InicioPanelFragment());
        }

        // Configuramos los clics en los botones de abajo
        barraNavegacion.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragmentoSeleccionado = null;

                // Usamos if-else para identificar que boton se pulso segun su ID del menu XML
                int id = item.getItemId();

                if (id == R.id.nav_panel) {
                    fragmentoSeleccionado = new InicioPanelFragment();
                } else if (id == R.id.nav_marchas) {
                    fragmentoSeleccionado = new MarchasFragment();
                } else if (id == R.id.nav_info) {
                    fragmentoSeleccionado = new InfoBandaFragment();
                } else if (id == R.id.nav_insignias) {
                    fragmentoSeleccionado = new InsigniasFragment();
                } else if (id == R.id.nav_chat) {
                    fragmentoSeleccionado = new ChatFragment();
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

        View cabecera = findViewById(R.id.cabecera_incluida);
        View fotoPerfil = cabecera.findViewById(R.id.contenedorFotoPerfil);

        // Reemplaza el listener de la foto de perfil dentro de tu PanelPrincipalActividad.java

        fotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(PanelPrincipalActividad.this, v);
                popup.getMenu().add("Mi Perfil");
                popup.getMenu().add("Ajustes");
                popup.getMenu().add("Cerrar Sesión");

                popup.setOnMenuItemClickListener(item -> {
                    switch (item.getTitle().toString()) {
                        case "Mi Perfil":
                            cargarFragmento(new PerfilFragment());
                            return true;
                        case "Cerrar Sesión":
                            new GestorSesion(PanelPrincipalActividad.this).cerrarSesion();

                            android.content.Intent intencionLogin = new android.content.Intent(PanelPrincipalActividad.this, com.example.appbandacomponente.MainActivity.class);
                            intencionLogin.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intencionLogin);
                            return true;
                    }
                    return false;
                });
                popup.show();
            }
        });
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