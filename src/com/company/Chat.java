package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.ColorUIResource;
import java.io.File;

public class Chat extends JFrame implements Observer {

    private static String nombreUsu;
    private JPanel mainPanel;
    private JTextArea tChat;//Chat donde aparecen los mensajes
    private JTextField tMensaje;//Mensaje que escribo para enviarlo
    private JButton bEnviar;//Boton para enviar los mensajes
    private JButton bImagen;
    private JButton bGaleria;
    private JButton bSubir;
private  File selectedFile;

    public Chat(String titulo) {

        super(titulo);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        //Instanciamos el servidor dentro del objeto chat
        Servidor servidor = new Servidor(5000);
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

        tMensaje.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    enviarMensaje();
                }
            }
        });

        bImagen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "jpeg");
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                fileChooser.setFileFilter(filter);
                int result = fileChooser.showOpenDialog(mainPanel);
                //Si selecciona una foto
                if (result == JFileChooser.APPROVE_OPTION) {

                    selectedFile = fileChooser.getSelectedFile();
                    Cliente cliente = new Cliente(8000, "",selectedFile.getAbsolutePath());
                }
            }
        });
        bGaleria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedFile!=null) {
                    BufferedImage image = null;
                    try {
                        image = ImageIO.read(selectedFile);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    JLabel picLabel = new JLabel(new ImageIcon(image));
                    JOptionPane.showMessageDialog(null, picLabel, "Ultima imagen recibida", JOptionPane.PLAIN_MESSAGE, null);
                }
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

        Cliente cliente = new Cliente(9000, mensaje,"");
        Thread thread = new Thread(cliente);
        thread.start();
    }

    public static void main(String[] args) {
        UIManager UI=new UIManager();
        UI.put("OptionPane.background",Color.black);
        UI.put("Panel.background", new ColorUIResource(144, 154, 171));
        UI.put("OptionPane.background", Color.black);
        UI.put("Panel.background", Color.white);
        nombreUsu = JOptionPane.showInputDialog("Ingresa el nombre con el que te deseas identificar");
        if (nombreUsu.equals("")){
            nombreUsu="Samurai";
        }

        JFrame frame = new Chat("TurboChat");
        frame.setSize(500, 500);
        frame.setVisible(true);
        Cliente cliente = new Cliente(9000,"------"+nombreUsu+" se unió al chat------","");
        Thread thread = new Thread(cliente);
        thread.start();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    @Override
    public void update(Observable o, Object arg) {
        this.tChat.append((String) arg);
    }

    public void setData(Chat data) {
    }

    public void getData(Chat data) {
    }

    public boolean isModified(Chat data) {
        return false;
    }
}
