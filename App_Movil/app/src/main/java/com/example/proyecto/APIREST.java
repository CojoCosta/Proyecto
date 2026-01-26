package com.example.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto.Vistas.RegistroActivity;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class APIREST {
    public void anadirUsuario(String nombre, String apellidos, String nombreUsuario, String email, String password, String fecha){
        new Thread(()-> {
            try {
                //cambiar ip por 192.130.0.13 si no funciona en el emulador
                URL url = new URL("http://10.0.2.2:8080/tema5maven/rest/deportista/android");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();//Abrir conexion
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");
                con.setDoOutput(true); //escribir en el body

                JSONObject jsonObject =  new JSONObject();
                jsonObject.put("nombre", nombre);
                jsonObject.put("apellidos", apellidos);
                jsonObject.put("nombreUsuario", nombreUsuario);
                jsonObject.put("email", email);
                jsonObject.put("password", password);
                jsonObject.put("fecha", fecha);

                System.out.println(jsonObject);

                try(OutputStream os = con.getOutputStream()){
                    os.write(jsonObject.toString().getBytes(StandardCharsets.UTF_8));//Enviar el body
                }catch (Exception e){}
                int code = con.getResponseCode();//Forzar envio

                Log.i("Codigo API-REST", "El resultante es "+code);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

//    public static class RegistroActivity extends AppCompatActivity {
//        EditText editNombre, editApellidos, editUsuario, editEmail, editPassword, editFecha;
//        TextView txtRegistro;
//        Button btnRegistrar;
//
//
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            EdgeToEdge.enable(this);
//            setContentView(R.layout.activity_registro);
//            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//                return insets;
//            });
//            editNombre= findViewById(R.id.editNombreReg);
//            editApellidos = findViewById(R.id.editApellidosReg);
//            editUsuario = findViewById(R.id.editUsuarioReg);
//            editEmail = findViewById(R.id.editEmailReg);
//            editPassword = findViewById(R.id.editPasswordReg);
//            btnRegistrar = findViewById(R.id.btnRegistrar);
//            btnRegistrar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(RegistroActivity.this, RegistroActivity.class);
//
//                startActivity(intent);
//            }
//        });
//        }
//    }
}
