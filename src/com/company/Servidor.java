package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;

public class Servidor extends Observable implements Runnable {

    private int puerto;

    public Servidor(int puerto) {
        this.puerto = puerto;
    }

    @Override
    public void run() {

        ServerSocket servidor = null;
        Socket cliente = null;
        DataInputStream in;

        try {
            servidor = new ServerSocket(puerto);

            /* Siempre escuchando peticiones */
            while(true) {
                // Espero a que un cliente se coencte
                cliente = servidor.accept();
                in = new DataInputStream(cliente.getInputStream());

                //Leer el mensaje
                String mensaje = in.readUTF();

                // Como me ha llegado el mensaje, activo el cambio
                setChanged();

                // Notificar el cambio a mis observadores: usuarios
                notifyObservers(mensaje);

                clearChanged();

                cliente.close();
            }
        }catch(Exception e) {}
    }
}
