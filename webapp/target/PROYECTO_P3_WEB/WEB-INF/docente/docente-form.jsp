<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${empty docente.id_docente ? 'Nuevo' : 'Editar'} Docente - Sistema Académico</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link href="${pageContext.request.contextPath}/css/docente-form.css" rel="stylesheet">
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
                    ${empty docente.id_docente ? '➕' : '✏️'}
                </div>
                <div class="form-title">
                    <h1>${empty docente.id_docente ? 'Nuevo Docente' : 'Editar Docente'}</h1>
                    <div class="form-subtitle">
                        ${empty docente.id_docente ? 
                          'Complete el formulario para registrar un nuevo docente' : 
                          'Modifique la información del docente existente'}
                    </div>
                </div>
            </div>
            
            <!-- Previsualización del avatar -->
            <c:if test="${not empty docente.nombre_docente}">
                <div class="avatar-preview">
                    <div class="avatar-initials">
                        ${fn:substring(docente.nombre_docente, 0, 1)}${fn:substring(docente.apellido_docente, 0, 1)}
                    </div>
                </div>
            </c:if>
            
            <div class="form-card">
                <form action="${pageContext.request.contextPath}/docentes" method="post" 
                      onsubmit="return validateDocenteForm()" id="docenteForm">
                    <input type="hidden" name="action" value="${formAction}">
                    
                    <c:if test="${formAction == 'update'}">
                        <input type="hidden" name="id_docente" value="${docente.id_docente}">
                    </c:if>
                    
                    <!-- Sección: Información personal -->
                    <div class="form-section">
                        <div class="section-title">
                            <span class="section-icon">👤</span>
                            <span>Información Personal</span>
                        </div>
                        
                        <div class="form-row">
                            <div class="form-group">
                                <label for="id_docente" class="form-label required">
                                    ID Docente
                                </label>
                                <c:choose>
                                    <c:when test="${formAction == 'update'}">
                                        <div class="readonly-field">
                                            ${docente.id_docente}
                                        </div>
                                        <input type="hidden" id="id_docente" name="id_docente" 
                                               value="${docente.id_docente}">
                                    </c:when>
                                    <c:otherwise>
                                        <input type="number" id="id_docente" name="id_docente" 
                                               class="form-control"
                                               value="${docente.id_docente}" 
                                               required
                                               min="1"
                                               placeholder="Ej: 1001">
                                        <span class="field-hint">Número único identificador del docente</span>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            
                            <div class="form-group">
                                <label for="nombre_docente" class="form-label required">
                                    Nombre
                                </label>
                                <input type="text" id="nombre_docente" name="nombre_docente" 
                                       class="form-control"
                                       value="${docente.nombre_docente}" 
                                       required
                                       maxlength="50"
                                       placeholder="Ej: Juan Carlos"
                                       oninput="updateAvatarPreview()">
                                <span class="field-hint">Nombre del docente (máx. 50 caracteres)</span>
                            </div>
                        </div>
                        
                        <div class="form-row">
                            <div class="form-group">
                                <label for="apellido_docente" class="form-label required">
                                    Apellido
                                </label>
                                <input type="text" id="apellido_docente" name="apellido_docente" 
                                       class="form-control"
                                       value="${docente.apellido_docente}" 
                                       required
                                       maxlength="50"
                                       placeholder="Ej: Pérez García"
                                       oninput="updateAvatarPreview()">
                                <span class="field-hint">Apellido del docente (máx. 50 caracteres)</span>
                            </div>
                        </div>
                    </div>
                    
                    <!-- Sección: Información de contacto -->
                    <div class="form-section">
                        <div class="section-title">
                            <span class="section-icon">📧</span>
                            <span>Información de Contacto</span>
                        </div>
                        
                        <div class="form-row">
                            <div class="form-group">
                                <label for="correo" class="form-label required">
                                    Correo Electrónico
                                </label>
                                <input type="email" id="correo" name="correo" 
                                       class="form-control"
                                       value="${docente.correo}" 
                                       required
                                       maxlength="100"
                                       placeholder="Ej: juan.perez@espe.edu.ec">
                                <span class="field-hint">Correo institucional del docente</span>
                            </div>
                        </div>
                    </div>
                    
                    <!-- Información adicional -->
                    <div class="form-info">
                        <div class="form-info-title">📝 Información importante</div>
                        <div class="form-info-content">
                            <p><strong>ID Docente:</strong> Debe ser un número único que no se repita en el sistema.</p>
                            <p><strong>Correo electrónico:</strong> Utilice el correo institucional cuando sea posible.</p>
                        </div>
                    </div>
                    
                    <!-- Acciones del formulario -->
                    <div class="form-actions">
                        <div>
                            <button type="submit" class="btn btn-success">
                                <span style="font-size: 1.2rem;">💾</span>
                                ${empty docente.id_docente ? 'Registrar Docente' : 'Actualizar Docente'}
                            </button>
                            <a href="${pageContext.request.contextPath}/docentes" class="btn btn-secondary ml-1">
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

    <script src="${pageContext.request.contextPath}/js/docente-form.js"></script>
</body>
</html>