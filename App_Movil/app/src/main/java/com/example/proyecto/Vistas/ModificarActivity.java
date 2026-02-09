package com.example.proyecto.Vistas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto.R;
import com.example.proyecto.Usuario;

public class ModificarActivity extends AppCompatActivity {
    Usuario usuarioPrincipal, usuarioModificado;
    TextView volverPerfil;
    EditText nombre_usuario, nombre, apellidos, email, password;
    Button editar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_modificar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            nombre_usuario = findViewById(R.id.editUsuarioEd);
            nombre = findViewById(R.id.editNombreEd);
            apellidos = findViewById(R.id.editApellidosEd);
            email = findViewById(R.id.editEmailEd);
            password = findViewById(R.id.editPasswordEd);
            editar = findViewById(R.id.btnEditar);
            volverPerfil = findViewById(R.id.volverPerfil);

            Intent intent = getIntent();
            usuarioPrincipal = (Usuario) intent.getSerializableExtra("usuarioPerfil");
            nombre_usuario.setText(usuarioPrincipal.getNombreUsuario());
            nombre.setText(usuarioPrincipal.getNombre());
            apellidos.setText(usuarioPrincipal.getApellidos());
            email.setText(usuarioPrincipal.getEmail());
            password.setText(usuarioPrincipal.getPassword());

            return insets;
        });
    }
}