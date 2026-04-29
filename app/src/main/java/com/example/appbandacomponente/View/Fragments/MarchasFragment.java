package com.example.appbandacomponente.View.Fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.appbandacomponente.Models.Marcha;
import com.example.appbandacomponente.Models.Partitura;
import com.example.appbandacomponente.NetWorks.ApiCliente;
import com.example.appbandacomponente.R;
import com.example.appbandacomponente.Utilities.GestorSesion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Fragmento encargado de gestionar el repertorio musical de la banda.
 * Proporciona un sistema de visualización dinámica de marchas con capacidades de
 * filtrado avanzado por género, fecha de montaje y búsqueda textual, permitiendo además
 * el acceso a recursos multimedia como partituras en PDF y archivos de audio.
 */
public class MarchasFragment extends Fragment {

    private GestorSesion gestorSesion;
    private LinearLayout contenedorMarchas;
    private EditText entradaBusqueda;
    private Spinner comboGenero;
    private Spinner comboFecha;

    // Lista maestra con todas las marchas descargadas
    private List<Marcha> listaMarchasOriginal = new ArrayList<>();

    // Opciones de los filtros
    private final String[] GENEROS = {"Todos", "Fúnebre", "Ligero", "Redobladas", "Clásica"};
    private final String[] FECHAS = {"Cualquiera", "Último mes", "Últimos 6 meses", "Último año"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.panel_marchas, container, false);

        gestorSesion = new GestorSesion(getContext());
        contenedorMarchas = vista.findViewById(R.id.contenedorDinamicoMarchas);
        entradaBusqueda = vista.findViewById(R.id.entradaBuscarMarchas);
        comboGenero = vista.findViewById(R.id.comboGenero);
        comboFecha = vista.findViewById(R.id.comboFecha);

        configurarFiltros();
        descargarRepertorio();

