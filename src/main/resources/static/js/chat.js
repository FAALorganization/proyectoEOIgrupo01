let socket;
let stompClient;
let selectedUserId = null;
const currentUserId = parseInt(document.getElementById("userId")?.value) || null;
//const currentUserId = document.getElementById("userId")?.value || null;
const mensajesMostrados = new Set(); // Para evitar mostrar mensajes duplicados

window.addEventListener("load", () => {
    connectWebSocket();

    const select = document.getElementById("selectUserToChat");
    if (select) {
        select.addEventListener("change", onUserSelectChange);
    }

    const sendButton = document.getElementById("sendMessageBtn");
    if (sendButton) {
        sendButton.addEventListener("click", sendMessage);
    }

    const chatInput = document.getElementById("chatInput");
    if (chatInput) {
        chatInput.addEventListener("keydown", (e) => {
            if (e.key === "Enter") {
                e.preventDefault();
                sendMessage();
            }
        });
    }
});

function connectWebSocket() {
    // 1. currentUserId como string:
    const currentUserId = document.getElementById("userId")?.value || null;

// 2. Conexión:
    //socket = new SockJS('/ws?userId=' + currentUserId);
    socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    //stompClient.connect({ 'usuario': currentUserId }, () => { ... });


    // stompClient.connect({}, () => {
    //     if (currentUserId) {
    //         // stompClient.subscribe(`/user/queue/mensajes/${currentUserId}`, (messageOutput) => {
    //         stompClient.subscribe(`/user/queue/mensajes`, (messageOutput) => {
    //
    //             const mensaje = JSON.parse(messageOutput.body);
    //
    //             if (mensajesMostrados.has(mensaje.id)) return; // Ya mostrado, ignorar
    //             mensajesMostrados.add(mensaje.id);
    //
    //             // Mostrar solo si es del chat abierto
    //             if (selectedUserId) {
    //                 const idSelected = Number(selectedUserId);
    //                 const esChatPrivado =
    //                     (mensaje.idEmisor == idSelected && mensaje.idReceptor == currentUserId) ||
    //                     (mensaje.idEmisor == currentUserId && mensaje.idReceptor == idSelected);
    //
    //                 if (esChatPrivado) {
    //                     mostrarMensaje(mensaje);
    //                 }
    //             }
    //         });
    //     }
    //
    //     stompClient.subscribe('/topic/mensajes', (messageOutput) => {
    //         const mensaje = JSON.parse(messageOutput.body);
    //
    //         if (!selectedUserId && !mensajesMostrados.has(mensaje.id)) {
    //             mensajesMostrados.add(mensaje.id);
    //             mostrarMensaje(mensaje);
    //         }
    //     });
    // });
    console.log(document.getElementById("userId").value)
    stompClient.connect(
        { 'usuario': document.getElementById("userId").value },
        function(frame) {
            console.log("Conectado al WebSocket", frame);
            stompClient.subscribe('/user/queue/mensajes', function(messageOutput) {
                console.log("Mensaje PRIVADO recibido:", messageOutput.body);
            });
        },
        function(error) {
            console.error("Error de conexión STOMP:", error);
        }
    );

    // stompClient.connect({ 'usuario': document.getElementById("userId").value }, () => {
    //     console.log("Conectado al WebSocket");
    //     // 1. Suscripción PRIVADA (sin depender de currentUserId en el momento de conexión)
    //     stompClient.subscribe(`/user/queue/mensajes`, (messageOutput) => {
    //         const mensaje = JSON.parse(messageOutput.body);
    //         console.log("Mensaje PRIVADO recibido:", mensaje); // Debug
    //
    //         if (mensajesMostrados.has(mensaje.id)) return;
    //         mensajesMostrados.add(mensaje.id);
    //
    //         // Mostrar SIEMPRE que el mensaje sea para el usuario actual
    //         const esChatPrivadoRelevante =
    //             String(mensaje.idReceptor) === currentUserId.toString() ||
    //             String(mensaje.idEmisor) === currentUserId.toString();
    //
    //         if (esChatPrivadoRelevante) {
    //             // Si el chat está abierto, muestra el mensaje
    //             if (selectedUserId &&
    //                 ( String(mensaje.idEmisor) === selectedUserId.toString() ||
    //                     String(mensaje.idReceptor) === selectedUserId.toString())) {
    //                 mostrarMensaje(mensaje);
    //             }
    //             // Si no está abierto, opcional: notificar con un badge/sonido
    //             else {
    //                 console.log("Nuevo mensaje de otro chat:", mensaje);
    //                 // Ej: mostrarNotificacion(mensaje);
    //             }
    //         }
    //     });
    //
    //     // 2. Suscripción GRUPAL (sin restricciones de selectedUserId)
    //     stompClient.subscribe('/topic/mensajes', (messageOutput) => {
    //         const mensaje = JSON.parse(messageOutput.body);
    //         console.log("Mensaje GRUPAL recibido:", mensaje); // Debug
    //
    //         if (!mensajesMostrados.has(mensaje.id)) {
    //             mensajesMostrados.add(mensaje.id);
    //             mostrarMensaje(mensaje); // Mostrar siempre mensajes grupales
    //         }
    //     });
    // });

}

