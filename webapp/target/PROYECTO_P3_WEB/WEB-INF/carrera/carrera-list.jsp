<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Carreras - Sistema Académico</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link href="${pageContext.request.contextPath}/css/carrera-list.css" rel="stylesheet">
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
                    <h1>🎓 Gestión de Carreras</h1>
                    <p class="form-hint">Administre todos los programas académicos del sistema</p>
                </div>
                
                <div class="header-actions">
                    <div class="search-box">
                        <span class="search-icon">🔍</span>
                        <input type="text" class="search-input" placeholder="Buscar carrera..." 
                               onkeyup="filterTable(this.value)">
                    </div>
                    <a href="${pageContext.request.contextPath}/carreras?action=new" 
                       class="btn btn-primary">
                        <span style="font-size: 1.2rem;">+</span> Nueva Carrera
                    </a>
                </div>
            </div>
            
            <!-- Resumen de estadísticas -->
            <!-- <div class="stats-summary">
                <div class="stat-item">
                    <div class="stat-value">${not empty carreras ? carreras.size() : 0}</div>
                    <div class="stat-label">Total Carreras</div>
                </div>
                <div class="stat-item">
                    <div class="stat-value">
                        <c:set var="totalAsignaturas" value="0" />
                        <c:forEach var="carrera" items="${carreras}"> -->
                            <!-- En una implementación real, obtendrías el conteo de asignaturas por carrera -->
            <!--                <c:set var="totalAsignaturas" value="${totalAsignaturas + 20}" />
                        </c:forEach>
                        ${totalAsignaturas}
                    </div>
                    <div class="stat-label">Asignaturas Totales</div>
                </div>
                <div class="stat-item">
                    <div class="stat-value">8</div>
                    <div class="stat-label">Semestres Promedio</div>
                </div>
                <div class="stat-item">
                    <div class="stat-value">4</div>
                    <div class="stat-label">Facultades</div>
                </div>
            </div> -->
            
            <!-- Tabla de carreras -->
            <c:choose>
                <c:when test="${not empty carreras && carreras.size() > 0}">
                    <div class="table-container">
                        <table class="table table-striped" id="carrerasTable">
                            <thead>
                                <tr>
                                    <th width="80">ID</th>
                                    <th>Carrera</th>
                                    <th width="180" class="text-center">Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="carrera" items="${carreras}">
                                    <tr>
                                        <td>
                                            <span class="badge badge-primary">${carrera.id_carrera}</span>
                                        </td>
                                        <td>
                                            <div class="career-info">
                                                <div class="career-icon">🎓</div>
                                                <div class="career-details">
                                                    <div class="career-name">${carrera.nombre_carrera}</div>
                                                    <div class="career-id">ID: ${carrera.id_carrera}</div>
                                                </div>
                                            </div>
                                        </td>
                                        <td>
                                            <div class="action-buttons">
                                                <a href="${pageContext.request.contextPath}/carreras?action=edit&id=${carrera.id_carrera}" 
                                                   class="btn btn-sm btn-primary btn-icon" title="Editar">
                                                    ✏️
                                                </a>
                                                <a href="${pageContext.request.contextPath}/carreras?action=delete&id=${carrera.id_carrera}" 
                                                    class="btn btn-sm btn-danger btn-icon delete-btn"
                                                    data-id="${carrera.id_carrera}"
                                                    data-nombre="${fn:escapeXml(carrera.nombre_carrera)}"
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
                            Mostrando <strong>${carreras.size()}</strong> carreras
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
                        <div class="empty-state-icon">🎓</div>
                        <h3 class="empty-state-title">No hay carreras registradas</h3>
                        <p class="empty-state-description">
                            Comience agregando carreras al sistema académico. 
                            Las carreras son los programas de estudio que ofrece la institución.
                        </p>
                        <a href="${pageContext.request.contextPath}/carreras?action=new" 
                           class="btn btn-primary btn-lg">
                            <span style="font-size: 1.2rem;">+</span> Crear primera carrera
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

    <script src="${pageContext.request.contextPath}/js/carrera-list.js"></script>
</body>
</html>