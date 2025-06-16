/// ticketing js///

// Enhanced form interactions
document.addEventListener('DOMContentLoaded', function() {
    // Check if form exists on the page
    const form = document.querySelector('form');
    if (!form) return;

    const inputs = form.querySelectorAll('input, select, textarea');

    // Add subtle animations to form elements
    inputs.forEach((input, index) => {
        input.style.animationDelay = `${index * 0.1}s`;

        // Enhanced focus effects
        input.addEventListener('focus', function () {
            this.parentElement.style.transform = 'scale(1.02)';
            this.parentElement.style.transition = 'transform 0.3s ease';
        });

        input.addEventListener('blur', function () {
            this.parentElement.style.transform = 'scale(1)';
        });
    });

    // Form validation feedback
    form.addEventListener('submit', function (e) {
        const requiredFields = form.querySelectorAll('[required]');
        let isValid = true;

        requiredFields.forEach(field => {
            if (!field.value.trim()) {
                field.style.borderColor = '#ff4757';
                field.style.boxShadow = '0 0 0 3px rgba(255, 71, 87, 0.1)';
                field.style.transition = 'all 0.3s ease';
                isValid = false;
            } else {
                field.style.borderColor = '#2ed573';
                field.style.boxShadow = '0 0 0 3px rgba(46, 213, 115, 0.1)';
                field.style.transition = 'all 0.3s ease';
            }
        });

        if (!isValid) {
            e.preventDefault();
            // Scroll to first invalid field
            const firstInvalid = form.querySelector('[required]:invalid, [required][value=""]');
            if (firstInvalid) {
                firstInvalid.scrollIntoView({behavior: 'smooth', block: 'center'});
                firstInvalid.focus();
            }
        }
    });

    // Reset form styling on reset
    form.addEventListener('reset', function () {
        setTimeout(() => {
            inputs.forEach(input => {
                input.style.borderColor = '#e0e0e0';
                input.style.boxShadow = 'none';
            });
        }, 100);
    });

    // File input enhancement
    const fileInput = document.getElementById('file');
    if (fileInput) {
        fileInput.addEventListener('change', function () {
            const fileName = this.files[0]?.name;
            if (fileName) {
                const label = this.previousElementSibling;
                if (label) {
                    label.textContent = `Archivo seleccionado: ${fileName}`;
                    label.style.color = '#2ed573';
                }
            }
        });
    }
});