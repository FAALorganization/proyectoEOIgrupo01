document.addEventListener('DOMContentLoaded', () => {
    const input = document.getElementById('buscadorProyectos');
    const lista = document.getElementById('listaProyectos');

    input.addEventListener('focus', () => {
        fetch('/proyectos/todos')
            .then(response => response.json())
            .then(data => {
                lista.innerHTML = ''; // limpiar lista

                data.forEach(proyecto => {
                    const li = document.createElement('li');
                    li.textContent = proyecto.nombre;
                    li.style.padding = '8px 12px';
                    li.style.cursor = 'pointer';
                    li.addEventListener('mousedown', (e) => {
                        e.preventDefault(); // prevenir blur antes del click
                        input.value = proyecto.nombre;
                        lista.style.display = 'none';
                    });
                    li.addEventListener('mouseenter', () => {
                        li.style.backgroundColor = '#eee';
                    });
                    li.addEventListener('mouseleave', () => {
                        li.style.backgroundColor = 'white';
                    });
                    lista.appendChild(li);
                });

                lista.style.display = 'block'; // mostrar lista
            });
    });

    // Ocultar lista cuando se pierde foco, con retraso para click
    input.addEventListener('blur', () => {
        setTimeout(() => {
            lista.style.display = 'none';
        }, 150);
    });
});
