package com.nadia.inmobiliariatp.ui.inicio;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class InicioViewModel extends AndroidViewModel {

    private MutableLiveData<MapaActual> mutableMapaActual;
    public InicioViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<MapaActual> getMutableMapaActual(){
        if(mutableMapaActual==null){
            mutableMapaActual =new MutableLiveData<>();
        }
        return mutableMapaActual;
    };
    public void obtenerMapa(){
        MapaActual mapaActual = new MapaActual();
        mutableMapaActual.setValue(mapaActual);
    }
    public class MapaActual implements OnMapReadyCallback {
        LatLng SANLUIS = new LatLng(-33.280576, -66.332482);
        LatLng ULP = new LatLng(-33.150720, -66.306864);

        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            googleMap.addMarker(new MarkerOptions().position(SANLUIS).title("San Luis"));
            googleMap.addMarker(new MarkerOptions().position(ULP).title("Ulp"));
            CameraPosition cameraPosition =
                    new CameraPosition.Builder().target(ULP).zoom(13).bearing(45).build();
            CameraUpdate cameraUpdate= CameraUpdateFactory.newCameraPosition(cameraPosition);
            googleMap.animateCamera(cameraUpdate);
        }
    }

}