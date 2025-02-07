package com.mycompany.bodegaapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Bodega {
    private ArrayList<Producto> productos;

    public Bodega() {
        productos = Persistencia.cargarProductos();  // Cargar productos desde archivo
    }
    
    // Método para obtener la lista de productos
    public ArrayList<Producto> getProductos() {
        return productos;
    }

    // Método para buscar un producto por su código
    public Producto buscarProducto(String codigo) {
        for (Producto producto : productos) {
            if (producto.getCodigo().equals(codigo)) {
                return producto;  // Retorna el producto si encuentra el código
            }
        }
        return null;  // Si no encuentra el producto, retorna null
    }
        
    // Agregar un producto
    public void agregarProducto(Producto producto) {
        productos.add(producto);
        Persistencia.guardarProductos(productos);  // Guardar productos después de agregar
    }
    
    //Eliminar un producto
    public void eliminarProducto(Producto producto) {
        productos.remove(producto);  // Eliminar de la lista interna
        Persistencia.guardarProductos(productos);  // Guardar la lista actualizada en el archivo
    }

    // Modificar un producto
    public void modificarProducto(String codigo, String nuevoNombre, int nuevaCantidad, double nuevoPrecio) {
        for (Producto producto : productos) {
            if (producto.getCodigo().equals(codigo)) {
                producto.setNombre(nuevoNombre);
                producto.setCantidad(nuevaCantidad);
                producto.setPrecio(nuevoPrecio);
                break;  // Ya encontramos el producto, no es necesario seguir buscando
            }
        }
        // Guardar los productos después de la modificación
        Persistencia.guardarProductos(productos);
    }


    // Mostrar productos en orden ascendente por un atributo (como nombre)
    public void listarProductosOrdenadosPorNombre() {
        Collections.sort(productos, Comparator.comparing(Producto::getNombre));
        for (Producto producto : productos) {
            System.out.println(producto.mostrarInfo());
        }
    }

    // Mostrar productos en orden ascendente por otro atributo (como precio)
    public void listarProductosOrdenadosPorPrecio() {
        Collections.sort(productos, Comparator.comparingDouble(Producto::getPrecio));
        for (Producto producto : productos) {
            System.out.println(producto.mostrarInfo());
        }
    }
    
    public void listarProductos() {
        for (Producto producto : productos) {
            System.out.println(producto.mostrarInfo());
        }
    }

}
