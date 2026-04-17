package org.jcastaneda.gestor.tareas.models;

public enum EstadoTarea {
    PENDIENTE("Pendiente"),
    EN_PROGRESO("En Progreso"),
    COMPLETADA("Completada"),
    CANCELADA("Cancelada");

    private final String valor;

    EstadoTarea(String valor){
        this.valor = valor;
    }

    public String getValor(){
        return valor;
    }
}
