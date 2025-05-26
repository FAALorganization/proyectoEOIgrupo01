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

function uploadFile(sectionId) {
    const fileInput = document.getElementById(`file${capitalize(sectionId)}`);
    const fileList = document.getElementById(`list${capitalize(sectionId)}`);

    if (fileInput.files.length > 0) {
        const file = fileInput.files[0]; // El archivo seleccionado
        const fileName = file.name;

        // Crear elemento en la lista
        const listItem = document.createElement("li");
        listItem.className = "list-group-item d-flex justify-content-between align-items-center";
        listItem.innerHTML = `
            ${fileName}
            <div>
                <button class="btn btn-success btn-sm me-2" onclick="downloadFile('${fileName}')">Descargar</button>
                <button class="btn btn-danger btn-sm" onclick="deleteFile(this)">Eliminar</button>
            </div>
        `;

        fileList.appendChild(listItem);

        // Limpiar el campo de archivo
        fileInput.value = "";
    } else {
        alert("Por favor selecciona un archivo antes de subirlo.");
    }
}

function deleteFile(element) {
    // Eliminar el elemento de la lista
    element.closest("li").remove();
}

function downloadFile(fileName) {
    alert(`Simulación de descarga de: ${fileName}`);
}

function capitalize(str) {
    return str.charAt(0).toUpperCase() + str.slice(1);
}