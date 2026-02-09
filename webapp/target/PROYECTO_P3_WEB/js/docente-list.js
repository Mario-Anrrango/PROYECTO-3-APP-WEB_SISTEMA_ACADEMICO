/**
 * Mostrar detalles de un docente
 */
function showDocenteDetails(docenteId) {
    alert('Detalles del docente ID: ' + docenteId + '\n\n' +
          'Información detallada:\n' +
          '• Nombre completo\n' + 
          '• Apellidos\n' +
          '• Correo electrónico\n' +
          '• Especialidades\n' +
          '• Asignaturas que imparte\n' +
          '• Horario de atención\n' +
          '• Experiencia profesional');
}

/**
 * Configurar eventos de los botones
 */
function setupActionButtons() {
    // 1. Botones de VER DETALLES
    const detailButtons = document.querySelectorAll('.details-btn');
    detailButtons.forEach(button => {
        button.addEventListener('click', function() {
            const docenteId = this.getAttribute('data-id');
            if (docenteId) {
                showDocenteDetails(docenteId);
            }
        });
    });
    
    // 2. Botones de ELIMINAR
    const deleteButtons = document.querySelectorAll('.delete-btn');
    deleteButtons.forEach(button => {
        button.addEventListener('click', function(event) {
            // Prevenir que el link se ejecute inmediatamente
            event.preventDefault();
            
            const docenteId = this.getAttribute('data-id');
            const docenteNombre = this.getAttribute('data-nombre');
            const docenteApellido = this.getAttribute('data-apellido');
            const deleteUrl = this.getAttribute('href');
            
            // Mostrar confirmación
            if (confirm('¿Está seguro de eliminar al docente "' + docenteNombre + ' ' + docenteApellido + '"? Esta acción no se puede deshacer.')) {
                // Si confirma, redirigir a la URL de eliminación
                window.location.href = deleteUrl;
            }
        });
    });
}

/**
 * Filtrar la tabla de docentes
 */
