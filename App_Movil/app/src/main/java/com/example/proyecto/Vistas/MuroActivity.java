package com.example.proyecto.Vistas;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto.R;
import com.example.proyecto.Usuario;

public class MuroActivity extends AppCompatActivity {
    Usuario usuario;
    Toolbar tb;
    ActionBar ab;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_muro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            tb = findViewById(R.id.tbMuro);
            setSupportActionBar(tb);
            ab = getSupportActionBar();
            getWindow().setNavigationBarColor(getColor(R.color.Azul_Logo));
            getWindow().setStatusBarColor(getColor(R.color.Azul_Logo));
            Intent intentUsuario = getIntent();
            usuario = (Usuario) intentUsuario.getSerializableExtra("usuario");
            return insets;
        });
    }
}