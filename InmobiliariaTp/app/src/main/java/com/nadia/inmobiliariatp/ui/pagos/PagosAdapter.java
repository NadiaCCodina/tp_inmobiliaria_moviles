package com.nadia.inmobiliariatp.ui.pagos;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.nadia.inmobiliariatp.R;
import com.nadia.inmobiliariatp.models.Pago;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PagosAdapter extends RecyclerView.Adapter<PagosAdapter.ViewHolder> {

    private List<Pago> listaPagos;
    private Context context;
    private LayoutInflater inflater;

    public PagosAdapter(Context context, List<Pago> listaPagos, LayoutInflater inflater) {
        this.context = context;
        this.listaPagos = listaPagos;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_pago, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pago pagoActual = listaPagos.get(position);
        String fechaOriginal = pagoActual.getFechaPago(); // "2025/08/13"

        String fechaConvertida = null;

        try {

            SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formatoSalida = new SimpleDateFormat("dd/MM/yyyy");
            Date fecha = formatoEntrada.parse(fechaOriginal);
            fechaConvertida = formatoSalida.format(fecha);

            System.out.println(fechaConvertida);
        } catch (ParseException e) {
            e.printStackTrace();
        }




        holder.tvFechaPago.setText("Fecha de pago: " + fechaConvertida);
        holder.tvCodigoPago.setText("Código pago actual: " + pagoActual.getIdPago());
       // holder.tvNumeroPago.setText("Número de pagoActual: " + pagoActual.getNumeroPago());
        //holder.tvCodigoContrato.setText("Código de contrato: " + pagoActual.getCodigoContrato());
        holder.tvImporte.setText("Importe: $" + pagoActual.getMonto());

    }

    @Override
    public int getItemCount() {
        return listaPagos != null ? listaPagos.size() : 0;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCodigoPago, tvNumeroPago, tvCodigoContrato, tvImporte, tvFechaPago;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCodigoPago = itemView.findViewById(R.id.tvCodigoPago);
            //tvNumeroPago = itemView.findViewById(R.id.tvNumeroPago);
            //tvCodigoContrato = itemView.findViewById(R.id.tvCodigoContrato);
            tvImporte = itemView.findViewById(R.id.tvImporte);
            tvFechaPago = itemView.findViewById(R.id.tvFechaPago);
        }
    }
}

