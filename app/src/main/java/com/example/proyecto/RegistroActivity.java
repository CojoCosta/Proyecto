package com.example.proyecto;

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
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegistroActivity extends AppCompatActivity {
    Usuario usuario;
    ActionBar actionBar;
    EditText editNombre, editApellidos, editUsuario, editEmail, editPassword;
    TextView tenerCuenta;
    Button btnRegistrar;

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
        tenerCuenta = findViewById(R.id.tenerCuentaReg);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editNombre.getText().equals("") || editApellidos.getText().equals("") || editUsuario.getText().equals("") || editEmail.getText().equals("") || editPassword.getText().equals("")) {
                    Toast.makeText(RegistroActivity.this, "Hay algún campo vacio", Toast.LENGTH_SHORT).show();
                } else {
                    usuario = new Usuario(editNombre.toString(), editApellidos.toString(), editUsuario.toString(), editEmail.toString(), editPassword.toString());
                }
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