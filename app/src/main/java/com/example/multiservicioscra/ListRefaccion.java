package com.example.multiservicioscra;

public class ListRefaccion {
    public String nombre;

    public int cantidad;

    public int id;

    public ListRefaccion(String nombre, int cantidad, int id){
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
