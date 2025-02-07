package com.mycompany.bodegaapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class BodegaAppGUI {

    private JFrame frame;
    private Bodega bodega;  // La bodega que contiene los productos
    private JTextArea textArea;  // Área para mostrar la lista de productos
    private JTextField codigoField, nombreField, cantidadField, precioField;
    private JPanel menuPanel, crudPanel;  // Paneles separados para menú y CRUD
    private JScrollPane scrollPane;  // Scroll para el área de texto

    public BodegaAppGUI(Bodega bodega) {
        this.bodega = bodega;

        // Crear la ventana principal
        frame = new JFrame("Bodega - Gestión de Productos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);  // Centrar la ventana
        frame.setLayout(new BorderLayout());

        // Crear el área de texto para mostrar los productos con un tamaño más grande
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setPreferredSize(new Dimension(500, 150));  // Definir tamaño de área de texto
        scrollPane = new JScrollPane(textArea);

        // Panel principal que contiene todos los sub-paneles
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        // Crear los paneles para las opciones y el CRUD
        menuPanel = crearMenuPanel();
        crudPanel = crearCrudPanel();

        // Mostrar el menú primero
        mainPanel.add(menuPanel, BorderLayout.CENTER);
        frame.add(mainPanel, BorderLayout.CENTER);

        // Mostrar la ventana de autenticación antes de mostrar el menú
        mostrarAutenticacion();

        frame.setVisible(true);
    }

    // Crear el panel del menú principal con todas las opciones visibles
    private JPanel crearMenuPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton listarButton = new JButton("Listar Productos");
        JButton agregarButton = new JButton("Agregar Producto");
        JButton modificarButton = new JButton("Modificar Producto");
        JButton eliminarButton = new JButton("Eliminar Producto");

        // Botones para ordenar por los diferentes atributos
        JButton ordenarCodigoButton = new JButton("Ordenar por Código");
        JButton ordenarNombreButton = new JButton("Ordenar por Nombre");
        JButton ordenarCantidadButton = new JButton("Ordenar por Cantidad");
        JButton ordenarPrecioButton = new JButton("Ordenar por Precio");

        listarButton.addActionListener(e -> listarProductos());
        agregarButton.addActionListener(e -> mostrarCrudPanel());
        modificarButton.addActionListener(e -> mostrarCrudPanel());
        eliminarButton.addActionListener(e -> mostrarCrudPanel());
        ordenarCodigoButton.addActionListener(e -> ordenarProductosPorCodigo());
        ordenarNombreButton.addActionListener(e -> ordenarProductosPorNombre());
        ordenarCantidadButton.addActionListener(e -> ordenarProductosPorCantidad());
        ordenarPrecioButton.addActionListener(e -> ordenarProductosPorPrecio());

        panel.add(listarButton);
        panel.add(agregarButton);
        panel.add(modificarButton);
        panel.add(eliminarButton);
        panel.add(ordenarCodigoButton);
        panel.add(ordenarNombreButton);
        panel.add(ordenarCantidadButton);
        panel.add(ordenarPrecioButton);

        return panel;
    }

    // Crear el panel CRUD para agregar, modificar, eliminar productos
    private JPanel crearCrudPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));  // Distribuir en filas y columnas

        // Campos de entrada para el producto
        panel.add(new JLabel("Código:"));
        codigoField = new JTextField();
        panel.add(codigoField);

        panel.add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        panel.add(nombreField);

        panel.add(new JLabel("Cantidad:"));
        cantidadField = new JTextField();
        panel.add(cantidadField);

        panel.add(new JLabel("Precio:"));
        precioField = new JTextField();
        panel.add(precioField);

        // Botones de las acciones CRUD
        JButton agregarButton = new JButton("Agregar");
        JButton modificarButton = new JButton("Modificar");
        JButton eliminarButton = new JButton("Eliminar");
        JButton listarButton = new JButton("Listar Productos");

        agregarButton.addActionListener(e -> agregarProducto());
        modificarButton.addActionListener(e -> modificarProducto());
        eliminarButton.addActionListener(e -> eliminarProducto());
        listarButton.addActionListener(e -> listarProductos());

        panel.add(agregarButton);
        panel.add(modificarButton);
        panel.add(eliminarButton);
        panel.add(listarButton);

        return panel;
    }

    // Método para mostrar la ventana de autenticación
    private void mostrarAutenticacion() {
        // Mostrar los cuadros de entrada para usuario y contraseña
        String usuario = JOptionPane.showInputDialog("Introduce el usuario:");
        String contraseña = JOptionPane.showInputDialog("Introduce la contraseña:");

        // Validar usuario y contraseña
        if ("admin".equals(usuario) && "1234".equals(contraseña)) {
            // Si es correcto, muestra el menú
            mostrarMenu();
        } else {
            // Si es incorrecto, muestra mensaje de error y cierra la aplicación
            JOptionPane.showMessageDialog(frame, "Usuario o contraseña incorrectos.");
            System.exit(0);  // Cierra la aplicación si el login falla
        }
    }

    // Método para mostrar el menú principal después de un login exitoso
    private void mostrarMenu() {
        // Crea el panel principal que contendrá las opciones del menú
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(menuPanel, BorderLayout.CENTER);

        // Coloca el panel principal en la ventana
        frame.add(mainPanel, BorderLayout.CENTER);

        // Hacer visible la ventana principal con las opciones
        frame.setVisible(true);
    }

    // Método para mostrar la vista CRUD
    private void mostrarCrudPanel() {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(crudPanel, BorderLayout.CENTER);
        frame.getContentPane().add(scrollPane, BorderLayout.SOUTH);
        frame.revalidate();
        frame.repaint();
    }

    private void agregarProducto() {
        String codigo = codigoField.getText();
        String nombre = nombreField.getText();
        int cantidad = Integer.parseInt(cantidadField.getText());
        double precio = Double.parseDouble(precioField.getText());

        Producto nuevoProducto = new Producto(codigo, nombre, cantidad, precio);
        bodega.agregarProducto(nuevoProducto);
        JOptionPane.showMessageDialog(frame, "Producto agregado exitosamente.");
        limpiarCampos();
    }

    private void modificarProducto() {
        String codigo = codigoField.getText();
        Producto producto = bodega.buscarProducto(codigo);

        if (producto != null) {
            String nombre = nombreField.getText();
            int cantidad = Integer.parseInt(cantidadField.getText());
            double precio = Double.parseDouble(precioField.getText());

            producto.setNombre(nombre);
            producto.setCantidad(cantidad);
            producto.setPrecio(precio);
            JOptionPane.showMessageDialog(frame, "Producto modificado exitosamente.");
        } else {
            JOptionPane.showMessageDialog(frame, "Producto no encontrado.");
        }
        limpiarCampos();
    }

    private void eliminarProducto() {
        String codigo = codigoField.getText();
        Producto producto = bodega.buscarProducto(codigo);

        if (producto != null) {
            bodega.eliminarProducto(producto);
            JOptionPane.showMessageDialog(frame, "Producto eliminado exitosamente.");
        } else {
            JOptionPane.showMessageDialog(frame, "Producto no encontrado.");
        }
        limpiarCampos();
    }

    private void listarProductos() {
        ArrayList<Producto> productos = bodega.getProductos();  // Obtenemos los productos desde la bodega

        // Verificamos si la lista de productos está vacía
        if (productos.isEmpty()) {
            textArea.setText("No hay productos en la bodega.");
        } else {
            StringBuilder textoProductos = new StringBuilder();
            for (Producto producto : productos) {
                textoProductos.append(producto.mostrarInfo()).append("\n");  // Concatenamos cada producto
            }
            textArea.setText(textoProductos.toString());  // Actualizamos el área de texto con la lista de productos
        }
    }


    private void ordenarProductosPorNombre() {
        ArrayList<Producto> productos = bodega.getProductos();
        productos.sort(Comparator.comparing(Producto::getNombre));
        mostrarProductosEnTexto(productos);
    }

    private void ordenarProductosPorCodigo() {
        ArrayList<Producto> productos = bodega.getProductos();
        productos.sort(Comparator.comparing(Producto::getCodigo));
        mostrarProductosEnTexto(productos);
    }

    private void ordenarProductosPorCantidad() {
        ArrayList<Producto> productos = bodega.getProductos();
        productos.sort(Comparator.comparingInt(Producto::getCantidad));
        mostrarProductosEnTexto(productos);
    }

    private void ordenarProductosPorPrecio() {
        ArrayList<Producto> productos = bodega.getProductos();
        productos.sort(Comparator.comparingDouble(Producto::getPrecio));
        mostrarProductosEnTexto(productos);
    }

    private void mostrarProductosEnTexto(ArrayList<Producto> productos) {
        StringBuilder sb = new StringBuilder();
        for (Producto producto : productos) {
            sb.append(producto.mostrarInfo()).append("\n");
        }
        textArea.setText(sb.toString());
    }

    private void limpiarCampos() {
        codigoField.setText("");
        nombreField.setText("");
        cantidadField.setText("");
        precioField.setText("");
    }

    public static void main(String[] args) {
        Bodega bodega = new Bodega();
        new BodegaAppGUI(bodega);
    }
}
