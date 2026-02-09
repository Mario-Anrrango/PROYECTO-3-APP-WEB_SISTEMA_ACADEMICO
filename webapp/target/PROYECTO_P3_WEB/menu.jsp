<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sistema Académico - Panel Principal</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link href="${pageContext.request.contextPath}/css/menu.css" rel="stylesheet">
</head>
<body>
    <div class="dashboard">
        <div class="dashboard-header">
            <h1 class="dashboard-title">Panel de Administración Académica</h1>
            <p class="dashboard-subtitle">
                Sistema integrado para la gestión de docentes, carreras y asignaturas.
                Administre toda la información académica desde un solo lugar.
            </p>
        </div>
        
        <!-- Módulos principales -->
        <div class="modules-grid">
            <a href="${pageContext.request.contextPath}/docentes" class="module-card module-docentes">
                <div class="module-icon">👨‍🏫</div>
                <h3 class="module-title">Gestión de Docentes</h3>
                <p class="module-description">
                    Administre toda la información personal, profesional y de contacto de los docentes.
                </p>
                <div class="module-actions">
                    <span class="btn btn-sm btn-success">Administrar →</span>
                </div>
            </a>
            
            <a href="${pageContext.request.contextPath}/carreras" class="module-card module-carreras">
                <div class="module-icon">🎓</div>
                <h3 class="module-title">Gestión de Carreras</h3>
                <p class="module-description">
                    Gestione carreras universitarias y toda su información relacionada.
                </p>
                <div class="module-actions">
                    <span class="btn btn-sm btn-primary">Ver carreras →</span>
                </div>
            </a>
            
            <a href="${pageContext.request.contextPath}/asignaturas" class="module-card module-asignaturas">
                <div class="module-icon">📚</div>
                <h3 class="module-title">Asignaturas y Materias</h3>
                <p class="module-description">
                    Administre las materias académicas, créditos, asignación
                    de docentes a cada asignatura del sistema.
                </p>
                <div class="module-actions">
                    <span class="btn btn-sm btn-warning">Gestionar →</span>
                </div>
            </a>
        </div>
        
        <!-- Acciones rápidas -->
        <div class="quick-actions">
            <h3 class="quick-actions-title">Acciones Rápidas</h3>
            <div class="actions-grid">
                <a href="${pageContext.request.contextPath}/docentes?action=new" class="action-btn">
                    <span class="action-btn-icon">➕</span>
                    <span class="action-btn-text">Registrar nuevo docente</span>
                </a>
                <a href="${pageContext.request.contextPath}/carreras?action=new" class="action-btn">
                    <span class="action-btn-icon">➕</span>
                    <span class="action-btn-text">Crear nueva carrera</span>
                </a>
                <a href="${pageContext.request.contextPath}/asignaturas?action=new" class="action-btn">
                    <span class="action-btn-icon">➕</span>
                    <span class="action-btn-text">Agregar asignatura</span>
                </a>
            </div>
        </div>
        
        <!-- Footer del dashboard -->
        <div class="dashboard-footer">
            <p>
                <strong>Sistema Académico v1.0</strong> • 
                Sesión activa: <span style="color: var(--success-color); font-weight: 500;">Administrador</span> • 
                Último acceso: <span id="currentDateTime"></span>
            </p>
            <p style="margin-top: 10px; font-size: 0.85rem; opacity: 0.8;">
                © 2026 Universidad ESPE. Todos los derechos reservados. 
            </p>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/js/menu.js"></script>
</body>
</html>