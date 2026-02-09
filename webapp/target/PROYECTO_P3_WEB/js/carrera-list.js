/**
 * Mostrar detalles de una carrera
 */
function showCarreraDetails(carreraId) {
    alert('Detalles de la carrera ID: ' + carreraId + '\n\n' +
          'Información detallada:\n' +
          '• Nombre completo\n' + 
          '• Código de carrera\n' +
          '• Duración en semestres\n' +
          '• Créditos totales\n' +
          '• Facultad\n' +
          '• Modalidad (Presencial/Virtual/Híbrida)\n' +
          '• Plan de estudios\n' +
          '• Perfil del egresado');
}

/**
 * Configurar eventos de los botones
 */
function setupActionButtons() {
    // 1. Botones de VER DETALLES
    const detailButtons = document.querySelectorAll('.details-btn');
    detailButtons.forEach(button => {
        button.addEventListener('click', function() {
            const carreraId = this.getAttribute('data-id');
            if (carreraId) {
                showCarreraDetails(carreraId);
            }
        });
    });
    
    // 2. Botones de ELIMINAR
    const deleteButtons = document.querySelectorAll('.delete-btn');
    deleteButtons.forEach(button => {
        button.addEventListener('click', function(event) {
            // Prevenir que el link se ejecute inmediatamente
            event.preventDefault();
            
            const carreraId = this.getAttribute('data-id');
            const carreraNombre = this.getAttribute('data-nombre');
            const deleteUrl = this.getAttribute('href');
            
            // Mostrar confirmación con advertencia sobre asignaturas relacionadas
            if (confirm('⚠️ ADVERTENCIA: ¿Está seguro de eliminar la carrera "' + carreraNombre + '"?\n\n' +
                       'Esta acción eliminará también todas las asignaturas asociadas a esta carrera.\n' +
                       'Esta acción NO se puede deshacer.')) {
                // Si confirma, redirigir a la URL de eliminación
                window.location.href = deleteUrl;
            }
        });
    });
}

/**
 * Filtrar la tabla de carreras
 */
