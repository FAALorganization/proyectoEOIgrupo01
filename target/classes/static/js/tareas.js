document.addEventListener("DOMContentLoaded", () => {
    const post = (url) => {
        fetch(url, {
            method: "POST",
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).then(() => {
            location.reload(); // recarga la página al completar la acción
        });
    };

    document.querySelectorAll(".btn-completar").forEach(btn => {
        btn.addEventListener("click", () => {
            const id = btn.getAttribute("data-id");
            post(`/tareas/completar/${id}`);
        });
    });

    document.querySelectorAll(".btn-eliminar").forEach(btn => {
        btn.addEventListener("click", () => {
            const id = btn.getAttribute("data-id");
            post(`/tareas/eliminar/${id}`);
        });
    });

    document.querySelectorAll(".btn-restaurar").forEach(btn => {
        btn.addEventListener("click", () => {
            const id = btn.getAttribute("data-id");
            post(`/tareas/restaurar/${id}`);
        });
    });
});