function mostrarMensaje(mensaje) {
    const chatBox = document.getElementById("chatMessages");

    // Solo mostrar si el mensaje es del chat actual
    const chatEsPrivado = selectedUserId !== null;

    // if (chatEsPrivado) {
    //     console.log(mensaje.idReceptor, mensaje.idEmisor, currentUserId, selectedUserId);
    //     const receptorMatch = mensaje.idReceptor === currentUserId && mensaje.idEmisor === parseInt(selectedUserId);
    //     const emisorMatch = mensaje.idEmisor === currentUserId && mensaje.idReceptor === parseInt(selectedUserId);
    //     if (!(receptorMatch || emisorMatch)) return;
    // } else {
    //     if (mensaje.idReceptor) return;
    // }

    const mensajeDiv = document.createElement("div");
    mensajeDiv.classList.add("mensaje");

    const esMio = mensaje.idEmisor === currentUserId;
    mensajeDiv.classList.add(esMio ? "mensaje-emisor" : "mensaje-receptor");

    mensajeDiv.innerHTML = `
      <div class="contenido">${mensaje.contenido}</div>
      <div class="fecha">${mensaje.fechaEnvio ? mensaje.fechaEnvio.replace('T', ' ').slice(0, 16) : ''}</div>
    `;

    chatBox.appendChild(mensajeDiv);
    scrollToBottom();
}


function scrollToBottom() {
    const chatBox = document.getElementById("chatMessages");
    chatBox.scrollTop = chatBox.scrollHeight;
}

function sendMessage() {
    const input = document.getElementById("chatInput");
    if (!input) return;
    const contenido = input.value.trim();
    if (contenido === "") return;

    // Crear ID temporal para evitar duplicados en la UI (NO se envía al backend)
    const tempId = 'temp-' + Date.now();

    // Mostrar mensaje en UI con tempId para evitar duplicados
    const mensajeLocal = {
        id: tempId, // solo para UI local
        contenido: contenido,
        idEmisor: parseInt(currentUserId),
        idReceptor:parseInt(selectedUserId) || null,
        esGrupal: !selectedUserId,
        fechaEnvio: new Date().toISOString()
    };


    mensajesMostrados.add(tempId);

    // Crear mensaje para backend SIN id (porque backend espera Integer)
    const mensajeParaBackend = {
        contenido: contenido,
        idEmisor: currentUserId,
        idReceptor: selectedUserId || null,
        esGrupal: !selectedUserId,
        fechaEnvio: mensajeLocal.fechaEnvio
    };

    stompClient.send("/app/enviarMensaje", {}, JSON.stringify(mensajeParaBackend));
    mostrarMensaje(mensajeLocal);
    input.value = "";
}


function limpiarMensajes() {
    const chatBox = document.getElementById("chatMessages");
    chatBox.innerHTML = "";
    mensajesMostrados.clear();
}

