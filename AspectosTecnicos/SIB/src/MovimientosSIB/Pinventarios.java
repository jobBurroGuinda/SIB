/*
 * El Sistema de Inventario de Bienes (SIB) fue desarrollado con la finalidad de
 * aportar una mejora al Centro de Educación Continua Unidad Morelia en cuanto
 * a la administración de los bienes muebles y equipo con los que cuenta.
 * 
 * Este software fue concebido, diseñado y desarrollado por el alumno cuyo seudónimo
 * adoptado es "Emoticón", como proyecto encomendado por el cumplimiento del servicio social.
 *
 */

// Indicamos el paquete al que pertenece esta clase
package MovimientosSIB;

// Importamos las clases necesarias para invocar los métodos correspondientes a cada función de esta clase
import Interacciones.FormatoTablaBien;
import ConexionDB.Inserciones;
import ConexionDB.Selecciones;
import ConexionDB.Updates;
import Interacciones.Bien;
import Interacciones.BselectBA;
import Interacciones.Fecha;
import Interacciones.GeneradorExcel;
import Login.Login;
import Login.RDU.CambNUC;
import Login.RDU.Usuario;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Emoticon
 * @version 1.0.0.0_2
 */


// Iniciamos la clase Pinventarios para la administración de los bienes
public class Pinventarios extends javax.swing.JFrame {
    
    // Declaramos las variales necesarias para el almacenamiento, búsqueda, actualización o baja de los bienes
                  // La variable "año" se pasa como String para facilitar su manipulación en diversos métodos que se mostrarán más adelante
    private String bien_id, descripcion, marca, modelo, claveCABM, noSerie, persona, edificio, departamento, detalles, fechaAsig, año;
    
    private double valor;
    
    private int nivel, local, mes, dia;
    
    
    
