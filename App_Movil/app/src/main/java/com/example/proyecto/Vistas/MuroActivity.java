package com.example.proyecto.Vistas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto.APIREST;
import com.example.proyecto.AdaptadorMuro;
import com.example.proyecto.Modelos.Publicacion;
import com.example.proyecto.R;
import com.example.proyecto.Modelos.Usuario;

import java.util.ArrayList;

public class MuroActivity extends AppCompatActivity {
    Usuario usuario;
    ArrayList<Usuario> usuarios;
    ArrayList<Publicacion> publicaciones;
    Toolbar tb;
    ActionBar ab;
    RecyclerView rv;
    RecyclerView.LayoutManager layout;
    AdaptadorMuro adaptadorMuro;
    APIREST apirest;
    ImageButton btnNavMuro, btnNavNuevaPubli, btnNavPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_muro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tb = findViewById(R.id.tbMuro);
        tb.setTitle("");
        setSupportActionBar(tb);
        ab = getSupportActionBar();
        apirest = new APIREST();
        getWindow().setNavigationBarColor(getColor(R.color.Azul_Logo));
        getWindow().setStatusBarColor(getColor(R.color.Azul_Logo));

        Intent intentUsuario = getIntent();
        usuario = (Usuario) intentUsuario.getSerializableExtra("usuario");

        rv = findViewById(R.id.recyclerView);
        publicaciones = new ArrayList<>();
        usuarios = new ArrayList<>();
        if (usuario != null) {
            usuarios.add(usuario);
        }

        layout = new LinearLayoutManager(this);
        rv.setLayoutManager(layout);

        adaptadorMuro = new AdaptadorMuro(publicaciones, usuarios, usuario);
        rv.setAdapter(adaptadorMuro);

        apirest.obtenerPublicaciones((success, publicacionesbd) -> {
            if (success && publicacionesbd != null){
                publicaciones.clear();
                publicaciones.addAll(publicacionesbd);
                adaptadorMuro.notifyDataSetChanged();
            }
        });

        // Configuración de la barra de navegación
        btnNavMuro = findViewById(R.id.btnNavMuro);
        btnNavNuevaPubli = findViewById(R.id.btnNavNuevaPubli);
        btnNavPerfil = findViewById(R.id.btnNavPerfil);

        btnNavMuro.setOnClickListener(v -> {
            // Ya estás aquí
        });

        btnNavNuevaPubli.setOnClickListener(v -> {
            Intent nuevaPubliIntent = new Intent(MuroActivity.this, NuevaPublicacionActivity.class);
            nuevaPubliIntent.putExtra("usuario", usuario);
            startActivity(nuevaPubliIntent);
        });

        btnNavPerfil.setOnClickListener(v -> {
            Intent perfilIntent = new Intent(MuroActivity.this, PerfilUsuarioActivity.class);
            perfilIntent.putExtra("usuario", usuario);
            startActivity(perfilIntent);
        });
    }
}