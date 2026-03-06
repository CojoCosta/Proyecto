package com.example.proyecto;

import android.util.Log;

import com.example.proyecto.Modelos.Publicacion;
import com.example.proyecto.Modelos.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
        void onResult(boolean success, ArrayList<Publicacion> posts);
    }
    public interface LoginCallback {
        void onLoginResult(boolean success, Usuario u);
    }
    public interface ModificarUsuarioCallback {
        void onResult(boolean success, Usuario usuario);
    }
    //#endregion
    public void anadirUsuario(String nombre, String apellidos, String nombre_usuario, String email, String password, String fecha_nacimiento){
        new Thread(()-> {
            try {
                URL url = new URL(pathUsuarios);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");
                con.setDoOutput(true);
                JSONObject jsonObject =  new JSONObject();
                jsonObject.put("nombreUsuario", nombre_usuario);
                jsonObject.put("nombre", nombre);
                jsonObject.put("apellidos", apellidos);
                jsonObject.put("email", email);
                jsonObject.put("password", password);

                try(OutputStream os = con.getOutputStream()){
                    os.write(jsonObject.toString().getBytes(StandardCharsets.UTF_8));
                }
                int code = con.getResponseCode();
                Log.i("APIREST", "anadirUsuario Code: " + code);
            } catch (Exception e) {
                Log.e("APIREST", "Error anadirUsuario", e);
            }
        }).start();
    }

    public void inicioSesion(String nombre_usuario, String password, LoginCallback callback) {
        new Thread(() -> {
            try {
                URL url = new URL(pathUsuarios + "inicioSesion/"+nombre_usuario+"/"+password);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                int code = conn.getResponseCode();
                if (code == 200) {
                    String response = readResponse(conn);
                    JSONObject obj = new JSONObject(response);
                    
                    Usuario u = new Usuario(
                            obj.optString("nombreUsuario"),
                            obj.optString("nombre"),
                            obj.optString("apellidos"),
                            obj.optString("email"),
                            null, 
                            obj.optString("password")
                    );
                    u.setId_usuario(obj.optInt("id_usuario"));

                    new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                        callback.onLoginResult(true, u);
                    });
                } else {
                    new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                        callback.onLoginResult(false, null);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                    callback.onLoginResult(false , null);
                });
            }
        }).start();
    }

    public void obtenerDatosUsuario(String nombreUsuario, ApiCallback callback){
        new Thread(()->{
            HttpURLConnection conexion = null;
            try {
                URL url = new URL(pathUsuarios + "perfil/" + nombreUsuario);
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
        }).start();
    }

    public void modificarUsuario(String nombre, String apellidos, String email, String password, ModificarUsuarioCallback callback) {
        new Thread(() -> {
            try {
                URL url = new URL(pathUsuarios);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("PUT");
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");
                con.setDoOutput(true);

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("nombre", nombre);
                jsonObject.put("apellidos", apellidos);
                jsonObject.put("email", email);
                jsonObject.put("password", password);

                try (OutputStream os = con.getOutputStream()) {
                    os.write(jsonObject.toString().getBytes(StandardCharsets.UTF_8));
                }

                int code = con.getResponseCode();
                boolean success = (code >= 200 && code < 300);
                Usuario usuarioActualizado = new Usuario(nombre, apellidos, email, password);

                new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                    callback.onResult(success, usuarioActualizado);
                });

            } catch (Exception e) {
                e.printStackTrace();
                new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                    callback.onResult(false, null);
                });
            }
        }).start();
    }

    private String readResponse(HttpURLConnection connection) throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
    }

    public void obtenerPublicaciones(PostsCallback callback){
        new Thread(()->{
            ArrayList<Publicacion> listaPublicaciones = new ArrayList<>();
            HttpURLConnection conexion = null;
            try {
                URL url = new URL(pathPublicaciones);
                conexion = (HttpURLConnection) url.openConnection();
                conexion.setRequestMethod("GET");
                conexion.setRequestProperty("Accept", "application/json");
                if (conexion.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    JSONArray array = new JSONArray(readResponse(conexion));
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        Publicacion p = new Publicacion(
                                obj.optInt("id_usuario"),
                                obj.optString("nombre_usuario"),
                                obj.optString("contenido"));
                        p.setIdPublicacion(obj.optInt("id_publicacion"));
                        listaPublicaciones.add(p);
                    }
                    new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                        callback.onResult(true, listaPublicaciones);
                    });
                } else {
                    new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                        callback.onResult(false, null);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                    callback.onResult(false, null);
                });
            } finally {
                if (conexion != null) {
                    conexion.disconnect();
                }
            }
        }).start();
    }

    public void anadirPublicacion(int idUsuario, String nombreUsuario, String contenido, ApiCallback callback) {
        new Thread(() -> {
            HttpURLConnection con = null;
            try {
                // Quitamos la barra final si existe para evitar problemas de redirección
                String urlString = pathPublicaciones;
                if (urlString.endsWith("/")) {
                    urlString = urlString.substring(0, urlString.length() - 1);
                }
                URL url = new URL(urlString);
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");
                con.setDoOutput(true);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id_usuario", idUsuario);
                jsonObject.put("nombre_usuario", nombreUsuario);
                jsonObject.put("contenido", contenido);

                Log.d("APIREST", "Enviando JSON: " + jsonObject.toString());

                try (OutputStream os = con.getOutputStream()) {
                    byte[] input = jsonObject.toString().getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                int code = con.getResponseCode();
                Log.d("APIREST", "Respuesta POST Publicaciones: " + code);

                // Si hay error, leemos el stream de error para debug
                if (code >= 400) {
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getErrorStream(), StandardCharsets.UTF_8))) {
                        StringBuilder response = new StringBuilder();
                        String responseLine;
                        while ((responseLine = br.readLine()) != null) {
                            response.append(responseLine.trim());
                        }
                        Log.e("APIREST", "Error de la API: " + response.toString());
                    } catch (Exception e) {
                        Log.e("APIREST", "No se pudo leer el stream de error");
                    }
                }

                boolean success = (code >= 200 && code < 300);

                new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                    callback.onResult(success);
                });

            } catch (Exception e) {
                Log.e("APIREST", "Excepción al añadir publicación", e);
                new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                    callback.onResult(false);
                });
            } finally {
                if (con != null) {
                    con.disconnect();
                }
            }
        }).start();
    }
}