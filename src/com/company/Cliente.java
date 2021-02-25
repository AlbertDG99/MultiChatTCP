package com.company;

import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.Base64;

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
            ImageIcon imageIcon = new ImageIcon(ruta);

            OutputStream outputStream = cliente.getOutputStream();
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            Image image = imageIcon.getImage();
            BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);

            ImageIO.write(bufferedImage, "png", bufferedOutputStream);
            bufferedOutputStream.close();
            cliente.close();

            File imagen = new File(ruta);
            //Image conversion to byte array
            FileInputStream imageInFile = new FileInputStream(imagen);
            byte imageData[] = new byte[(int) imagen.length()];
            imageInFile.read(imageData);

            //Image conversion byte array in Base64 String
            String imageDataString = encodeImage(imageData);
            imageInFile.close();

            //the object that will be send to Server
            JSONObject obj = new JSONObject();
            //name of the image
            obj.put("imagen", imageDataString);
            obj.put("mensaje", mensaje);

            //connection to Server

            DataOutputStream outToServer = new DataOutputStream(cliente.getOutputStream());

            //send data
            outToServer.writeBytes(obj.toJSONString());
            cliente.close();

/*
            out = new DataOutputStream(cliente.getOutputStream());

            //Envio del mensaje
            out.writeUTF(mensaje);

            cliente.close();
            mensaje = "";
*/
        } catch (IOException unknownHostException) {
            unknownHostException.printStackTrace();
        }
    }
    public static String encodeImage(byte[] imageByteArray) {
        return Base64.getEncoder().encodeToString(imageByteArray);
    }

}
