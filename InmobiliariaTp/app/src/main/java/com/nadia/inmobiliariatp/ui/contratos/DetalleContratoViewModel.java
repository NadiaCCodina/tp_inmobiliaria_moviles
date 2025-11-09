package com.nadia.inmobiliariatp.ui.contratos;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nadia.inmobiliariatp.models.Contrato;
import com.nadia.inmobiliariatp.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleContratoViewModel extends AndroidViewModel {
    private MutableLiveData<Contrato> mContrato;

    public DetalleContratoViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Contrato> getContrato(){
        if (mContrato == null){
            mContrato = new MutableLiveData<>();
        }
        return mContrato;
    }
    public void recuperarContrato(Bundle bundle){
        Contrato contrato = (Contrato) bundle.get("contratoBundle");
        if (contrato!= null){
            mContrato.setValue(contrato);
        }
    }
    public void cargarContratoPorInmueble(int idInmueble) {
        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmoServicio api = ApiClient.getInmoServicio();
        Call<Contrato> call =  api.getContrato("Bearer " + token, idInmueble);
        call.enqueue(new Callback<Contrato>() {
            @Override
            public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                if (response.isSuccessful()) {
                    mContrato.postValue(response.body());
                } else {
                    Toast.makeText(getApplication(), "No se obtuvieron Contrato", Toast.LENGTH_LONG).show();
                }
            }



            @Override
            public void onFailure(Call<Contrato> call, Throwable t) {
                Log.e("ContratoVM", "Error: " + t.getMessage());
            }
        });
    }
}