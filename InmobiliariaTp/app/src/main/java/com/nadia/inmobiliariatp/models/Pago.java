package com.nadia.inmobiliariatp.models;

import java.io.Serializable;

public class Pago implements Serializable {
    private int idPago;

    private boolean estado;
    private double monto;
    private String fechaPago;

    public Pago() {
    }

    public Pago(int idPago, boolean estado, double monto, String fechaPago) {
        this.idPago = idPago;
        this.estado = estado;
        this.monto = monto;
        this.fechaPago = fechaPago;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }
}