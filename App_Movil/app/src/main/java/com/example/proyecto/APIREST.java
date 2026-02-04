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
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class APIREST {
    String pathPrincipal = "http://10.0.2.2:8080/apirest/rest/usuario/";
    //#region INTERFACES
    public interface ApiCallback {
        void onResult(boolean success);
    }
    public interface PostsCallback {
        void onResult(ArrayList<Publicacion> posts);
    }
    public interface LoginCallback {
        void onLoginResult(boolean success);
    }
    //#endregion
    public void anadirUsuario(String nombre, String apellidos, String nombre_usuario, String email, String password, String fecha_nacimiento){
        new Thread(()-> {
            try {
                //cambiar ip por 192.130.0.13 si no funciona en el emulador
                URL url = new URL(pathPrincipal + "insertar");
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

    public void inicioSesion(String nombre_usuario, String password, LoginCallback callback) {
        new Thread(() -> {
            try {
                URL url = new URL(pathPrincipal + "inicioSesion/{"+nombre_usuario+"}/{"+password+"}");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                int code = conn.getResponseCode();
                System.out.println("Código HTTP: " + code);

                if (code == 200) {
                    callback.onLoginResult(true);
                } else {
                    callback.onLoginResult(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
                callback.onLoginResult(false);
            }
        }).start();
    }

    public void obtenerDatosUsuario(String nombreUsuario, ApiCallback callback){
        new Thread(()->{
            HttpURLConnection conexion = null;
            try {
                URL url = new URL(pathPrincipal + "/datosUsuario/{nombre_usuario}");
                conexion = (HttpURLConnection) url.openConnection();//Abrir conexion
                conexion.setRequestMethod("GET");
                conexion.setRequestProperty("Accept", "application/json");
                int code = conexion.getResponseCode();
                callback.onResult(code == HttpURLConnection.HTTP_OK);
            }catch (IOException e){
                callback.onResult(false);
            }finally {
                if (conexion != null){
                    conexion.disconnect();
                }
            }
        });
    }

}
