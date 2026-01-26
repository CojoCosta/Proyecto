import android.util.Log;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class APIREST {
    public void añadirUsuario(String nombre, String apellidos, String nombreUsuario, String email, String password, String fecha){
        new Thread(()-> {
            try {
                //cambiar ip por 10.0.2.2 si no funciona en el emulador
                URL url = new URL("http://192.130.0.13:8080/tema5maven/rest/deportista/android");
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
