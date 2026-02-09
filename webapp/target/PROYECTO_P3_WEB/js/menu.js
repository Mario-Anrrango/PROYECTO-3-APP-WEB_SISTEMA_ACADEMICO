function updateDateTime() {
            const now = new Date();
            const options = { 
                weekday: 'long', 
                year: 'numeric', 
                month: 'long', 
                day: 'numeric',
                hour: '2-digit',
                minute: '2-digit',
                second: '2-digit'
            };
            document.getElementById('currentDateTime').textContent = 
                now.toLocaleDateString('es-ES', options);
        }
        
        // Actualizar inmediatamente y luego cada segundo
        updateDateTime();
        setInterval(updateDateTime, 1000);
        
        // Efecto hover para las cards
        const cards = document.querySelectorAll('.module-card');
        cards.forEach(card => {
            card.addEventListener('mouseenter', function() {
                this.style.transform = 'translateY(-5px)';
            });
            
            card.addEventListener('mouseleave', function() {
                this.style.transform = 'translateY(0)';
            });
        });
        
        // Mostrar mensaje de bienvenida
        setTimeout(() => {
            console.log('✅ Sistema Académico cargado correctamente');
        }, 1000);