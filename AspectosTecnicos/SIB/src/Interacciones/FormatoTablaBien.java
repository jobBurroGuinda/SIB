/*
 * El Sistema de Inventario de Bienes (SIB) fue desarrollado con la finalidad de
 * aportar una mejora al Centro de Educación Continua Unidad Morelia en cuanto
 * a la administración de los bienes muebles y equipo con los que cuenta.
 * 
 * Este software fue concebido, diseñado y desarrollado por el alumno cuyo seudónimo
 * adoptado es "Emoticón", como proyecto encomendado por el cumplimiento del servicio social.
 *
 */
package Interacciones;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Emoticon
 * @version 1.0.0.0_2
 */
public class FormatoTablaBien extends AbstractTableModel{

    /*
    * Con esta clase le daremos un formato a la tabla
    * para que se visualicen las columnas con sus nombres correspondientes
    */
    private List<Bien> bien = new ArrayList();
    private final String[] nomColumnas = {"ID Bien", "Clave CABM", "Descripción", 
    "Marca", "Modelo", "No. Serie", "Asignado a..."};
    private final boolean[] editables = {false, false, false, false, false, false, false};
    private final String sql = null;
    
    
    public FormatoTablaBien(ArrayList bien){
        this.bien = bien;
    }
    
    public void setBienes(ArrayList bien){
        this.bien = bien;
    }
    
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex){
        return editables[columnIndex];
    }
    
    @Override
    public int getRowCount() {
        return bien.size();
    }

    @Override
    public int getColumnCount() {
        return nomColumnas.length;
    }
    
    @Override
    public String getColumnName(int column) {
        return nomColumnas[column];
    }
    
    
    
    public Bien getFila(int index){
        return bien.get(index);
    }

    
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            
            case 0: return bien.get(rowIndex).getIdBien();
            case 1: return bien.get(rowIndex).getClaveCABM();
            case 2: return bien.get(rowIndex).getDescripcion();
            case 3: return bien.get(rowIndex).getMarca();
            case 4: return bien.get(rowIndex).getModelo();
            case 5: return bien.get(rowIndex).getNoSerie();
            case 6: return bien.get(rowIndex).getPersonaDesig();
            default: return null;
                
        }
    }
    
}
