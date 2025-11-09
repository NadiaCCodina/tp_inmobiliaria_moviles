package com.nadia.inmobiliariatp.ui.contratos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nadia.inmobiliariatp.R;
import com.nadia.inmobiliariatp.databinding.FragmentDetalleContratoBinding;
import com.nadia.inmobiliariatp.models.Contrato;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetalleContratoFragment extends Fragment {
    private Context context;
    private DetalleContratoViewModel mViewModel;
    private FragmentDetalleContratoBinding binding;
    private Contrato contrato;

    public static DetalleContratoFragment newInstance() {
        return new DetalleContratoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

       mViewModel =new  ViewModelProvider(this).get(DetalleContratoViewModel.class);
       binding = FragmentDetalleContratoBinding.inflate(inflater, container, false);
       View view= binding.getRoot();
       mViewModel.getContrato().observe(getViewLifecycleOwner(), new Observer<Contrato>() {
           @Override
           public void onChanged(Contrato contrato) {
               String fechaInicio = contrato.getFechaInicio();
               String fechafin = contrato.getFechaFinalizacion();
               String fechaInicioConvertida= convertirFecha(fechaInicio);
               String fechaFinConvertida=convertirFecha(fechafin);

               binding.tvId.setText(String.valueOf(contrato.getIdContrato()));
               binding.tvFecha.setText(String.valueOf(fechaInicioConvertida));
               binding.tvFechafin.setText(String.valueOf(fechaFinConvertida));
               binding.tvInmueble.setText(String.valueOf(contrato.getInmueble().getDireccion()));
               binding.tvMonto.setText(String.valueOf(contrato.getInmueble().getValor()));
               binding.tvInquilino.setText(String.valueOf(contrato.getInquilino().getApellido()));


               binding.btInquilino.setOnClickListener(v -> {
                           Bundle bundle = new Bundle();
                           bundle.putSerializable("inquilino", contrato.getInquilino());
                           Navigation.findNavController(v)
                                   .navigate(R.id.action_detalleContratoFragment_to_fragment_inquilino, bundle);
                       });
               binding.btPagos.setOnClickListener( v->{
                           Bundle bundle = new Bundle();
                           bundle.putInt("idContrato", contrato.getIdContrato());
                           Navigation.findNavController(v)
                                   .navigate(R.id.action_detalleContratoFragment_to_pagosFragment, bundle);
                       });

           }
       });
        int idInmueble = getArguments().getInt("idInmueble", -1);
        if (idInmueble != -1) {
            mViewModel.cargarContratoPorInmueble(idInmueble);
        }

        return view;
    }
private String convertirFecha(String fechaString){
    String fechaConvertida = null;

    try {

        SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoSalida = new SimpleDateFormat("dd/MM/yyyy");
        Date fecha = formatoEntrada.parse(fechaString);
        fechaConvertida = formatoSalida.format(fecha);

        System.out.println(fechaConvertida);
    } catch (ParseException e) {
        e.printStackTrace();
    }
    return  fechaConvertida;
}


}