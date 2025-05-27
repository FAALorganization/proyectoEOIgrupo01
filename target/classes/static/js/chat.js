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

const usuarios = [
    { id: 1, nombre: "Carlos" },
    { id: 2, nombre: "Ana" },
    { id: 3, nombre: "Martín" },
    { id: 4, nombre: "Laura" },
];

let selectedUsers = [];
let currentChat = null;

window.onload = () => {
    loadUsers();
};

function loadUsers() {
    const userList = document.getElementById("userList");
    userList.innerHTML = "";
    usuarios.forEach(user => {
        const li = document.createElement("li");
        li.className = "list-group-item";
        li.textContent = user.nombre;
        li.dataset.id = user.id;
        li.onclick = () => toggleUser(user);
        userList.appendChild(li);
    });
}

function toggleUser(user) {
    const index = selectedUsers.findIndex(u => u.id === user.id);
    if (index >= 0) {
        selectedUsers.splice(index, 1);
    } else {
        selectedUsers.push(user);
    }

    document.querySelectorAll("#userList li").forEach(li => {
        const id = parseInt(li.dataset.id);
        li.classList.toggle("selected", selectedUsers.find(u => u.id === id));
    });

    if (selectedUsers.length === 1) {
        openChat(selectedUsers[0].nombre);
    } else {
        document.getElementById("chatTitle").textContent = selectedUsers.length > 1 ? "Nuevo chat grupal" : "Selecciona un chat";
        document.getElementById("chatMessages").innerHTML = "";
    }
}

function openChat(nombre) {
    currentChat = nombre;
    document.getElementById("chatTitle").textContent = `Chat con ${nombre}`;
    document.getElementById("chatMessages").innerHTML = `<p class="text-muted">Has abierto un chat con ${nombre}</p>`;
}

function createGroupChat() {
    if (selectedUsers.length < 2) {
        alert("Selecciona al menos dos usuarios para crear un chat grupal.");
        return;
    }

    const names = selectedUsers.map(u => u.nombre).join(", ");
    currentChat = `Grupo: ${names}`;
    document.getElementById("chatTitle").textContent = currentChat;
    document.getElementById("chatMessages").innerHTML = `<p class="text-muted">Chat grupal creado con: ${names}</p>`;
}

function sendMessage() {
    const input = document.getElementById("chatInput");
    const msg = input.value.trim();
    if (!msg || !currentChat) return;

    const container = document.getElementById("chatMessages");
    const msgDiv = document.createElement("div");
    msgDiv.className = "message self align-self-end";
    msgDiv.textContent = msg;
    container.appendChild(msgDiv);
    container.scrollTop = container.scrollHeight;
    input.value = "";
}

function filterUsers() {
    const filter = document.getElementById("searchUser").value.toLowerCase();
    const userList = document.getElementById("userList");
    userList.innerHTML = "";

    usuarios.filter(user => user.nombre.toLowerCase().includes(filter))
        .forEach(user => {
            const li = document.createElement("li");
            li.className = "list-group-item";
            li.textContent = user.nombre;
            li.dataset.id = user.id;
            li.onclick = () => toggleUser(user);
            userList.appendChild(li);
        });
}
