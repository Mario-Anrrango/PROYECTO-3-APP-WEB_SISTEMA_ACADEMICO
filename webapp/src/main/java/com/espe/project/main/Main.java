package com.espe.project.main;

import java.util.List;

import com.espe.project.dao.AsignaturaDAO;
import com.espe.project.dao.CarreraDAO;
import com.espe.project.dao.DocenteDAO;
import com.espe.project.model.Asignatura;
import com.espe.project.model.Carrera;
import com.espe.project.model.Docente;

public class Main {
    public static void main(String[] args){
        System.out.println("Hola mundo");

        AsignaturaDAO asignaturaDAO = new AsignaturaDAO();
        DocenteDAO docenteDAO = new DocenteDAO();
        CarreraDAO carreraDAO = new CarreraDAO();
        //List<Asignatura> asignaturas = asiganturaDAO.findAll();
        //for (Asignatura asignatura : asignaturas){
        //    System.out.println(asignatura);
        //}

        //System.out.println("/----------------/");
        //System.out.println("Nuevo Docente:");
        //Docente docente1 = new Docente();
        //docente1.setId_docente(3L);
        //docente1.setNombre_docente("Julian");
        //docente1.setApellido_docente("Pazmiño");
        //docente1.setCorreo("jlpazmino@espe.edu.ec");
        //System.out.println("Docente nuevo: " + docenteDAO.createDocente(docente1));
        //System.out.println("/----------------/");

        System.out.println("/----------------/");
        System.out.println("Nueva carrera:");
        Carrera carrera1 = new Carrera();
        carrera1.setId_carrera(1L);
        carrera1.setNombre_carrera("Ingeniería de Software");
        System.out.println("Carrera nueva:" + carreraDAO.createCarrera(carrera1));
        System.out.println("/----------------/");

        System.out.println("/----------------/");
        System.out.println("Nueva Asignatura:");
        Asignatura asignatura1 = new Asignatura();
        asignatura1.setId_asignatura(1L);
        asignatura1.setId_carrera(1L);
        asignatura1.setId_docente(2L);
        asignatura1.setNombre_asignatura("Modelos de Procesos del desarrollo del Software");
        asignatura1.setCreditos(4);
        System.out.println("Asignatura nueva:" + asignaturaDAO.createAsignatura(asignatura1));

        //System.out.println("/----------------/");
        //System.out.println("Eliminar Docente:");
        //boolean deleted = docenteDAO.deleteDocente(5L);
        //System.out.println("Docente eliminado: " + deleted);

        //System.out.println("/----------------/");
        //System.out.println("Actualizar Docente:");
        //Docente docenteToUpdate = docenteDAO.findById(2L); 
        //if (docenteToUpdate != null) {
        //    docenteToUpdate.setNombre_docente("Rosalia");
        //    docenteToUpdate.setApellido_docente("Guamán");
        //    docenteToUpdate.setCorreo("rslguaman@espe.edu.ec");
        //    boolean updated = docenteDAO.updateDocente(docenteToUpdate);
        //    System.out.println("Docente actualizado: " + updated);
        //} else {
        //    System.out.println("No se encontró el docente para actualizar");
        //}
    }
}
