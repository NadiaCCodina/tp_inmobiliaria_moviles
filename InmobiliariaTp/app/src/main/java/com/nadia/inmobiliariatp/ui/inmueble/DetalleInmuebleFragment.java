package com.nadia.inmobiliariatp.ui.inmueble;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.nadia.inmobiliariatp.R;
import com.nadia.inmobiliariatp.databinding.FragmentDetalleInmuebleBinding;
import com.nadia.inmobiliariatp.databinding.FragmentInmueblesBinding;
import com.nadia.inmobiliariatp.models.Inmueble;
import com.nadia.inmobiliariatp.request.ApiClient;

public class DetalleInmuebleFragment extends Fragment {

    private DetalleInmuebleViewModel mv;
    private FragmentDetalleInmuebleBinding binding;
    private Inmueble inmueble;

    public static DetalleInmuebleFragment newInstance() {
        return new DetalleInmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inmueble = new Inmueble();
        mv = new ViewModelProvider(this).get(DetalleInmuebleViewModel.class);
        binding = FragmentDetalleInmuebleBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        mv.getMInmueble().observe(getViewLifecycleOwner(), new Observer<Inmueble>() {
            @Override
            public void onChanged(Inmueble inmueble) {
                binding.etCodigo.setText(String.valueOf(inmueble.getIdInmueble()));
                binding.etDireccion.setText(inmueble.getDireccion());
                binding.etUso.setText(inmueble.getUso());
                binding.etAmbientes.setText(String.valueOf(inmueble.getAmbientes()));
                binding.etPrecio.setText(String.valueOf(inmueble.getValor()));
                binding.etTipo.setText(inmueble.getTipo());
                binding.cbDisponible.setChecked(inmueble.isDisponible());
                Glide.with(getContext())
                        .load(ApiClient.URL_BASE
                                + inmueble.getImagen())
                        .placeholder(null)
                        .error("null")
                        .into(binding.ivFoto);
            }
        });

        binding.btEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inmueble.setDisponible(binding.cbDisponible.isChecked());
                mv.actualizarInmueble(inmueble);
            }
        });

        mv.recuperarInmueble(getArguments());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mv = new ViewModelProvider(this).get(DetalleInmuebleViewModel.class);
        // TODO: Use the ViewModel
    }

}