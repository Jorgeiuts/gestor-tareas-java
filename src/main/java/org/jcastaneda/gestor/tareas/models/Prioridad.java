package org.jcastaneda.gestor.tareas.models;

public enum Prioridad {
    BAJA("Baja"),
    MEDIA("Media"),
    ALTA("Alta"),
    URGENTE("Urgente");

    private final String valor;

    Prioridad(String valor){
        this.valor = valor;
    }

    public String getValor(){
        return valor;
    }
}
