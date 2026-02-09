package com.espe.project.model;

public class Docente {

    private Long id_docente;
    private String nombre_docente;
    private String apellido_docente;
    private String correo;

    public Docente() {
    }

    public Docente(Long id_docente, String nombre_docente, String apellido_docente, String correo) {
        this.id_docente = id_docente;
        this.nombre_docente = nombre_docente;
        this.apellido_docente = apellido_docente;
        this.correo = correo;
    }

    public Long getId_docente() {
        return id_docente;
    }

    public String getNombre_docente() {
        return nombre_docente;
    }

    public String getApellido_docente() {
        return apellido_docente;
    }

    public String getCorreo() {
        return correo;
    }

    public void setId_docente(Long id_docente) {
        this.id_docente = id_docente;
    }

    public void setNombre_docente(String nombre_docente) {
        this.nombre_docente = nombre_docente;
    }

    public void setApellido_docente(String apellido_docente) {
        this.apellido_docente = apellido_docente;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
}
