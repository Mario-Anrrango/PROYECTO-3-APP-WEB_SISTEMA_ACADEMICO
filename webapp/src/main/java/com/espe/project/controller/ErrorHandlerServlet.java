package com.espe.project.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/error")
public class ErrorHandlerServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleError(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleError(req, resp);
    }
    
    private void handleError(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Obtener información del error
        Throwable throwable = (Throwable) req.getAttribute("jakarta.servlet.error.exception");
        Integer statusCode = (Integer) req.getAttribute("jakarta.servlet.error.status_code");
        String servletName = (String) req.getAttribute("jakarta.servlet.error.servlet_name");
        String requestUri = (String) req.getAttribute("jakarta.servlet.error.request_uri");
        
        // Mensaje amigable por defecto
        String errorMessage = "Ha ocurrido un error inesperado.";
        String errorDetails = "";
        
        if (throwable != null) {
            errorDetails = throwable.getMessage();
            
            // Mensajes personalizados según el tipo de error
            if (throwable instanceof ServletException) {
                errorMessage = throwable.getMessage();
            } else if (throwable.getMessage() != null) {
                String msg = throwable.getMessage().toLowerCase();
                
                if (msg.contains("duplicate") || msg.contains("ya existe")) {
                    errorMessage = "El ID ingresado ya existe. Por favor, use otro ID.";
                } else if (msg.contains("foreign key") || msg.contains("integrity constraint")) {
                    errorMessage = "No se puede realizar esta operación porque hay registros relacionados.";
                } else if (msg.contains("data too long")) {
                    errorMessage = "Los datos ingresados son demasiado largos.";
                } else if (msg.contains("incorrect") || msg.contains("numberformat")) {
                    errorMessage = "Formato de datos incorrecto. Verifique los valores ingresados.";
                } else if (msg.contains("connection") || msg.contains("timeout")) {
                    errorMessage = "Error de conexión con la base de datos. Intente nuevamente.";
                } else if (msg.contains("cannot be null")) {
                    errorMessage = "Todos los campos requeridos deben ser completados.";
                }
            }
        }
        
        // Para errores 404
        if (statusCode != null && statusCode == 404) {
            errorMessage = "La página solicitada no fue encontrada.";
        }
        
        // Pasar información a la página de error
        req.setAttribute("errorMessage", errorMessage);
        req.setAttribute("errorDetails", errorDetails);
        req.setAttribute("statusCode", statusCode);
        req.setAttribute("requestUri", requestUri);
        
        // Redirigir a la página de error
        req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, resp);
    }
}