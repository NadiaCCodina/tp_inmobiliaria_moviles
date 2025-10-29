package com.nadia.inmobiliariatp.ui.inmueble;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.nadia.inmobiliariatp.models.Inmueble;
import com.nadia.inmobiliariatp.request.ApiClient;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Array;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearInmuebleViewModel extends AndroidViewModel {
    private MutableLiveData<Uri> uriMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Inmueble> mInmueble = new MutableLiveData<>();
    private static Inmueble inmuebleNuevo;

    public CrearInmuebleViewModel(@NonNull Application application) {
        super(application);
        inmuebleNuevo = new Inmueble();
    }
    // TODO: Implement the ViewModel

    public MutableLiveData<Uri> getUriMutableLiveData() {
        return uriMutableLiveData;
    }

    public MutableLiveData<Inmueble> getmInmueble() {
        return mInmueble;
    }

    public static Inmueble getInmuebleNuevo() {
        return inmuebleNuevo;
    }

    public void recibirFoto(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();
            Uri uri = data.getData();
            Log.d("salada", uri.toString());
            uriMutableLiveData.setValue(uri);
        }
    }
//direccion, uso, tipo, valor, ambientes, disponible, superficie
    public void guardarInmueble(String direccion, String uso, String tipo, String precio, String ambientes, boolean disponible, int superficie) {
        try {
            double preciog = Double.parseDouble(precio);
            int ambientesg = Integer.parseInt(ambientes);
           // int superficieg = Integer.parseInt(superficie);
            Inmueble inmuebleGuardar = new Inmueble();
            inmuebleGuardar.setAmbientes(ambientesg);
            inmuebleGuardar.setDireccion(direccion);
            inmuebleGuardar.setDisponible(disponible);
            inmuebleGuardar.setUso(uso);
            inmuebleGuardar.setTipo(tipo);
            inmuebleGuardar.setValor(preciog);
            inmuebleGuardar.setSuperficie(superficie);
            //convertir la imagen en bits
            byte[] imagen = transformarImagen();
            //poner if para ver si vino vacia la imagen
            if (imagen.length == 0) {
                Toast.makeText(getApplication(), "Debe ingresar imagen", Toast.LENGTH_LONG).show();
                return;
            }
            String inmuebleJson = new Gson().toJson(inmuebleGuardar);
            RequestBody inmuebleBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), inmuebleJson);
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imagen);
            MultipartBody.Part imagenPart = MultipartBody.Part.createFormData("imagen", "imagen.jpg", requestFile);
            ApiClient.InmoServicio api=ApiClient.getInmoServicio();
            String token= ApiClient.leerToken(getApplication());
            Call<Inmueble>llamada= api.CargarInmueble("Bearer  "+token, imagenPart, inmuebleBody);

            llamada.enqueue(new Callback<Inmueble>() {
                @Override
                public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(getApplication(), "Inmueble guardado correctamente", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<Inmueble> call, Throwable throwable) {
                    Toast.makeText(getApplication(), "Error al guardar inmueble", Toast.LENGTH_SHORT).show();
                }
            });


        } catch (NumberFormatException ex) {
            Toast.makeText(getApplication(), "Debe ingresar numeros validos", Toast.LENGTH_LONG).show();
            Log.d("errorCargaInmueble",ex.getMessage());


        }


    }

    private byte[] transformarImagen() {
        try {
            Uri uri = uriMutableLiveData.getValue();
            InputStream inputStream = getApplication().getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (FileNotFoundException ex) {
            Toast.makeText(getApplication(), "Debe ingresar una foto", Toast.LENGTH_LONG).show();
            return new byte[]{};
        }

    }

}