    // Se inicializa la variable indicador con la que la ventana podrá saber si
    // accedieron a ella desde el Login, o si intentan abrirla de forma independiente
    private static boolean indicador = false;
   
    
    /**
     * Creates new form Pinventarios
     * @param indicador
     */
    public Pinventarios(boolean indicador) {
        // Se recibe el valor del indicados que le haya pasado la clase Login
        // si no recibe nada se quedará con su valor por defaulft "false"
        this.indicador = indicador;
        
            
      /*
        * El siguiente algoritmo tiene como finalidad detectar si existe más de un usuario, o si no existe ninguno
        * esto se desarrollo con fines de seguridad, ya que uno de los requerimientos es que sólo exista un usuario registrado
        * por lo que si no existe ninguno o si hay más de uno la aplicación se cerrará informando la situación
        * y no se volverá a abrir hasta que se haya resuelto el problema 
      */
        // Preparar para desplegar el nombre de usuario en el campo correspondiente
            // Inicializamos el ArrayList "Usuarioa" para verificar si los datos introducidos son correctos
            // es decir, si los datos corresponden a la cuenta de usuario registrada (porque nada más existe una
            // Si hay más el sistema se cerrará o simplemente no abrirá
            ArrayList<Usuario> Usuarios = new ArrayList();
            // Inicializamos la clase Selecciones de nuestro paquete "ConexionDB", con la variable 's'
            Selecciones s = new Selecciones();
                                                // Abrimos un "try" para poder cachar cualquier error que se pudiera presentar
                                                // Relacionado a la sintaxis SQL, o ejecución de mysql, o de la base de datos
                                                try {
                                                    Usuarios = s.VnomUsuario(); // Con este método obtendremos los nombres de los usuarios
                                                } catch (SQLException ex) {
                                                    Logger.getLogger(CambNUC.class.getName()).log(Level.SEVERE, null, ex);
                                                }
                // Contamos los usuarios
                    int tUsuarios = Usuarios.size();
                    // Si esiste más de un usuario registrado...
                    if(tUsuarios > 1){
                        // ... la aplicación se cerrará y se desplegará un mensaje informando la situación
                        getToolkit().beep();
                        JOptionPane.showMessageDialog(rootPane, 
                                "Existe más de un usuario registrado en el sistema, contacte al desarrollador de la aplicación para resolver el inconveniente",
                                                                        "¡ERROR!", JOptionPane.ERROR_MESSAGE);
                        dispose();
                    }
                    // De lo contrario, si no existe ningún usuario registrado...
                    else if(tUsuarios < 1){
                        // ... también la aplicación se cerrará y se desplegará un mensaje informando la situación
                        getToolkit().beep();
                        JOptionPane.showMessageDialog(rootPane,
                                "No se encontró ningún usuario registrado, contacte al desarrollador de la aplicación para resolver el inconveniente", 
                                                                        "¡ERROR!", JOptionPane.ERROR_MESSAGE);
                        dispose();
                    }
                    // De lo contrario, si no sucede nada de lo anterior significa que sólo existe un usuario registrado por lo que...
                    else{
                        // Podremos pasar a lo siguiente
                                // Verificamos que el valor del indicador sea diferente de false
                                // para comprobar que se inició sesión correctamente
                                    if(indicador != false) {
                                    // Desplegamos los componentes de esta ventana
                                    initComponents();

                                    // Con método le decimos que queremos que la ventana se muestre en el centro de la pantalla
                                    setLocationRelativeTo(null);
                                    // Con esto no se le podrá cambiar el tamaño a la ventana
                                    setResizable(false);
                                    // Le asignamos un título a la ventana para que aparezca en la parte superior de la misma
                                    setTitle("Centro de Educación Continua Unidad Morelia del Instituto Politécnico Nacional");
                                    // Desplegamos en el campo "edificio", el nombre del mismo: 'CEC Morelia
                                    campoEdificio.setText("CEC Morelia");
                                    campoAño.setText("Año");
                                    // Con este método evitaremos que el usuario escriba algo más que no sea el nombre del edificio
                                    // además de que no podrá porrar el texto
                                    campoEdificio.setEditable(false);
                                                  // Estos botones los inabilitaremos, para que se activen únicamente cuando se seleccione
                                                  // un bien de la tabla para
                                                    // actualizarlo
                                                  botonActualizar.setEnabled(false);
                                                    // o darlo de baja
                                                  botonBaja.setEnabled(false);
                                    // Esta etiqueta en su momento nos informará que estamos visualizando bienes dados de baja, pero por el momento no es así
                                    // Por lo que la hacemos invisible
                                    etiquetaAvisoBajas.setVisible(false);

                                    
                                    /*
                                        * Este algoritmo servirá para que al seleccionar una fila en particular de la tabla de los bienes
                                        * se habiliten los botones de actualizar y dar de baja
                                        * además de que cada uno de los datos del registro seleccionado se despliegue en su
                                        * campo correspondiente
                                    */
                                    // Le decimos que el nombre de la tabla a la que le queremos agregar este efecto
                                    // es: "tablab"
                                        tablab.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
                                            @Override
                                            public void valueChanged(ListSelectionEvent lse) {
                                                // Si alguna fila está seleccionada...
                                              if(tablab.getSelectedRow() != -1){
                                                  // Obtenemos el número de la fila seleccionada
                                                  int fila = tablab.getSelectedRow();
                                                  // Declaramos una variable de tipo "Bien", es decir,
                                                  // del tipo de la clase de los bienes para poder pasarle
                                                  // los valores a la tabla
                                                  // además de que se invoca la clase "FormatoTabla Bien" que permitirá
                                                  // Agregar el nombre de las columnas y en general, darle un formato a la tabla, o personalizarla
                                                  Bien bien = ((FormatoTablaBien)tablab.getModel()).getFila(fila);
                                                  // Se le asigna un "id" a la fila seleccionada
                                                  String id = tablab.getValueAt(fila, 0).toString();
                                                  // Se despliega el valor de la variable descripción a su campo correspondiente
                                                  // conforme a la fila seleccionada
                                                  campoDescripcion.setText(bien.getDescripcion());
                                                  // Se despliega el valor de la variable marca a su campo correspondiente
                                                  // conforme a la fila seleccionada
                                                  campoMarca.setText(bien.getMarca());
                                                  // Se despliega el valor de la variable modelo a su campo correspondiente
                                                  // conforme a la fila seleccionada
                                                  campoModelo.setText(bien.getModelo());
                                                  // Se despliega el valor de la variable clave CABM a su campo correspondiente
                                                  // conforme a la fila seleccionada
                                                  campoClaveCABM.setText(bien.getClaveCABM());
                                                  // Se despliega el valor de la variable NoSerie (número de serie) a su campo correspondiente
                                                  // conforme a la fila seleccionada
                                                  campoNoserie.setText(bien.getNoSerie());
                                                  // Se despliega el valor de la variable valor a su campo correspondiente
                                                  // conforme a la fila seleccionada
                                                  campoValor.setText("" + bien.getValor());
                                                  // Se despliega el valor de la variable personaDesig (persona designada) a su campo correspondiente
                                                  // conforme a la fila seleccionada
                                                  campoPersona.setText(bien.getPersonaDesig());
                                                  // Se despliega el valor de la variable nivel a su campo correspondiente
                                                  // conforme a la fila seleccionada
                                                  campoNivel.setText("" + bien.getNivel());
                                                  // Se despliega el valor de la variable local a su campo correspondiente
                                                  // conforme a la fila seleccionada
                                                  campoLocal.setText("" + bien.getLocal());
                                                  // Se despliega el valor de la variable departamento a su campo correspondiente
                                                  // conforme a la fila seleccionada
                                                  campoDepartamento.setText(bien.getDepartamento());
                                                  // Se despliega el valor de la variable año a su campo correspondiente
                                                  // conforme a la fila seleccionada
                                                  campoAño.setText(bien.getAño());
                                                  // Se selecciona el valor de la variable mes a su campo correspondiente
                                                  // conforme a la fila seleccionada
                                                  listaMes.setSelectedIndex(bien.getMes());
                                                  // Se selecciona el valor de la variable dia a su campo correspondiente
                                                  // conforme a la fila seleccionada
                                                  listaDia.setSelectedIndex(bien.getDia());
                                                  // Se despliega el valor de la variable detalles a su campo correspondiente
                                                  // conforme a la fila seleccionada
                                                  campoDetalles.setText(bien.getDetalles());

                                            // Se habilita el botónActualizar
                                            botonActualizar.setEnabled(true);
                                            // Se habilita el botón baja
                                            botonBaja.setEnabled(true);

                                              } 
                                              // De lo contrario
                                              // Si ninguna fila está seleccionada...
                                              else{
                                                  // Se inhabilita el botonActualizar
                                                  botonActualizar.setEnabled(false);
                                                  // Se inhabilita el botónBaja
                                                  botonBaja.setEnabled(false);
                                              }
                                        }
                                            // Termina el algoritmo de selección de filas
                                        });


                                    }
                                    // De lo contrario
                                    // Si el indicador tiene valor "false"...
                                    else {
                                        // Se desplegará el siguiente mensaje: "Usted no inició sesión debidamente."
                                            //Se escuchará un sonido de alerta
                                        getToolkit().beep();
                                            // Con este método
                                        JOptionPane.showMessageDialog(rootPane, "Usted no inició sesión debidamente.", "Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                    
            } // Se "termina" este algoritmo de el despliegue de los componentes
        
    } // Aquí finaliza el construcctor Pinventarios(boolean indicador)

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        etiquetaCerrarSesion = new javax.swing.JLabel();
        botonInvTBienes = new javax.swing.JButton();
        etiquetaDescripcion = new javax.swing.JLabel();
        campoDescripcion = new javax.swing.JTextField();
        etiquetaModelo = new javax.swing.JLabel();
        etiquetaNoSerie = new javax.swing.JLabel();
        etiquetaClavecabm = new javax.swing.JLabel();
        etiquetaValor = new javax.swing.JLabel();
        etiquetaPersona = new javax.swing.JLabel();
        etiquetaMarca = new javax.swing.JLabel();
        etiquetaEdificio = new javax.swing.JLabel();
        etiquetaDepartamento = new javax.swing.JLabel();
        etiquetaFecha = new javax.swing.JLabel();
        campoPersona = new javax.swing.JTextField();
        campoMarca = new javax.swing.JTextField();
        campoNoserie = new javax.swing.JTextField();
        campoClaveCABM = new javax.swing.JTextField();
        campoDepartamento = new javax.swing.JTextField();
        campoValor = new javax.swing.JTextField();
        campoModelo = new javax.swing.JTextField();
        campoEdificio = new javax.swing.JTextField();
        listaDia = new javax.swing.JComboBox();
        etiquetaNivel = new javax.swing.JLabel();
        campoNivel = new javax.swing.JTextField();
        campoLocal = new javax.swing.JTextField();
        etiquetaLocal = new javax.swing.JLabel();
        campoAño = new javax.swing.JTextField();
        listaMes = new javax.swing.JComboBox();
        etiquetaDetalles = new javax.swing.JLabel();
        campoDetalles = new javax.swing.JTextField();
        botonRegistrar = new javax.swing.JButton();
        botonActualizar = new javax.swing.JButton();
        botonBuscar = new javax.swing.JButton();
        botonBaja = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablab = new javax.swing.JTable();
        etiquetaInventariosEspecificos = new javax.swing.JLabel();
        etiquetaCambNUC = new javax.swing.JLabel();
        botonLimpiarCampos = new javax.swing.JButton();
        botonConsultarBajas = new javax.swing.JButton();
        etiquetaAvisoBajas = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        etiquetaCerrarSesion.setFont(new java.awt.Font("Arial", 2, 12)); // NOI18N
        etiquetaCerrarSesion.setText("Cerrar sesión");
        etiquetaCerrarSesion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        etiquetaCerrarSesion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                etiquetaCerrarSesionMouseClicked(evt);
            }
        });

        botonInvTBienes.setBackground(new java.awt.Color(51, 0, 0));
        botonInvTBienes.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        botonInvTBienes.setForeground(new java.awt.Color(255, 255, 255));
        botonInvTBienes.setText("Clic aquí para generar inventario general");
        botonInvTBienes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonInvTBienes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonInvTBienesActionPerformed(evt);
            }
        });

        etiquetaDescripcion.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        etiquetaDescripcion.setText("Descripción:");

        campoDescripcion.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        campoDescripcion.setSelectionColor(new java.awt.Color(51, 0, 0));
        campoDescripcion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                campoDescripcionMouseClicked(evt);
            }
        });
        campoDescripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                campoDescripcionKeyTyped(evt);
            }
        });

        etiquetaModelo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        etiquetaModelo.setText("Modelo:");

        etiquetaNoSerie.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        etiquetaNoSerie.setText("No. de serie:");

        etiquetaClavecabm.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        etiquetaClavecabm.setText("Clave CABM:");

        etiquetaValor.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        etiquetaValor.setText("Valor: $");

        etiquetaPersona.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        etiquetaPersona.setText("Asignado a:");

        etiquetaMarca.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        etiquetaMarca.setText("Marca:");

        etiquetaEdificio.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        etiquetaEdificio.setText("Edificio:");
        etiquetaEdificio.setMaximumSize(new java.awt.Dimension(84, 40));

        etiquetaDepartamento.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        etiquetaDepartamento.setText("Departamento:");

        etiquetaFecha.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        etiquetaFecha.setText("Fecha de asignación:");

        campoPersona.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        campoPersona.setSelectionColor(new java.awt.Color(51, 0, 0));
        campoPersona.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                campoPersonaKeyTyped(evt);
            }
        });

        campoMarca.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        campoMarca.setSelectionColor(new java.awt.Color(51, 0, 0));
        campoMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoMarcaActionPerformed(evt);
            }
        });
        campoMarca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                campoMarcaKeyTyped(evt);
            }
        });

        campoNoserie.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        campoNoserie.setSelectionColor(new java.awt.Color(51, 0, 0));
        campoNoserie.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                campoNoserieKeyTyped(evt);
            }
        });

        campoClaveCABM.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        campoClaveCABM.setSelectionColor(new java.awt.Color(51, 0, 0));
        campoClaveCABM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoClaveCABMActionPerformed(evt);
            }
        });
        campoClaveCABM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                campoClaveCABMKeyTyped(evt);
            }
        });

        campoDepartamento.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        campoDepartamento.setSelectionColor(new java.awt.Color(51, 0, 0));
        campoDepartamento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                campoDepartamentoKeyTyped(evt);
            }
        });

        campoValor.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        campoValor.setSelectionColor(new java.awt.Color(51, 0, 0));
        campoValor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoValorActionPerformed(evt);
            }
        });
        campoValor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                campoValorKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                campoValorKeyTyped(evt);
            }
        });

        campoModelo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        campoModelo.setSelectionColor(new java.awt.Color(51, 0, 0));
        campoModelo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                campoModeloKeyTyped(evt);
            }
        });

        campoEdificio.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        campoEdificio.setSelectionColor(new java.awt.Color(51, 0, 0));
        campoEdificio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoEdificioActionPerformed(evt);
            }
        });
        campoEdificio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                campoEdificioKeyTyped(evt);
            }
        });

        listaDia.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        listaDia.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Día:", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));

        etiquetaNivel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        etiquetaNivel.setText("Nivel:");

        campoNivel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        campoNivel.setSelectionColor(new java.awt.Color(51, 0, 0));
        campoNivel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                campoNivelKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                campoNivelKeyTyped(evt);
            }
        });

        campoLocal.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        campoLocal.setSelectionColor(new java.awt.Color(51, 0, 0));
        campoLocal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoLocalActionPerformed(evt);
            }
        });
        campoLocal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                campoLocalKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                campoLocalKeyTyped(evt);
            }
        });

        etiquetaLocal.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        etiquetaLocal.setText("Local:");

        campoAño.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        campoAño.setSelectionColor(new java.awt.Color(51, 0, 0));
        campoAño.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                campoAñoMouseClicked(evt);
            }
        });
        campoAño.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoAñoActionPerformed(evt);
            }
        });
        campoAño.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                campoAñoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                campoAñoKeyTyped(evt);
            }
        });

        listaMes.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        listaMes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mes:", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "junio", "julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));

        etiquetaDetalles.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        etiquetaDetalles.setText("Observaciones:");

        campoDetalles.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        campoDetalles.setSelectionColor(new java.awt.Color(51, 0, 0));
        campoDetalles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoDetallesActionPerformed(evt);
            }
        });
        campoDetalles.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                campoDetallesKeyTyped(evt);
            }
        });

        botonRegistrar.setBackground(new java.awt.Color(51, 0, 0));
        botonRegistrar.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        botonRegistrar.setForeground(new java.awt.Color(255, 255, 255));
        botonRegistrar.setText("Guardar nuevo registro");
        botonRegistrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRegistrarActionPerformed(evt);
            }
        });

        botonActualizar.setBackground(new java.awt.Color(51, 0, 0));
        botonActualizar.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        botonActualizar.setForeground(new java.awt.Color(255, 255, 255));
        botonActualizar.setText("Actualizar");
        botonActualizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonActualizarActionPerformed(evt);
            }
        });

        botonBuscar.setBackground(new java.awt.Color(51, 0, 0));
        botonBuscar.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        botonBuscar.setForeground(new java.awt.Color(255, 255, 255));
        botonBuscar.setText("Buscar");
        botonBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBuscarActionPerformed(evt);
            }
        });

        botonBaja.setBackground(new java.awt.Color(51, 0, 0));
        botonBaja.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        botonBaja.setForeground(new java.awt.Color(255, 255, 255));
        botonBaja.setText("Dar de baja");
        botonBaja.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonBaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBajaActionPerformed(evt);
            }
        });

        jScrollPane1.setPreferredSize(new java.awt.Dimension(375, 200));

        tablab.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        tablab.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tablab.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tablab.setGridColor(new java.awt.Color(255, 255, 255));
        tablab.setRequestFocusEnabled(false);
        tablab.setRowHeight(25);
        tablab.setSelectionBackground(new java.awt.Color(51, 0, 0));
        tablab.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                tablabMouseMoved(evt);
            }
        });
        jScrollPane1.setViewportView(tablab);

        etiquetaInventariosEspecificos.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        etiquetaInventariosEspecificos.setText("Sistema de Inventario de Bienes versión 1.0");

        etiquetaCambNUC.setFont(new java.awt.Font("Arial", 2, 12)); // NOI18N
        etiquetaCambNUC.setText("Modificar nombre de usuario y/o contraseña");
        etiquetaCambNUC.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        etiquetaCambNUC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                etiquetaCambNUCMouseClicked(evt);
            }
        });

        botonLimpiarCampos.setBackground(new java.awt.Color(51, 0, 0));
        botonLimpiarCampos.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        botonLimpiarCampos.setForeground(new java.awt.Color(255, 255, 255));
        botonLimpiarCampos.setText("Limpiar campos");
        botonLimpiarCampos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonLimpiarCampos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonLimpiarCamposActionPerformed(evt);
            }
        });

        botonConsultarBajas.setBackground(new java.awt.Color(51, 0, 0));
        botonConsultarBajas.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        botonConsultarBajas.setForeground(new java.awt.Color(255, 255, 255));
        botonConsultarBajas.setText("Generar inventario de bienes dados de baja");
        botonConsultarBajas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonConsultarBajas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonConsultarBajasActionPerformed(evt);
            }
        });

        etiquetaAvisoBajas.setFont(new java.awt.Font("Times New Roman", 2, 17)); // NOI18N
        etiquetaAvisoBajas.setForeground(new java.awt.Color(51, 0, 0));
        etiquetaAvisoBajas.setText("Usted está visualizando bienes dados de baja");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(botonRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(etiquetaFecha)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(listaDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(listaMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(campoAño, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(botonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(14, 14, 14)
                                    .addComponent(botonActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(botonBaja, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(etiquetaDetalles)
                                .addComponent(campoDetalles, javax.swing.GroupLayout.PREFERRED_SIZE, 819, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1078, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(etiquetaDescripcion)
                                .addComponent(etiquetaPersona)
                                .addComponent(etiquetaClavecabm)
                                .addComponent(campoClaveCABM)
                                .addComponent(campoDescripcion)
                                .addComponent(campoPersona, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(etiquetaMarca)
                                            .addComponent(etiquetaNoSerie)
                                            .addComponent(etiquetaEdificio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(campoNoserie)
                                            .addComponent(campoMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(campoEdificio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(16, 16, 16)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(etiquetaDepartamento)
                                            .addComponent(etiquetaValor)
                                            .addComponent(campoValor)
                                            .addComponent(campoModelo)
                                            .addComponent(campoDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(etiquetaModelo))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(campoNivel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(etiquetaNivel))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(etiquetaLocal)
                                        .addComponent(campoLocal, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(etiquetaInventariosEspecificos))))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(etiquetaCambNUC)
                            .addGap(114, 114, 114)
                            .addComponent(etiquetaAvisoBajas)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(etiquetaCerrarSesion))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(botonLimpiarCampos)
                                .addGap(594, 594, 594)
                                .addComponent(botonInvTBienes, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(botonConsultarBajas))))
                .addGap(19, 27, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etiquetaCerrarSesion)
                    .addComponent(etiquetaCambNUC)
                    .addComponent(etiquetaAvisoBajas))
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(botonInvTBienes, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(etiquetaInventariosEspecificos)
                        .addComponent(botonLimpiarCampos)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonConsultarBajas)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(124, 124, 124)
                                        .addComponent(etiquetaEdificio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(etiquetaNivel)
                                        .addComponent(etiquetaDepartamento)
                                        .addComponent(etiquetaLocal)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(campoEdificio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(campoDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(campoNivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(campoLocal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(etiquetaMarca)
                                .addGap(3, 3, 3)
                                .addComponent(campoMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(etiquetaNoSerie)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(campoNoserie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(etiquetaModelo)
                            .addGap(3, 3, 3)
                            .addComponent(campoModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(etiquetaValor)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(campoValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(67, 67, 67)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(etiquetaDescripcion)
                        .addGap(3, 3, 3)
                        .addComponent(campoDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(etiquetaClavecabm)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(campoClaveCABM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(etiquetaPersona)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(campoPersona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(etiquetaFecha)
                    .addComponent(etiquetaDetalles))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(listaMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(listaDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoDetalles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoAño, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonBaja, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1071, 1071, 1071))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 740, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    /*
    * Método para cerrar sesión
    */
    private void etiquetaCerrarSesionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_etiquetaCerrarSesionMouseClicked
        // Se abre la ventana de Login
        Login l = new Login();
        l.setVisible(true);
        // Y se cierra la de Pinventarios
        dispose();
    }//GEN-LAST:event_etiquetaCerrarSesionMouseClicked

    private void campoMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoMarcaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoMarcaActionPerformed

    private void campoClaveCABMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoClaveCABMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoClaveCABMActionPerformed

    private void campoEdificioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoEdificioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoEdificioActionPerformed

    private void campoLocalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoLocalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoLocalActionPerformed

    private void campoAñoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoAñoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoAñoActionPerformed

    private void campoDetallesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoDetallesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoDetallesActionPerformed

    
    /*
        * Método para registrar nuevos bienes en el sistema
    */
    private void botonRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRegistrarActionPerformed
        // Se captura lo que el usuario haya escrito en los respectivos campos
        descripcion = campoDescripcion.getText();    
        
        marca = campoMarca.getText();       
        
        modelo = campoModelo.getText();    
        
        claveCABM = campoClaveCABM.getText(); 
        
        noSerie = campoNoserie.getText();   
        
        persona = campoPersona.getText();              
        
        edificio = campoEdificio.getText();          
        
        departamento = campoDepartamento.getText();
        
        // El año para como String por causas de facilitación al momento de manipularlo
        año = campoAño.getText();
        
        // Se capturan los datos seleccionados de las listas desplegables
        mes = this.listaMes.getSelectedIndex();
        
        dia = this.listaDia.getSelectedIndex();
        
        detalles = campoDetalles.getText();
        
        // Se inicializa la variable "verificador"
        // Ésta servirá para verificar que una operación se haya llevado con éxito
        boolean verificador = false;
        
        // Todos los campos son obligatorios excepto la clave CABM, esto porque el CEC Morelia cuenta con bienes
        // sin éste dato
        // Esi que se verifica si el usuario cumplió con esta condición
        if(descripcion.isEmpty() | marca.isEmpty() | modelo.isEmpty() |
                noSerie.isEmpty() | this.campoValor.getText().isEmpty() |
                persona.isEmpty() | edificio.isEmpty() | departamento.isEmpty() |
                this.campoNivel.getText().isEmpty() | this.campoLocal.getText().isEmpty() |
                this.campoAño.getText().isEmpty() | this.campoAño.getText().equals("Año") | this.listaMes.getSelectedIndex() == 0 |
                this.listaDia.getSelectedIndex() == 0) {
            // Si no fue así se le informará que debe rellenar los campos que faltan
            getToolkit().beep();
            JOptionPane.showMessageDialog(rootPane, "Para guardar un nuevo registro todos los campos son oblicatorios, excepto la clave CABM",
                    "Rellene los campos que faltan", JOptionPane.ERROR_MESSAGE);
        }
        
        // De lo contrario, si el usuario sí cumplió con esta condición...
        else {
                // El año entra como String, pero con esto lo transformamos a entero
                int tAño = Integer.parseInt(año);
                // Verificamos si el año introducido es inferior al 1996
                // ya que en ese año se fundó el CEC y por lógica, no puede haber
                // bienes registrados en un año inferior a éste
                if(tAño < 1996){
                    // Si es así, es decir, si introdujeron un año inferior al 1996 se mostrará este mensaje y no se permitirá continuar
                    JOptionPane.showMessageDialog(rootPane, "No se acepta un año tan atrás", "Mensaje", JOptionPane.ERROR_MESSAGE);
                }
                // Si no es así, entonces podemos proceder con las demás validaciones
                else{
                    // Inicializamos una variable de la clase Selecciones del paquete ConexionDB
                    // en la cual se encuentran los métodos necesarios para las consultas a la base de datos
            Selecciones s = new Selecciones();
            // Cada clave CABM y No. de serie es único e irrepetible, y la base de datos está programada para que
            // estos datos sean únicos, pero para evitar excepciones de tipo SQL a causa de la entrada de valores iguales
            // se ejecutarán dos algoritmos para evitar estos errores o posibles ambiguedades
            // Se inicializarn las variables "VNR1" y "VNR2" que servirán para verificar si el usuario introdujo o no
            // una clave CABM o No. de serie existente
            boolean VNR1=false, VNR2=false;
                // Si se introdujo clave CABM
            if(!claveCABM.isEmpty()){
                try {
                    // Se invica el método VNclaveCABM(), que servirá para verificar si ya se encuentra registrada la clave cabm
                    // que ingresó el usuario
                    VNR1 = s.VNclaveCABM(claveCABM);
                } catch (SQLException ex) {
                    Logger.getLogger(Pinventarios.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(!noSerie.equals("sin serie")){
                try {
                    // Se invica el método VNnoSerie(), que servirá para verificar si ya se encuentra registrado el número de serie
                    // que ingresó el usuario
                    VNR2 = s.VNnoSerie(noSerie);
                } catch (SQLException ex) {
                    Logger.getLogger(Pinventarios.class.getName()).log(Level.SEVERE, null, ex);
                }
                        // Si el valor obtenido o resultado de VNR1 (verificación 1) es diferente de falso, es decir, es verdadero o "true"...
                        if(VNR1 != false){
                            // Significa que la clave cabm introducida ya existe, por lo que se le notifica al usuario 
                            // y no se le permite continuar hasta que corriga 
                            JOptionPane.showMessageDialog(this, "La clave CABM: '" + claveCABM + "', ya se encuentra registrada en el sistema", 
                                    "Clave CABM registrada previamente", JOptionPane.INFORMATION_MESSAGE);
                        }
                        // De lo contrario, si no existe una clave cabm igual a la ingresada proseguimos con la siguiente validación
                        else{
                            // Si el resultado de VNR2 es "true", significa que ya existe un número de serie igual al ingresado por lo que...
                            if(VNR2 != false){
                                // Se le notifica al usuario y no se le permite continuar hasta que corrija
                                JOptionPane.showMessageDialog(this, "El número de serie valio queso: '" + noSerie + "', ya se encuentra registrado en el sistema", 
                                    "Número de serie registrado previamente", JOptionPane.INFORMATION_MESSAGE);
                            }
                            // De lo contrario, si la clave cabm y no. de serie ingresados aún no existen
                            // se procede a los metodos necesarios para almacenar los datos
                            else{
                                // Se inicializa la variable "f" para manipular la fecha ingresada, así como la actual
                                Fecha f = new Fecha();
            // Se llama al método "Fecha()", de la clase para 'cachar" el año, mes y día que el usuario haya ingresado
            // y que estos datos se puedan manipular con formato "DATE" en 
            fechaAsig = f.Fecha(año, mes, dia);
            // Obtenemos lo que el usuario haya escrito en los campos numéricos
            valor = Double.parseDouble(campoValor.getText());
            nivel = Integer.parseInt(campoNivel.getText());
            local = Integer.parseInt(campoLocal.getText());
            
            // Inicializamos la clase que nos servirá para almacenar los datos
            Inserciones i = new Inserciones();
            // Llamamos al método apropiado y le pasamos los valores
            // Si todo sale bien el verificador va a tomar el valor "true"
            verificador = i.RegistrarBien(descripcion, marca, modelo, claveCABM,
                    noSerie, valor, persona, edificio, departamento, nivel, 
                    local, fechaAsig, detalles);
            
            // Aquí se verifica si esta condición se cumple
            if(verificador == true){
                
                // Inicializamos un Arraylist para los bienes
                ArrayList bienes = new ArrayList();
                // Indicamos el comando SQL que se le va a pasar a MySQL para que nos regrese los datos de los bienes
         String sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE nse_ben = '" + noSerie + "'";
            try {
                etiquetaFecha.setText("Fecha de asignación:");
                // Iniciamos el método "getBien()", de la clase selecciones y le pasamos el comando SQL mencionado anteriormente
                // Si todo sale bien los datos de los bienes se guardarán en el arraylist "bienes"
                bienes = s.getBien(sql);
                // Le pasamos el arraylist a la tabla, que es donde se visualizarán éstos
                tablab.setModel(new FormatoTablaBien(bienes));
                botonActualizar.setVisible(true);
                botonBaja.setVisible(true);
                etiquetaAvisoBajas.setVisible(false);
                // Informamos que el bien se ha almacenado exitosamente
                JOptionPane.showMessageDialog(rootPane, "El bien se ha registrado exitosamente en el sistema", "¡Éxito!", JOptionPane.INFORMATION_MESSAGE);
        // Limpiamos los campos
        campoDescripcion.setText("");
        campoMarca.setText("");
        campoModelo.setText("");
        campoClaveCABM.setText("");
        campoNoserie.setText("");
        campoValor.setText("");
        campoPersona.setText("");
        campoDepartamento.setText("");
        campoNivel.setText("");
        campoLocal.setText("");
        campoAño.setText("");
        campoDetalles.setText("");
        listaMes.setSelectedIndex(0);
        listaDia.setSelectedIndex(0);
            } catch (SQLException ex) {
                Logger.getLogger(Pinventarios.class.getName()).log(Level.SEVERE, null, ex);
            }
                
                            }
            // De lo contrario..., esto significa que el valor del verificador resultó "false" y por lo tanto
            // no se almacenó, por lo que informamos la situación
            else {
                JOptionPane.showMessageDialog(rootPane, 
                        "El bien no se ha agregado correctamente en el sistema,\nsi persiste el inconveniente contácte al desarrollador de la aplicación", 
                                                            "Error", JOptionPane.INFORMATION_MESSAGE);
            }
                        // Esto indica que se cierra el "else" de la verificación de No. de serie
                        // Lo que significa que no existía aún un nO de serie igual al que ingresamos
                        }
            }
                        // Este "else", significa que en el campo "noSerie" introdujeron el texto "sin serie"
            // y es necesario apartar este caso porque si los dejaramos juntos, el sistema
            // buscaría si hay bienes a los que también se les dejó vacios y como seguramente será así, entonces
            // mostrará un mensaje de "El número de serie ingresado ya fue registrado", el cual por lógica es incorrecto
                } else{
                    Fecha f = new Fecha();
            fechaAsig = f.Fecha(año, mes, dia);
            valor = Double.parseDouble(campoValor.getText());
            nivel = Integer.parseInt(campoNivel.getText());
            local = Integer.parseInt(campoLocal.getText());
            
            Inserciones i = new Inserciones();
            // Pasamos los valores para que se almacenen
            verificador = i.RegistrarBien(descripcion, marca, modelo, claveCABM,
                    noSerie, valor, persona, edificio, departamento, nivel, 
                    local, fechaAsig, detalles);
        
            // Si los datos se almacenaron correctamente
            if(verificador == true){
                ArrayList bienes = new ArrayList();
                // Comando SQL
         String sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE nse_ben = '" + noSerie + "'";
            try {
                etiquetaFecha.setText("Fecha de asignación:");
                bienes = s.getBien(sql);
                // Le pasamos los datos a la tabla
                tablab.setModel(new FormatoTablaBien(bienes));
                botonActualizar.setVisible(true);
                botonBaja.setVisible(true);
                etiquetaAvisoBajas.setVisible(false);
                JOptionPane.showMessageDialog(rootPane, "El bien se ha registrado exitosamente en el sistema", "¡Éxito!", JOptionPane.INFORMATION_MESSAGE);
                    campoDescripcion.setText("");
                    campoMarca.setText("");
                    campoModelo.setText("");
                    campoClaveCABM.setText("");
                    campoNoserie.setText("");
                    campoValor.setText("");
                    campoPersona.setText("");
                    campoDepartamento.setText("");
                    campoNivel.setText("");
                    campoLocal.setText("");
                    campoAño.setText("");
                    campoDetalles.setText("");
                    listaMes.setSelectedIndex(0);
                    listaDia.setSelectedIndex(0);
            } catch (SQLException ex) {
                Logger.getLogger(Pinventarios.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
            // El bien no se almacenó
            else {
                JOptionPane.showMessageDialog(rootPane, 
                        "El bien no se ha agregado correctamente en el sistema,\nsi persiste el inconveniente contácte al desarrollador de la aplicación",
                                "Error", JOptionPane.INFORMATION_MESSAGE);
            }
                }
            }
            // Este "else", significa que el campo "claveCABM" lo dejaron vacio
            // y es necesario apartar este caso porque si los dejaramos juntos, el sistema
            // buscaría si hay bienes a los que también se les dejó vacios y como seguramente será así, entonces
            // mostrará un mensaje de "La clave CABM ingresada ya fue registrada", el cual por lógica es incorrecto
            else{
                if(!noSerie.equals("sin serie")){
                try {
                    // Hacemos la comprobación del No de serie
                    VNR2 = s.VNnoSerie(noSerie);
                } catch (SQLException ex) {
                    Logger.getLogger(Pinventarios.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(VNR2 != false){
                    // Si el No de serie ya se encuentra registrado
                                JOptionPane.showMessageDialog(this, "El número de serie: '" + noSerie + "', ya se encuentra registrado en el sistema", 
                                    "Número de serie registrado previamente", JOptionPane.INFORMATION_MESSAGE);
                            }
                else {
            Fecha f = new Fecha();
            fechaAsig = f.Fecha(año, mes, dia);
            valor = Double.parseDouble(campoValor.getText());
            nivel = Integer.parseInt(campoNivel.getText());
            local = Integer.parseInt(campoLocal.getText());
            
            Inserciones i = new Inserciones();
            // Pasamos los valores para que se almacenen
            verificador = i.RegistrarBien(descripcion, marca, modelo, claveCABM,
                    noSerie, valor, persona, edificio, departamento, nivel, 
                    local, fechaAsig, detalles);
        
            // Si los datos se almacenaron correctamente
            if(verificador == true){
                ArrayList bienes = new ArrayList();
                // Comando SQL
         String sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE nse_ben = '" + noSerie + "'";
            try {
                etiquetaFecha.setText("Fecha de asignación:");
                bienes = s.getBien(sql);
                // Le pasamos los datos a la tabla
                tablab.setModel(new FormatoTablaBien(bienes));
                botonActualizar.setVisible(true);
                botonBaja.setVisible(true);
                etiquetaAvisoBajas.setVisible(false);
                JOptionPane.showMessageDialog(rootPane, "El bien se ha registrado exitosamente en el sistema", "¡Éxito!", JOptionPane.INFORMATION_MESSAGE);
        campoDescripcion.setText("");
        campoMarca.setText("");
        campoModelo.setText("");
        campoClaveCABM.setText("");
        campoNoserie.setText("");
        campoValor.setText("");
        campoPersona.setText("");
        campoDepartamento.setText("");
        campoNivel.setText("");
        campoLocal.setText("");
        campoAño.setText("");
        campoDetalles.setText("");
        listaMes.setSelectedIndex(0);
        listaDia.setSelectedIndex(0);
            } catch (SQLException ex) {
                Logger.getLogger(Pinventarios.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
            // El bien no se almacenó
            else {
                JOptionPane.showMessageDialog(rootPane, 
                        "El bien no se ha agregado correctamente en el sistema,\nsi persiste el inconveniente contácte al desarrollador de la aplicación",
                                "Error", JOptionPane.INFORMATION_MESSAGE);
            }
                }
              } else{
                    Fecha f = new Fecha();
            fechaAsig = f.Fecha(año, mes, dia);
            valor = Double.parseDouble(campoValor.getText());
            nivel = Integer.parseInt(campoNivel.getText());
            local = Integer.parseInt(campoLocal.getText());
            
            Inserciones i = new Inserciones();
            // Pasamos los valores para que se almacenen
            verificador = i.RegistrarBien(descripcion, marca, modelo, claveCABM,
                    noSerie, valor, persona, edificio, departamento, nivel, 
                    local, fechaAsig, detalles);
        
            // Si los datos se almacenaron correctamente
            if(verificador == true){
                ArrayList bienes = new ArrayList();
                // Comando SQL
         String sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes WHERE nse_ben = '" + noSerie + "'";
            try {
                etiquetaFecha.setText("Fecha de asignación:");
                bienes = s.getBien(sql);
                // Le pasamos los datos a la tabla
                tablab.setModel(new FormatoTablaBien(bienes));
                botonActualizar.setVisible(true);
                botonBaja.setVisible(true);
                etiquetaAvisoBajas.setVisible(false);
                JOptionPane.showMessageDialog(rootPane, "El bien se ha registrado exitosamente en el sistema", "¡Éxito!", JOptionPane.INFORMATION_MESSAGE);
                    campoDescripcion.setText("");
                    campoMarca.setText("");
                    campoModelo.setText("");
                    campoClaveCABM.setText("");
                    campoNoserie.setText("");
                    campoValor.setText("");
                    campoPersona.setText("");
                    campoDepartamento.setText("");
                    campoNivel.setText("");
                    campoLocal.setText("");
                    campoAño.setText("");
                    campoDetalles.setText("");
                    listaMes.setSelectedIndex(0);
                    listaDia.setSelectedIndex(0);
            } catch (SQLException ex) {
                Logger.getLogger(Pinventarios.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
            // El bien no se almacenó
            else {
                JOptionPane.showMessageDialog(rootPane, 
                        "El bien no se ha agregado correctamente en el sistema,\nsi persiste el inconveniente contácte al desarrollador de la aplicación",
                                "Error", JOptionPane.INFORMATION_MESSAGE);
            }
                }
            }
          }
        }
        
        
    }//GEN-LAST:event_botonRegistrarActionPerformed

    /*
    * Aquí se inicia el algoritmo de búsqueda avanzada, o detallada
    * que es cuando el usuario presiona el botón de buscar
    */
    private void botonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBuscarActionPerformed
        // Cachamos los valores String
        descripcion = campoDescripcion.getText();    
        
        marca = campoMarca.getText();       
        
        modelo = campoModelo.getText();    
        
        claveCABM = campoClaveCABM.getText(); 
        
        noSerie = campoNoserie.getText();   
        
        persona = campoPersona.getText();              
        
        edificio = campoEdificio.getText();          
        
        departamento = campoDepartamento.getText();
        
        // Los datos tipo Integer y Double no pasan si se dejaron vacios los campos
        // por lo que primero detectamos si fueron llenados
        if(!this.campoNivel.getText().isEmpty()){
            // Si fue así "cachamos" lo que se haya introducido
            nivel = Integer.parseInt(campoNivel.getText());
        } else{
            // De lo contrario de damos el valor de cero
            nivel = 0;
        } // Lo mismo para todos los demás
        
        if(!this.campoLocal.getText().isEmpty()){
            local = Integer.parseInt(campoLocal.getText());
        } else{
            local = 0;
        }
        
        if(!this.campoValor.getText().isEmpty()){
            valor = Double.parseDouble(campoValor.getText());
        } else{
            valor = 0.0;
        }
        
        // Si el campo año tiene este texto...
        if(this.campoAño.getText().equals("Año")){
            // borrarlo y aisgnarle un valor vacio, ya que significa que no escribieron nada en él
            campoAño.setText("");
            año = "";
        } else{
            año = campoAño.getText();
        }
        
        if(this.listaMes.getSelectedIndex() != 0){
            mes = this.listaMes.getSelectedIndex();
        } else{
            mes = 0;
        }
        
        if(this.listaDia.getSelectedIndex() != 0){
            dia = this.listaDia.getSelectedIndex();
        } else{
            dia = 0;
        }
        
        detalles = campoDetalles.getText();
        
        // Si ningún campo se llenó y por lógica sólo el de Edificio tiene valor (porque éste no se puede editar)
        if(descripcion.isEmpty() & marca.isEmpty() & modelo.isEmpty() & claveCABM.isEmpty() 
                & noSerie.isEmpty() & persona.isEmpty() & !edificio.isEmpty() & detalles.isEmpty() &
                departamento.isEmpty() & nivel==0 & local==0 & valor==0.0 & año.isEmpty() & mes==0 & dia==0){
                // Le damos un valor vacio a la fecha
                fechaAsig = "";
                // Indicamos el comando SQL para la consulta de todos los bienes
         String sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes";
         ArrayList bienes = new ArrayList();
         Selecciones s = new Selecciones();
                try {
                    // Obtenemos los datos de la DB y los ponemos un arraylist
                    bienes = s.getBien(sql);
                } catch (SQLException ex) {
                    Logger.getLogger(Pinventarios.class.getName()).log(Level.SEVERE, null, ex);
                }
                // Determinamos el tamaño del arraylist, esto nos servirá para saber cuántos resultados
                // coinciden con la búsqueda
                int tArrayList = bienes.size();
                // Si existe más de un bien coincidente con la búsqueda
                if(tArrayList != 0 & tArrayList > 1){
                    // Le pasamos el arraylist a la tabla
                tablab.setModel(new FormatoTablaBien(bienes));
                tablab.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                etiquetaFecha.setText("Fecha de asignación:");
                botonActualizar.setVisible(true);
                botonBaja.setVisible(true);
                botonActualizar.setEnabled(false);
                botonBaja.setEnabled(false);
                etiquetaAvisoBajas.setVisible(false);
                // Informamos al usuario cuántos bienes resultaron coincidentes con la búsqueda
                JOptionPane.showMessageDialog(this, "Se encontraron " + tArrayList + " registrados",
                                "¡Éxito!", JOptionPane.INFORMATION_MESSAGE);
                // Le preguntamos al usuario si desea generar un inventario o si sólo quiere dar de baja, modificar o visualizar
                // los datos de algún bien desde la tabla
                int conf = JOptionPane.showConfirmDialog(this, "Está apunto de generar un inventario en Microsoft Excel " +
                                "con todos los bienes del CEC Morelia, ¿desea continuar?\nSi sólo desea actualizar o dar de baja algún bien seleccione la opción de 'cancelar'", 
                                "¿Desea continuar?", JOptionPane.OK_CANCEL_OPTION);
                             // Si el usuario confirma que sí quiere generar un inventario...
                             if(conf == 0){
                                     // Se invoca la clase necesaria para generar los inventarios
                                     GeneradorExcel e = new GeneradorExcel();
                                     // Le pasamos el arraylist al método "GenerarInventarioE()" para que
                                     // los valores del arraylist se introduzcan en él
                                     // Si todo sale bien el verificador debe obtener un valor "true"
                                     boolean verificador = e.GenerarInventarioE(bienes);
                                     // verificamos que ésto sea así
                                     if(verificador != false){
                                         // Esta variable servirá para obtener el nombre del sistema operativo
                                        // ya que la ruta donde se guardarán los inventarios puede cambiar dependiendo si usamos Windows XP, 7 u 8
                                        String osName = System.getProperty("os.name");
                                        String mensajeConf=null;
                                        // Si el sistema operativo donde se está usando el sistema es Windows XP...
                                            if(osName.equals("Windows XP")){
                                                // La ruta donde se guardarán los datos será la siguiente:
                                                mensajeConf = "El inventario solicitado se generó correctamente en Microsoft Excel\n"+
                                                                "Busque en la carpeta Mis Docupentos del usuario Admin";
                                            }
                                            // De lo contrario, si..., el sistema operativo es Windows Vista, 7, 8, 8.1 o 10
                                            else if(osName.equals("Windows Vista") | osName.equals("Windows 7") | osName.equals("Windows 8") | osName.equals("Windows 8.1") | 
                                                                                                                                                        osName.equals("Windows 10")){
                                                // La ruta será la siguiente:                                                                                                  
                                                mensajeConf = "El inventario solicitado se generó correctamente en Microsoft Excel\n" +
                                                                    "Busque en la carpeta Docupentos Públicos. Dirección de la ubicación: C:\\Users\\Public\\Documents\\";
                                            }
                                         // Si es así informamos que se generó exitosamente en el sistema operativo correspondiente
                                         JOptionPane.showMessageDialog(this, mensajeConf, "¡Éxito!", JOptionPane.INFORMATION_MESSAGE);
                                     }
                                     // Si no fue así se le informará que el inventario no se pudo generar
                                     else{
                                         JOptionPane.showMessageDialog(this, "El inventario no se pudo generar por causas desconocidas",
                                                 "No se pudo generar el inventario", JOptionPane.ERROR_MESSAGE);
                                     }
                             }    
                    }
                    // De lo contrario, si..., sólo se encontró un valor conincidente con la búsqueda...
                    else if(tArrayList == 1){
                        // le pasamos el arraylist a la tabla
                tablab.setModel(new FormatoTablaBien(bienes));
                etiquetaFecha.setText("Fecha de asignación:");
                botonActualizar.setVisible(true);
                botonBaja.setVisible(true);
                botonActualizar.setEnabled(false);
                botonBaja.setEnabled(false);
                etiquetaAvisoBajas.setVisible(false);
                        // informamos la situación
                        JOptionPane.showMessageDialog(this, "Sólo se encontró 1 bien registrado",
                                "¡Éxito!", JOptionPane.INFORMATION_MESSAGE);
                        // Ahora más que antes, es importante preguntar si de verdad se desea generar un inventario
                        int conf = JOptionPane.showConfirmDialog(this, "Está apunto de generar un inventario en Microsoft Excel " +
                                "con todos los bienes del CEC Morelia, ¿desea continuar?\nSi sólo desea actualizar o dar de baja algún bien seleccione la opción de 'cancelar'", 
                                "¿Desea continuar?", JOptionPane.OK_CANCEL_OPTION);
                             // Si el usuario lo confirma...
                             if(conf == 0){
                                     GeneradorExcel e = new GeneradorExcel();
                                     // Mandamos los valores al al método correspondiente para que se genere el inventario
                                     boolean verificador = e.GenerarInventarioE(bienes);
                                     // Si se generó correctamente...
                                     if(verificador != false){
                                         // Esta variable servirá para obtener el nombre del sistema operativo
                                        // ya que la ruta donde se guardarán los inventarios puede cambiar dependiendo si usamos Windows XP, 7 u 8
                                        String osName = System.getProperty("os.name");
                                        String mensajeConf=null;
                                        // Si el sistema operativo donde se está usando el sistema es Windows XP...
                                            if(osName.equals("Windows XP")){
                                                // La ruta donde se guardarán los datos será la siguiente:
                                                mensajeConf = "El inventario solicitado se generó correctamente en Microsoft Excel\n"+
                                                                "Busque en la carpeta Mis Docupentos del usuario Admin";
                                            }
                                            // De lo contrario, si..., el sistema operativo es Windows Vista, 7, 8, 8.1 o 10
                                            else if(osName.equals("Windows Vista") | osName.equals("Windows 7") | osName.equals("Windows 8") | osName.equals("Windows 8.1") | 
                                                                                                                                                        osName.equals("Windows 10")){
                                                // La ruta será la siguiente:                                                                                                  
                                                mensajeConf = "El inventario solicitado se generó correctamente en Microsoft Excel\n" +
                                                                    "Busque en la carpeta Docupentos Públicos. Dirección de la ubicación: C:\\Users\\Public\\Documents\\";
                                            }
                                         // Si es así informamos que se generó exitosamente en el sistema operativo correspondiente
                                         JOptionPane.showMessageDialog(this, mensajeConf, "¡Éxito!", JOptionPane.INFORMATION_MESSAGE);
                                     }
                                     // Si no se pudo generar
                                     else{
                                         JOptionPane.showMessageDialog(this, "El inventario no se pudo generar por causas desconocidas",
                                                 "No se pudo generar el inventario", JOptionPane.ERROR_MESSAGE);
                                     }
                             }    
                    }
                    // De lo contrario..., si no se encontraron registros coincidentes con la búsqueda...
                    else{
                        JOptionPane.showMessageDialog(this, "No se encontraron registros",
                                "No se encontraron registros", JOptionPane.INFORMATION_MESSAGE);
                    }
        }
        
        
// Este "else" significa que el usuario llenó ciertos campos (sin contar el de Edificio)
        else{
            // Por ahora, si se quiere buscar los bienes que se asignaron en tal fecha, 
            // el sistema sólo puede buscar los registros coincidentes con el año, no con la fecha completa
            // Si se introdujo sólo el año en la fecha
            if(!año.isEmpty() & this.listaDia.getSelectedIndex() == 0 & this.listaMes.getSelectedIndex() == 0){
         // A la variable de damos el valor del año
         fechaAsig = año;
         // Inicializamos la clase BselectBA del paquete Interacciones, misma que contiene un método que permite determinar cuáles
         // campos fueron llenados y en base a ello será el comando SQL que se le mandará al gestor
         //  de esta forma nos mostrarán únicamente los datos que queremos consultar
         BselectBA ba1 = new BselectBA();
         // Le mandamos al método los valores
         // y obtenemos el valor del comando SQL
        String sql = ba1.busqASelcSQL(descripcion, marca, modelo, claveCABM, noSerie,
                valor, persona, edificio, departamento, nivel, local, fechaAsig, detalles);
        // Si el usuario introdujo una combinación de campos llenados no prevista, la variable tomará el valor: "Especificación no registrada"
        // en dado caso se le pedirá al usuario que no se puede realizar esa búsqueda, que ingrese menos datos
        if(sql.equals("Especificación no registrada")){
            JOptionPane.showMessageDialog(rootPane, "Su búsqueda no se puede realizar, ingrese menos datos y/o contacte al desarrolador de la aplicación",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        // De lo contrario..., si todo salió bien en la búsqueda de un comando apropiado...
        else{
         ArrayList bienes = new ArrayList();
         Selecciones s = new Selecciones();
                try {
                    // Obtenemos los datos de la DB y los ponemos un arraylist
                    bienes = s.getBien(sql);
                } catch (SQLException ex) {
                    Logger.getLogger(Pinventarios.class.getName()).log(Level.SEVERE, null, ex);
                }
                //  determinamos el tamaño del arraylist
                int tArrayList = bienes.size();
                // si tenemos más de un resultado
                if(tArrayList != 0 & tArrayList > 1){
                    // le pasamos el arraylist a la tabla
                tablab.setModel(new FormatoTablaBien(bienes));
                etiquetaFecha.setText("Fecha de asignación:");
                botonActualizar.setVisible(true);
                botonBaja.setVisible(true);
                botonActualizar.setEnabled(false);
                botonBaja.setEnabled(false);
                etiquetaAvisoBajas.setVisible(false);
                // Le informamos al usuario cuántos registros coincidieron con la búsqueda
                JOptionPane.showMessageDialog(this, "Se encontraron " + tArrayList + " bienes coincidentes con su búsqueda",
                                "¡Éxito!", JOptionPane.INFORMATION_MESSAGE);
                // Le preguntamos si desea generar un inventario en Excel
                int conf = JOptionPane.showConfirmDialog(this, "Está apunto de generar un inventario en Microsoft Excel, ¿desea continuar?" +
                "\nSi sólo desea actualizar o dar de baja algún bien seleccione la opción de 'cancelar'",
                        "¿Desea continuar?", JOptionPane.OK_CANCEL_OPTION);
                             if(conf == 0){
                                 // Si es así entonces proseguimos con lo siguiente...
                                     GeneradorExcel e = new GeneradorExcel();
                                     // le pasamos esos valores al método correspondiente 
                                     // para que se introduzcan en él los datos
                                     boolean verificador = e.GenerarInventarioE(bienes);
                                     // Si el inventario se generó correctamente...
                                     if(verificador != false){
                                         // Esta variable servirá para obtener el nombre del sistema operativo
                                        // ya que la ruta donde se guardarán los inventarios puede cambiar dependiendo si usamos Windows XP, 7 u 8
                                        String osName = System.getProperty("os.name");
                                        String mensajeConf=null;
                                        // Si el sistema operativo donde se está usando el sistema es Windows XP...
                                            if(osName.equals("Windows XP")){
                                                // La ruta donde se guardarán los datos será la siguiente:
                                                mensajeConf = "El inventario solicitado se generó correctamente en Microsoft Excel\n"+
                                                                "Busque en la carpeta Mis Docupentos del usuario Admin";
                                            }
                                            // De lo contrario, si..., el sistema operativo es Windows Vista, 7, 8, 8.1 o 10
                                            else if(osName.equals("Windows Vista") | osName.equals("Windows 7") | osName.equals("Windows 8") | osName.equals("Windows 8.1") | 
                                                                                                                                                        osName.equals("Windows 10")){
                                                // La ruta será la siguiente:                                                                                                  
                                                mensajeConf = "El inventario solicitado se generó correctamente en Microsoft Excel\n" +
                                                                    "Busque en la carpeta Docupentos Públicos. Dirección de la ubicación: C:\\Users\\Public\\Documents\\";
                                            }
                                         // Si es así informamos que se generó exitosamente en el sistema operativo correspondiente
                                         JOptionPane.showMessageDialog(this, mensajeConf, "¡Éxito!", JOptionPane.INFORMATION_MESSAGE);
                                     }
                                     // De lo contrario, mostramos un mensaje que diga que no se pudo generar el inventario
                                     else{
                                         JOptionPane.showMessageDialog(this, "El inventario no se pudo generar por causas desconocidas",
                                                 "No se pudo generar el inventario", JOptionPane.ERROR_MESSAGE);
                                     }
                             }    
                    }
                    // De lo contrario, si..., sólo se encontró un bien coincidente con la búsqueda
                    else if(tArrayList == 1){
                // Le pasamos el arraylist a la tabla
                tablab.setModel(new FormatoTablaBien(bienes));
                etiquetaFecha.setText("Fecha de asignación:");
                botonActualizar.setVisible(true);
                botonBaja.setVisible(true);
                botonActualizar.setEnabled(false);
                botonBaja.setEnabled(false);
                etiquetaAvisoBajas.setVisible(false);
                        // Informamos la situación
                        JOptionPane.showMessageDialog(this, "Sólo se encontró 1 bien coincidente con su búsqueda",
                                "¡Éxito!", JOptionPane.INFORMATION_MESSAGE);
                        // Le preguntammos si desea generar un inventario
                        int conf = JOptionPane.showConfirmDialog(this, "Está apunto de generar un inventario en Microsoft Excel, ¿desea continuar?" +
                "\nSi sólo desea actualizar o dar de baja algún bien seleccione la opción de 'cancelar'",
                        "¿Desea continuar?", JOptionPane.OK_CANCEL_OPTION);
                            // Si lo confirma...
                             if(conf == 0){
                                     GeneradorExcel e = new GeneradorExcel();
                                     // Le pasamos el arraylist al método para generar el inventario solicitado
                                     boolean verificador = e.GenerarInventarioE(bienes);
                                     // Si el inventario se generó correctamente...
                                     if(verificador != false){
                                         // Esta variable servirá para obtener el nombre del sistema operativo
                                        // ya que la ruta donde se guardarán los inventarios puede cambiar dependiendo si usamos Windows XP, 7 u 8
                                        String osName = System.getProperty("os.name");
                                        String mensajeConf=null;
                                        // Si el sistema operativo donde se está usando el sistema es Windows XP...
                                            if(osName.equals("Windows XP")){
                                                // La ruta donde se guardarán los datos será la siguiente:
                                                mensajeConf = "El inventario solicitado se generó correctamente en Microsoft Excel\n"+
                                                                "Busque en la carpeta Mis Docupentos del usuario Admin";
                                            }
                                            // De lo contrario, si..., el sistema operativo es Windows Vista, 7, 8, 8.1 o 10
                                            else if(osName.equals("Windows Vista") | osName.equals("Windows 7") | osName.equals("Windows 8") | osName.equals("Windows 8.1") | 
                                                                                                                                                        osName.equals("Windows 10")){
                                                // La ruta será la siguiente:                                                                                                  
                                                mensajeConf = "El inventario solicitado se generó correctamente en Microsoft Excel\n" +
                                                                    "Busque en la carpeta Docupentos Públicos. Dirección de la ubicación: C:\\Users\\Public\\Documents\\";
                                            }
                                         // Si es así informamos que se generó exitosamente en el sistema operativo correspondiente
                                         JOptionPane.showMessageDialog(this, mensajeConf, "¡Éxito!", JOptionPane.INFORMATION_MESSAGE);
                                     }
                                     // si no se pudo generar...
                                     else{
                                         JOptionPane.showMessageDialog(this, "El inventario no se pudo generar por causas desconocidas",
                                                 "No se pudo generar el inventario", JOptionPane.ERROR_MESSAGE);
                                     }
                             }    
                    }
                    // De lo contrario..., si no se encontraron registro coincidentes con la búsqueda inofrmamos al usuario
                    else{
                        JOptionPane.showMessageDialog(this, "No se encontraron registros coincidentes con su búsqueda",
                                "No se encontraron registros", JOptionPane.INFORMATION_MESSAGE);
                    }
                }       
            } // Se terminan las especificaciones para el caso de que se haya especificado fecha en la búsqueda
            
            // Si no se especificó fecha en la búsqueda...
            else if(año.isEmpty() & this.listaDia.getSelectedIndex() == 0 & this.listaMes.getSelectedIndex() == 0){
                fechaAsig = "";
         BselectBA ba1 = new BselectBA();
         // Le pasamos los valores al método correspondiente para determinar el comando SQL apropiado a la consulta solicitada
        String sql = ba1.busqASelcSQL(descripcion, marca, modelo, claveCABM, noSerie,
                valor, persona, edificio, departamento, nivel, local, fechaAsig, detalles);
        // Si el usuario introdujo una combinación de campos llenados no prevista, la variable tomará el valor: "Especificación no registrada"
        // en dado caso se le pedirá al usuario que no se puede realizar esa búsqueda, que ingrese menos datos
        if(sql.equals("Especificación no registrada")){
            JOptionPane.showMessageDialog(rootPane, "Su búsqueda no se puede realizar, ingrese menos datos y/o contacte al desarrolador de la aplicación",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        // De lo contrario..., si todo salió bien en la búsqueda de un comando apropiado...
        else{
         ArrayList bienes = new ArrayList();
         Selecciones s = new Selecciones();
                try {
                    // Realizamos la consulta SQL y le pasamos los resultados al arraylist
                    bienes = s.getBien(sql);
                } catch (SQLException ex) {
                    Logger.getLogger(Pinventarios.class.getName()).log(Level.SEVERE, null, ex);
                }
                // Medimos el arraylist
                int tArrayList = bienes.size();
                // Si se encontró más de un registro coincidente
                if(tArrayList != 0 & tArrayList > 1){
                    // Le pasamos el arraylist a la tabla
                tablab.setModel(new FormatoTablaBien(bienes));
                etiquetaFecha.setText("Fecha de asignación:");
                botonActualizar.setVisible(true);
                botonBaja.setVisible(true);
                botonActualizar.setEnabled(false);
                botonBaja.setEnabled(false);
                etiquetaAvisoBajas.setVisible(false);
                // informamos cuántos registros coincidieron con la búsqueda
                JOptionPane.showMessageDialog(this, "Se encontraron " + tArrayList + " bienes coincidentes con su búsqueda",
                                "¡Éxito!", JOptionPane.INFORMATION_MESSAGE);
                // Preguntamos si desea generar un inventario
                int conf = JOptionPane.showConfirmDialog(this, "Está apunto de generar un inventario en Microsoft Excel, ¿desea continuar?" +
                "\nSi sólo desea actualizar o dar de baja algún bien seleccione la opción de 'cancelar'",
                        "¿Desea continuar?", JOptionPane.OK_CANCEL_OPTION);
                            // Si se confirma...
                             if(conf == 0){
                                     GeneradorExcel e = new GeneradorExcel();
                                     // le pasamos el arraylist al método para generar el inventario
                                     boolean verificador = e.GenerarInventarioE(bienes);
                                     // Si se generó correctamente el inventario
                                     if(verificador != false){
                                         // Esta variable servirá para obtener el nombre del sistema operativo
                                        // ya que la ruta donde se guardarán los inventarios puede cambiar dependiendo si usamos Windows XP, 7 u 8
                                        String osName = System.getProperty("os.name");
                                        String mensajeConf=null;
                                        // Si el sistema operativo donde se está usando el sistema es Windows XP...
                                            if(osName.equals("Windows XP")){
                                                // La ruta donde se guardarán los datos será la siguiente:
                                                mensajeConf = "El inventario solicitado se generó correctamente en Microsoft Excel\n"+
                                                                "Busque en la carpeta Mis Docupentos del usuario Admin";
                                            }
                                            // De lo contrario, si..., el sistema operativo es Windows Vista, 7, 8, 8.1 o 10
                                            else if(osName.equals("Windows Vista") | osName.equals("Windows 7") | osName.equals("Windows 8") | osName.equals("Windows 8.1") | 
                                                                                                                                                        osName.equals("Windows 10")){
                                                // La ruta será la siguiente:                                                                                                  
                                                mensajeConf = "El inventario solicitado se generó correctamente en Microsoft Excel\n" +
                                                                    "Busque en la carpeta Docupentos Públicos. Dirección de la ubicación: C:\\Users\\Public\\Documents\\";
                                            }
                                         // Si es así informamos que se generó exitosamente en el sistema operativo correspondiente
                                         JOptionPane.showMessageDialog(this, mensajeConf, "¡Éxito!", JOptionPane.INFORMATION_MESSAGE);
                                     }
                                     // Si no se pudo generar
                                     else{
                                         JOptionPane.showMessageDialog(this, "El inventario no se pudo generar por causas desconocidas",
                                                 "No se pudo generar el inventario", JOptionPane.ERROR_MESSAGE);
                                     }
                             }    
                    }
                    // De lo contrario, si..., sólo se encontró un bien coincidente...
                    else if(tArrayList == 1){
                tablab.setModel(new FormatoTablaBien(bienes));
                etiquetaFecha.setText("Fecha de asignación:");
                botonActualizar.setVisible(true);
                botonBaja.setVisible(true);
                botonActualizar.setEnabled(false);
                botonBaja.setEnabled(false);
                etiquetaAvisoBajas.setVisible(false);
                        JOptionPane.showMessageDialog(this, "Sólo se encontró 1 bien coincidente con su búsqueda",
                                "¡Éxito!", JOptionPane.INFORMATION_MESSAGE);
                        int conf = JOptionPane.showConfirmDialog(this, "Está apunto de generar un inventario en Microsoft Excel, ¿desea continuar?" +
                "\nSi sólo desea actualizar o dar de baja algún bien seleccione la opción de 'cancelar'",
                        "¿Desea continuar?", JOptionPane.OK_CANCEL_OPTION);
                             if(conf == 0){
                                     GeneradorExcel e = new GeneradorExcel();
                                     boolean verificador = e.GenerarInventarioE(bienes);
                                     if(verificador != false){
                                         // Esta variable servirá para obtener el nombre del sistema operativo
                                        // ya que la ruta donde se guardarán los inventarios puede cambiar dependiendo si usamos Windows XP, 7 u 8
                                        String osName = System.getProperty("os.name");
                                        String mensajeConf=null;
                                        // Si el sistema operativo donde se está usando el sistema es Windows XP...
                                            if(osName.equals("Windows XP")){
                                                // La ruta donde se guardarán los datos será la siguiente:
                                                mensajeConf = "El inventario solicitado se generó correctamente en Microsoft Excel\n"+
                                                                "Busque en la carpeta Mis Docupentos del usuario Admin";
                                            }
                                            // De lo contrario, si..., el sistema operativo es Windows Vista, 7, 8, 8.1 o 10
                                            else if(osName.equals("Windows Vista") | osName.equals("Windows 7") | osName.equals("Windows 8") | osName.equals("Windows 8.1") | 
                                                                                                                                                        osName.equals("Windows 10")){
                                                // La ruta será la siguiente:                                                                                                  
                                                mensajeConf = "El inventario solicitado se generó correctamente en Microsoft Excel\n" +
                                                                    "Busque en la carpeta Docupentos Públicos. Dirección de la ubicación: C:\\Users\\Public\\Documents\\";
                                            }
                                         // Si es así informamos que se generó exitosamente en el sistema operativo correspondiente
                                         JOptionPane.showMessageDialog(this, mensajeConf, "¡Éxito!", JOptionPane.INFORMATION_MESSAGE);
                                     }else{
                                         JOptionPane.showMessageDialog(this, "El inventario no se pudo generar por causas desconocidas",
                                                 "No se pudo generar el inventario", JOptionPane.ERROR_MESSAGE);
                                     }
                             }    
                    }
                    // De lo contrario..., si no se encontraron registros coincidentes...
                    else{
                        JOptionPane.showMessageDialog(this, "No se encontraron registros coincidentes con su búsqueda",
                                "No se encontraron registros", JOptionPane.INFORMATION_MESSAGE);
                    }
        }
     }          
            
            // Se introdujeron más datos en la fecha
            else {
                    JOptionPane.showMessageDialog(this, "Para buscar por fecha debe introdur sólo el año", "Error", JOptionPane.INFORMATION_MESSAGE);
         
                } 
              
        }// Se termina el algoritmo de búsqueda avanzada o detallada
        
        // Introducimos el texto "Año" en el campo
        if(this.campoAño.getText().isEmpty()){
            campoAño.setText("Año");
        }
    }//GEN-LAST:event_botonBuscarActionPerformed

    
    /*
    * El edificio del CEC Morelia sólo cuenta con cinco niveles con bienes inventariados, por lo que
    * el siguiente algoritmo servirá para que la aplicación no le permita al usuario ingresar un número superior al 5
    * ni tampoco le permita ingresar números negativos
    */
    private void campoNivelKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoNivelKeyReleased
        if (evt.getSource() == campoNivel) {
                                            nivel = Integer.parseInt(campoNivel.getText());
                if (nivel < 0 | nivel > 5) {
                        campoNivel.setText("");
                        JOptionPane.showMessageDialog(rootPane, "Favor de introducir un valor correcto en el campo de nivel",
                                                                                            "Introduzca un valor correcto", JOptionPane.ERROR_MESSAGE);
                }
        }
    }//GEN-LAST:event_campoNivelKeyReleased

    private void campoLocalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoLocalKeyReleased
        
    }//GEN-LAST:event_campoLocalKeyReleased

    
    /*
    * Este algoritmo servirá para que el usuario no pueda introducir un año superior
    ** al actual, más de 4 números
    */
    private void campoAñoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoAñoKeyReleased
        if (evt.getSource() == campoAño) {
                año = campoAño.getText();
                int tAño = Integer.parseInt(año);
                             Fecha f = new Fecha();
                             // Obtenemos el año actual
                             int AñoActual = f.MostrarAño();
                   // Verificamos que si el año introducido es superior al actual
                if(tAño > AñoActual){
                    // Si es así se le debe notificar al usuario, y se borra lo que haya escrito
                    campoAño.setText("");
                    JOptionPane.showMessageDialog(rootPane, 
                            "No se puede introducir un año superior al actual que es el " + AñoActual,
                            "Mensaje", JOptionPane.ERROR_MESSAGE);
                }
        }
    }//GEN-LAST:event_campoAñoKeyReleased

    private void campoValorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoValorKeyReleased
        
    }//GEN-LAST:event_campoValorKeyReleased

    
    /*
    * El campo nivel no debe aceptar letras ni signos, sólo números, esi que este algoritmo servirá
    * para que se cumpla esta condición
    */
    private void campoNivelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoNivelKeyTyped
        if (evt.getSource() == campoNivel) {
                char c = evt.getKeyChar();
                // Si se introduce un caracter que no sea de tipo entero...
                
                if (!((Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE)))) {
                    getToolkit().beep();
                    evt.consume();
                    JOptionPane.showMessageDialog(null, "Sólo se pueden ingresar números en este campo", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            } 
    }//GEN-LAST:event_campoNivelKeyTyped
    
    
    /*
    * El campo local no debe aceptar letras ni signos, sólo números, esi que este algoritmo servirá
    * para que se cumpla esta condición
    */
    private void campoLocalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoLocalKeyTyped
        if (evt.getSource() == campoLocal) {
                char c = evt.getKeyChar();
                if (!((Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE)))) {
                    getToolkit().beep();
                    evt.consume();
                    JOptionPane.showMessageDialog(null, "Sólo se pueden ingresar números en este campo", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            } 
    }//GEN-LAST:event_campoLocalKeyTyped

    
    /*
    * El campo año no debe aceptar letras ni signos, sólo números, esi que este algoritmo servirá
    * para que se cumpla esta condición
    */
    private void campoAñoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoAñoKeyTyped
        if (evt.getSource() == campoAño) {
                char c = evt.getKeyChar();
                if (!Character.isDigit(c) & (c != KeyEvent.VK_BACK_SPACE)) {
                    getToolkit().beep();
                    evt.consume();
                    JOptionPane.showMessageDialog(null, "Sólo se pueden ingresar números en este campo", "ERROR", JOptionPane.ERROR_MESSAGE);
                } 
            }
    }//GEN-LAST:event_campoAñoKeyTyped

    
    /*
    * El campo valor no debe aceptar letras ni signos diferentes a un punto, sólo números decimales, esi que este algoritmo servirá
    * para que se cumpla esta condición
    */
    private void campoValorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoValorKeyTyped
        char c = evt.getKeyChar();
        // Si el usuario introduce un valor que no sea decimal..
        if(!Character.isDigit(c) & c!='.' & (c != KeyEvent.VK_BACK_SPACE)){
            getToolkit().beep();
            evt.consume();
            JOptionPane.showMessageDialog(null, "Sólo se pueden ingresar números decimales en este campo", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        // Se acepta que el usuario introduzca un punto pero sólo uno
        if(c=='.' & campoValor.getText().contains(".") & (c != KeyEvent.VK_BACK_SPACE)){
            getToolkit().beep();
            evt.consume();
            JOptionPane.showMessageDialog(null, "Sólo se pueden ingresar números decimales en este campo", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_campoValorKeyTyped

    
    /*
    * Si el usuario da clic en el botón de "Generar inventario con todos los bienes del CEC Morelia"
    * se activará el siguiente algoritmo
    */
    private void botonInvTBienesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonInvTBienesActionPerformed
        ArrayList bienes = new ArrayList();
         Selecciones s = new Selecciones();
         // Indicamos el comando SQL para la consulta
         String sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes";
        try {
            // Pasamos el comando al gestor y recojemos los valores que nos regrese a el arraylist
            bienes = s.getBien(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Pinventarios.class.getName()).log(Level.SEVERE, null, ex);
        }
                // medimos el arraylist
                int tArrayList = bienes.size();
                    if(tArrayList != 0 & tArrayList > 1){
                         etiquetaFecha.setText("Fecha de asignación:");
                // Pasamos el arraylist a la tabla
                tablab.setModel(new FormatoTablaBien(bienes));
                botonActualizar.setVisible(true);
                botonBaja.setVisible(true);
                botonActualizar.setEnabled(false);
                botonBaja.setEnabled(false);
                etiquetaAvisoBajas.setVisible(false);
                // Informamos cuántos bienes se encuentran registrados
                JOptionPane.showMessageDialog(this, "Se encontraron " + tArrayList + " bienes registrados",
                                "¡Éxito!", JOptionPane.INFORMATION_MESSAGE);
                // Preguntamos si se desea generar un inventario
                int conf = JOptionPane.showConfirmDialog(this, "Está apunto de generar un inventario con todos los bienes " + 
                "del CEC Morelia\nSi sólo desea dar de baja o actualizar algún bien, seleccione la opción de cancelar",
                "¿Desea continuar?", JOptionPane.OK_CANCEL_OPTION);
        // Si se confirma...
        if(conf == 0){
                GeneradorExcel e = new GeneradorExcel();
                // le mandamos el arraylist al método para que genere en inventario
                boolean verificador = e.GenerarInventarioE(bienes);
                // Si el inventario se generó correctamente...
                if(verificador != false){
                    // Esta variable servirá para obtener el nombre del sistema operativo
                                        // ya que la ruta donde se guardarán los inventarios puede cambiar dependiendo si usamos Windows XP, 7 u 8
                                        String osName = System.getProperty("os.name");
                                        String mensajeConf=null;
                                        // Si el sistema operativo donde se está usando el sistema es Windows XP...
                                            if(osName.equals("Windows XP")){
                                                // La ruta donde se guardarán los datos será la siguiente:
                                                mensajeConf = "El inventario solicitado se generó correctamente en Microsoft Excel\n"+
                                                                "Busque en la carpeta Mis Docupentos del usuario Admin";
                                            }
                                            // De lo contrario, si..., el sistema operativo es Windows Vista, 7, 8, 8.1 o 10
                                            else if(osName.equals("Windows Vista") | osName.equals("Windows 7") | osName.equals("Windows 8") | osName.equals("Windows 8.1") | 
                                                                                                                                                        osName.equals("Windows 10")){
                                                // La ruta será la siguiente:                                                                                                  
                                                mensajeConf = "El inventario solicitado se generó correctamente en Microsoft Excel\n" +
                                                                    "Busque en la carpeta Docupentos Públicos. Dirección de la ubicación: C:\\Users\\Public\\Documents\\";
                                            }
                                         // Si es así informamos que se generó exitosamente en el sistema operativo correspondiente
                                         JOptionPane.showMessageDialog(this, mensajeConf, "¡Éxito!", JOptionPane.INFORMATION_MESSAGE);
                }
                // De lo contrario, si no se generó...
                else{
                    JOptionPane.showMessageDialog(this, "El inventario no se pudo generar por causas desconocidas",
                            "No se pudo generar el inventario", JOptionPane.ERROR_MESSAGE);
                }
        }
                    
                    }
                    // De lo contrario, si..., sólo se encontró un bien
                    else if(tArrayList == 1){
                        etiquetaFecha.setText("Fecha de asignación:");
                // Le pasamos el arraylist a la tabla
                tablab.setModel(new FormatoTablaBien(bienes));
                botonActualizar.setVisible(true);
                botonBaja.setVisible(true);
                botonActualizar.setEnabled(false);
                botonBaja.setEnabled(false);
                etiquetaAvisoBajas.setVisible(false);
                        // Se informa la situación
                        JOptionPane.showMessageDialog(this, "Sólo se encontró 1 bien registrado en el sistema",
                                "¡Éxito!", JOptionPane.INFORMATION_MESSAGE);
                        // Se pide confirmación para generar un inventario en Excel
                        int conf = JOptionPane.showConfirmDialog(this, "Está apunto de generar un inventario con todos los bienes " + 
                "del CEC Morelia\nSi sólo desea dar de baja o actualizar algún bien, seleccione la opción de cancelar",
                "¿Desea continuar?", JOptionPane.OK_CANCEL_OPTION);
        // Si se confirma...
        if(conf == 0){
            try {
                // Realizamos la consulta SQL y le pasamos los resultados al arraylist
                bienes = s.getBien(sql);
                    GeneradorExcel e = new GeneradorExcel();
                    // Le pasamos el arraylist al método para generar los inventarios
                    boolean verificador = e.GenerarInventarioE(bienes);
                    // Si se generó correctamente...
                        if(verificador != false){
                            // Esta variable servirá para obtener el nombre del sistema operativo
                                        // ya que la ruta donde se guardarán los inventarios puede cambiar dependiendo si usamos Windows XP, 7 u 8
                                        String osName = System.getProperty("os.name");
                                        String mensajeConf=null;
                                        // Si el sistema operativo donde se está usando el sistema es Windows XP...
                                            if(osName.equals("Windows XP")){
                                                // La ruta donde se guardarán los datos será la siguiente:
                                                mensajeConf = "El inventario solicitado se generó correctamente en Microsoft Excel\n"+
                                                                "Busque en la carpeta Mis Docupentos del usuario Admin";
                                            }
                                            // De lo contrario, si..., el sistema operativo es Windows Vista, 7, 8, 8.1 o 10
                                            else if(osName.equals("Windows Vista") | osName.equals("Windows 7") | osName.equals("Windows 8") | osName.equals("Windows 8.1") | 
                                                                                                                                                        osName.equals("Windows 10")){
                                                // La ruta será la siguiente:                                                                                                  
                                                mensajeConf = "El inventario solicitado se generó correctamente en Microsoft Excel\n" +
                                                                    "Busque en la carpeta Docupentos Públicos. Dirección de la ubicación: C:\\Users\\Public\\Documents\\";
                                            }
                                         // Si es así informamos que se generó exitosamente en el sistema operativo correspondiente
                                         JOptionPane.showMessageDialog(this, mensajeConf, "¡Éxito!", JOptionPane.INFORMATION_MESSAGE);
                        }
                        // De lo contrario...
                        else{
                            JOptionPane.showMessageDialog(this, "El inventario no se pudo generar por causas desconocidas",
                                    "No se pudo generar el inventario", JOptionPane.ERROR_MESSAGE);
                        }
            } catch (SQLException ex) {
                Logger.getLogger(Pinventarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                    }
                    
                    // De lo contrario..., si no se encontraron bienes registrados
                    else{
                        JOptionPane.showMessageDialog(this, "No se encontraron registros",
                                "No se encontraron registros", JOptionPane.INFORMATION_MESSAGE);
                    }
                    
    }//GEN-LAST:event_botonInvTBienesActionPerformed

    
    /*
    * Este algoritmo sirve para limpiar los campos
    */
    private void botonLimpiarCamposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonLimpiarCamposActionPerformed
        campoDescripcion.setText("");
        campoMarca.setText("");
        campoModelo.setText("");
        campoClaveCABM.setText("");
        campoNoserie.setText("");
        campoValor.setText("");
        campoPersona.setText("");
        campoDepartamento.setText("");
        campoNivel.setText("");
        campoLocal.setText("");
        campoAño.setText("");
        campoDetalles.setText("");
        listaMes.setSelectedIndex(0);
        listaDia.setSelectedIndex(0);
    }//GEN-LAST:event_botonLimpiarCamposActionPerformed

    
    /*
    * Este algoritmo sirve para consultar los bienes datos de baja
    */
    private void botonConsultarBajasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonConsultarBajasActionPerformed
        ArrayList bienes = new ArrayList();
         Selecciones s = new Selecciones();
         // Indicamos el comando SQL para la consulta de todos los bienes dados de baja
         String sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fbj_ben), month(fbj_ben), day(fbj_ben), "+
                 "ant_cbj, val_det, dls_det FROM DBajas";
        try {
            // Realizamos la consulta y guardamos los resultados en el arraylist
            bienes = s.getBien(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Pinventarios.class.getName()).log(Level.SEVERE, null, ex);
        }
                // Medimos del arraylist
                int tArrayList = bienes.size();
                    if(tArrayList != 0 & tArrayList > 1){
                        // En la etiqueta de los campos de fecha le cambiamos el texto: "fecha de asignación"
                        // por el de "fecha de baja", para dar referencia de la fecha en la que fue dado de baja cada bien seleccionado
                         etiquetaFecha.setText("Fecha de baja:");
                // Acomodamos el arraylist en la tabla
                tablab.setModel(new FormatoTablaBien(bienes));
                // El botón de actualizar y el de dar de baja no deben mostrarse, ya que por lógica como están dados de baja no se pueden modificar
                // además de que una de las especificaciones del personal del CEC fue que a los dados de baja no se les pueda regresar a su estado de alta
                botonActualizar.setVisible(false);
                botonBaja.setVisible(false);
                botonActualizar.setEnabled(false);
                botonBaja.setEnabled(false);
                // La etiquetaAvisoBajas servirá para indicar que los vienes mostrados no están dados de alta
                etiquetaAvisoBajas.setVisible(true);
                // Indicamos cuántos bienes dados de baja están registrados
                JOptionPane.showMessageDialog(this, "Se encontraron " + tArrayList + " bienes dados de baja",
                                "¡Éxito!", JOptionPane.INFORMATION_MESSAGE);
                // Preguntamos si se desea generar un inventario
                int conf = JOptionPane.showConfirmDialog(this, "Está apunto de generar un inventario de los bienes dados de baja, ¿desea continuar?",
                "¿Desea continuar?", JOptionPane.OK_CANCEL_OPTION);
        // Si el usuario lo confirma...
        if(conf == 0){
            try {
                // Se realiza la consulta y el resultado se guarda en el arraylist
                bienes = s.getBien(sql);
                    GeneradorExcel e = new GeneradorExcel();
                    // Mandamos el arraylist al método para generar el inventario
                    boolean verificador = e.GenerarInventarioEBajas(bienes);
                        // Si todo salió bien... 
                        if(verificador != false){
                            // Esta variable servirá para obtener el nombre del sistema operativo
                                        // ya que la ruta donde se guardarán los inventarios puede cambiar dependiendo si usamos Windows XP, 7 u 8
                                        String osName = System.getProperty("os.name");
                                        String mensajeConf=null;
                                        // Si el sistema operativo donde se está usando el sistema es Windows XP...
                                            if(osName.equals("Windows XP")){
                                                // La ruta donde se guardarán los datos será la siguiente:
                                                mensajeConf = "El inventario solicitado se generó correctamente en Microsoft Excel\n"+
                                                                "Busque en la carpeta Mis Docupentos del usuario Admin";
                                            }
                                            // De lo contrario, si..., el sistema operativo es Windows Vista, 7, 8, 8.1 o 10
                                            else if(osName.equals("Windows Vista") | osName.equals("Windows 7") | osName.equals("Windows 8") | osName.equals("Windows 8.1") | 
                                                                                                                                                        osName.equals("Windows 10")){
                                                // La ruta será la siguiente:                                                                                                  
                                                mensajeConf = "El inventario solicitado se generó correctamente en Microsoft Excel\n" +
                                                                    "Busque en la carpeta Docupentos Públicos. Dirección de la ubicación: C:\\Users\\Public\\Documents\\";
                                            }
                                         // Si es así informamos que se generó exitosamente en el sistema operativo correspondiente
                                         JOptionPane.showMessageDialog(this, mensajeConf, "¡Éxito!", JOptionPane.INFORMATION_MESSAGE);
                        }
                        // De lo contrario...
                        else{
                            JOptionPane.showMessageDialog(this, "El inventario no se pudo generar por causas desconocidas",
                                    "No se pudo generar el inventario", JOptionPane.ERROR_MESSAGE);
                        }
            } catch (SQLException ex) {
                Logger.getLogger(Pinventarios.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Pinventarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                    }
                    // De lo contrario, si..., sólo se encontró un bien dado de baja
                    else if(tArrayList == 1){
                        etiquetaFecha.setText("Fecha de baja:");
                        // Mandamos el arraylist a la tabla
                tablab.setModel(new FormatoTablaBien(bienes));
                botonActualizar.setVisible(false);
                botonBaja.setVisible(false);
                botonActualizar.setEnabled(false);
                botonBaja.setEnabled(false);
                etiquetaAvisoBajas.setVisible(true);
                        // Informamos la situación
                        JOptionPane.showMessageDialog(this, "Sólo se encontró 1 bien dado de baja",
                                "¡Éxito!", JOptionPane.INFORMATION_MESSAGE);
                        // Preguntamos si se desea generar un inventario
                        int conf = JOptionPane.showConfirmDialog(this, "¿Desea generar un inventario en Excel?, recuerde que sólo existe un bien dado de baja",
                "¿Desea continuar?", JOptionPane.OK_CANCEL_OPTION);
        // Si se confirma...
        if(conf == 0){
            try {
                // Mandamos la consulta SQL y pasamos los resultados a un arraylist
                bienes = s.getBien(sql);
                    GeneradorExcel e = new GeneradorExcel();
                    // Le pasamos el arraylist al método para generar el inventario
                    boolean verificador = e.GenerarInventarioEBajas(bienes);
                        // Si se generó correctamente...
                        if(verificador != false){
                            // Esta variable servirá para obtener el nombre del sistema operativo
                                        // ya que la ruta donde se guardarán los inventarios puede cambiar dependiendo si usamos Windows XP, 7 u 8
                                        String osName = System.getProperty("os.name");
                                        String mensajeConf=null;
                                        // Si el sistema operativo donde se está usando el sistema es Windows XP...
                                            if(osName.equals("Windows XP")){
                                                // La ruta donde se guardarán los datos será la siguiente:
                                                mensajeConf = "El inventario solicitado se generó correctamente en Microsoft Excel\n"+
                                                                "Busque en la carpeta Mis Docupentos del usuario Admin";
                                            }
                                            // De lo contrario, si..., el sistema operativo es Windows Vista, 7, 8, 8.1 o 10
                                            else if(osName.equals("Windows Vista") | osName.equals("Windows 7") | osName.equals("Windows 8") | osName.equals("Windows 8.1") | 
                                                                                                                                                        osName.equals("Windows 10")){
                                                // La ruta será la siguiente:                                                                                                  
                                                mensajeConf = "El inventario solicitado se generó correctamente en Microsoft Excel\n" +
                                                                    "Busque en la carpeta Docupentos Públicos. Dirección de la ubicación: C:\\Users\\Public\\Documents\\";
                                            }
                                         // Si es así informamos que se generó exitosamente en el sistema operativo correspondiente
                                         JOptionPane.showMessageDialog(this, mensajeConf, "¡Éxito!", JOptionPane.INFORMATION_MESSAGE);
                        }
                        // De lo contrario..., si no se generó
                        else{
                            JOptionPane.showMessageDialog(this, "El inventario no se pudo generar por causas desconocidas",
                                    "No se pudo generar el inventario", JOptionPane.ERROR_MESSAGE);
                        }
            } catch (SQLException ex) {
                Logger.getLogger(Pinventarios.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Pinventarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                    }
                    
                    // De lo contrario..., si no se encontraron bienes dados de baja registrados
                    else{
                        JOptionPane.showMessageDialog(this, "No se encontraron registros dados de baja",
                                "No se encontraron registros", JOptionPane.INFORMATION_MESSAGE);
                    }
                    
    }//GEN-LAST:event_botonConsultarBajasActionPerformed

    
    /*
    * Este algoritmo servirá para actualizar el bien que se haya seleccionado de la tabla
    */
    private void botonActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonActualizarActionPerformed
        
        // Cachamos los datos con el mismo método del buscador
        descripcion = campoDescripcion.getText();    
        
        marca = campoMarca.getText();       
        
        modelo = campoModelo.getText();    
        
        claveCABM = campoClaveCABM.getText(); 
        
        noSerie = campoNoserie.getText();   
        
        persona = campoPersona.getText();              
        
        edificio = campoEdificio.getText();          
        
        departamento = campoDepartamento.getText();
        
        if(!this.campoNivel.getText().isEmpty()){
            nivel = Integer.parseInt(campoNivel.getText());
        } else{
            nivel = 0;
        }
        
        if(!this.campoLocal.getText().isEmpty()){
            local = Integer.parseInt(campoLocal.getText());
        } else{
            local = 0;
        }
        
        if(!this.campoValor.getText().isEmpty()){
            valor = Double.parseDouble(campoValor.getText());
        } else{
            valor = 0.0;
        }
        
        año = campoAño.getText();
        
        if(this.listaMes.getSelectedIndex() != 0){
            mes = this.listaMes.getSelectedIndex();
        } else{
            mes = 0;
        }
        
        if(this.listaDia.getSelectedIndex() != 0){
            dia = this.listaDia.getSelectedIndex();
        } else{
            dia = 0;
        }
        
        detalles = campoDetalles.getText();
        
        Fecha f = new Fecha();
        fechaAsig = f.Fecha(año, mes, dia);
        
        // Esto es para que se actualice únicamente lo que haya seleccionado de la tabla
        int fila = tablab.getSelectedRow();
        Bien bien = ((FormatoTablaBien)tablab.getModel()).getFila(fila);
        String id = tablab.getValueAt(fila, 0).toString();
                      
        int IDbien = bien.getIdBien();
        boolean verificador = false;
        
        // Se verifica no hay ningún campo vacio, (excepto el de la clave CABM, qye este es tolerable)
        if(descripcion.isEmpty() | marca.isEmpty() | modelo.isEmpty() |
                noSerie.isEmpty() | this.campoValor.getText().isEmpty() |
                persona.isEmpty() | edificio.isEmpty() | departamento.isEmpty() |
                this.campoNivel.getText().isEmpty() | this.campoLocal.getText().isEmpty() |
                this.campoAño.getText().isEmpty() | this.listaMes.getSelectedIndex() == 0 |
                this.listaDia.getSelectedIndex() == 0) {
            // Si es así le pedimos que rellene los campos que faltan
            getToolkit().beep();
            JOptionPane.showMessageDialog(rootPane, "Para actualizar un registro todos los campos son oblicatorios, excepto la clave CABM", "Rellene los campos que faltan", JOptionPane.ERROR_MESSAGE);
        }
        
        // De lo contrario..., si todos los campos están llenos (sin contar a la clave CABM)...
        else {
            Selecciones s = new Selecciones();
            // Inicializamos la clase Updates del paquete ConexionDB, el cual contiene los métodos para actualizar los datos
            Updates upD = new Updates();
            // Llamamos al método para actualizar los datos y le pasamos los valores
            verificador = upD.Actualizar(IDbien, descripcion, marca, modelo, claveCABM,
                    noSerie, valor, persona, edificio, departamento, nivel, local, fechaAsig, detalles);
            // Si todo salió bien...
            if(verificador == true){
                ArrayList bienes = new ArrayList();
                // Indicamos el comando de consulta para que se vizualice el bien actualizado en la tabla
                // Tomando el número de serie para saber que se trata de él, ya que este dato es de clave única
         String sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fas_arg), month(fas_arg), day(fas_arg), "+
                 "ant_cbi, val_det, dls_det FROM DBienes where nse_ben = '" + noSerie + "'";
            try {
                etiquetaFecha.setText("Fecha de asignación:");
                // Realizamos la consulta SQL y pasamos los resultados al arraylist
                bienes = s.getBien(sql);
                // Pasamos el arraylist a la tabla
                tablab.setModel(new FormatoTablaBien(bienes));
                botonActualizar.setVisible(true);
                botonBaja.setVisible(true);
                etiquetaAvisoBajas.setVisible(false);
                // Informamos que el bien se almacenó correctamente
                JOptionPane.showMessageDialog(rootPane, "El bien se ha actualizado exitosamente", "¡Éxito!", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                Logger.getLogger(Pinventarios.class.getName()).log(Level.SEVERE, null, ex);
            }
                
             }
            
            
            // De lo contrario..., si el bien no se actualizó 
            else {
                JOptionPane.showMessageDialog(rootPane, "El bien no se ha actualizado correctamente\nsi persiste el inconveniente contácte al desarrollador de la aplicación", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
            
            
        }
    }//GEN-LAST:event_botonActualizarActionPerformed

    
    /*
    * Este algoritmo servirá para dar de baja al bien que se seleccione de la tabla
    */
    private void botonBajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBajaActionPerformed
       // Esto servirá para que se dé de baja únicamente el registro seleccionado de la tabla
        int fila = tablab.getSelectedRow();
        Bien bien = ((FormatoTablaBien)tablab.getModel()).getFila(fila);
        String id = tablab.getValueAt(fila, 0).toString();
                      
        int IDbien = bien.getIdBien();
        
        boolean verificador = false;
        
        noSerie = campoNoserie.getText();
        // Preguntamos si en verdad se desea dar de baja el bien seleccionado
        int conf = JOptionPane.showConfirmDialog(this, "¿En verdad desea dar de baja el bien con número de serie '" + noSerie + "'?"
                , "¿Desea continuar?", JOptionPane.OK_CANCEL_OPTION);
        // Si se confirma...
        if(conf == 0){
            // La operación será Update en vez de Delete porque no se busca eliminar los datos, únicamente apartarlos de 
            // los que sí están dados de alta, esto con la finalidad de llevar un control de los bienes dados de baja
            Updates baja = new Updates();
            // Mandamos llamar el método necesario para dar de baja el bien y le pasamos su ID para garantizar que no se
            // dará de baja un bien equivocado
            verificador = baja.DarDeBaja(IDbien);
            // Si todo salió bien...
            if(verificador != false){
                ArrayList bienes = new ArrayList();
         Selecciones s = new Selecciones();
         // Se indica el comando SQL necesario para obtener los datos de las bajas
         String sql = "SELECT id_ben, cbm_ben, des_ben, nom_mrc, mlo_mrc, nse_ben, "+
                 "edi_ubi, nvl_ubi, lcl_ubi, dep_ubi, prs_asg, year(fbj_ben), month(fbj_ben), day(fbj_ben), "+
                 "ant_cbj, val_det, dls_det FROM DBajas";
            try {
                // El texto de la etiquetaFecha cambia a: "Fecha de baja"
                etiquetaFecha.setText("Fecha de baja:");
                // Realizamos la consulta y pasamos los valores al arraylist
                bienes = s.getBien(sql);
                // Medimos el arraylist
                int tArrayList = bienes.size();
                    if(tArrayList != 0 & tArrayList > 1){
                        // Pasamos el arraylist a la tabla para que se visualicen ahí los bienes en este estado
                        tablab.setModel(new FormatoTablaBien(bienes));
                        botonActualizar.setVisible(false);
                        botonBaja.setVisible(false);
                        etiquetaAvisoBajas.setVisible(true);
                        // Se informa que el bien se dio de baja correctamente
                        JOptionPane.showMessageDialog(this, "El bien con número de serie: '" + noSerie + "', se ha dado de baja correctamente",
                                "¡Éxito!", JOptionPane.INFORMATION_MESSAGE);
                    }
                    // Si sólo existe un bien dado de baja...
                    else if(tArrayList == 1){
                        // Pasamos el arraylist a la tabla para que se visualicen ahí los bienes en este estado
                        tablab.setModel(new FormatoTablaBien(bienes));
                        botonActualizar.setVisible(false);
                        botonBaja.setVisible(false);
                        etiquetaAvisoBajas.setVisible(true);
                        // Se informa que el bien se dio de baja correctamente
                        JOptionPane.showMessageDialog(this, "El bien con número de serie: '" + noSerie + "', se ha dado de baja correctamente",
                                 "¡Éxito!", JOptionPane.INFORMATION_MESSAGE);
                    }
                    // De lo contrario..., no se encontraron bienes dados de baja
                    // En las ciscunstancias es ilógico que ésto suceda, pero es mejor estar prevenido
                    else {
                        JOptionPane.showMessageDialog(this, "No se encontraron bienes dados de baja",
                                "No se encontraron bajas", JOptionPane.INFORMATION_MESSAGE);
                    }
            } catch (SQLException ex) {
                Logger.getLogger(Pinventarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            
            // De lo contrario..., si el bien seleccionado no se pudo dar de baja
            else{
                JOptionPane.showMessageDialog(this, "El bien no se pudo dar de baja",
                                "Error", JOptionPane.ERROR_MESSAGE);
            }
            
            
      }
    }//GEN-LAST:event_botonBajaActionPerformed

    private void campoValorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoValorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoValorActionPerformed

    
    /*
    * Con este algoritmo podremos abrir la ventana "CambNUC" del paquete Login.RDU
    */
    private void etiquetaCambNUCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_etiquetaCambNUCMouseClicked
        CambNUC c = null;
        try {
            c = new CambNUC(true);
        } catch (SQLException ex) {
            Logger.getLogger(Pinventarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.setVisible(true);
        // Cerramos la ventana Pinventarios
        dispose();
    }//GEN-LAST:event_etiquetaCambNUCMouseClicked

    
    
    private void campoDescripcionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoDescripcionKeyTyped
        
    }//GEN-LAST:event_campoDescripcionKeyTyped

    private void campoMarcaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoMarcaKeyTyped
        
    }//GEN-LAST:event_campoMarcaKeyTyped

    private void campoModeloKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoModeloKeyTyped
        
    }//GEN-LAST:event_campoModeloKeyTyped

    private void campoClaveCABMKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoClaveCABMKeyTyped
        
    }//GEN-LAST:event_campoClaveCABMKeyTyped

    private void campoNoserieKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoNoserieKeyTyped
        
    }//GEN-LAST:event_campoNoserieKeyTyped

    private void campoPersonaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoPersonaKeyTyped
        
    }//GEN-LAST:event_campoPersonaKeyTyped

    private void campoEdificioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoEdificioKeyTyped
        
    }//GEN-LAST:event_campoEdificioKeyTyped

    private void campoDepartamentoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoDepartamentoKeyTyped
        
    }//GEN-LAST:event_campoDepartamentoKeyTyped

    private void campoDetallesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoDetallesKeyTyped
        
    }//GEN-LAST:event_campoDetallesKeyTyped

    private void campoDescripcionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_campoDescripcionMouseClicked
        if(this.campoDescripcion.getText().isEmpty()){
            JOptionPane.showMessageDialog(rootPane, "Introducir el tipo de bien en el campo descripción");
        }
    }//GEN-LAST:event_campoDescripcionMouseClicked

    private void campoAñoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_campoAñoMouseClicked
        if(this.campoAño.getText().equals("Año")){
            campoAño.setText("");
        }
    }//GEN-LAST:event_campoAñoMouseClicked

    private void tablabMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablabMouseMoved
          
    }//GEN-LAST:event_tablabMouseMoved

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonActualizar;
    private javax.swing.JButton botonBaja;
    private javax.swing.JButton botonBuscar;
    private javax.swing.JButton botonConsultarBajas;
    private javax.swing.JButton botonInvTBienes;
    private javax.swing.JButton botonLimpiarCampos;
    private javax.swing.JButton botonRegistrar;
    private javax.swing.JTextField campoAño;
    private javax.swing.JTextField campoClaveCABM;
    private javax.swing.JTextField campoDepartamento;
    private javax.swing.JTextField campoDescripcion;
    private javax.swing.JTextField campoDetalles;
    private javax.swing.JTextField campoEdificio;
    private javax.swing.JTextField campoLocal;
    private javax.swing.JTextField campoMarca;
    private javax.swing.JTextField campoModelo;
    private javax.swing.JTextField campoNivel;
    private javax.swing.JTextField campoNoserie;
    private javax.swing.JTextField campoPersona;
    private javax.swing.JTextField campoValor;
    private javax.swing.JLabel etiquetaAvisoBajas;
    private javax.swing.JLabel etiquetaCambNUC;
    private javax.swing.JLabel etiquetaCerrarSesion;
    private javax.swing.JLabel etiquetaClavecabm;
    private javax.swing.JLabel etiquetaDepartamento;
    private javax.swing.JLabel etiquetaDescripcion;
    private javax.swing.JLabel etiquetaDetalles;
    private javax.swing.JLabel etiquetaEdificio;
    private javax.swing.JLabel etiquetaFecha;
    private javax.swing.JLabel etiquetaInventariosEspecificos;
    private javax.swing.JLabel etiquetaLocal;
    private javax.swing.JLabel etiquetaMarca;
    private javax.swing.JLabel etiquetaModelo;
    private javax.swing.JLabel etiquetaNivel;
    private javax.swing.JLabel etiquetaNoSerie;
    private javax.swing.JLabel etiquetaPersona;
    private javax.swing.JLabel etiquetaValor;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox listaDia;
    private javax.swing.JComboBox listaMes;
    private javax.swing.JTable tablab;
    // End of variables declaration//GEN-END:variables
}
