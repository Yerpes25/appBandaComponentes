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
 * Fragmento que gestiona la pantalla del repertorio de marchas.
 * Se encarga de mostrar la lista de partituras y gestionar los filtros de busqueda.
 */
public class MarchasFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Vinculacion del fragmento con su diseño XML correspondiente
        return inflater.inflate(R.layout.panel_marchas, container, false);
    }
}