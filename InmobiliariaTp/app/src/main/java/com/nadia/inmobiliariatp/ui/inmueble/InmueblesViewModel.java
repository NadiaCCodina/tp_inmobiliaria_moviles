package com.nadia.inmobiliariatp.ui.inmueble;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nadia.inmobiliariatp.models.Inmueble;
import com.nadia.inmobiliariatp.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmueblesViewModel extends AndroidViewModel {
    private MutableLiveData<Inmueble> mInmueble;
    private MutableLiveData<List<Inmueble>> listaInmuebles = new MutableLiveData<>();
    public InmueblesViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Inmueble> getMInmueble(){
        if (mInmueble == null){
            mInmueble = new MutableLiveData<>();
        }
        return mInmueble;
    }

    public MutableLiveData<List<Inmueble>> getListaInmuebles() {
        return listaInmuebles;
    }

    public void recuperarInmueble(Bundle bundle){
        Inmueble inmueble = (Inmueble) bundle.get("inmuebleBundle");
        if (inmueble!= null){
            mInmueble.setValue(inmueble);
        }
    }
    public void obtenerListaInmuebles(){
        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmoServicio api = ApiClient.getInmoServicio();
        Call <List<Inmueble>> call = api.getInmuebles("Bearer "+ token);

        call.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if (response.isSuccessful()){
                    listaInmuebles.postValue(response.body());
                }else {
                    Toast.makeText(getApplication(),"no se obtuvieron Inmuebles",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable throwable) {
                Log.d("errorInmueble",throwable.getMessage());

                Toast.makeText(getApplication(),"Error al obtener Inmuebles",Toast.LENGTH_LONG).show();
            }
        });
    }



    }

