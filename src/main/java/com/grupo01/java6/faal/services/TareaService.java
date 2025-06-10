package com.grupo01.java6.faal.services;

import com.grupo01.java6.faal.entities.Tarea;
import com.grupo01.java6.faal.repositories.TareaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TareaService {

    private final TareaRepository tareaRepository;

    public TareaService(TareaRepository tareaRepository) {
        this.tareaRepository = tareaRepository;
    }

    public List<Tarea> obtenerCompletadas() {
        return tareaRepository.findByEstado("completada"); // Solo tareas completadas
    }

    public List<Tarea> obtenerPendientes() {
        return tareaRepository.findByEstado("pendiente");
    }

    public List<Tarea> obtenerEliminadas() {
        return tareaRepository.findByEstado("eliminada"); // Solo tareas eliminadas
    }

    public void guardarTarea(Tarea tarea) {
        tareaRepository.save(tarea);
    }

    public void marcarComoCompletada(Integer id) {
        Tarea tarea = tareaRepository.findById(id).orElse(null);
        if (tarea != null) {
            tarea.setEstado("completada");
            tarea.setFechaFin(LocalDate.now()); // Guarda la fecha de finalización
            tareaRepository.save(tarea);
        }
    }

    public void eliminarTarea(Integer id) {
        Tarea tarea = tareaRepository.findById(id).orElse(null);
        if (tarea != null) {
            tarea.setEstado("eliminada"); // Cambio de estado
            tarea.setFechaEliminada(LocalDate.now()); // Guarda la fecha de eliminación
            tareaRepository.save(tarea);
        }
    }

    public void restaurarTarea(Integer id) {
        Tarea tarea = tareaRepository.findById(id).orElse(null);
        if (tarea != null) {
            tarea.setEstado("pendiente"); // Cambio de estado a pendiente
            tarea.setFechaEliminada(null); // Borra la fecha de eliminación
            tareaRepository.save(tarea);
        }
    }

    public void eliminarDefinitivamente(Integer id) {
        tareaRepository.deleteById(id); // Borra la tarea de la BD
    }

    public List<Tarea> obtenerTareasEstadoAndUsuario(Integer id, String estado) {
        return tareaRepository.findByLoginTarea_IdAndEstado(id,estado);
    }

}