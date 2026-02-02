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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class APIREST {
    Usuario usuario;

    public void anadirUsuario(String nombre, String apellidos, String nombre_usuario, String email, String password, String fecha_nacimiento){
        new Thread(()-> {
            try {
                //cambiar ip por 192.130.0.13 si no funciona en el emulador
                URL url = new URL("http://10.0.2.2:8080/apirest/rest/usuario/insertar");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();//Abrir conexion
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");
                con.setDoOutput(true); //escribir en el body
                JSONObject jsonObject =  new JSONObject();
                jsonObject.put("nombre_usuario", nombre_usuario);
                jsonObject.put("nombre", nombre);
                jsonObject.put("apellidos", apellidos);
                jsonObject.put("email", email);
                jsonObject.put("password", password);
                jsonObject.put("fecha_nacimiento", fecha_nacimiento);

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

    public Usuario iniciarSesion(String nombre_usuario, String password){
        new Thread(()->{
           try {
               URL url = new URL("http://10.0.2.2:8080/apirest/rest/usuario/insertar");
               HttpURLConnection con = (HttpURLConnection) url.openConnection();//Abrir conexion
               con.setRequestMethod("GET");
               con.setRequestProperty("Accept", "application/json");
               con.setDoOutput(true); //escribir en el body
               JSONObject jsonObject =  new JSONObject();
               jsonObject.put("nombre_usuario", nombre_usuario);
               jsonObject.put("password", password);
               int code = con.getResponseCode();
               System.out.println("Código HTTP: " + code);
               if (code == 200) {
                   BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
                   StringBuilder response = new StringBuilder();
                   String line;
                   while ((line = reader.readLine()) != null) {
                       response.append(line.trim());
                   }
                   JSONArray array = new JSONArray(response.toString());
                   for (int i = 0; i < array.length(); i++) {
                       JSONObject obj = array.getJSONObject(i);
                       String cadaUsuario = obj.getString("nombre_usuario");
                       String cadaPassword = obj.getString("password");
                       if (cadaUsuario.equals(nombre_usuario) && cadaPassword.equals(password)){
                            usuario = new Usuario(nombre_usuario, password);
                       }
                   }
               }
           } catch (Exception e) {
               throw new RuntimeException(e);
           }
        }).start();
        return usuario;
    }
}
