package com.nadia.inmobiliariatp.ui.inmueble;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultCallerLauncher;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nadia.inmobiliariatp.R;
import com.nadia.inmobiliariatp.databinding.FragmentCrearInmuebleBinding;
import com.nadia.inmobiliariatp.databinding.FragmentInmueblesBinding;

public class CrearInmuebleFragment extends Fragment {
    private @NonNull FragmentCrearInmuebleBinding binding;
    private CrearInmuebleViewModel mViewModel;
    private Intent intent;
    private ActivityResultLauncher<Intent> arl;

    public static CrearInmuebleFragment newInstance() {
        return new CrearInmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentCrearInmuebleBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    private void abrirGaleria() {
        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        arl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult resultado) {
                mViewModel.recibirFoto(resultado);
            }

        });

    }


    private void cargarInmueble(){
        String direccion= binding.etDireccion.getText().toString();
        String uso =binding.etUso.getText().toString();
        String tipo=binding.etTipo.getText().toString();
        double longitud =0;
        double latitud=0;
        String valor = binding.etValor.getText().toString();
        String ambientes= binding.etAmbientes.getText().toString();
        boolean disponible=binding.ckDisponible.isChecked();
        int superficie= 0;
        mViewModel.guardarInmueble(direccion, uso, tipo, valor, ambientes, disponible, superficie);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CrearInmuebleViewModel.class);
        // TODO: Use the ViewModel
        abrirGaleria();
        binding.btFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arl.launch(intent);
            }
        });
        binding.btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarInmueble();
            }
        });
        mViewModel.getUriMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                binding.imgInmueble.setImageURI(uri);
            }
        });
    }

}
