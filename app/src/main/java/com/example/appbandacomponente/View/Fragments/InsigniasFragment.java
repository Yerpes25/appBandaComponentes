package com.example.appbandacomponente.View.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appbandacomponente.Models.EstadisticasAsistenciaDTO;
import com.example.appbandacomponente.Models.InsigniaOtorgadaDTO;
import com.example.appbandacomponente.NetWorks.ApiCliente;
import com.example.appbandacomponente.R;
import com.example.appbandacomponente.Utilities.GestorSesion;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Introduccion explicativa:
 * Fragmento encargado de gestionar la pantalla de insignias y estadisticas de asistencia.
 * Muestra el progreso del musico en eventos y conciertos, asi como los logros obtenidos.
 */
public class InsigniasFragment extends Fragment {

    private GestorSesion gestorSesion;
    private LinearLayout contenedorInsignias;

    // Variables para Ensayos
    private ProgressBar barraEnsayos;
    private TextView porcentajeEnsayos;
    private TextView textoFraccionEnsayos;

    // Variables para Conciertos (Si las mantienes abajo en el XML)
    private ProgressBar barraConciertos;
    private TextView porcentajeConciertos;

    // 1. Declarar la variable de la fraccion
    private TextView textoFraccionConciertos;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.insig_estadis, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gestorSesion = new GestorSesion(getContext());

        contenedorInsignias = view.findViewById(R.id.contenedorInsigniasDinamico);

        // Ensayos
        barraEnsayos = view.findViewById(R.id.barraProgresoEnsayos);
        porcentajeEnsayos = view.findViewById(R.id.textoPorcentajeEnsayos);
        textoFraccionEnsayos = view.findViewById(R.id.textoFraccionEnsayos);

        // Conciertos
        barraConciertos = view.findViewById(R.id.barraProgresoConciertos);
        porcentajeConciertos = view.findViewById(R.id.textoPorcentajeConciertos);
        textoFraccionConciertos = view.findViewById(R.id.textoFraccionConciertos); // Nuevo

