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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.PrintSetup;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFPrintSetup;

/**
 *
 * @author Emoticon
 * @version 1.0.0.0_2
 */
public class GeneradorExcel {
    /*
    * Esta clase servirá para realizar la conexión con Microsoft Excel y así se puedan realizar los inventarios
    */
    
    // Declaramos la variable que servirá para el archivo de Excel
    private FileOutputStream fileOut;

    // Creamos un nuevo libro de Excel
    XSSFWorkbook workbook = new XSSFWorkbook(); 
    // Creamod una nueva hoja en el libro y le damos un nombre:
    XSSFSheet hoja = workbook.createSheet("Bienes CEC Morelia");
    // Invocamos a los métodos necesarios para detallar la hoja
        // Este es para la primera fila, la que contendrá los nombres de los campos
    XSSFCellStyle estiloCabecera = workbook.createCellStyle();
        // Este para las demás filas, donde se acomodarán los valores
    XSSFCellStyle estiloFilas = workbook.createCellStyle();
        // Este servirá para darle un formato a la fecha
    XSSFCellStyle estiloFecha = workbook.createCellStyle();
        // Para darle un estilo al font de la letra de la primera fila
    XSSFFont fontCabecera = workbook.createFont();
        // Para darle un estilo al font de la letra de las demás filas
    XSSFFont fontFilas = workbook.createFont();
    
    // Esta variable servirá para obtener el nombre del sistema operativo
    // ya que la ruta donde se guardarán los inventarios puede cambiar dependiendo si usamos Windows XP, 7 u 8
    private static final String osName = System.getProperty("os.name");
    
    
    // Invocamos a la clase XSSFPrintSetup que nos servirá para dar las especificaciones de cómo queremos que se
    // visualicen los datos en la hoja de impresión
    XSSFPrintSetup imprimir;

