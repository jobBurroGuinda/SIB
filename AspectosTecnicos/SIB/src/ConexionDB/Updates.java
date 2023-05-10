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

import static ConexionDB.Aconexion.ps;
import java.sql.SQLException;

/**
 *
 * @author Emoticon
 * @version 1.0.0.0_2
 */
public class Updates extends Aconexion {
    /*
    * Esta clase servirá para actualizar los datos de los bienes y/o para dar de baja
    * El comando para dar de baja será Update en vez de Delete porque no queremos eliminar sus datos
    * ya que más adelande se solicitará un informe de todo lo que se ha dado de baja
    */
    
            // El verificador es para "verificar" toda la operación (valga la redundancia
    private boolean verificador = false;
    
    // Iniciamos el método que servirá para actualizar los datos
    public boolean Actualizar(int IDbien, String descripcion, String marca, String modelo,
            String claveCABM, String nSerie, double valor, String personaA, String edificio,
            String departamento, int nivel, int local, String fechaA, String detalles){
        // No no se ha realizado la conexión con la base de datos la hacemos
        if(cn == null) {
            conectar();
        }
        // Inicializamos las variables que nos a yudará a verificar si toda la operación se llevó
        // a cabo exitosamente
            // Las variables "r" son para verificar el resultado de cada una de las inserciones u operacines
        int r1=0, r2=0, r3=0, r4=0, r5=0, rD=0, rST, rR, rC;
            // las "sql" servirán para cada uno de los comandos SQL que se enviarán al gestor
        String sql1=null, sql2=null, sql3=null, sql4=null, sql5=null, sqlST=null, sqlR=null, sqlC=null;
        
        
        // Iniciamos la transacción
        try{
            sqlST = "START TRANSACTION";
            ps = cn.prepareStatement(sqlST);
            rST= ps.executeUpdate();
        }catch(SQLException ex){System.out.println("sqlST");ex.printStackTrace();}
        
        // Primeramente almacenamos los datos de la tabla "CDetalles"
        try{
            // Le asigno el valor del ID del bien al ID de la tabla CDetalles
            // porque son los mismos, debe haber correspondencia en las tablas al estar relacionadas
            sql1 = "UPDATE CDetalles SET val_det=?, dls_det=? where id_det=" + IDbien;
            ps = cn.prepareStatement(sql1);
            ps.setDouble(1, valor);
            ps.setString(2, detalles);
            r1 = ps.executeUpdate();
        }catch(SQLException ex){System.out.println("Error de sintaxis SQL1");ex.printStackTrace();}
        
        //  La señal de que los datos de la tabla CDetalles se almacenaron correctamente es que el valor de r1 sea diferente de cero
        // Por lo cual no  debe continuar almacenando lo demás si antes no guardó eso
        if(r1 != 0){
            // Si todo saló bien almacenamos los datos de la tabla CMarca
           try{
            sql2 = "UPDATE CMarca SET nom_mrc=?, mlo_mrc=? where id_mrc=" + IDbien;
            ps = cn.prepareStatement(sql2);
            ps.setString(1, marca);
            ps.setString(2, modelo);
            r2 = ps.executeUpdate();
        }catch(SQLException ex){System.out.println("Error de sintaxis SQL2");ex.printStackTrace();}
            
              // La señal es la misma
              if(r2 != 0){
                  // Si todo salió bien entonces ahora pasaremos a almacenar datos en una tabla
                  // con claves foráneas, para lo cual es necesario que el ordenador recuerde cuál fue el último ID que se almacenó
                  // que vendría siendo el de la tabla CMarca (la anterior inserción)
    // Almacenamos los datos de la tabla "MBien"
    try{
        sql3 = "UPDATE MBien SET des_ben=?, nse_ben=?, cbm_ben=? where id_ben=" + IDbien;
            ps = cn.prepareStatement(sql3);
            ps.setString(1, descripcion);
            ps.setString(2, nSerie);
            ps.setString(3, claveCABM);
            r3 = ps.executeUpdate();
        }catch(SQLException ex){System.out.println("Error de sintaxis SQL3");}
    
                    // La misma validación de las variables "r"
                    if(r3 != 0){
                        // Introducimos los datos de la tabla "CUbicación"
                        try{
                       sql4 = "UPDATE CUbicacion SET edi_ubi=?, dep_ubi=?, nvl_ubi=?, lcl_ubi=? where id_ubi=" + IDbien;
            ps = cn.prepareStatement(sql4);
            ps.setString(1, edificio);
            ps.setString(2, departamento);
            ps.setInt(3, nivel);
            ps.setInt(4, local);
            r4 = ps.executeUpdate();
        }catch(SQLException ex){System.out.println("Error de sintaxis SQL4");}
                    
                    if(r4 != 0){
                         try{
        sql5 = "UPDATE CAsignacion SET prs_asg=?, fas_arg=? WHERE id_asg=" + IDbien;
            ps = cn.prepareStatement(sql5);
            ps.setString(1, personaA);
            ps.setString(2, fechaA);
            r5 = ps.executeUpdate();
        }catch(SQLException ex){System.out.println("Error de sintaxis SQL5");ex.printStackTrace();}
                    
              }
                    // Si no se registraron los datos de la tabla CUbicacion los datos que se hayan almacenado de las tablas anteriores deben borrarse
                    else{
                        try{
                                sqlR = "ROLLBACK";
                                ps = cn.prepareStatement(sqlR);
                                rR= ps.executeUpdate();
                            }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                        try{
                                sqlC = "COMMIT";
                                ps = cn.prepareStatement(sqlC);
                                rC= ps.executeUpdate();
                            }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                        }
           }
                    // Si no se registraron los datos de la tabla MBien los datos que se hayan almacenado de las tablas anteriores deben borrarse
                    else{
                        try{
                                sqlR = "ROLLBACK";
                                ps = cn.prepareStatement(sqlR);
                                rR= ps.executeUpdate();
                            }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                        try{
                                sqlC = "COMMIT";
                                ps = cn.prepareStatement(sqlC);
                                rC= ps.executeUpdate();
                            }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                        }
        }
        // Si no se registraron los datos de la tabla CMarca los datos que se hayan almacenado de las tablas anteriores deben borrarse
        else{
                  try{
                                sqlR = "ROLLBACK";
                                ps = cn.prepareStatement(sqlR);
                                rR= ps.executeUpdate();
                            }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                  try{
                                sqlC = "COMMIT";
                                ps = cn.prepareStatement(sqlC);
                                rC= ps.executeUpdate();
                            }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
              }
              
      }
            // Proseguimos a verificar que definitivamente todos y cada una de las operaciones
            // se hayan realizado exitosamente
            if (r1 != 0   &   r2 != 0   &   r3 != 0   &   r4 != 0   &   r5 != 0){
                // Si todo se guardó correctamente..., guardamos permanentemente los cambios con el comando SQL "COMMIT"
                        try{
                                sqlC = "COMMIT";
                                ps = cn.prepareStatement(sqlC);
                                rC= ps.executeUpdate();
                            }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                // Y le pasamos el valor "true" al verificador para indicar que el registro se llevó a cabo exitosamente
                verificador = true;
            }
            // Si alguna de las operaciones no se llevó a cabo exitosamente, se debe borrar todo lo que se haya almacenado
            // en el resto de las tablas
            else {
                        try{
                                sqlR = "ROLLBACK";
                                ps = cn.prepareStatement(sqlR);
                                rR= ps.executeUpdate();
                            }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                try{
                                sqlC = "COMMIT";
                                ps = cn.prepareStatement(sqlC);
                                rC= ps.executeUpdate();
                            }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                // Le pasamos el valor "false" al verificador para indicar que el registro no se llevó a cabo exitosamente 
                verificador = false;
                
            }
            
        // Le enviamos el valor del verificador para indicar si los datos se almacenaron o no
        return verificador;
    }
    
    
  
