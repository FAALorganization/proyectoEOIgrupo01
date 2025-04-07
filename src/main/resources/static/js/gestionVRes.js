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
    
    if (counter%2 === 0){
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

//Mini calendario://
// Elementos del DOM
const calendarGrid = document.getElementById('calendarGrid');
const yearSelect = document.getElementById('yearSelect');
const monthYearLabel = document.getElementById('monthYearLabel');
const prevBtn = document.getElementById('prevMonth');
const nextBtn = document.getElementById('nextMonth');

// Nombres de los meses y días (lunes a domingo)
const monthNames = [
  "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
  "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
];
const dayNames = ["Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom"];

// Fecha actual y fecha en vista (persistente con localStorage)
let today = new Date();
let currentDate = loadStoredDate() || new Date(today.getFullYear(), today.getMonth(), 1);

// Rellena el selector de años (20 años alrededor del actual)
function populateYearSelector() {
  const currentYear = today.getFullYear();
  for (let y = currentYear; y <= currentYear + 2; y++) {
    const opt = document.createElement("option");
    opt.value = y;
    opt.textContent = y;
    yearSelect.appendChild(opt);
  }
  yearSelect.value = currentDate.getFullYear();
}

// Generate all the calendar of the actual monthGenera
function generateCalendar(date) {
  // Reinicia la animación para efecto visual
  calendarGrid.classList.remove("fade-in");
  void calendarGrid.offsetWidth; // fuerza reflow para reiniciar animación
  calendarGrid.classList.add("fade-in");

  // Limpia contenido anterior
  calendarGrid.innerHTML = "";

  // Obtiene año y mes actuales
  const year = date.getFullYear();
  const month = date.getMonth();
  monthYearLabel.textContent = `${monthNames[month]} ${year}`;
  yearSelect.value = year;

  // Guarda en localStorage la vista actual
  localStorage.setItem("calendarDate", JSON.stringify({ year, month }));

  // Crea los encabezados de días
  dayNames.forEach(day => {
    const div = document.createElement("div");
    div.className = "day-name";
    div.textContent = day;
    calendarGrid.appendChild(div);
  });

  // Calcula el primer día del mes y su posición
  const firstDay = new Date(year, month, 1);
  const startDay = (firstDay.getDay() + 6) % 7; // Ajuste para empezar lunes

  // Número de días del mes actual y anterior
  const daysInMonth = new Date(year, month + 1, 0).getDate();
  const daysInPrevMonth = new Date(year, month, 0).getDate();

  // Agrega días del mes anterior como inactivos
  for (let i = startDay - 1; i >= 0; i--) {
    const day = document.createElement("div");
    day.className = "day inactive";
    day.textContent = daysInPrevMonth - i;
    calendarGrid.appendChild(day);
  }

  // Agrega días del mes actual
  for (let d = 1; d <= daysInMonth; d++) {
    const day = document.createElement("div");
    day.className = "day";
    // Si es el día de hoy, resaltarlo sutilmente
    if (
      year === today.getFullYear() &&
      month === today.getMonth() &&
      d === today.getDate()
    ) {
      day.classList.add("today");
    }
    day.textContent = d;
    calendarGrid.appendChild(day);
  }

  // Agrega días del siguiente mes como inactivos para completar el grid
  const totalCells = 7 * 6; // 6 filas completas
  const currentCells = calendarGrid.querySelectorAll('.day').length;
  const remaining = totalCells - currentCells;
  for (let d = 1; d <= remaining; d++) {
    const day = document.createElement("div");
    day.className = "day inactive";
    day.textContent = d;
    calendarGrid.appendChild(day);
  }
}

// Cambia el mes actual según offset (+1, -1)
function updateMonth(offset) {
  currentDate.setMonth(currentDate.getMonth() + offset);
  generateCalendar(currentDate);
}

// Evento al cambiar año desde el selector
yearSelect.addEventListener("change", () => {
  currentDate.setFullYear(parseInt(yearSelect.value));
  generateCalendar(currentDate);
});

// Botones de navegación
prevBtn.addEventListener("click", () => updateMonth(-1));
nextBtn.addEventListener("click", () => updateMonth(1));

// Recupera fecha almacenada (localStorage)
function loadStoredDate() {
  const stored = JSON.parse(localStorage.getItem("calendarDate"));
  if (stored && typeof stored.year === "number" && typeof stored.month === "number") {
    return new Date(stored.year, stored.month, 1);
  }
  return null;
}

// Inicializa el calendario
populateYearSelector();
generateCalendar(currentDate);

