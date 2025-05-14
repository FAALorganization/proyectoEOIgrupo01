function mostrarRegistro() {
    document.querySelector('.login').style.transform = "translateX(-100%)";
    document.querySelector('.registro').style.transform = "translateX(0)";
}

function mostrarLogin() {
    document.querySelector('.login').style.transform = "translateX(0)";
    document.querySelector('.registro').style.transform = "translateX(100%)";
}

function validarToken(event) {
    event.preventDefault(); // Evita que el formulario recargue la página

    let email = document.getElementById("emailRegistro").value;
    let token = document.getElementById("code").value;

    if (token === "123456" && email === "ejemplo@email.com") {
        abrirModalCambioPass();
    } else {
        alert("Código incorrecto. Intenta de nuevo.");
    }
}

function abrirModalCambioPass() {
    // Usar Bootstrap para mostrar el modal
    let modal = new bootstrap.Modal(document.getElementById("modalCambioPass"));
    modal.show();
}

function cerrarModalCambioPass() {
    // Usar Bootstrap para cerrar el modal
    let modal = bootstrap.Modal.getInstance(document.getElementById("modalCambioPass"));
    modal.hide();
    mostrarLogin(); // Regresa al login tras cerrar el modal
}

function cambiarPassword() {
    let nuevaPass = document.getElementById("nuevaPass").value;

    if (nuevaPass.length >= 6) {
        alert("Contraseña cambiada con éxito. Ahora inicia sesión.");
        cerrarModalCambioPass();
    } else {
        alert("La contraseña debe tener al menos 6 caracteres.");
    }
}
