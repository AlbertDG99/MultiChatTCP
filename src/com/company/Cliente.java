package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
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
            Socket cliente = new Socket(HOST, puerto);
            if(mensaje.equals("")){
                ImageIcon imageIcon=new ImageIcon(ruta);

                OutputStream outputStream=cliente.getOutputStream();
                BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(outputStream);
                Image image=imageIcon.getImage();
                BufferedImage bufferedImage=new BufferedImage(image.getWidth(null),image.getHeight(null),BufferedImage.TYPE_INT_RGB);

                ImageIO.write(bufferedImage,"png",bufferedOutputStream);
                bufferedOutputStream.close();
                cliente.close();


            }else{//Mensaje


                out = new DataOutputStream(cliente.getOutputStream());

                //Envio del mensaje
                out.writeUTF(mensaje);

                cliente.close();
                mensaje="";
            }

        } catch (Exception e) {

        }
    }

}
