package com.example.proyecto;

import android.util.Log;

import com.example.proyecto.Modelos.Publicacion;
import com.example.proyecto.Modelos.Usuario;

import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class APIREST {
    String pathUsuarios = "http://10.0.2.2:8080/apirest/rest/usuarios/";
    String pathPublicaciones = "http://10.0.2.2:8080/apirest/rest/publicaciones/";
    //#region INTERFACES
    public interface ApiCallback {
        void onResult(boolean success);
    }
    public interface PostsCallback {
        void onResult(ArrayList<Publicacion> posts);
    }
    public interface LoginCallback {
        void onLoginResult(boolean success, Usuario u);
    }
    //#endregion
    public void anadirUsuario(String nombre, String apellidos, String nombre_usuario, String email, String password, String fecha_nacimiento){
        new Thread(()-> {
            try {
                System.out.println(nombre);
                //cambiar ip por 192.130.0.13 si no funciona en el emulador
                URL url = new URL(pathUsuarios);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();//Abrir conexion
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");
                con.setDoOutput(true); //escribir en el body
                JSONObject jsonObject =  new JSONObject();
                jsonObject.put("nombreUsuario", nombre_usuario);
                jsonObject.put("nombre", nombre);
                jsonObject.put("apellidos", apellidos);
                jsonObject.put("email", email);
                jsonObject.put("password", password);
                // jsonObject.put("fecha_nacimiento", fecha_nacimiento);

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
    Usuario usuario = null;
    public void inicioSesion(String nombre_usuario, String password, LoginCallback callback) {
        new Thread(() -> {
            try {
                URL url = new URL(pathUsuarios + "inicioSesion/"+nombre_usuario+"/"+password);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                int code = conn.getResponseCode();
                System.out.println("Código HTTP: " + code);

                if (code == 200) {
                    callback.onLoginResult(true, usuario);
                } else {
                    callback.onLoginResult(false, null);
                }
            } catch (Exception e) {
                e.printStackTrace();
                callback.onLoginResult(false , null);
            }
        }).start();
    }

    public void obtenerDatosUsuario(String nombreUsuario, ApiCallback callback){
        new Thread(()->{
            HttpURLConnection conexion = null;
            try {
                URL url = new URL(pathUsuarios + "/perfil/{nombre_usuario}");
                conexion = (HttpURLConnection) url.openConnection();
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
