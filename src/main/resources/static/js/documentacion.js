<!-- JS PARA CONTROLAR LA FUNCIONALIDAD DE SUBIR DOCUMENTACIÓN POR PARTE DEL JEFE -->

document.addEventListener('DOMContentLoaded', () => {
    const uploadForms = document.querySelectorAll('form[action$="/documentacion/subir"]');

    uploadForms.forEach(form => {
        const fileInput = form.querySelector('input[type="file"]');
        const proyectoId = form.querySelector('input[name="idProyecto"]').value;
        const listaDocumentos = document.getElementById(`lista-documentos-${proyectoId}`);

        function cargarDocumentos() {
            fetch(`/documentacion/listar?idProyecto=${proyectoId}`)
                .then(res => res.json())
                .then(documentos => {
                    if (!listaDocumentos) return;

                    if (documentos.length === 0) {
                        listaDocumentos.innerHTML = '<li class="list-group-item text-muted">No hay documentos.</li>';
                        return;
                    }

                    const html = documentos.map(doc => `
                         <li class="list-group-item d-flex justify-content-between align-items-center">
                              <a href="/documentacion/descargar/${doc.id}" target="_blank" download>${doc.nombre}</a>
                              <div>
                              <!-- Botones de acción opcionales -->
                              </div>
                         </li>
                    `).join('');

                    listaDocumentos.innerHTML = html;
                })
                .catch(() => {
                    if (listaDocumentos) {
                        listaDocumentos.innerHTML = '<li class="list-group-item text-danger">Error al cargar documentos.</li>';
                    }
                });
        }

        function cargarOpcionesDocumentos(proyectoId) {
            const select = document.getElementById(`select-documentos-${proyectoId}`);
            if (!select) return;

            fetch(`/documentacion/documentos-proyecto/${proyectoId}`)
                .then(res => res.json())
                .then(documentos => {
                    select.innerHTML = '';

                    if (documentos.length === 0) {
                        const option = document.createElement('option');
                        option.textContent = 'No hay documentos';
                        option.disabled = true;
                        select.appendChild(option);
                        return;
                    }

                    documentos.forEach(doc => {
                        const option = document.createElement('option');
                        option.value = doc.id;
                        option.textContent = doc.nombre;
                        select.appendChild(option);
                    });
                })
                .catch(() => {
                    const option = document.createElement('option');
                    option.textContent = 'Error al cargar documentos';
                    option.disabled = true;
                    select.appendChild(option);
                });
        }

        cargarDocumentos();
        cargarOpcionesDocumentos(proyectoId);

        form.addEventListener('submit', e => {
            e.preventDefault();

            if (!fileInput.files.length) {
                alert('Selecciona al menos un archivo.');
                return;
            }

            const formData = new FormData(form);

            fetch(form.getAttribute('action'), {
                method: 'POST',
                body: formData
            })
                .then(response => {
                    if (!response.ok) {
                        return response.text().then(text => { throw new Error(text) });
                    }
                    return response.text();
                })
                .then(mensaje => {
                    alert(mensaje || 'Archivos subidos correctamente');
                    fileInput.value = '';
                    cargarDocumentos();
                    cargarOpcionesDocumentos(proyectoId);
                })
                .catch(error => {
                    alert(error.message || 'Error al subir archivos');
                });
        });
    });
});