        return vista;
    }

    /**
     * Inicializa los Spinners y el buscador de texto con sus eventos para re-filtrar en tiempo real.
     */
    private void configurarFiltros() {
        ArrayAdapter<String> adaptadorGeneros = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, GENEROS) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView vistaTexto = (TextView) super.getView(position, convertView, parent);
                vistaTexto.setTextColor(Color.BLACK);
                return vistaTexto;
            }
        };
        // setDropDownViewResource controla como se ve la lista cuando el Spinner esta ABIERTO
        adaptadorGeneros.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        comboGenero.setAdapter(adaptadorGeneros);

        ArrayAdapter<String> adaptadorFechas = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, FECHAS) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView vistaTexto = (TextView) super.getView(position, convertView, parent);
                // Forzamos que el color del texto seleccionado sea negro
                vistaTexto.setTextColor(Color.BLACK);
                return vistaTexto;
            }
        };
        adaptadorFechas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        comboFecha.setAdapter(adaptadorFechas);

        // Cada vez que cambie un desplegable, aplicamos filtros combinados
        AdapterView.OnItemSelectedListener listenerFiltros = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                aplicarFiltros();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        };

        comboGenero.setOnItemSelectedListener(listenerFiltros);
        comboFecha.setOnItemSelectedListener(listenerFiltros);

        // Evento al escribir en la barra de busqueda
        entradaBusqueda.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                aplicarFiltros();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    /**
     * Descarga la lista completa de marchas de la banda y las guarda en memoria.
     */
    private void descargarRepertorio() {
        int idBanda = gestorSesion.obtenerIdBanda();
        if (idBanda == -1) return;

        ApiCliente.obtenerInstancia().obtenerMarchasPorBanda(idBanda).enqueue(new Callback<List<Marcha>>() {
            @Override
            public void onResponse(Call<List<Marcha>> call, Response<List<Marcha>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaMarchasOriginal = response.body();
                    aplicarFiltros(); // Pinta la lista inicial
                }
            }

            @Override
            public void onFailure(Call<List<Marcha>> call, Throwable t) {
                Toast.makeText(getContext(), "Error al cargar repertorio", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Motor de busqueda local. Cruza el texto, el genero y la fecha elegida
     * para crear una lista reducida y mandarla a dibujar.
     */
    private void aplicarFiltros() {
        if (listaMarchasOriginal == null || listaMarchasOriginal.isEmpty()) return;

        String textoBusqueda = entradaBusqueda.getText().toString().toLowerCase().trim();
        String generoSeleccionado = comboGenero.getSelectedItem().toString();
        String fechaSeleccionada = comboFecha.getSelectedItem().toString();

        List<Marcha> listaFiltrada = new ArrayList<>();

        for (Marcha marcha : listaMarchasOriginal) {
            boolean coincideTexto = false;
            boolean coincideGenero = false;
            boolean coincideFecha = false;

            // 1. Filtro de Texto (Nombre o Autor)
            String nombre = marcha.getNombre() != null ? marcha.getNombre().toLowerCase() : "";
            String autor = marcha.getAutor() != null ? marcha.getAutor().toLowerCase() : "";
            if (textoBusqueda.isEmpty() || nombre.contains(textoBusqueda) || autor.contains(textoBusqueda)) {
                coincideTexto = true;
            }

            // 2. Filtro de Genero
            if (generoSeleccionado.equals("Todos")) {
                coincideGenero = true;
            } else if (marcha.getCategoria() != null && marcha.getCategoria().equalsIgnoreCase(generoSeleccionado)) {
                coincideGenero = true;
            }

            // 3. Filtro de Fecha (fMontaje)
            if (fechaSeleccionada.equals("Cualquiera")) {
                coincideFecha = true;
            } else if (marcha.getfMontaje() != null) {
                coincideFecha = verificarRangoFecha(marcha.getfMontaje(), fechaSeleccionada);
            }

            // Si pasa los tres filtros, la añadimos a la vista
            if (coincideTexto && coincideGenero && coincideFecha) {
                listaFiltrada.add(marcha);
            }
        }

        dibujarMarchas(listaFiltrada);
    }

    /**
     * Comprueba si la fecha de montaje de la marcha entra en el rango del filtro.
     * Suponemos formato yyyy-MM-dd que es el estandar de SQL Date / LocalDate.
     */
    private boolean verificarRangoFecha(String fechaMontaje, String filtro) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date fechaMarcha = sdf.parse(fechaMontaje);
            Calendar calMarcha = Calendar.getInstance();
            calMarcha.setTime(fechaMarcha);

            Calendar calLimite = Calendar.getInstance(); // Hoy

            if (filtro.equals("Último mes")) {
                calLimite.add(Calendar.MONTH, -1);
            } else if (filtro.equals("Últimos 6 meses")) {
                calLimite.add(Calendar.MONTH, -6);
            } else if (filtro.equals("Último año")) {
                calLimite.add(Calendar.YEAR, -1);
            }

            return fechaMarcha.after(calLimite.getTime());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Genera dinamicamente las tarjetas CardView para cada marcha.
     */
    private void dibujarMarchas(List<Marcha> marchas) {
        contenedorMarchas.removeAllViews();

        if (marchas.isEmpty()) {
            TextView textoVacio = new TextView(getContext());
            textoVacio.setText("No hay marchas que coincidan con los filtros.");
            textoVacio.setGravity(android.view.Gravity.CENTER);
            textoVacio.setPadding(0, 50, 0, 50);
            contenedorMarchas.addView(textoVacio);
            return;
        }

        for (Marcha m : marchas) {
            CardView tarjeta = new CardView(getContext());
            LinearLayout.LayoutParams paramsTarjeta = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            paramsTarjeta.setMargins(0, 0, 0, 30);
            tarjeta.setLayoutParams(paramsTarjeta);
            tarjeta.setCardElevation(4f);
            tarjeta.setRadius(16f);
            tarjeta.setCardBackgroundColor(android.graphics.Color.WHITE);

            LinearLayout interior = new LinearLayout(getContext());
            interior.setOrientation(LinearLayout.HORIZONTAL);
            interior.setPadding(30, 30, 30, 30);
            interior.setGravity(android.view.Gravity.CENTER_VERTICAL);

            // Icono musical
            ImageView icono = new ImageView(getContext());
            icono.setImageResource(android.R.drawable.ic_media_play);
            icono.setColorFilter(android.graphics.Color.parseColor("#D28F33"));
            icono.setBackgroundColor(android.graphics.Color.parseColor("#FFF5E6"));
            icono.setPadding(15, 15, 15, 15);
            interior.addView(icono, new LinearLayout.LayoutParams(100, 100));

            // Textos (Nombre, Autor, Genero)
            LinearLayout cajaTextos = new LinearLayout(getContext());
            cajaTextos.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams paramsTextos = new LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
            paramsTextos.setMargins(20, 0, 0, 0);
            cajaTextos.setLayoutParams(paramsTextos);

            TextView txtNombre = new TextView(getContext());
            txtNombre.setText(m.getNombre());
            txtNombre.setTextSize(16);
            txtNombre.setTypeface(null, android.graphics.Typeface.BOLD);
            txtNombre.setTextColor(android.graphics.Color.BLACK);

            TextView txtAutor = new TextView(getContext());
            String autorInfo = m.getAutor() != null ? m.getAutor() : "Desconocido";
            String catInfo = m.getCategoria() != null ? " | " + m.getCategoria() : "";
            txtAutor.setText(autorInfo + catInfo);
            txtAutor.setTextSize(13);
            txtAutor.setTextColor(android.graphics.Color.parseColor("#666666"));

            cajaTextos.addView(txtNombre);
            cajaTextos.addView(txtAutor);
            interior.addView(cajaTextos);

            // Botón Ver Partitura
            Button btnAcceder = new Button(getContext());
            btnAcceder.setText("Ver Partitura");
            btnAcceder.setTextSize(11);
            btnAcceder.setAllCaps(false);
            btnAcceder.setTextColor(android.graphics.Color.parseColor("#1976D2"));
            btnAcceder.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#E3F2FD")));

            btnAcceder.setOnClickListener(v -> abrirOpcionesPartitura(m.getIdMarcha(), m.getNombre()));

            interior.addView(btnAcceder, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            tarjeta.addView(interior);
            contenedorMarchas.addView(tarjeta);
        }
    }

    /**
     * Muestra el modal de seleccion y consulta a la API si existen los archivos asociados.
     */
    private void abrirOpcionesPartitura(int idMarcha, String nombreMarcha) {
        String[] opciones = {"Ver PDF", "Escuchar Audio"};

        new AlertDialog.Builder(getContext())
                .setTitle(nombreMarcha)
                .setItems(opciones, (dialog, which) -> {
                    consultarArchivo(idMarcha, which == 0); // which == 0 es PDF, which == 1 es Audio
                })
                .show();
    }

    /**
     * Llama al servidor para verificar la existencia de la partitura o el audio.
     * Implementa validaciones de seguridad para confirmar que la ruta es valida antes de abrirla.
     * * @param idMarcha Identificador unico de la marcha a consultar.
     * @param esPDF Determina si el usuario ha solicitado el documento (true) o el sonido (false).
     */
    private void consultarArchivo(int idMarcha, boolean esPDF) {
        ApiCliente.obtenerInstancia().obtenerPartiturasPorMarcha(idMarcha).enqueue(new Callback<List<Partitura>>() {
            @Override
            public void onResponse(Call<List<Partitura>> call, Response<List<Partitura>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {

                    Partitura partituraEncontrada = response.body().get(0);
                    String ruta = esPDF ? partituraEncontrada.getRutaPDF() : partituraEncontrada.getRutaAudio();

                    if (ruta != null && !ruta.trim().isEmpty()) {
                        try {
                            Intent intencionNavegador = new Intent(Intent.ACTION_VIEW);
                            intencionNavegador.setData(Uri.parse(ruta));
                            startActivity(intencionNavegador);
                        } catch (Exception e) {
                            mostrarAlertaArchivoNoEncontrado();
                        }
                    } else {
                        mostrarAlertaArchivoNoEncontrado();
                    }
                } else {
                    mostrarAlertaArchivoNoEncontrado();
                }
            }

            @Override
            public void onFailure(Call<List<Partitura>> call, Throwable t) {
                Toast.makeText(getContext(), "Fallo crítico de conexión con el servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Muestra una alerta informativa al usuario cuando el recurso solicitado no esta disponible.
     */
    private void mostrarAlertaArchivoNoEncontrado() {
        new AlertDialog.Builder(getContext())
                .setTitle("Recurso no disponible")
                .setMessage("No se ha podido encontrar pdf o audio. Contacte con el administrador.")
                .setPositiveButton("Cerrar", null)
                .show();
    }
}