package com.company;

import netscape.javascript.JSObject;
import org.json.simple.JSONValue;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Observable;

import org.apache.commons.io.IOUtils;

public class ServidorCentral extends Observable {

    public static byte[] decodeimage(String imagen){
        return Base64.getDecoder().decode(imagen);
    }


    public static void main(String[] args) {
        int puerto = 9000;
        ServerSocket servidor = null;
        Socket cliente = null;
        DataInputStream in;
        ArrayList<InetAddress> listaIP = new ArrayList<InetAddress>();

        try {



            /* Siempre escuchando peticiones */
            while (true) {
                // Espero a que un cliente se coencte
                cliente = servidor.accept();
                InetAddress ipCliActual = cliente.getInetAddress();
                if (listaIP.indexOf(cliente.getInetAddress()) == -1)
                    listaIP.add(cliente.getInetAddress());


                /*-------------------------------*/
                servidor = new ServerSocket(puerto);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                String input = org.apache.commons.io.IOUtils.toString(bufferedReader);
                JSObject objeto = (JSObject) JSONValue.parse(input);
                String mensaje=objeto.getMember("mensaje").toString();
                String imagen=objeto.getMember("imagen").toString();
                byte[] imageByte=decodeimage(imagen);
                DataOutputStream out;

                if(mensaje==""){

                    //Enviamos la imagen
                    for (InetAddress ip : listaIP) {
                        if (!ip.toString().equals(ipCliActual.toString())) {
                            cliente = new Socket(ip, 5000);
                            out = new DataOutputStream(cliente.getOutputStream());

                            //Envio del mensaje
                            out.write(imageByte);

                            cliente.close();
                        }
                    }

                }else{
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

               /* in = new DataInputStream(cliente.getInputStream());
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
                }*/
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
