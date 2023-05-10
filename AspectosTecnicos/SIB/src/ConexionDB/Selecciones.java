/*
 * El Sistema de Inventario de Bienes (SIB) fue desarrollado con la finalidad de
 * aportar una mejora al Centro de Educación Continua Unidad Morelia en cuanto
 * a la administración de los bienes muebles y equipo con los que cuenta.
 * 
 * Este software fue concebido, diseñado y desarrollado por el alumno cuyo seudónimo
 * adoptado es "Emoticón", como proyecto encomendado por el cumplimiento del servicio social.
 *
 */
package ConexionDB;

import Interacciones.Bien;
import Login.RDU.Usuario;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Emoticon
 * @version 1.0.0.0_2
 */
public class Selecciones extends Aconexion{
    /*
    * Esta clase sirve para realizar las consultas y verificaciones
    * (si algún dato se encuentra registrado en el sistema)
    */
    
    // La variable verificador servirá para comprobar si la operación se llavó a cabo exitosamente
    private boolean verificador = false;
    
    // La variable "sql" servirá para los comandos para la base de datos
    private String sql = null;
    
    // La variable "csp" servirá para "guardar" o contener los resultados de la búsqueda
    // y verificar si alg´pun dato introducido coincide con uno almacenado en la base de datos
    private String csp = null;
    
    
    
    
    /*
    * El siguiente método servirá al momento de querer registrar un nuevo bien
    * su función será verificar si el No de serie introducido ya existe, ya que éste es una clave única
    * esto será muy útil para que no se produzcan Excepciones tipo SQL
    */
    public boolean VNnoSerie(String noSerie) throws SQLException{
        // Este método funciona de la misma manera que el del Login
        if(cn == null) {
            conectar();
        }
        
        csp = "";
     sql = "select nse_ben from MBien where nse_ben='" + noSerie + "'";
         st = cn.createStatement();
         rs = st.executeQuery(sql); 
         while(rs.next()){
             csp=rs.getString("nse_ben");
         }
         if(csp.equals(noSerie)){
             verificador = true;
         } else{
             verificador = false;
         }
         
         return verificador;
     }
        
    
    /*
    * El siguiente método servirá al momento de querer registrar un nuevo bien
    * su función será verificar si la clave CABM introducida ya existe, ya que éste es una clave única
    * esto será muy útil para que no se produzcan Excepciones tipo SQL
    * esto siempre y cuando se haya introducido una clave CABM
    */
    public boolean VNclaveCABM(String claveCABM) throws SQLException{
        // Este método funciona de la misma manera que el del Login
        if(cn == null) {
            conectar();
        }
        
        csp = "";
     sql = "select cbm_ben from MBien where cbm_ben='" + claveCABM + "'";
         st = cn.createStatement();
         rs = st.executeQuery(sql); 
         while(rs.next()){
             csp=rs.getString("cbm_ben");
         }
         if(csp.equals(claveCABM)){
             verificador = true;
         } else{
             verificador = false;
         }
         
         return verificador;
     }
    
    
    
    /*
    * Este método servirá para las consultas de los bienes
    */
    public ArrayList<Bien> getBien (String sql) throws SQLException{
        // Primero se recibe el comando SQL apropiado dependiendo de la consulta que se desee
    // Creamos un ArrayList que servirá para los bienes
    ArrayList<Bien> bienes = new ArrayList<>();
    // Inicializamos una variable para nuestra clase "Bien", con la que podremos acceder a sus métodos
    Bien b = null;
    
        // Si no se ha realizado la conexión a la base de datos la iniciamos
        if(cn == null){
            conectar();
        }
            // Mandamos el comando SQL al gestor
            st = cn.createStatement();
            rs = st.executeQuery(sql);
            // Si todo sale bien obtenemos todo lo que resulte de la búsqueda
            while(rs.next()){
                // Iniciamos la clase bien
                b = new Bien();
                // Agregamsos a la variable de la clase Bien, cada uno de los datos de los bienes
                b.setIdBien(rs.getInt(1));
                b.setClaveCABM(rs.getString(2));
                b.setDescripcion(rs.getString(3));
                b.setMarca(rs.getString(4));
                b.setModelo(rs.getString(5));
                b.setNoSerie(rs.getString(6));
                b.setEdificio(rs.getString(7));
                b.setNivel(rs.getInt(8));
                b.setLocal(rs.getInt(9));
                b.setDepartamento(rs.getString(10));
                b.setPersonaDesig(rs.getString(11));
                b.setAño(rs.getString(12));
                b.setMes(rs.getInt(13));
                b.setDia(rs.getInt(14));
                b.setAntiguedad(rs.getString(15));
                b.setValor(rs.getDouble(16));
                b.setDetalles(rs.getString(17));
                // Agregamos los resultados obtenidos en la variable "b" al arraylist "bienes"
                bienes.add(b);
            }
    // Se devuelve el arraylist
    return bienes;
    }
    
    
    /*
    * Este método servirá para las consultas de los usuarios
    */
    public ArrayList<Usuario> VnomUsuario() throws SQLException{
        // Este método funciona de la msima manera que el anterior
        ArrayList<Usuario> Usuario = new ArrayList();
        Usuario nomUsuario = null;
         if(cn == null) {
            conectar();
        }
        String sql = "select nom_usu from MUsuario";
         st = cn.createStatement();
         rs = st.executeQuery(sql); 
         while(rs.next()){
             nomUsuario = new Usuario();
             nomUsuario.setNomUsuario(rs.getString(1));
             Usuario.add(nomUsuario);
         }
        return Usuario;
    }
    
}
