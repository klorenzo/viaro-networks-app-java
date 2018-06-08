package com.kevinlorenzo.jobs.viaronetworksapp.daos;

import com.kevinlorenzo.jobs.viaronetworksapp.db.DBConfig;
import com.kevinlorenzo.jobs.viaronetworksapp.models.AlumnoGrado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Kevin Lorenzo
 */
public class AlumnoGradoDAO {

    private static final Logger LOGGER = Logger.getLogger(AlumnoGradoDAO.class.getName());

    private static Connection conn = DBConfig.getInstance().getConnection();

    public AlumnoGrado getByID(int id) {
        AlumnoGrado alumnoGrado = null;
        try {

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM AlumnoGrado AS ag WHERE ag.id = ?");
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                alumnoGrado = new AlumnoGrado();
                alumnoGrado.setId(rs.getInt("id"));
                alumnoGrado.setAlumnoId(rs.getInt("alumnoId"));
                alumnoGrado.setGradoId(rs.getInt("gradoId"));
                alumnoGrado.setSeccion(rs.getString("seccion"));
            }

        } catch (SQLException sqle) {
            LOGGER.severe(sqle.getMessage());
        }
        return alumnoGrado;
    }

    public List<AlumnoGrado> getAll() {
        List<AlumnoGrado> alumnosGrados = new ArrayList<>();
        try {

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM AlumnoGrado");

            ResultSet rs = ps.executeQuery();

            AlumnoGrado alumnoGrado;
            while (rs.next()) {
                alumnoGrado = new AlumnoGrado();
                alumnoGrado.setId(rs.getInt("id"));
                alumnoGrado.setAlumnoId(rs.getInt("alumnoId"));
                alumnoGrado.setGradoId(rs.getInt("gradoId"));
                alumnoGrado.setSeccion(rs.getString("seccion"));
                alumnosGrados.add(alumnoGrado);
            }

        } catch (SQLException sqle) {
            LOGGER.severe(sqle.getMessage());
        }
        return alumnosGrados;
    }

    public boolean create(AlumnoGrado alumnoGrado) {
        try {

            PreparedStatement ps = conn.prepareStatement("INSERT INTO AlumnoGrado(alumnoId, gradoId,"
                    + " seccion) VALUES(?,?,?)");

            ps.setInt(1, alumnoGrado.getAlumnoId());
            ps.setInt(2, alumnoGrado.getGradoId());
            ps.setString(3, alumnoGrado.getSeccion());

            ps.executeUpdate();

            return true;

        } catch (SQLException sqle) {
            LOGGER.severe(sqle.getMessage());
        }
        return false;
    }

    public boolean update(AlumnoGrado alumnoGrado) {
        try {

            PreparedStatement ps = conn.prepareStatement("UPDATE AlumnoGrado SET alumnoId = ?, gradoId = ?,"
                    + " seccion = ? WHERE id = ?");

            ps.setInt(1, alumnoGrado.getAlumnoId());
            ps.setInt(2, alumnoGrado.getGradoId());
            ps.setString(3, alumnoGrado.getSeccion());
            ps.setInt(4, alumnoGrado.getId());
            ps.executeUpdate();

            return true;

        } catch (SQLException sqle) {
            LOGGER.severe(sqle.getMessage());
        }
        return false;
    }

    public boolean delete(int id) {
        try {
            if (this.getByID(id) != null) {

                PreparedStatement ps = conn.prepareStatement("DELETE FROM AlumnoGrado WHERE id = ?");

                ps.setInt(1, id);
                ps.executeUpdate();

                return true;

            }
        } catch (SQLException sqle) {
            LOGGER.severe(sqle.getMessage());
        }
        return false;
    }
    
    public boolean exists(AlumnoGrado alumnoGrado) {
        try {

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM AlumnoGrado AS ag WHERE ag.alumnoId = ? AND ag.gradoId = ?");
            ps.setInt(1, alumnoGrado.getAlumnoId());
            ps.setInt(2, alumnoGrado.getGradoId());
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                return true;
            }

        } catch (SQLException sqle) {
            LOGGER.severe(sqle.getMessage());
        }
        return false;
    }

}