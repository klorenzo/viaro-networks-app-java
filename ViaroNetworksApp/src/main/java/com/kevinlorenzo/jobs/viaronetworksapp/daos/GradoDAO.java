package com.kevinlorenzo.jobs.viaronetworksapp.daos;

import com.kevinlorenzo.jobs.viaronetworksapp.db.DBConfig;
import com.kevinlorenzo.jobs.viaronetworksapp.models.Grado;

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
public class GradoDAO {

    private static final Logger LOGGER = Logger.getLogger(GradoDAO.class.getName());

    private static Connection conn = DBConfig.getInstance().getConnection();

    public Grado getByID(int id) {
        Grado grado = null;
        try {

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Grado AS g WHERE g.id = ?");
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                grado = new Grado();
                grado.setId(rs.getInt("id"));
                grado.setNombre(rs.getString("nombre"));
                grado.setProfesorId(rs.getInt("profesorId"));
            }

        } catch (SQLException sqle) {
            LOGGER.severe(sqle.getMessage());
        }
        return grado;
    }

    public List<Grado> getAll() {
        List<Grado> grados = new ArrayList<>();
        try {

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Grado");

            ResultSet rs = ps.executeQuery();

            Grado grado;
            while (rs.next()) {
                grado = new Grado();
                grado.setId(rs.getInt("id"));
                grado.setNombre(rs.getString("nombre"));
                grado.setProfesorId(rs.getInt("profesorId"));
                grados.add(grado);
            }

        } catch (SQLException sqle) {
            LOGGER.severe(sqle.getMessage());
        }
        return grados;
    }

    public boolean create(Grado grado) {
        try {

            PreparedStatement ps = conn.prepareStatement("INSERT INTO Grado(nombre, profesorId) VALUES(?,?)");

            ps.setString(1, grado.getNombre());
            ps.setInt(2, grado.getProfesorId());

            ps.executeUpdate();

            return true;

        } catch (SQLException sqle) {
            LOGGER.severe(sqle.getMessage());
        }
        return false;
    }

    public boolean update(Grado grado) {
        try {

            PreparedStatement ps = conn.prepareStatement("UPDATE Grado SET nombre = ?, profesorId = ? WHERE id = ?");

            ps.setString(1, grado.getNombre());
            ps.setInt(2, grado.getProfesorId());
            ps.setInt(3, grado.getId());
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

                PreparedStatement ps = conn.prepareStatement("DELETE FROM Grado WHERE id = ?");

                ps.setInt(1, id);
                ps.executeUpdate();

                return true;

            }
        } catch (SQLException sqle) {
            LOGGER.severe(sqle.getMessage());
        }
        return false;
    }
    
    public boolean exists(Grado grado) {
        try {

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Grado AS g WHERE g.nombre = ? AND g.profesorId = ?");
            ps.setString(1, grado.getNombre());
            ps.setInt(2, grado.getProfesorId());

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