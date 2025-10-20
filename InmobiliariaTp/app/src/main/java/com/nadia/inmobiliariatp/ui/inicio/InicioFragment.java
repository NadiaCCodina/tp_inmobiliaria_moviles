package com.nadia.inmobiliariatp.ui.inicio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.SupportMapFragment;
import com.nadia.inmobiliariatp.R;
import com.nadia.inmobiliariatp.databinding.FragmentInicioBinding;


public class InicioFragment extends Fragment {
private InicioViewModel vm;
    private FragmentInicioBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

            vm=    new ViewModelProvider(this).get(InicioViewModel.class);

        binding = FragmentInicioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        vm.getMutableMapaActual().observe(getViewLifecycleOwner(), new Observer<InicioViewModel.MapaActual>() {
    @Override
    public void onChanged(InicioViewModel.MapaActual mapaActual) {
        ((SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map)).getMapAsync(mapaActual);
    }
});
        vm.obtenerMapa();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}