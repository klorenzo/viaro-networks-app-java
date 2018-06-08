package com.kevinlorenzo.jobs.viaronetworksapp.controllers;

import com.kevinlorenzo.jobs.viaronetworksapp.daos.AlumnoDAO;
import com.kevinlorenzo.jobs.viaronetworksapp.daos.AlumnoGradoDAO;
import com.kevinlorenzo.jobs.viaronetworksapp.daos.GradoDAO;
import com.kevinlorenzo.jobs.viaronetworksapp.daos.ProfesorDAO;

import com.kevinlorenzo.jobs.viaronetworksapp.models.Alumno;
import com.kevinlorenzo.jobs.viaronetworksapp.models.AlumnoGrado;
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
@Named("MBAlumnoGrado")
@ViewScoped
public class MBAlumnoGrado extends AlumnoGrado {

    private static final Logger LOGGER = Logger.getLogger(MBAlumnoGrado.class.getName());

    private static final String ID_MSG_GLOBAL = "msg";
    private static final String SCREEN_NAME = "AlumnoGrado";

    private AlumnoGrado alumnoGradoToUpdate = new AlumnoGrado();

    private List<AlumnoGrado> alumnosGrados = new ArrayList<>();

    private Map<String, String> alumnos = new HashMap<>();
    private Map<String, String> grados = new HashMap<>();

    public AlumnoGrado getAlumnoGradoToUpdate() {
        return alumnoGradoToUpdate;
    }

    public void setAlumnoGradoToUpdate(AlumnoGrado alumnoGradoToUpdate) {
        this.alumnoGradoToUpdate = alumnoGradoToUpdate;
    }

    public List<AlumnoGrado> getAlumnosGrados() {
        return alumnosGrados;
    }

    public void setAlumnosGrados(List<AlumnoGrado> alumnosGrados) {
        this.alumnosGrados = alumnosGrados;
    }

    public Map<String, String> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(Map<String, String> alumnos) {
        this.alumnos = alumnos;
    }

    public Map<String, String> getGrados() {
        return grados;
    }

    public void setGrados(Map<String, String> grados) {
        this.grados = grados;
    }

    @PostConstruct
    public void PostConstruct() {
        this.loadData();
        this.cargarAlumnos();
        this.cargarGrados();
    }

    public void loadData() {
        AlumnoGradoDAO dao = new AlumnoGradoDAO();
        alumnosGrados = dao.getAll();
    }

    public void cargarAlumnoGrado(int id) {
        AlumnoGradoDAO dao = new AlumnoGradoDAO();
        this.alumnoGradoToUpdate = dao.getByID(id);
    }

    public void create() {
        AlumnoGradoDAO dao = new AlumnoGradoDAO();

        AlumnoGrado nuevoAlumnoGrado = new AlumnoGrado();
        nuevoAlumnoGrado.setAlumnoId(this.getAlumnoId());
        nuevoAlumnoGrado.setGradoId(this.getGradoId());
        nuevoAlumnoGrado.setSeccion(this.getSeccion());

        if (dao.exists(nuevoAlumnoGrado)) {
            FacesContext.getCurrentInstance().addMessage(ID_MSG_GLOBAL,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, SCREEN_NAME, "AlumnoGrado no creado porque ya existe en la base de datos."));
        } else {
            if (dao.create(nuevoAlumnoGrado)) {
                FacesContext.getCurrentInstance().addMessage(ID_MSG_GLOBAL,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, SCREEN_NAME, "AlumnoGrado creado."));
            } else {
                FacesContext.getCurrentInstance().addMessage(ID_MSG_GLOBAL,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, SCREEN_NAME, "Error al crear un nuevo AlumnoGrado."));
            }

            this.setAlumnoId(0);
            this.setGradoId(0);
            this.setSeccion("");

            this.loadData();

            PrimeFaces.current().ajax().update("dlgAlumnoGradoCreate");
        }
    }

    public void delete(int id) {
        AlumnoGradoDAO dao = new AlumnoGradoDAO();

        if (dao.delete(id)) {
            FacesContext.getCurrentInstance().addMessage(ID_MSG_GLOBAL,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, SCREEN_NAME, "AlumnoGrado eliminado."));
        } else {
            FacesContext.getCurrentInstance().addMessage(ID_MSG_GLOBAL,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, SCREEN_NAME, "Error al eliminar el AlumnoGrado."));
        }

        this.loadData();
    }

    public void update() {
        AlumnoGradoDAO dao = new AlumnoGradoDAO();

        if (dao.exists(this.alumnoGradoToUpdate)) {
            FacesContext.getCurrentInstance().addMessage(ID_MSG_GLOBAL,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, SCREEN_NAME, "AlumnoGrado no actualizado porque ya existe en la base de datos."));
        } else {
            if (dao.update(this.alumnoGradoToUpdate)) {
                FacesContext.getCurrentInstance().addMessage(ID_MSG_GLOBAL,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, SCREEN_NAME, "AlumnoGrado modificado."));
            } else {
                FacesContext.getCurrentInstance().addMessage(ID_MSG_GLOBAL,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, SCREEN_NAME, "Error al modificar el AlumnoGrado."));
            }

            this.loadData();

            PrimeFaces.current().ajax().update("dlgAlumnoGradoUpdate");
        }
    }

    public void cargarAlumnos() {
        this.alumnos = new HashMap<>();

        AlumnoDAO dao = new AlumnoDAO();

        List<Alumno> list = dao.getAll();

        for (Alumno alumno : list) {
            this.alumnos.put(alumno.getNombre() + " " + alumno.getApellidos(), String.valueOf(alumno.getId()));
        }
    }

    public void cargarGrados() {
        this.grados = new HashMap<>();

        GradoDAO dao = new GradoDAO();
        ProfesorDAO daoProfesor = new ProfesorDAO();

        List<Grado> list = dao.getAll();

        for (Grado grado : list) {
            Profesor profesor = daoProfesor.getByID(grado.getProfesorId());
            if (profesor != null) {
                this.grados.put(grado.getNombre() + " - " + profesor.getNombre() + " " + profesor.getApellidos(), String.valueOf(grado.getId()));
            }
        }
    }

    public String getNombreAlumno(int id) {
        String nombreCompleto = "";

        AlumnoDAO dao = new AlumnoDAO();

        Alumno alumno = dao.getByID(id);

        if (alumno != null) {
            nombreCompleto = alumno.getNombre() + " " + alumno.getApellidos();
        }

        return nombreCompleto;
    }

    public String getNombreGrado(int id) {
        String nombreCompleto = "";

        GradoDAO dao = new GradoDAO();

        Grado grado = dao.getByID(id);

        if (grado != null) {
            nombreCompleto = grado.getNombre();
        }

        return nombreCompleto;
    }

}