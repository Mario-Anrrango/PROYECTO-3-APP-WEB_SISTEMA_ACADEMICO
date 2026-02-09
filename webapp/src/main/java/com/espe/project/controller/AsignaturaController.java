package com.espe.project.controller;

import com.espe.project.dao.AsignaturaDAO;
import com.espe.project.dao.DocenteDAO;
import com.espe.project.dao.CarreraDAO;
import com.espe.project.model.Asignatura;
import com.espe.project.model.Docente;
import com.espe.project.utils.Validaciones;
import com.espe.project.model.Carrera;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/asignaturas")
public class AsignaturaController extends HttpServlet {

    private final AsignaturaDAO asignaturaDAO = new AsignaturaDAO();
    private final DocenteDAO docenteDAO = new DocenteDAO();
    private final CarreraDAO carreraDAO = new CarreraDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        String action = req.getParameter("action");
        if (action == null || action.isBlank()) action = "list";

        try {
            switch (action) {
                case "new" -> showForm(req, resp, new Asignatura(), "create");
                case "edit" -> showEdit(req, resp);
                case "delete" -> doDeleteById(req, resp);
                default -> doList(req, resp);
            }
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        String action = req.getParameter("action");
        if (action == null) action = "";

        try {
            switch (action) {
                case "create" -> doCreate(req, resp);
                case "update" -> doUpdate(req, resp);
                default -> resp.sendRedirect(req.getContextPath() + "/asignaturas");
            }
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, resp);
        }
    }

