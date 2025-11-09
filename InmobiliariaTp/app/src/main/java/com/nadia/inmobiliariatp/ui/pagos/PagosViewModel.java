package com.nadia.inmobiliariatp.ui.pagos;

import static androidx.lifecycle.AndroidViewModel_androidKt.getApplication;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nadia.inmobiliariatp.models.Inmueble;
import com.nadia.inmobiliariatp.models.Pago;
import com.nadia.inmobiliariatp.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagosViewModel extends AndroidViewModel {
    private MutableLiveData<List<Pago>> listaPagos= new MutableLiveData<>();
    private Context context;
    private MutableLiveData<Pago> mPago;
    int idContrato;

    public PagosViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Pago> getmPago() {
        if(mPago==null){
            mPago=new MutableLiveData<>();
        }
        return mPago;
    }

    public LiveData<List<Pago>> getPagos() {
        return listaPagos;
    }
    public void recuperarPago(Bundle bundle){
        if (bundle != null && bundle.containsKey("idContrato")) {
            idContrato = bundle.getInt("idContrato"); // directamente getInt

        }
    }
    public void obtenerPagosPorInmueble() {
        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmoServicio api = ApiClient.getInmoServicio();
        Call<List<Pago>> call = api.getPagos("Bearer " + token, idContrato);


        call.enqueue(new Callback<List<Pago>>() {
            @Override
            public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
                if (response.isSuccessful()) {
                    listaPagos.postValue(response.body());
                } else {
                    Toast.makeText(getApplication(), "No se obtuvieron Pagos", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Pago>> call, Throwable throwable) {
                Log.d("errorInmueble", throwable.getMessage());

                Toast.makeText(getApplication(), "Error al obtener Inmuebles", Toast.LENGTH_LONG).show();
            }
        } );
    }
}