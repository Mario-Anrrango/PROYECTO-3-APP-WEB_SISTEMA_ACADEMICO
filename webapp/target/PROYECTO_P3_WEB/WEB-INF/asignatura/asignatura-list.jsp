<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Asignaturas - Sistema Académico</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/asignatura-list.css">
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
                    <h1>📚 Gestión de Asignaturas</h1>
                    <p class="form-hint">Administre todas las asignaturas del sistema académico</p>
                </div>
                
                <div class="header-actions">
                    <div class="search-box">
                        <span class="search-icon">🔍</span>
                        <input type="text" class="search-input" placeholder="Buscar asignatura..." 
                               onkeyup="filterTable(this.value)">
                    </div>
                    <a href="${pageContext.request.contextPath}/asignaturas?action=new" 
                       class="btn btn-success">
                        <span style="font-size: 1.2rem;">+</span> Nueva Asignatura
                    </a>
                </div>
            </div>
            
            <!-- Tabla de asignaturas -->
            <c:choose>
                <c:when test="${not empty asignaturas && !asignaturas.isEmpty()}">
                    <div class="table-container">
                        <table class="table table-striped" id="asignaturasTable">
                            <thead>
                                <tr>
                                    <th width="80">ID</th>
                                    <th>Asignatura</th>
                                    <th width="100">Créditos</th>
                                    <th>Docente</th>
                                    <th>Carrera</th>
                                    <th width="180" class="text-center">Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="asignatura" items="${asignaturas}">
                                    <tr>
                                        <td>
                                            <span class="badge badge-primary">${asignatura.id_asignatura}</span>
                                        </td>
                                        <td>
                                            <div class="subject-info">
                                                <div class="subject-name">${asignatura.nombre_asignatura}</div>
                                                <div class="subject-code">ID: ${asignatura.id_asignatura}</div>
                                            </div>
                                        </td>
                                        <td>
                                            <span class="credits-badge">
                                                <span>📘</span>
                                                <span>${asignatura.creditos}</span>
                                            </span>
                                        </td>
                                        <td>
                                            <div class="teacher-info">
                                                <div class="teacher-name">
                                                    <!-- Mostrar nombre del docente si está disponible -->
                                                    <c:if test="${not empty docentes}">
                                                        <c:forEach var="docente" items="${docentes}">
                                                            <c:if test="${docente.id_docente == asignatura.id_docente}">
                                                                ${docente.nombre_docente} ${docente.apellido_docente}
                                                            </c:if>
                                                        </c:forEach>
                                                    </c:if>
                                                    <c:if test="${empty docentes}">
                                                        Docente ID: ${asignatura.id_docente}
                                                    </c:if>
                                                </div>
                                                <div class="teacher-id">
                                                    ID: ${asignatura.id_docente}
                                                </div>
                                            </div>
                                        </td>
                                        <td>
                                            <div class="career-info">
                                                <div class="career-name">
                                                    <!-- Mostrar nombre de la carrera si está disponible -->
                                                    <c:if test="${not empty carreras}">
                                                        <c:forEach var="carrera" items="${carreras}">
                                                            <c:if test="${carrera.id_carrera == asignatura.id_carrera}">
                                                                ${carrera.nombre_carrera}
                                                            </c:if>
                                                        </c:forEach>
                                                    </c:if>
                                                    <c:if test="${empty carreras}">
                                                        Carrera ID: ${asignatura.id_carrera}
                                                    </c:if>
                                                </div>
                                                <div class="career-id">
                                                    ID: ${asignatura.id_carrera}
                                                </div>
                                            </div>
                                        </td>
                                        <td>
                                            <div class="action-buttons">
                                                <a href="${pageContext.request.contextPath}/asignaturas?action=edit&id=${asignatura.id_asignatura}" 
                                                class="btn btn-sm btn-primary btn-icon" title="Editar">
                                                    ✏️
                                                </a>
                                                <a href="${pageContext.request.contextPath}/asignaturas?action=delete&id=${asignatura.id_asignatura}" 
                                                class="btn btn-sm btn-danger btn-icon delete-btn"
                                                data-id="${asignatura.id_asignatura}"
                                                data-name="${fn:escapeXml(asignatura.nombre_asignatura)}"
                                                title="Eliminar"
                                                onclick="return confirm('¿Está seguro de eliminar la asignatura \'${fn:escapeXml(asignatura.nombre_asignatura)}\'?')">
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
                            Mostrando <strong>${asignaturas.size()}</strong> asignaturas
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
                        <div class="empty-state-icon">📚</div>
                        <h3 class="empty-state-title">No hay asignaturas registradas</h3>
                        <p class="empty-state-description">
                            Comience agregando asignaturas al sistema académico. 
                            Las asignaturas son las materias que se imparten en cada carrera.
                        </p>
                        <a href="${pageContext.request.contextPath}/asignaturas?action=new" 
                           class="btn btn-success btn-lg">
                            <span style="font-size: 1.2rem;">+</span> Crear primera asignatura
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

    <script>
        // Script básico para filtrar la tabla
        function filterTable(searchTerm) {
            const table = document.getElementById('asignaturasTable');
            if (!table) return;
            
            const rows = table.querySelectorAll('tbody tr');
            searchTerm = searchTerm.toLowerCase().trim();
            
            rows.forEach(row => {
                const cells = row.querySelectorAll('td');
                let found = false;
                
                for (let i = 0; i < cells.length - 1; i++) {
                    const cellText = cells[i].textContent || cells[i].innerText;
                    if (cellText.toLowerCase().includes(searchTerm)) {
                        found = true;
                        break;
                    }
                }
                
                row.style.display = found ? '' : 'none';
            });
        }
    </script>
</body>
</html>