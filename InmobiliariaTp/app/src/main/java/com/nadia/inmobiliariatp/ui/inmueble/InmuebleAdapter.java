package com.nadia.inmobiliariatp.ui.inmueble;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nadia.inmobiliariatp.R;
import com.nadia.inmobiliariatp.models.Inmueble;
import com.nadia.inmobiliariatp.request.ApiClient;

import java.util.List;

public class InmuebleAdapter extends RecyclerView.Adapter<InmuebleAdapter.ViewHolderInmueble> {
    private List<Inmueble> listaInmuebles;
    private Context context;
    private LayoutInflater inflater;

    public InmuebleAdapter(List<Inmueble> listaInmuebles, Context context, LayoutInflater inflater) {
        this.listaInmuebles = listaInmuebles;
        this.context = context;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public ViewHolderInmueble onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=inflater.inflate(R.layout.item_inmueble,parent,false);
        return new ViewHolderInmueble(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderInmueble holder, int position) {
        Inmueble inmActual = listaInmuebles.get(position);
        holder.direccion.setText(inmActual.getDireccion());
        holder.precio.setText(inmActual.getValor()+"");
        Glide.with(context)
                .load(ApiClient.URL_BASE + inmActual.getImagen())
                .placeholder(null)
                .error("null")
                .into(holder.portada);
        ((ViewHolderInmueble) holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("inmuebleBundle", inmActual);
                Navigation.findNavController((Activity)context, R.id.nav_host_fragment_content_main).navigate(R.id.listaInmuebles, bundle);

            }
        });
    }

    @Override
    public int getItemCount() {
        return listaInmuebles.size();
    }

    public class ViewHolderInmueble extends RecyclerView.ViewHolder  {

        private TextView direccion, precio;
        private ImageView portada;
        public ViewHolderInmueble(@NonNull View itemView) {
            super(itemView);
            direccion = itemView.findViewById(R.id.tvDireccion);
            precio = itemView.findViewById(R.id.tvValor);
            portada= itemView.findViewById(R.id.imgPortada);
        }
    }
}
