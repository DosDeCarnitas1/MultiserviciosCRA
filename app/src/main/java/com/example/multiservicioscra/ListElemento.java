package com.example.multiservicioscra;

public class ListElemento {
    public String titulo;
    public String descripcion;
    public String estado;
    public int id;

    public ListElemento(String titulo, String descripcion, String estado, int id) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estado = estado;
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    public int getId() {
        return id;
    };

    public void setId(int id) {
        this.id = id;
    }
}
