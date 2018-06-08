package com.kevinlorenzo.jobs.viaronetworksapp.controllers;

import com.kevinlorenzo.jobs.viaronetworksapp.daos.ProfesorDAO;
import com.kevinlorenzo.jobs.viaronetworksapp.models.Profesor;

import java.util.ArrayList;
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
@Named("MBProfesor")
@ViewScoped
public class MBProfesor extends Profesor {

    private static final Logger LOGGER = Logger.getLogger(MBProfesor.class.getName());

    private static final String ID_MSG_GLOBAL = "msg";
    private static final String SCREEN_NAME = "Profesor";

    private Profesor profesorToUpdate = new Profesor();

    private List<Profesor> profesores = new ArrayList<>();

    public Profesor getProfesorToUpdate() {
        return profesorToUpdate;
    }

    public void setProfesorToUpdate(Profesor profesorToUpdate) {
        this.profesorToUpdate = profesorToUpdate;
    }

    public List<Profesor> getProfesores() {
        return profesores;
    }

    public void setProfesores(List<Profesor> profesores) {
        this.profesores = profesores;
    }

    @PostConstruct
    public void PostConstruct() {
        this.loadData();
    }

    public void loadData() {
        ProfesorDAO dao = new ProfesorDAO();
        profesores = dao.getAll();
    }

    public void cargarProfesor(int id) {
        ProfesorDAO dao = new ProfesorDAO();
        this.profesorToUpdate = dao.getByID(id);
    }

    public void create() {
        Profesor nuevoProfesor = new Profesor();
        nuevoProfesor.setNombre(this.getNombre());
        nuevoProfesor.setApellidos(this.getApellidos());
        nuevoProfesor.setGenero(this.getGenero());

        ProfesorDAO dao = new ProfesorDAO();

        if (dao.create(nuevoProfesor)) {
            FacesContext.getCurrentInstance().addMessage(ID_MSG_GLOBAL,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, SCREEN_NAME, "Profesor creado."));
        } else {
            FacesContext.getCurrentInstance().addMessage(ID_MSG_GLOBAL,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, SCREEN_NAME, "Error al crear un nuevo Profesor."));
        }

        this.setNombre("");
        this.setApellidos("");
        this.setGenero("");

        this.loadData();

        PrimeFaces.current().ajax().update("dlgProfesorCreate");
    }

    public void delete(int id) {
        ProfesorDAO dao = new ProfesorDAO();

        if (dao.delete(id)) {
            FacesContext.getCurrentInstance().addMessage(ID_MSG_GLOBAL,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, SCREEN_NAME, "Profesor eliminado."));
        } else {
            FacesContext.getCurrentInstance().addMessage(ID_MSG_GLOBAL,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, SCREEN_NAME, "Error al eliminar el Profesor."));
        }

        this.loadData();
    }

    public void update() {
        ProfesorDAO dao = new ProfesorDAO();

        if (dao.update(this.profesorToUpdate)) {
            FacesContext.getCurrentInstance().addMessage(ID_MSG_GLOBAL,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, SCREEN_NAME, "Profesor modificado."));
        } else {
            FacesContext.getCurrentInstance().addMessage(ID_MSG_GLOBAL,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, SCREEN_NAME, "Error al modificar el Profesor."));
        }

        this.loadData();

        PrimeFaces.current().ajax().update("dlgProfesorUpdate");
    }

}