package com.kevinlorenzo.jobs.viaronetworksapp.models;

import java.io.Serializable;

/**
 *
 * @author Kevin Lorenzo
 */
public class Grado implements Serializable {
    
    private int id;
    private String nombre;
    private int profesorId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getProfesorId() {
        return profesorId;
    }

    public void setProfesorId(int profesorId) {
        this.profesorId = profesorId;
    }
    
}