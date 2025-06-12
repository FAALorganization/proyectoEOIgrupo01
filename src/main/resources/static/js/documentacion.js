function validarFechas() {
    const fechaInicio = document.getElementById("fechaInicio").value;
    const fechaFin = document.getElementById("fechaFin").value;
    const hoy = new Date().toISOString().split("T")[0];

    if (fechaInicio < hoy) {
        alert("⚠️ La fecha de inicio no puede ser anterior a hoy.");
        return false;
    }
    if (fechaFin < fechaInicio) {
        alert("⚠️ La fecha de fin no puede ser anterior a la fecha de inicio.");
        return false;
    }
    if (new Date(fechaFin).getFullYear() > 2100) {
        alert("⚠️ La fecha de fin no puede ser mayor al año 2100.");
        return false;
    }
    return true;
}

function cerrarModal() {
    document.getElementById('modalSubida').style.display = 'none';
}

// Cerrar modal al hacer click fuera del contenido
window.onclick = function(event) {
    const modal = document.getElementById('modalSubida');
    if (event.target === modal) {
        cerrarModal();
    }
}

//modal para subir documentos
function abrirModal(){
    const modalJustificacionEl = document.getElementById('aus-modal-justificacion');
    const ausModalJustificacion = modalJustificacionEl ? new bootstrap.Modal(modalJustificacionEl) : null;
    const botones = document.querySelectorAll('.aus-btn-justificar');
    console.log(modalJustificacionEl, ausModalJustificacion, botones);
    console.log("IGUALDAD: " + (botones && ausModalIncidencias && ausModalJustificacion));


    if (botones && ausModalIncidencias && ausModalJustificacion) {
        botones.forEach(btn => {
            btn.addEventListener('click', () => {
                console.log("NO FUNCIONA");
                ausModalIncidencias.hide();
                btnIncidencias.focus();
                ausModalJustificacion.show();
            });
        });
    }


    const btnVolver = document.getElementById('aus-btn-volver');
    if (btnVolver && ausModalIncidencias && ausModalJustificacion) {
        btnVolver.addEventListener('click', () => {
            ausModalJustificacion.hide();
            btnIncidencias.focus();
            ausModalIncidencias.show();
        });
    }

    const formJustificacion = document.getElementById('aus-form-justificacion');
    if (formJustificacion && ausModalJustificacion) {
        formJustificacion.addEventListener('submit', function (e) {
            e.preventDefault();

            const asunto = document.getElementById('aus-asunto')?.value.trim();
            const descripcion = document.getElementById('aus-descripcion')?.value.trim();
            const archivos = document.getElementById('aus-archivos')?.files;

            if (!asunto || !descripcion) {
                alert("Por favor completa asunto y descripción.");
                return;
            }

            ausModalJustificacion.hide();
        });
    }

    document.addEventListener('DOMContentLoaded', () => {
        abrirModal();
    });
}

alert ("El modal de justificación se ha abierto correctamente. Puedes proceder a completar el formulario.");