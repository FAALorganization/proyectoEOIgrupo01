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

if (monthSelect) {
    function populateMonthYear() {

        monthSelect.innerHTML = months.map((m, i) => `<option value="${i}" ${i === currentMonth ? 'selected' : ''}>${m}</option>`).join('');
        for (let y = 2025; y <= 2035; y++) {
            yearSelect.innerHTML += `<option value="${y}" ${y === currentYear ? 'selected' : ''}>${y}</option>`;
        }
    }
}

if (daysGrid) {
    function renderCalendar() {
        daysGrid.innerHTML = '';
        const firstDayOfMonth = new Date(currentYear, currentMonth, 1);
        const lastDayOfMonth = new Date(currentYear, currentMonth + 1, 0);
        const firstWeekDay = (firstDayOfMonth.getDay() + 6) % 7;
        const prevMonthDays = new Date(currentYear, currentMonth, 0).getDate();

        for (let i = firstWeekDay - 1; i >= 0; i--) {
            const d = prevMonthDays - i;
            daysGrid.innerHTML += `<div class="day inactive dayInact-${d}">${d}</div>`;
        }

        for (let d = 1; d <= lastDayOfMonth.getDate(); d++) {
            const isToday = d === today.getDate() && currentMonth === today.getMonth() && currentYear === today.getFullYear();
            daysGrid.innerHTML += `<div class="day ${isToday ? 'today' : ''}" data-date="${d}">${d}</div>`;
        }

        const nextPadding = 42 - daysGrid.children.length;
        for (let i = 1; i <= nextPadding; i++) {
            daysGrid.innerHTML += `<div class="day inactive dayInact-${i}">${i}</div>`;
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

if (yearSelect && typeof currentYear !== 'undefined') {
    yearSelect.onchange = e => {
        currentYear = parseInt(e.target.value);
        renderCalendar();
    };
}

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


document.addEventListener("DOMContentLoaded", function (event) {

    const showNavbar = (toggleId, navId, bodyId, headerId) => {
        const toggle = document.getElementById(toggleId),
            nav = document.getElementById(navId),
            bodypd = document.getElementById(bodyId),
            headerpd = document.getElementById(headerId)

        // Validate that all variables exist
        if (toggle && nav && bodypd && headerpd) {
            toggle.addEventListener('click', () => {
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

    showNavbar('header-toggle', 'nav-bar', 'body-pd', 'header')

    /*===== LINK ACTIVE =====*/
    const linkColor = document.querySelectorAll('.nav_link')

    function colorLink() {
        if (linkColor) {
            linkColor.forEach(l => l.classList.remove('active'))
            this.classList.add('active')
        }
    }

    linkColor.forEach(l => l.addEventListener('click', colorLink))

    // Your code to run since DOM is loaded and ready
});

counter = 0;
const buttonNav = document.getElementById("header-toggle");
buttonNav.addEventListener("click", function () {

    if (counter % 2 == 0) {
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
        localStorage.setItem("calendarDate", JSON.stringify({year, month}));

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


/*Funcio agrupar fechas*/
function agruparFechasConsecutivas(selectedDays) {
    const selectedArray = Array.from(selectedDays);
    if (selectedArray.length === 0) return [];

    const fechasOrdenadas = selectedArray.sort((a, b) => {
        const [diaA, mesA, yearA] = a.split("-").map(Number);
        const [diaB, mesB, yearB] = b.split("-").map(Number);
        return new Date(yearA, mesA - 1, diaA) - new Date(yearB, mesB - 1, diaB);
    });

    let diasTotales = [];
    let grupoActual = [fechasOrdenadas[0]];

    for (let i = 1; i < fechasOrdenadas.length; i++) {
        const [diaPrev, mesPrev, yearPrev] = fechasOrdenadas[i - 1].split("-").map(Number);
        const [diaAct, mesAct, yearAct] = fechasOrdenadas[i].split("-").map(Number);

        const fechaPrev = new Date(yearPrev, mesPrev - 1, diaPrev);
        const fechaAct = new Date(yearAct, mesAct - 1, diaAct);

        const diffDays = (fechaAct - fechaPrev) / (1000 * 60 * 60 * 24);

        if (diffDays === 1) {
            grupoActual.push(fechasOrdenadas[i]);
        } else {
            diasTotales.push(grupoActual);
            grupoActual = [fechasOrdenadas[i]];
        }
    }

    // Agregar el último grupo
    if (grupoActual.length) {
        diasTotales.push(grupoActual);
    }

    return diasTotales;
}

/**************/
document.addEventListener('click', function (event) {
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

function abrirModal(){
    const modalIncidenciasEl = document.getElementById('aus-modal-incidencias');
    const modalJustificacionEl = document.getElementById('aus-modal-justificacion');
    const ausModalIncidencias = modalIncidenciasEl ? new bootstrap.Modal(modalIncidenciasEl) : null;
    const ausModalJustificacion = modalJustificacionEl ? new bootstrap.Modal(modalJustificacionEl) : null;
    const botones = document.querySelectorAll('.aus-btn-justificar');
    const btnIncidencias = document.querySelector('.aus-btn-incidencias');

    if (btnIncidencias && ausModalIncidencias) {
        btnIncidencias.addEventListener('click', () => {
            ausModalIncidencias.show();
            btnIncidencias.focus();
        });
    }

    if (botones && ausModalIncidencias && ausModalJustificacion) {
        botones.forEach(btn => {
            btn.addEventListener('click', () => {
                ausModalIncidencias.hide();
                btnIncidencias.focus();
                ausModalJustificacion.show();
            });
        });
    }


    const btnVolver = document.getElementById('aus-btn-volver');
    if (btnVolver && ausModalIncidencias && ausModalJustificacion) {
        btnVolver.addEventListener('click', () => {
            ausModalJustificacion.hide();
            btnIncidencias.focus();
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

            // if (!archivos || archivos.length === 0) {
            //     alert("Por favor adjunta al menos un archivo.");
            //     return;
            // }

            // alert("Justificación enviada correctamente (simulado).");
            ausModalJustificacion.hide();
        });
    }
}

document.addEventListener('DOMContentLoaded', () => {
    abrirModal();
});

/******************************************MODAL GESTION AUSENCIAS*****************************/

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

/********************************************Gestion ausencias****************************/
function parseDate(str) {
    return new Date(str + 'T00:00:00'); // asegura zona horaria
}

function crearFechasDir(companero) {
    //Función para parsear string fecha '2025-05-04' a Date

    const fechas = {};
    const currentYear = new Date().getFullYear();

    companero.ausencias.forEach(ausencia => {
        const fechaInicio = parseDate(ausencia.fechaInicio);
        const fechaFin = parseDate(ausencia.fechaFin);
        if (fechaInicio.getFullYear() === currentYear || fechaFin.getFullYear() === currentYear + 1) {
            fechas[fechaInicio.getFullYear()] = {};
            fechas[fechaFin.getFullYear()] = {};
        }
    });

    companero.ausencias.forEach(ausencia => {
        const fechaInicio = parseDate(ausencia.fechaInicio);
        const fechaFin = parseDate(ausencia.fechaFin);
        const mesInicio = fechaInicio.getMonth();
        const mesFin = fechaFin.getMonth();

        if (fechas[fechaInicio.getFullYear()]) {
            if (!fechas[fechaInicio.getFullYear()][mesInicio]) {
                fechas[fechaInicio.getFullYear()][mesInicio] = {};
            }
        }

        if (fechas[fechaFin.getFullYear()]) {
            if (!fechas[fechaFin.getFullYear()][mesFin]) {
                fechas[fechaFin.getFullYear()][mesFin] = {};
            }
        }
    });

    companero.ausencias.forEach(ausencia => {

        const fechaInicio = parseDate(ausencia.fechaInicio);
        const fechaFin = parseDate(ausencia.fechaFin);

        const mesInicio = fechaInicio.getMonth();
        const mesFin = fechaFin.getMonth();

        const diaInicio = fechaInicio.getDate();
        const diaFin = fechaFin.getDate();

        const tipo = ausencia.tipoAusencias.id;
        const aprobado = ausencia.aprobado;

        if (mesInicio === mesFin) {
            for (let i = diaInicio; i <= diaFin; i++) {
                fechas[fechaInicio.getFullYear()][mesInicio][i] = [tipo,aprobado];
            }
        } else if (mesInicio !== mesFin) {
            const lastDayMonth = new Date(fechaInicio.getFullYear(), fechaInicio.getMonth() + 1, 0).getDate();
            for (let i = diaInicio; i <= lastDayMonth; i++) {
                fechas[fechaInicio.getFullYear()][mesInicio][i] = [tipo,aprobado];
            }

            for (let i = 1; i <= diaFin; i++) {
                fechas[fechaFin.getFullYear()][mesFin][i] = [tipo,aprobado];
            }
        }
    });
    return fechas;
}

function crearFechasDirAusencias(ausencias) {
    const fechas = {};

    ausencias.forEach(ausencia => {
        const fechaInicio = parseDate(ausencia.fechaInicio);
        const fechaFin = parseDate(ausencia.fechaFin);
        if (fechaInicio.getFullYear() === new Date().getFullYear() || fechaFin.getFullYear() === new Date().getFullYear() + 1) {
            fechas[fechaInicio.getFullYear()] = {};
            fechas[fechaFin.getFullYear()] = {};
        }
    });

    ausencias.forEach(ausencia => {
        const fechaInicio = parseDate(ausencia.fechaInicio);
        const fechaFin = parseDate(ausencia.fechaFin);
        const mesInicio = fechaInicio.getMonth();
        const mesFin = fechaFin.getMonth();

        if (fechas[fechaInicio.getFullYear()]) {
            if (!fechas[fechaInicio.getFullYear()][mesInicio]) {
                fechas[fechaInicio.getFullYear()][mesInicio] = {};
            }
        }

        if (fechas[fechaFin.getFullYear()]) {
            if (!fechas[fechaFin.getFullYear()][mesFin]) {
                fechas[fechaFin.getFullYear()][mesFin] = {};
            }
        }
    });

    ausencias.forEach(ausencia => {
        const fechaInicio = parseDate(ausencia.fechaInicio);
        const fechaFin = parseDate(ausencia.fechaFin);
        const tipo = ausencia.tipoAusenciaId; // id del tipo ausencia (número)
        const aprobado = ausencia.aprobado; //boolean
        const esJustificada = ausencia.justificada// boolean

        const mesInicio = fechaInicio.getMonth();
        const mesFin = fechaFin.getMonth();

        const diaInicio = fechaInicio.getDate();
        const diaFin = fechaFin.getDate();

        if (mesInicio === mesFin) {
            for (let i = diaInicio; i <= diaFin; i++) {
                fechas[fechaInicio.getFullYear()][mesInicio][i] = [tipo, aprobado, esJustificada];
            }
        } else if (mesInicio !== mesFin) {
            const lastDayMonth = new Date(fechaInicio.getFullYear(), fechaInicio.getMonth() + 1, 0).getDate();
            for (let i = diaInicio; i <= lastDayMonth; i++) {
                fechas[fechaInicio.getFullYear()][mesInicio][i] = [tipo, aprobado, esJustificada];
            }

            for (let i = 1; i <= diaFin; i++) {
                fechas[fechaFin.getFullYear()][mesFin][i] = [tipo, aprobado, esJustificada];
            }
        }
    });
    return fechas;
}

function obtenerClaseDia(tipo, aprobado) {
    if (tipo === 1 && aprobado === true) {
        return "dayVacCon";
    } else if (tipo === 1 && aprobado === false) {
        return "dayVacPen";
    } else if (tipo === 2 && aprobado === false) {
        return "dayAus";
    } else if (tipo === 2 && aprobado === true) {
        return "dayNorm"
    }
    return ""; // Si no coincide con ningún caso
}


//Pintar findes funcion:
function colorWeekendGray(){

    function addGrayBgWeekend (element, month,year) {
        if ([0,6].includes((new Date(year, month, parseInt(element.innerHTML))).getDay())) {
            let clases = ["dayVacPen", "dayAus"]

            clases.forEach((clase) => {
                if (element.classList.contains(clase)) {
                    element.classList.remove(clase);
                }
            })
            element.classList.add("weekEnd");
        }

    }

    let allDaysDivs = document.querySelectorAll('.day');
    let selectedMonth = parseInt(document.querySelector('#monthSelect').value);
    let selectedYear = parseInt(document.querySelector('#yearSelect').value);

    allDaysDivs.forEach((day, index) => {
            let innerDataInt = parseInt(day.innerHTML);
            let innerDataString = innerDataInt.toString();

            if (day.classList.contains("dayInact-" + innerDataString) && innerDataInt - 1 > index) {
                addGrayBgWeekend(day, selectedMonth - 1, selectedYear);
            } else if (day.classList.contains("dayInact-" + innerDataString) && innerDataInt < index) {
                addGrayBgWeekend(day, selectedMonth + 1, selectedYear);
            }else {
                addGrayBgWeekend(day, selectedMonth, selectedYear);
            }
        }

    )
}

function pintarAusencias(ausencias) {
    //console.log("Estoy pintando!!")
    // Obtener valor del select: "2025-05"
    const selectedMonth = parseInt(document.getElementById('monthSelect').value);
    const selectedYear = parseInt(document.getElementById('yearSelect').value);
    // Quitar clases anteriores de todos los días activos
    document.querySelectorAll('.day').forEach(dia => {
        dia.classList.remove('dayVacCon', 'dayVacPen', 'dayAus');
    });

// Quitar clases de días inactivos (de meses anterior/siguiente)
    document.querySelectorAll('[class^="dayInact-"]').forEach(dia => {
        dia.classList.remove('dayVacCon', 'dayVacPen', 'dayAus');
    });

    //Función para parsear string fecha '2025-05-04' a Date
    function parseDate(str) {
        return new Date(str + 'T00:00:00'); // asegura zona horaria
    }

    let fechas = crearFechasDirAusencias(ausencias);
    //console.log(fechas);
    Object.keys(fechas).forEach(year => {
        Object.keys(fechas[year]).forEach(month => {
            Object.keys(fechas[year][month]).forEach(dayNum => {
                if (parseInt(month) === selectedMonth) {
                    const tipo = fechas[year][month][dayNum][0];
                    const aprobado = fechas[year][month][dayNum][1];
                    const claseName = document.querySelector(`.day[data-date="${parseInt(dayNum)}"]`);
                    if (claseName) {
                        claseName.classList.add(obtenerClaseDia(tipo, aprobado));
                    }
                } else if (parseInt(month) === selectedMonth - 1 && parseInt(dayNum) > 24 || parseInt(month) === selectedMonth + 1 && parseInt(dayNum) < 8) {
                    const tipo = fechas[year][month][dayNum][0];
                    const aprobado = fechas[year][month][dayNum][1];
                    const claseName = document.getElementsByClassName("dayInact-" + dayNum)[0];
                    if (claseName) {
                        claseName.classList.add(obtenerClaseDia(tipo, aprobado));
                    }
                }
            })
        })
    });

    colorWeekendGray();
}

function getColor(index) {
    const colors = [
        "#FF5733", // rojo anaranjado
        "#33B5FF", // azul cielo
        "#28A745", // verde intenso
        "#FFC300", // amarillo vibrante
        "#6F42C1", // púrpura
        "#E83E8C", // rosa fuerte
        "#20C997", // turquesa
        "#FD7E14", // naranja quemado
        "#6610F2", // violeta fuerte
        "#17A2B8", // cian
        "#DC3545", // rojo fuerte
        "#007BFF", // azul estándar
        "#F39C12", // dorado cálido
        "#2ECC71", // verde claro
        "#D35400", // naranja oscuro
        "#C0392B", // rojo vino
        "#8E44AD", // morado oscuro
        "#1ABC9C", // aguamarina
        "#34495E", // gris azulado oscuro
        "#F1C40F"  // amarillo cálido
    ];
    return colors[index % colors.length];
}


if (window.location.pathname === '/gestion') {
document.querySelector(".btn-ind-vac").addEventListener("click", (event) => {
        let ausencias = [];

        fetch('/gestion/ausencias')
            .then(response => response.json())
            .then(data => {
                ausencias = data;
                pintarAusencias(ausencias);
            });
        const selectores = ['monthSelect', 'yearSelect'];
        const flechas = ['nextMonth', 'prevMonth'];

        selectores.forEach(id => {
            document.getElementById(id).addEventListener('change', () => {
                pintarAusencias(ausencias);

            });
        });

        flechas.forEach(id => {
            document.getElementById(id).addEventListener('click', () => {
                pintarAusencias(ausencias);
            });
        });
});
}
/*******************************************REST NOMBRES***************************************/
// Opcional: función para variar colores
if (window.location.pathname === '/gestion') {
    document.addEventListener("DOMContentLoaded", function () {
        fetch("/gestion/companeros")
            .then(response => {
                if (!response.ok) {
                    throw new Error("Error al obtener los compañeros");
                }
                return response.json();
            })
            .then(data => {
                const container = document.getElementById("companeros-container");
                container.innerHTML = ""; // Limpiar antes de insertar

                data.forEach((companero, index) => {
                    const divLeyenda = document.createElement("div");
                    divLeyenda.classList.add("perfil-usuario-leyenda");

                    // Crear icono
                    const icon = document.createElement("i");
                    icon.className = "fa-regular fa-user";
                    icon.style.color = getColor(index); // función para cambiar color

                    // Crear nombre completo
                    const span = document.createElement("span");
                    span.textContent = `${companero.nombre} ${companero.apellidos}`;
                    span.style.color = getColor(index); // mismo color o diferente

                    divLeyenda.appendChild(icon);
                    divLeyenda.appendChild(span);

                    container.appendChild(divLeyenda);
                });
            })
            .catch(error => {
                console.error("Error:", error);
            });
    });
}

    /***********************************************GENERAL CO-WORKERS HOLIDAYS********************************/
    function crearSimbolosAusencias() {
        fetch("/gestion/companeros")
            .then(response => {
                if (!response.ok) throw new Error("Error al obtener los compañeros");
                return response.json();
            })
            .then(companeros => {
                let dirColorsNames = {};
                companeros.forEach((companero, index) => {
                    let nombreCompleto = companero.nombre + " " + companero.apellidos;
                    dirColorsNames[nombreCompleto] = getColor(index);
                });

                // Solo un fetch a ausencias
                return fetch("/companeros-con-ausencias")
                    .then(response => {
                        if (!response.ok) throw new Error("Error al obtener ausencias");
                        return response.json();
                    })
                    .then(companerosAusencias => {
                        companerosAusencias.forEach((companero2, index2) => {

                            let registros = crearFechasDir(companero2);
                            let nombre = companero2.nombre;
                            let apellidos = companero2.apellidos;
                            console.log(registros);
                            const selectedMonth = parseInt(document.getElementById('monthSelect').value);

                            Object.keys(registros).forEach(year => {
                                Object.keys(registros[year]).forEach(month => {
                                    Object.keys(registros[year][month]).forEach(dayNum => {
                                        const tipo = registros[year][month][dayNum][0]
                                        const aprobado = registros[year][month][dayNum][1]
                                        if (tipo === 1 && aprobado === true) {
                                            const divIcon = document.createElement("div");
                                            divIcon.className = "div-container-icons";

                                            const isSelectedMonth = parseInt(month) === selectedMonth;
                                            const isAdjacentMonth = (parseInt(month) === selectedMonth - 1 && parseInt(dayNum) > 24)
                                                || (parseInt(month) === selectedMonth + 1 && parseInt(dayNum) < 8);

                                            const isWeekday = ![0, 6].includes(new Date(year, month, dayNum).getDay());

                                            if ((isSelectedMonth || isAdjacentMonth) && isWeekday) {
                                                let claseName;
                                                if (isSelectedMonth) {
                                                    claseName = document.querySelector(`.day[data-date="${parseInt(dayNum)}"]`);
                                                } else {
                                                    claseName = document.getElementsByClassName("dayInact-" + dayNum)[0];
                                                }

                                                if (claseName) {
                                                    const icon = document.createElement("i");
                                                    icon.className = "fa-regular fa-user icon-container-estilo";
                                                    icon.style.color = dirColorsNames[nombre + " " +  apellidos]; // o usar dirColorsNames si coincide
                                                    divIcon.appendChild(icon);
                                                    claseName.appendChild(divIcon);
                                                }
                                            }
                                        }

                                    });
                                });
                            });
                        });

                        // Añadir relleno para los días que tienen icono
                        const days = document.querySelectorAll('.day');
                        days.forEach(day => {
                            const hasIcon = day.querySelector('i') !== null;
                            if (hasIcon) {
                                const emptyDiv1 = document.createElement("div");
                                const emptyDiv2 = document.createElement("div");
                                day.insertBefore(emptyDiv2, day.children[0]);
                                day.insertBefore(emptyDiv1, day.children[0]);
                            }
                        });
                    });
            })
            .catch(error => {
                console.error("Error:", error);
            });
    }


    if (window.location.pathname === '/gestion') {
        document.addEventListener("DOMContentLoaded", function () {
            crearSimbolosAusencias();

        });

        // Ocultar íconos
        document.querySelector('.btn-ind-vac').addEventListener('click', () => {
            document.querySelectorAll('i.icon-container-estilo').forEach(icon => {
                icon.classList.add('icon-hidden');
            });

            document.querySelectorAll('.day').forEach(day => {
                day.classList.remove("normal-bg-color")
            });
        });

// Mostrar íconos
        document.querySelector('.btn-general').addEventListener('click', () => {

            if (document.querySelectorAll('i.icon-container-estilo').length === 0) {
                crearSimbolosAusencias();
            }

            document.querySelectorAll('i.icon-container-estilo').forEach(icon => {
                icon.classList.remove('icon-hidden');
            });

            document.querySelectorAll('.day').forEach(day => {
                day.classList.add("normal-bg-color")
            });
        });
    }

    /********************************************CONTADOR AUSENCIAS******************************************************/
    function createSimpleDate(dayNum, month, year) {
        const mes = (parseInt(month) + 1).toString();
        return `${dayNum.length === 1 ? '0' + dayNum : dayNum}-${mes.length === 1 ? '0' + mes : mes}-${year}`;
    }

    function ordenarFechas(objFechas) {
        return Object.keys(objFechas).sort((a, b) => {
            const sepA = a.includes('/') ? '/' : '-';
            const sepB = b.includes('/') ? '/' : '-';

            const [diaA, mesA, yearA] = a.split(sepA);
            const [diaB, mesB, yearB] = b.split(sepB);

            const fechaA = new Date(`${yearA}-${mesA}-${diaA}`);
            const fechaB = new Date(`${yearB}-${mesB}-${diaB}`);

            return fechaA - fechaB; // ascendente
        })
            .reduce((ordenado, key) => {
                ordenado[key] = objFechas[key];
                return ordenado;
            }, {});
    }

    function createAusenciaLaboral(string, historial) {
        let listaFechas = [];
        console.log(historial);
        if (string.length > 0) {
            listaFechas = string.split('.');
            let nombre;
            if (listaFechas.length === 1) {
                nombre = listaFechas[0];
            } else if (listaFechas.length > 1) {
                nombre = listaFechas[0] + " al " + listaFechas.at(-1);
            }

            let nombreClase = "btn btn-sm btn-warning aus-btn-justificar";
            let mensaje = "Justificar";

            if (nombre.length > 3) {
                if (historial[nombre.split(" al ")[0]][1] === true) {
                    nombreClase = "btn btn-sm btn-success aus-btn-justificar";
                    mensaje = "Re-justificar";
                }
            }
            let trElemento = document.createElement("tr");
            let tdElementoCabecera = document.createElement("td");
            let tdElementoIntervalo = document.createElement("td");
            let tdElementoButton = document.createElement("td");
            let elementoButton = document.createElement("button");

            elementoButton.className = nombreClase;
            elementoButton.innerText = mensaje;
            tdElementoCabecera.innerHTML = "No asiste";
            tdElementoIntervalo.innerHTML = nombre;
            tdElementoButton.appendChild(elementoButton);

            trElemento.appendChild(tdElementoCabecera);
            trElemento.appendChild(tdElementoIntervalo);
            trElemento.appendChild(tdElementoButton);

            return trElemento;
        }
    }

function calcularDiasControl(ausencias, boolean) {

    let selectedYear = parseInt(document.getElementById('yearSelect').value);

    let fechas = crearFechasDirAusencias(ausencias);

    let currentDay = new Date().getDate();
    let currMonth = new Date().getMonth();

    let contadores = {}

    let historialVacaciones = {}
    let historialAusencias = {}

    Object.keys(fechas).forEach(year => {

        if (!contadores[year]) {
            contadores[year] = {};
        }

        let contadorTotales = 25;
        let contadorAceptados = 0;
        let contadorAprobar = 0;
        let contadorDisfrutados = 0;

        Object.keys(fechas[year]).forEach(month => {
            Object.keys(fechas[year][month]).forEach(dayNum => {

                let dayName = new Date(year, month, dayNum).getDay();
                if (dayName !== 0 && dayName !== 6) {
                    if (fechas[year][month][dayNum][1] === false && fechas[year][month][dayNum][0] === 1) {
                        contadorAprobar += 1;
                        historialVacaciones[createSimpleDate(dayNum, month, year)] = "Pendiente";

                    } else if (fechas[year][month][dayNum][1] === true && fechas[year][month][dayNum][0] === 1) {
                        if (parseInt(month) < currMonth || (parseInt(month) === currMonth && dayNum <= currentDay)) {
                            contadorDisfrutados += 1;
                            historialVacaciones[createSimpleDate(dayNum, month, year)] = "Disfrutada";
                        } else {
                            contadorAceptados += 1;
                            historialVacaciones[createSimpleDate(dayNum, month, year)] = "Aceptada";
                        }
                    } else if ([2, 3, 4].includes(fechas[year][month][dayNum][0]) && fechas[year][month][dayNum][1] === false) {
                        historialAusencias[createSimpleDate(dayNum, month, year)] = ["No asiste", fechas[year][month][dayNum][2]];
                    }
                }
            })
        })

        contadores[year] = {
            "totales": contadorTotales,
            "aceptados": contadorAceptados,
            "aprobar": contadorAprobar,
            "disfrutados": contadorDisfrutados
        }

        if (boolean === true) {
            document.querySelector('.contador-totales').innerHTML = "Totales: " + (contadores[selectedYear]["totales"] ?? "-")
            document.querySelector('.contador-aceptados').innerHTML = "Aceptados: " + (contadores[selectedYear]["aceptados"] ?? "-")
            document.querySelector('.contador-aprobar').innerHTML = "Por aprobar: " + (contadores[selectedYear]["aprobar"] ?? "-")
            document.querySelector('.contador-disfrutados').innerHTML = "Disfrutados: " + (contadores[selectedYear]["disfrutados"] ?? "-")
        }

        let historialVacacionesOrdenado = ordenarFechas(historialVacaciones);
        let historialAusenciasOrdenado = ordenarFechas(historialAusencias);

        if (boolean === true) {
            let divTablaHistorial = document.querySelector('.historial-vacaciones-tabla');
            let divTablaIncidencias = document.querySelector('.historial-vacaciones-tabla-noasis')
            divTablaHistorial.innerHTML = "";
            divTablaIncidencias.innerHTML = "";

            // Tabla de vacaciones
            Object.keys(historialVacacionesOrdenado).forEach(key => {
                let trElement = document.createElement("tr");
                let tdElementFecha = document.createElement("td");
                let tdElementEstado = document.createElement("td");
                let tdElementButton = document.createElement("td");

                tdElementFecha.innerHTML = key;
                tdElementEstado.innerHTML = historialVacacionesOrdenado[key];

                trElement.appendChild(tdElementFecha);
                trElement.appendChild(tdElementEstado);

                if (historialVacacionesOrdenado[key] === "Aceptada" || historialVacacionesOrdenado[key] === "Pendiente") {
                    // Botón pendiente
                    tdElementButton.innerHTML = "   ";
                } else {
                    tdElementButton.innerHTML = "   -";
                }

                trElement.appendChild(tdElementButton);
                divTablaHistorial.appendChild(trElement);
            })

            // Tabla de ausencias
            let keys = Object.keys(historialAusenciasOrdenado);
            let grupo = [];

            for (let i = 0; i < keys.length; i++) {
                const fechaActual = keys[i];
                const [diaAct, mesAct] = fechaActual.split("-").map(n => parseInt(n));
                const fechaSig = keys[i + 1];
                let esContigua = false;

                if (fechaSig) {
                    const [diaSig, mesSig] = fechaSig.split("-").map(n => parseInt(n));
                    esContigua = (mesAct === mesSig) && (diaSig === diaAct + 1);
                }

                grupo.push(fechaActual);

                if (!esContigua || i === keys.length - 1) {
                    let stringGrupo = grupo.join(".");
                    let elemento = createAusenciaLaboral(stringGrupo, historialAusenciasOrdenado);
                    if (elemento) {
                        divTablaIncidencias.appendChild(elemento);
                    }
                    grupo = [];
                }
            }
        }

    });

    return [historialVacaciones, historialAusencias];
}



document.addEventListener("DOMContentLoaded", function () {
        if (window.location.pathname === '/gestion') {
            let ausencias = [];

            fetch('/gestion/ausencias')
                .then(response => response.json())
                .then(data => {
                    ausencias = data;
                    let ausenciasList = calcularDiasControl(ausencias, true);

                    abrirModal();
                });

        }
    });


    /*********************************************Enviar Justificacion********************************************/
    if (window.location.pathname === "/gestion") {
        let fecha = null;
        let btn = null;
        document.addEventListener('click', function (e) {
            if (e.target.classList.contains('aus-btn-justificar')) {
                const tr = e.target.closest('tr');
                //const fecha = tr?.children[1]?.textContent?.trim();
                fecha = tr?.children[1]?.innerHTML;
                btn = e;
            }
        });
        document.getElementById("aus-form-justificacion").addEventListener("submit", async function (e) {
            e.preventDefault();

            const asuntoSelect = document.getElementById("aus-asunto");
            const descripcionInput = document.getElementById("aus-descripcion");
            const archivosInput = document.getElementById("aus-archivos");

            const asuntoValue = asuntoSelect.value;
            const descripcionValue = descripcionInput.value.trim();

            if (asuntoValue === "Selecciona una opción" || !descripcionValue) {
                alert("Por favor, selecciona un asunto y completa la descripción.");
                return;
            }

            const formData = new FormData();
            formData.append("fecha", fecha);
            formData.append("asunto", asuntoValue);
            formData.append("descripcion", descripcionValue);

            for (const file of archivosInput.files) {
                formData.append("archivos", file); // Puedes usar "archivos[]" si esperas un array en backend
            }

            try {
                const response = await fetch("/gestion/justificacion", {
                    method: "POST",
                    body: formData
                    // headers: {
                    //     "Content-Type": "application/json"
                    // },
                    // body: JSON.stringify({
                    //     fecha: fecha,
                    //     asunto: asuntoValue,
                    //     descripcion: descripcionValue
                    // })
                });

                if (response.ok) {
                    console.log("Justificación enviada correctamente.");
                    location.reload();

                    // Puedes cerrar el modal o resetear el formulario aquí
                } else {
                    console.log("Error al enviar la justificación.");
                }
            } catch (err) {
                console.error(err);
                alert("Error de red.");
            }
        });

        document.getElementById('aus-form-justificacion').addEventListener('submit', function (event) {
            event.preventDefault();  // evitar envío real si quieres controlar con JS
            // Aquí podrías procesar el formulario o validaciones
            // Obtener el modal padre
            const modal = this.closest('.modal');
            // Obtener instancia de Bootstrap Modal
            const instance = bootstrap.Modal.getInstance(modal);
            // Ocultar el modal
            instance?.hide();
            // Eliminar todos los backdrops (por si quedan)
            document.querySelectorAll('.modal-backdrop').forEach(el => el.remove());
        });
    }

    /*****************************Gestion Ausencias calendario completo***********************/
    if (window.location.pathname === '/gestion') {

        let holidaysTaken = [];
        fetch('/gestion/ausencias')
            .then(response => response.json())
            .then(data => {
                ausencias = data;
                let ausenciasList = calcularDiasControl(ausencias, false);
                holidaysTaken = Object.keys(ausenciasList[0]);

                const calendarContainer = document.getElementById("calendarContainer");
                const yearDisplay = document.getElementById("yearDisplay");
                let currentYear = new Date().getFullYear();
                const selectedDays = new Set();
                let remainingDays = 25 - holidaysTaken.length;
                const maxSelectableDays = remainingDays;
                const remainingDaysEl = document.getElementById("remainingDays");


                //holidaysTaken = ["12-01-2025", "25-12-2025", "01-01-2025", "15-08-2025", "01-05-2025"];
                const monthNames = [
                    "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                    "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
                ];

                const weekDays = ["L", "M", "X", "J", "V", "S", "D"];

                function updateRemainingDays() {
                    remainingDaysEl.textContent = remainingDays;
                }

                function generateCalendar(year) {
                    calendarContainer.innerHTML = "";
                    yearDisplay.textContent = year;

                    for (let m = 0; m < 12; m++) {
                        const firstDayOfMonth = new Date(year, m, 1);
                        const lastDayOfMonth = new Date(year, m + 1, 0);
                        const totalDaysInMonth = lastDayOfMonth.getDate();
                        const firstWeekDay = (firstDayOfMonth.getDay() + 6) % 7;

                        const monthDiv = document.createElement("div");
                        monthDiv.className = "month-container-modal-gest shadow-sm";

                        const title = document.createElement("h6");
                        title.className = "text-center text-muted";
                        title.textContent = monthNames[m];
                        monthDiv.appendChild(title);

                        const weekHeader = document.createElement("div");
                        weekHeader.className = "calendar-grid-modal-gest";
                        weekDays.forEach(day => {
                            const dayEl = document.createElement("div");
                            dayEl.className = "calendar-day-name-modal-gest font-weight-bold";
                            dayEl.textContent = day;
                            weekHeader.appendChild(dayEl);
                        });
                        monthDiv.appendChild(weekHeader);

                        const grid = document.createElement("div");
                        grid.className = "calendar-grid-modal-gest";

                        const totalCells = 42;
                        const prevMonth = new Date(year, m, 0);
                        const prevMonthDays = prevMonth.getDate();

                        for (let i = 0; i < totalCells; i++) {
                            const dayDiv = document.createElement("div");
                            let date;
                            let displayDay;
                            let classList = "calendar-day-modal-gest";

                            if (i < firstWeekDay) {
                                displayDay = prevMonthDays - firstWeekDay + i + 1;
                                classList = "faded-day-modal-gest";
                                date = new Date(year, m - 1, displayDay);
                            } else if (i >= firstWeekDay + totalDaysInMonth) {
                                displayDay = i - (firstWeekDay + totalDaysInMonth) + 1;
                                classList = "faded-day-modal-gest";
                                date = new Date(year, m + 1, displayDay);
                            } else {
                                displayDay = i - firstWeekDay + 1;
                                date = new Date(year, m, displayDay);
                                const id = `${displayDay.toString().padStart(2, '0')}-${(m + 1).toString().padStart(2, '0')}-${year}`;
                                classList += ` ${id}`;
                                if (holidaysTaken.includes(id)) {
                                    classList += " inactive-modal-gest";
                                }
                            }

                            dayDiv.className = classList;
                            dayDiv.textContent = displayDay;

                            const id = `${date.getDate().toString().padStart(2, '0')}-${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getFullYear()}`;

                            if (!dayDiv.classList.contains("faded-day-modal-gest") && !dayDiv.classList.contains("inactive-modal-gest")) {
                                dayDiv.addEventListener("click", () => {
                                    if (dayDiv.classList.contains("selected-modal-gest")) {
                                        dayDiv.classList.remove("selected-modal-gest");
                                        selectedDays.delete(id);
                                        remainingDays++;
                                        updateRemainingDays();
                                    } else {
                                        if (remainingDays > 0) {
                                            dayDiv.classList.add("selected-modal-gest");
                                            selectedDays.add(id);
                                            remainingDays--;
                                            updateRemainingDays();
                                        }
                                    }
                                });
                            }

                            grid.appendChild(dayDiv);
                        }

                        monthDiv.appendChild(grid);
                        calendarContainer.appendChild(monthDiv);
                    }
                }

                document.getElementById("prevYearBtn").addEventListener("click", () => {
                    currentYear--;
                    generateCalendar(currentYear);
                });

                document.getElementById("nextYearBtn").addEventListener("click", () => {
                    currentYear++;
                    generateCalendar(currentYear);
                });

                document.getElementById("sendSelectedDays").addEventListener("click", async function (e) {
                    e.preventDefault();
                    const diasTotales = agruparFechasConsecutivas(selectedDays);
                    if (diasTotales.length >= 1) {

                        try {
                            //console.log(JSON.stringify(diasTotales));
                            const response = await fetch("/gestion/pedirVacaciones", {
                                method: "POST",
                                headers: {"Content-Type": "application/json"},
                                body: JSON.stringify(diasTotales),
                            });

                            if (response.ok) {
                                console.log("Vacaciones enviadas correctamente.");
                                location.reload();

                                // Puedes cerrar el modal o resetear el formulario aquí
                            } else {
                                console.log("Error al enviar las vacaciones.");
                            }
                        } catch (err) {
                            console.error(err);
                            alert("Error de red.");
                        }
                    }

                });

                generateCalendar(currentYear);
                updateRemainingDays();

                //añadimos inactive a sabados y domingos.
                //inactive-modal-gest

                let year = parseInt(yearDisplay.innerHTML);
                const startDate = new Date(year, 0, 1); // enero = 0
                const endDate = new Date(year, 11, 31); // diciembre = 11

                const currentDate = new Date();
                while (startDate <= endDate) {
                    //console.log("Estoy en el while");
                    const dayOfWeek = startDate.getDay();
                    const day = startDate.getDate().toString();
                    const mes = (startDate.getMonth()).toString();
                    const year = startDate.getFullYear()
                    const fechaStr = createSimpleDate(day, mes, year);

                    const cell = document.getElementsByClassName(fechaStr)[0];

                    // Si es sábado (6) o domingo (0)
                    if (dayOfWeek === 6 || dayOfWeek === 0) {
                        // Seleccionamos el elemento con esa fecha
                        if (cell) {
                            cell.classList.add("inactive-modal-gest"); // clase personalizada
                        }
                    } else if (startDate <= currentDate) {
                        cell.classList.add("inactive-modal-gest");
                    }
                    startDate.setDate(startDate.getDate() + 1);
                }
            })
    }

    /***********************************Gestion vacaciones modificar****************************************/
    if (window.location.pathname === "/gestion") {
        fetch('/gestion/ausencias')
            .then(response => response.json())
            .then(data => {
                ausencias = data;
                const tbodyElement = document.querySelector('.historial-vacaciones-modificar-tabla');
                const currentDate = new Date();
                ausencias.forEach(ausencia => {

                    if (ausencia.tipoAusenciaId === 1) {
                        const fechaIni = ausencia.fechaInicio;
                        const fechaFin = ausencia.fechaFin;

                        const iniDate = new Date(fechaIni);
                        const trElement = document.createElement("tr");

                        let fechaName;
                        if (fechaIni === fechaFin) {
                            fechaName = fechaIni;
                        } else {
                            fechaName = fechaIni + " al " + fechaFin;
                        }

                        const tdElementName = document.createElement("td");
                        tdElementName.innerHTML = fechaName;
                        const tdElementAction = document.createElement("td");

                        const fechaLimite = new Date(currentDate);
                        fechaLimite.setDate(currentDate.getDate() + 14)
                        if (iniDate >= fechaLimite) {

                            let elementLable = document.createElement("label");
                            let elementInput = document.createElement("input");
                            let elementSpan = document.createElement("span");

                            elementLable.classList = "aus-checkbox-btn";
                            elementInput.type = "checkbox";
                            elementInput.name = "accion";
                            elementInput.value = "R";
                            elementSpan.classList = "btn-letter";
                            elementSpan.innerHTML = "R";
                            elementInput.setAttribute("data-fecha", fechaIni);


                            elementLable.appendChild(elementInput);
                            elementLable.appendChild(elementSpan);
                            tdElementAction.appendChild(elementLable);
                        } else {

                            tdElementAction.innerHTML = "-";
                        }

                        trElement.appendChild(tdElementName);
                        trElement.appendChild(tdElementAction);

                        tbodyElement.appendChild(trElement);

                    }

                })
            });
        // Lista global para mantener las fechas seleccionadas
        const fechasSeleccionadas = [];

        // Delegamos el evento en el contenedor
        document.addEventListener('change', function (event) {
            const checkbox = event.target;

            // Nos aseguramos que sea un checkbox con name="accion"
            if (checkbox.matches('input[type="checkbox"][name="accion"]')) {
                const fecha = checkbox.getAttribute('data-fecha');

                if (checkbox.checked) {
                    if (!fechasSeleccionadas.includes(fecha)) {
                        fechasSeleccionadas.push(fecha);
                    }
                } else {
                    const index = fechasSeleccionadas.indexOf(fecha);
                    if (index > -1) {
                        fechasSeleccionadas.splice(index, 1);
                    }
                }

                console.log('Fechas seleccionadas:', fechasSeleccionadas);
            }

        });

        document.querySelector(".aus-btn-enviar-cancelar").addEventListener("click", async function (e) {
            e.preventDefault();
            if (fechasSeleccionadas.length > 0) {
                //console.log(fechasSeleccionadas);
                const diasTotales = agruparFechasConsecutivas(fechasSeleccionadas);
                //console.log(diasTotales);
                if (diasTotales.length >= 1) {
                    try {
                        //console.log(JSON.stringify(diasTotales));
                        const response = await fetch("/gestion/anularVacaciones", {
                            method: "POST",
                            headers: {"Content-Type": "application/json"},
                            body: JSON.stringify(diasTotales),
                        });
                        if (response.ok) {
                            console.log("Vacaciones para anular enviadas correctamente.");
                            location.reload();

                            // Puedes cerrar el modal o resetear el formulario aquí
                        } else {
                            console.log("Error al enviar las vacaciones para anular.");
                        }
                    } catch (err) {
                        console.error(err);
                        alert("Error de red.");
                    }
                }
            }
        });

    }

    /*******************************Gestion companeros justificacion******************************/
    if (window.location.pathname === "/gestionVRes") {
        // Crear tooltip global una vez
        const tooltip = document.createElement("div");
        tooltip.id = "aus-tooltip";
        tooltip.classList = "aus-tooltip";
        tooltip.style.display = "none";
        document.body.appendChild(tooltip);
        console.log("Estas en gestionVres!!")
        fetch('/companeros-con-ausencias').then(response => response.json())
            .then(data => {
                let ausencias = data;
                let divGeneral = document.querySelector(".aus-content-usuarios");
                //console.log(ausencias);
                Object.keys(ausencias).forEach(index => {
                    // Crear estructura de usuario
                    let divName1 = document.createElement("div");
                    divName1.classList = "aus-usuario-info";

                    let divName2 = document.createElement("div");
                    divName2.classList = "aus-imagen-container";

                    let divName3 = document.createElement("div");
                    divName3.classList = "aus-circle-decorative1";

                    let divName4 = document.createElement("div");
                    divName4.classList = "aus-circle-decorative2";

                    let divName5 = document.createElement("div");
                    divName5.classList = "aus-circle-decorativePNG";

                    let divImg = document.createElement("img");
                    divImg.src = "/images/perfil.png";
                    divImg.alt = "perfil";

                    divName5.appendChild(divImg);
                    divName4.appendChild(divName5);
                    divName3.appendChild(divName4);
                    divName2.appendChild(divName3);
                    divName1.appendChild(divName2);

                    let divInfo = document.createElement("div");
                    divInfo.classList = "aus-personal-info";

                    let spanNombre = document.createElement("span");
                    const nombreInc = `${ausencias[index].nombre} ${ausencias[index].apellidos}`;
                    spanNombre.classList = "aus-nombre " + nombreInc.replaceAll(" ",".");

                    spanNombre.textContent = nombreInc;

                    divInfo.appendChild(spanNombre);
                    divName1.appendChild(divInfo);
                    divGeneral.appendChild(divName1);

                    // Modal y tabla
                    const modalContainer = document.getElementById("aus-modal-container");
                    const modalOverlay = modalContainer.querySelector(".aus-modal-overlay");
                    const tbody = document.querySelector(".aus-table-modal-body");

                    divName1.addEventListener("click", function () {
                        tbody.innerHTML = "";
                        ausencias[index].ausencias.forEach(ausencia => {
                            if (ausencia.aprobado === false) {
                                // Crear fila y celdas
                                let trElement = document.createElement("tr");
                                let tdTitulo = document.createElement("td");
                                let tdFechas = document.createElement("td");
                                let tdAcciones = document.createElement("td");

                                let boolVar = ausencia.tipoAusencias.id === 1;
                                tdTitulo.innerHTML = boolVar ? "Vacaciones" : "No asiste";

                                let fechaIni = ausencia.fechaInicio;
                                let fechaFin = ausencia.fechaFin;
                                tdFechas.innerHTML = (fechaIni === fechaFin) ? fechaIni : `${fechaIni} al ${fechaFin}`;

                                // Crear acciones
                                let tdLabel1 = document.createElement("label");
                                tdLabel1.classList = "aus-checkbox-btn";
                                let input1 = document.createElement("input");
                                input1.type = "checkbox";
                                input1.name = "accion";
                                input1.value = "A";
                                let span1 = document.createElement("span");
                                span1.classList = "btn-letter";
                                span1.innerHTML = "A";

                                let tdLabel2 = document.createElement("label");
                                tdLabel2.classList = "aus-checkbox-btn";
                                let input2 = document.createElement("input");
                                input2.type = "checkbox";
                                input2.name = "accion";
                                input2.value = "R";
                                let span2 = document.createElement("span");
                                span2.classList = "btn-letter";
                                span2.innerHTML = "R";

                                tdLabel1.appendChild(input1);
                                tdLabel1.appendChild(span1);
                                tdLabel2.appendChild(input2);
                                tdLabel2.appendChild(span2);
                                tdAcciones.appendChild(tdLabel1);
                                tdAcciones.appendChild(tdLabel2);

                                const justificacion = ausencia.justificacion;

                                if (!boolVar) {
                                    let tdButton = document.createElement("button");
                                    tdButton.classList = `btn btn-warning aus-btn ${fechaIni}-${fechaFin}`;
                                    tdButton.innerHTML = "D";

                                    // Crear tooltip
                                    let tooltip = document.createElement("div");
                                    tooltip.classList = "aus-tooltip";
                                    tooltip.style.left = "0px";
                                    tooltip.style.top = "0px";
                                    document.body.appendChild(tooltip);

                                    if (justificacion === null) {
                                        // Simular estado deshabilitado sin usar 'disabled'
                                        input1.disabled = true;
                                        input2.disabled = true;
                                        tdLabel1.style.opacity = "0.5";
                                        tdLabel2.style.opacity = "0.5";
                                        tdLabel1.style.pointerEvents = "none";
                                        tdLabel2.style.pointerEvents = "none";

                                        // Botón D apariencia deshabilitada pero sin disabled
                                        tdButton.style.opacity = "0.5";
                                        tdButton.style.cursor = "not-allowed";
                                        tdButton.setAttribute("data-disabled", "true"); // atributo personalizado para controlar

                                        // Prevenir clic si "deshabilitado"
                                        tdButton.addEventListener("click", (e) => {
                                            if (tdButton.getAttribute("data-disabled") === "true") {
                                                e.preventDefault();
                                                e.stopPropagation();
                                            }
                                        });

                                        tdButton.addEventListener("mouseenter", (e) => {
                                            tooltip.innerHTML = "Justificación no disponible";
                                            tooltip.classList.add("visible");
                                            tooltip.style.left = (e.pageX + 10) + "px";
                                            tooltip.style.top = (e.pageY + 10) + "px";
                                        });

                                        tdButton.addEventListener("mousemove", (e) => {
                                            tooltip.style.left = (e.pageX + 10) + "px";
                                            tooltip.style.top = (e.pageY + 10) + "px";
                                        });

                                        tdButton.addEventListener("mouseleave", () => {
                                            tooltip.classList.remove("visible");
                                        });
                                    } else {
                                        // Si justificación existe, comportamiento normal
                                        const justList = ["Enfermedad o Incapacidad temporal", "Cita Médica", "Permiso Personal", "Permiso retribuido", "Huelga", "Baja maternidad", "Reducción de jornada"]
                                        tdButton.addEventListener("mouseenter", (e) => {
                                            const motivo = justList[parseInt(justificacion.split("//")[0].trim()) - 1];
                                            const cuerpoJust = justificacion.split("//")[1].trim();
                                            tooltip.innerHTML = motivo + " - " + cuerpoJust;
                                            tooltip.classList.add("visible");
                                            tooltip.style.left = (e.pageX + 10) + "px";
                                            tooltip.style.top = (e.pageY + 10) + "px";
                                        });

                                        tdButton.addEventListener("mousemove", (e) => {
                                            tooltip.style.left = (e.pageX + 10) + "px";
                                            tooltip.style.top = (e.pageY + 10) + "px";
                                        });

                                        tdButton.addEventListener("mouseleave", () => {
                                            tooltip.classList.remove("visible");
                                        });
                                    }

                                    tdAcciones.appendChild(tdButton);
                                }

                                trElement.appendChild(tdTitulo);
                                trElement.appendChild(tdFechas);
                                trElement.appendChild(tdAcciones);
                                tbody.appendChild(trElement);
                                tbody.classList = nombreInc.replaceAll(" ",".");
                            }
                        });

                        // Comportamiento de checkboxes (solo uno activo)
                        tbody.querySelectorAll('tr').forEach(fila => {
                            const checks = fila.querySelectorAll('input[name="accion"]');
                            checks.forEach(check => {
                                check.addEventListener("change", function () {
                                    if (this.checked) {
                                        checks.forEach(c => {
                                            if (c !== this) c.checked = false;
                                        });
                                    }
                                });
                            });
                        });

                        modalContainer.style.display = "block";
                        modalOverlay.style.display = "block";
                    });

                    // Cerrar modal
                    document.getElementById("aus-closeModal").addEventListener("click", function () {
                        modalContainer.style.display = "none";
                        modalOverlay.style.display = "none";
                        document.querySelectorAll('.aus-modal-background').forEach(el => el.style.display = 'none');
                        tooltip.style.display = "none";
                    });
                });
            });
    }

/*Download justificantes*/
if (window.location.pathname === "/gestionVRes") {
    document.addEventListener('click', async function (event) {
        const elemento = event.target;
        if (elemento.innerHTML === "D") {
            const trPadre = elemento.closest('tr');
            const name = elemento.closest('tbody').classList[0];
            const fechas = trPadre.children[1].innerHTML;
            const fechaIni = fechas.split(" al ")[0];
            const fechaFin = fechas.split(" al ")[1];
            // Para nombre archivo mejor usar guiones en vez de puntos
            const fechasSeleccionadas = fechaIni + "." + fechaFin;

            try {
                const response = await fetch('/gestionVRes/aprobar-justificacion', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'text/plain'
                    },
                    body: name.replaceAll(".","-") + "." + fechasSeleccionadas
                });

                if (!response.ok) {
                    throw new Error('Error al comunicar con el backend');
                }

                // Asegurarse de que la respuesta es un blob con tipo zip
                const blob = await response.blob();

                // Crear URL blob y forzar descarga
                const url = window.URL.createObjectURL(new Blob([blob], { type: 'application/zip' }));

                const a = document.createElement('a');
                a.href = url;
                a.download = `justificantes_${fechasSeleccionadas}.zip`;
                document.body.appendChild(a);
                a.click();
                a.remove();

                window.URL.revokeObjectURL(url);

            } catch (error) {
                console.error("Error enviando las fechas o descargando:", error);
            }
        }
    });
}

/*Enviar resolucion justificacion*/
if (window.location.pathname === "/gestionVRes") {
    document.querySelector('#aus-enviarInfo').addEventListener('click', async function (event) {
        const name = document.querySelector('#aus-modalOverlay tbody').classList[0];
        let dirRespuestas = {};
        dirRespuestas[name] = {};
        const trElementos = document.querySelector('#aus-modalOverlay tbody').querySelectorAll('tr');
        trElementos.forEach(trElemento => {
            const tdElementos = trElemento.children[2].children;
            for (const tdElemento of tdElementos) {
                if (tdElemento.tagName === 'LABEL') {
                    const checkbox = tdElemento.querySelector('input[type="checkbox"]')
                    if (checkbox.checked) {
                        const spanElement = tdElemento.children[1];
                        const trElementParent = spanElement.closest('tr');
                        const fecha = trElementParent.children[1].innerHTML.split(" al ");
                        let fechaFinal = "";
                        if (fecha.length > 1) {
                            fechaFinal = fecha[0] + "." + fecha[1];
                        } else {
                            fechaFinal = fecha[0] + "." + fecha[0];
                        }
                        const nombreResolucion = spanElement.innerHTML;
                        let resolucion = false;
                        if (nombreResolucion === "A") {
                            resolucion = true;
                        }
                        dirRespuestas[name][fechaFinal] = resolucion;
                    }
                }
            }
        })
        try {
            const response = await fetch('/gestionVRes/resolucion', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(dirRespuestas)
            });

            if (!response.ok) {
                throw new Error('Error al comunicar con el backend');
            }else {
                console.log("Justificacion enviada correctamente")
                location.reload();
            }

        } catch (error) {
            console.error("Error enviando las fechas o descargando:", error);
        }
    })
}

/********************************GESTION CALENDARIO***********************************/
function marcarDiaConPunto(fechaLista, yearSelected, monthSelected) {
    const date = new Date(fechaLista);
    const year = date.getFullYear().toString();
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const day = date.getDate().toString().padStart(2, '0');

    if (yearSelected === year && monthSelected === month) {
        if (![0, 6].includes(date.getDay())) {
            const dayElement = document.querySelector(`.day[data-date="${day}"]`);
            if (dayElement) {
                // No añadir otro punto si ya existe
                if (!dayElement.querySelector('span.dot')) {
                    const puntoSpan = document.createElement("span");
                    puntoSpan.className = "dot";
                    dayElement.appendChild(puntoSpan);
                }
            }
        }
    }
}


if (window.location.pathname === "/calendario") {
    let booleanVar = true; // variable global para el estado actual

    function limpiarMarcas() {
        // Seleccionamos todos los spans con clase dot dentro de cualquier elemento con clase day
        const puntos = document.querySelectorAll('.day span.dot');
        puntos.forEach(punto => {
            punto.remove();  // elimina físicamente el span del DOM
        });
    }

    function locateTareas(boolean) {
        limpiarMarcas();
        // Aquí tu código actual, con fetch dinámico según boolean
        const endpoint = boolean ? "/calendario/tareas" : "/calendario/tareas-general";

        fetch(endpoint)
                .then(response => {
                    if (!response.ok) {
                        throw new Error("Error al obtener los tareas");
                    }
                    return response.json();
                })
                .then(data => {
                    tareas = data;
                    let fechas = [];
                    contenidos = {};
                    tareas.forEach( (tarea, index) => {
                        const fechaIni = tarea.fechaInicio;
                        const fechaFin = tarea.fechaFin;
                        const fechaEli = tarea.fechaEliminada;
                        const fechaLim = tarea.fechaLimite;
                        const titulo = tarea.titulo;
                        const descripcion = tarea.descripcion;
                        const tipoTarea = tarea.tipoTarea;

                        if (fechaEli === null && fechaFin === null) {
                            if (fechaIni === fechaLim) {
                                fechas.push([fechaIni])
                                contenidos[fechaIni + "." + index] = [titulo,descripcion,tipoTarea]
                            }else {
                                const yearIni = fechaIni.split("-")[0];
                                const monthIni = fechaIni.split("-")[1];
                                const dayIni = fechaIni.split("-")[2];
                                const yearLim = fechaLim.split("-")[0];
                                const monthLim = fechaLim.split("-")[1];
                                const dayLim = fechaLim.split("-")[2];
                                let temp = [];
                                if (yearIni === yearLim) {
                                    if (monthIni === monthLim) {
                                        const start = parseInt(dayIni), end = parseInt(dayLim);
                                        const intervalo = Array.from({length: end - start + 1}, (_, i) => `${yearIni}-${monthIni}-${start + i}`);
                                        temp.push(intervalo);
                                        for (const int of intervalo) {
                                            contenidos[int + "." + index] = [titulo,descripcion,tipoTarea]
                                        }

                                    }
                                }
                                fechas.push([temp])
                            }
                        }

                    })
                    //console.log(fechas, contenidos);

                    const yearselect = document.querySelector('#yearSelect');
                    const yearSelected = String(yearselect.value);
                    const monthselect = document.querySelector('#monthSelect');
                    const mesCeroIndex = parseInt(monthselect.value); // valor del select, 0-based
                    const monthSelected = String(mesCeroIndex + 1).padStart(2, '0'); // formatea a "01", "02", ...


                    for (let i = 0; i < fechas.length; i++) {
                        let fechaLista = fechas[i];
                        for (const element of fechaLista) {
                            if (Array.isArray(element)) {
                                for (const elm of element) {
                                    for (const a of elm) {
                                        marcarDiaConPunto(a,yearSelected,monthSelected);
                                        //console.log(i,a,yearSelected,monthSelected);
                                    }
                                }
                            }else {
                                marcarDiaConPunto(element,yearSelected,monthSelected);
                                //console.log(i,element,yearSelected,monthSelected);
                            }
                        }
                    }

                    const days = document.querySelectorAll('.day');
                    let modalContent = document.querySelector('.modal-content-text');

                    // Eliminar event listeners previos para evitar duplicados
                    days.forEach(day => {
                        day.replaceWith(day.cloneNode(true));
                    });

                    // Volver a seleccionar para añadir listeners
                    const newDays = document.querySelectorAll('.day');
                    const listaTareas = ['Desarrollo', 'Corrección de errores', 'Documentación', 'Testing', 'Revisión de código', 'Reunión', 'Evento'];

                    newDays.forEach(day => {
                        day.style.cursor = 'pointer';
                        day.addEventListener('click', (e) => {
                            const daySelected = day.getAttribute('data-date');
                            modalContent.innerHTML = "";
                            if (daySelected) {
                                const formattedDay = daySelected.trim().padStart(2, '0');
                                const yearselect = document.querySelector('#yearSelect');
                                const yearSelected = String(yearselect.value);
                                const monthselect = document.querySelector('#monthSelect');
                                const mesCeroIndex = parseInt(monthselect.value); // valor del select, 0-based
                                const monthSelected = String(mesCeroIndex + 1).padStart(2, '0'); // formatea a "01", "02", ...

                                const reconstructedDate = yearSelected + "-" + monthSelected + "-" + formattedDay
                                console.log(reconstructedDate);
                                const tareasParaFecha = Object.keys(contenidos).filter(date => date.split(".")[0] === reconstructedDate);

                                modalContent.innerHTML = ""; // limpio antes

                                if (tareasParaFecha.length === 0) {
                                    const spanElement = document.createElement("span");
                                    spanElement.textContent = "No hay tareas para el día seleccionado.";
                                    modalContent.appendChild(spanElement);
                                } else {
                                    tareasParaFecha.forEach(date => {
                                        const [titulo, descripcion, tipoTarea] = contenidos[date];

                                        const tareaDiv = document.createElement("div");
                                        tareaDiv.classList.add("tarea");

                                        const h4Element = document.createElement("h4");
                                        h4Element.innerText = titulo;

                                        const descripcionSpan = document.createElement("span");
                                        descripcionSpan.innerText = descripcion;

                                        const tipoSpan = document.createElement("span");
                                        tipoSpan.innerText = "Tipo de tarea: " + listaTareas[parseInt(tipoTarea)-1];

                                        tareaDiv.appendChild(h4Element);
                                        tareaDiv.appendChild(descripcionSpan);
                                        tareaDiv.appendChild(tipoSpan);

                                        modalContent.appendChild(tareaDiv);
                                    });
                                }
                            }
                        })
                    })
                })
                .catch(error => {
                    console.error("Error:", error);
                    limpiarMarcas();
                });
        }

    // Ejecutar la función al cargar la página
    document.addEventListener("DOMContentLoaded", () => {
        locateTareas(booleanVar);

        // Añadir listeners para recargar datos al cambiar año o mes
        const yearSelect = document.getElementById('yearSelect');
        const monthSelect = document.getElementById('monthSelect');

        yearSelect.addEventListener('change', () => {
            locateTareas(booleanVar);
        });

        monthSelect.addEventListener('change', () => {
            locateTareas(booleanVar);
        });

        // Añadir listeners para flechas de mes (cambia los selectores si es necesario)
        const btnPrev = document.getElementById('prevMonth');
        const btnNext = document.getElementById('nextMonth');

        if (btnPrev) {
            btnPrev.addEventListener('click', () => {
                locateTareas(booleanVar);
            });
        }
        if (btnNext) {
            btnNext.addEventListener('click', () => {
                locateTareas(booleanVar);
            });
        }

        const btnIndividual = document.getElementById('btnIndividual');
        const btnGeneral = document.getElementById('btnGeneral');

        if (btnIndividual) {
            btnIndividual.addEventListener('click', () => {
                booleanVar = true;
                locateTareas(booleanVar);
            });
        }

        if (btnGeneral) {
            btnGeneral.addEventListener('click', () => {
                booleanVar = false;
                locateTareas(booleanVar);
            });
        }
    });
}
