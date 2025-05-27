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

function addTask(isGroup) {
    let name = document.getElementById('taskName').value.trim();
    let desc = document.getElementById('taskDesc').value.trim();
    let errorMsg = document.getElementById('error-msg');

    if (name === '') {
        errorMsg.style.display = 'block';
        return;
    } else {
        errorMsg.style.display = 'none';
    }

    let list = isGroup ? document.getElementById('eliminatedTaskList') : document.getElementById('taskList');
    let li = document.createElement('li');
    li.classList.add('list-eliminated-item', 'd-flex', 'justify-content-between', 'align-items-center');
    li.setAttribute('data-eliminated', isGroup);

    let taskContent = document.createElement('div');
    taskContent.innerHTML = `<strong>${name}</strong><br>${desc}`;
    li.appendChild(taskContent);

    let btnGroup = document.createElement('div');
    btnGroup.classList.add('d-flex', 'gap-2');
    btnGroup.innerHTML = `
        <button class='btn btn-success btn-sm' onclick='completeTask(this)'><i class="bi bi-check-lg"></i></button>
        <button class='btn btn-danger btn-sm' onclick='removeTask(this)'><i class="bi bi-trash"></i></button>
    `;
    li.appendChild(btnGroup);

    list.appendChild(li);

    document.getElementById('taskName').value = '';
    document.getElementById('taskDesc').value = '';
}

function completeTask(button) {
    let li = button.closest('li');
    li.querySelector('div:last-child').innerHTML = `
        <button class='btn btn-warning btn-sm' onclick='restoreTask(this)'><i class="bi bi-arrow-counterclockwise"></i></button>
        <button class='btn btn-danger btn-sm' onclick='deleteTask(this)'><i class="bi bi-trash"></i></button>
    `;
    document.getElementById('completedTasks').appendChild(li);
}

function restoreTask(button) {
    let li = button.closest('li');
    li.querySelector('div:last-child').innerHTML = `
        <button class='btn btn-success btn-sm' onclick='completeTask(this)'><i class="bi bi-check-lg"></i></button>
        <button class='btn btn-danger btn-sm' onclick='removeTask(this)'><i class="bi bi-trash"></i></button>
    `;

    let isGroup = li.getAttribute('data-eliminated') === 'true';
    let targetList = isGroup ? document.getElementById('eliminatedTaskList') : document.getElementById('taskList');
    targetList.appendChild(li);
}

function removeTask(button) {
    let li = button.closest('li');
    li.querySelector('div:last-child').innerHTML = `
        <button class='btn btn-secondary btn-sm' onclick='restoreDeletedTask(this)'><i class="bi bi-arrow-counterclockwise"></i></button>
        <button class='btn btn-danger btn-sm' onclick='deleteTask(this)'><i class="bi bi-trash"></i></button>
    `;
    document.getElementById('eliminatedTaskList').appendChild(li);
}

function restoreDeletedTask(button) {
    let li = button.closest('li');
    li.querySelector('div:last-child').innerHTML = `
        <button class='btn btn-success btn-sm' onclick='completeTask(this)'><i class="bi bi-check-lg"></i></button>
        <button class='btn btn-danger btn-sm' onclick='removeTask(this)'><i class="bi bi-trash"></i></button>
    `;
    document.getElementById('taskList').appendChild(li);
}

function deleteTask(button) {
    let li = button.closest('li');
    li.remove();
}
