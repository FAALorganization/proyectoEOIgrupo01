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

    // Mostrar tokens generados en el contenedor
    function mostrarTokensEnGestion(tokens) {
        const tokensGeneratedContent = document.getElementById('tokensGeneratedContent');
        const tokensGenerated = document.getElementById('tokensGenerated');

        if (tokensGeneratedContent && tokensGenerated) {
            tokensGenerated.textContent = tokens; // Muestra los tokens en el contenedor
            tokensGeneratedContent.style.display = 'block'; // Muestra el contenedor
        }
    }

    // Función de subida de CSV
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
            mostrarTokensEnGestion(data);
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

    // Exponer función al contexto global para que puedas llamar openConfirmDeleteView desde HTML
    window.openConfirmDeleteView = openConfirmDeleteView;
});

// Funcionalidad jefe
document.addEventListener('DOMContentLoaded', () => {
    const usuariosView = document.getElementById('usuariosView');
    const equiposView = document.getElementById('equiposView');
    const modalTitle = document.getElementById('JefeModalLabel');

    function showUsuarios() {
        usuariosView.style.display = 'block';
        equiposView.style.display = 'none';
        modalTitle.textContent = 'Gestión de usuarios';
    }

    function showEquipos() {
        usuariosView.style.display = 'none';
        equiposView.style.display = 'block';
        modalTitle.textContent = 'Gestión de equipos';
    }

    // Botón para cambiar a equipos
    const btnShowEquipos = document.getElementById('btnShowEquipos');
    if (btnShowEquipos) {
        btnShowEquipos.addEventListener('click', () => {
            showEquipos();
        });
    }

    // Botón "Cancelar" o "Volver" en equipos para regresar a usuarios
    const btnBackUsuarios = document.getElementById('btnBackUsuarios');
    if (btnBackUsuarios) {
        btnBackUsuarios.addEventListener('click', () => {
            showUsuarios();
        });
    }

    // Función para buscar usuario en la tabla de jefe
    function buscarUserJefe() {
        const input = document.getElementById("jefeMyInput");
        if (!input) return;

        const filter = input.value.toUpperCase();
        const table = document.getElementById("jefeMyTable");
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

    const jefeMyInput = document.getElementById("jefeMyInput");
    if (jefeMyInput) {
        jefeMyInput.addEventListener('keyup', buscarUserJefe);
    }
});