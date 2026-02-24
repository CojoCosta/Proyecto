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
import com.example.proyecto.Modelos.Usuario;

public class ModificarActivity extends AppCompatActivity {
    Usuario usuarioPrincipal, usuarioModificado;
    TextView volverPerfil;
    EditText nombre_usuario, nombre, apellidos, email, password;
    Button editar;
    APIREST apirest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_modificar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        nombre_usuario = findViewById(R.id.editUsuarioEd);
        nombre = findViewById(R.id.editNombreEd);
        apellidos = findViewById(R.id.editApellidosEd);
        email = findViewById(R.id.editEmailEd);
        password = findViewById(R.id.editPasswordEd);
        editar = findViewById(R.id.btEditar);
        volverPerfil = findViewById(R.id.volverPerfil);
        apirest = new APIREST();

        Intent intent = getIntent();
        usuarioPrincipal = (Usuario) intent.getSerializableExtra("usuarioPerfil");
        nombre_usuario.setText(usuarioPrincipal.getNombreUsuario());
        nombre.setText(usuarioPrincipal.getNombre());
        apellidos.setText(usuarioPrincipal.getApellidos());
        email.setText(usuarioPrincipal.getEmail());
        password.setText(usuarioPrincipal.getPassword());
        volverPerfil.setOnClickListener(v -> finish());

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apirest.modificarUsuario(nombre.getText().toString(), apellidos.getText().toString(), email.getText().toString(), password.getText().toString(), (success, usuario) -> {
                    if (success) {
                        Intent intent = new Intent(ModificarActivity.this, MuroActivity.class);
                        intent.putExtra("usuario", usuario);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(ModificarActivity.this, "Error al modificar usuario", Toast.LENGTH_SHORT).show();
                    }

                });
            }
        });
    }
}