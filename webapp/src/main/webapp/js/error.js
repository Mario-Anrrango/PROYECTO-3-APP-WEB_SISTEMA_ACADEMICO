        // Mostrar/ocultar detalles del error
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
        
        // Mostrar información del usuario
        document.addEventListener('DOMContentLoaded', function() {
            // Fecha y hora actual
            const now = new Date();
            const dateTimeStr = now.toLocaleString('es-ES', {
                year: 'numeric',
                month: 'long',
                day: 'numeric',
                hour: '2-digit',
                minute: '2-digit',
                second: '2-digit'
            });
            document.getElementById('currentDateTime').textContent = dateTimeStr;
            
            // Información del navegador
            const userAgent = navigator.userAgent;
            const browserInfo = userAgent.substring(0, 100) + (userAgent.length > 100 ? '...' : '');
            document.getElementById('userAgent').textContent = browserInfo;
            
            // Auto-redireccionar después de 30 segundos
            setTimeout(function() {
                if (confirm('¿Desea volver a la página anterior?')) {
                    history.back();
                }
            }, 30000);
            
            // Mostrar consola de ayuda
            console.log('🔧 Para obtener más información sobre este error:');
            console.log('1. Verifique la consola del navegador (F12)');
            console.log('2. Revise los logs del servidor');
            console.log('3. Contacte al administrador del sistema');
        });
        
        // Análisis de la URL para errores comunes
        const currentUrl = window.location.href;
        const urlParams = new URLSearchParams(window.location.search);
        
        // Sugerencias basadas en el error
        if (currentUrl.includes('404')) {
            console.log('💡 Sugerencia: Verifique la URL e intente nuevamente.');
        } else if (currentUrl.includes('500')) {
            console.log('💡 Sugerencia: Espere unos minutos e intente recargar la página.');
        }