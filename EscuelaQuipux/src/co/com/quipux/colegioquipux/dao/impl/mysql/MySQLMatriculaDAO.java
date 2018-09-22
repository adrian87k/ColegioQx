/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.quipux.colegioquipux.dao.impl.mysql;

import co.com.quipux.colegioquipux.dao.impl.AlumnoDAO;
import co.com.quipux.colegioquipux.dao.impl.DAOException;
import co.com.quipux.colegioquipux.dao.impl.MatriculaDAO;
import co.com.quipux.colegioquipux.models.entity.Matricula;
import java.sql.Connection;
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
public class MySQLMatriculaDAO implements MatriculaDAO {
 final String INSERT = "INSERT INTO matricula(id,alumno, asignatura) VALUES (?,?,?)";
    final String UPDATE = "UPDATE matricula SET alumno = ?,id = ? WHERE asignatura = ?";
    final String DELETE = "DELETE FROM matricula WHERE id = ?";
    final String GETALL = "SELECT id,alumno, asignatura FROM matricula";
    final String GETONE = "SELECT id,alumno, asignatura FROM matricula WHERE id = ?";
     private Connection  con;
    public MySQLMatriculaDAO(Connection con){
        this.con = con;
    }

    @Override
    public void insertar(Matricula a) throws DAOException {
         PreparedStatement stat = null;
        try{
            stat = con.prepareStatement(INSERT);
            stat.setString(1, a.getAlumno());
            stat.setLong(2, a.getAsignatura());
            
            
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
    public void modificar(Matricula a) throws DAOException {
          PreparedStatement stat = null;
        try{
            stat = con.prepareStatement(UPDATE);
            stat.setString(1, a.getAlumno());
            stat.setLong(2, a.getAsignatura());
            
            
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
    public void eliminar(Matricula a) throws DAOException {
        PreparedStatement stat = null;
        try{
            stat = con.prepareStatement(DELETE);
            stat.setString(1, a.getAlumno());
            stat.setLong(2, a.getAsignatura());
            
            
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
private Matricula convertir(ResultSet rs) throws SQLException {
        String alumno = rs.getString("alumno");
        Long asignatura = rs.getLong("asignatura");
        
        Matricula matricula = new Matricula(alumno, asignatura);
        matricula.setId(rs.getLong("id"));
        return matricula;
    }
    @Override
    public List<Matricula> obtenerTodos() throws DAOException {
         PreparedStatement stat = null;
        ResultSet rs = null;
        List<Matricula> matricula = new ArrayList<>();
        try {
            stat = con.prepareStatement(GETALL);
            rs = stat.executeQuery();
            while (rs.next()) {
                matricula.add(convertir(rs));
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
        return matricula;
    
    }

    @Override
    public Matricula obtener(Long id) throws DAOException {
        
        PreparedStatement stat = null;
        ResultSet rs = null;
        Matricula a = null;
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
            MatriculaDAO dao = new MySQLMatriculaDAO(con);

            List<Matricula> matricula = dao.obtenerTodos();
            for (Matricula a : matricula) {
                System.out.println(a.toString());
            }
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }
}
    
   

