package com.example.appbandacomponente.View.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appbandacomponente.MainActivity;
import com.example.appbandacomponente.Models.Usuario;
import com.example.appbandacomponente.NetWorks.ApiCliente;
import com.example.appbandacomponente.R;
import com.example.appbandacomponente.Utilities.GestorSesion;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Fragmento encargado de gestionar el perfil del usuario dentro de la aplicación.
 * Permite visualizar la información personal, editar los datos de contacto y tallas
 * de uniformidad, sincronizar los cambios con el servidor y gestionar el cierre de sesión.
 */
public class PerfilFragment extends Fragment {

    private GestorSesion gestorSesion;

    private TextView nombreMostrar;
    private LinearLayout layoutEdicion;
    private EditText campoNombre, campoApellidos, campoDni, campoEmail, campoTelefono, campoEmergencia;
    private EditText campoDireccion, campoTallaCamisa, campoTallaChaqueta, campoTallaPantalon, campoTallaGorra;
    private Button botonCerrar, btnEditar, botonGuardarCambios;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.perfil_cuenta, container, false);

        gestorSesion = new GestorSesion(getContext());

        nombreMostrar = vista.findViewById(R.id.textoNombreUsuario);
        botonCerrar = vista.findViewById(R.id.botonCerrarSesion);
        btnEditar = vista.findViewById(R.id.botonEditarPerfil);
        botonGuardarCambios = vista.findViewById(R.id.botonGuardarCambios);
        layoutEdicion = vista.findViewById(R.id.contenedorEdicion);

        campoNombre = vista.findViewById(R.id.editNombre);
        campoApellidos = vista.findViewById(R.id.editApellidos);
        campoDni = vista.findViewById(R.id.editDni);
        campoEmail = vista.findViewById(R.id.editEmail);
        campoTelefono = vista.findViewById(R.id.editTelefono);
        campoEmergencia = vista.findViewById(R.id.editEmergencia);
        campoDireccion = vista.findViewById(R.id.editDireccion);
        campoTallaCamisa = vista.findViewById(R.id.editTallaCamisa);
        campoTallaChaqueta = vista.findViewById(R.id.editTallaChaqueta);
        campoTallaPantalon = vista.findViewById(R.id.editTallaPantalon);
        campoTallaGorra = vista.findViewById(R.id.editTallaGorra);

        nombreMostrar.setText(gestorSesion.obtenerNombreUsuario() + " " + gestorSesion.obtenerApellidosUsuario());

        botonCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gestorSesion.cerrarSesion();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutEdicion.setVisibility(View.VISIBLE);

                campoNombre.setText(gestorSesion.obtenerNombreUsuario());
                campoApellidos.setText(gestorSesion.obtenerApellidosUsuario());
                campoDni.setText(gestorSesion.obtenerDni());
                campoEmail.setText(gestorSesion.obtenerEmail());
                campoTelefono.setText(gestorSesion.obtenerTelefono());
                campoEmergencia.setText(gestorSesion.obtenerContactoEmergencia());
                campoDireccion.setText(gestorSesion.obtenerDireccion());
                campoTallaCamisa.setText(gestorSesion.obtenerTallaCamisa());
                campoTallaChaqueta.setText(gestorSesion.obtenerTallaChaqueta());
                campoTallaPantalon.setText(gestorSesion.obtenerTallaPantalon());
                campoTallaGorra.setText(gestorSesion.obtenerTallaGorra());
            }
        });

        botonGuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarDatosEnServidor();
            }
        });

        return vista;
    }

    private void actualizarDatosEnServidor() {
        botonGuardarCambios.setEnabled(false);
        botonGuardarCambios.setText("Actualizando...");

        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setNombre(campoNombre.getText().toString().trim());
        usuarioActualizado.setApellidos(campoApellidos.getText().toString().trim());
        usuarioActualizado.setDni(campoDni.getText().toString().trim());
        usuarioActualizado.setEmail(campoEmail.getText().toString().trim());
        usuarioActualizado.setTelefono(campoTelefono.getText().toString().trim());
        usuarioActualizado.setContactEmerg(campoEmergencia.getText().toString().trim());
        usuarioActualizado.setDireccion(campoDireccion.getText().toString().trim());
        usuarioActualizado.setTallaCamisa(campoTallaCamisa.getText().toString().trim());
        usuarioActualizado.setTallaChaqueta(campoTallaChaqueta.getText().toString().trim());
        usuarioActualizado.setTallaPantalon(campoTallaPantalon.getText().toString().trim());
        usuarioActualizado.setTallaGorra(campoTallaGorra.getText().toString().trim());

        int idUsuario = gestorSesion.obtenerIdUsuario();

        Call<Usuario> llamada = ApiCliente.obtenerInstancia().actualizarUsuario(idUsuario, usuarioActualizado);
        llamada.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                botonGuardarCambios.setEnabled(true);
                botonGuardarCambios.setText("Actualizar en Base de Datos");

                if (response.isSuccessful() && response.body() != null) {
                    gestorSesion.actualizarDatosPersonales(
                            usuarioActualizado.getNombre(),
                            usuarioActualizado.getApellidos(),
                            usuarioActualizado.getDni(),
                            usuarioActualizado.getEmail(),
                            usuarioActualizado.getTelefono(),
                            usuarioActualizado.getContactEmerg(),
                            usuarioActualizado.getDireccion(),
                            usuarioActualizado.getTallaCamisa(),
                            usuarioActualizado.getTallaChaqueta(),
                            usuarioActualizado.getTallaPantalon(),
                            usuarioActualizado.getTallaGorra()
                    );

                    nombreMostrar.setText(usuarioActualizado.getNombre() + " " + usuarioActualizado.getApellidos());
                    layoutEdicion.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Error al actualizar los datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                botonGuardarCambios.setEnabled(true);
                botonGuardarCambios.setText("Actualizar en Base de Datos");
                Toast.makeText(getContext(), "Fallo de conexion", Toast.LENGTH_SHORT).show();
            }
        });
    }
}