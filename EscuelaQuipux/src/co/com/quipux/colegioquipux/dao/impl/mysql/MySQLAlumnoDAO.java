package co.com.quipux.colegioquipux.dao.impl.mysql;

import co.com.quipux.colegioquipux.dao.impl.AlumnoDAO;
import co.com.quipux.colegioquipux.dao.impl.DAOException;
import co.com.quipux.colegioquipux.models.entity.Alumno;
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
public class MySQLAlumnoDAO implements AlumnoDAO {

    final String INSERT = "INSERT INTO alumno(nombre, apellidos, fecha_nac) VALUES (?,?,?)";
    final String UPDATE = "UPDATE alumno SET nombre = ?, apellidos = ?, fecha_nac=? WHERE id = ?";
    final String DELETE = "DELETE FROM alumno WHERE id = ?";
    final String GETALL = "SELECT id, nombre, apellidos, fecha_nac FROM alumno";
    final String GETONE = "SELECT id, nombre, apellidos, fecha nac FROM alumno WHERE id = ?";

    private Connection con;

    public MySQLAlumnoDAO(Connection con) {
        this.con = con;
    }

    @Override
    public void insertar(Alumno a) throws DAOException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        try {
            stat = con.prepareStatement(INSERT);
            stat.setString(1, a.getNombre());
            stat.setString(2, a.getApellidos());
            stat.setDate(3, new Date(a.getFechaNacimiento().getTime()));
            if (stat.executeUpdate() == 0) {
                throw new DAOException("Error al guardar");
            }
            rs = stat.getGeneratedKeys();
            if (rs.next()) {
                a.setId(rs.getLong(1));
            } else {
                throw new DAOException("No se puede asignar un Id");
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
            if (stat != null) {
                try {
                    stat.close();
                } catch (SQLException ex) {
                    throw new DAOException("Error en SQL", ex);
                }
            }

        }
    }

    @Override
    public void modificar(Alumno a) throws DAOException {
        PreparedStatement stat = null;
        try {
            stat = con.prepareStatement(UPDATE);
            stat.setLong(1, a.getId());
            stat.setString(2, a.getNombre());
            stat.setString(3, a.getApellidos());
            stat.setDate(4, new Date(a.getFechaNacimiento().getTime()));
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
    }

    @Override
    public void eliminar(Alumno a) throws DAOException {
        PreparedStatement stat = null;
        try {
            stat = con.prepareStatement(DELETE);
            stat.setLong(1, a.getId());
            stat.setString(2, a.getNombre());
            stat.setString(3, a.getApellidos());
            stat.setDate(4, new Date(a.getFechaNacimiento().getTime()));
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
    }

    private Alumno convertir(ResultSet rs) throws SQLException {
        String nombre = rs.getString("nombre");
        String apellidos = rs.getString("apellidos");
        Date fechaNac = rs.getDate("fecha_nac");
        Alumno alumno = new Alumno(nombre, apellidos, fechaNac);
        alumno.setId(rs.getLong("id"));
        return alumno;
    }

    @Override
    public List<Alumno> obtenerTodos() throws DAOException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<Alumno> alumno = new ArrayList<>();
        try {
            stat = con.prepareStatement(GETALL);
            rs = stat.executeQuery();
            while (rs.next()) {
                alumno.add(convertir(rs));
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
        return alumno;
    }

    @Override
    public Alumno obtener(Long id) throws DAOException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        Alumno a = null;
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
            AlumnoDAO dao = new MySQLAlumnoDAO(con);

            List<Alumno> alumno = dao.obtenerTodos();
            for (Alumno a : alumno) {
                System.out.println(a.toString());
            }
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }
}
