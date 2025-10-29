package com.nadia.inmobiliariatp.ui.inmueble;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nadia.inmobiliariatp.models.Inmueble;
import com.nadia.inmobiliariatp.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetalleInmuebleViewModel extends AndroidViewModel {
    private MutableLiveData<Inmueble> mInmueble;
    public DetalleInmuebleViewModel(Application application) {
        super(application);
    }
    public LiveData<Inmueble> getMInmueble(){
        if (mInmueble == null){
            mInmueble = new MutableLiveData<>();
        }
        return mInmueble;
    }
    public void recuperarInmueble(Bundle bundle){
        Inmueble inmueble = (Inmueble) bundle.get("inmuebleBundle");
        if (inmueble!= null){
            mInmueble.setValue(inmueble);
        }
    }
    public void actualizarInmueble(Inmueble inmueble){
        ApiClient.InmoServicio api = ApiClient.getInmoServicio()  ;
        String token = ApiClient.leerToken(getApplication());
        inmueble.setIdInmueble(mInmueble.getValue().getIdInmueble());
        Call<Inmueble> call = api.actualizarInmueble("Bearer "+token, inmueble);
        call.enqueue(new Callback<Inmueble>() {

            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getApplication(), "Inmueble actualizado correctamente" + response.code(), Toast.LENGTH_LONG).show();
                }else{
                    Log.d("InmuebleVM", "error en la respuesta: " + response.code());
                    Toast.makeText(getApplication(), "No se pudo actulizar el inmueble" + response.code(), Toast.LENGTH_LONG).show();
                }
            }


            public void onFailure(Call<Inmueble> call, Throwable t) {
                Log.d("errorActualizar", "Error: " + t.getMessage());
            }
        });
    }

}