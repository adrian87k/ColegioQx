/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.quipux.colegioquipux.dao.impl.mysql;

import co.com.quipux.colegioquipux.dao.impl.AlumnoDAO;
import co.com.quipux.colegioquipux.dao.impl.AsignaturaDAO;
import co.com.quipux.colegioquipux.dao.impl.DAOException;
import co.com.quipux.colegioquipux.dao.impl.DAOmanager;
import co.com.quipux.colegioquipux.dao.impl.MatriculaDAO;
import co.com.quipux.colegioquipux.dao.impl.ProfesorDAO;
import co.com.quipux.colegioquipux.models.entity.Alumno;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class MySQLDaoManager implements DAOmanager {
    private Connection con;
    private AlumnoDAO alumno = null;
    private ProfesorDAO profesor = null;
    private MatriculaDAO matricula = null;
    private AsignaturaDAO asignatura = null;
    public MySQLDaoManager(String host, String username, String password, String database) throws SQLException{
    con = DriverManager.getConnection("jdbc:mysql://"+host+"/"+database,username,password);
    
    }

    @Override
    public AlumnoDAO getAlumnoDAO() {
        if (alumno == null){
        alumno = new MySQLAlumnoDAO(con);
        }
        return alumno;
    }

    @Override
    public AsignaturaDAO getAsignaturaDAO() {
       if (asignatura == null){
        asignatura = new MySQLAsignaturaDAO(con);
        }
        return asignatura;
    }

    @Override
    public ProfesorDAO getProfesorDAO() {
         if (profesor == null){
        profesor = new MySQLProfesorDAO(con);
        }
        return profesor;
    }

    @Override
    public MatriculaDAO getMatriculaDAO() {
         if (matricula == null){
        matricula = new MySQLMatriculaDAO(con);
        }
        return matricula;
    }
    public static void main(String[] args) throws SQLException, DAOException {
        MySQLDaoManager man = new MySQLDaoManager("localhost", "root", "12345678", "escuela" );
        List<Alumno> alumno = man.getAlumnoDAO().obtenerTodos();
        System.out.print(alumno);
    }
    
}
