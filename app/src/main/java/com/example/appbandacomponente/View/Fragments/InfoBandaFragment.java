package com.example.appbandacomponente.View.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appbandacomponente.R;

/**
 * Introduccion explicativa:
 * Fragmento que muestra la informacion detallada de la banda.
 * Incluye el tablon de anuncios, la ubicacion del local y enlaces a redes sociales.
 */
public class InfoBandaFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflamos el diseño XML de informacion de banda
        return inflater.inflate(R.layout.info_banda, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Aqui programaremos mas adelante la carga de anuncios y el mapa
    }
}