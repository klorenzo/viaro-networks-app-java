package com.kevinlorenzo.jobs.viaronetworksapp.models;

import java.io.Serializable;

import java.util.Date;

/**
 *
 * @author Kevin Lorenzo
 */
public class Alumno implements Serializable {

    private int id;
    private String nombre;
    private String apellidos;
    private String genero;
    private Date fechaNacimiento = new Date();

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

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

}