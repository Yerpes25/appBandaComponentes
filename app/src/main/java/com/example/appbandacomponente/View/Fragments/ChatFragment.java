package com.example.appbandacomponente.View.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appbandacomponente.R;

/**
 * Fragmento que representa la interfaz de mensajeria instantanea o chat de la banda.
 * Gestiona la visualizacion de la conversacion y permite a los usuarios redactar
 * y enviar mensajes de texto o archivos adjuntos a sus compañeros.
 */
public class ChatFragment extends Fragment {

    private EditText entradaMensaje;
    private ImageView botonEnviar, botonAdjuntar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflamos el diseño del chat con las burbujas de colores
        return inflater.inflate(R.layout.chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Vinculamos los controles de la parte inferior
        entradaMensaje = view.findViewById(R.id.entradaMensaje);
        botonEnviar = view.findViewById(R.id.botonEnviar);
        botonAdjuntar = view.findViewById(R.id.botonAdjuntar);

        // Configuramos el clic del boton enviar de forma manual
        botonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mensaje = entradaMensaje.getText().toString().trim();
                if (!mensaje.isEmpty()) {
                    // Por ahora solo limpiamos la caja, luego conectaremos con el servidor
                    entradaMensaje.setText("");
                    Toast.makeText(getContext(), "Enviando mensaje...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}