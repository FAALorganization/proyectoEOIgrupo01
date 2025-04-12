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

/*JS CHAT*/

document.addEventListener("DOMContentLoaded", function () {
    const users = [
        { name: 'Usuario A', avatar: 'https://i.pravatar.cc/40?u=alice' },
        { name: 'Usuario B', avatar: 'https://i.pravatar.cc/40?u=bob' },
        { name: 'Usuario C', avatar: 'https://i.pravatar.cc/40?u=carlos' },
        { name: 'Usuario D', avatar: 'https://i.pravatar.cc/40?u=diana' }
    ];

    let currentChatUser = null;
    let chatHistory = {};
    let isGroupChat = false;

    const userList = document.getElementById('userList');
    const chatBox = document.getElementById('chatBox');
    const chatHeader = document.getElementById('chatHeader');
    const messageInput = document.getElementById('messageInput');

    // Mostrar lista de usuarios
    users.forEach(user => {
        const btn = document.createElement('div');
        btn.className = 'flex items-center gap-3 p-3 cursor-pointer hover:bg-blue-100';
        btn.innerHTML = `
        <img src="${user.avatar}" alt="avatar" class="w-8 h-8 rounded-full">
        <div>${user.name}</div>
      `;
        btn.onclick = () => openChat(user.name);
        userList.appendChild(btn);
    });

    function openChat(user) {
        isGroupChat = false;
        currentChatUser = user;
        chatHeader.textContent = `Chat con ${user}`;
        displayMessages(chatHistory[user] || []);
        messageInput.value = ''; // Limpiar el input al abrir el chat
        messageInput.focus(); // Opcional: enfocar el input
    }

    function sendMessage() {
        const msg = messageInput.value.trim();
        if (!msg || !currentChatUser) return;

        if (!chatHistory[currentChatUser]) chatHistory[currentChatUser] = [];
        chatHistory[currentChatUser].push(msg);
        messageInput.value = '';
        displayMessages(chatHistory[currentChatUser]);
    }

    function displayMessages(messages) {
        chatBox.innerHTML = '';
        messages.forEach(msg => {
            const div = document.createElement('div');
            div.className = 'mb-3 flex';
            div.innerHTML = `
        <div class="bg-blue-100 text-gray-800 shadow px-4 py-2 rounded-xl max-w-[80%]">
        ${msg}
        </div>
      `;

            chatBox.appendChild(div);
        });
        chatBox.scrollTop = chatBox.scrollHeight;
    }

    // MODAL
    function openGroupModal() {
        document.getElementById('groupModal').classList.remove('hidden');
        const list = document.getElementById('groupUserList');
        list.innerHTML = '';
        users.forEach(user => {
            const label = document.createElement('label');
            label.className = 'flex items-center gap-2';
            label.innerHTML = `
          <input type="checkbox" value="${user.name}" class="form-checkbox" />
          <span>${user.name}</span>
        `;
            list.appendChild(label);
        });
    }

    function closeGroupModal() {
        document.getElementById('groupModal').classList.add('hidden');
    }

    function createGroupChat() {
        const checkboxes = document.querySelectorAll('#groupUserList input:checked');
        const groupName = document.getElementById('groupNameInput').value.trim();
        if (checkboxes.length < 2 || !groupName) {
            alert('Selecciona al menos 2 usuarios y pon un nombre al grupo.');
            return;
        }

        const participants = Array.from(checkboxes).map(cb => cb.value);
        currentChatUser = groupName;
        isGroupChat = true;
        chatHistory[groupName] = chatHistory[groupName] || [];

        if (!document.getElementById(`group-${groupName}`)) {
            const div = document.createElement('div');
            div.id = `group-${groupName}`;
            div.className = 'cursor-pointer px-3 py-2 rounded-xl hover:bg-blue-100';
            div.textContent = `👥 ${groupName}`;
            div.onclick = () => {
                currentChatUser = groupName;
                isGroupChat = true;
                chatHeader.textContent = `Grupo: ${groupName}`;
                displayMessages(chatHistory[groupName]);
                messageInput.value = ''; // limpiar input al abrir grupo
                messageInput.focus();
            };
            userList.appendChild(div);
        }

        chatHeader.textContent = `Grupo: ${groupName}`;
        displayMessages(chatHistory[groupName]);
        closeGroupModal();
        messageInput.value = '';
        messageInput.focus();
    }

    // Exponer funciones globalmente si se usan desde HTML
    window.openGroupModal = openGroupModal;
    window.closeGroupModal = closeGroupModal;
    window.createGroupChat = createGroupChat;
    window.sendMessage = sendMessage;
});

