package com.example.proyecto.Vistas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto.APIREST;
import com.example.proyecto.R;

public class InicioActivity extends AppCompatActivity {
    EditText editUsuario, editPassword;
    TextView txtRegistro;
    Button btnEntrar;
    APIREST apirest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inicio);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    editUsuario = findViewById(R.id.edit_nombre_usuario);
    editPassword = findViewById(R.id.edit_password);
    btnEntrar = findViewById(R.id.btn_entrar);
    txtRegistro = findViewById(R.id.txt_registrar);

    txtRegistro.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(InicioActivity.this, RegistroActivity.class);
            startActivity(intent);
        }
    });

    btnEntrar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            apirest.inicioSesion(editUsuario.getText().toString(), editPassword.getText().toString(), success -> {
                if (success) {
                    Intent intent = new Intent(InicioActivity.this, PagInicioActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(InicioActivity.this, "Usuario o contraseña erróneos", Toast.LENGTH_SHORT).show();
                }
            });


        }
    });
    }
}