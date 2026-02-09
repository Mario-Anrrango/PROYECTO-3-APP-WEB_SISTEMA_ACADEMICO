<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${empty asignatura.id_asignatura ? 'Nueva' : 'Editar'} Asignatura - Sistema Académico</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link href="${pageContext.request.contextPath}/css/asignatura-form.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <!-- Mensajes de error -->
        <c:if test="${not empty error}">
            <div class="alert alert-danger">
                <span style="font-size: 1.2rem;">⚠️</span>
                <span>${error}</span>
            </div>
        </c:if>

        <div class="form-container">
            <div class="form-header">
                <div class="form-icon">
                    ${empty asignatura.id_asignatura ? '➕' : '✏️'}
                </div>
                <div class="form-title">
                    <h1>${empty asignatura.id_asignatura ? 'Nueva Asignatura' : 'Editar Asignatura'}</h1>
                    <div class="form-subtitle">
                        ${empty asignatura.id_asignatura ? 
                          'Complete el formulario para registrar una nueva asignatura' : 
                          'Modifique la información de la asignatura existente'}
                    </div>
                </div>
            </div>
            
            <div class="form-card">
                <form action="${pageContext.request.contextPath}/asignaturas" method="post" 
                      onsubmit="return validateAsignaturaForm()" id="asignaturaForm">
                    <input type="hidden" name="action" value="${formAction}">
                    
                    <c:if test="${formAction == 'update'}">
                        <input type="hidden" name="id_asignatura" value="${asignatura.id_asignatura}">
                    </c:if>
                    
                    <!-- Sección: Información básica -->
                    <div class="form-section">
                        <div class="section-title">
                            <span class="section-icon">📋</span>
                            <span>Información Básica</span>
                        </div>
                        
                        <div class="form-row">
                            <div class="form-group">
                                <label for="id_asignatura" class="form-label required">
                                    ID Asignatura
                                </label>
                                <c:choose>
                                    <c:when test="${formAction == 'update'}">
                                        <div class="readonly-field">
                                            ${asignatura.id_asignatura}
                                        </div>
                                        <input type="hidden" id="id_asignatura" name="id_asignatura" 
                                               value="${asignatura.id_asignatura}">
                                    </c:when>
                                    <c:otherwise>
                                        <input type="number" id="id_asignatura" name="id_asignatura" 
                                               class="form-control"
                                               value="${asignatura.id_asignatura}" 
                                               required
                                               min="1"
                                               placeholder="Ej: 1001">
                                        <span class="field-hint">Número único identificador de la asignatura</span>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            
                            <div class="form-group">
                                <label for="nombre_asignatura" class="form-label required">
                                    Nombre de la Asignatura
                                </label>
                                <input type="text" id="nombre_asignatura" name="nombre_asignatura" 
                                       class="form-control"
                                       value="${asignatura.nombre_asignatura}" 
                                       required
                                       maxlength="100"
                                       placeholder="Ej: Cálculo Diferencial">
                                <span class="field-hint">Nombre completo de la asignatura (máx. 100 caracteres)</span>
                            </div>
                        </div>
                        
                        <div class="form-row">
                            <div class="form-group">
                                <label for="creditos" class="form-label required">
                                    Créditos
                                </label>
                                <input type="number" id="creditos" name="creditos" 
                                       class="form-control"
                                       value="${asignatura.creditos}" 
                                       required
                                       min="1" max="10"
                                       placeholder="Ej: 4">
                                <span class="field-hint">Número de créditos (entre 1 y 10)</span>
                            </div>
                        </div>
                    </div>
                    
                    <!-- Sección: Asignación -->
                    <div class="form-section">
                        <div class="section-title">
                            <span class="section-icon">👨‍🏫</span>
                            <span>Asignación</span>
                        </div>
                        
                        <div class="form-row">
                            <div class="form-group">
                                <label for="id_docente" class="form-label required">
                                    Docente Responsable
                                </label>
                                <select id="id_docente" name="id_docente" class="form-select" required>
                                    <option value="">Seleccione un docente...</option>
                                    <c:forEach var="docente" items="${docentes}">
                                        <option value="${docente.id_docente}" 
                                                ${asignatura.id_docente == docente.id_docente ? 'selected' : ''}>
                                            ${docente.nombre_docente} ${docente.apellido_docente} 
                                            (ID: ${docente.id_docente})
                                        </option>
                                    </c:forEach>
                                </select>
                                <span class="field-hint">Seleccione el docente que impartirá esta asignatura</span>
                            </div>
                            
                            <div class="form-group">
                                <label for="id_carrera" class="form-label required">
                                    Carrera
                                </label>
                                <select id="id_carrera" name="id_carrera" class="form-select" required>
                                    <option value="">Seleccione una carrera...</option>
                                    <c:forEach var="carrera" items="${carreras}">
                                        <option value="${carrera.id_carrera}" 
                                                ${asignatura.id_carrera == carrera.id_carrera ? 'selected' : ''}>
                                            ${carrera.nombre_carrera} (ID: ${carrera.id_carrera})
                                        </option>
                                    </c:forEach>
                                </select>
                                <span class="field-hint">Seleccione la carrera a la que pertenece esta asignatura</span>
                            </div>
                        </div>
                    </div>
                    
                    <!-- Información adicional -->
                    <div class="form-info">
                        <div class="form-info-title">📝 Información importante</div>
                        <div class="form-info-content">
                            <p><strong>ID Asignatura:</strong> Debe ser un número único que no se repita en el sistema.</p>
                            <p><strong>Créditos:</strong> Representan la carga académica de la asignatura.</p>
                            <p><strong>Docente:</strong> Asegúrese de que el docente seleccionado esté activo en el sistema.</p>
                            <p><strong>Carrera:</strong> La asignatura se vinculará permanentemente a la carrera seleccionada.</p>
                        </div>
                    </div>
                    
                    <!-- Acciones del formulario -->
                    <div class="form-actions">
                        <div>
                            <button type="submit" class="btn btn-success">
                                <span style="font-size: 1.2rem;">💾</span>
                                ${empty asignatura.id_asignatura ? 'Crear Asignatura' : 'Actualizar Asignatura'}
                            </button>
                            <a href="${pageContext.request.contextPath}/asignaturas" class="btn btn-secondary ml-1">
                                Cancelar
                            </a>
                        </div>
                        
                        <div style="color: var(--gray-color); font-size: 0.9rem;">
                            <span class="required">*</span> Campos obligatorios
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/js/asignatura-form.js"></script>
</body>
</html>