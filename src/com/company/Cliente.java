package com.company;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Cliente implements Runnable {

    private int puerto;
    private String mensaje;

    public Cliente(int puerto, String mensaje) {
        this.puerto = puerto;
        this.mensaje = mensaje;
    }

    @Override
    public void run() {
        final String HOST = "192.168.1.121"; // 127.0.0.1

        DataOutputStream out;

        try {
            Socket cliente = new Socket(HOST, puerto);

            out = new DataOutputStream(cliente.getOutputStream());

            //Envio del mensaje
            out.writeUTF(mensaje);

            cliente.close();
        } catch (Exception e) {

        }
    }
}
