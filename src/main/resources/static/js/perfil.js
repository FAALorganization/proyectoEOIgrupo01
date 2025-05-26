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