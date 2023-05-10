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

/**
 *
 * @author Emoticon
 * @version 1.0.0.0_2
 */



/*
* Esta clase contiene el método apropiado determinar cuál es el comando SQL más apropiado 
* para las consultas detalladas que se realicen desde la evntana Pinventarios, que es la que
* sirve para llevar el control de los bienes
*/
public class BselectBA  {
    // Este es el método del que se habla en el comentario anterior
    public String busqASelcSQL(String descripcion, String marca, String modelo,
            String claveCABM, String noSerie, double valor, String personaAsig,
            String edificio, String departamento, int nivel, int local, String fechaAsig, String detalles){
        // Inicializamos la variable sql que será la que contendrá al comando apropiado
        String sql = null;
        /*
            * Para determinar esto se utilizarán instrucciones de tipo else if()
            * mismas que detectando los campos llenados, no continuarán buscando
            * lo cual optimiza al código
            * Acontinuación se estarán indicando las posibles combinaciones que sí se podrían presentar (previstas)
            * de campos llenados:
        */
        
            // Todos los campos excepto la clave CABM y el número de serie
                if(!descripcion.isEmpty() & !marca.isEmpty() & !modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor != 0.0 & !personaAsig.isEmpty() &
                   !edificio.isEmpty() & !departamento.isEmpty() & nivel != 0 &
                   local != 0 & !fechaAsig.isEmpty() & !detalles.isEmpty()){
                        // Si se cumple esta condición, la variable sql adquiere este valor:
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE des_ben REGEXP '" + descripcion + "' and " +
                                    "nom_mrc REGEXP '" + marca + "' and mlo_mrc = '" + modelo + "' and " +
                                    "val_det = " + valor + " and prs_asg REGEXP '" + personaAsig + "' and " +
                                    "edi_ubi REGEXP '" + edificio + "' and dep_ubi REGEXP '" + departamento + "' and " +
                                    "nvl_ubi =" + nivel + " and lcl_ubi =" + local + " and " +
                                    "fas_arg REGEXP '^" + fechaAsig + "' and dls_det REGEXP '" + detalles + "'";
                            // Lo mismo para todos los casos
                } 
                
                
            // Sin clave CABM
                else if(descripcion.isEmpty() & marca.isEmpty() & modelo.isEmpty() &
                            claveCABM.equals(" ") | claveCABM.equals("vacia") | claveCABM.equals("vacio") | 
                            claveCABM.equals("sin clave") | claveCABM.equals("sin clave cabm") | 
                            claveCABM.equals("VACIA") | claveCABM.equals("VACIO") | 
                            claveCABM.equals("SIN CLAVE") | claveCABM.equals("SIN CLAVE CABM") | 
                            claveCABM.equals("sin clave CABM") &
                   noSerie.isEmpty() & valor == 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel == 0 &
                   local == 0 & fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                    sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE cbm_ben = '' OR cbm_ben IS NULL OR cbm_ben='sin clave cabm'";
                }
                
                
            // Clave CABM
                else if( !claveCABM.isEmpty() ){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE cbm_ben REGEXP '^" + claveCABM + "'";
                }  
                
                
             // Descripción
                else if(!descripcion.isEmpty() & marca.isEmpty() & modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel == 0 &
                   local == 0 & fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE des_ben REGEXP '" + descripcion + "'";
                } 
                
                
            //Marca
                else if(descripcion.isEmpty() & !marca.isEmpty() & modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel == 0 &
                   local == 0 & fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE nom_mrc REGEXP '" + marca + "'";
                } 
                
                
            // Modelo
                else if(descripcion.isEmpty() & marca.isEmpty() & !modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel == 0 &
                   local == 0 & fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE mlo_mrc = '" + modelo + "'";
                } 
                
                
            // No de serie
                else if(!noSerie.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE nse_ben REGEXP '^" + noSerie + "'";
                } 
                
                
            //Nivel
                else if(descripcion.isEmpty() & marca.isEmpty() & modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel != 0 &
                   local == 0 & fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE nvl_ubi = " + nivel;
                } 
                
                // Local
                else if(descripcion.isEmpty() & marca.isEmpty() & modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel == 0 &
                   local != 0 & fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE lcl_ubi =" + local;
                } 
                
                
                // Persona asignada
                else if(descripcion.isEmpty() & marca.isEmpty() & modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & !personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel == 0 &
                   local == 0 & fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE prs_asg REGEXP '" + personaAsig + "'";
                } 
                
                
                // Departamento
                else if(descripcion.isEmpty() & marca.isEmpty() & modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & !departamento.isEmpty() & nivel == 0 &
                   local == 0 & fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE dep_ubi REGEXP '" + departamento + "'";
                } 
                
                
                // Valor
                else if(descripcion.isEmpty() & marca.isEmpty() & modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor != 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel == 0 &
                   local == 0 & fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE val_det =" + valor;
                } 
                
                
                // Detalles
                else if(descripcion.isEmpty() & marca.isEmpty() & modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel == 0 &
                   local == 0 & fechaAsig.isEmpty() & !detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE dls_det REGEXP '" + detalles + "'";
                } 
                
                
                // Fecha de asignación
                else if(descripcion.isEmpty() & marca.isEmpty() & modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel == 0 &
                   local == 0 & !fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE fas_arg REGEXP '^" + fechaAsig + "'";
                } 
                
                
                // Descripción, marca
                else if(!descripcion.isEmpty() & !marca.isEmpty() & modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel == 0 &
                   local == 0 & fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE des_ben REGEXP '" + descripcion + "' and " +
                                    "nom_mrc REGEXP '" + marca + "'";
                        
                } 
                
                
                // Descripción, marca, modelo
                else if(!descripcion.isEmpty() & !marca.isEmpty() & !modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel == 0 &
                   local == 0 & fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE des_ben REGEXP '" + descripcion + "' and " +
                                    "nom_mrc REGEXP '" + marca + "' and mlo_mrc = '" + modelo + "'";
                } 
                
                
                // Marca, modelo, departamento
                else if(descripcion.isEmpty() & !marca.isEmpty() & !modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & !departamento.isEmpty() & nivel == 0 &
                   local == 0 & fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE nom_mrc REGEXP '" + marca + "' and " +
                                    "dep_ubi REGEXP '" + departamento + "' and mlo_mrc = '" + modelo + "'";
                } 
                
                
                // Descripción, departamento
                else if(!descripcion.isEmpty() & marca.isEmpty() & modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & !departamento.isEmpty() & nivel == 0 &
                   local == 0 & fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE des_ben REGEXP '" + descripcion + "' and " +
                                    "dep_ubi REGEXP '" + departamento + "'";
                        
                } 
                
                // Descripción, persona asignada
                else if(!descripcion.isEmpty() & marca.isEmpty() & modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & !personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel == 0 &
                   local == 0 & fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE des_ben REGEXP '" + descripcion + "' and " +
                                    "prs_asg REGEXP '" + personaAsig + "'";
                } 
                
                
                // descripción, marca, modelo, persona, departamento
                else if(!descripcion.isEmpty() & !marca.isEmpty() & !modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & !personaAsig.isEmpty() &
                   !edificio.isEmpty() & !departamento.isEmpty() & nivel == 0 &
                   local == 0 & fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE nom_mrc REGEXP '" + marca + "' and " +
                                    "dep_ubi REGEXP '" + departamento + "' and mlo_mrc = '" + modelo + "' and " + 
                                    "des_ben REGEXP '" + descripcion + "' and prs_asg REGEXP '" + personaAsig + "'";
                } 
                
                
                // Descripción, persona, departamento
                else if(!descripcion.isEmpty() & marca.isEmpty() & modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & !personaAsig.isEmpty() &
                   !edificio.isEmpty() & !departamento.isEmpty() & nivel == 0 &
                   local == 0 & fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE des_ben REGEXP '" + descripcion + "' and " +
                                    "prs_asg REGEXP '" + personaAsig + "' and dep_ubi REGEXP '" + departamento + "'";
                } 
                
                
                // descripción, nivel
                else if(!descripcion.isEmpty() & marca.isEmpty() & modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel != 0 &
                   local == 0 & fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE des_ben REGEXP '" + descripcion + "' and " +
                                    "nvl_ubi = " + nivel;
                } 
                
                
                // Descripción, marca, modelo, departamento
                else if(!descripcion.isEmpty() & !marca.isEmpty() & !modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & !departamento.isEmpty() & nivel == 0 &
                   local == 0 & fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE nom_mrc REGEXP '" + marca + "' and " +
                                    "dep_ubi REGEXP '" + departamento + "' and mlo_mrc = '" + modelo + "' and " +
                                    "des_ben REGEXP '" + descripcion + "'";
                } 
                
                
                // descripción, marca, modelo, departamento, detalles
                else if(!descripcion.isEmpty() & !marca.isEmpty() & !modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & !departamento.isEmpty() & nivel == 0 &
                   local == 0 & fechaAsig.isEmpty() & !detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE nom_mrc REGEXP '" + marca + "' and " +
                                    "dep_ubi REGEXP '" + departamento + "' and mlo_mrc = '" + modelo + "' and " + 
                                    "des_ben REGEXP '" + descripcion + "' and dls_det REGEXP '" + detalles + "'";
                } 
                
                
                // Descripción, persona, marca
                else if(!descripcion.isEmpty() & !marca.isEmpty() & modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & !personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel == 0 &
                   local == 0 & fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE nom_mrc REGEXP '" + marca + "' and " +
                                    "des_ben REGEXP '" + descripcion + "' and prs_asg REGEXP '" + personaAsig + "'";
                } 
                
                
                // Descripción, detalles, persona
                else if(!descripcion.isEmpty() & marca.isEmpty() & modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & !personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel == 0 &
                   local == 0 & fechaAsig.isEmpty() & !detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE des_ben REGEXP '" + descripcion + "' and " +
                                    "dls_det REGEXP '" + detalles + "' and prs_asg REGEXP '" + personaAsig + "'";
                } 
                
                
                // Descripción, marca, departamento
                else if(!descripcion.isEmpty() & !marca.isEmpty() & modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & !departamento.isEmpty() & nivel == 0 &
                   local == 0 & fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE nom_mrc REGEXP '" + marca + "' and " +
                                    "dep_ubi REGEXP '" + departamento + "' and des_ben REGEXP '" + descripcion + "'";
                } 
                
                
                // Descripción, modelo, departamento
                else if(!descripcion.isEmpty() & marca.isEmpty() & !modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & !departamento.isEmpty() & nivel == 0 &
                   local == 0 & fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE des_ben REGEXP '" + descripcion + "' and " +
                                    "dep_ubi REGEXP '" + departamento + "' and mlo_mrc = '" + modelo + "'";
                } 
                
                
                // Descripción, modelo
                else if(!descripcion.isEmpty() & marca.isEmpty() & !modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel == 0 &
                   local == 0 & fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE des_ben REGEXP '" + descripcion + "' and " +
                                    "mlo_mrc = '" + modelo + "'";
                } 
                
                
                // Descripción, marca, valor
                else if(!descripcion.isEmpty() & !marca.isEmpty() & modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor != 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel == 0 &
                   local == 0 & fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE nom_mrc REGEXP '" + marca + "' and " +
                                    "des_ben REGEXP '" + descripcion + "' and val_det = " + valor;
                } 
                
                
                // Marca, valor
                else if(descripcion.isEmpty() & !marca.isEmpty() & modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor != 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel == 0 &
                   local == 0 & fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE nom_mrc REGEXP '" + marca + "' and " +
                                    "val_det = " + valor;
                } 
                
                
                // Marca, detalles
                else if(descripcion.isEmpty() & !marca.isEmpty() & modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel == 0 &
                   local == 0 & fechaAsig.isEmpty() & !detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE nom_mrc REGEXP '" + marca + "' and " +
                                    "dls_det REGEXP '" + detalles + "'";
                } 
                
                
                // Marca, modelo
                else if(descripcion.isEmpty() & !marca.isEmpty() & !modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel == 0 &
                   local == 0 & fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE nom_mrc REGEXP '" + marca + "' and " +
                                    "mlo_mrc = '" + modelo + "'";
                } 
                
                
                // Marca, departamento
                else if(descripcion.isEmpty() & !marca.isEmpty() & modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & !departamento.isEmpty() & nivel == 0 &
                   local == 0 & fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE nom_mrc REGEXP '" + marca + "' and " +
                                    "dep_ubi REGEXP '" + departamento + "'";
                } 
                
                
                // Marca, valor, detalles
                else if(descripcion.isEmpty() & !marca.isEmpty() & modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor != 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel == 0 &
                   local == 0 & fechaAsig.isEmpty() & !detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE nom_mrc REGEXP '" + marca + "' and " +
                                    "val_det = " + valor + " and dls_det REGEXP '" + detalles + "'";
                } 
                
                
                //Marca, nivel
                else if(descripcion.isEmpty() & !marca.isEmpty() & modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel != 0 &
                   local == 0 & fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE nom_mrc REGEXP '" + marca + "' and " +
                                    "nvl_ubi = " + nivel;
                } 
                
                
                // Descripción, local
                else if(!descripcion.isEmpty() & marca.isEmpty() & modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel == 0 &
                   local != 0 & fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE des_ben REGEXP '" + descripcion + "' and " +
                                    "lcl_ubi = " + local;
                } 
                
                
                // Marca, persona
                else if(descripcion.isEmpty() & !marca.isEmpty() & modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & !personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel == 0 &
                   local == 0 & fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE nom_mrc REGEXP '" + marca + "' and " +
                                    "prs_asg REGEXP '" + personaAsig + "'";
                } 
                
                
                // Marca, modelo, persona designada
                else if(descripcion.isEmpty() & !marca.isEmpty() & !modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & !personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel == 0 &
                   local == 0 & fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE nom_mrc REGEXP '" + marca + "' and " +
                                    "prs_asg REGEXP '" + personaAsig + "' and mlo_mrc = '" + modelo + "'";
                } 
                
                
                // Descripción, valor
                else if(!descripcion.isEmpty() & marca.isEmpty() & modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor != 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel == 0 &
                   local == 0 & fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE des_ben REGEXP '" + descripcion + "' and " +
                                    "val_det = " + valor;
                } 
                
                
                // Descripción, detalles
                else if(!descripcion.isEmpty() & marca.isEmpty() & modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel == 0 &
                   local == 0 & fechaAsig.isEmpty() & !detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE des_ben REGEXP '" + descripcion + "' and " +
                                    "dls_det REGEXP '" + detalles + "'";
                } 
                
                
                // Descripción, marca, modelo, valor
                else if(!descripcion.isEmpty() & !marca.isEmpty() & !modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor != 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel == 0 &
                   local == 0 & fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE nom_mrc REGEXP '" + marca + "' and " +
                                    "des_ben REGEXP '" + descripcion + "' and mlo_mrc = '" + modelo + "' and " +
                                    "val_det = " + valor;
                } 
                
                
                // Marca, modelo, valor
                else if(descripcion.isEmpty() & !marca.isEmpty() & !modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor != 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel == 0 &
                   local == 0 & fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE nom_mrc REGEXP '" + marca + "' and " +
                                    "val_det = " + valor + " and mlo_mrc = '" + modelo + "'";
                } 
                
                
                // Marca, modelo, detalles
                else if(descripcion.isEmpty() & !marca.isEmpty() & !modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel == 0 &
                   local == 0 & fechaAsig.isEmpty() & !detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE nom_mrc REGEXP '" + marca + "' and " +
                                    "dls_det REGEXP '" + detalles + "' and mlo_mrc = '" + modelo + "'";
                } 
                
                
                // Descripción, marca, modelo, detalles
                else if(!descripcion.isEmpty() & !marca.isEmpty() & !modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel == 0 &
                   local == 0 & fechaAsig.isEmpty() & !detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE nom_mrc REGEXP '" + marca + "' and " +
                                    "des_ben REGEXP '" + descripcion + "' and mlo_mrc = '" + modelo + "' and " +
                                    "dls_det REGEXP '" + detalles + "'";
                } 
                
                
                // Descripción, marca, detalles
                else if(!descripcion.isEmpty() & !marca.isEmpty() & modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel == 0 &
                   local == 0 & fechaAsig.isEmpty() & !detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE nom_mrc REGEXP '" + marca + "' and " +
                                    "des_ben REGEXP '" + descripcion + "' and dls_det REGEXP '" + detalles + "'";
                } 
                
                
                // Marca, modelo, valor, detalles
                else if(descripcion.isEmpty() & !marca.isEmpty() & !modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor != 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel == 0 &
                   local == 0 & fechaAsig.isEmpty() & !detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE nom_mrc REGEXP '" + marca + "' and " +
                                    "dls_det REGEXP '" + detalles + "' and mlo_mrc = '" + modelo + "' and " +
                                    "val_det = " + valor;
                } 
                
                
                // Descripción, marca, modelo valor, detalles
                else if(!descripcion.isEmpty() & !marca.isEmpty() & !modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor != 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel == 0 &
                   local == 0 & fechaAsig.isEmpty() & !detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE nom_mrc REGEXP '" + marca + "' and " +
                                    "des_ben REGEXP '" + descripcion + "' and mlo_mrc = '" + modelo + "' and " +
                                    "val_det = " + valor + " and dls_det REGEXP '" + detalles + "'";
                } 
                
                
                // Descripción, marca, modelo, local
                else if(!descripcion.isEmpty() & !marca.isEmpty() & !modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel == 0 &
                   local != 0 & fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE nom_mrc REGEXP '" + marca + "' and " +
                                    "des_ben REGEXP '" + descripcion + "' and mlo_mrc = '" + modelo + "' and " + 
                                    "lcl_ubi = " + local;
                } 
                
                
                // Descripción, marca, modelo, local, detalles
                else if(!descripcion.isEmpty() & !marca.isEmpty() & !modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel == 0 &
                   local != 0 & fechaAsig.isEmpty() & !detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE nom_mrc REGEXP '" + marca + "' and " +
                                    "des_ben REGEXP '" + descripcion + "' and mlo_mrc = '" + modelo + "' and " +
                                    "lcl_ubi = " + local + " and dls_det REGEXP '" + detalles + "'";
                } 
                
                
                // Descripción, marca, local
                else if(!descripcion.isEmpty() & !marca.isEmpty() & modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel == 0 &
                   local != 0 & fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE nom_mrc REGEXP '" + marca + "' and " +
                                    "des_ben REGEXP '" + descripcion + "' and lcl_ubi = " + local;
                } 
                
                
                // Descripción, marca, local, detalles
                else if(!descripcion.isEmpty() & !marca.isEmpty() & modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel == 0 &
                   local != 0 & fechaAsig.isEmpty() & !detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE nom_mrc REGEXP '" + marca + "' and " +
                                    "des_ben REGEXP '" + descripcion + "' and lcl_ubi = " + local + " and " +
                                    "dls_det REGEXP '" + detalles + "'";
                } 
                
                
                // Descripción, marca, modelo, nivel, detalles
                else if(!descripcion.isEmpty() & !marca.isEmpty() & !modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel != 0 &
                   local == 0 & fechaAsig.isEmpty() & !detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE nom_mrc REGEXP '" + marca + "' and " +
                                    "des_ben REGEXP '" + descripcion + "' and mlo_mrc = '" + modelo + "' and " +
                                    "nvl_ubi = " + nivel + " and dls_det REGEXP '" + detalles + "'";
                } 
                
                
                // Descripción, marca, nivel
                else if(!descripcion.isEmpty() & !marca.isEmpty() & modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel != 0 &
                   local == 0 & fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE nom_mrc REGEXP '" + marca + "' and " +
                                    "des_ben REGEXP '" + descripcion + "' and nvl_ubi = " + nivel;
                } 
                
                
                // Descripción, marca, nivel, detalles
                else if(!descripcion.isEmpty() & !marca.isEmpty() & modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel != 0 &
                   local == 0 & fechaAsig.isEmpty() & !detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE nom_mrc REGEXP '" + marca + "' and " +
                                    "des_ben REGEXP '" + descripcion + "' and nvl_ubi = " + nivel + " and " +
                                    "dls_det REGEXP '" + detalles + "'";
                } 
                
                
                // Descripción, marca, modelo, nivel
                else if(!descripcion.isEmpty() & !marca.isEmpty() & !modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel != 0 &
                   local == 0 & fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE nom_mrc REGEXP '" + marca + "' and " +
                                    "des_ben REGEXP '" + descripcion + "' and mlo_mrc = '" + modelo + "' and " + 
                                    "nvl_ubi = " + nivel;
                } 
                
                
                // Descripción, marca, valor, nivel
                else if(!descripcion.isEmpty() & !marca.isEmpty() & modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor != 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel != 0 &
                   local == 0 & fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE nom_mrc REGEXP '" + marca + "' and " +
                                    "des_ben REGEXP '" + descripcion + "' and val_det = " + valor + " and nvl_ubi = " + nivel;
                } 
                
                
                // Descripción, marca, modelo, valor, nivel
                else if(!descripcion.isEmpty() & !marca.isEmpty() & !modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor != 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel != 0 &
                   local == 0 & fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE nom_mrc REGEXP '" + marca + "' and " +
                                    "des_ben REGEXP '" + descripcion + "' and mlo_mrc = '" + modelo + "' and val_det = " + valor + " and nvl_ubi = " + nivel;
                } 
                
                
                // Marca, nivel, detalles, valor 
                else if(descripcion.isEmpty() & !marca.isEmpty() & modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor != 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel != 0 &
                   local == 0 & fechaAsig.isEmpty() & !detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE nom_mrc REGEXP '" + marca + "' and " +
                                    "dls_det REGEXP '" + detalles + "' and nvl_ubi = " + nivel + " and val_det = " + valor;
                } 
                
                
                // Marca, nivel, detalles 
                else if(descripcion.isEmpty() & !marca.isEmpty() & modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel != 0 &
                   local == 0 & fechaAsig.isEmpty() & !detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE nom_mrc REGEXP '" + marca + "' and " +
                                    "dls_det REGEXP '" + detalles + "' and nvl_ubi = " + nivel;
                } 
                
                
                // Marca, local, valor
                else if(descripcion.isEmpty() & !marca.isEmpty() & modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor != 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel == 0 &
                   local != 0 & fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE nom_mrc REGEXP '" + marca + "' and " +
                                    "lcl_ubi = " + local + " and val_det = " + valor;
                } 
                
                
                // Marca, local, detalles, valor
                else if(descripcion.isEmpty() & !marca.isEmpty() & modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor != 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel == 0 &
                   local != 0 & fechaAsig.isEmpty() & !detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE nom_mrc REGEXP '" + marca + "' and " +
                                    "dls_det REGEXP '" + detalles + "' and lcl_ubi = " + local + " and val_det = " + valor;
                } 
                
                
                // Marca, nivel, valor
                else if(descripcion.isEmpty() & !marca.isEmpty() & modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor != 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel != 0 &
                   local == 0 & fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE nom_mrc REGEXP '" + marca + "' and " +
                                    "nvl_ubi =" + nivel + " and val_det = " + valor;
                } 
                
                
                // Descripción, persona, fecha asignacion
                else if(!descripcion.isEmpty() & marca.isEmpty() & modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & !personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel == 0 &
                   local == 0 & !fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE des_ben REGEXP '" + descripcion + "' and " +
                                    "prs_asg REGEXP '" + personaAsig + "' and fas_arg REGEXP '^" + fechaAsig + "'";
                } 
                
                
                // Descripción, marca, nivel, detalles, valor
                else if(!descripcion.isEmpty() & !marca.isEmpty() & modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor != 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel != 0 &
                   local == 0 & fechaAsig.isEmpty() & !detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE des_ben REGEXP '" + descripcion + "' and " +
                                    "nom_mrc REGEXP '" + marca + "' and dls_det REGEXP '" + detalles + "' and nvl_ubi = " + nivel + " and val_det = " + valor;
                } 
                
                
                // Descripción, marca, modelo, nivel, valor, detalles
                else if(!descripcion.isEmpty() & !marca.isEmpty() & !modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor != 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel != 0 &
                   local == 0 & fechaAsig.isEmpty() & !detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE des_ben REGEXP '" + descripcion + "' and " +
                                    "nom_mrc REGEXP '" + marca + "' and mlo_mrc = '" + modelo + "' and dls_det REGEXP '" + detalles +
                                    "' and nvl_ubi = " + nivel + " and val_det = " + valor;
                } 
                
                
                // Descripción, marca, modelo, valor, persona
                else if(!descripcion.isEmpty() & !marca.isEmpty() & !modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor != 0.0 & !personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel == 0 &
                   local == 0 & fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE des_ben REGEXP '" + descripcion + "' and " +
                                    "nom_mrc REGEXP '" + marca + "' and mlo_mrc = '" + modelo + "' and prs_asg REGEXP '" + personaAsig +
                                    "' and val_det = " + valor;
                } 
                
                
                // Descripción, modelo, detalles
                else if(!descripcion.isEmpty() & marca.isEmpty() & !modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel == 0 &
                   local == 0 & fechaAsig.isEmpty() & !detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE des_ben REGEXP '" + descripcion + "' and " +
                                    "mlo_mrc = '" + modelo + "' and dls_det REGEXP '" + detalles + "'";
                } 
                
                
                // Descripción, modelo, local
                else if(!descripcion.isEmpty() & marca.isEmpty() & !modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel == 0 &
                   local != 0 & fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE des_ben REGEXP '" + descripcion + "' and " +
                                    "mlo_mrc = '" + modelo + "' and lcl_ubi = " + local;
                } 
                
                
                // Descripción, modelo, local, detalles
                else if(!descripcion.isEmpty() & marca.isEmpty() & !modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel == 0 &
                   local != 0 & fechaAsig.isEmpty() & !detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE des_ben REGEXP '" + descripcion + "' and " +
                                    "mlo_mrc = '" + modelo + "' and dls_det REGEXP '" + detalles + "' and " +
                                    "lcl_ubi = " + local;
                } 
                
                
                // Descripción, nivel, local
                else if(!descripcion.isEmpty() & marca.isEmpty() & modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel != 0 &
                   local != 0 & fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE des_ben REGEXP '" + descripcion + "' and " +
                                    "nvl_ubi = " + nivel + " and lcl_ubi = " + local;
                } 
                
                
                // Descripción, marca, modelo, persona
                else if(!descripcion.isEmpty() & !marca.isEmpty() & !modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & !personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel == 0 &
                   local == 0 & fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE des_ben REGEXP '" + descripcion + "' and " +
                                    "nom_mrc REGEXP '" + marca + "' and mlo_mrc REGEXP '" + modelo + "' and prs_asg REGEXP '" + personaAsig + "'";
                }
                
                // Descripción, fecha
                else if(!descripcion.isEmpty() & marca.isEmpty() & modelo.isEmpty() &
                   claveCABM.isEmpty() & noSerie.isEmpty() & valor == 0.0 & personaAsig.isEmpty() &
                   !edificio.isEmpty() & departamento.isEmpty() & nivel == 0 &
                   local == 0 & !fechaAsig.isEmpty() & detalles.isEmpty()){
                    
                            sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE des_ben REGEXP '" + descripcion + "' AND fas_arg REGEXP '^" + fechaAsig + "'";
                }
                
                
                // Si se presentó una combinación de campos no llenados la variable sql obtendrá el siguiente valor:
                else{
                    sql = "Especificación no registrada";
                    // Mismo en el que se le recibirá en la clase Pinventarios y se le informará al usuario que no se puede realizar esa búsqueda
                    // que introduzca menos datos y/o contacte al desarrollador de la aplicación
                }
                
                
        // Se devuelve el valor de la variable sql
        return sql;
    }
    
// Se termina la clase
}
