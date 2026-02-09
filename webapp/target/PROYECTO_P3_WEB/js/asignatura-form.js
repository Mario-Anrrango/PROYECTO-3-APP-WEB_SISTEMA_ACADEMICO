        // Validación del formulario
        function validateAsignaturaForm() {
            const form = document.getElementById('asignaturaForm');
            let isValid = true;
            
            // Limpiar errores previos
            const errorElements = document.querySelectorAll('.field-error');
            errorElements.forEach(el => el.remove());
            
            const formControls = form.querySelectorAll('.form-control, .form-select');
            formControls.forEach(control => {
                control.style.borderColor = '';
            });
            
            // Validar ID Asignatura (solo para creación)
            if (document.getElementById('id_asignatura') && 
                document.getElementById('id_asignatura').type === 'number') {
                const id = document.getElementById('id_asignatura').value;
                if (!id || id <= 0) {
                    showError('id_asignatura', 'El ID debe ser un número positivo');
                    isValid = false;
                } else if (id > 999999) {
                    showError('id_asignatura', 'El ID no puede ser mayor a 999999');
                    isValid = false;
                }
            }
            
            // Validar Nombre
            const nombre = document.getElementById('nombre_asignatura').value;
            if (!nombre || nombre.trim().length < 3) {
                showError('nombre_asignatura', 'El nombre debe tener al menos 3 caracteres');
                isValid = false;
            } else if (nombre.length > 100) {
                showError('nombre_asignatura', 'El nombre no puede exceder 100 caracteres');
                isValid = false;
            }
            
            // Validar Créditos
            const creditos = document.getElementById('creditos').value;
            if (!creditos || creditos < 1 || creditos > 10) {
                showError('creditos', 'Los créditos deben estar entre 1 y 10');
                isValid = false;
            }
            
            // Validar Docente
            const docente = document.getElementById('id_docente').value;
            if (!docente) {
                showError('id_docente', 'Seleccione un docente');
                isValid = false;
            }
            
            // Validar Carrera
            const carrera = document.getElementById('id_carrera').value;
            if (!carrera) {
                showError('id_carrera', 'Seleccione una carrera');
                isValid = false;
            }
            
            if (!isValid) {
                // Desplazar al primer error
                const firstError = document.querySelector('.field-error');
                if (firstError) {
                    firstError.scrollIntoView({ behavior: 'smooth', block: 'center' });
                }
                
                // Mostrar alerta
                alert('Por favor, corrija los errores en el formulario antes de continuar.');
            }
            
            return isValid;
        }
        
        // Mostrar error en un campo
        function showError(fieldId, message) {
            const field = document.getElementById(fieldId);
            if (field) {
                field.style.borderColor = 'var(--danger-color)';
                
                const errorDiv = document.createElement('div');
                errorDiv.className = 'field-error';
                errorDiv.textContent = message;
                
                // Insertar después del campo
                field.parentNode.insertBefore(errorDiv, field.nextSibling);
            }
        }
        
        // Validación en tiempo real
        document.addEventListener('DOMContentLoaded', function() {
            const form = document.getElementById('asignaturaForm');
            
            // Agregar validación en tiempo real
            const fieldsToValidate = ['id_asignatura', 'nombre_asignatura', 'creditos', 'id_docente', 'id_carrera'];
            fieldsToValidate.forEach(fieldId => {
                const field = document.getElementById(fieldId);
                if (field) {
                    field.addEventListener('blur', function() {
                        validateField(this);
                    });
                }
            });
            
            // Prevenir envío con Enter fuera de los campos de texto
            form.addEventListener('keydown', function(e) {
                if (e.key === 'Enter' && e.target.type !== 'textarea') {
                    e.preventDefault();
                }
            });
        });
        
        // Validar campo individual
        function validateField(field) {
            const fieldId = field.id;
            const value = field.value;
            
            // Limpiar error previo
            const existingError = field.parentNode.querySelector('.field-error');
            if (existingError) {
                existingError.remove();
            }
            
            field.style.borderColor = '';
            
            // Validar según el campo
            switch(fieldId) {
                case 'id_asignatura':
                    if (value && (value <= 0 || value > 999999)) {
                        showError(fieldId, 'ID inválido. Debe ser entre 1 y 999999');
                        return false;
                    }
                    break;
                    
                case 'nombre_asignatura':
                    if (value && value.length > 100) {
                        showError(fieldId, 'Máximo 100 caracteres');
                        return false;
                    }
                    break;
                    
                case 'creditos':
                    if (value && (value < 1 || value > 10)) {
                        showError(fieldId, 'Entre 1 y 10 créditos');
                        return false;
                    }
                    break;
            }
            
            return true;
        }
        
        // Confirmar si se abandona el formulario sin guardar
        let formChanged = false;
        document.addEventListener('DOMContentLoaded', function() {
            const form = document.getElementById('asignaturaForm');
            const inputs = form.querySelectorAll('input, select, textarea');
            
            inputs.forEach(input => {
                input.addEventListener('input', () => formChanged = true);
                input.addEventListener('change', () => formChanged = true);
            });
            
            // Confirmar antes de abandonar la página
            window.addEventListener('beforeunload', function(e) {
                if (formChanged) {
                    e.preventDefault();
                    e.returnValue = 'Tienes cambios sin guardar. ¿Seguro que quieres abandonar la página?';
                }
            });
            
            // Confirmar antes de cancelar
            const cancelBtn = document.querySelector('a[href*="/asignaturas"]');
            if (cancelBtn) {
                cancelBtn.addEventListener('click', function(e) {
                    if (formChanged) {
                        if (!confirm('Tienes cambios sin guardar. ¿Seguro que quieres cancelar?')) {
                            e.preventDefault();
                        }
                    }
                });
            }
        });