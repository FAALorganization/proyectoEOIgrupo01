
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
function agregarCheck(accion, tipo = null) {
    const idLogin = 1; // Cambia esto por el ID del usuario actual
    const url = accion === 'in' ? '/checkin' : '/checkout';
    const data = tipo ? { tipo, idLogin } : { idLogin };

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: new URLSearchParams(data),
    })
        .then(response => response.text())
        .then(message => {
            document.getElementById('mensaje').innerText = message;
            actualizarHistorial();
        })
        .catch(error => console.error('Error:', error));
}