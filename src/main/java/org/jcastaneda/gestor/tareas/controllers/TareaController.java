package org.jcastaneda.gestor.tareas.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.jcastaneda.gestor.tareas.models.EstadoTarea;
import org.jcastaneda.gestor.tareas.models.Prioridad;
import org.jcastaneda.gestor.tareas.models.Tarea;
import org.jcastaneda.gestor.tareas.services.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/tareas")
public class TareaController {

    @Autowired
    private TareaService tareaService;

    @GetMapping("/estadisticas/resumen")
    public ResponseEntity<Map<String, Object>> obtenerEstadisticas() {
        long total = tareaService.contarTareas();
        long completadas = tareaService.contarCompletadas();
        long pendientes = total - completadas;

        Map<String, Object> estadisticas = new HashMap<>();
        estadisticas.put("total", total);
        estadisticas.put("completadas", completadas);
        estadisticas.put("pendientes", pendientes);
        estadisticas.put("porcentajeCompletacion", total > 0 ? (completadas * 100.0) / total : 0);

        return ResponseEntity.ok(estadisticas);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Tarea>> obtenerPorEstado(@PathVariable String estado){
        try {
            EstadoTarea estadoEnum = EstadoTarea.valueOf(estado.toUpperCase());
            List<Tarea> tareas = tareaService.obtenerPorEstado(estadoEnum);
            return ResponseEntity.ok(tareas);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/prioridad/{prioridad}")
    public ResponseEntity<List<Tarea>> obtenerPorPrioridad(@PathVariable String prioridad){
        try {
            Prioridad prioridadEnum = Prioridad.valueOf(prioridad.toUpperCase());
            List<Tarea> tareas = tareaService.obtenerPorPrioridad(prioridadEnum);
            return ResponseEntity.ok(tareas);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarea> obtenerPorId(@PathVariable Long id){
        Optional<Tarea> tarea = tareaService.obtenerPorId(id);

        if(tarea.isPresent()){
            return ResponseEntity.ok(tarea.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PatchMapping("/{id}/completar")
    public ResponseEntity<Tarea> completarTarea(@PathVariable Long id){
        try {
            Tarea tarea = tareaService.completarTarea(id);
            return ResponseEntity.ok(tarea);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Tarea>> obtenerTareasConFiltro(@RequestParam(required = false) String titulo){
        if (titulo != null && !titulo.isBlank()){
            List<Tarea> tareas = tareaService.buscarPorTitulo(titulo);
            return ResponseEntity.ok(tareas);
        } else {
            List<Tarea> tareas = tareaService.obtenerTodas();
            return ResponseEntity.ok(tareas);
        }
    }

    @PostMapping
    public ResponseEntity<Tarea> crearTarea(@RequestBody Tarea tarea){
        try {
            Tarea nuevaTarea = tareaService.crearTarea(tarea);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaTarea);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarea> actualizarTarea(@PathVariable Long id, @RequestBody Tarea tareaActualizada){
        try {
            Tarea tarea = tareaService.actualizarTarea(id, tareaActualizada);
            return ResponseEntity.ok(tarea);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTarea(@PathVariable Long id){
        try {
            tareaService.eliminarTarea(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
