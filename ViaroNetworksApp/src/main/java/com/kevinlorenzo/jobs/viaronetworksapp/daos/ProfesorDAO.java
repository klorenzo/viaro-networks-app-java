package com.kevinlorenzo.jobs.viaronetworksapp.daos;

import com.kevinlorenzo.jobs.viaronetworksapp.db.DBConfig;
import com.kevinlorenzo.jobs.viaronetworksapp.models.Profesor;

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
public class ProfesorDAO {

    private static final Logger LOGGER = Logger.getLogger(ProfesorDAO.class.getName());

    private static Connection conn = DBConfig.getInstance().getConnection();

    public Profesor getByID(int id) {
        Profesor profesor = null;
        try {

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Profesor AS p WHERE p.id = ?");
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                profesor = new Profesor();
                profesor.setId(rs.getInt("id"));
                profesor.setNombre(rs.getString("nombre"));
                profesor.setApellidos(rs.getString("apellidos"));
                profesor.setGenero(rs.getString("genero"));
            }

        } catch (SQLException sqle) {
            LOGGER.severe(sqle.getMessage());
        }
        return profesor;
    }

    public List<Profesor> getAll() {
        List<Profesor> profesores = new ArrayList<>();
        try {

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Profesor");

            ResultSet rs = ps.executeQuery();

            Profesor profesor;
            while (rs.next()) {
                profesor = new Profesor();
                profesor.setId(rs.getInt("id"));
                profesor.setNombre(rs.getString("nombre"));
                profesor.setApellidos(rs.getString("apellidos"));
                profesor.setGenero(rs.getString("genero"));
                profesores.add(profesor);
            }

        } catch (SQLException sqle) {
            LOGGER.severe(sqle.getMessage());
        }
        return profesores;
    }

    public boolean create(Profesor profesor) {
        try {

            PreparedStatement ps = conn.prepareStatement("INSERT INTO Profesor(nombre, apellidos,"
                    + " genero) VALUES(?,?,?)");

            ps.setString(1, profesor.getNombre());
            ps.setString(2, profesor.getApellidos());
            ps.setString(3, profesor.getGenero());

            ps.executeUpdate();

            return true;

        } catch (SQLException sqle) {
            LOGGER.severe(sqle.getMessage());
        }
        return false;
    }

    public boolean update(Profesor profesor) {
        try {

            PreparedStatement ps = conn.prepareStatement("UPDATE Profesor SET nombre = ?, apellidos = ?,"
                    + " genero = ? WHERE id = ?");

            ps.setString(1, profesor.getNombre());
            ps.setString(2, profesor.getApellidos());
            ps.setString(3, profesor.getGenero());
            ps.setInt(4, profesor.getId());
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

                PreparedStatement ps = conn.prepareStatement("DELETE FROM Profesor WHERE id = ?");

                ps.setInt(1, id);
                ps.executeUpdate();

                return true;

            }
        } catch (SQLException sqle) {
            LOGGER.severe(sqle.getMessage());
        }
        return false;
    }

}