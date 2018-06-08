package com.kevinlorenzo.jobs.viaronetworksapp.controllers;

import com.kevinlorenzo.jobs.viaronetworksapp.daos.AlumnoDAO;
import com.kevinlorenzo.jobs.viaronetworksapp.models.Alumno;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

/**
 *
 * @author Kevin Lorenzo
 */
@Named("MBAlumno")
@ViewScoped
public class MBAlumno extends Alumno {

    private static final Logger LOGGER = Logger.getLogger(MBAlumno.class.getName());

    private static final String ID_MSG_GLOBAL = "msg";
    private static final String SCREEN_NAME = "Alumno";

    private Alumno alumnoToUpdate = new Alumno();

    private List<Alumno> alumnos = new ArrayList<>();

    public Alumno getAlumnoToUpdate() {
        return alumnoToUpdate;
    }

    public void setAlumnoToUpdate(Alumno alumnoToUpdate) {
        this.alumnoToUpdate = alumnoToUpdate;
    }

    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }

    @PostConstruct
    public void PostConstruct() {
        this.loadData();
    }

    public void loadData() {
        AlumnoDAO dao = new AlumnoDAO();
        alumnos = dao.getAll();
    }

    public void cargarAlumno(int id) {
        AlumnoDAO dao = new AlumnoDAO();
        this.alumnoToUpdate = dao.getByID(id);
    }

    public void create() {
        Alumno nuevoAlumno = new Alumno();
        nuevoAlumno.setNombre(this.getNombre());
        nuevoAlumno.setApellidos(this.getApellidos());
        nuevoAlumno.setGenero(this.getGenero());
        nuevoAlumno.setFechaNacimiento(this.getFechaNacimiento());

        AlumnoDAO dao = new AlumnoDAO();

        if (dao.create(nuevoAlumno)) {
            FacesContext.getCurrentInstance().addMessage(ID_MSG_GLOBAL,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, SCREEN_NAME, "Alumno creado."));
        } else {
            FacesContext.getCurrentInstance().addMessage(ID_MSG_GLOBAL,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, SCREEN_NAME, "Error al crear un nuevo Alumno."));
        }

        this.setNombre("");
        this.setApellidos("");
        this.setGenero("");
        this.setFechaNacimiento(new Date());

        this.loadData();

        PrimeFaces.current().ajax().update("dlgAlumnoCreate");
    }

    public void delete(int id) {
        AlumnoDAO dao = new AlumnoDAO();

        if (dao.delete(id)) {
            FacesContext.getCurrentInstance().addMessage(ID_MSG_GLOBAL,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, SCREEN_NAME, "Alumno eliminado."));
        } else {
            FacesContext.getCurrentInstance().addMessage(ID_MSG_GLOBAL,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, SCREEN_NAME, "Error al eliminar el Alumno."));
        }

        this.loadData();
    }

    public void update() {
        AlumnoDAO dao = new AlumnoDAO();

        if (dao.update(this.alumnoToUpdate)) {
            FacesContext.getCurrentInstance().addMessage(ID_MSG_GLOBAL,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, SCREEN_NAME, "Alumno modificado."));
        } else {
            FacesContext.getCurrentInstance().addMessage(ID_MSG_GLOBAL,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, SCREEN_NAME, "Error al modificar el Alumno."));
        }

        this.loadData();

        PrimeFaces.current().ajax().update("dlgAlumnoUpdate");
    }

}