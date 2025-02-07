package com.mycompany.bodegaapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Persistencia {

    // Guardar productos en un archivo
    public static void guardarProductos(ArrayList<Producto> productos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("productos.txt"))) {
            for (Producto producto : productos) {
                // Escribir en el archivo el formato: codigo,nombre,cantidad,precio
                writer.write(producto.getCodigo() + "," + producto.getNombre() + "," + producto.getCantidad() + "," + producto.getPrecio());
                writer.newLine();
            }
            System.out.println("Productos guardados exitosamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Cargar productos desde un archivo
    public static ArrayList<Producto> cargarProductos() {
        ArrayList<Producto> productos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("productos.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String codigo = data[0];
                String nombre = data[1];
                int cantidad = Integer.parseInt(data[2]);
                double precio = Double.parseDouble(data[3]);
                Producto producto = new Producto(codigo, nombre, cantidad, precio);
                productos.add(producto);
            }
            System.out.println("Productos cargados exitosamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productos;
    }
}
