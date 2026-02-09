package com.espe.project.utils;

public class Errores {
    
    public static String getFriendlyErrorMessage(Throwable e) {
        if (e == null) return "Error desconocido";
        
        String message = e.getMessage();
        if (message == null) return "Ha ocurrido un error inesperado";
        
        String msgLower = message.toLowerCase();
        
        // Errores de base de datos
        if (msgLower.contains("duplicate entry") || 
            msgLower.contains("ya existe") ||
            msgLower.contains("unique constraint")) {
            return "El registro ya existe. Por favor, verifique los datos.";
        }
        
        if (msgLower.contains("foreign key constraint") ||
            msgLower.contains("integrity constraint")) {
            return "No se puede realizar esta operación porque hay registros relacionados.";
        }
        
        if (msgLower.contains("data too long")) {
            return "Los datos ingresados son demasiado largos.";
        }
        
        if (msgLower.contains("cannot be null")) {
            return "Todos los campos son requeridos.";
        }
        
        if (msgLower.contains("connection") || 
            msgLower.contains("timeout") ||
            msgLower.contains("communications link failure")) {
            return "Error de conexión con la base de datos. Intente nuevamente.";
        }
        
        // Errores de validación
        if (e instanceof NumberFormatException) {
            return "Formato numérico incorrecto. Ingrese un número válido.";
        }
        
        // Errores de SQL
        if (msgLower.contains("sql syntax")) {
            return "Error interno en la base de datos.";
        }
        
        return "Error: " + getSimpleMessage(message);
    }
    
    private static String getSimpleMessage(String fullMessage) {
        if (fullMessage == null) return "";
        
        // Extraer solo la parte relevante del mensaje
        if (fullMessage.contains("SQLException:")) {
            int start = fullMessage.indexOf("SQLException:") + 13;
            return fullMessage.substring(start).trim();
        }
        
        if (fullMessage.contains("Exception:")) {
            int start = fullMessage.indexOf("Exception:") + 10;
            return fullMessage.substring(start).trim();
        }
        
        // Limitar longitud
        if (fullMessage.length() > 100) {
            return fullMessage.substring(0, 100) + "...";
        }
        
        return fullMessage;
    }
}
