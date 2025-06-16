 // Función para filtrar la tabla de usuarios
    function buscarUser() {
        var input = document.getElementById("myInput");
        var filter = input.value.toUpperCase();
        var table = document.getElementById("myTable");
        var tr = table.getElementsByTagName("tr");

        for (let i = 0; i < tr.length; i++) {
            let td = tr[i].getElementsByTagName("td")[1]; // Columna de nombre
            if (td) {
                let txtValue = td.textContent || td.innerText;
                tr[i].style.display = txtValue.toUpperCase().indexOf(filter) > -1 ? "" : "none";
            }
        }
    }

    // Obtener el botón de "Equipos de trabajo"
    const teamButton = document.querySelector('[data-bs-toggle="modal"][data-bs-target="#changeTeamModal"]');
    if (teamButton) {
        teamButton.addEventListener('click', function () {
            const firstModal = document.getElementById('exampleModal');
            const bootstrapModal = bootstrap.Modal.getInstance(firstModal);

            if (bootstrapModal) {
                bootstrapModal.hide(); // Cierra el primer modal
            } else {
                console.warn('El primer modal no tiene una instancia activa.');
            }

        });
function reopenFirstModal() {
    const secondModal = document.getElementById('confirmDeleteModal');
    const bootstrapSecondModal = bootstrap.Modal.getInstance(changeTeamModal);
    if (bootstrapSecondModal) {
        bootstrapSecondModal.hide(); // Cierra el segundo modal
    } else {
        console.warn('El segundo modal no tiene una instancia activa.');
    }

    const firstModal = new bootstrap.Modal(document.getElementById('changeTeamModal'));
    firstModal.show(); // Reabre el primer modal
}}