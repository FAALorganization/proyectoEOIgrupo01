document.addEventListener("DOMContentLoaded", function(event) {

    const showNavbar = (toggleId, navId, bodyId, headerId) =>{
        const toggle = document.getElementById(toggleId),
            nav = document.getElementById(navId),
            bodypd = document.getElementById(bodyId),
            headerpd = document.getElementById(headerId)

        // Validate that all variables exist
        if(toggle && nav && bodypd && headerpd){
            toggle.addEventListener('click', ()=>{
                // show navbar
                nav.classList.toggle('show')
                // change icon
                toggle.classList.toggle('bx-x')
                // add padding to body
                bodypd.classList.toggle('body-pd')
                // add padding to header
                headerpd.classList.toggle('body-pd')
            })
        }
    }

    showNavbar('header-toggle','nav-bar','body-pd','header')

    /*===== LINK ACTIVE =====*/
    const linkColor = document.querySelectorAll('.nav_link')

    function colorLink(){
        if(linkColor){
            linkColor.forEach(l=> l.classList.remove('active'))
            this.classList.add('active')
        }
    }
    linkColor.forEach(l=> l.addEventListener('click', colorLink))

    // Your code to run since DOM is loaded and ready
});

counter = 0;
const buttonNav = document.getElementById("header-toggle");
buttonNav.addEventListener("click", function(){

    if (counter%2 == 0){
        document.querySelectorAll(".nav_name").forEach(link =>{
            link.style.color = "white";
            link.classList.add("textenter");
        })
        counter += 1;
    }else {
        document.querySelectorAll(".nav_name").forEach(link =>{
            link.style.color = "#252323";
            link.classList.remove("textenter");
        })
        counter += 1;
    }
});

let currentChat = '';
let chats = {}; // Para almacenar los chats individuales y grupales
let selectedUsersForGroup = []; // Para almacenar los usuarios seleccionados para el chat grupal

function toggleUserChat(user) {
    const chatExists = chats[user];
    if (chatExists) {
        // Si el chat ya existe, no hacer nada o preguntar si se quiere abrir el chat
        const openChat = confirm(`¿Quieres abrir el chat con ${user}?`);
        if (openChat) {
            currentChat = user;
            loadChat();
        }
    } else {
        // Si el chat no existe, preguntar si se quiere abrir uno
        const openChat = confirm(`¿Quieres iniciar un chat con ${user}?`);
        if (openChat) {
            chats[user] = { type: 'individual', users: [user], messages: [] };
            currentChat = user;
            loadChat();
        }
    }
}

function createGroupChat() {
    const checkboxes = document.querySelectorAll('.user-checkbox:checked');
    selectedUsersForGroup = Array.from(checkboxes).map(checkbox => checkbox.closest('li').innerText.trim());

    if (selectedUsersForGroup.length > 0) {
        const groupName = selectedUsersForGroup.join(', ');
        if (!chats[groupName]) {
            // Crear un nuevo chat grupal
            chats[groupName] = { type: 'group', users: selectedUsersForGroup, messages: [] };
        }
        currentChat = groupName;
        loadChat();
        $('#createGroupChatModal').modal('hide'); // Cerrar el modal
    }
}

function loadChat() {
    // Actualizar el título y el área de chat
    const chat = chats[currentChat];
    document.getElementById('chat-title').innerText = `Chat con ${chat.type === 'individual' ? currentChat : `Grupo: ${currentChat}`}`;

    // Generar los mensajes
    const chatBox = document.getElementById('chat-box');
    chatBox.innerHTML = ''; // Limpiar el área de chat
    chat.messages.forEach(msg => {
        const messageElement = document.createElement('div');
        messageElement.innerText = msg;
        chatBox.appendChild(messageElement);
    });

    chatBox.scrollTop = chatBox.scrollHeight; // Hacer scroll hacia abajo

    // Actualizar las pestañas de los chats
    updateChatTabs();
}

function sendMessage() {
    const message = document.getElementById('message-input').value;
    if (message && currentChat) {
        chats[currentChat].messages.push(`Tú: ${message}`);
        loadChat(); // Recargar el chat
        document.getElementById('message-input').value = '';  // Limpiar el input
    }
}

function updateChatTabs() {
    const chatTabs = document.getElementById('chat-tabs');
    chatTabs.innerHTML = ''; // Limpiar las pestañas anteriores

    for (const chatName in chats) {
        const chat = chats[chatName];
        const tab = document.createElement('div');
        tab.classList.add('chat-tab', 'd-inline-flex', 'align-items-center', 'mr-2');

        // Crear el botón de la pestaña
        const tabButton = document.createElement('button');
        tabButton.classList.add('btn', 'btn-secondary', 'mr-2');
        tabButton.innerText = chat.type === 'individual' ? chatName : `Grupo: ${chatName}`;
        tabButton.onclick = () => {
            currentChat = chatName;
            loadChat();
        };

        // Crear el botón de eliminar más estético
        const closeButton = document.createElement('button');
        closeButton.classList.add('btn', 'btn-sm', 'btn-danger', 'rounded-circle', 'ml-1');
        closeButton.innerHTML = '<i class="fas fa-trash-alt"></i>';
        closeButton.onclick = () => {
            deleteChat(chatName);
        };

        tab.appendChild(tabButton);
        tab.appendChild(closeButton);
        chatTabs.appendChild(tab);
    }
}

function deleteChat(chatName) {
    // Eliminar el chat de la lista
    delete chats[chatName];
    if (currentChat === chatName) {
        currentChat = '';
        document.getElementById('chat-title').innerText = 'Seleccione un usuario para iniciar el chat';
        document.getElementById('chat-box').innerHTML = '';
    }
    updateChatTabs(); // Actualizar las pestañas
}