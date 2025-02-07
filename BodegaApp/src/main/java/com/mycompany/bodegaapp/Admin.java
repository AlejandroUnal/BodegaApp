package com.mycompany.bodegaapp;

public class Admin {
    // Método para autenticar
    public static boolean autenticar(String usuario, String contrasena) {
        return "admin".equals(usuario) && "1234".equals(contrasena);
    }
}
