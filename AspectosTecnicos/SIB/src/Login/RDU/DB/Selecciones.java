/*
 * El Sistema de Inventario de Bienes (SIB) fue desarrollado con la finalidad de
 * aportar una mejora al Centro de Educación Continua Unidad Morelia en cuanto
 * a la administración de los bienes muebles y equipo con los que cuenta.
 * 
 * Este software fue concebido, diseñado y desarrollado por el alumno cuyo seudónimo
 * adoptado es "Emoticón", como proyecto encomendado por el cumplimiento del servicio social.
 *
 */
package Login.RDU.DB;

import Login.RDU.Usuario;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Emoticon
 * @version 1.0.0.0_2
 */
public class Selecciones extends Aconexion {
    private boolean verificador = false;
    private String csp = null;
    private String sql = null;
    
    
    /*
    * Este método sirve para iniciar sesión, comprobar si el nombre de usuario y contraseña son correctos
    */
    public boolean Login(String nomUsuario, String contraseña) throws SQLException{
        // Si no se ha realizado la conexión con la base de datos la iniciamos
        if(cn == null) {
            conectar();
        }
        
     csp = "";
     // Especificamos el comando SQL de la consulta
     // La tabla "vusuarios", en realidad es una vista o view que sirve para desplegar únicamente el nombre de usuario
     // y contraseña
     sql = "select nom_usu, psw_usu from MUsuario where nom_usu='" + nomUsuario + "' and psw_usu=password('" + contraseña + "')";
         st = cn.createStatement();
         // Mandamos el comando al gestor
         rs = st.executeQuery(sql); 
         // Si el nombre de usuario y contraseña son correctos proseguimos con lo siguiente, de lo contrario 
                // el verificador tomará el valor de false, lo que indicará que alguno de los dos datos o los dos son incorrectos
         // Si todo salió bien buscamos todo lo que haya en la tabla...
         while(rs.next()){
             // ..., especificamente en el campo "nomUsuario" y "guardamos" lo que encontremos en la variable "csp"
             csp=rs.getString("nom_usu");
         }
         // Verificamos si el nombre de usuario introducido definitivamente coincide con el registrado en la base de datos
            // si es así...
         if(csp.equals(nomUsuario)){
                // El verificador tomará el valor de "true" para indicar que todos los datos son correctos y el usuario puede entrar
             verificador = true;
         }
         // De lo contrario..., si el nombre de usuario introducido no coincide con el registrado en la base de datos
         else{
             // El verificador tomará el valor de "false" para indicar que el nombre de usuario y/o contraseña es incorrecta y el usuario no puede entrar
             verificador = false;
         }
         // Devolvemos el valor del verificador
         return verificador;
    }
    
    
    public ArrayList<PR> Preguntas() throws SQLException{
        ArrayList<PR> Preguntas = new ArrayList();
        PR pr = null;
         if(cn == null) {
            conectar();
        }
        String csp = "";
        String sql = "select pre_prg from CPregunta";
         st = cn.createStatement();
         rs = st.executeQuery(sql); 
         while(rs.next()){
             pr = new PR();
             pr.setPregunta(rs.getString(1));
             Preguntas.add(pr);
         }
        return Preguntas;
    }
    
    
    public ArrayList<Usuario> VnomUsuario() throws SQLException{
        ArrayList<Usuario> Usuario = new ArrayList();
        Usuario nomUsuario = null;
         if(cn == null) {
            conectar();
        }
        String csp = "";
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
    
    
    public boolean VRespuesta1(String pregunta1, String respuesta1) throws SQLException{
         if(cn == null) {
            conectar();
        }
         
         csp = "";
         sql = "select * from DPregResp where pre_prg='" + pregunta1 + "' AND res_rta=password('" + respuesta1 + "')";
            st = cn.createStatement();
            rs = st.executeQuery(sql);
                while(rs.next()){
                    csp=rs.getString("pre_prg");
                    }
                    if(csp.equals(pregunta1)){
                        verificador = true;
                    } else{
                        verificador = false;
                    }
                    
        return verificador;
    }
    
    public boolean VRespuesta2(String pregunta2, String respuesta2) throws SQLException{
         if(cn == null) {
            conectar();
        }
         
         csp = "";
         sql = "select * from DPregResp where pre_prg='" + pregunta2 + "' AND res_rta=password('" + respuesta2 + "')";
            st = cn.createStatement();
            rs = st.executeQuery(sql);
                while(rs.next()){
                    csp=rs.getString("pre_prg");
                    }
                    if(csp.equals(pregunta2)){
                        verificador = true;
                    } else{
                        verificador = false;
                    }
                    
        return verificador;
    }
    
    public boolean VRespuesta3(String pregunta3, String respuesta3) throws SQLException{
         if(cn == null) {
            conectar();
        }
         
         csp = "";
         sql = "select * from DPregResp where pre_prg='" + pregunta3 + "' AND res_rta=password('" + respuesta3 + "')";
            st = cn.createStatement();
            rs = st.executeQuery(sql);
                while(rs.next()){
                    csp=rs.getString("pre_prg");
                    }
                    if(csp.equals(pregunta3)){
                        verificador = true;
                    } else{
                        verificador = false;
                    }
                    
        return verificador;
    }
    
    public boolean VRespuesta4(String pregunta4, String respuesta4) throws SQLException{
         if(cn == null) {
            conectar();
        }
         
         csp = "";
         sql = "select * from DPregResp where pre_prg='" + pregunta4 + "' AND res_rta=password('" + respuesta4 + "')";
            st = cn.createStatement();
            rs = st.executeQuery(sql);
                while(rs.next()){
                    csp=rs.getString("pre_prg");
                    }
                    if(csp.equals(pregunta4)){
                        verificador = true;
                    } else{
                        verificador = false;
                    }
                    
        return verificador;
    }
    
    
    public boolean Vcontraseña(String pregunta, String contraseña) throws SQLException {
        if(cn == null) {
            conectar();
        }
         csp = "";
         sql = "select * from DPrgRpasw where pre_prg='" + pregunta + "' AND psw_usu=password('" + contraseña + "')";
            st = cn.createStatement();
            rs = st.executeQuery(sql);
                while(rs.next()){
                    csp=rs.getString("pre_prg");
                    }
                    if(csp.equals(pregunta)){
                        verificador = true;
                    } else{
                        verificador = false;
                    }
                    
        return verificador;
    }
    
    
    public boolean Vrespuesta(String pregunta, String respuesta) throws SQLException {
        if(cn == null) {
            conectar();
        }
         csp = "";
         sql = "select * from DPregResp where pre_prg='" + pregunta + "' AND res_rta=password('" + respuesta + "')";
            st = cn.createStatement();
            rs = st.executeQuery(sql);
                while(rs.next()){
                    csp=rs.getString("pre_prg");
                    }
                    if(csp.equals(pregunta)){
                        verificador = true;
                    } else{
                        verificador = false;
                    }
                    
        return verificador;
    }
    
}
