
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

    // Historial de Check-ins
    let historial = [];

    // Función para agregar el check-in o check-out
    function agregarCheck(tipo, tipoEntrada) {
        const nombre = document.getElementById("nombre").value.trim();
        if (nombre === "") {
            alert("Por favor, ingresa tu nombre o ID.");
            return;
        }

        const fecha = new Date();
        const hora = fecha.toLocaleTimeString();
        const mensaje = tipo === 'in' ? `✅ Check-in registrado a las ${hora}` : `⏳ Check-out registrado a las ${hora}`;

        // Verificar si ya hay un check-in registrado para el mismo usuario sin check-out
        const ultimaEntrada = historial.find(entry => entry.nombre === nombre && entry.tipo === 'in' && !entry.checkOut);
        
        if (tipo === 'out' && ultimaEntrada) {
            // Si hay un check-in previo, agregamos el check-out
            ultimaEntrada.checkOut = hora;
            ultimaEntrada.fechaOut = fecha.toLocaleDateString();
        } else if (tipo === 'in') {
            // Si es un check-in, lo agregamos al historial
            historial.push({ 
                nombre, 
                tipo, 
                tipoEntrada,  // Aquí se guarda si el check-in es presencial o remoto
                hora, 
                fecha: fecha.toLocaleDateString(),
                checkOut: null,
                fechaOut: null 
            });
        }

        // Mostrar el mensaje
        document.getElementById("mensaje").innerText = mensaje;

        // Mostrar historial actualizado
        mostrarHistorial();
    }

    // Función para mostrar el historial de check-ins y check-outs
    function mostrarHistorial() {
        const historialList = document.getElementById("historial-list");
        historialList.innerHTML = "";  // Limpiar historial previo

        // Mostrar cada entrada del historial
        historial.forEach((entrada) => {
            const tr = document.createElement("tr");

            // Crear las celdas con la información del check-in
            const tdFecha = document.createElement("td");
            tdFecha.textContent = entrada.fecha;
            tr.appendChild(tdFecha);

            const tdCheckIn = document.createElement("td");
            tdCheckIn.textContent = entrada.tipo === 'in' ? entrada.hora : "-";
            tr.appendChild(tdCheckIn);

            const tdTipo = document.createElement("td");
            tdTipo.textContent = entrada.tipoEntrada;  // Tipo (presencial o remoto)
            tr.appendChild(tdTipo);

            const tdCheckOut = document.createElement("td");
            tdCheckOut.textContent = entrada.checkOut ? entrada.checkOut : "-";
            tr.appendChild(tdCheckOut);

            const tdHorasExtra = document.createElement("td");
            tdHorasExtra.textContent = entrada.checkOut ? calcularHorasExtras(entrada) : "-";
            tr.appendChild(tdHorasExtra);

            // Agregar la fila al tbody
            historialList.appendChild(tr);
        });
    }

    // Función para calcular las horas extras (ejemplo simple)
    function calcularHorasExtras(entrada) {
        if (!entrada.checkOut) return "-";

        // Convertir las horas a milisegundos
        const fechaIn = new Date(`${entrada.fecha} ${entrada.hora}`);
        const fechaOut = new Date(`${entrada.fechaOut} ${entrada.checkOut}`);
        
        // Diferencia en horas
        const diferenciaMs = fechaOut - fechaIn;
        const horasExtras = (diferenciaMs / 1000 / 60 / 60).toFixed(2);

        return horasExtras;
    }

    // Manejo de Check-in a través del Modal
    const modalButtons = document.querySelectorAll(".btn[data-bs-target='#checkinModal']");
    modalButtons.forEach(button => {
        button.addEventListener("click", function() {
            const tipoEntrada = button.innerText.toLowerCase();  // 'presencial' o 'remoto'
            agregarCheck('in', tipoEntrada);  // Llama a la función para agregar check-in con tipo
        });
    });
document.getElementById('busquedaEmpleado').addEventListener('input', function () {
    const filtro = this.value.toLowerCase();
    const filas = document.querySelectorAll('#tablaEmpleados tbody tr');

    filas.forEach(fila => {
        const nombre = fila.children[0].textContent.toLowerCase();
        fila.style.display = nombre.includes(filtro) ? '' : 'none';
    });
});