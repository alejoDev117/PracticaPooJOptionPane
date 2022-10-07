package com.alejandro.dominio;

import java.util.Random;

public class Jugador {
    private static final int MINIMO_DADO = 1;
    private static final int MAXIMO_DADO = 6;
    private int validacionSegundaOportunidad;
    private String nombre;
    private int dineroActual;

    public Jugador(String nombre, int dineroActual) {
        this.nombre = nombre;
        this.dineroActual = dineroActual;
    }

    public int lanzarDado(){
       return (int) (Math.random()* MAXIMO_DADO + MINIMO_DADO);
    }

    public boolean ValidarMontos(int montoApuesta){
        if(montoApuesta<=dineroActual){
            return true;
        }else {
            return false;
        }
    }

    public String getNombre() {
        return nombre;
    }

    public int getDineroActual() {
        return dineroActual;
    }

    public void setDineroActual(int dineroActual) {
        this.dineroActual = dineroActual;
    }
}