    // Invocamos una lista a partir de la clave Bien
    private List<Bien>   bienes      = new ArrayList<>();
    
    
    public GeneradorExcel(){
    }
    
    
    /*
    * Este método sirve para generar inventarios de bienes dados de alta
    */
     public boolean GenerarInventarioE(ArrayList<Bien> bienes){
         // Primero recibe el arraylist
         boolean verificador = false;
         this.bienes = bienes;
         
         // Centramos los datos de la primera fila
         estiloCabecera.setAlignment(HorizontalAlignment.CENTER);
         // Damos un estilo al rellenado de la fila
         estiloCabecera.setFillPattern(FillPatternType.FINE_DOTS);
         // Rellenamos la fila de color guinda
         estiloCabecera.setFillBackgroundColor(new XSSFColor(new java.awt.Color(120, 0, 0)));
         // La letra de la esta primera fila será negrita
         fontCabecera.setBold(true);
         // AriaL
         fontCabecera.setFontName("Arial");
         // Tamaño 12
         fontCabecera.setFontHeightInPoints((short)12);
         // De color blanco
         fontCabecera.setColor(new XSSFColor(new java.awt.Color(225, 225, 225)));
         // Agregamos este estilo de letra al estilo de la fila
         estiloCabecera.setFont(fontCabecera);
         
         // Los valores de las demás filas estarán alineados hacia la izquierda
         estiloFilas.setAlignment(HorizontalAlignment.LEFT);
         // Con letra Arial
         fontFilas.setFontName("Arial");
         // Tamaño 12
         fontFilas.setFontHeightInPoints((short)12);
         estiloFilas.setFont(fontFilas);
         
        // Usamos a la clase Fecha que nos servirá para obtener la fecha actual
        Fecha f = new Fecha();
            // Creamos una nueva fila
            XSSFRow row=hoja.createRow(0);
            // Declaramos una variable para crear nuevas celdas
            XSSFCell cell;
            // Creamos una nueva celda
            cell=row.createCell(0);// le indicamos su ubicación
            cell.setCellValue("ID Bien");// Le introducimos texto, que viene siendo el nombre de la columna
            cell.setCellStyle(estiloCabecera);// Y le damos un estilo 
            // Lo mismo para todos
            cell=row.createCell(1);
            cell.setCellValue("Clave CABM");
            cell.setCellStyle(estiloCabecera);
            cell=row.createCell(2);
            cell.setCellValue("Descripción");
            cell.setCellStyle(estiloCabecera);
            cell=row.createCell(3);
            cell.setCellValue("Marca");
            cell.setCellStyle(estiloCabecera);
            cell=row.createCell(4);
            cell.setCellValue("Modelo");
            cell.setCellStyle(estiloCabecera);
            cell=row.createCell(5);
            cell.setCellValue("No de Serie");
            cell.setCellStyle(estiloCabecera);
            cell=row.createCell(6);
            cell.setCellValue("Nivel");
            cell.setCellStyle(estiloCabecera);
            cell=row.createCell(7);
            cell.setCellValue("Local");
            cell.setCellStyle(estiloCabecera);
            cell=row.createCell(8);
            cell.setCellValue("Departamento");
            cell.setCellStyle(estiloCabecera);
            cell=row.createCell(9);
            cell.setCellValue("Persona Asignada");
            cell.setCellStyle(estiloCabecera);
            cell=row.createCell(10);
            cell.setCellValue("Fecha de asignación");
            cell.setCellStyle(estiloCabecera);
            cell=row.createCell(11);
            cell.setCellValue("Antigüedad");
            cell.setCellStyle(estiloCabecera);
            cell=row.createCell(12);
            cell.setCellValue("Valor");
            cell.setCellStyle(estiloCabecera);
            cell=row.createCell(13);
            cell.setCellValue("Observaciones");
            cell.setCellStyle(estiloCabecera);
            
            // Con esta línea la fila superior (la que tiene el nombre de los campos)
            // Se quedará inmovil para que pueda apreciarse una correspondencia
            // de los datos con su respectiva columna, de manera más eficiente.
            hoja.createFreezePane(0, 1);
            
            // La variable de esta clase servirá para darle formato a la fecha
            CreationHelper createHelper = workbook.getCreationHelper();
            
            // Si el arraylist no está vacio...
            if (!bienes.isEmpty()) {
                // Esto servirá para indicar desde cuál fila debe ubicar las variables, desde la 1
                // que hablando con el lenguaje común de Excel, sería la fila 2
                int numerofila = 1;
                // El siguiente ciclo for() servirá para que se acomoden todos los datos de cada una de las filas o tuplas de la tabla o view de la DB
                for (Bien bien : bienes)
                {
        // Le damos un formato a la fecha 
        String fechaAsig = f.Fecha(bien.getAño(), bien.getMes(), bien.getDia());
                    // Con esto se van a poder crear las filas que sean necesarias
                    XSSFRow nextfila = hoja.createRow(numerofila);
                    // Con esto se van a poder crear nuevas celdas
                     XSSFCell celdaFila;
                    celdaFila = nextfila.createCell(0);// Se indica su posición, que vendría siendo A
                    celdaFila.setCellValue(bien.getIdBien());// Se introduce el valor del registro
                                        celdaFila.setCellStyle(estiloFilas);// por último se le da un estilo a la celda
                    // Lo mismo para todas las celdas
                    celdaFila = nextfila.createCell(1);
                    celdaFila.setCellValue(bien.getClaveCABM());
                                        celdaFila.setCellStyle(estiloFilas);
                    celdaFila = nextfila.createCell(2);
                    celdaFila.setCellValue(bien.getDescripcion());
                                        celdaFila.setCellStyle(estiloFilas);
                    celdaFila = nextfila.createCell(3);
                    celdaFila.setCellValue(bien.getMarca());
                                        celdaFila.setCellStyle(estiloFilas);
                    celdaFila = nextfila.createCell(4);
                    celdaFila.setCellValue(bien.getModelo());
                                        celdaFila.setCellStyle(estiloFilas);
                    celdaFila = nextfila.createCell(5);
                    celdaFila.setCellValue(bien.getNoSerie());
                                        celdaFila.setCellStyle(estiloFilas);
                    celdaFila = nextfila.createCell(6);
                    celdaFila.setCellValue(bien.getNivel());
                                        celdaFila.setCellStyle(estiloFilas);
                    celdaFila = nextfila.createCell(7);
                    celdaFila.setCellValue(bien.getLocal());
                                        celdaFila.setCellStyle(estiloFilas);
                    celdaFila = nextfila.createCell(8);
                    celdaFila.setCellValue(bien.getDepartamento());
                                        celdaFila.setCellStyle(estiloFilas);
                    celdaFila = nextfila.createCell(9);
                    celdaFila.setCellValue(bien.getPersonaDesig());
                                        celdaFila.setCellStyle(estiloFilas);
                    celdaFila = nextfila.createCell(10);
                    celdaFila.setCellValue(fechaAsig);
                                        celdaFila.setCellStyle(estiloFilas);
                                        // Con esto le indicaremos a Excel que se trata de una fecha:
                                        estiloFecha.setDataFormat(
                                                createHelper.createDataFormat().getFormat("DD/MM/YYYY"));
                                        celdaFila.setCellStyle(estiloFecha);
                    celdaFila = nextfila.createCell(11);
                    celdaFila.setCellValue(bien.getAntiguedad());
                                        celdaFila.setCellStyle(estiloFilas);
                    celdaFila = nextfila.createCell(12);
                    celdaFila.setCellValue(bien.getValor());
                                        celdaFila.setCellStyle(estiloFilas);
                    celdaFila = nextfila.createCell(13);
                    celdaFila.setCellValue(bien.getDetalles());
                                        celdaFila.setCellStyle(estiloFilas);
                  numerofila++;
                }
            
            // Autoajustamos el tamaño de las columnas
            hoja.autoSizeColumn(0);
            hoja.autoSizeColumn(1);
            hoja.autoSizeColumn(2);
            hoja.autoSizeColumn(3);
            hoja.autoSizeColumn(4);
            hoja.autoSizeColumn(5);
            hoja.autoSizeColumn(6);
            hoja.autoSizeColumn(7);
            hoja.autoSizeColumn(8);
            hoja.autoSizeColumn(9);
            hoja.autoSizeColumn(10);
            hoja.autoSizeColumn(11);
            hoja.autoSizeColumn(12);
            hoja.autoSizeColumn(13);

          // Algoritmo para para especificar los detalles de impresión del inventario
            // tArrayList es el número de bienes o de filas 
         int tArrayList = bienes.size();
         // Con esto la tabla con los datos se ubicará horizontalmente y centrandose
         hoja.setHorizontallyCenter(true);
         // Esto indica si la hoja muestra saltos de página automáticos
         hoja.setAutobreaks(true);
         // Esto sirve para que toda la tabla se autoajuste al tamaño de la hoja (horizontalmente)
         hoja.setFitToPage(true);
         // Esta clase servirá para especificar detalles de impresión
         PrintSetup ps = hoja.getPrintSetup();
         // Voltear la orientación de la hoja de papel
         ps.setLandscape(true);
         // Se indica el tipo de hoja
         ps.setPaperSize(XSSFPrintSetup.LETTER_PAPERSIZE);// en mi caso será tamaño carta
         // Se especifican los margenes
            //Del encabezado
         ps.setHeaderMargin(0.2D);
            // Y del pie de página
         ps.setFooterMargin(0.2D);
         
         // Con esto indicamos el área de impresión
         // setPrintArea(int hojaIndex, int columnaDeInicio, int columnaDeTermino, int inicioDeFila, int terminoDeFila)
         // El valor entero del terminoFila será la cantidad de registros porque ese es el indicador del término de la misma
         workbook.setPrintArea(0, 0, 13, 0, tArrayList);
           // Esto sirve para remarcar en la hoja de Excel el contorno del área de impresión
         hoja.setPrintGridlines(true);
         
         // Esto sirve para que todo el ancho de la tabla se acomode en una sola hoja de papel
         short numeroOfPagesDataSpans = (short)(tArrayList/27);
         ps.setFitHeight(numeroOfPagesDataSpans);
         ps.setFitWidth((short)1);
                // Obtenemos la hora y fecha actual
                String FH = f.mostrarFechayHora();
                // Inicializamos a variable para el nombre del archivo
                String nomArchivo = null;
                // Verificamos cuál es el sistema operativo que se estamos usando para saber cuál es la ruta adecuada
                    // donde se guardarán los inventarios
                // Si el sistema operativo donde se está usando el sistema es Windows XP...
                if(osName.equals("Windows XP")){
                    // La ruta donde se guardarán los datos será la siguiente:
                    nomArchivo = "C:\\Documents and Settings\\Admin\\Mis documentos\\" +
                                    "BienesCECMorelia--"+ FH + ".xlsx";
                }
                // De lo contrario, si..., el sistema operativo es Windows Vista, 7, 8, 8.1 o 10
                else if(osName.equals("Windows Vista") | osName.equals("Windows 7") | osName.equals("Windows 8") | osName.equals("Windows 8.1") | 
                                                                                                                            osName.equals("Windows 10")){
                    // La ruta será la siguiente:                                                                                                  
                    nomArchivo = "C:\\Users\\Public\\Documents\\" +
                                        "BienesCECMorelia--"+ FH + ".xlsx";
                } else {
                        // Si se detecta un sistema operativo diferente a los mencionados previamente se mostrará este mensaje:
                    JOptionPane.showConfirmDialog(null, "No se pueden generar inventarios en el sistema operativo: " + osName);
                }
                    // Inicializamos la variable salida para generar el archivo               
                    FileOutputStream salida = null;
             try {
                 salida = new FileOutputStream(
                         new File(nomArchivo));// Le asignamos el nombre de nuestro previamente mencionado al archivo y la ruta donde se va a guardar
             } catch (FileNotFoundException ex) {
                 // Si no se pudo generar el inventario
                 JOptionPane.showMessageDialog(null, "No se ha podido generar"+ 
                         " el inventario en Excel, intentelo de nuevo, si persiste" +
                         " el problema contacte al desarrollador de la aplicación",
                         "Error", numerofila);
             }
             try {
                 // Generamos el archivo
                 workbook.write(salida);
             } catch (IOException ex) {
                 JOptionPane.showMessageDialog(null, "No se han podido introducir los datos del bien"+ 
                         " en el inventario de Excel, intentelo de nuevo, si persiste" +
                         " el problema contacte al desarrollador de la aplicación",
                         "Error", numerofila);
             }
             try {
                 // Damos por concluida la generación del inventario
                 salida.close();
             } catch (IOException ex) {
                 Logger.getLogger(GeneradorExcel.class.getName()).log(Level.SEVERE, null, ex);
             }
                    // Le pasamos el valor "true" al verificador para indicarle a la clase Pinventarios que 
                    // El inventario se generó correctamente
                    verificador = true;
            }
            
            // De lo contrario..., esto significa que el arraylist está vacio
            // esto seguramente no pasará porque desde la clase Pinventarios se prevee esta situación y se propone una solución
            else{
                // Le pasamos el valor "false" al verificador para indicarle a la clase Pinventarios que 
                // El inventario no se generó correctamente
                verificador = false;
            }
         
         // Devolvemos el valor del verificador para comprobar si se generó correctamente o no el archivo
         return verificador;
     }
     
     
     
