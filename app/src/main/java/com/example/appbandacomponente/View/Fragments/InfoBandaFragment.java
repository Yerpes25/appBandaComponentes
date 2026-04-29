package com.example.appbandacomponente.View.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.appbandacomponente.Models.Banda;
import com.example.appbandacomponente.Models.ComponenteDTO;
import com.example.appbandacomponente.Models.TablonAnuncio;
import com.example.appbandacomponente.Models.Usuario;
import com.example.appbandacomponente.NetWorks.ApiCliente;
import com.example.appbandacomponente.R;
import com.example.appbandacomponente.Utilities.GestorSesion;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Fragmento encargado de mostrar la información detallada de la banda musical.
 * Gestiona la visualización de datos institucionales, la ubicación del local de ensayo en un mapa de Google,
 * el acceso a redes sociales, anuncios específicos y el listado completo de componentes de la agrupación.
 */
public class InfoBandaFragment extends Fragment implements OnMapReadyCallback {

    private GestorSesion gestorSesion;
    private TextView textoNombreBanda;
    private ImageView fotoPortada, btnInsta, btnTwitter, btnYoutube;
    private LinearLayout contenedorAnuncios;
    private GoogleMap mapaGoogle;
    private Banda bandaActual;
    private LinearLayout contenedorComponentes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.info_banda, container, false);

        gestorSesion = new GestorSesion(getContext());
        textoNombreBanda = vista.findViewById(R.id.textoNombreBanda);
        fotoPortada = vista.findViewById(R.id.imagenPerfilBanda);
        contenedorAnuncios = vista.findViewById(R.id.contenedorAnunciosInfo);
        contenedorComponentes = vista.findViewById(R.id.contenedorComponentesInfo);

        btnInsta = vista.findViewById(R.id.btnInstagram);
        btnTwitter = vista.findViewById(R.id.btnTwitter);
        btnYoutube = vista.findViewById(R.id.btnYoutube);

        // Inicializar el mapa de Google
        SupportMapFragment mapaFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapaInfo);
        if (mapaFragment != null) {
            mapaFragment.getMapAsync(this);
        }

        descargarDatosBanda();
        descargarAnunciosInfo();
        descargarComponentesInfo();

        return vista;
    }

    private void descargarDatosBanda() {
        int idBanda = gestorSesion.obtenerIdBanda();
        ApiCliente.obtenerInstancia().obtenerBandaPorId(idBanda).enqueue(new Callback<Banda>() {
            @Override
            public void onResponse(Call<Banda> call, Response<Banda> response) {
                if (response.isSuccessful() && response.body() != null) {
                    bandaActual = response.body();
                    textoNombreBanda.setText(bandaActual.getNombre().toUpperCase());
                    // Aqui cargarias la foto con Glide/Picasso si tuvieras la libreria
                    configurarRedesSociales();
                    actualizarMapaConDireccion();
                }
            }

            @Override
            public void onFailure(Call<Banda> call, Throwable t) {
                Toast.makeText(getContext(), "Error al cargar info de banda", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void descargarAnunciosInfo() {
        int idBanda = gestorSesion.obtenerIdBanda();
        ApiCliente.obtenerInstancia().obtenerNoticiasPorBanda(idBanda).enqueue(new Callback<List<TablonAnuncio>>() {
            @Override
            public void onResponse(Call<List<TablonAnuncio>> call, Response<List<TablonAnuncio>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    dibujarListaAnuncios(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<TablonAnuncio>> call, Throwable t) {}
        });
    }

    /**
     * Crea tarjetas expandibles para cada anuncio.
     * Al hacer clic en el titulo, se muestra/oculta el mensaje.
     */
    private void dibujarListaAnuncios(List<TablonAnuncio> lista) {
        contenedorAnuncios.removeAllViews();
        for (TablonAnuncio anuncio : lista) {
            View vistaAnuncio = getLayoutInflater().inflate(android.R.layout.simple_list_item_2, null);
            CardView tarjeta = new CardView(getContext());
            tarjeta.setRadius(12);
            tarjeta.setCardElevation(4);
            tarjeta.setUseCompatPadding(true);

            LinearLayout layoutInterno = new LinearLayout(getContext());
            layoutInterno.setOrientation(LinearLayout.VERTICAL);
            layoutInterno.setPadding(30, 30, 30, 30);

            TextView titulo = new TextView(getContext());
            titulo.setText("• " + anuncio.getTitulo());
            titulo.setTextSize(16);
            titulo.setTextColor(Color.BLACK);
            titulo.setTypeface(null, android.graphics.Typeface.BOLD);

            TextView mensaje = new TextView(getContext());
            mensaje.setText(anuncio.getMensaje());
            mensaje.setVisibility(View.GONE); // Oculto por defecto
            mensaje.setPadding(20, 10, 0, 0);

            titulo.setOnClickListener(v -> {
                mensaje.setVisibility(mensaje.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            });

            layoutInterno.addView(titulo);
            layoutInterno.addView(mensaje);
            tarjeta.addView(layoutInterno);
            contenedorAnuncios.addView(tarjeta);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mapaGoogle = googleMap;
        actualizarMapaConDireccion();
    }

    /**
     * Extrae la direccion en texto de la base de datos y la convierte en
     * coordenadas de Google Maps utilizando el servicio de Geocoder.
     */
    private void actualizarMapaConDireccion() {
        if (mapaGoogle != null && bandaActual != null) {
            String direccionTexto = bandaActual.getDireccion();

            // Verificamos que la banda tenga una direccion guardada
            if (direccionTexto != null && !direccionTexto.trim().isEmpty()) {
                Geocoder geocodificador = new Geocoder(getContext(), Locale.getDefault());

                try {
                    // Buscamos 1 resultado que coincida con el texto de la direccion
                    List<Address> direccionesEncontradas = geocodificador.getFromLocationName(direccionTexto, 1);

                    if (direccionesEncontradas != null && !direccionesEncontradas.isEmpty()) {
                        Address ubicacionResultante = direccionesEncontradas.get(0);
                        LatLng coordenadas = new LatLng(ubicacionResultante.getLatitude(), ubicacionResultante.getLongitude());

                        // Limpiamos marcadores previos por si la vista se recarga
                        mapaGoogle.clear();

                        // Añadimos la chincheta y movemos la camara
                        mapaGoogle.addMarker(new MarkerOptions().position(coordenadas).title("Local de Ensayo"));
                        mapaGoogle.moveCamera(CameraUpdateFactory.newLatLngZoom(coordenadas, 16f));
                    } else {
                        Toast.makeText(getContext(), "Dirección no encontrada en Google Maps", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    Toast.makeText(getContext(), "Error de red al buscar el local", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "La banda no tiene dirección configurada", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void configurarRedesSociales() {
        btnInsta.setOnClickListener(v -> abrirEnlace(bandaActual.getInstagram()));
        btnTwitter.setOnClickListener(v -> abrirEnlace(bandaActual.getTwitter()));
        btnYoutube.setOnClickListener(v -> abrirEnlace(bandaActual.getYoutube()));
    }

    private void abrirEnlace(String url) {
        if (url != null && !url.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), "Red social no configurada", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Llama al servidor para descargar los detalles completos (nombre, instrumento, cargo)
     * de todos los componentes de la banda.
     */
    private void descargarComponentesInfo() {
        int idBanda = gestorSesion.obtenerIdBanda();
        ApiCliente.obtenerInstancia().obtenerUsuariosPorBanda(idBanda).enqueue(new Callback<List<ComponenteDTO>>() {
            @Override
            public void onResponse(Call<List<ComponenteDTO>> call, Response<List<ComponenteDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    dibujarListaComponentes(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ComponenteDTO>> call, Throwable t) {
                Toast.makeText(getContext(), "Error al cargar componentes", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Dibuja dinamicamente la lista visual.
     * Muestra la foto circular a la izquierda y apila a la derecha: Nombre, Cargo e Instrumento/Voz.
     */
    private void dibujarListaComponentes(List<com.example.appbandacomponente.Models.ComponenteDTO> listaComponentes) {
        contenedorComponentes.removeAllViews();

        if (listaComponentes.isEmpty()) {
            TextView textoVacio = new TextView(getContext());
            textoVacio.setText("No hay componentes registrados.");
            textoVacio.setTextColor(android.graphics.Color.parseColor("#999999"));
            contenedorComponentes.addView(textoVacio);
            return;
        }

        // Tamaño de la foto circular (50dp)
        int tamanoFoto = (int) (50 * getContext().getResources().getDisplayMetrics().density);
        int radio = tamanoFoto / 2;

        for (com.example.appbandacomponente.Models.ComponenteDTO comp : listaComponentes) {
            LinearLayout filaComponente = new LinearLayout(getContext());
            filaComponente.setOrientation(LinearLayout.HORIZONTAL);
            filaComponente.setPadding(0, 20, 0, 20);
            filaComponente.setGravity(android.view.Gravity.CENTER_VERTICAL);

            /*
             * 1. FOTO A LA IZQUIERDA
             */
            androidx.cardview.widget.CardView tarjetaFoto = new androidx.cardview.widget.CardView(getContext());
            LinearLayout.LayoutParams paramsTarjeta = new LinearLayout.LayoutParams(tamanoFoto, tamanoFoto);
            paramsTarjeta.setMargins(0, 0, 30, 0);
            tarjetaFoto.setLayoutParams(paramsTarjeta);
            tarjetaFoto.setRadius(radio);
            tarjetaFoto.setCardElevation(0f);
            tarjetaFoto.setCardBackgroundColor(android.graphics.Color.parseColor("#E0E0E0"));

            ImageView iconoPerfil = new ImageView(getContext());
            iconoPerfil.setImageResource(android.R.drawable.ic_menu_myplaces);
            iconoPerfil.setScaleType(ImageView.ScaleType.CENTER_CROP);
            tarjetaFoto.addView(iconoPerfil);

            filaComponente.addView(tarjetaFoto);

            /*
             * 2. TEXTOS APILADOS A LA DERECHA
             */
            LinearLayout cajaTextos = new LinearLayout(getContext());
            cajaTextos.setOrientation(LinearLayout.VERTICAL);

            // Bloque 1: Nombre (Negrita, oscuro)
            TextView vistaNombre = new TextView(getContext());
            vistaNombre.setText(comp.getNombreCompleto());
            vistaNombre.setTextSize(16);
            vistaNombre.setTextColor(android.graphics.Color.parseColor("#111111"));
            vistaNombre.setTypeface(null, android.graphics.Typeface.BOLD);

            // Bloque 2: Cargo (Gris oscuro, tamaño medio)
            TextView vistaCargo = new TextView(getContext());
            vistaCargo.setText(comp.getCargo());
            vistaCargo.setTextSize(14);
            vistaCargo.setTextColor(android.graphics.Color.parseColor("#444444"));
            vistaCargo.setPadding(0, 4, 0, 0); // Pequeña separación arriba

            // Bloque 3: Instrumento / Voz (Gris claro, tamaño pequeño)
            TextView vistaInstrumento = new TextView(getContext());
            vistaInstrumento.setText(comp.getInstrumentoYVoz());
            vistaInstrumento.setTextSize(13);
            vistaInstrumento.setTextColor(android.graphics.Color.parseColor("#888888"));

            cajaTextos.addView(vistaNombre);
            cajaTextos.addView(vistaCargo);
            cajaTextos.addView(vistaInstrumento);

            filaComponente.addView(cajaTextos);
            contenedorComponentes.addView(filaComponente);

            // 3. SEPARADOR INFERIOR
            View separador = new View(getContext());
            separador.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 2));
            separador.setBackgroundColor(android.graphics.Color.parseColor("#F0F2F5"));
            contenedorComponentes.addView(separador);
        }
    }
}