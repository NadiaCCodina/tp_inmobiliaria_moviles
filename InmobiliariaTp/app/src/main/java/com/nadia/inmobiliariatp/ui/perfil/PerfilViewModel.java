package com.nadia.inmobiliariatp.ui.perfil;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nadia.inmobiliariatp.models.Propietario;
import com.nadia.inmobiliariatp.request.ApiClient;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {
    private MutableLiveData<Boolean> mEstado = new MutableLiveData<>();
    private MutableLiveData<String> mNombre = new MutableLiveData<>();
    private Context context;
    private MutableLiveData<Propietario> mPropietario = new MutableLiveData<>();

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Boolean> getmEstado() {
        return mEstado;
    }

    public LiveData<String> getmNombre() {
        return mNombre;
    }

    public LiveData<Propietario> getmPropietario() {
        return mPropietario;
    }

    public void datosPropietario() {
        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmoServicio api=ApiClient.getInmoServicio();
        Call<Propietario> call=api.getPropietario("Bearer "+token);
    call.enqueue(new Callback<Propietario>() {
        @Override
        public void onResponse(Call<Propietario> call, Response<Propietario> response) {
            if(response.isSuccessful()){
               mPropietario.postValue(response.body());
            }else{
                Toast.makeText(getApplication(), response.toString(), Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFailure(Call<Propietario> call, Throwable throwable) {
            Toast.makeText(getApplication(), throwable.getMessage(), Toast.LENGTH_LONG).show();
        }
    });

    }

    public void cambioBoton(String nombreBoton, String nombre, String apellido, String email, String telefono, String dni) {
        if (nombreBoton.equalsIgnoreCase("editar")) {
            mEstado.setValue(true);
            mNombre.setValue("GUARGAR");
        } else {
            mEstado.setValue(false);
            mNombre.setValue("EDITAR");
            if (!validarCampos(nombre, apellido, email, telefono, dni)) {
                return;
            }
            Propietario propietarioModel = new Propietario();
            propietarioModel.setApellido(apellido);
            propietarioModel.setDni(dni);
            propietarioModel.setNombre(nombre);
            propietarioModel.setEmail(email);
            propietarioModel.setTelefono(telefono);
            propietarioModel.setIdPropietario(mPropietario.getValue().getIdPropietario());
            String token = ApiClient.leerToken(context);
            ApiClient.InmoServicio api = ApiClient.getInmoServicio();
            Call<Propietario> call= api.ActualizarPropietario("Bearer "+token, propietarioModel);
            call.enqueue(new Callback<Propietario>() {
                @Override
                public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(getApplication(), "Se guardaron los cambios", Toast.LENGTH_LONG).show();
                        Log.d("Respuesta",response.toString());
                        Log.d("Respuesta", token);
                    }
                }

                @Override
                public void onFailure(Call<Propietario> call, Throwable throwable) {
                    Toast.makeText(getApplication(), "Error al guardar los cambios", Toast.LENGTH_LONG).show();
                }
            });


        }

    }
    private boolean validarCampos(String nombre, String apellido, String email, String telefono, String dni) {
        if (nombre == null || nombre.trim().isEmpty()) {
            Toast.makeText(getApplication(), "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (apellido == null || apellido.trim().isEmpty()) {
            Toast.makeText(getApplication(), "El apellido no puede estar vacío", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (dni == null || dni.trim().isEmpty() || !dni.matches("\\d+")) {
            Toast.makeText(getApplication(), "Ingrese un DNI válido", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (telefono == null || telefono.trim().isEmpty() || !telefono.matches("\\d{7,15}")) {
            Toast.makeText(getApplication(), "Ingrese un teléfono válido", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (email == null || email.trim().isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getApplication(), "Ingrese un email válido", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true; // todo ok
    }
}