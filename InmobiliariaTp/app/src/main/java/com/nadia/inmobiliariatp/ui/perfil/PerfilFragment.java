package com.nadia.inmobiliariatp.ui.perfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.nadia.inmobiliariatp.databinding.FragmentPerfilBinding;
import com.nadia.inmobiliariatp.models.Propietario;


public class PerfilFragment extends Fragment {

    private PerfilViewModel mViewModel;
    private FragmentPerfilBinding binding;

    public static PerfilFragment newInstance() {
        return new PerfilFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,  ViewGroup container,
                              Bundle savedInstanceState) {

        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(PerfilViewModel.class);
        View root = binding.getRoot();
        mViewModel.getmEstado().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.etNombre.setEnabled(aBoolean);
                binding.etApellido.setEnabled(aBoolean);
                binding.etDni.setEnabled(aBoolean);
                binding.etEmail.setEnabled(aBoolean);
                binding.etTelefono.setEnabled(aBoolean);

            }
        });
        mViewModel.getmNombre().observe((getViewLifecycleOwner()), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.btPerfil.setText(s);

            }
        });
        mViewModel.getmPropietario().observe((getViewLifecycleOwner()), new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
              binding.etNombre.setText(propietario.getNombre());
              binding.etEmail.setText(propietario.getEmail());
              binding.etDni.setText(propietario.getDni());
              binding.etApellido.setText(propietario.getApellido());
              binding.etTelefono.setText(propietario.getTelefono());

            }
        });
        binding.btPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mViewModel.cambioBoton(binding.btPerfil.getText().toString(),
                        binding.etNombre.getText().toString(),
                        binding.etApellido.getText().toString(),
                        binding.etEmail.getText().toString(),
                        binding.etTelefono.getText().toString(),
                        binding.etDni.getText().toString());
            }
        });
        mViewModel.datosPropietario();
        return root;
    }



}