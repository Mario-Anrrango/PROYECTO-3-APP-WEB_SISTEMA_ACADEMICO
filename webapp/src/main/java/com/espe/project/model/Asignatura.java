package com.espe.project.model;

public class Asignatura {

    private Long id_asignatura;
    private Long id_carrera;
    private Long id_docente;
    private String nombre_asignatura;
    private int creditos;

    public Asignatura() {
    }

    public Asignatura(Long id_asignatura, Long id_carrera, Long id_docente, String nombre_asignatura,
            int creditos) {
        this.id_asignatura = id_asignatura;
        this.id_carrera = id_carrera;
        this.id_docente = id_docente;
        this.nombre_asignatura = nombre_asignatura;
        this.creditos = creditos;
    }

    public Long getId_asignatura() {
        return id_asignatura;
    }

    public Long getId_carrera() {
        return id_carrera;
    }

    public Long getId_docente() {
        return id_docente;
    }

    public String getNombre_asignatura() {
        return nombre_asignatura;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setId_asignatura(Long id_asignatura) {
        this.id_asignatura = id_asignatura;
    }

    public void setId_carrera(Long id_carrera) {
        this.id_carrera = id_carrera;
    }

    public void setId_docente(Long id_docente) {
        this.id_docente = id_docente;
    }

    public void setNombre_asignatura(String nombre_asignatura) {
        this.nombre_asignatura = nombre_asignatura;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }
    
}
