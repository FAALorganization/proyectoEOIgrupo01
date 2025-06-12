let socket;
let stompClient;
let selectedUserId = null;
const currentUserId = document.getElementById("userId")?.value || null;

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
    socket = new SockJS("/chat-websocket");
    stompClient = Stomp.over(socket);

    stompClient.connect({}, () => {
        stompClient.subscribe('/topic/mensajes', (messageOutput) => {
            const mensaje = JSON.parse(messageOutput.body);
            mostrarMensaje(mensaje);
        });
    });
}

function mostrarMensaje(mensaje) {
    const mensajeDiv = document.createElement("div");
    mensajeDiv.classList.add("mb-2");

    const esMio = mensaje.emisorId === currentUserId;
    mensajeDiv.classList.add(esMio ? "text-end" : "text-start");

    mensajeDiv.innerHTML = `
      <div class="d-inline-block p-2 rounded ${esMio ? 'bg-primary text-white' : 'bg-white border'}">
          <strong>${mensaje.emisorNombre}</strong><br/>
          ${mensaje.contenido}
      </div>
  `;

    document.getElementById("chatMessages").appendChild(mensajeDiv);
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

    const mensaje = {
        contenido: contenido,
        emisorId: currentUserId,
        receptorId: selectedUserId || null,
        esGrupal: !selectedUserId
    };

    stompClient.send("/app/chat", {}, JSON.stringify(mensaje));
    input.value = "";
}

function limpiarMensajes() {
    const chatBox = document.getElementById("chatMessages");
    chatBox.innerHTML = "";
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
