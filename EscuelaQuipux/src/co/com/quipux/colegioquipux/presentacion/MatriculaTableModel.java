
package co.com.quipux.colegioquipux.presentacion;

import co.com.quipux.colegioquipux.dao.impl.AlumnoDAO;
import co.com.quipux.colegioquipux.dao.impl.DAOException;
import co.com.quipux.colegioquipux.models.entity.Alumno;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class MatriculaTableModel extends AbstractTableModel  {
    private AlumnoDAO alumno;
    private List<Alumno> datos = new ArrayList<>();

public MatriculaTableModel(AlumnoDAO alumno)  {
this.alumno = alumno;
}
public void updateModel() throws DAOException{
 datos = alumno.obtenerTodos();
}
    @Override
    public String getColumnName(int column) {
        switch(column){
            case 0: return "ID";
            case 1: return "Apellidos";
            case 2: return "Nombre";
            case 3: return "Fecha de nacimiento";
           default: return "[no]";
        }
    }



    @Override
    public int getRowCount() {
      return datos.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
       Alumno preguntado = datos.get(rowIndex);
       switch (columnIndex){
           case 0: return preguntado.getId();
           case 1: return preguntado.getApellidos();
           case 2: return preguntado.getNombre();
           case 3: DateFormat df = DateFormat.getDateInstance();
           return df.format(preguntado.getFechaNacimiento());
           default: return"";
       }
    }
}
