package com.example.proyecto.Vistas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.proyecto.Usuario;

public class RegistroActivity extends AppCompatActivity {
    Usuario usuario;
    ActionBar actionBar;
    EditText editNombre, editApellidos, editUsuario, editEmail, editPassword, editFecha;
    TextView tenerCuenta;
    Button btnRegistrar;
    APIREST apirest;
    Toolbar tbRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWindow().setNavigationBarColor(getColor(R.color.Azul_Logo));
        getWindow().setStatusBarColor(getColor(R.color.Azul_Logo));
        editNombre = findViewById(R.id.editNombreEd);
        editApellidos = findViewById(R.id.editApellidosEd);
        editUsuario = findViewById(R.id.editUsuarioEd);
        editEmail = findViewById(R.id.editEmailEd);
        editPassword = findViewById(R.id.editPasswordEd);
        editFecha = findViewById(R.id.editFechaReg);
        tenerCuenta = findViewById(R.id.tenerCuentaReg);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        tbRegistro = findViewById(R.id.tbRegistro);




        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apirest.anadirUsuario(editNombre.getText().toString(), editApellidos.getText().toString(), editUsuario.getText().toString(), editEmail.getText().toString(), editPassword.getText().toString(), editFecha.getText().toString());
            }
        });
        tenerCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistroActivity.this, InicioActivity.class);
                startActivity(intent);
            }
        });

    }
}