package com.mycompany.bodegaapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginDialog extends JDialog {
    private JTextField usuarioField;
    private JPasswordField contraseñaField;
    private JButton btnLogin;
    private boolean autenticado = false;

    public LoginDialog(JFrame parent) {
        super(parent, "Autenticación", true);
        setLayout(new GridLayout(3, 2));

        // Crear campos de entrada para usuario y contraseña
        JLabel usuarioLabel = new JLabel("Usuario:");
        usuarioField = new JTextField(20);
        JLabel contraseñaLabel = new JLabel("Contraseña:");
        contraseñaField = new JPasswordField(20);

        // Botón de login
        btnLogin = new JButton("Iniciar sesión");
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = usuarioField.getText();
                String contraseña = new String(contraseñaField.getPassword());

                if (usuario.equals("admin") && contraseña.equals("1234")) {
                    autenticado = true;
                    dispose();  // Cerrar el cuadro de diálogo si la autenticación es exitosa
                } else {
                    JOptionPane.showMessageDialog(LoginDialog.this, "Usuario o contraseña incorrectos.");
                }
            }
        });

        // Agregar los componentes al diálogo
        add(usuarioLabel);
        add(usuarioField);
        add(contraseñaLabel);
        add(contraseñaField);
        add(new JLabel());  // Espacio vacío
        add(btnLogin);

        setSize(300, 150);
        setLocationRelativeTo(parent);  // Centrar el diálogo
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    // Método para verificar si el login fue exitoso
    public boolean isAutenticado() {
        return autenticado;
    }
}
