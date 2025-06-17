function mostrarRegistro() {
    document.querySelector('.login').style.transform = "translateX(-100%)";
    document.querySelector('.registro').style.transform = "translateX(0)";
}

function mostrarLogin() {
    document.querySelector('.login').style.transform = "translateX(0)";
    document.querySelector('.registro').style.transform = "translateX(100%)";
}

// Validar token + email con backend (POST a /login/validar-token)
function validarToken(event) {
    event.preventDefault();

    const email = document.getElementById("emailRegistro").value.trim();
    const token = document.getElementById("code").value.trim();

    if (!email || !token) {
        alert("Por favor, completa ambos campos.");
        return;
    }

    fetch('/login/validar-token', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, token })
    })
        .then(response => {
            if (!response.ok) throw new Error('Token inválido');
            return response.text(); // puede cambiar a .json() si backend devuelve json
        })
        .then(data => {
            console.log("Token validado ✔", data);
            abrirModalCambioPass(email); // Pasamos el correo aquí
        })
        .catch(error => {
            alert("Correo o token incorrecto. Intenta de nuevo.");
            console.error("Error en la validación:", error);
        });
}

function abrirModalCambioPass(email) {
    // Insertamos el email en un input oculto dentro del modal
    const inputHidden = document.getElementById("emailCambioPass");
    inputHidden.value = email;

    const modal = new bootstrap.Modal(document.getElementById("modalCambioPass"));
    modal.show();
}

function cerrarModalCambioPass() {
    const modal = bootstrap.Modal.getInstance(document.getElementById("modalCambioPass"));
    if (modal) modal.hide();
    mostrarLogin();
}

async function cambiarPassword(event) {
    event.preventDefault();

    const nuevaPass = document.getElementById("nuevaPass").value.trim();
    const confirmarPass = document.getElementById("confirmarPass").value.trim();
    const email = document.getElementById("emailCambioPass").value.trim(); // Aquí tomamos el email real

    if (nuevaPass.length < 6) {
        alert("La contraseña debe tener al menos 6 caracteres.");
        return;
    }
    if (nuevaPass !== confirmarPass) {
        alert("Las contraseñas no coinciden.");
        return;
    }

    try {
        const response = await fetch('/login/cambiar-password', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ email, nuevaPass })
        });

        if (response.ok) {
            alert("Contraseña cambiada con éxito. Ahora inicia sesión.");
            cerrarModalCambioPass();
        } else {
            const errorText = await response.text();
            alert("Error: " + errorText);
        }
    } catch (error) {
        alert("Error de conexión con el servidor.");
        console.error(error);
    }
}

document.getElementById("formCambioPass").addEventListener("submit", cambiarPassword);