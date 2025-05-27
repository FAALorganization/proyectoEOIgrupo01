const daysGrid = document.getElementById('daysGrid');
const modalOverlay = document.getElementById('modalOverlay');
const modalDate = document.getElementById('modalDate');
const closeModal = document.getElementById('closeModal');
const monthSelect = document.getElementById('monthSelect');
const yearSelect = document.getElementById('yearSelect');
const months_eng = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
const months = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"];
const today = new Date();
let currentMonth = today.getMonth();
let currentYear = today.getFullYear();

if(monthSelect){
    function populateMonthYear() {

        monthSelect.innerHTML = months.map((m, i) => `<option value="${i}" ${i === currentMonth ? 'selected' : ''}>${m}</option>`).join('');
        for (let y = 2025; y <= 2035; y++) {
            yearSelect.innerHTML += `<option value="${y}" ${y === currentYear ? 'selected' : ''}>${y}</option>`;
        }
    }
}

if(daysGrid){
    function renderCalendar() {
        daysGrid.innerHTML = '';
        const firstDayOfMonth = new Date(currentYear, currentMonth, 1);
        const lastDayOfMonth = new Date(currentYear, currentMonth + 1, 0);
        const firstWeekDay = (firstDayOfMonth.getDay() + 6) % 7;
        const prevMonthDays = new Date(currentYear, currentMonth, 0).getDate();

        for (let i = firstWeekDay - 1; i >= 0; i--) {
            const d = prevMonthDays - i;
            daysGrid.innerHTML += `<div class="day inactive">${d}</div>`;
        }

        for (let d = 1; d <= lastDayOfMonth.getDate(); d++) {
            const isToday = d === today.getDate() && currentMonth === today.getMonth() && currentYear === today.getFullYear();
            daysGrid.innerHTML += `<div class="day ${isToday ? 'today' : ''}" data-date="${d}">${d}</div>`;
        }

        const nextPadding = 42 - daysGrid.children.length;
        for (let i = 1; i <= nextPadding; i++) {
            daysGrid.innerHTML += `<div class="day inactive">${i}</div>`;
        }
    }
}
if (currentMonth) {
    function changeMonth(diff) {
        currentMonth += diff;
        if (currentMonth < 0) {
            currentMonth = 11;
            currentYear--;
        } else if (currentMonth > 11) {
            currentMonth = 0;
            currentYear++;
        }
        monthSelect.value = currentMonth;
        yearSelect.value = currentYear;
        renderCalendar();
    }
}
const prevBtn = document.getElementById('prevMonth');
const nextBtn = document.getElementById('nextMonth');

if (prevBtn) {
    prevBtn.onclick = () => changeMonth(-1);
}

if (nextBtn) {
    nextBtn.onclick = () => changeMonth(1);
}

if (monthSelect && typeof currentMonth !== 'undefined') {
    monthSelect.addEventListener('change', e => {
        currentMonth = parseInt(e.target.value);
        renderCalendar();
    });
}
// if (currentMonth) {
//     monthSelect.onchange = e => {
//         currentMonth = parseInt(e.target.value);
//         renderCalendar();
//     }
// }

if (yearSelect && typeof currentYear !== 'undefined') {
    yearSelect.onchange = e => {
        currentYear = parseInt(e.target.value);
        renderCalendar();
    };
}
// if (currentYear) {
//     yearSelect.onchange = e => {
//         currentYear = parseInt(e.target.value);
//         renderCalendar();
//     }
// }

if (daysGrid) {
    daysGrid.addEventListener('click', e => {
        if (
            e.target.classList.contains('day') &&
            !e.target.classList.contains('inactive') &&
            modalOverlay &&
            modalDate
        ) {
            modalDate.textContent = `${e.target.dataset.date} ${monthSelect.options[monthSelect.selectedIndex].text} ${yearSelect.value}`;
            showModal();  // Llama a la función que muestra el modal
        }
    });
}



