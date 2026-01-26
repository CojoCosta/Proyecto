package com.example.proyecto.Vistas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
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
        editNombre = findViewById(R.id.editNombreReg);
        editApellidos = findViewById(R.id.editApellidosReg);
        editUsuario = findViewById(R.id.editUsuarioReg);
        editEmail = findViewById(R.id.editEmailReg);
        editPassword = findViewById(R.id.editPasswordReg);
        editFecha = findViewById(R.id.editFechaReg);
        tenerCuenta = findViewById(R.id.tenerCuentaReg);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        apirest = new APIREST();
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