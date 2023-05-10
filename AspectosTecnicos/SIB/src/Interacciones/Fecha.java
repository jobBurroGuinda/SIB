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

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Emoticon
 * @version 1.0.0.0_2
 */
public class Fecha {
    
    /*
    * Esta clase servirá para obtener la fecha actual o darle un formato a los datos introducidos en las listas desplegables
    * para que esto pueda ser procesado como dato tipo DATE por el gestor de DB
    */
    
    
    // Este método sirve para darle un formato a los datos introducidos en las listas desplegables
    public String Fecha(String año, int mes, int dia){
        String fecha = null;
        fecha = año + "-" + mes + "-" + dia;
        return fecha;
    }
    
    
    // Este sirve para obtener la fecha completa, actual
    public String mostrarFechaA(){
        String fechaA = null;
            Calendar cal = new GregorianCalendar();
                int dia = cal.get(Calendar.DAY_OF_MONTH);
                int mes = cal.get(Calendar.MONTH);
                int año = cal.get(Calendar.YEAR);
                    fechaA=fechaA = año + "-" + mes + "-" + dia;
    return fechaA;            
    }
    
    
    // Este sirve para obtener la fecha completa junto con la hora, actual
    public String mostrarFechayHora(){
        String fechaHA = null;
            Calendar cal = new GregorianCalendar();
                int segundo = cal.get(Calendar.SECOND);
                int minuto = cal.get(Calendar.MINUTE);
                int hora = cal.get(Calendar.HOUR);
                int m = cal.get(Calendar.AM_PM);
                int dia = cal.get(Calendar.DAY_OF_MONTH);
                int mes = cal.get(Calendar.MONTH);
                int año = cal.get(Calendar.YEAR);
                    String meririano = null;
                    if(m == 0){
                        meririano = "AM";
                    } else if(m == 1){
                        meririano = "PM";
                    }
                        fechaHA =año + "-" + mes + "-" + dia + "  Hora=" + 
                                hora + ";" + minuto + ";" + segundo + " " + meririano;
        return fechaHA;
    }
    
    
    // Este sirve para obtener sólo el año actual
    public int MostrarAño(){
        int año = 0;
            Calendar cal = new GregorianCalendar();
                año = cal.get(Calendar.YEAR);
    return año; 
    }
    
}
