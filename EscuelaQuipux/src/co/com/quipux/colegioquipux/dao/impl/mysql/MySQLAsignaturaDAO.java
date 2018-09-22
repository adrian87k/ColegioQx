/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.quipux.colegioquipux.dao.impl.mysql;

import co.com.quipux.colegioquipux.dao.impl.AlumnoDAO;
import co.com.quipux.colegioquipux.dao.impl.AsignaturaDAO;
import co.com.quipux.colegioquipux.dao.impl.DAOException;
import co.com.quipux.colegioquipux.models.entity.Alumno;
import co.com.quipux.colegioquipux.models.entity.Asignatura;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hp
 */
public class MySQLAsignaturaDAO implements AsignaturaDAO{
    final String INSERT = "INSERT INTO asignatura(id, nombre,idprofesor) VALUES (?,?,?)";
    final String UPDATE = "UPDATE id SET nombre = ? WHERE idprofesor = ?";
    final String DELETE = "DELETE FROM alumno WHERE id = ?";
    final String GETALL = "SELECT id, nombre, idprofesor FROM asignatura";
    final String GETONE = "SELECT id, nombre,idprofesor FROM asignatura WHERE id = ?";
    
    private Connection  con;
    public MySQLAsignaturaDAO(Connection con){
        this.con = con;
    }

    @Override
    public void insertar(Asignatura a) throws DAOException {
        PreparedStatement stat = null;
        try{
            stat = con.prepareStatement(INSERT);
            stat.setLong(1, a.getId());
            stat.setString(2, a.getNombre());
            stat.setLong(3, a.getIdProfesor());
            if(stat.executeUpdate()==0){
                throw new DAOException("Error al guardar");
            }
        }catch (SQLException ex){
            throw new DAOException("Error en SQL",ex);
        }
            
        finally{
            if(stat != null){
                try{
                    stat.close();
                    }catch(SQLException ex){
                    throw new DAOException("Error en SQL", ex);
                    }
            }
            
        }
    }

    @Override
    public void modificar(Asignatura a) throws DAOException {
       PreparedStatement stat = null;
        try{
            stat = con.prepareStatement(UPDATE);
            stat.setLong(1, a.getId());
            stat.setString(2, a.getNombre());
            stat.setLong(3, a.getIdProfesor());
            
            if(stat.executeUpdate()==0){
                throw new DAOException("Error al guardar");
            }
        }catch (SQLException ex){
            throw new DAOException("Error en SQL",ex);
        }
            
        finally{
            if(stat != null){
                try{
                    stat.close();
                    }catch(SQLException ex){
                    throw new DAOException("Error en SQL", ex);
                    }
            }
            
        }
    }

    @Override
    public void eliminar(Asignatura a) throws DAOException {
        PreparedStatement stat = null;
        try{
            stat = con.prepareStatement(DELETE);
            stat.setLong(1, a.getId());
            if(stat.executeUpdate()==0){
                throw new DAOException("Error al borrar");
            }
        }catch (SQLException ex){
            throw new DAOException("Error en SQL",ex);
        }
            
        finally{
            if(stat != null){
                try{
                    stat.close();
                    }catch(SQLException ex){
                    throw new DAOException("Error en SQL", ex);
                    }
            }
            
        }
    }
     private Asignatura convertir(ResultSet rs) throws SQLException {
        String nombre = rs.getString("nombre");
        Long idProfesor = rs.getLong("idProfesor");
        Asignatura asignatura = new Asignatura(nombre, idProfesor);
        asignatura.setId(rs.getLong("id"));
        return asignatura;
    }
    

    @Override
    public List<Asignatura> obtenerTodos() throws DAOException {
       PreparedStatement stat = null;
        ResultSet rs = null;
        List<Asignatura> asignatura = new ArrayList<>();
        try {
            stat = con.prepareStatement(GETALL);
            rs = stat.executeQuery();
            while (rs.next()) {
                asignatura.add(convertir(rs));
            }
        } catch (SQLException ex) {
            throw new DAOException("error en SQL", ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    new DAOException("Error en SQL", ex);
                }

            }
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException ex) {
                    new DAOException("Error en SQL", ex);
                }
            }
        }
        return asignatura;
    }

    @Override
    public Asignatura obtener(Long id) throws DAOException {
         PreparedStatement stat = null;
        ResultSet rs = null;
        Asignatura a = null;
        try {
            stat = con.prepareStatement(GETONE);
            stat.setLong(1, id);
            rs = stat.executeQuery();
            if (rs.next()) {
                a = convertir(rs);
            } else {
                throw new DAOException("No se ha encontrado ese registro");
            }
        } catch (SQLException ex) {
            throw new DAOException("error en SQL", ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    new DAOException("Error en SQL", ex);
                }

            }
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException ex) {
                    new DAOException("Error en SQL", ex);
                }
            }
        }
        return a;
    }

    public static void main(String[] args) throws SQLException, DAOException {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/escuela", "root", "12345678");
            AsignaturaDAO dao = new MySQLAsignaturaDAO(con);

            List<Asignatura> asignatura = dao.obtenerTodos();
            for (Asignatura a : asignatura) {
                System.out.println(a.toString());
            }
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }
}
    
