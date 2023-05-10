/*
 * El Sistema de Inventario de Bienes (SIB) fue desarrollado con la finalidad de
 * aportar una mejora al Centro de Educación Continua Unidad Morelia en cuanto
 * a la administración de los bienes muebles y equipo con los que cuenta.
 * 
 * Este software fue concebido, diseñado y desarrollado por el alumno cuyo seudónimo
 * adoptado es "Emoticón", como proyecto encomendado por el cumplimiento del servicio social.
 *
 */
package Login;


// Importamos las clases necesarias para invocar los métodos correspondientes a cada función de esta clase
import ConexionDB.Aconexion;
import Login.RDU.DB.Selecciones;
import Login.RDU.CambNUC;
import Login.RDU.NvoUsuario;
import Login.RDU.RecupContra;
import Login.RDU.Usuario;
import MovimientosSIB.Pinventarios;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Emoticon
 * @version 1.0.0.0_2
 */
// Inicia la clase Login
public class Login extends javax.swing.JFrame {
    // Se declaran las variables necesarias, en este caso será la de usuario y la de contraseña
private String user, passw;
    
    /**
     * Creates new form SIB
     */
    public Login() {
        
        
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
                        NvoUsuario nu = new NvoUsuario();
                        nu.setVisible(true);
                        dispose();
                        JOptionPane.showMessageDialog(rootPane, 
                                "¿Primera vez en el sistema?, no se preocupe, vamos a iniciar creando la cuenta de usuario\n" +
                                        "Llene todos los campos correctamente",
                                                            "¡Bienvenido!", JOptionPane.INFORMATION_MESSAGE);
                    }
                    // De lo contrario, si no sucede nada de lo anterior significa que sólo existe un usuario registrado por lo que...
                    else{
                        // Todo está en orden, entonces sí se despliega la ventana de Login con todos sus componentes
                        initComponents();
                        // Con método le decimos que queremos que la ventana se muestre en el centro de la pantalla
                        setLocationRelativeTo(null);
                        // Con esto no se le podrá cambiar el tamaño a la ventana
                        setResizable(false);
                        // Le asignamos un título a la ventana para que aparezca en la parte superior de la misma
                        setTitle("Login");
                        // "Borramos la etiqueta de mensaje de error
                        labeldincorrectos.setVisible(false);
                        // Inicializamos la clase de conexión a la base de datos
                        Aconexion cn = new Aconexion();
                        // Invocamos el método "conectar" para acceder a la base de datos
                        cn.conectar();
                    }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        etiquetaPoli = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        campoContraseña = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();
        botonEntrar = new javax.swing.JButton();
        camponomUsuario = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        etiquetaOlvidoContraseña = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        etiquetaDincorrectos = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        etiquetadincorrectos = new javax.swing.JLabel();
        labeldincorrectos = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(251, 251, 251));
        setMaximumSize(new java.awt.Dimension(32767, 32767));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        etiquetaPoli.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        etiquetaPoli.setText("Instituto Politécnico Nacional");

        jLabel5.setFont(new java.awt.Font("Arial", 0, 27)); // NOI18N
        jLabel5.setText("Centro de Educación Continua Unidad Morelia");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 23)); // NOI18N
        jLabel6.setText("Sistema de Inventario de Bienes");

        jLabel1.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel1.setText("Nombre de usuario:");

        campoContraseña.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        campoContraseña.setSelectionColor(new java.awt.Color(51, 0, 0));
        campoContraseña.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                campoContraseñaMouseClicked(evt);
            }
        });
        campoContraseña.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                campoContraseñaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                campoContraseñaKeyTyped(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel2.setText("Contraseña:");

        botonEntrar.setBackground(new java.awt.Color(51, 0, 0));
        botonEntrar.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        botonEntrar.setForeground(new java.awt.Color(255, 255, 255));
        botonEntrar.setText("Entrar");
        botonEntrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonEntrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEntrarActionPerformed(evt);
            }
        });

        camponomUsuario.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        camponomUsuario.setSelectionColor(new java.awt.Color(51, 0, 0));
        camponomUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                camponomUsuarioKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                camponomUsuarioKeyTyped(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial", 1, 28)); // NOI18N
        jLabel3.setText("Iniciar sesión");

        etiquetaOlvidoContraseña.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        etiquetaOlvidoContraseña.setText("Seleccione esta opción si no puede entrar");
        etiquetaOlvidoContraseña.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        etiquetaOlvidoContraseña.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                etiquetaOlvidoContraseñaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                etiquetaOlvidoContraseñaMouseEntered(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Arial", 0, 25)); // NOI18N
        jLabel10.setText("Departamento de Servicios Técnicos y Administrativos");

        etiquetaDincorrectos.setFont(new java.awt.Font("Arial", 2, 17)); // NOI18N
        etiquetaDincorrectos.setForeground(new java.awt.Color(255, 0, 51));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Login/logoipn.jpg"))); // NOI18N

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Login/cec.jpg.png"))); // NOI18N

        etiquetadincorrectos.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        etiquetadincorrectos.setForeground(new java.awt.Color(204, 0, 0));

        labeldincorrectos.setFont(new java.awt.Font("Arial", 2, 18)); // NOI18N
        labeldincorrectos.setForeground(new java.awt.Color(204, 0, 0));
        labeldincorrectos.setText("jLabel");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(etiquetaDincorrectos)
                            .addComponent(jLabel11))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel9))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(48, 48, 48)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(etiquetaOlvidoContraseña, javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(camponomUsuario)
                                                .addComponent(campoContraseña)
                                                .addComponent(botonEntrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addComponent(jLabel1)
                                            .addComponent(jLabel2)
                                            .addComponent(labeldincorrectos, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 29, Short.MAX_VALUE)))
                                .addGap(228, 228, 228))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(684, 684, 684)
                                        .addComponent(etiquetadincorrectos, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(67, 67, 67)
                                                .addComponent(jLabel5))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(102, 102, 102)
                                                .addComponent(etiquetaPoli)))
                                        .addGap(77, 77, 77)
                                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(208, 208, 208)
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(etiquetaPoli)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10))
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(120, 120, 120)
                                .addComponent(etiquetadincorrectos))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(103, 103, 103)
                                .addComponent(labeldincorrectos)
                                .addGap(3, 3, 3)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(camponomUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel2))))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel3)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel8)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(8, 8, 8)
                                    .addComponent(jLabel9)))
                            .addGap(102, 102, 102)
                            .addComponent(jLabel11)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(etiquetaDincorrectos))))
                .addGap(1, 1, 1)
                .addComponent(campoContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonEntrar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68)
                .addComponent(etiquetaOlvidoContraseña)
                .addContainerGap(120, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

   /*
    * Iniciamos el algoritmo para validar los datos ingresados en los campos correspondientes 
    */ 
    private void botonEntrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEntrarActionPerformed
        // Cachamos lo que se haya introducido en el campo de nombre de usuario
        user = camponomUsuario.getText();                                    
        // Cachamos lo que se haya introducido en el campo de contraseña
        passw = campoContraseña.getText();
        // El indicador es una variable que nos permitirá saber si los datos introducidos son correctos, es decir,
        // corresponden a los del usuario registrado
        // la inicializamos con el valor "false" (falso)
        boolean indicador = false;
        // Si el usuario llenó los dos campos...
        if(!user.isEmpty() & !passw.isEmpty()){
            // Nos pasará a esta otra sección
            // Inicializamos la clase Selecciones del paquete "ConexionDB"
            Selecciones s = new Selecciones();
            try {
                // Se realiza la validación de los datos, de modo que si son correctos
                // el valor de la variable "indicador" cambiará a "true", de lo contrario continuará siendo "false"
                indicador = s.Login(user, passw);
            } catch (SQLException ex) {
                // Se cachan errores relacionados a la base de datos
            getToolkit().beep();
                JOptionPane.showMessageDialog(rootPane, "Error desconocido SQL al intentar iniciar sesión", "Error", JOptionPane.ERROR_MESSAGE);
            }
            // Si después de pasar por la validación el valor del indicador sigue siendo false...
            if(indicador == false){
                // Se habilitará la etiqueta de errores
                labeldincorrectos.setVisible(true);
                // Se borrará lo que haya escrito en el campo contraseña
                campoContraseña.setText("");
                // Se escuchará un sonido de alerta
                getToolkit().beep();
                    // Si el nombre de usuario es correcto, el campo correspondiente no estará habilitado
                    if(!this.camponomUsuario.isEnabled()){
                    labeldincorrectos.setText("Contraseña incorrecta");
                    } else{
                        // por lo que si lo está significa que no lo es y no se sabe si este dato
                    // O la ontraseña son incorrectos, por lo que se nos despliega el siguiente mensaje:
                    labeldincorrectos.setText("El usuario y/o contraseña son incorrectos, verifiquelos y vuelva a intentar");
                    }
            }
            // De lo contrario, si el valor del indicador cambió a "true" o verdadero...
            else if(indicador == true){
                // Se abrirá la ventana para administrar los bienes
                Pinventarios pi = new Pinventarios(true);// Al contructor le pasamos el valor true para que el sistema sepa que
                                                         // el usuario inició sesión correctamente y le permita la entrada
                pi.setVisible(true);
                // Cerramos la ventana de Login
                dispose(); 
            }
            // De lo contrario, si se llegará a recibir un valor diferente de true o false al indicador
            // Se prodeucirá una Excepción, pero si suceriera otra cosa...
            else {
                // Se desplegará el siguiente mensaje de error
            getToolkit().beep();
                JOptionPane.showMessageDialog(rootPane, "Error", "Error desconocido del inicio de sesión", JOptionPane.ERROR_MESSAGE);
            }
         
    }
        // Si el usuario no llenó alguno de los campos
        else {
            // Se habilitará la etiqueta de errores
            labeldincorrectos.setVisible(true);
            // Se borrará lo que se haya escrito en el campo contraseña (en caso de que se haya llenado)
            campoContraseña.setText("");
            // Se escuchará un sonido de alerta
            getToolkit().beep();
            // En la etiqueta se desplegará un mensaje indicando que se deben rellenar los dos campos
            labeldincorrectos.setText("Debe rellenar los dos campos");
        }
    }//GEN-LAST:event_botonEntrarActionPerformed

    /*
    * Si el usuario da clic en la opción o etiqueta de "olvido de contraseña"
    * la cual puede nombrarse de diferentes formas, se invocará el siguiente método:
    */
    private void etiquetaOlvidoContraseñaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_etiquetaOlvidoContraseñaMouseClicked
        // Inicializamos a la clase de la ventana para recuperar la contraseña
        RecupContra rc = null;
    try {
        // Invocamos al constructor
        rc = new RecupContra();
    } catch (SQLException ex) {
        Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
    }
        // Abrimos la ventana de Recuperar Contraseña
        rc.setVisible(true);
        // Cerramos la ventana de Login
        dispose();
    }//GEN-LAST:event_etiquetaOlvidoContraseñaMouseClicked

    private void etiquetaOlvidoContraseñaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_etiquetaOlvidoContraseñaMouseEntered
        
    }//GEN-LAST:event_etiquetaOlvidoContraseñaMouseEntered

    private void camponomUsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_camponomUsuarioKeyTyped
        
    }//GEN-LAST:event_camponomUsuarioKeyTyped

    private void campoContraseñaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoContraseñaKeyTyped
        
    }//GEN-LAST:event_campoContraseñaKeyTyped

    private void camponomUsuarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_camponomUsuarioKeyReleased
               if(!this.camponomUsuario.getText().isEmpty()){
            campoContraseña.setEditable(true);
           user = camponomUsuario.getText();
           // Verificamos que el nombre de usuario introducido sea el correcto
            ArrayList<Usuario> usuarios = new ArrayList();
            Selecciones s = new Selecciones();
            String usernameCorrecto;
                                                try {
                                                    usuarios = s.VnomUsuario();
                                                } catch (SQLException ex) {
                                                    Logger.getLogger(RecupContra.class.getName()).log(Level.SEVERE, null, ex);
                                                }
                    // Contamos los usuarios
                    int tUsuarios = usuarios.size();
                    if(tUsuarios > 1){
                        JOptionPane.showMessageDialog(rootPane, "Existe más de un usuario registrado en el sistema", "¡ERROR!", JOptionPane.ERROR_MESSAGE);
                        dispose();
                    }
                    else if(tUsuarios < 1){
                        JOptionPane.showMessageDialog(rootPane, "No se encontró ningún usuario registrado", "¡ERROR!", JOptionPane.ERROR_MESSAGE);
                        dispose();
                    }
                    else{
                        // Obtenemos el nombre de usuario correcto y lo conparamos con el introducido
                        usernameCorrecto = usuarios.get(0).getNomUsuario();
                        if(user.equals(usernameCorrecto)){
                            camponomUsuario.setEnabled(false);
                        }
                    }
       }
               
    }//GEN-LAST:event_camponomUsuarioKeyReleased

    private void campoContraseñaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_campoContraseñaMouseClicked
        
    }//GEN-LAST:event_campoContraseñaMouseClicked

    private void campoContraseñaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoContraseñaKeyReleased
        
        if(!this.campoContraseña.getText().isEmpty()){            
            if(!this.camponomUsuario.isEnabled()){
                            boolean indicador = false;
                            user = camponomUsuario.getText();
                            passw = campoContraseña.getText();
                            // Nos pasará a esta otra sección
                        // Inicializamos la clase Selecciones del paquete "ConexionDB"
                        Selecciones s = new Selecciones();
                        try {
                            // Se realiza la validación de los datos, de modo que si son correctos
                            // el valor de la variable "indicador" cambiará a "true", de lo contrario continuará siendo "false"
                            indicador = s.Login(user, passw);
                        } catch (SQLException ex) {
                            // Se cachan errores relacionados a la base de datos
                        getToolkit().beep();
                            JOptionPane.showMessageDialog(rootPane, "Error desconocido SQL al intentar iniciar sesión", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                            // El usuario y contraseña son correctos
                            if(indicador == true){
                                // Se abrirá la ventana para administrar los bienes
                                Pinventarios pi = new Pinventarios(true);// Al contructor le pasamos el valor true para que el sistema sepa que
                                                                         // el usuario inició sesión correctamente y le permita la entrada
                                pi.setVisible(true);
                                // Cerramos la ventana de Login
                                dispose(); 
                                JOptionPane.showMessageDialog(rootPane, "¡Bienvenido " + user + "!");
                            }
            }
        }
        
    }//GEN-LAST:event_campoContraseñaKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonEntrar;
    private javax.swing.JPasswordField campoContraseña;
    private javax.swing.JTextField camponomUsuario;
    private javax.swing.JLabel etiquetaDincorrectos;
    private javax.swing.JLabel etiquetaOlvidoContraseña;
    private javax.swing.JLabel etiquetaPoli;
    private javax.swing.JLabel etiquetadincorrectos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labeldincorrectos;
    // End of variables declaration//GEN-END:variables
}
