package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.util.Observable;
import java.util.Observer;

public class Chat extends JFrame implements Observer {

    private String nombreUsu = "Usuario 1";
    private JPanel mainPanel;
    private JTextArea tChat;//Chat donde aparecen los mensajes
    private JTextField tMensaje;//Mensaje que escribo para enviarlo
    private JButton bEnviar;//Boton para enviar los mensajes
    private JButton GIFButton;


    public Chat(String titulo) {

        super(titulo);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        //Instanciamos el servidor dentro del objeto chat
        Servidor servidor = new Servidor(8000);
        // Agrego chat como observador de servidor para recibir notificaciones de mensajes
        servidor.addObserver(this);
        Thread thread = new Thread(servidor);
        thread.start();

        bEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enviarMensaje();
            }
        });
    }

    /*Enviar mensaje
     * Envia un mensaje desde un usuario hacia otro
     */
    private void enviarMensaje() {
        String mensaje = "< " + nombreUsu + " > " + tMensaje.getText() + "\n";

        tChat.append(mensaje);

        tMensaje.setText("");

        Cliente cliente = new Cliente(5000, mensaje);
        Thread thread = new Thread(cliente);
        thread.start();
    }

    public static void main(String[] args) {

        JFrame frame = new Chat("TurboChat");
        frame.setSize(500, 500);
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
