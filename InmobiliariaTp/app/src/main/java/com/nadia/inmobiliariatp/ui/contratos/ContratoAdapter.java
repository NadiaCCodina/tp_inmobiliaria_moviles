package com.nadia.inmobiliariatp.ui.contratos;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nadia.inmobiliariatp.R;
import com.nadia.inmobiliariatp.models.Inmueble;
import com.nadia.inmobiliariatp.request.ApiClient;
;

import java.util.List;

public class ContratoAdapter extends RecyclerView.Adapter<ContratoAdapter.ViewHolderContrato> {

private  List<Inmueble>listaInmuebles;
    private Context context;
    private LayoutInflater inflater;

    public ContratoAdapter(List<Inmueble> listaInmuebles, Context context, LayoutInflater inflater) {
        this.listaInmuebles = listaInmuebles;
        this.context = context;
        this.inflater = inflater;
    }
    @NonNull
    @Override
    public ContratoAdapter.ViewHolderContrato onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=inflater.inflate(R.layout.item_inmueble2,parent,false);
        return new ContratoAdapter.ViewHolderContrato(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderContrato holder, int position) {

            Inmueble inmActual = listaInmuebles.get(position);
            holder.direccion.setText(inmActual.getDireccion());

            Glide.with(context)
                    .load(ApiClient.URL_BASE + inmActual.getImagen())
                    .placeholder(null)
                    .error("null")
                    .into(holder.portada);
        holder.boton.setOnClickListener(v -> {
                    Bundle bundle = new Bundle();
                    bundle.putInt("idInmueble", inmActual.getIdInmueble());
                    Navigation.findNavController((Activity)context, R.id.nav_host_fragment_content_main).navigate(R.id.detalleContratoFragment, bundle);

                }
            );
        }


    @Override
    public int getItemCount() {
        return listaInmuebles.size();
    }

    public class ViewHolderContrato extends RecyclerView.ViewHolder  {

        private TextView direccion, precio;
        private ImageView portada;
        private  Button boton;
        public ViewHolderContrato(@NonNull View itemView) {
            super(itemView);
            direccion = itemView.findViewById(R.id.tvInmueble);
            precio = itemView.findViewById(R.id.tvValor);
            portada= itemView.findViewById(R.id.imgPortada);
            boton = itemView.findViewById(R.id.btnContrato); //
        }


    }
}
