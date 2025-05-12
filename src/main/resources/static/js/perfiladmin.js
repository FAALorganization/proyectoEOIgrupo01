
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
function buscarUser() {
    // Declare variables
    var input, filter, table, tr, td, i, txtValue;
    input = document.getElementById("myInput"); // Asegúrate de que el id coincide con el del input
    filter = input.value.toUpperCase();
    table = document.getElementById("myTable");
    tr = table.getElementsByTagName("tr");
  
    // Loop through all table rows, and hide those who don't match the search query
    for (i = 0; i < tr.length; i++) {
      td = tr[i].getElementsByTagName("td")[1]; // Aquí estamos buscando el texto en la columna de "Nombre"
      if (td) {
        txtValue = td.textContent || td.innerText;
        if (txtValue.toUpperCase().indexOf(filter) > -1) {
          tr[i].style.display = "";
        } else {
          tr[i].style.display = "none";
        }
      }
    }
  }

function closeFirstModal() {
    const firstModal = document.getElementById('exampleModal');
    const bootstrapModal = bootstrap.Modal.getInstance(firstModal);
    if (bootstrapModal) {
        bootstrapModal.hide(); // Cierra el primer modal
    } else {
        console.warn('El primer modal no tiene una instancia activa.');
    }
}

function reopenFirstModal() {
    const secondModal = document.getElementById('confirmDeleteModal');
    const bootstrapSecondModal = bootstrap.Modal.getInstance(secondModal);
    if (bootstrapSecondModal) {
        bootstrapSecondModal.hide(); // Cierra el segundo modal
    } else {
        console.warn('El segundo modal no tiene una instancia activa.');
    }

    const firstModal = new bootstrap.Modal(document.getElementById('exampleModal'));
    firstModal.show(); // Reabre el primer modal
}