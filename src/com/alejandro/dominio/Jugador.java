package com.alejandro.dominio;

import javax.swing.*;
import java.util.Random;

public class Jugador {
    private static final int MINIMO_DADO = 1;
    private static final int MAXIMO_DADO = 6;
    private String nombre;
    private int dineroActual;
    private ImageIcon cara;

    public Jugador(String nombre, int dineroActual, ImageIcon cara) {
        this.nombre = nombre;
        this.dineroActual = dineroActual;
        this.cara = cara;
    }

    public int lanzarDado(){
       return (int) (Math.random()* MAXIMO_DADO + MINIMO_DADO);
    }

    public boolean ValidarMontos(int montoApuesta){
        if(montoApuesta<dineroActual){
            return true;
        }else {
            return false;
        }
    }

    public ImageIcon getCara() {
        return cara;
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