function onUserSelectChange(event) {
    selectedUserId = event.target.value;
    console.log("Usuario seleccionado:", selectedUserId);

    if (!selectedUserId) {
        console.log("No se seleccionó usuario válido");
        limpiarMensajes();
        return;
    }

    fetch('/chat/activar', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: new URLSearchParams({ usuarioBId: selectedUserId }).toString()
    })
        .then(response => {
            if (response.ok) {
                location.reload();
                limpiarMensajes();
            } else {
                response.text().then(text => alert('Error: ' + text));
            }
        })
        .catch(error => {
            console.error('Error en fetch:', error);
            alert('Error en la comunicación con el servidor');
        });

    fetch('/chat/mensajes', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: new URLSearchParams({ usuarioBId: selectedUserId }).toString()
    })
        .then(response => {
            if (!response.ok) throw new Error("No se pudieron cargar mensajes");
            return response.json();
        })
        .then(mensajes => {
            mostrarMensajesEnPantalla(mensajes);
        });
}

function cerrarChat(usuarioId) {
    fetch('/chat/desactivar', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: new URLSearchParams({ usuarioBId: usuarioId }).toString()
    })
        .then(response => {
            if (response.ok) {
                location.reload();
            } else {
                response.text().then(text => alert('Error: ' + text));
            }
        })
        .catch(error => {
            console.error('Error en fetch:', error);
            alert('Error en la comunicación con el servidor');
        });
}

function createGroupChat() {
    selectedUserId = null;
    const chatTitle = document.getElementById("chatTitle");
    if (chatTitle) chatTitle.textContent = "Chat grupal";

    document.getElementById("chatInputGroup")?.classList.remove("d-none");
    document.getElementById("selectUserGroup")?.classList.add("d-none");

    limpiarMensajes();
}

function mostrarMensajesEnPantalla(mensajes, nombreUsuario = "") {
    const chatMessages = document.getElementById('chatMessages');
    const usuarioActualId = parseInt(document.getElementById('userId').value);

    chatMessages.innerHTML = ''; // Limpiar mensajes anteriores
    mensajesMostrados.clear();

    if (mensajes.length === 0) {
        const mensajeVacio = document.createElement("div");
        mensajeVacio.textContent = `El chat está vacío. Comienza a chatear con ${nombreUsuario}`;
        mensajeVacio.style.textAlign = "center";
        mensajeVacio.style.marginTop = "50px";
        mensajeVacio.style.fontStyle = "italic";
        mensajeVacio.style.color = "#777";
        chatMessages.appendChild(mensajeVacio);
        return;
    }

    mensajes.forEach(mensaje => {
        mensajesMostrados.add(mensaje.id);
        const esEmisor = mensaje.idEmisor === usuarioActualId;
        const mensajeDiv = document.createElement('div');
        mensajeDiv.classList.add('mensaje', esEmisor ? 'mensaje-emisor' : 'mensaje-receptor');

        mensajeDiv.innerHTML = `
            <div class="contenido">${mensaje.contenido}</div>
            <div class="fecha">${mensaje.fechaEnvio ? mensaje.fechaEnvio.replace('T', ' ').slice(0, 16) : ''}</div>
        `;

        chatMessages.appendChild(mensajeDiv);
    });

    chatMessages.scrollTop = chatMessages.scrollHeight;
}

document.querySelectorAll('.list-group-item').forEach(elemento => {
    elemento.addEventListener('click', (event) => {
        const userId = event.currentTarget.getAttribute('data-user-id');
        const nombre = event.currentTarget.innerText || "este usuario";
        selectedUserId = userId;

        console.log('Usuario seleccionado con ID:', userId);

        fetch('/chat/mensajes', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: new URLSearchParams({
                usuarioBId: userId
            })
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('No se pudieron obtener los mensajes');
                }
                return response.json();
            })
            .then(mensajes => {
                console.log('Mensajes recibidos:', mensajes);
                mostrarMensajesEnPantalla(mensajes, nombre);
            })
            .catch(error => {
                console.error('Error al cargar mensajes:', error);
            });
    });
});
