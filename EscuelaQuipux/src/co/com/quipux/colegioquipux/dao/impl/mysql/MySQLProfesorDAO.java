/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.quipux.colegioquipux.dao.impl.mysql;

import co.com.quipux.colegioquipux.dao.impl.AlumnoDAO;
import co.com.quipux.colegioquipux.dao.impl.DAOException;
import co.com.quipux.colegioquipux.dao.impl.ProfesorDAO;
import co.com.quipux.colegioquipux.models.entity.Alumno;
import co.com.quipux.colegioquipux.models.entity.Profesor;
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
public class MySQLProfesorDAO implements ProfesorDAO{
final String INSERT = "INSERT Profesor(id, nombre, apellidos) VALUES (?,?,?)";
    final String UPDATE = "UPDATE profesor SET nombre = ?, apellidos = ? WHERE id = ?";
    final String DELETE = "DELETE FROM profesor WHERE id = ?";
    final String GETALL = "SELECT id,nombre, apellidos FROM profesor";
    final String GETONE = "SELECT id, nombre,apellido FROM profesor WHERE id = ?";
     private Connection  con;
    public MySQLProfesorDAO(Connection con){
        this.con = con;}
    @Override
    public void insertar(Profesor a) throws DAOException {
     
        PreparedStatement stat = null;
        try{
            stat = con.prepareStatement(INSERT);
            stat.setLong(1, a.getId());
            stat.setString(2, a.getNombre());
            stat.setString(3, a.getApellidos());
            
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
    public void modificar(Profesor a) throws DAOException {
        PreparedStatement stat = null;
        try{
            stat = con.prepareStatement(UPDATE);
            stat.setLong(1, a.getId());
            stat.setString(2, a.getNombre());
            stat.setString(3, a.getApellidos());
            
            if(stat.executeUpdate()==0){
                throw new DAOException("Error al subir");
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
    public void eliminar(Profesor a) throws DAOException {
       PreparedStatement stat = null;
        try {
            stat = con.prepareStatement(DELETE);
            stat.setLong(1, a.getId());
            stat.setString(2, a.getNombre());
            stat.setString(3, a.getApellidos());
            
            if (stat.executeUpdate() == 0) {
                throw new DAOException("Error al guardar");
            }
        } catch (SQLException ex) {
            throw new DAOException("Error en SQL", ex);
        } finally {
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException ex) {
                    throw new DAOException("Error en SQL", ex);
                }
            }

        }
    }private Profesor convertir(ResultSet rs) throws SQLException {
        String nombre = rs.getString("nombre");
        String apellidos = rs.getString("apellidos");
       
        
        Profesor profesor = new Profesor(nombre, apellidos);
        profesor.setId(rs.getLong("id"));
        return profesor;
    }

    @Override
    public List<Profesor> obtenerTodos() throws DAOException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<Profesor> profesor = new ArrayList<>();
        try {
            stat = con.prepareStatement(GETALL);
            rs = stat.executeQuery();
            while (rs.next()) {
                profesor.add(convertir(rs));
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
        return profesor;
    
    }

    @Override
    public Profesor obtener(Long id) throws DAOException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        Profesor a = null;
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
    } public static void main(String[] args) throws SQLException, DAOException {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/escuela", "root", "12345678");
            ProfesorDAO dao = new MySQLProfesorDAO(con);

            List<Profesor> profesor = dao.obtenerTodos();
            for (Profesor a : profesor) {
                System.out.println(a.toString());
            }
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }
}
    
   
    

