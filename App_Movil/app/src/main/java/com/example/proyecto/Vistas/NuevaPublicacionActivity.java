package com.example.proyecto.Vistas;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

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

public class NuevaPublicacionActivity extends AppCompatActivity {
    EditText contenido;
    Button btnPublicar;
    APIREST apirest;
    Toolbar tb;
    ActionBar ab;
    ImageButton btnNavMuro, btnNavNuevaPubli, btnNavPerfil;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nueva_publicacion);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tb = findViewById(R.id.tbNuevaPubli);
        setSupportActionBar(tb);
        ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("");
        }

        Intent intent = getIntent();
        usuario = (Usuario) intent.getSerializableExtra("usuario");

        contenido = findViewById(R.id.editContenido);
        btnPublicar = findViewById(R.id.btnSubirPubli);
        apirest = new APIREST();

        btnPublicar.setOnClickListener(v -> {
            String textoContenido = contenido.getText().toString().trim();
            if (!textoContenido.isEmpty()) {
                if (usuario != null) {
                    apirest.anadirPublicacion(usuario.getId_usuario(), usuario.getNombreUsuario(), textoContenido, success -> {
                        if (success) {
                            Toast.makeText(this, "Publicación subida con éxito", Toast.LENGTH_SHORT).show();
                            // Volver al muro
                            Intent muroIntent = new Intent(NuevaPublicacionActivity.this, MuroActivity.class);
                            muroIntent.putExtra("usuario", usuario);
                            startActivity(muroIntent);
                            finish();
                        } else {
                            Toast.makeText(this, "Error al subir la publicación", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(this, "Error: Usuario no identificado", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "El contenido no puede estar vacío", Toast.LENGTH_SHORT).show();
            }
        });

        btnNavMuro = findViewById(R.id.btnNavMuro);
        btnNavNuevaPubli = findViewById(R.id.btnNavNuevaPubli);
        btnNavPerfil = findViewById(R.id.btnNavPerfil);

        btnNavMuro.setOnClickListener(v -> {
            Intent muroIntent = new Intent(NuevaPublicacionActivity.this, MuroActivity.class);
            muroIntent.putExtra("usuario", usuario);
            startActivity(muroIntent);
            finish();
        });

        btnNavNuevaPubli.setOnClickListener(v -> {
            // Ya estás aquí
        });

        btnNavPerfil.setOnClickListener(v -> {
            Intent perfilIntent = new Intent(NuevaPublicacionActivity.this, PerfilUsuarioActivity.class);
            perfilIntent.putExtra("usuario", usuario);
            startActivity(perfilIntent);
            finish();
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}