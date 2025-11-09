package com.nadia.inmobiliariatp.ui.inquilinos;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nadia.inmobiliariatp.R;
import com.nadia.inmobiliariatp.databinding.FragmentDetalleInquilinoBinding;
import com.nadia.inmobiliariatp.models.Inquilino;

public class InquilinoFragment extends Fragment {

    private InquilinoViewModel mViewModel;
    private FragmentDetalleInquilinoBinding binding;
    public static InquilinoFragment newInstance() {
        return new InquilinoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDetalleInquilinoBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(InquilinoViewModel.class);

        if (getArguments() != null) {
            Inquilino inquilino = (Inquilino) getArguments().getSerializable("inquilino");
            mViewModel.setInquilino(inquilino);
        }
        // 🔹 Observa los datos del inquilino
        mViewModel.getInquilino().observe(getViewLifecycleOwner(), inquilino -> {
            if (inquilino != null) {
                binding.etNombre.setText(inquilino.getNombre());
                binding.etApellido.setText(inquilino.getApellido());
                binding.etDni.setText(String.valueOf(inquilino.getDni()));
                binding.etTelefono.setText(inquilino.getTelefono());
                binding.etEmail.setText(inquilino.getEmail());
            }
        });


        return binding.getRoot();
    }



}