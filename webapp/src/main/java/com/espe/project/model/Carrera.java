package com.espe.project.model;

public class Carrera {
    private Long id_carrera;
    private String nombre_carrera;
    
    public Carrera() {
    }

    public Carrera(Long id_carrera, String nombre_carrera) {
        this.id_carrera = id_carrera;
        this.nombre_carrera = nombre_carrera;
    }

    public Long getId_carrera() {
        return id_carrera;
    }

    public String getNombre_carrera() {
        return nombre_carrera;
    }

    public void setId_carrera(Long id_carrera) {
        this.id_carrera = id_carrera;
    }

    public void setNombre_carrera(String nombre_carrera) {
        this.nombre_carrera = nombre_carrera;
    }

}
