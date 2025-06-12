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

function abrirModal(idProyecto) {
    document.getElementById('modalProyectoId').value = idProyecto;
    document.getElementById('modalSubida').style.display = 'flex';
}

function cerrarModal() {
    document.getElementById('modalSubida').style.display = 'none';
}

// Cerrar al hacer clic fuera del modal
window.onclick = function(event) {
    const modal = document.getElementById('modalSubida');
    if (event.target === modal) {
        cerrarModal();
    }
}
