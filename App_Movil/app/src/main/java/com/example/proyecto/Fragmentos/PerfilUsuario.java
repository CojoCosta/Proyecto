package com.example.proyecto.Fragmentos;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.proyecto.APIREST;
import com.example.proyecto.Modelos.Usuario;
import com.example.proyecto.R;
import com.example.proyecto.Vistas.ModificarActivity;

public class PerfilUsuario extends Fragment {

    private Usuario usuario;
    private APIREST apirest;

    private Toolbar tb;
    private ActionBar ab;
    private TextView nombre, apellidos, nombre_usuario, descripcion;
    private ImageButton editarPerfil;

    public PerfilUsuario() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            usuario = (Usuario) getArguments().getSerializable("usuarioPerfil");
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil_usuario, container, false);

        nombre = view.findViewById(R.id.txtNombreP);
        apellidos = view.findViewById(R.id.txtApellidosP);
        nombre_usuario = view.findViewById(R.id.txtNombreUsuarioP);
        descripcion = view.findViewById(R.id.txtDescripcion);
        editarPerfil = view.findViewById(R.id.btEditar);
        tb = view.findViewById(R.id.tbPerfil);

        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.setSupportActionBar(tb);
        ab = activity.getSupportActionBar();

        activity.getWindow().setStatusBarColor(
                activity.getColor(R.color.Azul_Logo)
        );
        activity.getWindow().setNavigationBarColor(
                activity.getColor(R.color.Azul_Logo)
        );

        if (usuario != null) {
            nombre.setText(usuario.getNombre());
            apellidos.setText(usuario.getApellidos());
            nombre_usuario.setText(usuario.getNombreUsuario());
            descripcion.setText("Descripción del usuario");
        }

        editarPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), ModificarActivity.class);
            intent.putExtra("usuarioPerfil", usuario);
            startActivity(intent);
        });

        return view;
    }
}
