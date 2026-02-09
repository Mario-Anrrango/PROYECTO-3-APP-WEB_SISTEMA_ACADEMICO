package com.espe.project.utils;

import java.util.regex.Pattern;

public class Validaciones {
    
    // Expresiones regulares para validaciones
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s]{2,50}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern SUBJECT_NAME_PATTERN = Pattern.compile("^[A-Za-zÁÉÍÓÚáéíóúÑñ0-9\\s.,-]{3,100}$");
    private static final Pattern CAREER_NAME_PATTERN = Pattern.compile("^[A-Za-zÁÉÍÓÚáéíóúÑñ\\s.,-]{3,100}$");
    
    // Métodos de validación
    public static void validateName(String name, String fieldName) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " no puede estar vacío");
        }
        if (!NAME_PATTERN.matcher(name).matches()) {
            throw new IllegalArgumentException(fieldName + " debe contener solo letras y espacios (2-50 caracteres)");
        }
    }
    
    public static void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo electrónico no puede estar vacío");
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Formato de correo electrónico inválido");
        }
        if (email.length() > 100) {
            throw new IllegalArgumentException("El correo electrónico no puede exceder 100 caracteres");
        }
    }
    
    public static void validateSubjectName(String subjectName) {
        if (subjectName == null || subjectName.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la asignatura no puede estar vacío");
        }
        if (!SUBJECT_NAME_PATTERN.matcher(subjectName).matches()) {
            throw new IllegalArgumentException("Nombre de asignatura inválido. Use solo letras, números y espacios (3-100 caracteres)");
        }
    }
    
    public static void validateCareerName(String careerName) {
        if (careerName == null || careerName.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la carrera no puede estar vacío");
        }
        if (!CAREER_NAME_PATTERN.matcher(careerName).matches()) {
            throw new IllegalArgumentException("Nombre de carrera inválido. Use solo letras y espacios (3-100 caracteres)");
        }
    }
    
    public static void validateDescription(String description) {
        if (description != null && description.length() > 500) {
            throw new IllegalArgumentException("La descripción no puede exceder 500 caracteres");
        }
    }
    
    public static void validateCredits(Integer credits) {
        if (credits == null) {
            throw new IllegalArgumentException("Los créditos son requeridos");
        }
        if (credits < 1 || credits > 10) {
            throw new IllegalArgumentException("Los créditos deben estar entre 1 y 10");
        }
    }
    
    public static void validateId(Long id, String entityName) {
        if (id == null) {
            throw new IllegalArgumentException("ID de " + entityName + " es requerido");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("ID de " + entityName + " debe ser un número positivo");
        }
        if (id > 999999) {
            throw new IllegalArgumentException("ID de " + entityName + " no puede ser mayor a 999999");
        }
    }
    
    public static void validatePhone(String phone) {
        if (phone != null && !phone.trim().isEmpty()) {
            if (!phone.matches("^[0-9]{7,15}$")) {
                throw new IllegalArgumentException("Formato de teléfono inválido. Use solo números (7-15 dígitos)");
            }
        }
    }
}