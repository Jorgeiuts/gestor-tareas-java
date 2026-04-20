package org.jcastaneda.gestor.tareas.services;

import java.util.List;
import java.util.Optional;

import org.jcastaneda.gestor.tareas.models.EstadoTarea;
import org.jcastaneda.gestor.tareas.models.Prioridad;
import org.jcastaneda.gestor.tareas.models.Tarea;
import org.jcastaneda.gestor.tareas.repository.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TareaService {

    @Autowired
    private TareaRepository tareaRepository;

    public List<Tarea> obtenerTodas(){
        return tareaRepository.findAll();
    }

    public Optional<Tarea> obtenerPorId(Long id){
        return tareaRepository.findById(id);
    }

    public Tarea crearTarea(Tarea tarea){
        if (tarea.getTitulo() == null || tarea.getTitulo().isBlank()){
            throw new IllegalArgumentException("El titulo no puede estar vacío");
        }
        return tareaRepository.save(tarea);
    }

    public Tarea actualizarTarea(Long id, Tarea tareaActualizada){
        Optional<Tarea> tareaExistente = tareaRepository.findById(id);

        if (tareaExistente.isEmpty()){
            throw new IllegalArgumentException("Tarea no encontrada con ID: " + id);
        }

        Tarea tarea = tareaExistente.get();

        if(tareaActualizada.getTitulo() != null && !tareaActualizada.getTitulo().isBlank()){
            tarea.setTitulo(tareaActualizada.getTitulo());
        }
        if(tareaActualizada.getDescription() != null){
            tarea.setDescription(tareaActualizada.getDescription());
        }
        if(tareaActualizada.getEstado() != null){
            tarea.setEstado(tareaActualizada.getEstado());
        }
        if(tareaActualizada.getFechaVencimiento() != null){
            tarea.setFechaVencimiento(tareaActualizada.getFechaVencimiento());
        }

        return tareaRepository.save(tarea);
    }

    public void eliminarTarea(Long id){
        if (!tareaRepository.existsById(id)){
            throw new IllegalArgumentException("Tarea no encontrada con ID: " + id);
        }
        tareaRepository.deleteById(id);
    }

    public List<Tarea> obtenerPorEstado(EstadoTarea estado){
        return tareaRepository.findByEstado(estado);
    }

    public List<Tarea> obtenerPorPrioridad(Prioridad prioridad){
        return tareaRepository.findByPrioridad(prioridad);
    }

    public List<Tarea> buscarPorTitulo(String titulo){
        return tareaRepository.findByTituloContainingIgnoreCase(titulo);
    }

    public Tarea completarTarea(Long id){
        Optional<Tarea> tareaOpt = tareaRepository.findById(id);

        if (tareaOpt.isEmpty()){
            throw new IllegalArgumentException("Tarea no encontrada con ID: " + id);
        }

        Tarea tarea = tareaOpt.get();
        tarea.setEstado(EstadoTarea.COMPLETADA);
        return tareaRepository.save(tarea);
    }

    public long contarTareas(){
        return tareaRepository.count();
    }

    public long contarCompletadas(){
        return tareaRepository.findByEstado(EstadoTarea.COMPLETADA).size();
    }
}
