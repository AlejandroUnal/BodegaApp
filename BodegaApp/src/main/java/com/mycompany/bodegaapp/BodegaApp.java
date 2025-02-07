package com.mycompany.bodegaapp;

import java.util.Scanner;

public class BodegaApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bodega bodega = new Bodega();
        Admin admin = new Admin();

        new BodegaAppGUI(bodega);      // Crear la interfaz gráfica

        // Autenticación
        System.out.print("Usuario: ");
        String usuario = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine();

        if (admin.autenticar(usuario, contrasena)) {
            System.out.println("Autenticación exitosa.");
            int opcion = 0;

            // Menú
            do {
                System.out.println("\nMenú:");
                System.out.println("1. Listar productos en bodega");
                System.out.println("2. Agregar producto");
                System.out.println("3. Modificar producto");
                System.out.println("4. Listar productos por nombre");
                System.out.println("5. Listar productos por precio");
                System.out.println("6. Salir");
                System.out.print("Seleccione una opción: ");
                opcion = scanner.nextInt();
                scanner.nextLine();  // Limpiar buffer

                switch (opcion) {
                    case 1:
                        bodega.listarProductos();
                        break;
                    case 2:
                        System.out.print("Ingrese código: ");
                        String codigo = scanner.nextLine();
                        System.out.print("Ingrese nombre: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Ingrese cantidad: ");
                        int cantidad = scanner.nextInt();
                        System.out.print("Ingrese precio: ");
                        double precio = scanner.nextDouble();
                        Producto producto = new Producto(codigo, nombre, cantidad, precio);
                        bodega.agregarProducto(producto);
                        break;
                    case 3:
                        System.out.print("Ingrese código del producto a modificar: ");
                        String codMod = scanner.nextLine();
                        System.out.print("Nuevo nombre: ");
                        String nuevoNombre = scanner.nextLine();
                        System.out.print("Nueva cantidad: ");
                        int nuevaCantidad = scanner.nextInt();
                        System.out.print("Nuevo precio: ");
                        double nuevoPrecio = scanner.nextDouble();
                        bodega.modificarProducto(codMod, nuevoNombre, nuevaCantidad, nuevoPrecio);
                        break;
                    case 4:
                        bodega.listarProductosOrdenadosPorNombre();
                        break;
                    case 5:
                        bodega.listarProductosOrdenadosPorPrecio();
                        break;
                    case 6:
                        System.out.println("Saliendo...");
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } while (opcion != 6);
        } else {
            System.out.println("Autenticación fallida.");
        }

        scanner.close();
    }
}
