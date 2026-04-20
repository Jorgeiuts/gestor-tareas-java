package org.jcastaneda.gestor.tareas.repository;

import java.util.List;

import org.jcastaneda.gestor.tareas.models.EstadoTarea;
import org.jcastaneda.gestor.tareas.models.Prioridad;
import org.jcastaneda.gestor.tareas.models.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {
    List<Tarea> findByEstado(EstadoTarea estado);
    List<Tarea> findByPrioridad(Prioridad prioridad);
    List<Tarea> findByTituloContainingIgnoreCase(String titulo);
}
