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

    public List<Tarea> obtenerPendientes() {
        return tareaRepository.findByFechaFinNullAndFechaEliminadaNull();
    }

    public List<Tarea> obtenerCompletadas() {
        return tareaRepository.findByFechaFinNotNull();
    }

    public List<Tarea> obtenerEliminadas() {
        return tareaRepository.findByFechaEliminadaNotNull();
    }

    public void guardarTarea(Tarea tarea) {
        tareaRepository.save(tarea);
    }

    public void marcarComoCompletada(Integer id) {
        Tarea tarea = tareaRepository.findById(id).orElseThrow();
        tarea.setFechaFin(LocalDate.now());
        tareaRepository.save(tarea);
    }

    public void eliminarTarea(Integer id) {
        Tarea tarea = tareaRepository.findById(id).orElseThrow();
        tarea.setFechaEliminada(LocalDate.now());
        tareaRepository.save(tarea);
    }

    public void restaurarTarea(Integer id) {
        Tarea tarea = tareaRepository.findById(id).orElseThrow();
        tarea.setFechaEliminada(null);
        tareaRepository.save(tarea);
    }
}