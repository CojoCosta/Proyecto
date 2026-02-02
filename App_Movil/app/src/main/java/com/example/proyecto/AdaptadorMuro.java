package com.example.proyecto;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorMuro extends RecyclerView.Adapter<AdaptadorMuro.MyViewHolder> {
    ArrayList<Publicacion> publicacion;
    public AdaptadorMuro (ArrayList<Publicacion> publicacion){
        this.publicacion = publicacion;
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
        holder.getIdUsuario().setText(pu.getIdUsuario()+ "");//VEr esto
        holder.getFotoPublicacion().setImageResource(pu.getFotoPublicacion());
        holder.getNumLikes().setText(pu.getNumLikes()+ "");
    }

    @Override
    public int getItemCount() {
        return this.publicacion.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nombreUsuario, numLikes;
        ImageButton ibLike;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.fotoPublicacion = itemView.findViewById(R.id.fotoPublicacion);
            this.nombreUsuario = itemView.findViewById(R.id.txtUsuarioCelda);
            this.numLikes = itemView.findViewById(R.id.txtNumLikesCelda);
            this.ibLike = itemView.findViewById(R.id.ibLikeCelda);
        }
        public int getFotoPublicacion(){
            return fotoPublicacion;
        }
        public TextView getNombreUsuario() {
            return nombreUsuario;
        }
        public TextView getNumLikes() {
            return numLikes;
        }
    }
}
