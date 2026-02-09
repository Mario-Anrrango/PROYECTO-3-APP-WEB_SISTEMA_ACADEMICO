<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${empty carrera.id_carrera ? 'Nueva' : 'Editar'} Carrera - Sistema Académico</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link href="${pageContext.request.contextPath}/css/carrera-form.css" rel="stylesheet">
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
                    ${empty carrera.id_carrera ? '➕' : '✏️'}
                </div>
                <div class="form-title">
                    <h1>${empty carrera.id_carrera ? 'Nueva Carrera' : 'Editar Carrera'}</h1>
                    <div class="form-subtitle">
                        ${empty carrera.id_carrera ? 
                          'Complete el formulario para registrar una nueva carrera' : 
                          'Modifique la información de la carrera existente'}
                    </div>
                </div>
            </div>
            
            <div class="form-card">
                <form action="${pageContext.request.contextPath}/carreras" method="post" 
                      onsubmit="return validateCarreraForm()" id="carreraForm">
                    <input type="hidden" name="action" value="${formAction}">
                    
                    <c:if test="${formAction == 'update'}">
                        <input type="hidden" name="id_carrera" value="${carrera.id_carrera}">
                    </c:if>
                    
                    <!-- Sección: Información básica -->
                    <div class="form-section">
                        <div class="section-title">
                            <span class="section-icon">📋</span>
                            <span>Información Básica</span>
                        </div>
                        
                        <div class="form-row">
                            <div class="form-group">
                                <label for="id_carrera" class="form-label required">
                                    ID Carrera
                                </label>
                                <c:choose>
                                    <c:when test="${formAction == 'update'}">
                                        <div class="readonly-field">
                                            ${carrera.id_carrera}
                                        </div>
                                        <input type="hidden" id="id_carrera" name="id_carrera" 
                                               value="${carrera.id_carrera}">
                                    </c:when>
                                    <c:otherwise>
                                        <input type="number" id="id_carrera" name="id_carrera" 
                                               class="form-control"
                                               value="${carrera.id_carrera}" 
                                               required
                                               min="1"
                                               placeholder="Ej: 1001">
                                        <span class="field-hint">Número único identificador de la carrera</span>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            
                            <div class="form-group">
                                <label for="nombre_carrera" class="form-label required">
                                    Nombre de la Carrera
                                </label>
                                <input type="text" id="nombre_carrera" name="nombre_carrera" 
                                       class="form-control"
                                       value="${carrera.nombre_carrera}" 
                                       required
                                       maxlength="100"
                                       placeholder="Ej: Ingeniería de Software">
                                <span class="field-hint">Nombre completo de la carrera (máx. 100 caracteres)</span>
                            </div>
                        </div>
                    </div>
                    
                    <!-- Información adicional -->
                    <div class="form-info">
                        <div class="form-info-title">📝 Información importante</div>
                        <div class="form-info-content">
                            <p><strong>ID Carrera:</strong> Debe ser un número único que no se repita en el sistema.</p>
                            <p><strong>Nombre:</strong> Use el nombre oficial y completo de la carrera.</p>
                        </div>
                    </div>
                    
                    <!-- Acciones del formulario -->
                    <div class="form-actions">
                        <div>
                            <button type="submit" class="btn btn-primary">
                                <span style="font-size: 1.2rem;">💾</span>
                                ${empty carrera.id_carrera ? 'Crear Carrera' : 'Actualizar Carrera'}
                            </button>
                            <a href="${pageContext.request.contextPath}/carreras" class="btn btn-secondary ml-1">
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

    <script src="${pageContext.request.contextPath}/js/carrera-form.js"></script>
</body>
</html>