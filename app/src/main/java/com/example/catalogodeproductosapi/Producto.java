package com.example.catalogodeproductosapi;

public class Producto {
    private final String nombre, categoria, imagen;
    private final double precio, rating;

    public Producto(String nombre, String categoria, String imagen, double precio, double rating) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.imagen = imagen;
        this.precio = precio;
        this.rating = rating;
    }
    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public String getImagen() { return imagen; }
    public double getPrecio() { return precio; }
    public double getRating() { return rating; }
}
