
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

// Función para actualizar la tabla después de un check
function actualizarTablaHistorial() {
    const userId = document.getElementById('userId').value;

    fetch(`/api/checkins/${userId}`)
        .then(response => response.json())
        .then(data => {
            const tbody = document.getElementById('historial-list');
            tbody.innerHTML = ''; // Limpiar la tabla

            data.forEach(checkin => {
                const fila = document.createElement('tr');
                fila.innerHTML = `
                    <td>${checkin.fechaFormatted}</td>
                    <td>${checkin.horaEntradaFormatted || ''}</td>
                    <td>${checkin.tipo || ''}</td>
                    <td>${checkin.horaSalidaFormatted || ''}</td>
                `;
                tbody.appendChild(fila);
            });
        })
        .catch(error => console.error('Error actualizando tabla:', error));
}

// Función para realizar check-in/check-out
function agregarCheck(accion, tipo = null) {
    const url = accion === 'in' ? '/checkin' : '/checkout';
    const data = tipo ? { tipo } : {};

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
            // Actualizar la tabla después de un check exitoso
            actualizarTablaHistorial();
        })
        .catch(error => {
            console.error('Error:', error);
            document.getElementById('mensaje').innerText = 'Error al procesar la solicitud';
        });
}

// Mantener la funcionalidad de búsqueda de empleados
document.getElementById('busquedaEmpleado')?.addEventListener('input', function () {
    const filtro = this.value.toLowerCase();
    const filas = document.querySelectorAll('#tablaEmpleados tbody tr');

    filas.forEach(fila => {
        const nombre = fila.children[0].textContent.toLowerCase();
        fila.style.display = nombre.includes(filtro) ? '' : 'none';
    });
});

// Función para cargar registros de empleados
function cargarRegistrosEmpleado(idEmpleado) {
    if (!idEmpleado) {
        alert("Por favor selecciona un empleado válido.");
        return;
    }

    fetch(`/api/checkins/${idEmpleado}`)
        .then(response => {
            if (!response.ok) {
                throw new Error(`Error al obtener los registros del empleado. Código: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            const tabla = document.getElementById("empleadoHistorial");
            tabla.innerHTML = "";

            if (data.length === 0) {
                const fila = document.createElement("tr");
                const celda = document.createElement("td");
                celda.colSpan = 4;
                celda.textContent = "No hay registros disponibles para este empleado.";
                fila.appendChild(celda);
                tabla.appendChild(fila);
                return;
            }

            data.forEach(checkin => {
                const fila = document.createElement("tr");
                fila.innerHTML = `
                    <td>${checkin.fechaFormatted}</td>
                    <td>${checkin.horaEntradaFormatted || ''}</td>
                    <td>${checkin.tipo || ''}</td>
                    <td>${checkin.horaSalidaFormatted || ''}</td>
                `;
                tabla.appendChild(fila);
            });
        })
        .catch(error => {
            console.error(error);
            alert("No se pudieron cargar los registros del empleado. Detalles: " + error.message);
        });
}