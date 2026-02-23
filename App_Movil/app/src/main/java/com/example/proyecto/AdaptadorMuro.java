package com.example.proyecto;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto.Modelos.Publicacion;
import com.example.proyecto.Modelos.Usuario;

import java.util.ArrayList;

public class AdaptadorMuro extends RecyclerView.Adapter<AdaptadorMuro.MyViewHolder> {
    ArrayList<Publicacion> publicacion;
    ArrayList<Usuario> usuarios;
    Usuario usuario;
    public AdaptadorMuro (ArrayList<Publicacion> publicacion, ArrayList<Usuario> usuarios, Usuario usuario){
        this.publicacion = publicacion;
        this.usuarios = usuarios;
        this.usuario = usuario;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View elemento= LayoutInflater.from(parent.getContext()).inflate(R.layout.celda_muro, parent, false);
        MyViewHolder mvh = new MyViewHolder(elemento);
        return  mvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Publicacion pu = this.publicacion.get(position);
        for (Usuario u : usuarios) {
            if (pu.getIdUsuario()-1 == u.getId_usuario()){
                holder.getNombreUsuario().setText(u.getNombreUsuario());
            }
        }
    }

    @Override
    public int getItemCount() {
        return this.publicacion.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nombreUsuario, numLikes;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.nombreUsuario = itemView.findViewById(R.id.txtUsuarioCelda);
            this.numLikes = itemView.findViewById(R.id.txtNumLikesCelda);
        }
        public TextView getNombreUsuario() {
            return nombreUsuario;
        }
        public TextView getNumLikes() {
            return numLikes;
        }


    }
}
