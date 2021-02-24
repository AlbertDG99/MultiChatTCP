package com.company;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class Cliente implements Runnable {

    private int puerto;
    private String mensaje;
    private String ruta;

    public Cliente(int puerto, String mensaje,String ruta) {
        this.puerto = puerto;
        this.mensaje = mensaje;
        this.ruta=ruta;
    }



    @Override
    public void run() {
        final String HOST = "25.121.10.101"; // 127.0.0.1

        DataOutputStream out;

        try {
            if(this.mensaje.equals("")){
                //ImageIcon imagen=new ImageIcon(this.ruta);
                //Socket cliente = new Socket(HOST, puerto);

               // OutputStream outputSream=new BufferedOutputStream(outputSream)

            }else{//Mensaje
                Socket cliente = new Socket(HOST, puerto);

                out = new DataOutputStream(cliente.getOutputStream());

                //Envio del mensaje
                out.writeUTF(mensaje);

                cliente.close();
            }

        } catch (Exception e) {

        }
    }

}