function filterTable(searchTerm) {
    const table = document.getElementById('docentesTable');
    if (!table) return;
    
    const rows = table.querySelectorAll('tbody tr');
    searchTerm = searchTerm.toLowerCase().trim();
    
    rows.forEach(row => {
        const cells = row.querySelectorAll('td');
        let found = false;
        
        // Buscar en todas las celdas excepto la última (acciones)
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

/**
 * Configurar avatares de docentes con colores diferentes
 */
function setupTeacherAvatars() {
    const avatars = document.querySelectorAll('.teacher-avatar');
    const colors = [
        'linear-gradient(135deg, #3498db, #2980b9)',      // Azul
        'linear-gradient(135deg, #2ecc71, #27ae60)',      // Verde
        'linear-gradient(135deg, #e74c3c, #c0392b)',      // Rojo
        'linear-gradient(135deg, #9b59b6, #8e44ad)',      // Púrpura
        'linear-gradient(135deg, #f39c12, #d35400)',      // Naranja
        'linear-gradient(135deg, #1abc9c, #16a085)'       // Turquesa
    ];
    
    avatars.forEach((avatar, index) => {
        const colorIndex = index % colors.length;
        avatar.style.background = colors[colorIndex];
        
        // Agregar efecto hover
        avatar.addEventListener('mouseenter', function() {
            this.style.transform = 'scale(1.1)';
            this.style.transition = 'transform 0.3s ease';
        });
        
        avatar.addEventListener('mouseleave', function() {
            this.style.transform = 'scale(1)';
        });
    });
}

/**
 * Configurar colores de badges de estado
 */
function setupStatusBadges() {
    const statusBadges = document.querySelectorAll('.status-badge');
    statusBadges.forEach(badge => {
        if (badge.classList.contains('status-active')) {
            badge.style.background = '#e8f5e9';
            badge.style.color = '#27ae60';
            badge.style.border = '1px solid #c8e6c9';
        } else if (badge.classList.contains('status-inactive')) {
            badge.style.background = '#f5f5f5';
            badge.style.color = '#95a5a6';
            badge.style.border = '1px solid #e0e0e0';
        }
    });
}

/**
 * Configurar enlaces de correo
 */
function setupEmailLinks() {
    const emailElements = document.querySelectorAll('.email-address');
    emailElements.forEach(email => {
        const emailText = email.textContent || email.innerText;
        if (emailText.includes('@')) {
            email.style.cursor = 'pointer';
            email.style.color = '#3498db';
            email.style.textDecoration = 'underline';
            
            email.addEventListener('click', function() {
                window.location.href = 'mailto:' + emailText;
            });
            
            email.title = 'Click para enviar correo';
        }
    });
}

/**
 * Calcular y mostrar estadísticas
 */
function showStatistics() {
    const totalDocentes = document.querySelectorAll('#docentesTable tbody tr').length;
    const activeDocentes = document.querySelectorAll('.status-active').length;
    
    console.log('📊 Estadísticas de docentes:');
    console.log('• Total de docentes:', totalDocentes);
    console.log('• Docentes activos:', activeDocentes);
    console.log('• Docentes inactivos:', totalDocentes - activeDocentes);
    
    // Actualizar el valor en el stat-item si existe
    const statValueElement = document.querySelector('.stat-value');
    if (statValueElement && statValueElement.textContent === '24') { // Valor por defecto
        statValueElement.textContent = totalDocentes;
    }
}

/**
 * Configurar ordenamiento de tabla
 */
function setupTableSorting() {
    const table = document.getElementById('docentesTable');
    if (!table) return;
    
    const headers = table.querySelectorAll('th');
    
    headers.forEach((header, index) => {
        // No ordenar la última columna (acciones)
        if (index !== headers.length - 1) {
            header.style.cursor = 'pointer';
            header.title = 'Click para ordenar';
            
            header.addEventListener('click', function() {
                sortTableByColumn(index);
            });
        }
    });
}

/**
 * Ordenar tabla por columna
 */
function sortTableByColumn(columnIndex) {
    const table = document.getElementById('docentesTable');
    if (!table) return;
    
    const tbody = table.querySelector('tbody');
    const rows = Array.from(tbody.querySelectorAll('tr'));
    
    // Determinar dirección de ordenamiento
    const isAscending = table.getAttribute('data-sort-dir') !== 'desc';
    
    rows.sort((rowA, rowB) => {
        const cellA = rowA.querySelectorAll('td')[columnIndex];
        const cellB = rowB.querySelectorAll('td')[columnIndex];
        
        const textA = cellA.textContent.trim().toLowerCase();
        const textB = cellB.textContent.trim().toLowerCase();
        
        // Intentar convertir a número
        const numA = parseFloat(textA);
        const numB = parseFloat(textB);
        
        let comparison = 0;
        
        if (!isNaN(numA) && !isNaN(numB)) {
            comparison = numA - numB;
        } else {
            comparison = textA.localeCompare(textB);
        }
        
        return isAscending ? comparison : -comparison;
    });
    
    // Reordenar filas
    rows.forEach(row => tbody.appendChild(row));
    
    // Actualizar estado de ordenamiento
    table.setAttribute('data-sort-dir', isAscending ? 'desc' : 'asc');
    
    console.log('📋 Tabla ordenada por columna', columnIndex, 'en orden', isAscending ? 'ascendente' : 'descendente');
}

/**
 * Inicializar todo cuando la página cargue
 */
function initDocentes() {
    console.log('✅ Módulo de docentes inicializado');
    
    // 1. Configurar botones
    setupActionButtons();
    
    // 2. Configurar avatares
    setupTeacherAvatars();
    
    // 3. Configurar badges de estado
    setupStatusBadges();
    
    // 4. Configurar enlaces de correo
    setupEmailLinks();
    
    // 5. Configurar ordenamiento de tabla
    setupTableSorting();
    
    // 6. Configurar búsqueda
    const searchInput = document.querySelector('.search-input');
    if (searchInput) {
        searchInput.addEventListener('keyup', function() {
            filterTable(this.value);
        });
    }
    
    // 7. Mostrar estadísticas
    showStatistics();
    
    // 8. Agregar evento para el botón de exportar si existe
    const exportBtn = document.querySelector('.export-btn');
    if (exportBtn) {
        exportBtn.addEventListener('click', function() {
            alert('📤 Exportando lista de docentes...\n' +
                  'Formato: PDF / Excel / CSV\n' +
                  'Esta función generaría un archivo descargable.');
        });
    }
}

// Esperar a que el DOM esté completamente cargado
document.addEventListener('DOMContentLoaded', function() {
    initDocentes();
});