if (modalOverlay) {
    // Mostrar el modal
    function showModal() {
        if (modalOverlay.style.display !== 'flex') {  // Verifica si ya está visible
            modalOverlay.style.display = 'flex';  // Hace visible el modalOverlay
        }
    }

    // Cerrar el modal
    modalOverlay.onclick = function (e) {
        if (e.target === modalOverlay || e.target === closeModal) {
            modalOverlay.style.display = 'none';  // Oculta el modalOverlay
        }
    }
}

if (monthSelect) {
    populateMonthYear();
}

if (daysGrid) {
    renderCalendar();
}



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
                nav.classList.toggle('navbar-expanded')
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
/**************************************************MINI CALENDARIO****************************************/
//Mini calendario://
// Elementos del DOM
const ausCalendarGrid = document.getElementById('aus-calendarGrid');
const ausyearSelect = document.getElementById('aus-yearSelect');
const ausMonthYearLabel = document.getElementById('aus-monthYearLabel');
const ausPrevBtn = document.getElementById('aus-prevMonth');
const ausNextBtn = document.getElementById('aus-nextMonth');


// Nombres de los meses y días (lunes a domingo)

const dayNames = ["Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom"];

// Fecha actual y fecha en vista (persistente con localStorage)

let currentDate = loadStoredDate() || new Date(today.getFullYear(), today.getMonth(), 1);

// Rellena el selector de años (20 años alrededor del actual)

if (ausyearSelect && currentYear) {
    function populateYearSelector() {
        for (let y = currentYear; y <= currentYear + 2; y++) {
            const opt = document.createElement("option");
            opt.value = y;
            opt.textContent = y;
            ausyearSelect.appendChild(opt);
        }
        ausyearSelect.value = currentDate.getFullYear();
    }
}

