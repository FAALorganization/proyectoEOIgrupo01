
// Función para actualizar el reloj en tiempo real
function actualizarReloj() {
    const fecha = new Date();
    const horas = String(fecha.getHours()).padStart(2, '0');
    const minutos = String(fecha.getMinutes()).padStart(2, '0');
    const segundos = String(fecha.getSeconds()).padStart(2, '0');
    const horaActual = `${horas}:${minutos}:${segundos}`;
    document.getElementById("reloj").textContent = horaActual;
}

// Llamar la función cada segundo (1000ms)
setInterval(actualizarReloj, 1000);