    /*
    * El siguiente método sirve para dar de baja a un bien especificado
    */
    public boolean DarDeBaja(int IDbien){
        // primeramente se recibe el ID del bien a dar de baja
        // Después, si no estamos conectados a la base de datos lo hacemos
        if(cn == null) {
            conectar();
        }
        
        // Esta variable servirá para saber si la operación se llevó a cabo exitosamente
        int r = 0;
        
        try{
            // Existe una tabla de CEstado, la cual sólo cuenta con dos, "A" (alta) y "B" (baja) 
            // el ID del valor A es 1 y el de B es 2, por lo tanto, para dar de baja debemos de cambiarle
            // el estado al bien de 1 a 2, ya que cuando un nuevo registro se almacena, por default se le asigna el estado 1 (dado de alta)
            // además debemos agregar la fecha en que se dio de baja, para esto sirve la función SQL "CURDATE()"
            // (el servidor de MySQL no distingue mayúsculas de las minúsculas)
            // por último indicamos el ID del bien para que se dé de baja únicamente lo que queremos que se dé de baja (valga la redundancia)
        String sql = "UPDATE MBien SET id_est=2, fbj_ben=curdate() where id_ben=" + IDbien;
            // Mandamos el comando SQL al servidor
            ps = cn.prepareStatement(sql);
            r = ps.executeUpdate();
        }catch(SQLException ex){ex.printStackTrace();}
                    
        // Si la operación se realizó correctamente el verificador obtendrá el valor "true"
        // que indicará que el bien se dio de baja exitosamente
        // Para esto la variable "r" debe ser diferente de cero
        if(r != 0){
            verificador = true;
        }
        // de lo contrario, el verificador ya tiene por default el de false
        // que indicará que el bien no se dio de baja exitosamente
        
        return verificador;
    }
 

}
