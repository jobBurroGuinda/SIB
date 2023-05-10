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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Emoticon
 * @version 1.0.0.0_2
 */
public class Aconexion {
    static Connection cn = null;
    static Statement st = null;
    static PreparedStatement ps = null;    
    static ResultSet rs = null;
    
    private static final String servidor = "localhost";
    private static final String nombreBD =  "sib";
    private static final String usuario = "root";
    static String contraseña = "t1nocota+";
    
    private boolean verificador;
    
    
    public void conectar(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://" + servidor + ":3306/" + nombreBD;
            cn = DriverManager.getConnection(url, usuario, contraseña);
            
        } catch(ClassNotFoundException ex){System.out.println("Se produjo un error de la url");}
        catch(SQLException ex){String msg = "";
            if(ex.getErrorCode() == 1049)
            {
                msg = "La base de datos: "+nombreBD+" no existe.";
            }else if(ex.getErrorCode() == 1044)
            {
                msg = "El usuario: "+usuario+" no existe.";
            }else if(ex.getErrorCode() == 1045)
            {
                msg = "Contraseña incorrecta.";
            }else if(ex.getErrorCode() == 0)
            {
                msg = "La coneccion con la base de datos no se puede realizar.\nParece que el servidor de base de datos no esta activo.";
            }
            JOptionPane.showMessageDialog(null, msg, ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    
    // Método para guardar nuevos usuarios
    public boolean RegistrarUsuario(String nomUsuario, String password, String pregunta1, 
            String pregunta2, String pregunta3, String pregunta4, String respuesta1,
            String respuesta2, String respuesta3, String respuesta4){
        
        if(cn == null) {
            conectar();
        }
        
        int r1=0, r2=0, r3=0, r4=0, r5=0, r6=0, r7=0, r8=0, r9=0, rST=0, rR=0, rC=0;
        String sql1=null, sql2=null, sql3=null, sql4=null, sql5=null, sql6=null, sql7=null, sql8=null, sql9=null, sqlST=null, sqlR=null, sqlC=null;
        
        
        try{
            sqlST = "START TRANSACTION";
            ps = cn.prepareStatement(sqlST);
            rST= ps.executeUpdate();
        }catch(SQLException ex){System.out.println("sqlST");ex.printStackTrace();}
        
        try{
            sql1 = "insert into MUsuario (nom_usu, psw_usu, id_usu, id_prv) values (?,password(?),1,1)";
            ps = cn.prepareStatement(sql1);
            ps.setString(1, nomUsuario);
            ps.setString(2, password);
            r1 = ps.executeUpdate();
        }catch(SQLException ex){System.out.println("sql1");ex.printStackTrace();}
            //Guardar respuesta 1 
            if(r1 != 0){
                        try{
                    sql2 = "insert into CRespuesta (res_rta) values (password(?))";
                    ps = cn.prepareStatement(sql2);
                    ps.setString(1, respuesta1);
                    r2 = ps.executeUpdate();
                }catch(SQLException ex){System.out.println("sql2");ex.printStackTrace();}
                        
                        if(r2 != 0){
                            // Guardar pregunta 1
                                                try{
                            String sql = "SELECT @last := LAST_INSERT_ID()";
                                st = cn.createStatement();
                                st.executeQuery(sql);
                            }catch(SQLException ex){ex.printStackTrace();}
                            try{
                        sql3 = "insert into CPregunta (pre_prg, id_usu, id_rta) values (?, 1, @last)";
                        ps = cn.prepareStatement(sql3);
                        ps.setString(1, pregunta1);
                        r3 = ps.executeUpdate();
                    }catch(SQLException ex){System.out.println("sql3");ex.printStackTrace();}
                            
                            if(r3 != 0){
                                // Guardar la respuesta 2
                                        try{
                            sql4 = "insert into CRespuesta (res_rta) values (password(?))";
                            ps = cn.prepareStatement(sql4);
                            ps.setString(1, respuesta2);
                            r4 = ps.executeUpdate();
                        }catch(SQLException ex){System.out.println("sql4");ex.printStackTrace();}
                                        
                                 if(r4 != 0){
                                     // Guardar pregunta 2
                                     try{
                                    String sql = "SELECT @last := LAST_INSERT_ID()";
                                        st = cn.createStatement();
                                        st.executeQuery(sql);
                                    }catch(SQLException ex){ex.printStackTrace();}
                                    try{
                                sql5 = "insert into CPregunta (pre_prg, id_usu, id_rta) values (?, 1, @last)";
                                ps = cn.prepareStatement(sql5);
                                ps.setString(1, pregunta2);
                                r5 = ps.executeUpdate();
                            }catch(SQLException ex){System.out.println("sql5");ex.printStackTrace();}
                                    
                                        if(r5 != 0){
                                            // Guardar respuesta 3
                                            try{
                                                sql6 = "insert into CRespuesta (res_rta) values (password(?))";
                                                ps = cn.prepareStatement(sql6);
                                                ps.setString(1, respuesta3);
                                                r6 = ps.executeUpdate();
                                            }catch(SQLException ex){System.out.println("sql6");ex.printStackTrace();}
                                            
                                                if(r6 != 0){
                                                   // Guardar pregunta 3
                                                    try{
                                                        String sql = "SELECT @last := LAST_INSERT_ID()";
                                                            st = cn.createStatement();
                                                            st.executeQuery(sql);
                                                        }catch(SQLException ex){ex.printStackTrace();}
                                                        try{
                                                    sql7 = "insert into CPregunta (pre_prg, id_usu, id_rta) values (?, 1, @last)";
                                                    ps = cn.prepareStatement(sql7);
                                                    ps.setString(1, pregunta3);
                                                    r7 = ps.executeUpdate();
                                                }catch(SQLException ex){System.out.println("sql7");ex.printStackTrace();}
                                                        if(r7 != 0){
                                                            // Guardar respuesta 4
                                                            try{
                                                                sql8 = "insert into CRespuesta (res_rta) values (password(?))";
                                                                ps = cn.prepareStatement(sql8);
                                                                ps.setString(1, respuesta4);
                                                                r8 = ps.executeUpdate();
                                                            }catch(SQLException ex){System.out.println("sql8");ex.printStackTrace();}
                                                                    if(r8 != 0){
                                                                        // Guardar pregunta 4
                                                                        try{
                                                                            String sql = "SELECT @last := LAST_INSERT_ID()";
                                                                                st = cn.createStatement();
                                                                                st.executeQuery(sql);
                                                                            }catch(SQLException ex){ex.printStackTrace();}
                                                                            try{
                                                                        sql9 = "insert into CPregunta (pre_prg, id_usu, id_rta) values (?, 1, @last)";
                                                                        ps = cn.prepareStatement(sql9);
                                                                        ps.setString(1, pregunta4);
                                                                        r9 = ps.executeUpdate();
                                                                    }catch(SQLException ex){System.out.println("sql9");ex.printStackTrace();}
                                                                    }
                                                                    // No se pudo guardar la respuesta 4
                                                                    else{
                                                                        try{
                                                                       sqlR = "ROLLBACK";
                                                                       ps = cn.prepareStatement(sqlR);
                                                                       rR= ps.executeUpdate();
                                                                   }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                                                                        }
                                                        }
                                                        // No se pudo guardar la pregunta 3
                                                        else{
                                                            try{
                                                           sqlR = "ROLLBACK";
                                                           ps = cn.prepareStatement(sqlR);
                                                           rR= ps.executeUpdate();
                                                       }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                                                            }
                                                }
                                                // No se pudo guardar la respuesta 3
                                                    else{
                                                    try{
                                                   sqlR = "ROLLBACK";
                                                   ps = cn.prepareStatement(sqlR);
                                                   rR= ps.executeUpdate();
                                               }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                                                }
                                        }
                                        // No se pudo guardar la pregunta 2
                                        else{
                                                try{
                                               sqlR = "ROLLBACK";
                                               ps = cn.prepareStatement(sqlR);
                                               rR= ps.executeUpdate();
                                           }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                                        }
                                 }
                                 // No se pudo guardar la respuesta 2
                                 else{
                                        try{
                                       sqlR = "ROLLBACK";
                                       ps = cn.prepareStatement(sqlR);
                                       rR= ps.executeUpdate();
                                   }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                                }   
                            }
                            // No se pudo guardar la pregunta 1
                            else{
                                try{
                                    sqlR = "ROLLBACK";
                                    ps = cn.prepareStatement(sqlR);
                                    rR= ps.executeUpdate();
                                }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                                }
                        }
                        // No se pudo guardar la respuesta 1
                        else{
                            try{
                                sqlR = "ROLLBACK";
                                ps = cn.prepareStatement(sqlR);
                                rR= ps.executeUpdate();
                            }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                        }
            }
            // No se pudieron guardar los datos del usuario, esi que regresamos como al principio
            else{
                try{
                                sqlR = "ROLLBACK";
                                ps = cn.prepareStatement(sqlR);
                                rR= ps.executeUpdate();
                            }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
            }
        
            
            if(r9 != 0){
                if (r1 != 0   &   r2 != 0   &   r3 != 0   &   r4 != 0   &   r5 != 0     &      r6 != 0
                                                        &      r7 != 0     &      r8 != 0     &      r9 != 0){
                    // Si todo se guardó correctamente...
                            try{
                                    sqlC = "COMMIT";
                                    ps = cn.prepareStatement(sqlC);
                                    rC= ps.executeUpdate();
                                }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                    verificador = true;
                }
                else {
                    verificador = false;
                }
            }
            // No se guardó correctamente la pregunta 4, por lo que todo debe ser borrado
            else{
                try{
                                sqlR = "ROLLBACK";
                                ps = cn.prepareStatement(sqlR);
                                rR= ps.executeUpdate();
                            }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
            }
            
        return verificador;
    }
}
