package com.espe.project.controller;

import com.espe.project.dao.CarreraDAO;
import com.espe.project.model.Carrera;
import com.espe.project.utils.Validaciones;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/carreras")
public class CarreraController extends HttpServlet {

    private final CarreraDAO carreraDAO = new CarreraDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        String action = req.getParameter("action");
        if (action == null || action.isBlank()) action = "list";

        try {
            switch (action) {
                case "new" -> showForm(req, resp, new Carrera(), "create");
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
                default -> resp.sendRedirect(req.getContextPath() + "/carreras");
            }
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, resp);
        }
    }

    private void doList(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Carrera> carreras = carreraDAO.findAll();
            req.setAttribute("carreras", carreras);
            req.getRequestDispatcher("/WEB-INF/carrera/carrera-list.jsp").forward(req, response);
        } catch (Exception e) {
            req.setAttribute("error", "Error al cargar la lista de carreras: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, response);
        }
        
    }

    private void showEdit(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        try {
            long id = Long.parseLong(req.getParameter("id"));
            Carrera carrera = carreraDAO.findById(id);
            if (carrera == null) {
                req.setAttribute("error", "No se encontró la carrera con ID " + id);
                req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, response);
            return;
            }
            showForm(req, response, carrera, "update");
        } catch (NumberFormatException e) {
            req.setAttribute("error", "ID inválido");
            req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, response);
        } catch (Exception e) {
            req.setAttribute("error", "Error al buscar carrera: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, response);
        }
    }

    private void showForm(HttpServletRequest req, HttpServletResponse response, Carrera carrera, String action) throws ServletException, IOException {
        req.setAttribute("carrera", carrera);
        req.setAttribute("formAction", action);
        req.getRequestDispatcher("/WEB-INF/carrera/carrera-form.jsp").forward(req, response);
    }

    private void doCreate(HttpServletRequest req, HttpServletResponse response) throws IOException, ServletException {
        try {
            Carrera carrera = parseCarrera(req, false);
            carreraDAO.createCarrera(carrera);
            req.getSession().setAttribute("successMessage", "Carrera creada exitosamente!");
            response.sendRedirect(req.getContextPath() + "/carreras");
        } catch (ServletException e) {
            req.setAttribute("error", e.getMessage());
            showForm(req, response, parseCarreraFromRequest(req), "create");
        }
    }

    private void doUpdate(HttpServletRequest req, HttpServletResponse response) throws IOException, ServletException {
        try {
            Carrera carrera = parseCarrera(req, true);
            carreraDAO.updateCarrera(carrera);
            req.getSession().setAttribute("successMessage", "Carrera actualizada exitosamente!");
            response.sendRedirect(req.getContextPath() + "/carreras");    
        } catch (ServletException e) {
            req.setAttribute("error", e.getMessage());
            showForm(req, response, parseCarrera(req, true), "update");
        }
    }

    private void doDeleteById(HttpServletRequest req, HttpServletResponse response) throws IOException {
        try {
            long id = Long.parseLong(req.getParameter("id"));
            carreraDAO.deleteCarrera(id);
            req.getSession().setAttribute("successMessage", "Carrera eliminada exitosamente!");
            response.sendRedirect(req.getContextPath() + "/carreras");    
        } catch (Exception e) {
            req.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(req.getContextPath() + "/carreras");
        }
        
    }

    private Carrera parseCarrera(HttpServletRequest req, boolean withId) throws ServletException {
    try {
        String idStr = req.getParameter("id_carrera");
        String nombre = req.getParameter("nombre_carrera");

        // Validaciones
        if (idStr == null || idStr.isBlank()) {
            throw new ServletException("ID de carrera requerido");
        }
        
        Long id;
        try {
            id = Long.parseLong(idStr);
        } catch (NumberFormatException e) {
            throw new ServletException("ID debe ser un número válido");
        }
        
        // Validar ID
        Validaciones.validateId(id, "carrera");
        
        // Validar nombre de carrera
        Validaciones.validateName(nombre, "Nombre de carrera");
        
        // Verificar si el ID ya existe (solo para creación)
        if (!withId) {
            Carrera existente = carreraDAO.findById(id);
            if (existente != null) {
                throw new ServletException("El ID " + id + " ya existe. Use otro ID.");
            }
        }

        Carrera carrera = new Carrera();
        carrera.setId_carrera(id);
        carrera.setNombre_carrera(nombre.trim());

        return carrera;
    } catch (IllegalArgumentException e) {
        throw new ServletException(e.getMessage());
    }
    }

    private Carrera parseCarreraFromRequest(HttpServletRequest req) {
        Carrera carrera = new Carrera();
        try {
            if (req.getParameter("id_carrera") != null) {
                carrera.setId_carrera(Long.parseLong(req.getParameter("id_carrera")));
            }
            carrera.setNombre_carrera(req.getParameter("nombre_carrera"));
        } catch (Exception e) {
            // Ignorar errores
        }
        return carrera;
    }
}