private void doList(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
    System.out.println("=== EJECUTANDO doList de Asignaturas ===");
    
    try {
        // 1. Obtener asignaturas
        List<Asignatura> asignaturas = asignaturaDAO.findAll();
        System.out.println("Asignaturas encontradas: " + (asignaturas != null ? asignaturas.size() : 0));
        
        // 2. Obtener docentes y carreras
        List<Docente> docentes = docenteDAO.findAll();
        List<Carrera> carreras = carreraDAO.findAll();
        
        System.out.println("Docentes encontrados: " + (docentes != null ? docentes.size() : 0));
        System.out.println("Carreras encontradas: " + (carreras != null ? carreras.size() : 0));
        
        // Pasar todo a la vista - Asegurarse que no sean null
        if (asignaturas == null) {
            asignaturas = new ArrayList<>();
        }
        if (docentes == null) {
            docentes = new ArrayList<>();
        }
        if (carreras == null) {
            carreras = new ArrayList<>();
        }
        
        req.setAttribute("asignaturas", asignaturas);
        req.setAttribute("docentes", docentes);
        req.setAttribute("carreras", carreras);
        
        System.out.println("Redirigiendo a asignatura-list.jsp");
        req.getRequestDispatcher("/WEB-INF/asignatura/asignatura-list.jsp").forward(req, response);            
    } catch (Exception e) {
        System.out.println("ERROR en doList: " + e.getMessage());
        e.printStackTrace();
        
        req.setAttribute("error", "Error al cargar asignaturas: " + e.getMessage());
        req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, response);
    }
}

    private void showEdit(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        try {
            long id = Long.parseLong(req.getParameter("id"));
            Asignatura asignatura = asignaturaDAO.findById(id);
            if (asignatura == null) {
                req.setAttribute("error", "No se encontró la asignatura con ID " + id);
                req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, response);
            return;
            }
            cargarDatosParaFormulario(req);
            showForm(req, response, asignatura, "update");
        } catch (NumberFormatException e) {
            req.setAttribute("error", "ID inválido");
            req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, response);
        } catch (Exception e) {
            req.setAttribute("error", "Error al buscar asignatura: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, response);
        }
    }

    private void showForm(HttpServletRequest req, HttpServletResponse response, Asignatura asignatura, String action) throws ServletException, IOException {
        cargarDatosParaFormulario(req);
        
        req.setAttribute("asignatura", asignatura);
        req.setAttribute("formAction", action);
        req.getRequestDispatcher("/WEB-INF/asignatura/asignatura-form.jsp").forward(req, response);
    }

    private void cargarDatosParaFormulario(HttpServletRequest req) {
        // Cargar listas de docentes y carreras para los selects
        List<Docente> docentes = docenteDAO.findAll();
        List<Carrera> carreras = carreraDAO.findAll();
        
        req.setAttribute("docentes", docentes);
        req.setAttribute("carreras", carreras);
    }

    private void doCreate(HttpServletRequest req, HttpServletResponse response) throws IOException, ServletException {
        try {
            Asignatura asignatura = parseAsignatura(req, false);
            asignaturaDAO.createAsignatura(asignatura);
            req.getSession().setAttribute("successMessage", "Asignatura creada exitosamente!");
            response.sendRedirect(req.getContextPath() + "/asignaturas");
        } catch (ServletException e) {
            req.setAttribute("error", e.getMessage());
            showForm(req, response, parseAsignaturaFromRequest(req), "create");
        }

    }

    private void doUpdate(HttpServletRequest req, HttpServletResponse response) throws IOException, ServletException {
        try {
            Asignatura asignatura = parseAsignatura(req, true);
            asignaturaDAO.updateAsignatura(asignatura);
            req.getSession().setAttribute("successMessage", "Asignatura actualizada exitosamente!");
            response.sendRedirect(req.getContextPath() + "/asignaturas");  
        } catch (ServletException e) {
            req.setAttribute("error", e.getMessage());
            showForm(req, response, parseAsignatura(req,true), "update");
        }

    }

    private void doDeleteById(HttpServletRequest req, HttpServletResponse response) throws IOException {
        try {
            long id = Long.parseLong(req.getParameter("id"));
            asignaturaDAO.deleteAsignatura(id);
            req.getSession().setAttribute("successMessage", "Asignatura eliminada exitosamente!");
            response.sendRedirect(req.getContextPath() + "/asignaturas");
        } catch (Exception e) {
            req.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(req.getContextPath() + "/carreras");
        }

    }

    private Asignatura parseAsignatura(HttpServletRequest req, boolean withId) throws ServletException {
    try {
        String idStr = req.getParameter("id_asignatura");
        String nombre = req.getParameter("nombre_asignatura");
        String creditosStr = req.getParameter("creditos");
        String idDocenteStr = req.getParameter("id_docente");
        String idCarreraStr = req.getParameter("id_carrera");

        // Validaciones básicas
        if (idStr == null || idStr.isBlank()) throw new ServletException("ID de asignatura requerido");
        if (nombre == null || nombre.isBlank()) throw new ServletException("Nombre de asignatura requerido");
        if (creditosStr == null || creditosStr.isBlank()) throw new ServletException("Créditos requeridos");
        if (idDocenteStr == null || idDocenteStr.isBlank()) throw new ServletException("Docente requerido");
        if (idCarreraStr == null || idCarreraStr.isBlank()) throw new ServletException("Carrera requerida");

        Long id;
        Long idDocente;
        Long idCarrera;
        Integer creditos;
        
        try {
            id = Long.parseLong(idStr);
            creditos = Integer.parseInt(creditosStr);
            idDocente = Long.parseLong(idDocenteStr);
            idCarrera = Long.parseLong(idCarreraStr);
        } catch (NumberFormatException e) {
            throw new ServletException("Formato numérico inválido en uno de los campos");
        }

        // Validaciones usando Validaciones
        Validaciones.validateId(id, "asignatura");
        Validaciones.validateName(nombre, "Nombre de asignatura");
        Validaciones.validateCredits(creditos);
        Validaciones.validateId(idDocente, "docente");
        Validaciones.validateId(idCarrera, "carrera");
        
        // Verificar si el ID ya existe (solo para creación)
        if (!withId) {
            Asignatura existente = asignaturaDAO.findById(id);
            if (existente != null) {
                throw new ServletException("El ID " + id + " ya existe. Use otro ID.");
            }
        }

        // Verificar que el docente y carrera existan
        if (docenteDAO.findById(idDocente) == null) {
            throw new ServletException("El docente seleccionado no existe");
        }
        if (carreraDAO.findById(idCarrera) == null) {
            throw new ServletException("La carrera seleccionada no existe");
        }

        Asignatura asignatura = new Asignatura();
        asignatura.setId_asignatura(id);
        asignatura.setNombre_asignatura(nombre.trim());
        asignatura.setCreditos(creditos);
        asignatura.setId_docente(idDocente);
        asignatura.setId_carrera(idCarrera);

        return asignatura;
    } catch (IllegalArgumentException e) {
        throw new ServletException(e.getMessage());
    }
    }

        private Asignatura parseAsignaturaFromRequest(HttpServletRequest req) {
        Asignatura asignatura = new Asignatura();
        try {
            if (req.getParameter("id_asignatura") != null) {
                asignatura.setId_asignatura(Long.parseLong(req.getParameter("id_asignatura")));
            }
            asignatura.setNombre_asignatura(req.getParameter("nombre_asignatura"));
            asignatura.setCreditos(Integer.parseInt(req.getParameter("creditos")));
            asignatura.setId_docente(Long.parseLong(req.getParameter("id_docente")));
            asignatura.setId_carrera(Long.parseLong(req.getParameter("id_carrera")));
        } catch (Exception e) {
            // Ignorar errores
        }
        return asignatura;
    }
}