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

    public static void main(String[] args) {
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
                InetAddress ipCliActual = cliente.getInetAddress();
                if (listaIP.indexOf(cliente.getInetAddress()) == -1)
                    listaIP.add(cliente.getInetAddress());

                /*-------------------------------*/
                /*
               BufferedInputStream bufferedInputStream = new BufferedInputStream(cliente.getInputStream());
               BufferedImage bufferedImage = ImageIO.read(bufferedInputStream);

                if (bufferedImage != null) {
                    for (InetAddress ip : listaIP) {
                        if (!ip.toString().equals(ipCliActual.toString())) {
                            cliente = new Socket(ip, 5000);
                            OutputStream outputStream = cliente.getOutputStream();
                            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
                            //Envio del mensaje
                            ImageIO.write(bufferedImage, "png", bufferedOutputStream);
                            bufferedOutputStream.close();
                            cliente.close();
                        }
                    }
                } else {*/

                    in = new DataInputStream(cliente.getInputStream());
                    String mensaje = in.readUTF();
                    cliente.close();

                    DataOutputStream out;
                    for (InetAddress ip : listaIP) {
                        if (!ip.toString().equals(ipCliActual.toString())) {
                            cliente = new Socket(ip, 5000);
                            out = new DataOutputStream(cliente.getOutputStream());

                            //Envio del mensaje
                            out.writeUTF(mensaje);

                            cliente.close();
                        }
                    }
                }
            } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