        descargarInsignias();
        descargarEstadisticasEnsayos();
        descargarEstadisticasConciertos();
    }

    /**
     * Descarga la lista de logros del servidor.
     */
    private void descargarInsignias() {
        int idUsuario = gestorSesion.obtenerIdUsuario();
        if (idUsuario == -1) return;

        ApiCliente.obtenerInstancia().obtenerInsigniasPorUsuario(idUsuario).enqueue(new Callback<List<InsigniaOtorgadaDTO>>() {
            @Override
            public void onResponse(Call<List<InsigniaOtorgadaDTO>> call, Response<List<InsigniaOtorgadaDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    dibujarVitrina(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<InsigniaOtorgadaDTO>> call, Throwable t) {
                Toast.makeText(getContext(), "Error al cargar insignias", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Pide los datos de asistencia a ensayos al servidor y actualiza la barra y los textos.
     */
    private void descargarEstadisticasEnsayos() {
        int idUsuario = gestorSesion.obtenerIdUsuario();
        if (idUsuario == -1) return;

        ApiCliente.obtenerInstancia().obtenerEstadisticasAsistencia(idUsuario).enqueue(new Callback<EstadisticasAsistenciaDTO>() {
            @Override
            public void onResponse(Call<EstadisticasAsistenciaDTO> call, Response<EstadisticasAsistenciaDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    EstadisticasAsistenciaDTO stats = response.body();

                    // Actualizamos la barra de progreso
                    barraEnsayos.setProgress(stats.getPorcentaje());

                    // Actualizamos el porcentaje del centro (Ej. "85%")
                    porcentajeEnsayos.setText(stats.getPorcentaje() + "%");

                    // Actualizamos la fraccion de abajo (Ej. "17/20")
                    textoFraccionEnsayos.setText(stats.getAsistidos() + "/" + stats.getTotales());
                }
            }

            @Override
            public void onFailure(Call<EstadisticasAsistenciaDTO> call, Throwable t) {
                // Error silencioso si no carga
            }
        });
    }

    /**
     * Crea las tarjetas de forma dinamica siguiendo el diseño claro y las
     * coloca una al lado de la otra formando un carrusel horizontal.
     */
    private void dibujarVitrina(List<InsigniaOtorgadaDTO> lista) {
        contenedorInsignias.removeAllViews();

        if (lista.isEmpty()) {
            TextView textoVacio = new TextView(getContext());
            textoVacio.setText("Aún no has desbloqueado ninguna insignia.");
            textoVacio.setTextColor(Color.parseColor("#9CA3AF"));
            contenedorInsignias.addView(textoVacio);
            return;
        }

        // Definimos un ancho fijo para cada elemento del carrusel (aprox 110dp)
        int anchoElemento = (int) (110 * getResources().getDisplayMetrics().density);

        for (InsigniaOtorgadaDTO insignia : lista) {

            // 1. Contenedor principal de la insignia
            LinearLayout interior = new LinearLayout(getContext());
            interior.setOrientation(LinearLayout.VERTICAL);
            interior.setGravity(Gravity.CENTER);
            interior.setPadding(8, 8, 8, 8);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(anchoElemento, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 16, 0);
            interior.setLayoutParams(params);

            // 2. Icono
            ImageView icono = new ImageView(getContext());
            icono.setImageResource(android.R.drawable.btn_star_big_on);
            icono.setColorFilter(Color.parseColor("#FACC15")); // Dorado

            int tamanoIcono = (int) (60 * getResources().getDisplayMetrics().density);
            LinearLayout.LayoutParams paramsIcono = new LinearLayout.LayoutParams(tamanoIcono, tamanoIcono);
            paramsIcono.setMargins(0, 0, 0, 8);
            interior.addView(icono, paramsIcono);

            // 3. Titulo de la insignia
            TextView txtTitulo = new TextView(getContext());
            txtTitulo.setText(insignia.getTitulo());
            txtTitulo.setTextColor(Color.parseColor("#333333")); // Texto oscuro
            txtTitulo.setTextSize(12);
            txtTitulo.setTypeface(null, android.graphics.Typeface.BOLD);
            txtTitulo.setGravity(Gravity.CENTER);
            interior.addView(txtTitulo);

            // 4. Fecha de obtencion
            TextView txtFecha = new TextView(getContext());
            txtFecha.setText("Otorgada: " + insignia.getFechaOtorgada());
            txtFecha.setTextColor(Color.parseColor("#666666")); // Gris
            txtFecha.setTextSize(10);
            txtFecha.setGravity(Gravity.CENTER);
            interior.addView(txtFecha);

            // 5. Meta
            TextView txtMeta = new TextView(getContext());
            String textoMeta = insignia.getMeta() != null ? "Meta - " + insignia.getMeta() : "Sin meta requerida";
            txtMeta.setText(textoMeta);
            txtMeta.setTextColor(Color.parseColor("#999999")); // Gris claro
            txtMeta.setTextSize(9);
            txtMeta.setGravity(Gravity.CENTER);
            txtMeta.setPadding(0, 4, 0, 0);
            interior.addView(txtMeta);

            contenedorInsignias.addView(interior);
        }
    }

    /**
     * Pide los datos de asistencia a conciertos al servidor.
     */
    private void descargarEstadisticasConciertos() {
        int idUsuario = gestorSesion.obtenerIdUsuario();
        if (idUsuario == -1) return;

        ApiCliente.obtenerInstancia().obtenerEstadisticasConciertos(idUsuario).enqueue(new Callback<EstadisticasAsistenciaDTO>() {
            @Override
            public void onResponse(Call<EstadisticasAsistenciaDTO> call, Response<EstadisticasAsistenciaDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    EstadisticasAsistenciaDTO stats = response.body();
                    barraConciertos.setProgress(stats.getPorcentaje());
                    porcentajeConciertos.setText(stats.getPorcentaje() + "%");
                    textoFraccionConciertos.setText(stats.getAsistidos() + "/" + stats.getTotales());
                }
            }

            @Override
            public void onFailure(Call<EstadisticasAsistenciaDTO> call, Throwable t) {}
        });
    }
}