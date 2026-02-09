/**
 * Mostrar detalles de una asignatura
 */
function showAsignaturaDetails(asignaturaId) {
    alert('Detalles de la asignatura ID: ' + asignaturaId + '\n\n' +
          'Información detallada:\n' +
          '• Nombre completo\n' + 
          '• Créditos\n' +
          '• Docente asignado\n' +
          '• Carrera\n' +
          '• Horarios\n' +
          '• Prerrequisitos\n' +
          '• Tipo (Obligatoria/Optativa/Electiva)\n' +
          '• Descripción');
}

/**
 * Configurar eventos de los botones
 */
function setupActionButtons() {
    // 1. Botones de VER DETALLES
    const detailButtons = document.querySelectorAll('.details-btn');
    detailButtons.forEach(button => {
        button.addEventListener('click', function() {
            const asignaturaId = this.getAttribute('data-id');
            if (asignaturaId) {
                showAsignaturaDetails(asignaturaId);
            }
        });
    });
    
    // 2. Botones de ELIMINAR
    const deleteButtons = document.querySelectorAll('.delete-btn');
    deleteButtons.forEach(button => {
        button.addEventListener('click', function(event) {
            // Prevenir que el link se ejecute inmediatamente
            event.preventDefault();
            
            const asignaturaId = this.getAttribute('data-id');
            const asignaturaName = this.getAttribute('data-name');
            const deleteUrl = this.getAttribute('href');
            
            // Mostrar confirmación
            if (confirm('¿Está seguro de eliminar la asignatura "' + asignaturaName + '"? Esta acción no se puede deshacer.')) {
                // Si confirma, redirigir a la URL de eliminación
                window.location.href = deleteUrl;
            }
        });
    });
}

/**
 * Filtrar la tabla de asignaturas
 */
function filterTable(searchTerm) {
    const table = document.getElementById('asignaturasTable');
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
 * Configurar colores de badges de créditos
 */
function setupCreditsBadges() {
    const creditBadges = document.querySelectorAll('.credits-badge');
    creditBadges.forEach(badge => {
        const text = badge.textContent || '';
        const credits = parseInt(text.match(/\d+/)?.[0] || 0);
        
        // Colores según créditos
        if (credits >= 8) {
            badge.style.background = '#ffebee';
            badge.style.color = '#e74c3c';
            badge.title = 'Asignatura con muchos créditos';
        } else if (credits >= 5) {
            badge.style.background = '#fff8e1';
            badge.style.color = '#f39c12';
            badge.title = 'Asignatura con créditos moderados';
        } else {
            badge.style.background = '#e8f5e9';
            badge.style.color = '#27ae60';
            badge.title = 'Asignatura con pocos créditos';
        }
        
        // Agregar tooltip
        badge.addEventListener('mouseenter', function() {
            this.style.transform = 'translateY(-2px)';
            this.style.boxShadow = '0 4px 8px rgba(0,0,0,0.1)';
        });
        
        badge.addEventListener('mouseleave', function() {
            this.style.transform = 'translateY(0)';
            this.style.boxShadow = 'none';
        });
    });
}

/**
 * Configurar ordenamiento de tabla
 */
function setupTableSorting() {
    const table = document.getElementById('asignaturasTable');
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
    const table = document.getElementById('asignaturasTable');
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
        
        // Para columna de créditos (índice 2)
        if (columnIndex === 2) {
            const creditsA = parseInt(textA.match(/\d+/)?.[0] || 0);
            const creditsB = parseInt(textB.match(/\d+/)?.[0] || 0);
            return isAscending ? creditsA - creditsB : creditsB - creditsA;
        }
        
        // Para otras columnas
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
 * Calcular y mostrar estadísticas
 */
function showStatistics() {
    const totalAsignaturas = document.querySelectorAll('#asignaturasTable tbody tr').length;
    let totalCreditos = 0;
    
    // Calcular total de créditos
    const creditBadges = document.querySelectorAll('.credits-badge');
    creditBadges.forEach(badge => {
        const text = badge.textContent || '';
        const creditos = parseInt(text.match(/\d+/)?.[0] || 0);
        totalCreditos += creditos;
    });
    
    const promedioCreditos = totalAsignaturas > 0 ? (totalCreditos / totalAsignaturas).toFixed(1) : 0;
    
    console.log('📊 Estadísticas de asignaturas:');
    console.log('• Total de asignaturas:', totalAsignaturas);
    console.log('• Total de créditos:', totalCreditos);
    console.log('• Promedio de créditos por asignatura:', promedioCreditos);
    
    // Actualizar valores en los stat-items si existen
    const statValues = document.querySelectorAll('.stat-value');
    if (statValues.length >= 2) {
        if (statValues[0].textContent === '24') { // Valor por defecto
            statValues[0].textContent = totalAsignaturas;
        }
        if (statValues[1].textContent === '156') { // Valor por defecto para créditos
            statValues[1].textContent = totalCreditos;
        }
    }
}

/**
 * Agregar funcionalidad de clic en nombres de asignatura
 */
function setupSubjectNameClicks() {
    const subjectNames = document.querySelectorAll('.subject-name');
    subjectNames.forEach(name => {
        name.style.cursor = 'pointer';
        name.style.color = '#3498db';
        
        name.addEventListener('click', function() {
            const asignaturaId = this.closest('tr').querySelector('.badge').textContent.trim();
            if (asignaturaId) {
                showAsignaturaDetails(asignaturaId);
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
function initAsignaturas() {
    console.log('✅ Módulo de asignaturas inicializado');
    
    // 1. Configurar botones
    setupActionButtons();
    
    // 2. Configurar badges de créditos
    setupCreditsBadges();
    
    // 3. Configurar clics en nombres de asignatura
    setupSubjectNameClicks();
    
    // 4. Configurar ordenamiento de tabla
    setupTableSorting();
    
    // 5. Configurar búsqueda
    const searchInput = document.querySelector('.search-input');
    if (searchInput) {
        searchInput.addEventListener('keyup', function() {
            filterTable(this.value);
        });
    }
    
    // 6. Mostrar estadísticas
    showStatistics();
    
    // 7. Agregar evento para el botón de exportar si existe
    const exportBtn = document.querySelector('.export-btn');
    if (exportBtn) {
        exportBtn.addEventListener('click', function() {
            alert('📤 Exportando lista de asignaturas...\n' +
                  'Formato: PDF / Excel / CSV\n' +
                  'Esta función generaría un archivo descargable.');
        });
    }
    
    // 8. Configurar tooltips
    const tooltipElements = document.querySelectorAll('[title]');
    tooltipElements.forEach(element => {
        element.addEventListener('mouseenter', function() {
            console.log('ℹ️ Tooltip:', this.title);
        });
    });
}

// Esperar a que el DOM esté completamente cargado
document.addEventListener('DOMContentLoaded', function() {
    initAsignaturas();
});