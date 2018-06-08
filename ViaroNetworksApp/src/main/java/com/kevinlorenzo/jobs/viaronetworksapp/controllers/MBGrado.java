package com.kevinlorenzo.jobs.viaronetworksapp.controllers;

import com.kevinlorenzo.jobs.viaronetworksapp.daos.GradoDAO;
import com.kevinlorenzo.jobs.viaronetworksapp.daos.ProfesorDAO;

import com.kevinlorenzo.jobs.viaronetworksapp.models.Grado;
import com.kevinlorenzo.jobs.viaronetworksapp.models.Profesor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@Named("MBGrado")
@ViewScoped
public class MBGrado extends Grado {

    private static final Logger LOGGER = Logger.getLogger(MBGrado.class.getName());

    private static final String ID_MSG_GLOBAL = "msg";
    private static final String SCREEN_NAME = "Grado";

    private Grado gradoToUpdate = new Grado();

    private List<Grado> grados = new ArrayList<>();

    private Map<String, String> profesores = new HashMap<>();

    public Grado getGradoToUpdate() {
        return gradoToUpdate;
    }

    public void setGradoToUpdate(Grado gradoToUpdate) {
        this.gradoToUpdate = gradoToUpdate;
    }

    public List<Grado> getGrados() {
        return grados;
    }

    public void setGrados(List<Grado> grados) {
        this.grados = grados;
    }

    public Map<String, String> getProfesores() {
        return profesores;
    }

    public void setProfesores(Map<String, String> profesores) {
        this.profesores = profesores;
    }

    @PostConstruct
    public void PostConstruct() {
        this.cargarProfesores();
        this.loadData();
    }

    public void loadData() {
        GradoDAO dao = new GradoDAO();
        grados = dao.getAll();
    }

    public void cargarGrado(int id) {
        GradoDAO dao = new GradoDAO();
        this.gradoToUpdate = dao.getByID(id);
    }

    public void create() {
        Grado nuevoGrado = new Grado();
        nuevoGrado.setNombre(this.getNombre());
        nuevoGrado.setProfesorId(this.getProfesorId());

        GradoDAO dao = new GradoDAO();

        if (dao.exists(nuevoGrado)) {
            FacesContext.getCurrentInstance().addMessage(ID_MSG_GLOBAL,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, SCREEN_NAME, "Grado no creado porque ya existe en la base de datos."));
        } else {
            if (dao.create(nuevoGrado)) {
                FacesContext.getCurrentInstance().addMessage(ID_MSG_GLOBAL,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, SCREEN_NAME, "Grado creado."));
            } else {
                FacesContext.getCurrentInstance().addMessage(ID_MSG_GLOBAL,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, SCREEN_NAME, "Error al crear un nuevo Grado."));
            }

            this.setNombre("");
            this.setProfesorId(0);

            this.loadData();

            PrimeFaces.current().ajax().update("dlgGradoCreate");
        }
    }

    public void delete(int id) {
        GradoDAO dao = new GradoDAO();

        if (dao.delete(id)) {
            FacesContext.getCurrentInstance().addMessage(ID_MSG_GLOBAL,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, SCREEN_NAME, "Grado eliminado."));
        } else {
            FacesContext.getCurrentInstance().addMessage(ID_MSG_GLOBAL,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, SCREEN_NAME, "Error al eliminar el Grado."));
        }

        this.loadData();
    }

    public void update() {
        GradoDAO dao = new GradoDAO();

        if (dao.exists(this.gradoToUpdate)) {
            FacesContext.getCurrentInstance().addMessage(ID_MSG_GLOBAL,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, SCREEN_NAME, "Grado no actualizado porque ya existe en la base de datos."));
        } else {

            if (dao.update(this.gradoToUpdate)) {
                FacesContext.getCurrentInstance().addMessage(ID_MSG_GLOBAL,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, SCREEN_NAME, "Grado modificado."));
            } else {
                FacesContext.getCurrentInstance().addMessage(ID_MSG_GLOBAL,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, SCREEN_NAME, "Error al modificar el Grado."));
            }

            this.loadData();

            PrimeFaces.current().ajax().update("dlgGradoUpdate");
        }
    }

    public String getNombreProfesor(int id) {
        String nombreCompleto = "";

        ProfesorDAO dao = new ProfesorDAO();

        Profesor profesor = dao.getByID(id);

        if (profesor != null) {
            nombreCompleto = profesor.getNombre() + " " + profesor.getApellidos();
        }

        return nombreCompleto;
    }

    public void cargarProfesores() {
        this.profesores = new HashMap<>();

        ProfesorDAO dao = new ProfesorDAO();

        List<Profesor> list = dao.getAll();

        for (Profesor profesor : list) {
            this.profesores.put(profesor.getNombre() + " " + profesor.getApellidos(), String.valueOf(profesor.getId()));
        }
    }

}