     /*
     * Este método sirve para generar inventarios de bienes dados de baja
     */
     public boolean GenerarInventarioEBajas(ArrayList<Bien> bienes) throws IOException{
         // Primero recibe el arraylist
         boolean verificador = false;
         this.bienes = bienes;
         
         // Centramos los datos de la primera fila
         estiloCabecera.setAlignment(HorizontalAlignment.CENTER);
         // Damos un estilo al rellenado de la fila
         estiloCabecera.setFillPattern(FillPatternType.FINE_DOTS);
         // Rellenamos la fila de color guinda
         estiloCabecera.setFillBackgroundColor(new XSSFColor(new java.awt.Color(120, 0, 0)));
         // La letra de la esta primera fila será negrita
         fontCabecera.setBold(true);
         // AriaL
         fontCabecera.setFontName("Arial");
         // Tamaño 12
         fontCabecera.setFontHeightInPoints((short)12);
         // De color blanco
         fontCabecera.setColor(new XSSFColor(new java.awt.Color(225, 225, 225)));
         // Agregamos este estilo de letra al estilo de la fila
         estiloCabecera.setFont(fontCabecera);
         
         // Los valores de las demás filas estarán alineados hacia la izquierda
         estiloFilas.setAlignment(HorizontalAlignment.LEFT);
         // Con letra Arial
         fontFilas.setFontName("Arial");
         // Tamaño 12
         fontFilas.setFontHeightInPoints((short)12);
         estiloFilas.setFont(fontFilas);
         
        // Usamos a la clase Fecha que nos servirá para obtener la fecha actual
        Fecha f = new Fecha();
            // Creamos una nueva fila
            XSSFRow row=hoja.createRow(0);
            // Declaramos una variable para crear nuevas celdas
            XSSFCell cell;
            // Creamos una nueva celda
            cell=row.createCell(0);// le indicamos su ubicación
            cell.setCellValue("ID Bien");// Le introducimos texto, que viene siendo el nombre de la columna
            cell.setCellStyle(estiloCabecera);// Y le damos un estilo 
            // Lo mismo para todos
            cell=row.createCell(1);
            cell.setCellValue("Clave CABM");
            cell.setCellStyle(estiloCabecera);
            cell=row.createCell(2);
            cell.setCellValue("Descripción");
            cell.setCellStyle(estiloCabecera);
            cell=row.createCell(3);
            cell.setCellValue("Marca");
            cell.setCellStyle(estiloCabecera);
            cell=row.createCell(4);
            cell.setCellValue("Modelo");
            cell.setCellStyle(estiloCabecera);
            cell=row.createCell(5);
            cell.setCellValue("No de Serie");
            cell.setCellStyle(estiloCabecera);
            cell=row.createCell(6);
            cell.setCellValue("Nivel");
            cell.setCellStyle(estiloCabecera);
            cell=row.createCell(7);
            cell.setCellValue("Local");
            cell.setCellStyle(estiloCabecera);
            cell=row.createCell(8);
            cell.setCellValue("Departamento");
            cell.setCellStyle(estiloCabecera);
            cell=row.createCell(9);
            cell.setCellValue("Persona Asignada");
            cell.setCellStyle(estiloCabecera);
            cell=row.createCell(10);
            cell.setCellValue("Fecha de baja");
            cell.setCellStyle(estiloCabecera);
            cell=row.createCell(11);
            cell.setCellValue("Antigüedad");
            cell.setCellStyle(estiloCabecera);
            cell=row.createCell(12);
            cell.setCellValue("Valor");
            cell.setCellStyle(estiloCabecera);
            cell=row.createCell(13);
            cell.setCellValue("Observaciones");
            cell.setCellStyle(estiloCabecera);
            
            // Con esta línea la fila superior (la que tiene el nombre de los campos)
            // Se quedará inmovil para que pueda apreciarse una correspondencia
            // de los datos con su respectiva columna, de manera más eficiente.
            hoja.createFreezePane(0, 1);
            
            // La variable de esta clase servirá para darle formato a la fecha
            CreationHelper createHelper = workbook.getCreationHelper();
            
            // Si el arraylist no está vacio...
            if (!bienes.isEmpty()) {
                // Esto servirá para indicar desde cuál fila debe ubicar las variables, desde la 1
                // que hablando con el lenguaje común de Excel, sería la fila 2
                int numerofila = 1;
                // El siguiente ciclo for() servirá para que se acomoden todos los datos de cada una de las filas o tuplas de la tabla o view de la DB
                for (Bien bien : bienes)
                {
        // Le damos un formato a la fecha 
        String fechaAsig = f.Fecha(bien.getAño(), bien.getMes(), bien.getDia());
                    // Con esto se van a poder crear las filas que sean necesarias
                    XSSFRow nextfila = hoja.createRow(numerofila);
                    // Con esto se van a poder crear nuevas celdas
                     XSSFCell celdaFila;
                    celdaFila = nextfila.createCell(0);// Se indica su posición, que vendría siendo A
                    celdaFila.setCellValue(bien.getIdBien());// Se introduce el valor del registro
                                        celdaFila.setCellStyle(estiloFilas);// por último se le da un estilo a la celda
                    // Lo mismo para todas las celdas
                    celdaFila = nextfila.createCell(1);
                    celdaFila.setCellValue(bien.getClaveCABM());
                                        celdaFila.setCellStyle(estiloFilas);
                    celdaFila = nextfila.createCell(2);
                    celdaFila.setCellValue(bien.getDescripcion());
                                        celdaFila.setCellStyle(estiloFilas);
                    celdaFila = nextfila.createCell(3);
                    celdaFila.setCellValue(bien.getMarca());
                                        celdaFila.setCellStyle(estiloFilas);
                    celdaFila = nextfila.createCell(4);
                    celdaFila.setCellValue(bien.getModelo());
                                        celdaFila.setCellStyle(estiloFilas);
                    celdaFila = nextfila.createCell(5);
                    celdaFila.setCellValue(bien.getNoSerie());
                                        celdaFila.setCellStyle(estiloFilas);
                    celdaFila = nextfila.createCell(6);
                    celdaFila.setCellValue(bien.getNivel());
                                        celdaFila.setCellStyle(estiloFilas);
                    celdaFila = nextfila.createCell(7);
                    celdaFila.setCellValue(bien.getLocal());
                                        celdaFila.setCellStyle(estiloFilas);
                    celdaFila = nextfila.createCell(8);
                    celdaFila.setCellValue(bien.getDepartamento());
                                        celdaFila.setCellStyle(estiloFilas);
                    celdaFila = nextfila.createCell(9);
                    celdaFila.setCellValue(bien.getPersonaDesig());
                                        celdaFila.setCellStyle(estiloFilas);
                    celdaFila = nextfila.createCell(10);
                    celdaFila.setCellValue(fechaAsig);
                                        celdaFila.setCellStyle(estiloFilas);
                                        // Con esto le indicaremos a Excel que se trata de una fecha:
                                        estiloFecha.setDataFormat(
                                                createHelper.createDataFormat().getFormat("DD/MM/YYYY"));
                                        celdaFila.setCellStyle(estiloFecha);
                    celdaFila = nextfila.createCell(11);
                    celdaFila.setCellValue(bien.getAntiguedad());
                                        celdaFila.setCellStyle(estiloFilas);
                    celdaFila = nextfila.createCell(12);
                    celdaFila.setCellValue(bien.getValor());
                                        celdaFila.setCellStyle(estiloFilas);
                    celdaFila = nextfila.createCell(13);
                    celdaFila.setCellValue(bien.getDetalles());
                                        celdaFila.setCellStyle(estiloFilas);
                  numerofila++;
                }
            
            // Autoajustamos el tamaño de las columnas
            hoja.autoSizeColumn(0);
            hoja.autoSizeColumn(1);
            hoja.autoSizeColumn(2);
            hoja.autoSizeColumn(3);
            hoja.autoSizeColumn(4);
            hoja.autoSizeColumn(5);
            hoja.autoSizeColumn(6);
            hoja.autoSizeColumn(7);
            hoja.autoSizeColumn(8);
            hoja.autoSizeColumn(9);
            hoja.autoSizeColumn(10);
            hoja.autoSizeColumn(11);
            hoja.autoSizeColumn(12);
            hoja.autoSizeColumn(13);

          // Algoritmo para para especificar los detalles de impresión del inventario
            // tArrayList es el número de bienes o de filas 
         int tArrayList = bienes.size();
         // Con esto la tabla con los datos se ubicará horizontalmente y centrandose
         hoja.setHorizontallyCenter(true);
         // Esto indica si la hoja muestra saltos de página automáticos
         hoja.setAutobreaks(true);
         // Esto sirve para que toda la tabla se autoajuste al tamaño de la hoja (horizontalmente)
         hoja.setFitToPage(true);
         // Esta clase servirá para especificar detalles de impresión
         PrintSetup ps = hoja.getPrintSetup();
         // Voltear la orientación de la hoja de papel
         ps.setLandscape(true);
         // Se indica el tipo de hoja
         ps.setPaperSize(XSSFPrintSetup.LETTER_PAPERSIZE);// en mi caso será tamaño carta
         // Se especifican los margenes
            //Del encabezado
         ps.setHeaderMargin(0.2D);
            // Y del pie de página
         ps.setFooterMargin(0.2D);
         
         // Con esto indicamos el área de impresión
         // setPrintArea(int hojaIndex, int columnaDeInicio, int columnaDeTermino, int inicioDeFila, int terminoDeFila)
         // El valor entero del terminoFila será la cantidad de registros porque ese es el indicador del término de la misma
         workbook.setPrintArea(0, 0, 13, 0, tArrayList);
           // Esto sirve para remarcar en la hoja de Excel el contorno del área de impresión
         hoja.setPrintGridlines(true);
         // Esto sirve para que todo el ancho de la tabla se acomode en una sola hoja de papel
         short numeroOfPagesDataSpans = (short)(tArrayList/27);
         ps.setFitHeight(numeroOfPagesDataSpans);
         ps.setFitWidth((short)1);
                // Obtenemos la hora y fecha actual
                String FH = f.mostrarFechayHora();
                // Inicializamos a variable para el nombre del archivo
                String nomArchivo = null;
                // Si el sistema operativo donde se está usando el sistema es Windows XP
                if(osName.equals("Windows XP")){
                    // La ruta donde se guardarán los datos será la siguiente:
                    nomArchivo = "C:\\Documents and Settings\\Admin\\Mis documentos\\" +
                                    "BienesCECMorelia_BAJAS--"+ FH + ".xlsx";
                }
                // De lo contrario, si..., el sistema operativo es Windows Vista, 7, 8, 8.1 o 10
                else if(osName.equals("Windows Vista") | osName.equals("Windows 7") | osName.equals("Windows 8") | osName.equals("Windows 8.1") | 
                                                                                                                            osName.equals("Windows 10")){
                        // La ruta será la siguiente:                                                                                                  
                            nomArchivo = "C:\\Users\\Public\\Documents\\" +
                                                "BienesCECMorelia_BAJAS--"+ FH + ".xlsx";
            } else {
                    // Si se detecta otro sistema operativo diferente a los mencionados previamente se mostrará este mensaje:
                        JOptionPane.showConfirmDialog(null, "No se pueden generar inventarios en el sistema operativo: " + osName);
            }
                    // Inicializamos la variable salida para generar el archivo               
                    FileOutputStream salida = null;
             try {
                 salida = new FileOutputStream(
                         new File(nomArchivo));// Le asignamos el nombre de nuestro archivo y la ruta donde se va a guardar
             } catch (FileNotFoundException ex) {
                 // Si no se pudo generar el inventario
                 JOptionPane.showMessageDialog(null, "No se ha podido generar"+ 
                         " el inventario en Excel, intentelo de nuevo, si persiste" +
                         " el problema contacte al desarrollador de la aplicación",
                         "Error", numerofila);
             }
             try {
                 // Generamos el archivo
                 workbook.write(salida);
             } catch (IOException ex) {
                 JOptionPane.showMessageDialog(null, "No se han podido introducir los datos del bien"+ 
                         " en el inventario de Excel, intentelo de nuevo, si persiste" +
                         " el problema contacte al desarrollador de la aplicación",
                         "Error", numerofila);
             }
             try {
                 // Damos por concluida la generación del inventario
                 salida.close();
             } catch (IOException ex) {
                 Logger.getLogger(GeneradorExcel.class.getName()).log(Level.SEVERE, null, ex);
             }
                    // Le pasamos el valor "true" al verificador para indicarle a la clase Pinventarios que 
                    // El inventario se generó correctamente
                    verificador = true;
            }
            
            // De lo contrario..., esto significa que el arraylist está vacio
            // esto seguramente no pasará porque desde la clase Pinventarios se prevee esta situación y se propone una solución
            else{
                // Le pasamos el valor "false" al verificador para indicarle a la clase Pinventarios que 
                // El inventario no se generó correctamente
                verificador = false;
            }
         
         // Devolvemos el valor del verificador para comprobar si se generó correctamente o no el archivo
         return verificador;
     }
}
