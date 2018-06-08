package com.kevinlorenzo.jobs.viaronetworksapp.models;

import java.io.Serializable;

/**
 *
 * @author Kevin Lorenzo
 */
public class AlumnoGrado implements Serializable {

    private int id;
    private int alumnoId;
    private int gradoId;
    private String seccion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(int alumnoId) {
        this.alumnoId = alumnoId;
    }

    public int getGradoId() {
        return gradoId;
    }

    public void setGradoId(int gradoId) {
        this.gradoId = gradoId;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

}