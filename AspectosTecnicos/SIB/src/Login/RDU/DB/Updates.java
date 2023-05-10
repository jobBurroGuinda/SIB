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

import java.sql.SQLException;

/**
 *
 * @author Emoticon
 * @version 1.0.0.0_2
 */
public class Updates extends Aconexion {
    
    /*
    * Este método sirve para actualizar o modificar la contraseña
    */
    public boolean ActualizarC(String password){
        boolean verificador = false;
        
        if(cn == null) {
            conectar();
        }
        
        int r1=0, rD=0, rST, rR, rC;
        String sql1=null, sqlST=null, sqlR=null, sqlC=null;
        
        try{
            sqlST = "START TRANSACTION";
            ps = cn.prepareStatement(sqlST);
            rST= ps.executeUpdate();
        }catch(SQLException ex){System.out.println("sqlST");ex.printStackTrace();}
        
        try{
            sql1 = "UPDATE MUsuario SET psw_usu=password(?)";
            ps = cn.prepareStatement(sql1);
            ps.setString(1, password);
            r1 = ps.executeUpdate();
        }catch(SQLException ex){System.err.println("Error");ex.printStackTrace();}
        
            // Se verifica si se realizó la actualización correctamente
            if(r1 != 0){
                // Si es así los cambios se almacenan
                try{
                                sqlC = "COMMIT";
                                ps = cn.prepareStatement(sqlC);
                                rC= ps.executeUpdate();
                            }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                verificador = true;
            }
            else{
                // Si no, regresamos al estado inicial como si jamás hubieramos hecho nada
                try{
                                sqlR = "ROLLBACK";
                                ps = cn.prepareStatement(sqlR);
                                rR= ps.executeUpdate();
                            }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                verificador = false;
                    try{
                                sqlC = "COMMIT";
                                ps = cn.prepareStatement(sqlC);
                                rC= ps.executeUpdate();
                            }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                
            }
        
        return verificador;
    }
    
    
    
    // Este metodo sirve para actualizar el nombre de usuario y la contraseña
    public boolean ActualizarNomUC(String nomUsuario, String password){
        boolean verificador = false;
        
        if(cn == null) {
            conectar();
        }
        
        int r1=0, rD=0, rST, rR, rC;
        String sql1=null, sqlST=null, sqlR=null, sqlC=null;
        
        try{
            sqlST = "START TRANSACTION";
            ps = cn.prepareStatement(sqlST);
            rST= ps.executeUpdate();
        }catch(SQLException ex){System.out.println("sqlST");ex.printStackTrace();}
        
        try{
            sql1 = "UPDATE MUsuario SET nom_usu=?, psw_usu=password(?)";
            ps = cn.prepareStatement(sql1);
            ps.setString(1, nomUsuario);
            ps.setString(2, password);
            r1 = ps.executeUpdate();
        }catch(SQLException ex){System.err.println("Error");ex.printStackTrace();}
        
        
            // Se verifica si se realizó la actualización correctamente
            if(r1 != 0){
                // Si es así los cambios se almacenan
                try{
                                sqlC = "COMMIT";
                                ps = cn.prepareStatement(sqlC);
                                rC= ps.executeUpdate();
                            }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                verificador = true;
            }
            else{
                // Si no, regresamos al estado inicial como si jamás hubieramos hecho nada
                try{
                                sqlR = "ROLLBACK";
                                ps = cn.prepareStatement(sqlR);
                                rR= ps.executeUpdate();
                            }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                verificador = false;
                    try{
                                sqlC = "COMMIT";
                                ps = cn.prepareStatement(sqlC);
                                rC= ps.executeUpdate();
                            }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                
            }
        
        return verificador;
    }
    
}
