package com.example.proyecto.Fragmentos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto.Modelos.Usuario;
import com.example.proyecto.R;

public class Muro extends Fragment {

    private Usuario usuario;
    private Toolbar tb;
    private ActionBar ab;
    private RecyclerView rv;

    public Muro() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            usuario = (Usuario) getArguments().getSerializable("usuario");
        }
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_muro, container, false);

        tb = view.findViewById(R.id.tbMuro);
        rv = view.findViewById(R.id.recyclerView);

        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.setSupportActionBar(tb);
        ab = activity.getSupportActionBar();

        activity.getWindow().setNavigationBarColor(
                activity.getColor(R.color.Azul_Logo)
        );
        activity.getWindow().setStatusBarColor(
                activity.getColor(R.color.Azul_Logo)
        );

        return view;
    }
}
