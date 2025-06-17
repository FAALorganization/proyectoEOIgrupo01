if (window.location.pathname === "/calendario") {
    function marcarDiaConPunto(fechaLista, yearSelected, monthSelected, colorSelected) {
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
                        puntoSpan.style.backgroundColor = colorSelected;
                        dayElement.appendChild(puntoSpan);
                    }
                }
            }
        }
    }
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
                //console.log(tareas);
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
                    const estado = tarea.estado;

                    if (fechaFin === null) {
                        if (fechaIni === fechaLim) {
                            fechas.push([fechaIni,estado])
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
                                    temp.push(intervalo,estado);
                                    for (const int of intervalo) {
                                        contenidos[int + "." + index] = [titulo,descripcion,tipoTarea, estado]
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
                        let isFinished = false;
                        for (const elementList of fechaLista) {
                            if (elementList[1] === "eliminada") {
                                isFinished = true;
                            }
                        }
                        let colorDot = "red";
                        if (isFinished) {
                            colorDot = "yellow";
                        }
                        console.log(element);
                        if (Array.isArray(element) && element[1] === "pendiente") {
                            for (const elm of element) {
                                for (const a of elm) {
                                    marcarDiaConPunto(a,yearSelected,monthSelected, colorDot);
                                    //console.log(i,a,yearSelected,monthSelected);
                                }
                            }
                        }else {
                            marcarDiaConPunto(element,yearSelected,monthSelected, colorDot);
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
                            //console.log(reconstructedDate);
                            const tareasParaFecha = Object.keys(contenidos).filter(date => date.split(".")[0] === reconstructedDate);

                            modalContent.innerHTML = ""; // limpio antes

                            if (tareasParaFecha.length === 0) {
                                const spanElement = document.createElement("span");
                                spanElement.textContent = "No hay tareas para el día seleccionado.";
                                modalContent.appendChild(spanElement);
                            } else {
                                tareasParaFecha.forEach(date => {
                                    const [titulo, descripcion, tipoTarea, estadoTarea] = contenidos[date];
                                    if (estadoTarea === "pendiente") {
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
                                    }

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