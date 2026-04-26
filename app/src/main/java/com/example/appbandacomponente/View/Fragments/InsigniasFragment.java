package com.example.appbandacomponente.View.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appbandacomponente.R;

/**
 * Introduccion explicativa:
 * Fragmento encargado de gestionar la pantalla de insignias y estadisticas de asistencia.
 * Muestra el progreso del musico en eventos y conciertos, asi como los logros obtenidos.
 */
public class InsigniasFragment extends Fragment {

    private ProgressBar barraEventos, barraConciertos;
    private TextView porcentajeEventos, porcentajeConciertos;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflamos el diseño oscuro que has preparado para esta seccion
        return inflater.inflate(R.layout.insig_estadis, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Vinculamos los elementos dinamicos (barras de progreso y textos)
        barraEventos = view.findViewById(R.id.barraProgresoEventos);
        porcentajeEventos = view.findViewById(R.id.textoPorcentajeEventos);

        barraConciertos = view.findViewById(R.id.barraProgresoConciertos);
        porcentajeConciertos = view.findViewById(R.id.textoPorcentajeConciertos);

        // De momento, los valores se quedan como estan en el XML hasta que conectemos la base de datos
    }
}