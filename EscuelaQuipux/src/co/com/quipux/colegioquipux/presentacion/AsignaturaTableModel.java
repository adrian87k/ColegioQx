
package co.com.quipux.colegioquipux.presentacion;

import co.com.quipux.colegioquipux.dao.impl.AlumnoDAO;
import co.com.quipux.colegioquipux.dao.impl.AsignaturaDAO;
import co.com.quipux.colegioquipux.dao.impl.DAOException;
import co.com.quipux.colegioquipux.models.entity.Alumno;
import co.com.quipux.colegioquipux.models.entity.Asignatura;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class AsignaturaTableModel extends AbstractTableModel  {
    private AsignaturaDAO asignatura;
    private List<Asignatura> datos = new ArrayList<>();

public AsignaturaTableModel(AsignaturaDAO asignatura)  {
this.asignatura = asignatura;
}
public void updateModel() throws DAOException{
 datos = asignatura.obtenerTodos();
}
    @Override
    public String getColumnName(int column) {
        switch(column){
            case 0: return "ID";
            case 1: return "Apellidos";
            case 2: return "Nombre";
           
           default: return "[no]";
        }
    }



    @Override
    public int getRowCount() {
      return datos.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
       Asignatura preguntado = datos.get(rowIndex);
       switch (columnIndex){
           case 0: return preguntado.getId();
           case 1: return preguntado.getNombre();
           case 2: return preguntado.getIdProfesor();
           case 3: DateFormat df = DateFormat.getDateInstance();
         
           default: return"";
       }
    }
}
