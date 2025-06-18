/*******************************Gestion companeros justificacion******************************/
document.addEventListener("DOMContentLoaded", () => {
    if (window.location.pathname.toLowerCase().includes("gestionvres")) {
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
                console.log(ausencias);
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
                    divImg.src = "/images/personal/" + ausencias[index].token + ".png";
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
});