        // Actualizar previsualización del avatar
        function updateAvatarPreview() {
            const nombre = document.getElementById('nombre_docente')?.value || '';
            const apellido = document.getElementById('apellido_docente')?.value || '';
            
            if (nombre || apellido) {
                const iniciales = (nombre.charAt(0) + apellido.charAt(0)).toUpperCase();
                const avatarElement = document.querySelector('.avatar-initials');
                if (avatarElement) {
                    avatarElement.textContent = iniciales;
                }
            }
        }
        
        // Validación del formulario
        function validateDocenteForm() {
            const form = document.getElementById('docenteForm');
            let isValid = true;
            
            // Limpiar errores previos
            const errorElements = document.querySelectorAll('.field-error');
            errorElements.forEach(el => el.remove());
            
            const formControls = form.querySelectorAll('.form-control, .form-select');
            formControls.forEach(control => {
                control.style.borderColor = '';
            });
            
            // Validar ID Docente (solo para creación)
            if (document.getElementById('id_docente') && 
                document.getElementById('id_docente').type === 'number') {
                const id = document.getElementById('id_docente').value;
                if (!id || id <= 0) {
                    showError('id_docente', 'El ID debe ser un número positivo');
                    isValid = false;
                } else if (id > 999999) {
                    showError('id_docente', 'El ID no puede ser mayor a 999999');
                    isValid = false;
                }
            }
            
            // Validar Nombre
            const nombre = document.getElementById('nombre_docente').value;
            if (!nombre || nombre.trim().length < 2) {
                showError('nombre_docente', 'El nombre debe tener al menos 2 caracteres');
                isValid = false;
            } else if (nombre.length > 50) {
                showError('nombre_docente', 'El nombre no puede exceder 50 caracteres');
                isValid = false;
            }
            
            // Validar Apellido
            const apellido = document.getElementById('apellido_docente').value;
            if (!apellido || apellido.trim().length < 2) {
                showError('apellido_docente', 'El apellido debe tener al menos 2 caracteres');
                isValid = false;
            } else if (apellido.length > 50) {
                showError('apellido_docente', 'El apellido no puede exceder 50 caracteres');
                isValid = false;
            }
            
            // Validar Correo
            const correo = document.getElementById('correo').value;
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!correo) {
                showError('correo', 'El correo electrónico es requerido');
                isValid = false;
            } else if (!emailRegex.test(correo)) {
                showError('correo', 'Formato de correo electrónico inválido');
                isValid = false;
            } else if (correo.length > 100) {
                showError('correo', 'El correo no puede exceder 100 caracteres');
                isValid = false;
            }
            
            // Validar Teléfono (opcional)
            const telefono = document.getElementById('telefono')?.value;
            if (telefono && telefono.trim() !== '') {
                const phoneRegex = /^[0-9\s\-+()]{7,15}$/;
                if (!phoneRegex.test(telefono)) {
                    showError('telefono', 'Formato de teléfono inválido');
                    isValid = false;
                }
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
            const form = document.getElementById('docenteForm');
            
            // Agregar validación en tiempo real
            const fieldsToValidate = ['id_docente', 'nombre_docente', 'apellido_docente', 'correo', 'telefono'];
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
            
            // Inicializar avatar si hay datos
            updateAvatarPreview();
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
                case 'id_docente':
                    if (value && (value <= 0 || value > 999999)) {
                        showError(fieldId, 'ID inválido. Debe ser entre 1 y 999999');
                        return false;
                    }
                    break;
                    
                case 'nombre_docente':
                case 'apellido_docente':
                    if (value && value.length > 50) {
                        showError(fieldId, 'Máximo 50 caracteres');
                        return false;
                    }
                    const nameRegex = /^[A-Za-zÁÉÍÓÚáéíóúÑñ\s]+$/;
                    if (value && !nameRegex.test(value)) {
                        showError(fieldId, 'Solo letras y espacios permitidos');
                        return false;
                    }
                    break;
                    
                case 'correo':
                    if (value) {
                        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                        if (!emailRegex.test(value)) {
                            showError(fieldId, 'Formato de correo inválido');
                            return false;
                        }
                    }
                    break;
                    
                case 'telefono':
                    if (value && value.trim() !== '') {
                        const phoneRegex = /^[0-9\s\-+()]{7,15}$/;
                        if (!phoneRegex.test(value)) {
                            showError(fieldId, 'Formato de teléfono inválido');
                            return false;
                        }
                    }
                    break;
            }
            
            return true;
        }
        
        // Confirmar si se abandona el formulario sin guardar
        let formChanged = false;
        document.addEventListener('DOMContentLoaded', function() {
            const form = document.getElementById('docenteForm');
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
            const cancelBtn = document.querySelector('a[href*="/docentes"]');
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