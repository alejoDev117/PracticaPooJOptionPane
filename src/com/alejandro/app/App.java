package com.alejandro.app;
import com.alejandro.dominio.JuegoGuayabita;
import com.alejandro.dominio.Jugador;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws IOException {
        ImageIcon iconoGuayaba = new ImageIcon(App.class.getResourceAsStream("iconoGuayaba.jpg").readAllBytes());
        ImageIcon iconoInstrucciones = new ImageIcon(App.class.getResourceAsStream("iconoInstrucciones.jpg").readAllBytes());
        ImageIcon iconoPlata = new ImageIcon(App.class.getResourceAsStream("iconoMoneda.jpg").readAllBytes());
        ImageIcon iconoJugador1 = new ImageIcon(App.class.getResourceAsStream("iconoPersona1.jpg").readAllBytes());
        ImageIcon iconoJugador2 = new ImageIcon(App.class.getResourceAsStream("iconoPersona2.jpg").readAllBytes());
        ImageIcon iconoVictoria = new ImageIcon(App.class.getResourceAsStream("iconoVictoria.png").readAllBytes());
        ImageIcon iconoPerdida = new ImageIcon(App.class.getResourceAsStream("iconoPerdida.jpg").readAllBytes());
        ImageIcon iconoError = new ImageIcon(App.class.getResourceAsStream("iconoError.png").readAllBytes());
        //Caras del dado
        ImageIcon icono1 = new ImageIcon(App.class.getResourceAsStream("iconoDado1.jpg").readAllBytes());
        ImageIcon icono2 = new ImageIcon(App.class.getResourceAsStream("iconoDado2.jpg").readAllBytes());
        ImageIcon icono3 = new ImageIcon(App.class.getResourceAsStream("iconoDado3.jpg").readAllBytes());
        ImageIcon icono4 = new ImageIcon(App.class.getResourceAsStream("iconoDado4.jpg").readAllBytes());
        ImageIcon icono5 = new ImageIcon(App.class.getResourceAsStream("iconoDado5.jpg").readAllBytes());
        ImageIcon icono6 = new ImageIcon(App.class.getResourceAsStream("iconoDado6.jpg").readAllBytes());
        List<ImageIcon> carasDado = new ArrayList<>();
        carasDado.add(iconoGuayaba);
        carasDado.add(icono1);
        carasDado.add(icono2);
        carasDado.add(icono3);
        carasDado.add(icono4);
        carasDado.add(icono5);
        carasDado.add(icono6);
        boolean juego = true;
        while(juego){
            int opcion = (int) JOptionPane.showOptionDialog(null,"Bienvenido al juego de la guayabita\nQue quieres hacer?","Guayabita",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,iconoGuayaba,new Object[]{"jugar","instrucciones"},null);
            switch (opcion){
                case 0: {
                        int apuestaInicial = Integer.parseInt((String) JOptionPane.showInputDialog(null, "Ingrese monto inicial", "Apuesta inicial", JOptionPane.YES_OPTION, iconoPlata, null, 0));
                        JuegoGuayabita nuevoJuego = new JuegoGuayabita(apuestaInicial);
                        ////////////
                        String nombre1 = (String) JOptionPane.showInputDialog(null, "Ingrese nombre del jugador", "Jugador 1", JOptionPane.OK_OPTION, iconoJugador1, null, "nombre");
                        int dineroJugador1 = Integer.parseInt((String) JOptionPane.showInputDialog(null, nombre1 + " Ingrese dinero(monto inicial $" + apuestaInicial + ")", "Dinero", JOptionPane.QUESTION_MESSAGE, iconoPlata, null, 0));
                        Jugador jugador1 = new Jugador(nombre1, dineroJugador1,iconoJugador1);
                        /////////
                        String nombre2 = (String) JOptionPane.showInputDialog(null, "Ingrese nombre del jugador", "Jugador 2", JOptionPane.OK_OPTION, iconoJugador2, null, "nombre");
                        int dineroJugador2 = Integer.parseInt((String) JOptionPane.showInputDialog(null, nombre2 + " Ingrese dinero(monto inicial $" + apuestaInicial + ")", "Dinero", JOptionPane.QUESTION_MESSAGE, iconoPlata, null, 0));
                        Jugador jugador2 = new Jugador(nombre2, dineroJugador2,iconoJugador2);
                        //////////
                        if (nuevoJuego.iniciarJuego(jugador1, jugador2)) {
                            JOptionPane.showMessageDialog(null, "Pote inicial $" + nuevoJuego.getPote(), "Pote", JOptionPane.OK_OPTION, iconoPlata);
                        } else {
                            JOptionPane.showMessageDialog(null, "Error el dinero de los jugadores debe ser mayor  a la apuesta inicial", "Error falta de dinero", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                    /////////EMPIEZA EL JUEGO////////
                    Jugador jugadorActualJugando = nuevoJuego.cambioDeTurno(jugador1,jugador2);
                    while (jugador1.getDineroActual() > 0 && jugador2.getDineroActual() > 0 && nuevoJuego.getPote() > 0) {
                            int decisionTurno = JOptionPane.showConfirmDialog(null, jugadorActualJugando.getNombre() + " El pote actual es $" + nuevoJuego.getPote() + "\nDesea lanzar el dado?", "Turno de " + jugadorActualJugando.getNombre(), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, jugadorActualJugando.getCara());
                            if (decisionTurno == JOptionPane.YES_OPTION) {// si desea tirar o ceder turno
                                int valorDado = jugadorActualJugando.lanzarDado();
                                if (valorDado == 1 || valorDado == 6) {  // si el dado del jugador 1 es 1 o 6
                                    JOptionPane.showMessageDialog(null, "Este es tu dado\ndebes ceder el turno", "Turno de " + jugadorActualJugando.getNombre(),JOptionPane.INFORMATION_MESSAGE, carasDado.get(valorDado));
                                    jugadorActualJugando = nuevoJuego.cambioDeTurno(jugador1,jugador2);
                                } else { // si el dado del jugador 1 es 2 a 5
                                    int decisionDeApuesta = JOptionPane.showConfirmDialog(null, jugadorActualJugando.getNombre() + " Este es tu dado\nDesea hacer un apuesta?", "Turno de " +jugadorActualJugando.getNombre(), JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, carasDado.get(valorDado));
                                    if (decisionDeApuesta == JOptionPane.YES_OPTION) {//si hace la apuesta
                                        int valorApostar = Integer.parseInt((String) JOptionPane.showInputDialog(null, "Cuanto deseas apostar(Dinero Actual $" + jugadorActualJugando.getDineroActual() + ")" + "?\n(El pote esta en $" + nuevoJuego.getPote() + ")", "Turno de " + jugadorActualJugando.getNombre(), JOptionPane.INFORMATION_MESSAGE, iconoPlata, null, 0));
                                        int cantidadAntesDeApuesta = jugadorActualJugando.getDineroActual();
                                        if (nuevoJuego.segundaTirada(valorDado, jugadorActualJugando, valorApostar)) {
                                            JOptionPane.showMessageDialog(null, " En hora buena, tu dado fue " + nuevoJuego.dadoSegundaTirada() + " tu dinero $" + jugadorActualJugando.getDineroActual() + "\nPote actual $" + nuevoJuego.getPote(), "Victoria!!!", JOptionPane.INFORMATION_MESSAGE, iconoVictoria);
                                            jugadorActualJugando = nuevoJuego.cambioDeTurno(jugador1,jugador2);
                                        } else if (jugador1.getDineroActual() == cantidadAntesDeApuesta) {
                                            JOptionPane.showMessageDialog(null, "Cantidad no valida para apostar", "ERROR", JOptionPane.INFORMATION_MESSAGE, iconoError);
                                            jugadorActualJugando = nuevoJuego.cambioDeTurno(jugador1,jugador2);
                                        } else {
                                            JOptionPane.showMessageDialog(null, "Lastima, tu dado fue " + nuevoJuego.dadoSegundaTirada() + " y tu dinero actual $" + jugadorActualJugando.getDineroActual() + " Pote actual $" + nuevoJuego.getPote(), "Derrota", JOptionPane.INFORMATION_MESSAGE, iconoPerdida);
                                            jugadorActualJugando = nuevoJuego.cambioDeTurno(jugador1,jugador2);
                                        }
                                    } else if(decisionDeApuesta == JOptionPane.NO_OPTION){// si desea ceder el turno de posibilidad de apuesta
                                     jugadorActualJugando =  nuevoJuego.cambioDeTurno(jugador1,jugador2);
                                    }
                                }
                            } else if(decisionTurno == JOptionPane.NO_OPTION){// cede turno sin tirar el dado
                                jugadorActualJugando = nuevoJuego.cambioDeTurno(jugador1,jugador2);
                            }
                    }
                    //MOSTRAR VICTORIA //
                    if(nuevoJuego.getPote() == 0){
                        JOptionPane.showMessageDialog(null,"Pote en cero\n"+jugador1.getNombre()+" Dinero Actual $"+jugador1.getDineroActual()+"\n"+jugador2.getNombre()+" Dinero Actual $"+jugador2.getDineroActual(),"Fin de la partida",JOptionPane.INFORMATION_MESSAGE,iconoGuayaba);
                        juego = false;
                    }else {
                        Jugador jugadorGanador = nuevoJuego.ganadador(jugador1,jugador2);
                        jugadorGanador.setDineroActual(jugadorGanador.getDineroActual() + nuevoJuego.getPote());
                        JOptionPane.showMessageDialog(null,"Ganador "+jugadorGanador.getNombre() +" Dinero Total $"+ jugadorGanador.getDineroActual(),"FIN DEL JUEGO",JOptionPane.INFORMATION_MESSAGE,iconoGuayaba);
                    }
                    break;
                }
                case 1: {
                    String instrucciones = "1.Inicialmente se debe hacer un valor para la puesta inicial\n2.Para poder entrar en el juego su dinero para apostar debe ser mayor que el monto de apuesta inicial\n3.Al lanzar el dado si sale 1 o 6 tendra que ceder el turno\n4.Si sale otro numero tendra la posibilidad de apostar hasta la cantidad existente en el pote\nsi el numero del dado es mayor al que anteriormente saco se lleva lo apostado, de lo contrario lo perdera\nEl juego termina cuando uno de los dos jugadores quede sin dinero\nO el pote queda en cero.";
                    JOptionPane.showMessageDialog(null, instrucciones, "Instrucciones", JOptionPane.INFORMATION_MESSAGE, iconoInstrucciones);
                    break;
                }
                case -1: {
                    juego = false;
                    break;
                }
            }
        }
    }
}
