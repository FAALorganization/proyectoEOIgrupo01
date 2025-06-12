let socket;
let selectedUserId = null;
let currentUserId = document.getElementById("userId")?.value || null;

// Conectar al WebSocket al cargar la página
window.addEventListener("load", () => {
    connectWebSocket();
});

function connectWebSocket() {
    socket = new SockJS("/chat-websocket");
    const stompClient = Stomp.over(socket);

    stompClient.connect({}, () => {
        stompClient.subscribe('/topic/mensajes', (messageOutput) => {
            const mensaje = JSON.parse(messageOutput.body);
            mostrarMensaje(mensaje);
        });
    });

    window.stompClient = stompClient;
}

// Mostrar un mensaje en el área del chat
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

// Enviar mensaje
function sendMessage() {
    const input = document.getElementById("chatInput");
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

// Filtrar usuarios en la lista
function filterUsers() {
    const filter = document.getElementById("searchUser").value.toLowerCase();
    const users = document.getElementById("userList").getElementsByTagName("li");

    for (let i = 0; i < users.length; i++) {
        const name = users[i].textContent.toLowerCase();
        users[i].style.display = name.includes(filter) ? "" : "none";
    }
}

// Seleccionar usuario desde lista
function selectUser(element) {
    const userId = element.getAttribute("data-user-id");
    selectedUserId = userId;

    document.getElementById("chatTitle").textContent = element.textContent;

    document.getElementById("chatInputGroup").classList.remove("d-none");
    document.getElementById("selectUserGroup").classList.add("d-none");

    limpiarMensajes();
    // Aquí podrías hacer un fetch para obtener mensajes previos con ese usuario
}

// Seleccionar usuario desde <select>
function startChatWithUser(selectElement) {
    const userId = selectElement.value;

    if (userId === "") return;

    const selectedOption = selectElement.options[selectElement.selectedIndex];
    selectedUserId = userId;

    document.getElementById("chatTitle").textContent = selectedOption.text;

    document.getElementById("chatInputGroup").classList.remove("d-none");
    document.getElementById("selectUserGroup").classList.add("d-none");

    limpiarMensajes();
}

// Mostrar <select> para iniciar nuevo chat
function createGroupChat() {
    selectedUserId = null;
    document.getElementById("chatTitle").textContent = "Chat grupal";

    document.getElementById("chatInputGroup").classList.remove("d-none");
    document.getElementById("selectUserGroup").classList.add("d-none");

    limpiarMensajes();
}

// Limpiar mensajes del chat actual
function limpiarMensajes() {
    const chatBox = document.getElementById("chatMessages");
    chatBox.innerHTML = "";
}
