package saludtap;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Paez
 */
public class JfInicioSesion extends javax.swing.JFrame {

    Jframeprincipal framePrincipal = new Jframeprincipal();
            Conectar conec = new Conectar();

    /**
     * Creates new form Jfiniciosesion
     */
    public JfInicioSesion() {

//            Toolkit laPantalla = Toolkit.getDefaultToolkit();
//            Dimension medidas=laPantalla.getScreenSize();    
//                                        //ubicacion del archivo
//            Image iconito =  laPantalla.getImage("src/imagenes/logoApp.png");
//        //insertamos el icono 
//            setIconImage(iconito);
        initComponents();
        colocarImagen();
        iconoAPP();

        panelLateral.setBackground(framePrincipal.colorPrinCuandoSePasaElmouse);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
    }

    
    
    private void colocarImagen() {
        PanelImagen.setLayout(new BorderLayout());
        JLabel etiquetaLogin = new JLabel(new ImageIcon("src/imagenes/estete.jpg"));
        PanelImagen.add(etiquetaLogin, BorderLayout.CENTER);
    }

    private void iconoAPP() {
        //poner icono ala app
        Toolkit laPantalla = Toolkit.getDefaultToolkit();
        Dimension medidas = laPantalla.getScreenSize();
        //ubicacion del archivo desde la clase principal se pone la ubicacion del icono
        String url2 = framePrincipal.url;
        Image iconito = laPantalla.getImage(url2);
        //insertamos el icono 
        setIconImage(iconito);

// locacion al centro
        this.setLocationRelativeTo(null);
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelImagen = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnIniciarSesion = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        btn_registrarse = new javax.swing.JButton();
        panelLateral = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtFacebook = new javax.swing.JTextArea();
        txtUsuario = new jtextfieldround.JTextFieldRound();
        txtPass = new jtextfieldround.JPasswordFieldRound();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SALUD TAP");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Script MT Bold", 3, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 51));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/iniciar-sesion.png"))); // NOI18N
        jLabel6.setText("Usuario");
        jLabel6.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel7.setFont(new java.awt.Font("Script MT Bold", 3, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 51));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/password.png"))); // NOI18N
        jLabel7.setText("Contraseña");
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel7.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        btnIniciarSesion.setBackground(new java.awt.Color(255, 255, 255));
        btnIniciarSesion.setFont(new java.awt.Font("Cambria", 3, 24)); // NOI18N
        btnIniciarSesion.setForeground(new java.awt.Color(0, 0, 51));
        btnIniciarSesion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ingresados.png"))); // NOI18N
        btnIniciarSesion.setText("Iniciar sesion");
        btnIniciarSesion.setBorder(null);
        btnIniciarSesion.setBorderPainted(false);
        btnIniciarSesion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIniciarSesion.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnIniciarSesion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnIniciarSesionMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnIniciarSesionMouseExited(evt);
            }
        });
        btnIniciarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarSesionActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Serif", 3, 18)); // NOI18N
        jLabel8.setText("Si no tiene cuenta puede registrarse aquí");

        btn_registrarse.setBackground(new java.awt.Color(255, 255, 255));
        btn_registrarse.setFont(new java.awt.Font("Serif", 3, 14)); // NOI18N
        btn_registrarse.setText("Registrarse");
        btn_registrarse.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, new java.awt.Color(0, 0, 51), null));
        btn_registrarse.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_registrarse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_registrarseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_registrarseMouseExited(evt);
            }
        });
        btn_registrarse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_registrarseActionPerformed(evt);
            }
        });

        panelLateral.setBackground(new java.awt.Color(0, 0, 76));

        javax.swing.GroupLayout panelLateralLayout = new javax.swing.GroupLayout(panelLateral);
        panelLateral.setLayout(panelLateralLayout);
        panelLateralLayout.setHorizontalGroup(
            panelLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 21, Short.MAX_VALUE)
        );
        panelLateralLayout.setVerticalGroup(
            panelLateralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel2.setFont(new java.awt.Font("Serif", 3, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/saludtap4.png"))); // NOI18N
        jLabel2.setToolTipText("");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        txtFacebook.setEditable(false);
        txtFacebook.setColumns(20);
        txtFacebook.setRows(5);
        txtFacebook.setText("para mas informacion \ncontactenos en nuestra\npagina de facebook\n\nPRESIONE AQUI PARA SEGUIR NUESTRA\nPAGINA DE FACEBOOK");
        txtFacebook.setBorder(null);
        txtFacebook.setRequestFocusEnabled(false);
        txtFacebook.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtFacebookMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(txtFacebook);

        txtUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuarioActionPerformed(evt);
            }
        });

        txtPass.setText("123");
        txtPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPassActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(panelLateral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnIniciarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(52, 52, 52)
                                        .addComponent(jLabel7))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(62, 62, 62)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap())
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btn_registrarse, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(57, 57, 57))))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelLateral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(btnIniciarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_registrarse, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout PanelImagenLayout = new javax.swing.GroupLayout(PanelImagen);
        PanelImagen.setLayout(PanelImagenLayout);
        PanelImagenLayout.setHorizontalGroup(
            PanelImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelImagenLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 623, Short.MAX_VALUE))
        );
        PanelImagenLayout.setVerticalGroup(
            PanelImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelImagenLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(PanelImagen, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
     public void mostrarCitas() {
        try {

            framePrincipal.JtAMiCita.setText("");
            String idPaciente = framePrincipal.laID.getText();
            

            String SQL = " select fecha,hora,descripcion,folio from citas where id_cita_p='" +idPaciente+ "';";

            conec.rs = conec.sentencia.executeQuery(SQL);
            

            // TxtResultado.setText("datos a imprimir"+ conec.rs.getString("nombre"));
            while (conec.rs.next()) {
                //System.out.println("\n datos a imprimir "+conec.rs.getString("fecha")+" "+conec.rs.getString("hora")+conec.rs.getString("descripcion"));
                
                framePrincipal.JtAMiCita.append("\n su cita es el dia " + conec.rs.getString("fecha") + " "
                        + "\n alas " + conec.rs.getString("hora") + ""
                        + "\n por el motivo de " + conec.rs.getString("descripcion") + ""
                         + "\n con numero folio n°: "+conec.rs.getString("folio")+"\n");
                
                
            }

        } catch (Exception e) {
            System.out.println("error al hacer Select " + e.getMessage());
        }
    }
     

     
    
   public void iniciarSesion(){
   
        try {
            //Conecction conec = new Conecction();
            String usuario = txtUsuario.getText();
            String password = txtPass.getText();
            // conec.Connection();
            String SQL = " SELECT nombre, apellido_paterno, apellido_materno, edad, curp, usuario, password,id FROM pacientes2 WHERE usuario ='" + usuario + "' and password='" + password + "';";

            conec.rs = conec.sentencia.executeQuery(SQL);

            if (conec.rs.next()) {
                this.dispose();
           framePrincipal.LabelNombre.setText("Bienvenido " + conec.rs.getString("nombre")+" "+conec.rs.getString("apellido_paterno"));
                framePrincipal.txtNombre.setText("" + conec.rs.getString("nombre"));
                framePrincipal.txtApellido.setText("" + conec.rs.getString("apellido_paterno"));
                framePrincipal.txtApellidoM.setText("" + conec.rs.getString("apellido_materno"));
                framePrincipal.txtEdad.setText("" + conec.rs.getString("edad"));
                framePrincipal.txtCurp.setText("" + conec.rs.getString("curp"));
                framePrincipal.txtUsuario.setText("" + conec.rs.getString("usuario"));
                framePrincipal.txtPassword.setText("" + conec.rs.getString("password"));
                framePrincipal.laID.setText("" + conec.rs.getString("id"));
                
                framePrincipal.setVisible(true);
//se llama ala funcion de mostrar cita para que no haga mas codigo aqui                
       
               
                mostrarCitas();
//se desconecta ala bd para no saturar de conexiones                
                conec.desconectar();
            } else {
                JOptionPane.showMessageDialog(null, "UPS ingresaste la contraseña o el usuario incorrecto"
                        + "\n por favor intenta de nuevo");
            }
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
   
   }   
     
     
    private void btnIniciarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarSesionActionPerformed
        iniciarSesion();

    }//GEN-LAST:event_btnIniciarSesionActionPerformed

   
    
    
    private void btn_registrarseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_registrarseActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new jframeRegistrarse().setVisible(true);


    }//GEN-LAST:event_btn_registrarseActionPerformed

    private void btnIniciarSesionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnIniciarSesionMouseEntered
        btnIniciarSesion.setBackground(framePrincipal.colorPrinCuandoSePasaElmouse);
        btnIniciarSesion.setForeground(new java.awt.Color(255, 255, 255));
        btnIniciarSesion.setFont(new java.awt.Font("Cambria", 3, 29));
        // btnIniciar1.setSize(290,47);
        // TODO add your handling code here:
    }//GEN-LAST:event_btnIniciarSesionMouseEntered

    private void btnIniciarSesionMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnIniciarSesionMouseExited
        // TODO add your handling code here:
        btnIniciarSesion.setBackground(framePrincipal.colorCuandoSaleDelMouse);
        btnIniciarSesion.setForeground(new java.awt.Color(0, 0, 72));
        //btnIniciar1.setSize(267,47);
        btnIniciarSesion.setFont(new java.awt.Font("Cambria", 3, 24));

    }//GEN-LAST:event_btnIniciarSesionMouseExited

    private void btn_registrarseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_registrarseMouseEntered
        btn_registrarse.setBackground(framePrincipal.colorPrinCuandoSePasaElmouse);
        btn_registrarse.setForeground(new java.awt.Color(255, 255, 255));

    }//GEN-LAST:event_btn_registrarseMouseEntered

    private void btn_registrarseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_registrarseMouseExited
        btn_registrarse.setBackground(framePrincipal.colorCuandoSaleDelMouse);
        btn_registrarse.setForeground(new java.awt.Color(0, 0, 72));
    }//GEN-LAST:event_btn_registrarseMouseExited

    private void txtFacebookMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtFacebookMouseClicked
        try {
            Desktop.getDesktop().browse(new URI("https://www.youtube.com/watch?v=1yVMYQQlWAQ"));
        } catch (IOException | URISyntaxException e1) {
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtFacebookMouseClicked

    private void txtPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPassActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPassActionPerformed

    private void txtUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsuarioActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        conec.desconectar();
        conec.cierraConsultas();
          System.exit(0);
        
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelImagen;
    private javax.swing.JButton btnIniciarSesion;
    private javax.swing.JButton btn_registrarse;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelLateral;
    private javax.swing.JTextArea txtFacebook;
    private jtextfieldround.JPasswordFieldRound txtPass;
    public jtextfieldround.JTextFieldRound txtUsuario;
    // End of variables declaration//GEN-END:variables

}
