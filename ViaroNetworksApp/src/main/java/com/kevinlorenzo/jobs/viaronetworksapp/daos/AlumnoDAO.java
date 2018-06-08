package com.kevinlorenzo.jobs.viaronetworksapp.daos;

import com.kevinlorenzo.jobs.viaronetworksapp.db.DBConfig;
import com.kevinlorenzo.jobs.viaronetworksapp.models.Alumno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Kevin Lorenzo
 */
public class AlumnoDAO {

    private static final Logger LOGGER = Logger.getLogger(AlumnoDAO.class.getName());

    private static Connection conn = DBConfig.getInstance().getConnection();

    public Alumno getByID(int id) {
        Alumno alumno = null;
        try {

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Alumno AS a WHERE a.id = ?");
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                alumno = new Alumno();
                alumno.setId(rs.getInt("id"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setApellidos(rs.getString("apellidos"));
                alumno.setGenero(rs.getString("genero"));
                alumno.setFechaNacimiento(rs.getTimestamp("fechaNacimiento"));
            }

        } catch (SQLException sqle) {
            LOGGER.severe(sqle.getMessage());
        }
        return alumno;
    }

    public List<Alumno> getAll() {
        List<Alumno> alumnos = new ArrayList<>();
        try {

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Alumno");

            ResultSet rs = ps.executeQuery();

            Alumno alumno;
            while (rs.next()) {
                alumno = new Alumno();
                alumno.setId(rs.getInt("id"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setApellidos(rs.getString("apellidos"));
                alumno.setGenero(rs.getString("genero"));
                alumno.setFechaNacimiento(rs.getTimestamp("fechaNacimiento"));
                alumnos.add(alumno);
            }

        } catch (SQLException sqle) {
            LOGGER.severe(sqle.getMessage());
        }
        return alumnos;
    }

    public boolean create(Alumno alumno) {
        try {

            PreparedStatement ps = conn.prepareStatement("INSERT INTO Alumno(nombre, apellidos,"
                    + " genero, fechaNacimiento) VALUES(?,?,?,?)");

            ps.setString(1, alumno.getNombre());
            ps.setString(2, alumno.getApellidos());
            ps.setString(3, alumno.getGenero());
            ps.setTimestamp(4, new Timestamp(alumno.getFechaNacimiento().getTime()));

            ps.executeUpdate();

            return true;

        } catch (SQLException sqle) {
            LOGGER.severe(sqle.getMessage());
        }
        return false;
    }

    public boolean update(Alumno alumno) {
        try {

            PreparedStatement ps = conn.prepareStatement("UPDATE Alumno SET nombre = ?, apellidos = ?,"
                    + " genero = ?, fechaNacimiento = ? WHERE id = ?");

            ps.setString(1, alumno.getNombre());
            ps.setString(2, alumno.getApellidos());
            ps.setString(3, alumno.getGenero());
            ps.setTimestamp(4, new Timestamp(alumno.getFechaNacimiento().getTime()));
            ps.setInt(5, alumno.getId());
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

                PreparedStatement ps = conn.prepareStatement("DELETE FROM Alumno WHERE id = ?");

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