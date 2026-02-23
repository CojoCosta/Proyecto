package com.example.proyecto.Vistas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto.APIREST;
import com.example.proyecto.Modelos.Usuario;
import com.example.proyecto.R;

public class PerfilUsuarioActivity extends AppCompatActivity {
    Usuario usuario;
    APIREST apirest;
    Toolbar tb;
    ActionBar ab;
    TextView nombre, apellidos, nombre_usuario, descripcion;
    Button editarPerfil;
    ImageButton btnNavMuro, btnNavNuevaPubli, btnNavPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfil_usuario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        usuario = (Usuario) intent.getSerializableExtra("usuario");

        nombre = findViewById(R.id.txtNombreP);
        apellidos = findViewById(R.id.txtApellidosP);
        nombre_usuario = findViewById(R.id.txtNombreUsuarioP);
        descripcion = findViewById(R.id.txtDescripcion);
        // editarPerfil = findViewById(R.id.btEditar); // Este ID no existe en el layout activity_perfil_usuario.xml

        tb = findViewById(R.id.tbPerfil);
        setSupportActionBar(tb);
        ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("");
        }

        getWindow().setNavigationBarColor(getColor(R.color.Azul_Logo));
        getWindow().setStatusBarColor(getColor(R.color.Azul_Logo));

        if (usuario != null) {
            nombre.setText(usuario.getNombre());
            apellidos.setText(usuario.getApellidos());
            nombre_usuario.setText(usuario.getNombreUsuario());
        }

        /*
        editarPerfil.setOnClickListener(v -> {
            Intent editIntent = new Intent(PerfilUsuarioActivity.this, ModificarActivity.class);
            editIntent.putExtra("usuarioPerfil", usuario);
            startActivity(editIntent);
        });
        */

        btnNavMuro = findViewById(R.id.btnNavMuro);
        btnNavNuevaPubli = findViewById(R.id.btnNavNuevaPubli);
        btnNavPerfil = findViewById(R.id.btnNavPerfil);

        btnNavMuro.setOnClickListener(v -> {
            Intent muroIntent = new Intent(PerfilUsuarioActivity.this, MuroActivity.class);
            muroIntent.putExtra("usuario", usuario);
            startActivity(muroIntent);
        });

        btnNavNuevaPubli.setOnClickListener(v -> {
            Intent nuevaPubliIntent = new Intent(PerfilUsuarioActivity.this, NuevaPublicacionActivity.class);
            nuevaPubliIntent.putExtra("usuario", usuario);
            startActivity(nuevaPubliIntent);
        });

        btnNavPerfil.setOnClickListener(v -> {
            // Ya estás aquí
        });
    }
}