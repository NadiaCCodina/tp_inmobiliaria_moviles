package com.nadia.inmobiliariatp.ui.contratos;

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
import com.nadia.inmobiliariatp.databinding.FragmentContratosBinding;
import com.nadia.inmobiliariatp.databinding.FragmentInmueblesBinding;
import com.nadia.inmobiliariatp.models.Inmueble;
import com.nadia.inmobiliariatp.ui.inmueble.InmuebleAdapter;

import java.util.List;

public class ContratosFragment extends Fragment {
    private FragmentContratosBinding binding;

    private ContratosViewModel mViewModel;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel= new ViewModelProvider(this).get(ContratosViewModel.class);
        binding = FragmentContratosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mViewModel.getListaInmuebles().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
         @Override
         public void onChanged(List<Inmueble> inmuebles) {
             ContratoAdapter adapter = new ContratoAdapter(inmuebles,getContext(),getLayoutInflater());
             GridLayoutManager glm=new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
             binding.listaInmueblesContratos.setLayoutManager(glm);
             binding.listaInmueblesContratos.setAdapter(adapter);
         }
     });

        mViewModel.obtenerListaInmuebles();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}