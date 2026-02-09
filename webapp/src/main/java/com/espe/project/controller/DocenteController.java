package com.espe.project.controller;

import com.espe.project.dao.DocenteDAO;
import com.espe.project.model.Docente;
import com.espe.project.utils.Validaciones;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/docentes")
public class DocenteController extends HttpServlet {

    private final DocenteDAO docenteDAO = new DocenteDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");        

        String action = req.getParameter("action");
        if (action == null || action.isBlank()) action = "list";

        try {
            switch (action) {
                case "new" -> showForm(req, resp, new Docente(), "create");
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
                default -> resp.sendRedirect(req.getContextPath() + "/docentes");
            }
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, resp);
        }
    }

    private void doList(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Docente> docentes = docenteDAO.findAll();
            req.setAttribute("docentes", docentes);
            req.getRequestDispatcher("/WEB-INF/docente/docente-list.jsp").forward(req, response);
        } catch (Exception e) {
            req.setAttribute("error", "Error al cargar la lista de docentes: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, response);

        }
        
    }

    private void showEdit(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        
        try {
            long id = Long.parseLong(req.getParameter("id"));
            Docente docente = docenteDAO.findById(id);
            if (docente == null) {
                req.setAttribute("error", "No se encontró el docente con ID " + id);
                req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, response);
                return;
            }
            showForm(req, response, docente, "update");
        } catch (NumberFormatException e) {
            req.setAttribute("error", "ID inválido");
            req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, response);
        } catch (Exception e) {
            req.setAttribute("error", "Error al buscar docente: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, response);
        }
    }

    private void showForm(HttpServletRequest req, HttpServletResponse response, Docente docente, String action) throws ServletException, IOException {
        req.setAttribute("docente", docente);
        req.setAttribute("formAction", action);
        req.getRequestDispatcher("/WEB-INF/docente/docente-form.jsp").forward(req, response);
    }

private void doCreate(HttpServletRequest req, HttpServletResponse response) throws IOException, ServletException {
    try {
        Docente docente = parseDocente(req, false);
        docenteDAO.createDocente(docente);
        req.getSession().setAttribute("successMessage", "Docente creado exitosamente!");
        response.sendRedirect(req.getContextPath() + "/docentes");
    } catch (ServletException e) {
        // AGREGAR LOG PARA DEPURAR
        System.out.println("Error en doCreate: " + e.getMessage());
        e.printStackTrace();
        
        req.setAttribute("error", e.getMessage());
        
        // Crea un docente temporal con los datos del formulario
        Docente tempDocente = parseDocenteFromRequest(req);
        showForm(req, response, tempDocente, "create");
    }
}

    private void doUpdate(HttpServletRequest req, HttpServletResponse response) throws IOException, ServletException {
        try {
            Docente docente = parseDocente(req, true);
            docenteDAO.updateDocente(docente);
            req.getSession().setAttribute("successMessage", "Docente actualizado exitosamente!");
            response.sendRedirect(req.getContextPath() + "/docentes");
        } catch (ServletException e) {
            req.setAttribute("error", e.getMessage());
            showForm(req, response, parseDocenteFromRequest(req), "update");
        }
 
    }

    private void doDeleteById(HttpServletRequest req, HttpServletResponse response) throws IOException {
        try {
            long id = Long.parseLong(req.getParameter("id"));
            docenteDAO.deleteDocente(id);
            req.getSession().setAttribute("successMessage", "Docente eliminado exitosamente!");
            response.sendRedirect(req.getContextPath() + "/docentes");
        } catch (Exception e) {
            req.getSession().setAttribute("errorMessage", e.getMessage());
            response.sendRedirect(req.getContextPath() + "/docentes");
        }    
        
    }

    private Docente parseDocente(HttpServletRequest req, boolean withId) throws ServletException {
        try {
            String idStr = req.getParameter("id_docente");
            String nombre = req.getParameter("nombre_docente");
            String apellido = req.getParameter("apellido_docente");
            String correo = req.getParameter("correo");

            // Validaciones
            if (idStr == null || idStr.isBlank()) {
                throw new ServletException("ID de docente requerido");
            }
            
            Long id;
            try {
                id = Long.parseLong(idStr);
            } catch (NumberFormatException e) {
                throw new ServletException("ID debe ser un número válido");
            }
            
            // Validar ID
            Validaciones.validateId(id, "docente");
            
            // Validar nombre y apellido
            Validaciones.validateName(nombre, "Nombre");
            Validaciones.validateName(apellido, "Apellido");
            
            // Validar correo
            Validaciones.validateEmail(correo);
            
            // Verificar si el ID ya existe (solo para creación)
            if (!withId) {
                Docente existente = docenteDAO.findById(id);
                if (existente != null) {
                    throw new ServletException("El ID " + id + " ya existe. Use otro ID.");
                }
            }

            Docente docente = new Docente();
            docente.setId_docente(id);
            docente.setNombre_docente(nombre.trim());
            docente.setApellido_docente(apellido.trim());
            docente.setCorreo(correo.trim());

            return docente;
        } catch (IllegalArgumentException e) {
            throw new ServletException(e.getMessage());
        }
    }

    private Docente parseDocenteFromRequest(HttpServletRequest req) {
        Docente docente = new Docente();
        try {
            if (req.getParameter("id_docente") != null) {
                docente.setId_docente(Long.parseLong(req.getParameter("id_docente")));
            }
            docente.setNombre_docente(req.getParameter("nombre_docente"));
            docente.setApellido_docente(req.getParameter("apellido_docente"));
            docente.setCorreo(req.getParameter("correo"));
        } catch (Exception e) {
            // Ignorar errores
        }
        return docente;
    }
}