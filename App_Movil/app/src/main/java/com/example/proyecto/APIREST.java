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
}
