package com.nadia.inmobiliariatp.ui.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.nadia.inmobiliariatp.MainActivity;
import com.nadia.inmobiliariatp.databinding.LoginBinding;

public class LoginActivity extends AppCompatActivity {
    private LoginActivityViewModel viewModel;
    private static final int REQUEST_CALL_PERMISSION = 100;

    private LoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =LoginBinding.inflate(getLayoutInflater());
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginActivityViewModel.class);
        setContentView(binding.getRoot());
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    REQUEST_CALL_PERMISSION
            );
        }
        binding.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = binding.etEmail.getText().toString();
                String clave = binding.etPassword.getText().toString();
                viewModel.validarUsuario(user, clave);

            }
        });
        viewModel.getmMensaje().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(LoginActivity.this, s, Toast.LENGTH_LONG).show();
            }
        });
        viewModel.getsesionCorrecta().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Toast.makeText(LoginActivity.this, "entro", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        viewModel.iniciarDeteccionMovimiento();
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModel.detenerDeteccionMovimiento();
    }

}