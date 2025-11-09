package com.nadia.inmobiliariatp.ui.pagos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nadia.inmobiliariatp.R;
import com.nadia.inmobiliariatp.databinding.FragmentPagosBinding;

import com.nadia.inmobiliariatp.models.Pago;
import com.nadia.inmobiliariatp.ui.contratos.ContratoAdapter;

import java.util.List;

public class PagosFragment extends Fragment {

    private PagosViewModel pagosViewModel;
    private FragmentPagosBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentPagosBinding.inflate(inflater, container, false);
        pagosViewModel = new ViewModelProvider(this).get(PagosViewModel.class);

        View root = binding.getRoot();


        pagosViewModel.getPagos().observe(getViewLifecycleOwner(), new Observer<List<Pago>>() {
            @Override
            public void onChanged(List<Pago> pagos) {
                PagosAdapter adapter = new PagosAdapter(getContext(), pagos, getLayoutInflater());
                GridLayoutManager glm = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
                binding.fragmentPagos.setLayoutManager(glm);
                binding.fragmentPagos.setAdapter(adapter);
            }
        });
        pagosViewModel.recuperarPago(getArguments());
        pagosViewModel.obtenerPagosPorInmueble();
        return root;
    }
}