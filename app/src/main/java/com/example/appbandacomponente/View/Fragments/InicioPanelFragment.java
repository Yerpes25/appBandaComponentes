// 3. En InicioPanelFragment.java
// Sustituye el codigo completo de tu fragmento por este:

package com.example.appbandacomponente.View.Fragments;

import android.app.AlertDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appbandacomponente.Models.Asistencia;
import com.example.appbandacomponente.Models.Evento;
import com.example.appbandacomponente.Models.TablonAnuncio;
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
 * Aqui se mostraran los avisos importantes en formato carrusel interactivo y la
 * lista de proximos eventos, descargados de la base de datos segun la banda del usuario.
 */
public class InicioPanelFragment extends Fragment {

    private LinearLayout contenedorEnsayos;
    private LinearLayout contenedorEventos;
    private GestorSesion gestorSesion;
    private List<Asistencia> listaVotosUsuario;
    private ImageView iconoAnterior, iconoSiguiente;
    private TextView textoTituloNoticia, textoCuerpoNoticia, textoIndicadoresCarrusel;
    private List<TablonAnuncio> listaNoticias;
    private int indiceNoticiaActual = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.panel_de_componente, container, false);

        contenedorEnsayos = vista.findViewById(R.id.contenedorDinamicoEnsayos);
        contenedorEventos = vista.findViewById(R.id.contenedorDinamicoEventos);

        iconoAnterior = vista.findViewById(R.id.iconoAnterior);
        iconoSiguiente = vista.findViewById(R.id.iconoSiguiente);
        textoTituloNoticia = vista.findViewById(R.id.textoTituloNoticia);
        textoCuerpoNoticia = vista.findViewById(R.id.textoCuerpoNoticia);
        textoIndicadoresCarrusel = vista.findViewById(R.id.textoIndicadoresCarrusel);

        gestorSesion = new GestorSesion(getContext());

        configurarBotonesCarrusel();
        descargarAgenda();
        descargarNoticias();

        return vista;
    }

    /**
     * Configura la interaccion manual de los botones de anterior y siguiente del carrusel.
     * Avanza o retrocede el indice comprobando los limites de la lista.
     */
    private void configurarBotonesCarrusel() {
        iconoAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listaNoticias != null && !listaNoticias.isEmpty()) {
                    if (indiceNoticiaActual > 0) {
                        indiceNoticiaActual--;
                        actualizarVistaCarrusel();
                    }
                }
            }
        });

        iconoSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listaNoticias != null && !listaNoticias.isEmpty()) {
                    if (indiceNoticiaActual < listaNoticias.size() - 1) {
                        indiceNoticiaActual++;
                        actualizarVistaCarrusel();
                    }
                }
            }
        });
    }

    /**
     * Gestiona la descarga y visualización de los anuncios del tablón.
     * Si no hay noticias, configura la interfaz para mostrar un aviso informativo.
     */
    private void descargarNoticias() {
        int idBanda = gestorSesion.obtenerIdBanda();

        // Si el usuario no tiene banda, forzamos el estado vacío inmediatamente
        if (idBanda == -1) {
            listaNoticias = null;
            actualizarVistaCarrusel();
            return;
        }

        Call<List<TablonAnuncio>> llamada = ApiCliente.obtenerInstancia().obtenerNoticiasPorBanda(idBanda);
        llamada.enqueue(new Callback<List<TablonAnuncio>>() {
            @Override
            public void onResponse(Call<List<TablonAnuncio>> call, Response<List<TablonAnuncio>> response) {
                /* * Definicion: Verificamos si la respuesta es exitosa y contiene elementos.
                 * En caso contrario, tratamos la lista como nula para disparar el aviso de vacio.
                 */
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    listaNoticias = response.body();
                    indiceNoticiaActual = 0;
                } else {
                    listaNoticias = null;
                }
                actualizarVistaCarrusel();
            }

            @Override
            public void onFailure(Call<List<TablonAnuncio>> call, Throwable t) {
                listaNoticias = null;
                actualizarVistaCarrusel();
            }
        });
    }

    /**
     * Refresca los componentes visuales del carrusel de noticias.
     * Controla la visibilidad de las flechas y los indicadores según la disponibilidad de datos.
     */
    private void actualizarVistaCarrusel() {
        /*
         * Definicion: Si no existen noticias, actualizamos los textos de la tarjeta
         * con un mensaje de aviso y ocultamos los iconos de navegacion.
         */
        if (listaNoticias == null || listaNoticias.isEmpty()) {
            textoTituloNoticia.setText("Sin avisos");
            textoCuerpoNoticia.setText("No hay de momento noticias o avisos importantes para mostrar.");
            textoIndicadoresCarrusel.setText("");

            iconoAnterior.setVisibility(View.INVISIBLE);
            iconoSiguiente.setVisibility(View.INVISIBLE);
            return;
        }

        /*
         * Definicion: Si hay noticias disponibles, restauramos la navegacion
         * y pintamos el contenido segun el indice seleccionado.
         */
        iconoAnterior.setVisibility(View.VISIBLE);
        iconoSiguiente.setVisibility(View.VISIBLE);

        TablonAnuncio noticiaActual = listaNoticias.get(indiceNoticiaActual);
        textoTituloNoticia.setText(noticiaActual.getTitulo());
        textoCuerpoNoticia.setText(noticiaActual.getMensaje());

        // Actualizacion de los puntos indicadores de posicion
        StringBuilder indicadores = new StringBuilder();
        for (int i = 0; i < listaNoticias.size(); i++) {
            indicadores.append(i == indiceNoticiaActual ? "● " : "○ ");
        }
        textoIndicadoresCarrusel.setText(indicadores.toString().trim());

        // Ajuste de la transparencia para indicar limites de navegacion
        iconoAnterior.setAlpha(indiceNoticiaActual == 0 ? 0.3f : 1.0f);
        iconoSiguiente.setAlpha(indiceNoticiaActual == listaNoticias.size() - 1 ? 0.3f : 1.0f);
    }
    /**
     * Metodo principal de carga.
     * Primero descarga los votos del usuario de la base de datos
     * y, cuando termina, descarga la agenda de eventos para poder cruzarlos.
     */
    private void descargarAgenda() {
        int idUsuario = gestorSesion.obtenerIdUsuario();
        int idBanda = gestorSesion.obtenerIdBanda();

        if (idBanda == -1) {
            clasificarYMostrarEventos(null);
            return;
        }

        /*
         * PASO A: Pedimos a la API todas las asistencias que este usuario ya ha respondido.
         */
        ApiCliente.obtenerInstancia().obtenerAsistenciasPorUsuario(idUsuario).enqueue(new Callback<List<Asistencia>>() {
            @Override
            public void onResponse(Call<List<Asistencia>> call, Response<List<Asistencia>> response) {
                if (response.isSuccessful()) {
                    // Guardamos los votos en la lista de la clase para usarlos luego
                    listaVotosUsuario = response.body();
                }

                /*
                 * PASO B: Independientemente de si hemos encontrado votos o no,
                 * procedemos a cargar los eventos de la banda.
                 */
                cargarEventosDeBanda(idBanda);
            }

            @Override
            public void onFailure(Call<List<Asistencia>> call, Throwable t) {
                // Si falla la red al buscar votos, intentamos cargar los eventos igualmente
                cargarEventosDeBanda(idBanda);
            }
        });
    }

    /**
     * Metodo auxiliar que descarga la lista de eventos desde Spring Boot.
     */
    private void cargarEventosDeBanda(int idBanda) {
        Call<List<Evento>> llamada = ApiCliente.obtenerInstancia().obtenerEventosPorBanda(idBanda);
        llamada.enqueue(new Callback<List<Evento>>() {
            @Override
            public void onResponse(Call<List<Evento>> call, Response<List<Evento>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    /*
                     * Al llamar a este metodo, listaVotosUsuario ya tendra datos
                     * y el bucle podra encontrar tu voto guardado.
                     */
                    clasificarYMostrarEventos(response.body());
                } else {
                    clasificarYMostrarEventos(null);
                }
            }

            @Override
            public void onFailure(Call<List<Evento>> call, Throwable t) {
                Toast.makeText(getContext(), "Error al cargar la agenda de la banda", Toast.LENGTH_SHORT).show();
                clasificarYMostrarEventos(null);
            }
        });
    }

    private void clasificarYMostrarEventos(List<Evento> listaEventos) {
        if (!isAdded() || getContext() == null) {
            return;
        }

        contenedorEnsayos.removeAllViews();
        contenedorEventos.removeAllViews();

        int contadorEnsayos = 0;
        int contadorOtrosEventos = 0;

        if (listaEventos != null && !listaEventos.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault());
            String momentoActual = sdf.format(new Date());

            for (Evento evento : listaEventos) {
                String fechaBruta = evento.getfHora();

                if (fechaBruta != null && fechaBruta.compareTo(momentoActual) < 0) {
                    continue;
                }

                LinearLayout itemLayout = new LinearLayout(getContext());
                itemLayout.setOrientation(LinearLayout.VERTICAL);
                itemLayout.setPadding(40, 30, 40, 50);

                TextView vistaTipo = new TextView(getContext());
                String tipo = (evento.getTipo() != null) ? evento.getTipo().toUpperCase() : "EVENTO";
                String titulo = evento.getTitulo();

                if (titulo != null && !titulo.isEmpty() && !titulo.equals("null")) {
                    vistaTipo.setText(tipo + " - " + titulo);
                } else {
                    vistaTipo.setText(tipo);
                }

                vistaTipo.setTextColor(android.graphics.Color.parseColor("#2A5C9A"));
                vistaTipo.setTextSize(17);
                vistaTipo.setTypeface(null, Typeface.BOLD);
                itemLayout.addView(vistaTipo);

                TextView vistaHorario = new TextView(getContext());
                String fechaCorta = "";
                String horaInicio = "--:--";
                if (evento.getfHora() != null && evento.getfHora().length() >= 16) {
                    fechaCorta = evento.getfHora().substring(8, 10) + "/" + evento.getfHora().substring(5, 7);
                    horaInicio = evento.getfHora().substring(11, 16);
                }
                String horaFin = (evento.getHoraFin() != null) ? evento.getHoraFin() : "--:--";

                vistaHorario.setText(fechaCorta + "  -  HORA: " + horaInicio + " a " + horaFin);
                vistaHorario.setTextColor(Color.parseColor("#333333"));
                vistaHorario.setTextSize(15);
                vistaHorario.setPadding(0, 20, 0, 0);
                itemLayout.addView(vistaHorario);

                TextView vistaDireccion = new TextView(getContext());
                vistaDireccion.setText(evento.getDireccion() != null ? evento.getDireccion() : "Sin ubicación");
                vistaDireccion.setTextColor(Color.parseColor("#666666"));
                vistaDireccion.setTextSize(14);
                itemLayout.addView(vistaDireccion);

                /*
                 * Creacion de botones pequeños de confirmacion (✔ / ✘)
                 */
                if (Boolean.TRUE.equals(evento.getRequiereConf())) {
                    // Creamos un contenedor que podamos limpiar y redibujar
                    LinearLayout contenedorVoto = new LinearLayout(getContext());
                    contenedorVoto.setOrientation(LinearLayout.VERTICAL);
                    LinearLayout.LayoutParams paramsContenedor = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    paramsContenedor.setMargins(0, 20, 0, 0);
                    contenedorVoto.setLayoutParams(paramsContenedor);

                    itemLayout.addView(contenedorVoto);

                    String estadoEncontrado = null;
                    if (listaVotosUsuario != null) {
                        for (com.example.appbandacomponente.Models.Asistencia voto : listaVotosUsuario) {
                            // Comparamos el ID del evento (el modelo Asistencia tiene un objeto ID con idEvento)
                            if (voto.getId() != null && voto.getId().getIdEvento().equals(evento.getIdEvento())) {
                                estadoEncontrado = voto.getEstado();
                                break;
                            }
                        }
                    }

                    dibujarInterfazVotacion(contenedorVoto, evento.getIdEvento(), estadoEncontrado);
                }

                View separador = new View(getContext());
                separador.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, 2));
                separador.setBackgroundColor(Color.parseColor("#E0E0E0"));

                if (evento.getTipo() != null && evento.getTipo().toLowerCase().contains("ensayo")) {
                    contenedorEnsayos.addView(itemLayout);
                    contadorEnsayos++;
                } else {
                    contenedorEventos.addView(itemLayout);
                    contadorOtrosEventos++;
                }
            }
        }
        if (contadorEnsayos == 0) {
            mostrarAvisoVacio(contenedorEnsayos, "No hay de momento ensayos programados");
        }

        if (contadorOtrosEventos == 0) {
            mostrarAvisoVacio(contenedorEventos, "No hay de momento próximos eventos");
        }
    }

    /**
     * Metodo para redibujar la zona de votacion de un evento especifico.
     * Se encarga de alternar entre los iconos (voto nuevo) y el boton de estado (voto realizado).
     */
    private void dibujarInterfazVotacion(LinearLayout contenedor, int idEvento, String estadoActual) {
        int alturaBoton = (int) (45 * getContext().getResources().getDisplayMetrics().density);
        int anchoIcono = (int) (60 * getContext().getResources().getDisplayMetrics().density);
        // Limpiamos lo que haya actualmente en ese hueco de la tarjeta para poner lo nuevo
        contenedor.removeAllViews();

        if (estadoActual == null) {
            // CASO A: El usuario aun no ha votado (mostramos Tick y Cruz)
            LinearLayout layoutBotones = new LinearLayout(getContext());
            layoutBotones.setGravity(Gravity.END);
            layoutBotones.setPadding(0, 0, 10, 0);

            Button btnSi = new Button(getContext());
            btnSi.setText("✔");
            btnSi.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#22C55E")));
            btnSi.setTextColor(Color.WHITE);
            layoutBotones.addView(btnSi, new LinearLayout.LayoutParams(anchoIcono, alturaBoton));

            Button btnNo = new Button(getContext());
            btnNo.setText("✘");
            btnNo.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EF4444")));
            btnNo.setTextColor(Color.WHITE);
            LinearLayout.LayoutParams pNo = new LinearLayout.LayoutParams(anchoIcono, alturaBoton);
            pNo.setMargins(20, 0, 0, 0);
            layoutBotones.addView(btnNo, pNo);

            contenedor.addView(layoutBotones);

            // Al hacer clic, enviamos a la BD y en el EXITO redibujamos como "Asiste"
            btnSi.setOnClickListener(v -> enviarVoto(gestorSesion.obtenerIdUsuario(), idEvento, "Asiste", "", () ->
                    dibujarInterfazVotacion(contenedor, idEvento, "Asiste")
            ));

            // Al hacer clic en No, pedimos motivo y en el EXITO redibujamos como "Falta"
            btnNo.setOnClickListener(v -> mostrarModalMotivo(idEvento, () ->
                    dibujarInterfazVotacion(contenedor, idEvento, "Falta")
            ));

        } else {
            // CASO B: Ya existe un voto (mostramos un unico boton con el estado actual)
            Button btnEstado = new Button(getContext());
            boolean esAsistencia = estadoActual.equals("Asiste");

            btnEstado.setText(esAsistencia ? "VOY" : "NO VOY");
            btnEstado.setBackgroundTintList(ColorStateList.valueOf(
                    Color.parseColor(esAsistencia ? "#22C55E" : "#EF4444")));
            btnEstado.setTextColor(Color.WHITE);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 20, 0, 0);
            contenedor.addView(btnEstado, params);

            // Si pulsa el boton ya votado, preguntamos si quiere cambiar la opcion
            btnEstado.setOnClickListener(v -> {
                new AlertDialog.Builder(getContext())
                        .setTitle("Modificar Asistencia")
                        .setMessage("¿Quieres cambiar tu respuesta para este evento?")
                        .setPositiveButton("Cambiar", (dialog, which) -> {
                            if (esAsistencia) {
                                // Si cambia de SI a NO, pedimos motivo y actualizamos BD
                                mostrarModalMotivo(idEvento, () -> dibujarInterfazVotacion(contenedor, idEvento, "Falta"));
                            } else {
                                // Si cambia de NO a SI, actualizamos directo en BD
                                enviarVoto(gestorSesion.obtenerIdUsuario(), idEvento, "Asiste", "", () ->
                                        dibujarInterfazVotacion(contenedor, idEvento, "Asiste"));
                            }
                        })
                        .setNegativeButton("Mantener", null)
                        .show();
            });
        }
    }

    /**
     * Realiza la peticion POST al servidor.
     * El parametro 'accionAlFinalizar' es el que hace que la interfaz cambie "sola" tras el exito.
     */
    private void enviarVoto(int idUsuario, int idEvento, String estado, String motivo, Runnable accionAlFinalizar) {
        Call<Void> llamada = ApiCliente.obtenerInstancia().enviarVotoAsistencia(idUsuario, idEvento, estado, motivo);

        llamada.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Esta es la parte magica: ejecutamos el redibujado solo si la API dijo OK
                    if (accionAlFinalizar != null) {
                        accionAlFinalizar.run();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarModalMotivo(int idEvento, Runnable accionExito) {
        android.app.AlertDialog.Builder constructor = new android.app.AlertDialog.Builder(getContext());
        constructor.setTitle("Motivo de la falta");
        final android.widget.EditText entradaMotivo = new android.widget.EditText(getContext());
        entradaMotivo.setHint("Explica por qué no puedes ir...");
        constructor.setView(entradaMotivo);

        constructor.setPositiveButton("Guardar", (dialogo, que) -> {
            String motivo = entradaMotivo.getText().toString().trim();
            if (!motivo.isEmpty()) {
                enviarVoto(gestorSesion.obtenerIdUsuario(), idEvento, "Falta", motivo, accionExito);
            } else {
                Toast.makeText(getContext(), "El motivo es obligatorio", Toast.LENGTH_SHORT).show();
            }
        });
        constructor.setNegativeButton("Cancelar", null);
        constructor.show();
    }

    /**
     * Crea y añade un TextView centrado al contenedor indicado cuando no hay datos.
     *
     * @param contenedor El LinearLayout donde se insertara el mensaje.
     * @param mensaje    El texto explicativo a mostrar.
     */
    private void mostrarAvisoVacio(LinearLayout contenedor, String mensaje) {
        TextView textoAviso = new TextView(getContext());
        textoAviso.setText(mensaje);
        textoAviso.setTextColor(Color.parseColor("#999999"));
        textoAviso.setTextSize(14);
        textoAviso.setGravity(Gravity.CENTER);

        // Añadimos margen para que no este pegado a los bordes de la tarjeta
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 50, 0, 50);
        textoAviso.setLayoutParams(params);

        contenedor.addView(textoAviso);
    }
}