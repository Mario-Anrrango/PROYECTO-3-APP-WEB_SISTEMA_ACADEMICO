<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Docentes - Sistema Académico</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link href="${pageContext.request.contextPath}/css/docente-list.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <!-- Mensajes de éxito/error -->
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success">
                <span style="font-size: 1.2rem;">✅</span>
                <span>${successMessage}</span>
            </div>
            <c:remove var="successMessage" scope="session"/>
        </c:if>
        
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">
                <span style="font-size: 1.2rem;">❌</span>
                <span>${errorMessage}</span>
            </div>
            <c:remove var="errorMessage" scope="session"/>
        </c:if>

        <div class="content-wrapper">
            <!-- Header de la página -->
            <div class="page-header">
                <div>
                    <h1>👨‍🏫 Gestión de Docentes</h1>
                    <p class="form-hint">Administre todos los docentes del sistema académico</p>
                </div>
                
                <div class="header-actions">
                    <div class="search-box">
                        <span class="search-icon">🔍</span>
                        <input type="text" class="search-input" placeholder="Buscar docente..." 
                               onkeyup="filterTable(this.value)">
                    </div>
                    <a href="${pageContext.request.contextPath}/docentes?action=new" 
                       class="btn btn-success">
                        <span style="font-size: 1.2rem;">+</span> Nuevo Docente
                    </a>
                </div>
            </div>
            
            <!-- Tabla de docentes -->
            <c:choose>
                <c:when test="${not empty docentes && docentes.size() > 0}">
                    <div class="table-container">
                        <table class="table table-striped" id="docentesTable">
                            <thead>
                                <tr>
                                    <th width="80">ID</th>
                                    <th>Docente</th>
                                    <th>Apellido</th>
                                    <th>Correo Electrónico</th>
                                    <th width="180" class="text-center">Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="docente" items="${docentes}">
                                    <tr>
                                        <td>
                                            <span class="badge badge-primary">${docente.id_docente}</span>
                                        </td>
                                        <td>
                                            <div class="teacher-info">
                                                <div class="teacher-avatar">
                                                    ${fn:substring(docente.nombre_docente, 0, 1)}${fn:substring(docente.apellido_docente, 0, 1)}
                                                </div>
                                                <div class="teacher-details">
                                                    <div class="teacher-name">${docente.nombre_docente}</div>
                                                    <div class="teacher-id">ID: ${docente.id_docente}</div>
                                                </div>
                                            </div>
                                        </td>
                                        <td>
                                            <strong>${docente.apellido_docente}</strong>
                                        </td>
                                        <td>
                                            <div class="email-info">
                                                <span class="email-icon">✉️</span>
                                                <span class="email-address">${docente.correo}</span>
                                            </div>
                                        </td>
                                        <td>
                                            <div class="action-buttons">
                                                <a href="${pageContext.request.contextPath}/docentes?action=edit&id=${docente.id_docente}" 
                                                   class="btn btn-sm btn-primary btn-icon" title="Editar">
                                                    ✏️
                                                </a>
                                                <a href="${pageContext.request.contextPath}/docentes?action=delete&id=${docente.id_docente}" 
                                                    class="btn btn-sm btn-danger btn-icon delete-btn"
                                                    data-id="${docente.id_docente}"
                                                    data-nombre="${fn:escapeXml(docente.nombre_docente)}"
                                                    data-apellido="${fn:escapeXml(docente.apellido_docente)}"
                                                    title="Eliminar">
                                                        🗑️
                                                </a>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    
                    <!-- Información del pie -->
                    <div class="d-flex justify-content-between align-items-center mt-3">
                        <div style="color: var(--gray-color); font-size: 0.9rem;">
                            Mostrando <strong>${docentes.size()}</strong> docentes
                        </div>
                        <div>
                            <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">
                                ← Volver al inicio
                            </a>
                        </div>
                    </div>
                </c:when>
                
                <c:otherwise>
                    <!-- Estado vacío -->
                    <div class="empty-state">
                        <div class="empty-state-icon">👨‍🏫</div>
                        <h3 class="empty-state-title">No hay docentes registrados</h3>
                        <p class="empty-state-description">
                            Comience agregando docentes al sistema académico. 
                            Los docentes son los profesionales que imparten las asignaturas.
                        </p>
                        <a href="${pageContext.request.contextPath}/docentes?action=new" 
                           class="btn btn-success btn-lg">
                            <span style="font-size: 1.2rem;">+</span> Registrar primer docente
                        </a>
                        <br>
                        <br>
                        <div>
                            <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">
                                ← Volver al inicio
                            </a>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/js/docente-list.js"></script>
</body>
</html>