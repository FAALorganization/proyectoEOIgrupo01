let stompClient = null;
let currentUserId = null;
let selectedUserId = null;

// Cargar al inicio
window.onload = () => {
    currentUserId = parseInt(document.getElementById("currentUserId").value);
    connectWebSocket();
    loadUsers(); // solo si usas usuarios simulados
};

// Usuarios simulados (elimina esto si usas usuarios reales desde el backend)
const users = ["Ana", "Luis", "Marta", "Carlos"];
function loadUsers() {
    const userList = document.getElementById("userList");
    userList.innerHTML = "";
    users.forEach(user => {
        const li = document.createElement("li");
        li.classList.add("list-group-item", "list-group-item-action");
        li.textContent = user;
        li.onclick = () => selectUserSimulado(user);
        userList.appendChild(li);
    });
}

// Filtrar usuarios
function filterUsers() {
    const search = document.getElementById("searchUser").value.toLowerCase();
    const userList = document.getElementById("userList");
    Array.from(userList.children).forEach(li => {
        li.style.display = li.textContent.toLowerCase().includes(search) ? "" : "none";
    });
}

// Seleccionar usuario real
function selectUser(element) {
    selectedUserId = parseInt(element.getAttribute("data-user-id"));
    document.getElementById("chatTitle").innerText = element.innerText;
    document.getElementById("chatMessages").innerHTML = '';
}

// Simulado
function selectUserSimulado(user) {
    currentChat = user;
    document.getElementById("chatTitle").textContent = user;
    document.getElementById("chatMessages").innerHTML = '';
}

// Crear chat grupal
function createGroupChat() {
    selectedUserId = null;
    document.getElementById("chatTitle").innerText = "Chat grupal";
    document.getElementById("chatMessages").innerHTML = '';
}

// Conexión WebSocket
function connectWebSocket() {
    const socket = new SockJS('/chat-websocket');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, () => {
        console.log('Conectado a WebSocket');

        stompClient.subscribe(`/queue/mensajes/${currentUserId}`, messageOutput => {
            const mensaje = JSON.parse(messageOutput.body);
            mostrarMensaje(mensaje);
        });

        stompClient.subscribe("/topic/mensajes", messageOutput => {
            const mensaje = JSON.parse(messageOutput.body);
            mostrarMensaje(mensaje);
        });
    });
}

// Enviar mensaje
function sendMessage() {
    const input = document.getElementById("chatInput");
    const contenido = input.value.trim();
    if (!contenido) return;

    const mensaje = {
        emisorId: currentUserId,
        receptorId: selectedUserId,
        contenido: contenido,
        esGrupal: selectedUserId === null
    };

    stompClient.send("/app/enviarMensaje", {}, JSON.stringify(mensaje));
    input.value = '';
}

// Mostrar mensaje recibido
function mostrarMensaje(mensaje) {
    const contenedor = document.getElementById("chatMessages");
    const div = document.createElement("div");
    div.className = "mb-2";

    const esPropio = mensaje.emisor.id === currentUserId;
    div.innerHTML = `
        <div class="p-2 ${esPropio ? 'bg-primary text-white text-end ms-auto' : 'bg-white text-start me-auto'} rounded shadow-sm" style="max-width: 60%;">
            ${mensaje.contenido}
        </div>
    `;
    contenedor.appendChild(div);
    contenedor.scrollTop = contenedor.scrollHeight;
}
