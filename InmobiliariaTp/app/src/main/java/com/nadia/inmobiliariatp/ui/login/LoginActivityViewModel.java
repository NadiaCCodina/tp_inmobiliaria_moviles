package com.nadia.inmobiliariatp.ui.login;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nadia.inmobiliariatp.MainActivity;
import com.nadia.inmobiliariatp.models.Usuario;
import com.nadia.inmobiliariatp.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivityViewModel extends AndroidViewModel {
    private  MutableLiveData<String> mMensaje;
    private MutableLiveData<Boolean> sesionCorrecta;
    private SensorManager gestorSensores;
    private List<Sensor> listaSensores;
    DetectorMovimiento detectorMovimiento;





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
        });}
    public void iniciarDeteccionMovimiento() {
        gestorSensores = (SensorManager) getApplication().getSystemService(Context.SENSOR_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            listaSensores = gestorSensores.getSensorList(Sensor.TYPE_ACCELEROMETER);
        }

        if (listaSensores.size() > 0) {
            detectorMovimiento = new DetectorMovimiento();
            gestorSensores.registerListener(detectorMovimiento, listaSensores.get(0), SensorManager.SENSOR_DELAY_UI);
        }
    }

    public void detenerDeteccionMovimiento() {
        if (gestorSensores != null && detectorMovimiento != null) {
            gestorSensores.unregisterListener(detectorMovimiento);
        }
    }

    private class DetectorMovimiento implements SensorEventListener {

        private float aceleracionActual = SensorManager.GRAVITY_EARTH;
        private float ultimaAceleracion = SensorManager.GRAVITY_EARTH;
            private final float UMBRAL_SACUDIDA = 12.0f;
            private boolean estaSacudiendo = false;

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // No se utiliza
            }

            @Override
            public void onSensorChanged(SensorEvent evento) {
                if (evento.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    float ejeX = evento.values[0];
                    float ejeY = evento.values[1];
                    float ejeZ = evento.values[2];

                    ultimaAceleracion = aceleracionActual;
                    aceleracionActual = (float) Math.sqrt((ejeX * ejeX + ejeY * ejeY + ejeZ * ejeZ));
                    float variacion = aceleracionActual - ultimaAceleracion;

                    if (variacion > UMBRAL_SACUDIDA && !estaSacudiendo) {
                        estaSacudiendo = true;
                        realizarLlamadaInmobiliaria();
                    }
                }
            }

            private void realizarLlamadaInmobiliaria() {
                Intent intentoLlamada = new Intent(Intent.ACTION_CALL);
                intentoLlamada.setData(Uri.parse("tel:1132675403"));
                intentoLlamada.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                if (ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplication(), "No hay permiso para llamadas", Toast.LENGTH_SHORT).show();
                    estaSacudiendo = false;
                    return;
                }

                getApplication().startActivity(intentoLlamada);
                Toast.makeText(getApplication(), "Llamando a la inmobiliaria...", Toast.LENGTH_SHORT).show();

                // Evita múltiples llamadas seguidas
                // new android.os.Handler().postDelayed(() -> estaSacudiendo = false, 3000);
            }
        }



        }
