package com.nadia.inmobiliariatp.ui.inquilinos;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nadia.inmobiliariatp.models.Inquilino;
import com.nadia.inmobiliariatp.request.ApiClient;

public class InquilinoViewModel extends AndroidViewModel {
    private MutableLiveData<Inquilino> inquilino = new MutableLiveData<>();

    public InquilinoViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Inquilino> getInquilino() {

        return inquilino;
    }

    public void setInquilino(Inquilino inq) {
        inquilino.setValue(inq);
    }

}