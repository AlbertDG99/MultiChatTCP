package com.company;


import java.util.Observable;
import java.util.Observer;

public class Usuario1 extends javax.swing.JFrame implements Observer {

    public Usuario1() {
        iniciarComponentes();
        this.getRootPane().setDefaultButton(this.btnEnviar);
        
        Servidor servidor = new Servidor(5000);
        // Agrego usuario 1 como observador de servidor para recibir notificaciones de mensajes
        servidor.addObserver(this);
        Thread thread = new Thread(servidor);
        thread.start();
    }

    private void enviarMensaje(java.awt.event.ActionEvent evt) {
    	String mensaje = "--- USUARIO 1 --- " + this.txtTextoEnviar.getText() + "\n";
    	
    	this.txtTexto.append(mensaje);
    	
    	//Cliente cliente = new Cliente(5000, mensaje);
    	//Thread thread = new Thread(cliente);
    	//thread.start();
    }

    @Override
    public void update(Observable o, Object arg) {
        this.txtTexto.append((String) arg);
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {} catch (InstantiationException ex) {} catch (IllegalAccessException ex) {} catch (javax.swing.UnsupportedLookAndFeelException ex) {}

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Usuario1().setVisible(true);
            }
        });
    }

    private javax.swing.JButton btnEnviar; 
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtTexto; // CONVERSACION 
    private javax.swing.JTextField txtTextoEnviar; // MENSAJE QUE ESCRIBO PARA ENVIAR 

    /* Metodo que inicia y configura la interfaz grafica */
    private void iniciarComponentes() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txtTexto = new javax.swing.JTextArea();
        btnEnviar = new javax.swing.JButton();
        txtTextoEnviar = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Usuario 1");

        txtTexto.setColumns(20);
        txtTexto.setRows(5);
        jScrollPane1.setViewportView(txtTexto);

        btnEnviar.setText("Enviar");
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enviarMensaje(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtTextoEnviar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEnviar, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(txtTextoEnviar))
                .addContainerGap())
        );
        pack();
    }
}
