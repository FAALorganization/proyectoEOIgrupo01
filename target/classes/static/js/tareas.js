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
