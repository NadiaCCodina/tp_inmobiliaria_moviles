package com.nadia.inmobiliariatp.ui.login;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nadia.inmobiliariatp.MainActivity;
import com.nadia.inmobiliariatp.models.Usuario;
import com.nadia.inmobiliariatp.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivityViewModel extends AndroidViewModel {
    private  MutableLiveData<String> mMensaje;
    private MutableLiveData<Boolean> sesionCorrecta;
    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<String> getmMensaje() {
        if (mMensaje == null) {
            mMensaje = new MutableLiveData<>();

        }
        return mMensaje;

    }
    public LiveData<Boolean> getsesionCorrecta() {

        if (sesionCorrecta == null) {
            sesionCorrecta = new MutableLiveData<>();

        }
        return sesionCorrecta;

    }
    public void validarUsuario(String email, String password){
        if (email.isEmpty() || password.isEmpty()) {
            mMensaje.setValue("Todos los campos son obligatorios");

            return;
        }
       ApiClient.InmoServicio inmoServicio= ApiClient.getInmoServicio();
       Call<String> call = inmoServicio.loginForm(email, password);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    String token= response.body();
                    Log.d("token ",token);
                    ApiClient.guardarToken(getApplication(), token);
                    sesionCorrecta.setValue(true);

                }else{
                    Log.d("token error mensaje", response.message());
                    Log.d("token codigo", response.code()+"");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Log.d("ERROR VALIDAR USUARIO", throwable.getMessage());
               // sesionCorrecta.setValue(true);
            }
        });

//            if (email.equals("Fran") && password.equals("1234")) {
//                Usuario usuario = new Usuario();
//                usuario.setEmail(email);
//                usuario.setPassword(password);
//                usuario.setNombre("Martin");
//                //usuario.setFoto(R.drawable.fotouser);
//                Intent intent = new Intent(getApplication(), MainActivity.class);
//
//
//            } else {
//                mMensaje.setValue("Usuario ");
//            }


        }
        }