// Generate all the calendar of the actual monthGenera
if (ausCalendarGrid) {
    function generateCalendar(date) {
// Reinicia la animación para efecto visual
        ausCalendarGrid.classList.remove("fade-in");
        void ausCalendarGrid.offsetWidth; // fuerza reflow para reiniciar animación
        ausCalendarGrid.classList.add("fade-in");

// Limpia contenido anterior
        ausCalendarGrid.innerHTML = "";

// Obtiene año y mes actuales
        const year = date.getFullYear();
        const month = date.getMonth();
        ausMonthYearLabel.textContent = `${months[month]} ${year}`;
        ausyearSelect.value = year;

// Guarda en localStorage la vista actual
        localStorage.setItem("calendarDate", JSON.stringify({ year, month }));

// Crea los encabezados de días
        dayNames.forEach(day => {
            const div = document.createElement("div");
            div.className = "aus-day-name";
            div.textContent = day;
            ausCalendarGrid.appendChild(div);
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
            day.className = "aus-day aus-inactive";
            day.textContent = daysInPrevMonth - i;
            ausCalendarGrid.appendChild(day);
        }

// Agrega días del mes actual
        for (let d = 1; d <= daysInMonth; d++) {
            const day = document.createElement("div");
            day.className = "aus-day";
            // Si es el día de hoy, resaltarlo sutilmente
            if (
                year === today.getFullYear() &&
                month === today.getMonth() &&
                d === today.getDate()
            ) {
                day.classList.add("aus-today");
            }
            day.textContent = d;
            ausCalendarGrid.appendChild(day);
        }

// Agrega días del siguiente mes como inactivos para completar el grid
        const totalCells = 7 * 6; // 6 filas completas
        const currentCells = ausCalendarGrid.querySelectorAll('.aus-day').length;
        const remaining = totalCells - currentCells;
        for (let d = 1; d <= remaining; d++) {
            const day = document.createElement("div");
            day.className = "aus-day aus-inactive";
            day.textContent = d;
            ausCalendarGrid.appendChild(day);
        }
    }
}
if (currentDate) {
// Cambia el mes actual según offset (+1, -1)
    function updateMonth(offset) {
        currentDate.setMonth(currentDate.getMonth() + offset);
        generateCalendar(currentDate);
    }
}

if (ausyearSelect) {
// Evento al cambiar año desde el selector
    ausyearSelect.addEventListener("change", () => {
        currentDate.setFullYear(parseInt(ausyearSelect.value));
        generateCalendar(currentDate);
    });
}

// Botones de navegación
if (ausPrevBtn && ausNextBtn) {
    ausPrevBtn.addEventListener("click", () => updateMonth(-1));
    ausNextBtn.addEventListener("click", () => updateMonth(1));
}

// Recupera fecha almacenada (localStorage)
function loadStoredDate() {
    const stored = JSON.parse(localStorage.getItem("calendarDate"));
    if (stored && typeof stored.year === "number" && typeof stored.month === "number") {
        return new Date(stored.year, stored.month, 1);
    }
    return null;
}

// Inicializa el calendario
if (ausyearSelect) {
    populateYearSelector();
}
if (ausCalendarGrid) {
    generateCalendar(currentDate);
}

/**************/
document.addEventListener('click', function(event) {
    const elemento = event.target;
    const clases = elemento.className;

    console.log("Clases del elemento clicado:", clases);
});

const ausModalOverlay = document.getElementById('aus-modalOverlay');
const ausCloseModal = document.getElementById('aus-closeModal');




if (ausModalOverlay) {

    ausModalOverlay.onclick = e => {
        if (e.target === ausModalOverlay || e.target === ausCloseModal) {
            ausModalOverlay.style.display = 'none';
        }
    }
}


const usuarioInfo = document.querySelector('.aus-usuario-info');
const modalBackground = document.getElementById('aus-modalOverlay');

let holdTimeout;

if (usuarioInfo) {
    usuarioInfo.addEventListener('mousedown', () => {
        holdTimeout = setTimeout(() => {
            modalBackground.style.display = 'flex';
        }, 50); // aumenta el tiempo si quieres más "intención"
    });
}


// Detecta el mouseup globalmente (mejor fiabilidad)
if (usuarioInfo) {
    document.addEventListener('mouseup', () => {
        clearTimeout(holdTimeout);
    });
}


document.addEventListener('DOMContentLoaded', function () {
    const filas = document.querySelectorAll('.aus-tabla-modal-ausencias tbody tr');

    if (filas) {
        filas.forEach(fila => {
            const checkboxes = fila.querySelectorAll('input[type="checkbox"][name="accion"]');

            checkboxes.forEach(checkbox => {
                checkbox.addEventListener('change', function () {
                    if (this.checked) {
                        checkboxes.forEach(cb => {
                            if (cb !== this) cb.checked = false;
                        });
                    }
                });
            });
        });
    }
});

/******************************************MODAL GESTION AUSENCIAS*****************************/
document.addEventListener('DOMContentLoaded', () => {
    const modalIncidenciasEl = document.getElementById('aus-modal-incidencias');
    const modalJustificacionEl = document.getElementById('aus-modal-justificacion');

    const ausModalIncidencias = modalIncidenciasEl ? new bootstrap.Modal(modalIncidenciasEl) : null;
    const ausModalJustificacion = modalJustificacionEl ? new bootstrap.Modal(modalJustificacionEl) : null;

    const btnIncidencias = document.querySelector('.aus-btn-incidencias');
    if (btnIncidencias && ausModalIncidencias) {
        btnIncidencias.addEventListener('click', () => {
            ausModalIncidencias.show();
        });
    }

    const btnJustificar = document.querySelector('.aus-btn-justificar');
    if (btnJustificar && ausModalIncidencias && ausModalJustificacion) {
        btnJustificar.addEventListener('click', () => {
            ausModalIncidencias.hide();
            ausModalJustificacion.show();
        });
    }

    const btnVolver = document.getElementById('aus-btn-volver');
    if (btnVolver && ausModalIncidencias && ausModalJustificacion) {
        btnVolver.addEventListener('click', () => {
            ausModalJustificacion.hide();
            ausModalIncidencias.show();
        });
    }

    const formJustificacion = document.getElementById('aus-form-justificacion');
    if (formJustificacion && ausModalJustificacion) {
        formJustificacion.addEventListener('submit', function (e) {
            e.preventDefault();

            const asunto = document.getElementById('aus-asunto')?.value.trim();
            const descripcion = document.getElementById('aus-descripcion')?.value.trim();
            const archivos = document.getElementById('aus-archivos')?.files;

            if (!asunto || !descripcion) {
                alert("Por favor completa asunto y descripción.");
                return;
            }

            if (!archivos || archivos.length === 0) {
                alert("Por favor adjunta al menos un archivo.");
                return;
            }

            alert("Justificación enviada correctamente (simulado).");
            ausModalJustificacion.hide();
        });
    }
});

/******************************************MODAL GESTION AUSENCIAS*****************************/
document.addEventListener('DOMContentLoaded', () => {
    const modalIncidenciasEl = document.getElementById('aus-modal-incidencias');
    const modalJustificacionEl = document.getElementById('aus-modal-justificacion');

    const ausModalIncidencias = modalIncidenciasEl ? new bootstrap.Modal(modalIncidenciasEl, { backdrop: 'static' }) : null;
    const ausModalJustificacion = modalJustificacionEl ? new bootstrap.Modal(modalJustificacionEl, { backdrop: 'static' }) : null;

    const btnIncidencias = document.querySelector('.aus-btn-incidencias');
    if (btnIncidencias && ausModalIncidencias) {
        btnIncidencias.addEventListener('click', () => {
            ausModalIncidencias.show();
        });
    }

    const btnJustificar = document.querySelector('.aus-btn-justificar');
    if (btnJustificar && ausModalIncidencias && ausModalJustificacion) {
        btnJustificar.addEventListener('click', () => {
            ausModalIncidencias.hide();
            ausModalJustificacion.show();
        });
    }

    const btnVolver = document.getElementById('aus-btn-volver');
    if (btnVolver && ausModalIncidencias && ausModalJustificacion) {
        btnVolver.addEventListener('click', () => {
            ausModalJustificacion.hide();
            ausModalIncidencias.show();
        });
    }

    const formJustificacion = document.getElementById('aus-form-justificacion');
    if (formJustificacion && ausModalJustificacion) {
        formJustificacion.addEventListener('submit', function (e) {
            e.preventDefault();

            const asunto = document.getElementById('aus-asunto')?.value.trim();
            const descripcion = document.getElementById('aus-descripcion')?.value.trim();
            const archivos = document.getElementById('aus-archivos')?.files;

            if (!asunto || !descripcion) {
                alert("Por favor completa asunto y descripción.");
                return;
            }

            if (!archivos || archivos.length === 0) {
                alert("Por favor adjunta al menos un archivo.");
                return;
            }

            alert("Justificación enviada correctamente (simulado).");
            ausModalJustificacion.hide();
        });
    }
});

// Restaurar scroll al cerrarse un modal, de forma segura
document.querySelectorAll('.modal').forEach(modal => {
    modal.addEventListener('hidden.bs.modal', () => {
        document.body.style.overflow = '';
    });
});

// Detectar click fuera del contenido (en backdrop)
document.addEventListener('click', (event) => {
    // Si se hace click en el backdrop (fuera del modal)
    if (event.target.classList.contains('modal')) {
        const instance = bootstrap.Modal.getInstance(event.target);
        document.querySelectorAll('.modal-backdrop').forEach(el => el.remove());
        instance?.hide(); // Deja que Bootstrap se encargue de ocultar
    }

    // Si se hace click en el botón de cerrar
    if (event.target.classList.contains('btn-close')) {
        const modal = event.target.closest('.modal');
        const instance = bootstrap.Modal.getInstance(modal);
        document.querySelectorAll('.modal-backdrop').forEach(el => el.remove());
        instance?.hide();
    }

});

//*******************JS NAVBAR ******************//

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
