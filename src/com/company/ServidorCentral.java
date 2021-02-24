package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;

public class ServidorCentral extends Observable {

    public static void main() {
        int puerto = 9000;
        ServerSocket servidor = null;
        Socket cliente = null;
        DataInputStream in;
        ArrayList<InetAddress> listaIP = new ArrayList<InetAddress>();

        try {
            servidor = new ServerSocket(puerto);



            /* Siempre escuchando peticiones */
            while (true) {
                // Espero a que un cliente se coencte
                cliente = servidor.accept();
                if (listaIP.indexOf(cliente.getInetAddress()) == -1)
                    listaIP.add(cliente.getInetAddress());
                in = new DataInputStream(cliente.getInputStream());
                /*-------------------------------*/
                try {
                    BufferedInputStream bufferedInputStream=new BufferedInputStream(in);
                    BufferedImage bufferedImage= ImageIO.read(bufferedInputStream);




                   // ImageIO.write(bufferedImage,"png",bufferedOutputStream);
                   // bufferedOutputStream.close();
                    cliente.close();
                }catch (Exception e){

                }




                //Leer el mensaje
                String mensaje = in.readUTF();

                cliente.close();

                DataOutputStream out;
                for (InetAddress ip : listaIP) {
                    cliente = new Socket(ip, 5000);
                    out = new DataOutputStream(cliente.getOutputStream());

                    //Envio del mensaje
                    out.writeUTF(mensaje);

                    cliente.close();
                }


            }

        } catch (Exception e) {
        }
    }
}
