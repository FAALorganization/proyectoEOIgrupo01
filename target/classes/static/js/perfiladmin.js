document.addEventListener('DOMContentLoaded', () => {
    // Variables para modal y botones
    const gestionModalEl = document.getElementById('gestionModal');
    if (!gestionModalEl) return;

    const gestionModal = new bootstrap.Modal(gestionModalEl);

    const gestionContent = document.getElementById('gestionContent');
    const confirmDeleteContent = document.getElementById('confirmDeleteContent');

    const cancelDeleteBtn = document.getElementById('cancelDeleteBtn');
    const confirmDeleteBtn = document.getElementById('confirmDeleteBtn');
    const closeGestionBtn = document.getElementById('closeGestionBtn');

    const usuarioIdEliminarInput = document.getElementById('usuarioIdEliminar');

    // Abrir modal de gestión completo
    const openGestionModalBtn = document.getElementById('openGestionModalBtn');
    if (openGestionModalBtn) {
        openGestionModalBtn.addEventListener('click', () => {
            showGestionView();
            gestionModal.show();
        });
    }

    // Mostrar vista de gestión
    function showGestionView() {
        toggleView(true);
        document.getElementById('gestionModalLabel').textContent = 'Gestión de usuarios';
    }

    // Mostrar vista de confirmación para eliminar usuario
    function openConfirmDeleteView(usuarioId) {
        toggleView(false);
        document.getElementById('gestionModalLabel').textContent = 'Confirmar Eliminación';
        if (usuarioIdEliminarInput) {
            usuarioIdEliminarInput.value = usuarioId; // Asigna el ID del usuario correctamente
        }
    }

    // Alternar entre vistas del modal
    function toggleView(isGestionView) {
        if (gestionContent) gestionContent.style.display = isGestionView ? 'block' : 'none';
        if (confirmDeleteContent) confirmDeleteContent.style.display = isGestionView ? 'none' : 'block';
        if (cancelDeleteBtn) cancelDeleteBtn.style.display = isGestionView ? 'none' : 'inline-block';
        if (confirmDeleteBtn) confirmDeleteBtn.style.display = isGestionView ? 'none' : 'inline-block';
        if (closeGestionBtn) closeGestionBtn.style.display = isGestionView ? 'inline-block' : 'none';
    }

    // Cancelar eliminación, volver a vista gestión
    if (cancelDeleteBtn) {
        cancelDeleteBtn.addEventListener('click', () => {
            showGestionView();
        });
    }

    // Función para buscar usuario en la tabla
    function buscarUser() {
        const input = document.getElementById("myInput");
        if (!input) return;

        const filter = input.value.toUpperCase();
        const table = document.getElementById("myTable");
        if (!table) return;

        const tr = table.getElementsByTagName("tr");
        for (let i = 0; i < tr.length; i++) {
            const td = tr[i].getElementsByTagName("td")[1];
            if (td) {
                const txtValue = td.textContent || td.innerText;
                tr[i].style.display = txtValue.toUpperCase().indexOf(filter) > -1 ? "" : "none";
            }
        }
    }
    const myInput = document.getElementById("myInput");
    if (myInput) {
        myInput.addEventListener('keyup', buscarUser);
    }

    // Subir CSV
    async function uploadCsv() {
        try {
            const fileInput = document.getElementById('csvFileInput');
            if (!fileInput || fileInput.files.length === 0) {
                alert('Por favor, selecciona al menos un archivo CSV.');
                return;
            }

            const formData = new FormData();
            for (let i = 0; i < fileInput.files.length; i++) {
                formData.append('archivo', fileInput.files[i]);
            }

            const response = await fetch('/upload', {
                method: 'POST',
                body: formData
            });

            if (!response.ok) {
                throw new Error(`Error al subir archivo (status: ${response.status})`);
            }

            const data = await response.text();
            alert(`Subida completada: ${data}`);
            gestionModal.hide();
        } catch (error) {
            console.error('Error al subir archivo:', error);
            alert(`Error al subir archivo: ${error.message}`);
        }
    }
    const uploadCsvBtn = document.getElementById('uploadCsvBtn');
    if (uploadCsvBtn) {
        uploadCsvBtn.addEventListener('click', uploadCsv);
    }

    // Desactivar usuario (Eliminar)
    async function desactivarUsuario() {
        if (!usuarioIdEliminarInput || !usuarioIdEliminarInput.value) {
            alert('No se encontró el ID del usuario.');
            return;
        }

        try {
            const response = await fetch(`/usuarios/desactivar/${usuarioIdEliminarInput.value}`, {
                method: 'PUT'
            });

            if (response.ok) {
                alert('Usuario desactivado correctamente.');
                gestionModal.hide();
                // Aquí podrías actualizar la tabla o recargar la página
            } else {
                alert('Error al desactivar el usuario.');
            }
        } catch (error) {
            console.error('Error:', error);
            alert('Error al desactivar el usuario.');
        }
    }
    if (confirmDeleteBtn) {
        confirmDeleteBtn.addEventListener('click', desactivarUsuario);
    }

    // Botón cerrar modal
    if (closeGestionBtn) {
        closeGestionBtn.addEventListener('click', () => {
            gestionModal.hide();
        });
    }

    // Exponer funciones al contexto global
    window.openConfirmDeleteView = openConfirmDeleteView;
});
function toggleView(isGestionView) {
    if (gestionContent && confirmDeleteContent) {
        // Oculta ambas vistas inicialmente
        gestionContent.classList.remove('show', 'fade-transition');
        confirmDeleteContent.classList.remove('show', 'fade-transition');

        // Usa un pequeño retraso para aplicar la transición
        setTimeout(() => {
            if (isGestionView) {
                gestionContent.classList.add('fade-transition', 'show');
            } else {
                confirmDeleteContent.classList.add('fade-transition', 'show');
            }
        }, 10);
    }

    if (cancelDeleteBtn) cancelDeleteBtn.style.display = isGestionView ? 'none' : 'inline-block';
    if (confirmDeleteBtn) confirmDeleteBtn.style.display = isGestionView ? 'none' : 'inline-block';
    if (closeGestionBtn) closeGestionBtn.style.display = isGestionView ? 'inline-block' : 'none';
}