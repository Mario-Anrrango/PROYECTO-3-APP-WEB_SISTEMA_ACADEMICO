        // Contador de caracteres para descripción
        const descripcionTextarea = document.getElementById('descripcion');
        const charCountElement = document.getElementById('charCount');
        
        if (descripcionTextarea && charCountElement) {
            descripcionTextarea.addEventListener('input', function() {
                const currentLength = this.value.length;
                charCountElement.textContent = `${currentLength}/500 caracteres`;
                
                if (currentLength > 500) {
                    charCountElement.style.color = 'var(--danger-color)';
                    this.style.borderColor = 'var(--danger-color)';
                } else {
                    charCountElement.style.color = 'var(--gray-color)';
                    this.style.borderColor = '';
                }
            });
        }
        
        // Validación del formulario
        function validateCarreraForm() {
            const form = document.getElementById('carreraForm');
            let isValid = true;
            
            // Limpiar errores previos
            const errorElements = document.querySelectorAll('.field-error');
            errorElements.forEach(el => el.remove());
            
            const formControls = form.querySelectorAll('.form-control, .form-select, .faculty-select, .textarea-control');
            formControls.forEach(control => {
                control.style.borderColor = '';
            });
            
            // Validar ID Carrera (solo para creación)
            if (document.getElementById('id_carrera') && 
                document.getElementById('id_carrera').type === 'number') {
                const id = document.getElementById('id_carrera').value;
                if (!id || id <= 0) {
                    showError('id_carrera', 'El ID debe ser un número positivo');
                    isValid = false;
                } else if (id > 999999) {
                    showError('id_carrera', 'El ID no puede ser mayor a 999999');
                    isValid = false;
                }
            }
            
            // Validar Nombre de Carrera
            const nombre = document.getElementById('nombre_carrera').value;
            if (!nombre || nombre.trim().length < 3) {
                showError('nombre_carrera', 'El nombre debe tener al menos 3 caracteres');
                isValid = false;
            } else if (nombre.length > 100) {
                showError('nombre_carrera', 'El nombre no puede exceder 100 caracteres');
                isValid = false;
            }
            
            // Validar Código de Carrera (opcional)
            const codigo = document.getElementById('codigo_carrera')?.value;
            if (codigo && codigo.length > 20) {
                showError('codigo_carrera', 'El código no puede exceder 20 caracteres');
                isValid = false;
            }
            
            // Validar Facultad
            const facultad = document.getElementById('facultad').value;
            if (!facultad) {
                showError('facultad', 'Seleccione una facultad');
                isValid = false;
            }
            
            // Validar Duración
            const duracion = document.getElementById('duracion').value;
            if (!duracion) {
                showError('duracion', 'Seleccione la duración de la carrera');
                isValid = false;
            }
            
            // Validar Créditos Totales (opcional)
            const creditos = document.getElementById('creditos_totales')?.value;
            if (creditos && (creditos < 1 || creditos > 500)) {
                showError('creditos_totales', 'Los créditos deben estar entre 1 y 500');
                isValid = false;
            }
            
            // Validar Descripción (opcional)
            const descripcion = document.getElementById('descripcion')?.value;
            if (descripcion && descripcion.length > 500) {
                showError('descripcion', 'La descripción no puede exceder 500 caracteres');
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
            const form = document.getElementById('carreraForm');
            
            // Agregar validación en tiempo real
            const fieldsToValidate = ['id_carrera', 'nombre_carrera', 'codigo_carrera', 'creditos_totales', 'descripcion'];
            fieldsToValidate.forEach(fieldId => {
                const field = document.getElementById(fieldId);
                if (field) {
                    field.addEventListener('blur', function() {
                        validateField(this);
                    });
                }
            });
            
            // Validar select de facultad
            const facultadSelect = document.getElementById('facultad');
            if (facultadSelect) {
                facultadSelect.addEventListener('change', function() {
                    validateField(this);
                });
            }
            
            // Validar select de duración
            const duracionSelect = document.getElementById('duracion');
            if (duracionSelect) {
                duracionSelect.addEventListener('change', function() {
                    validateField(this);
                });
            }
            
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
                case 'id_carrera':
                    if (value && (value <= 0 || value > 999999)) {
                        showError(fieldId, 'ID inválido. Debe ser entre 1 y 999999');
                        return false;
                    }
                    break;
                    
                case 'nombre_carrera':
                    if (value && value.length > 100) {
                        showError(fieldId, 'Máximo 100 caracteres');
                        return false;
                    }
                    break;
                    
                case 'codigo_carrera':
                    if (value && value.length > 20) {
                        showError(fieldId, 'Máximo 20 caracteres');
                        return false;
                    }
                    break;
                    
                case 'creditos_totales':
                    if (value && (value < 1 || value > 500)) {
                        showError(fieldId, 'Entre 1 y 500 créditos');
                        return false;
                    }
                    break;
                    
                case 'descripcion':
                    if (value && value.length > 500) {
                        showError(fieldId, 'Máximo 500 caracteres');
                        return false;
                    }
                    break;
                    
                case 'facultad':
                    if (field.tagName === 'SELECT' && !value) {
                        showError(fieldId, 'Seleccione una facultad');
                        return false;
                    }
                    break;
                    
                case 'duracion':
                    if (field.tagName === 'SELECT' && !value) {
                        showError(fieldId, 'Seleccione la duración');
                        return false;
                    }
                    break;
            }
            
            return true;
        }
        
        // Confirmar si se abandona el formulario sin guardar
        let formChanged = false;
        document.addEventListener('DOMContentLoaded', function() {
            const form = document.getElementById('carreraForm');
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
            const cancelBtn = document.querySelector('a[href*="/carreras"]');
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