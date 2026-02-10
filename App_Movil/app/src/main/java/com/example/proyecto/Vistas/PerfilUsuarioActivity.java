package com.example.proyecto.Vistas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto.APIREST;
import com.example.proyecto.R;
import com.example.proyecto.Modelos.Usuario;

public class PerfilUsuarioActivity extends AppCompatActivity {
Usuario usuario;
APIREST apirest;
Toolbar tb;
ActionBar ab;
TextView nombre, apellidos,nombre_usuario, descripcion;
Button editarPerfil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfil_usuario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            nombre = findViewById(R.id.txtNombreP);
            apellidos = findViewById(R.id.txtApellidosP);
            nombre_usuario = findViewById(R.id.txtNombreUsuarioP);
            descripcion = findViewById(R.id.txtDescripcion);
            editarPerfil = findViewById(R.id.btEditar);
            tb = findViewById(R.id.tbMuro);
            setSupportActionBar(tb);
            ab = getSupportActionBar();
            getWindow().setNavigationBarColor(getColor(R.color.Azul_Logo));
            getWindow().setStatusBarColor(getColor(R.color.Azul_Logo));

            editarPerfil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PerfilUsuarioActivity.this, ModificarActivity.class);
                    intent.putExtra("usuarioPerfil", usuario);
                    startActivity(intent);
                }
            });


            return insets;
        });
    }

}