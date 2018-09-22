
package co.com.quipux.colegioquipux.presentacion;

import co.com.quipux.colegioquipux.dao.impl.AlumnoDAO;
import co.com.quipux.colegioquipux.dao.impl.DAOException;
import co.com.quipux.colegioquipux.dao.impl.ProfesorDAO;
import co.com.quipux.colegioquipux.models.entity.Alumno;
import co.com.quipux.colegioquipux.models.entity.Profesor;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ProfesorTableModel extends AbstractTableModel  {
    private ProfesorDAO profesor;
    private List<Profesor> datos = new ArrayList<>();

public ProfesorTableModel(ProfesorDAO profesor)  {
this.profesor = profesor;
}
public void updateModel() throws DAOException{
 datos = profesor.obtenerTodos();
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
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
     Profesor preguntado = datos.get(rowIndex);
       switch (columnIndex){
           case 0: return preguntado.getId();
           case 1: return preguntado.getApellidos();
           case 2: return preguntado.getNombre();
           case 3: DateFormat df = DateFormat.getDateInstance();
           
           default: return"";
       }
    }
}
