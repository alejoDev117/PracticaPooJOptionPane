package com.alejandro.app;
import com.alejandro.dominio.JuegoGuayabita;
import com.alejandro.dominio.Jugador;

import javax.swing.*;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        ImageIcon iconoGuayaba = new ImageIcon(App.class.getResourceAsStream("iconoGuayaba.jpg").readAllBytes());
        ImageIcon iconoInstrucciones = new ImageIcon(App.class.getResourceAsStream("iconoInstrucciones.jpg").readAllBytes());
        ImageIcon iconoPlata = new ImageIcon(App.class.getResourceAsStream("iconoMoneda.jpg").readAllBytes());
        ImageIcon iconoJugador1 = new ImageIcon(App.class.getResourceAsStream("iconoPersona1.jpg").readAllBytes());
        ImageIcon iconoJugador2 = new ImageIcon(App.class.getResourceAsStream("iconoPersona2.jpg").readAllBytes());
        boolean juego = true;
        while(juego){
            int opcion = (int) JOptionPane.showOptionDialog(null,"Bienvenido al juego de la guayabita\nQue quieres hacer?","Guayabita",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,iconoGuayaba,new Object[]{"jugar","instrucciones"},null);
            switch (opcion){
                case 0: {
                    int apuestaInicial = Integer.parseInt((String) JOptionPane.showInputDialog(null, "Ingrese monto inicial", "Apuesta inicial", JOptionPane.YES_OPTION, iconoPlata, null, 0));
                    JuegoGuayabita nuevoJuego = new JuegoGuayabita(apuestaInicial);
                    ////////////
                    String nombre1 = (String) JOptionPane.showInputDialog(null, "Ingrese nombre del jugador", "Jugador 1", JOptionPane.OK_OPTION, iconoJugador1, null, "nombre");
                    int dineroJugador1 = Integer.parseInt((String) JOptionPane.showInputDialog(null, nombre1 + " Ingrese dinero(monto inicial: " + apuestaInicial + ")", "Dinero", JOptionPane.QUESTION_MESSAGE, iconoPlata, null, 0));
                    Jugador jugador1 = new Jugador(nombre1, dineroJugador1);
                    /////////
                    String nombre2 = (String) JOptionPane.showInputDialog(null, "Ingrese nombre del jugador", "Jugador 2", JOptionPane.OK_OPTION, iconoJugador2, null, "nombre");
                    int dineroJugador2 = Integer.parseInt((String) JOptionPane.showInputDialog(null, nombre2 + " Ingrese dinero(monto inicial: " + apuestaInicial + ")", "Dinero", JOptionPane.QUESTION_MESSAGE, iconoPlata, null, 0));
                    Jugador jugador2 = new Jugador(nombre2, dineroJugador2);
                    //////////
                    if (nuevoJuego.iniciarJuego(jugador1, jugador2)) {
                        JOptionPane.showMessageDialog(null, "Pote inicial: " + nuevoJuego.getPote(), "Pote", JOptionPane.OK_OPTION, iconoPlata);
                    } else {
                        JOptionPane.showMessageDialog(null, "Error el dinero de los jugadores debe ser mayor o igual a la apuesta inicial", "Error falta de dinero", JOptionPane.WARNING_MESSAGE);
                        break;
                    }
                    /////////EMPIEZA EL JUEGO////////
                    int cambio = 0;
                    while (jugador1.getDineroActual() > 0 && jugador2.getDineroActual() > 0) {
                        if (cambio == 0) { // jugador 1
                            int decisionTurno = JOptionPane.showConfirmDialog(null, jugador1.getNombre() + " El pote actual es: " + nuevoJuego.getPote() + " Desea lanzar el dado?", "Turno de: " + jugador1.getNombre(), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
                            if (decisionTurno == 0) {// si desea tirar o ceder turno
                                int valorDado = jugador1.lanzarDado();
                                if (valorDado == 1 || valorDado == 6) {  // si el dado del jugador 1 es 1 o 6
                                    JOptionPane.showConfirmDialog(null, "Tu dado fue: " + valorDado + " debes ceder el turno", "Turno de: " + jugador1.getNombre(), JOptionPane.YES_OPTION, JOptionPane.INFORMATION_MESSAGE, null);
                                    cambio = 1;
                                } else { // si el dado del jugador 1 es 2 a 5
                                    int decisionDeApuesta = JOptionPane.showConfirmDialog(null, jugador1.getNombre() + " Este es tu dado: " + valorDado + "\nDesea hacer un apuesta?", "Turno de: " + jugador1.getNombre(), JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null);
                                    if (decisionDeApuesta == 0) {//si hace la apuesta
                                        System.out.println(jugador1.getDineroActual());
                                        System.out.println(nuevoJuego.getPote());
                                        int valorApostar = Integer.parseInt((String) JOptionPane.showInputDialog(null, "Cuanto deseas apostar?\n(El pote esta en:" + nuevoJuego.getPote() + ")", "Turno de: " + jugador1.getNombre(), JOptionPane.INFORMATION_MESSAGE, null, null, 0));
                                        int cantidadAntesDeApuesta = jugador1.getDineroActual();
                                        if (nuevoJuego.segundaTirada(valorDado, jugador1, valorApostar)) {
                                            JOptionPane.showMessageDialog(null, " En hora buena, tu dado fue: " + valorDado + " tu dinero: " + jugador1.getDineroActual() + "\nPote actual: " + nuevoJuego.getPote(), "Victoria!!!", JOptionPane.INFORMATION_MESSAGE, null);
                                        } else if (jugador1.getDineroActual() == cantidadAntesDeApuesta) {
                                            JOptionPane.showMessageDialog(null, "No posees cantidad suficiente para apostar", "ERROR", JOptionPane.INFORMATION_MESSAGE, null);
                                        } else {
                                            JOptionPane.showMessageDialog(null, "Lastima, tu dado fue:" + valorDado + " Tu dinero actual: " + jugador1.getDineroActual() + " Pote actual: " + nuevoJuego.getPote(), "Derrota", JOptionPane.INFORMATION_MESSAGE, null);
                                        }
                                    } else {// si desea ceder el turno de posibilidad de apuesta
                                        cambio = 1;
                                    }
                                }
                                cambio = 1;
                            } else {// cede turno sin tirar el dado
                                cambio = 1;
                            }
                            cambio = 1;
                        } else if (cambio == 1) { // jugador 2
                            int decisionTurno = JOptionPane.showConfirmDialog(null, jugador2.getNombre() + " El pote actual es: " + nuevoJuego.getPote() + " Desea lanzar el dado?", "Turno de: " + jugador2.getNombre(), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
                            if (decisionTurno == 0) {
                                int valorDado = jugador2.lanzarDado();
                                if (valorDado == 1 || valorDado == 6) {
                                    JOptionPane.showConfirmDialog(null, "Tu dado fue: " + valorDado + " debes ceder el turno", "Turno de: " + jugador2.getNombre(), JOptionPane.YES_OPTION, JOptionPane.INFORMATION_MESSAGE, null);
                                    cambio = 0;
                                } else {
                                    // si el dado del jugador 2 es 2 a 5
                                    int decisionDeApuesta = JOptionPane.showConfirmDialog(null, jugador2.getNombre() + " Este es tu dado: " + valorDado + "\nDesea hacer un apuesta?", "Turno de: " + jugador2.getNombre(), JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null);
                                    if (decisionDeApuesta == 0) {//si hace la apuesta
                                        System.out.println(jugador2.getDineroActual());
                                        System.out.println(nuevoJuego.getPote());
                                        int valorApostar = Integer.parseInt((String) JOptionPane.showInputDialog(null, "Cuanto deseas apostar?\n(El pote esta en:" + nuevoJuego.getPote() + ")", "Turno de: " + jugador2.getNombre(), JOptionPane.INFORMATION_MESSAGE, null, null, 0));
                                        int cantidadAntesDeApuesta = jugador2.getDineroActual();
                                        if (nuevoJuego.segundaTirada(valorDado, jugador2, valorApostar)) {
                                            JOptionPane.showMessageDialog(null, " En hora buena, tu dado fue: " + valorDado + " tu dinero: " + jugador2.getDineroActual() + "\nPote actual: " + nuevoJuego.getPote(), "Victoria!!!", JOptionPane.INFORMATION_MESSAGE, null);
                                        } else if (jugador1.getDineroActual() == cantidadAntesDeApuesta) {
                                            JOptionPane.showMessageDialog(null, "No posees cantidad suficiente para apostar", "ERROR", JOptionPane.INFORMATION_MESSAGE, null);
                                        } else {
                                            JOptionPane.showMessageDialog(null, "Lastima, tu dado fue:" + valorDado + " Tu dinero actual: " + jugador2.getDineroActual() + " Pote actual: " + nuevoJuego.getPote(), "Derrota", JOptionPane.INFORMATION_MESSAGE, null);
                                        }
                                    } else {// si desea ceder el turno
                                        cambio = 0;
                                    }
                                }
                                cambio = 0;
                            } else {
                                cambio = 0;
                            }
                        }
                    }
                    //MOSTRAR VICTORIA //
                    break;
                }
                case 1:
                    String instrucciones = "1.Inicialmente se debe hacer un valor para la puesta inicial\n2.Para poder entrar en el juego su dinero para apostar debe ser mayor que el monto de apuesta inicial\n3.Al lanzar el dado si sale 1 o 6 tendra que ceder el turno\n4.Si sale otro numero tendra la posibilidad de apostar hasta la cantidad existente en el pote\nsi el numero del dado es mayor o igual al que anteriormente saco se lleva lo apostado, de lo contrario lo perdera\nEl juego termina cuando uno de los dos jugadores quede sin dinero";
                    JOptionPane.showMessageDialog(null,instrucciones,"Instrucciones",JOptionPane.INFORMATION_MESSAGE,iconoInstrucciones);
                    break;
                case -1:
                    juego = false;
                    break;
            }
        }
    }
}
