package com.alejandro.dominio;

public class JuegoGuayabita {
    private int pote;
    private int apuestaInicial;

    public JuegoGuayabita(int apuestaInicial) {
        this.apuestaInicial = apuestaInicial;
    }
    public boolean iniciarJuego(Jugador jugador1, Jugador jugador2){
        if(jugador1.ValidarMontos(apuestaInicial) && jugador2.ValidarMontos(apuestaInicial))
        {
            jugador1.setDineroActual(jugador1.getDineroActual() - apuestaInicial);
            jugador2.setDineroActual(jugador2.getDineroActual()- apuestaInicial);
            pote = apuestaInicial * 2;
            return true;
        }
        return false;
    }
    public boolean segundaTirada(int dado, Jugador jugador, int cantidadEnApuesta){
        if(validarApuestaSegura(cantidadEnApuesta,jugador)){
            jugador.setDineroActual(jugador.getDineroActual()-cantidadEnApuesta);
            if(jugador.lanzarDado()>=dado){
                jugador.setDineroActual(jugador.getDineroActual()+cantidadEnApuesta);
                pote -= cantidadEnApuesta;
                return true;
            }else {
                pote += cantidadEnApuesta;
                return false;
            }
        }
        return false;
    }

    private boolean validarApuestaSegura(int montoApostar,Jugador jugadorApostar){
        if(montoApostar<=jugadorApostar.getDineroActual() && montoApostar<= pote){
            return true;
        }else{
            return false;
        }
    }
    public Jugador ganadador(Jugador numero1 ,Jugador numero2){
        if(numero1.getDineroActual()> 0 && numero2.getDineroActual()<= 0){
            return numero1;
        }
        if(numero2.getDineroActual()>0 && numero1.getDineroActual()<= 0){
            return numero2;
        }
        return null;
    }

    public int getPote() {
        return pote;
    }

    public void setPote(int pote) {
        this.pote = pote;
    }
}
