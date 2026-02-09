<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error - Sistema Académico</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error.css">
</head>
<body>
    <div class="container">
        <div class="error-container">
            <div class="error-header">
                <div class="error-icon">⚠️</div>
                
                <c:choose>
                    <c:when test="${not empty statusCode}">
                        <div class="error-code">${statusCode}</div>
                        <h1 class="error-title">
                            <c:choose>
                                <c:when test="${statusCode == 404}">
                                    Página no encontrada
                                </c:when>
                                <c:when test="${statusCode == 500}">
                                    Error del servidor
                                </c:when>
                                <c:when test="${statusCode == 403}">
                                    Acceso denegado
                                </c:when>
                                <c:when test="${statusCode == 400}">
                                    Solicitud incorrecta
                                </c:when>
                                <c:otherwise>
                                    Error ${statusCode}
                                </c:otherwise>
                            </c:choose>
                        </h1>
                    </c:when>
                    <c:otherwise>
                        <div class="error-code">Error</div>
                        <h1 class="error-title">¡Algo salió mal!</h1>
                    </c:otherwise>
                </c:choose>
                
                <div class="error-message">
                    <c:choose>
                        <c:when test="${not empty errorMessage}">
                            ${errorMessage}
                        </c:when>
                        <c:when test="${statusCode == 404}">
                            La página que estás buscando no existe o ha sido movida.
                        </c:when>
                        <c:when test="${statusCode == 500}">
                            Ha ocurrido un error interno en el servidor. Nuestro equipo ha sido notificado.
                        </c:when>
                        <c:otherwise>
                            Ha ocurrido un error inesperado. Por favor, intente nuevamente.
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            
            <div class="error-content">
                <div class="error-actions">
                    <a href="javascript:history.back()" class="action-card" style="border-top-color: var(--secondary-color);">
                        <div class="action-icon">↩️</div>
                        <div class="action-title">Volver atrás</div>
                        <div class="action-description">
                            Regresar a la página anterior
                        </div>
                    </a>
                    
                    <a href="${pageContext.request.contextPath}/" class="action-card" style="border-top-color: var(--success-color);">
                        <div class="action-icon">🏠</div>
                        <div class="action-title">Ir al inicio</div>
                        <div class="action-description">
                            Volver a la página principal
                        </div>
                    </a>
                </div>
                
                <!-- Detalles del error -->
                <c:if test="${not empty errorDetails}">
                    <div class="error-details">
                        <div class="details-header" onclick="toggleDetails()">
                            <div class="details-title">Detalles técnicos del error</div>
                            <div class="details-toggle" id="toggleText">Mostrar detalles</div>
                        </div>
                        <div class="details-content" id="errorDetails">
                            <pre>${errorDetails}</pre>
                        </div>
                    </div>
                </c:if>
                
                <!-- Información técnica -->
                <c:if test="${pageContext.request.serverName == 'localhost'}">
                    <div class="tech-info">
                        <div style="color: var(--primary-color); margin-bottom: 15px; font-weight: 600;">
                            Información de diagnóstico
                        </div>
                        <div class="info-grid">
                            <div class="info-item">
                                <span class="info-label">URL solicitada:</span>
                                <span class="info-value">${not empty requestUri ? requestUri : 'N/A'}</span>
                            </div>
                            <div class="info-item">
                                <span class="info-label">Código de estado:</span>
                                <span class="info-value">${not empty statusCode ? statusCode : 'N/A'}</span>
                            </div>
                            <div class="info-item">
                                <span class="info-label">Servidor:</span>
                                <span class="info-value">${pageContext.request.serverName}:${pageContext.request.serverPort}</span>
                            </div>
                            <div class="info-item">
                                <span class="info-label">Fecha y hora:</span>
                                <span class="info-value" id="currentDateTime"></span>
                            </div>
                            <div class="info-item">
                                <span class="info-label">Navegador:</span>
                                <span class="info-value" id="userAgent"></span>
                            </div>
                            <div class="info-item">
                                <span class="info-label">Sesión ID:</span>
                                <span class="info-value">${pageContext.session.id}</span>
                            </div>
                        </div>
                    </div>
                </c:if>
            </div>
            
            <div class="error-footer">
                <p>
                    <strong>Sistema Académico</strong> • 
                    Si el problema persiste, por favor contacte al administrador del sistema.
                </p>
                <p style="margin-top: 10px; font-size: 0.85rem;">
                    <c:set var="currentYear" value="<%= java.time.Year.now().getValue() %>" />
                    © ${currentYear} Universidad ESPE. Todos los derechos reservados.
                </p>
            </div>
        </div>
    </div>

    <script>
    // Código JavaScript inline
    function toggleDetails() {
        const details = document.getElementById('errorDetails');
        const toggleText = document.getElementById('toggleText');
        
        if (details.style.display === 'block') {
            details.style.display = 'none';
            toggleText.textContent = 'Mostrar detalles';
        } else {
            details.style.display = 'block';
            toggleText.textContent = 'Ocultar detalles';
        }
    }
</script>
</body>
</html>