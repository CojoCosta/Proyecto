package com.example.proyecto;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto.Modelos.Publicacion;
import com.example.proyecto.Modelos.Usuario;

import java.util.ArrayList;

public class AdaptadorMuro extends RecyclerView.Adapter<AdaptadorMuro.MyViewHolder> {
    ArrayList<Publicacion> publicaciones;
    ArrayList<Usuario> usuarios;
    Usuario usuarioLogueado;

    public AdaptadorMuro (ArrayList<Publicacion> publicaciones, ArrayList<Usuario> usuarios, Usuario usuarioLogueado){
        this.publicaciones = publicaciones;
        this.usuarios = usuarios;
        this.usuarioLogueado = usuarioLogueado;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View elemento = LayoutInflater.from(parent.getContext()).inflate(R.layout.celda_muro, parent, false);
        return new MyViewHolder(elemento);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Publicacion pu = this.publicaciones.get(position);
        
        // Mostrar el contenido de la publicación
        holder.getContenido().setText(pu.getContenido());
        
        // Mostrar el nombre de usuario (preferiblemente el que viene en la publicación si existe)
        if (pu.getNombreUsuario() != null && !pu.getNombreUsuario().isEmpty()) {
            holder.getNombreUsuario().setText(pu.getNombreUsuario());
        } else {
            // Si no, buscarlo en la lista de usuarios por ID
            boolean encontrado = false;
            for (Usuario u : usuarios) {
                if (pu.getIdUsuario() == u.getId_usuario()){
                    holder.getNombreUsuario().setText(u.getNombreUsuario());
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                holder.getNombreUsuario().setText("Usuario #" + pu.getIdUsuario());
            }
        }
    }

    @Override
    public int getItemCount() {
        return this.publicaciones != null ? this.publicaciones.size() : 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView nombreUsuario;
        private final TextView contenido;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.nombreUsuario = itemView.findViewById(R.id.txtUsuarioCelda);
            this.contenido = itemView.findViewById(R.id.txtContenido);
        }

        public TextView getNombreUsuario() {
            return nombreUsuario;
        }

        public TextView getContenido() {
            return contenido;
        }
    }
}