document.getElementById('taskForm').addEventListener('submit', function(e) {
    e.preventDefault();

    const user = document.getElementById('userSelect').value;
    const name = document.getElementById('taskName').value;
    const desc = document.getElementById('taskDesc').value;

    console.log("Tarea añadida para:", user);
    console.log("Nombre:", name);
    console.log("Descripción:", desc);

    alert(`✅ Tarea añadida para ${user}`);
    this.reset(); // Limpiar el formulario
});