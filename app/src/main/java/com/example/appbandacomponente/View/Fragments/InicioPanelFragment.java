package com.example.appbandacomponente.View.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appbandacomponente.Models.Evento;
import com.example.appbandacomponente.NetWorks.ApiCliente;
import com.example.appbandacomponente.R;
import com.example.appbandacomponente.Utilities.GestorSesion;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Introduccion explicativa:
 * Este fragmento representa la vista principal o "Home" del panel del componente.
 * Aqui se mostraran los avisos importantes y la lista de proximos eventos,
 * descargados directamente de la base de datos segun la banda del usuario.
 */
public class InicioPanelFragment extends Fragment {

    private LinearLayout contenedorEnsayos;
    private LinearLayout contenedorEventos;
    private GestorSesion gestorSesion;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.panel_de_componente, container, false);

        contenedorEnsayos = vista.findViewById(R.id.contenedorDinamicoEnsayos);
        contenedorEventos = vista.findViewById(R.id.contenedorDinamicoEventos);

        gestorSesion = new GestorSesion(getContext());

        descargarAgenda();

        return vista;
    }

    /**
     * Metodo que llama al servidor de Spring Boot para pedir todos los eventos
     * pertenecientes a la banda del usuario que ha iniciado sesion.
     */
    private void descargarAgenda() {
        int idBanda = gestorSesion.obtenerIdBanda();

        // Validamos que exista un id de banda valido antes de llamar a la API
        if (idBanda == -1) {
            Toast.makeText(getContext(), "Error: No se encontro la banda del usuario", Toast.LENGTH_SHORT).show();
            return;
        }

        Call<List<Evento>> llamada = ApiCliente.obtenerInstancia().obtenerEventosPorBanda(idBanda);

        llamada.enqueue(new Callback<List<Evento>>() {
            @Override
            public void onResponse(Call<List<Evento>> call, Response<List<Evento>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    clasificarYMostrarEventos(response.body());
                } else {
                    Toast.makeText(getContext(), "Error al obtener la agenda de la banda", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Evento>> call, Throwable t) {
                Toast.makeText(getContext(), "Fallo de conexion con el servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Recorre la lista de eventos recibida y los pinta en la pantalla.
     * Si el tipo contiene la palabra "Ensayo", lo pone en la tarjeta superior.
     * Si es otro tipo (Concierto, Desfile...), lo pone en la tarjeta inferior.
     */
    private void clasificarYMostrarEventos(List<Evento> listaEventos) {
        if (!isAdded() || getContext() == null) {
            return;
        }

        contenedorEnsayos.removeAllViews();
        contenedorEventos.removeAllViews();

        // Obtencion del momento actual para filtrar eventos pasados
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault());
        String momentoActual = sdf.format(new Date());

        for (Evento evento : listaEventos) {
            String fechaBruta = evento.getfHora(); // Formato: 2026-04-27T21:00:00

            if (fechaBruta != null && fechaBruta.compareTo(momentoActual) < 0) {
                continue;
            }

            // Contenedor principal para el item del evento
            LinearLayout itemLayout = new LinearLayout(getContext());
            itemLayout.setOrientation(LinearLayout.VERTICAL);
            itemLayout.setPadding(40, 30, 40, 30);

            // --- FILA 1: TIPO Y TITULO ---
            TextView vistaTipo = new TextView(getContext());
            String tipo = (evento.getTipo() != null) ? evento.getTipo().toUpperCase() : "EVENTO";
            String titulo = evento.getTitulo();

// Si hay un título válido, lo concatenamos al tipo. Si no, solo el tipo.
            if (titulo != null && !titulo.isEmpty() && !titulo.equals("null")) {
                vistaTipo.setText(tipo + " - " + titulo);
            } else {
                vistaTipo.setText(tipo);
            }

            vistaTipo.setTextColor(android.graphics.Color.parseColor("#2A5C9A"));
            vistaTipo.setTextSize(17);
            vistaTipo.setTypeface(null, android.graphics.Typeface.BOLD);
            itemLayout.addView(vistaTipo);

// --- FILA 2: HORARIO (INICIO - FIN) ---
            TextView vistaHorario = new TextView(getContext());
            String fechaCorta = "";
            String horaInicio = "--:--";
            if (evento.getfHora() != null && evento.getfHora().length() >= 16) {
                fechaCorta = evento.getfHora().substring(8, 10) + "/" + evento.getfHora().substring(5, 7);
                horaInicio = evento.getfHora().substring(11, 16);
            }
            String horaFin = (evento.getHoraFin() != null) ? evento.getHoraFin() : "--:--";

            vistaHorario.setText(fechaCorta + "  -  HORA: " + horaInicio + " a " + horaFin);
            vistaHorario.setTextColor(android.graphics.Color.parseColor("#333333"));
            vistaHorario.setTextSize(15);
            vistaHorario.setPadding(0, 5, 0, 5);
            itemLayout.addView(vistaHorario);

// --- FILA 3: DIRECCIÓN ---
            TextView vistaDireccion = new TextView(getContext());
            vistaDireccion.setText(evento.getDireccion() != null ? evento.getDireccion() : "Sin ubicación");
            vistaDireccion.setTextColor(android.graphics.Color.parseColor("#666666"));
            vistaDireccion.setTextSize(14);
            itemLayout.addView(vistaDireccion);

            // Linea de separacion visual entre eventos
            View separador = new View(getContext());
            separador.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 2));
            separador.setBackgroundColor(Color.parseColor("#E0E0E0"));

            // Clasificacion segun el tipo de evento
            if (evento.getTipo() != null && evento.getTipo().toLowerCase().contains("ensayo")) {
                contenedorEnsayos.addView(itemLayout);
                contenedorEnsayos.addView(separador);
            } else {
                contenedorEventos.addView(itemLayout);
                contenedorEventos.addView(separador);
            }
        }
    }
}