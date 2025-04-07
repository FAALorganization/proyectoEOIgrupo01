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

    let counter;
    if (counter % 2 === 0) {
        document.querySelectorAll(".nav_name").forEach(link => {
            link.style.color = "white";
            link.classList.add("textenter");
        })
        counter += 1;
    } else {
        document.querySelectorAll(".nav_name").forEach(link => {
            link.style.color = "#252323";
            link.classList.remove("textenter");
        })
        counter += 1;
    }    
});

const buttonsUser = document.querySelectorAll('.fa-magnifying-glass');

buttonsUser.forEach(boton => {
    boton.addEventListener("click", function(){
        document.getElementById("modal-container").classList.add("show");
        document.getElementById("modal-content").classList.add("show");
    });
});

document.getElementById("close-modal").addEventListener("click", function(){

    document.getElementById("modal-container").classList.remove("show");
    document.getElementById("modal-content").classList.remove("show");
});