function filterTable(searchTerm) {
    const table = document.getElementById('carrerasTable');
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
 * Configurar íconos de carreras con colores diferentes
 */
function setupCareerIcons() {
    const icons = document.querySelectorAll('.career-icon');
    const colors = [
        'linear-gradient(135deg, #3498db, #2980b9)',      // Azul - Ingenierías
        'linear-gradient(135deg, #2ecc71, #27ae60)',      // Verde - Ciencias
        'linear-gradient(135deg, #9b59b6, #8e44ad)',      // Púrpura - Humanidades
        'linear-gradient(135deg, #e74c3c, #c0392b)',      // Rojo - Salud
        'linear-gradient(135deg, #f39c12, #d35400)',      // Naranja - Economía
        'linear-gradient(135deg, #1abc9c, #16a085)'       // Turquesa - Derecho
    ];
    
    icons.forEach((icon, index) => {
        const colorIndex = index % colors.length;
        icon.style.background = colors[colorIndex];
        
        // Agregar efecto hover
        icon.addEventListener('mouseenter', function() {
            this.style.transform = 'rotate(10deg) scale(1.1)';
            this.style.transition = 'transform 0.3s ease';
        });
        
        icon.addEventListener('mouseleave', function() {
            this.style.transform = 'rotate(0deg) scale(1)';
        });
    });
}

/**
 * Configurar badges de asignaturas
 */
function setupSubjectBadges() {
    const subjectBadges = document.querySelectorAll('.subjects-count');
    subjectBadges.forEach(badge => {
        const text = badge.textContent || '';
        const count = parseInt(text.match(/\d+/)?.[0] || 0);
        
        // Colores según cantidad de asignaturas
        if (count > 30) {
            badge.style.background = '#ffebee';
            badge.style.color = '#e74c3c';
            badge.title = 'Carrera con muchas asignaturas';
        } else if (count > 20) {
            badge.style.background = '#fff8e1';
            badge.style.color = '#f39c12';
            badge.title = 'Carrera con asignaturas moderadas';
        } else {
            badge.style.background = '#e8f5e9';
            badge.style.color = '#27ae60';
            badge.title = 'Carrera con pocas asignaturas';
        }
        
        // Agregar tooltip
        badge.addEventListener('mouseenter', function() {
            this.style.transform = 'translateY(-2px)';
        });
        
        badge.addEventListener('mouseleave', function() {
            this.style.transform = 'translateY(0)';
        });
    });
}

/**
 * Configurar badges de duración
 */
function setupDurationBadges() {
    const durationBadges = document.querySelectorAll('.semesters-badge');
    durationBadges.forEach(badge => {
        const text = badge.textContent || '';
        const semesters = parseInt(text.match(/\d+/)?.[0] || 8);
        
        // Colores según duración
        if (semesters > 10) {
            badge.style.background = '#e3f2fd';
            badge.style.color = '#1565c0';
            badge.title = 'Carrera de larga duración';
        } else if (semesters > 8) {
            badge.style.background = '#f3e5f5';
            badge.style.color = '#7b1fa2';
            badge.title = 'Carrera de duración estándar';
        } else {
            badge.style.background = '#e8f5e9';
            badge.style.color = '#2e7d32';
            badge.title = 'Carrera de corta duración';
        }
    });
}

/**
 * Calcular y mostrar estadísticas
 */
function showStatistics() {
    const totalCarreras = document.querySelectorAll('#carrerasTable tbody tr').length;
    let totalAsignaturas = 0;
    let totalSemestres = 0;
    
    // Calcular totales
    const subjectBadges = document.querySelectorAll('.subjects-count');
    subjectBadges.forEach(badge => {
        const text = badge.textContent || '';
        const count = parseInt(text.match(/\d+/)?.[0] || 0);
        totalAsignaturas += count;
    });
    
    const durationBadges = document.querySelectorAll('.semesters-badge');
    durationBadges.forEach(badge => {
        const text = badge.textContent || '';
        const semesters = parseInt(text.match(/\d+/)?.[0] || 0);
        totalSemestres += semesters;
    });
    
    const promedioSemestres = totalCarreras > 0 ? (totalSemestres / totalCarreras).toFixed(1) : 0;
    const promedioAsignaturas = totalCarreras > 0 ? (totalAsignaturas / totalCarreras).toFixed(1) : 0;
    
    console.log('📊 Estadísticas de carreras:');
    console.log('• Total de carreras:', totalCarreras);
    console.log('• Total de asignaturas:', totalAsignaturas);
    console.log('• Promedio de asignaturas por carrera:', promedioAsignaturas);
    console.log('• Promedio de semestres por carrera:', promedioSemestres);
    
    // Actualizar valores en los stat-items si existen
    const statValues = document.querySelectorAll('.stat-value');
    if (statValues.length >= 2) {
        if (statValues[0].textContent === '8') { // Valor por defecto para carreras
            statValues[0].textContent = totalCarreras;
        }
        if (statValues[1].textContent === '156') { // Valor por defecto para asignaturas
            statValues[1].textContent = totalAsignaturas;
        }
    }
}

/**
 * Configurar ordenamiento de tabla
 */
function setupTableSorting() {
    const table = document.getElementById('carrerasTable');
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
    const table = document.getElementById('carrerasTable');
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
        
        // Para columnas especiales (asignaturas, duración)
        if (columnIndex === 2 || columnIndex === 3) { // Índices de asignaturas y duración
            const numA = parseInt(textA.match(/\d+/)?.[0] || 0);
            const numB = parseInt(textB.match(/\d+/)?.[0] || 0);
            return isAscending ? numA - numB : numB - numA;
        }
        
        // Para otras columnas (texto)
        let comparison = 0;
        
        // Intentar convertir a número
        const numA = parseFloat(textA);
        const numB = parseFloat(textB);
        
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
 * Agregar funcionalidad de clic en nombres de carrera
 */
function setupCareerNameClicks() {
    const careerNames = document.querySelectorAll('.career-name');
    careerNames.forEach(name => {
        name.style.cursor = 'pointer';
        name.style.color = '#3498db';
        
        name.addEventListener('click', function() {
            const carreraId = this.closest('tr').querySelector('.badge').textContent.trim();
            if (carreraId) {
                showCarreraDetails(carreraId);
            }
        });
        
        // Efecto hover
        name.addEventListener('mouseenter', function() {
            this.style.textDecoration = 'underline';
        });
        
        name.addEventListener('mouseleave', function() {
            this.style.textDecoration = 'none';
        });
    });
}

/**
 * Inicializar todo cuando la página cargue
 */
function initCarreras() {
    console.log('✅ Módulo de carreras inicializado');
    
    // 1. Configurar botones
    setupActionButtons();
    
    // 2. Configurar íconos
    setupCareerIcons();
    
    // 3. Configurar badges de asignaturas
    setupSubjectBadges();
    
    // 4. Configurar badges de duración
    setupDurationBadges();
    
    // 5. Configurar clics en nombres de carrera
    setupCareerNameClicks();
    
    // 6. Configurar ordenamiento de tabla
    setupTableSorting();
    
    // 7. Configurar búsqueda
    const searchInput = document.querySelector('.search-input');
    if (searchInput) {
        searchInput.addEventListener('keyup', function() {
            filterTable(this.value);
        });
    }
    
    // 8. Mostrar estadísticas
    showStatistics();
    
    // 9. Agregar evento para el botón de exportar si existe
    const exportBtn = document.querySelector('.export-btn');
    if (exportBtn) {
        exportBtn.addEventListener('click', function() {
            alert('📤 Exportando lista de carreras...\n' +
                  'Formato: PDF / Excel / CSV\n' +
                  'Esta función generaría un catálogo académico descargable.');
        });
    }
    
    // 10. Configurar tooltips
    const tooltipElements = document.querySelectorAll('[title]');
    tooltipElements.forEach(element => {
        element.addEventListener('mouseenter', function() {
            console.log('ℹ️ Tooltip:', this.title);
        });
    });
}

// Esperar a que el DOM esté completamente cargado
document.addEventListener('DOMContentLoaded', function() {
    initCarreras();
});