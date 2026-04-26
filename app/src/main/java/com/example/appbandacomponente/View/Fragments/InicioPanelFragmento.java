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
 * Este fragmento representa la vista principal o "Home" del panel del componente.
 * Aqui se mostraran los avisos importantes y la lista de proximos eventos.
 */
public class InicioPanelFragmento extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Aqui le decimos que infle tu archivo XML de los anuncios y eventos
        return inflater.inflate(R.layout.panel_de_componente, container, false);
    }
}