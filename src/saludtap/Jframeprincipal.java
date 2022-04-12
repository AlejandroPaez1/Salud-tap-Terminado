package saludtap;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Jframeprincipal extends javax.swing.JFrame implements MouseListener {
// declaracion de variables  

    prueba pru = new prueba();
    // InicioSesion pru = new InicioSesion();
    Conectar conec = new Conectar();

    String estado;

// colores del boton cuando se hace click y se pasa el puntero del mouse por encima    
    public Color colorPrinCuandoSePasaElmouse = new Color(0, 0, 72);

//para que regrese a su color base, cuando sale del area del mouse
    public Color colorCuandoSaleDelMouse = new Color(255, 255, 255);

//color blanco para el setForegrond
    public Color colorBlanco = new Color(255, 255, 255);

//color negro para el foregroun cuando sale del mouse
    public Color colorNegro = new Color(0, 0, 10);

//para poner icono ala app se pone la direccion del archivo para que cambie alos 3 frames
    public String url = "src/imagenes/iconoCruzroja.png";

    public Jframeprincipal() {
        initComponents();
        iconoAPP();

//agregamos los mouse listener para detectar el mouse   
        btnPanelInformacion.addMouseListener(this);
        btnPanelMisCitas.addMouseListener(this);
        btnPanelNuevaCita.addMouseListener(this);
        btnPanelCancelar.addMouseListener(this);
        btnPanelPerfil.addMouseListener(this);
        txtAcerca.addMouseListener(this);
        btnCancelarCita.addMouseListener(this);
        BtnAgendarCita.addMouseListener(this);
        btnActualizarDatos.addMouseListener(this);
        JtareDescripcion.addMouseListener(this);
        btnBuscarCita.addMouseListener(this);
//agrego el color principal al panel lateral
        PanelLateralAzul.setBackground(colorPrinCuandoSePasaElmouse);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

    }

    private void iconoAPP() {
        //poner icono ala app
        Toolkit laPantalla = Toolkit.getDefaultToolkit();
        Dimension medidas = laPantalla.getScreenSize();
        //ubicacion del archivo desde la url que se encuentra por arriba
        Image iconito = laPantalla.getImage(url);
        //insertamos el icono 
        setIconImage(iconito);

// locacion al centro
        this.setLocationRelativeTo(null);
    }

    
    
    public void cajaCombo() {
        JcomboSeleccionar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"seleccionar"}));
        try {

            String id = this.laID.getText();
            int idPaciente = Integer.valueOf(id);

            String SQL = " select folio from citas where id_cita_p = '" + idPaciente + "';";
            conec.rs = conec.sentencia.executeQuery(SQL);
            if (conec.rs.next()) {
//entra a un while para poder añadir al jcombo
                JcomboSeleccionar.addItem("" + conec.rs.getString("folio"));
                while (conec.rs.next()) {
                    JcomboSeleccionar.addItem("" + conec.rs.getString("folio"));
                    //  System.out.println("imprime los folios de caja combo: " + conec.rs.getString("folio") + "\n");
                }
            } else {
//desactiva los botones para que no busque cita donde no hay                
                JcomboSeleccionar.setEnabled(false);
                btnCancelarCita.setEnabled(false);
                btnBuscarCita.setEnabled(false);
                
//como no hay citas que limpie las citas anteriores
                                JtAMiCita.setText("");
                                JtIResultadoCita.setText("");

                
               JOptionPane.showMessageDialog(null, "no tienes citas"
                     + "\n por favor agrega una cita para poder cancelar su cita");
               
            }
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, "error de combo" + error.getMessage());
        }

    }

    private void AgendarCita() {

        try {
//convertimos a tipo fecha a simple
            String fecha2 = new SimpleDateFormat("dd/MM/yyyy").format(this.Jfecha.getDate());
//seleccionamos del combobox la hora
            String Jhora = JcomboHora.getSelectedItem().toString();

            String descripcion = JtareDescripcion.getText();

            String id = this.laID.getText();
            int idPaciente = Integer.valueOf(id);

            // System.out.println("la fecha es " + fecha2 + " con hora " + Jhora + " con descrip " + descripcion);
            String SQL = "SELECT folio FROM citas WHERE fecha ='" + fecha2 + "' and hora='" + Jhora + "';";

//            String SQL2 = "insert into citas values ('31/12/2020','9pm','balazos',2);";
            conec.rs = conec.sentencia.executeQuery(SQL);

            if (conec.rs.next()) {
                JOptionPane.showMessageDialog(null, "UPS la hora y la fecha esta ocupada seleccione otra fecha por favor");

            } else {
                conec.registrarCita(fecha2, Jhora, descripcion, idPaciente);

                JtIResultadoCita.setText("la cita es el dia: \n"
                        + "\n" + fecha2 + " con hora " + Jhora + "\n"
                        + "\n con el razon de su cita de  \n"
                        + descripcion);
        
                JtAMiCita.append("\n su cita es el dia " + fecha2 + " "
                        + "\n alas " + Jhora + ""
                        + "\n por el motivo de " + descripcion + "\n");

//se vuelve a ejecutar la sentencia query para que muestre el numero de folio
                String SQL2 = "SELECT folio FROM citas WHERE fecha ='" + fecha2 + "' and hora='" + Jhora + "';";
                conec.rs = conec.sentencia.executeQuery(SQL2);
                if (conec.rs.next()) {
                    labelFolio.setText("folio n°: " + conec.rs.getString("folio"));
                    JtIResultadoCita.append("\n \n con folio n°: " + conec.rs.getString("folio") + "\n");
//se manda a poner el folio al textarea de mis citas

                    JtAMiCita.append("con numero de folio n°: " + conec.rs.getString("folio") + "\n");
// se le añade al comboBox la otra cita                    
                    JcomboSeleccionar.addItem("" + conec.rs.getString("folio"));
//se activa los botones por que no tenia cita y se añade una activa                     
                    btnCancelarCita.setEnabled(true);
                    btnBuscarCita.setEnabled(true);
                    JcomboSeleccionar.setEnabled(true);
                }

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Parece que no has seleccionado la fecha todavia \n"
                    + " SELECCIONA UNA POR FAVOR");
            System.out.println("erro de insertar  del catch query " + e.getMessage());

        }

    }

    private void ActualizarDatos() {

        String nombre = this.txtNombre.getText();
        String apellido_paterno = this.txtApellido.getText();
        String apellido_materno = this.txtApellidoM.getText();
        String edad = this.txtEdad.getText();
        //pasamos los valores de tipo string a int
        int edad2 = Integer.valueOf(edad);

        String curp = this.txtCurp.getText();
        String usuario = this.txtUsuario.getText();
        String password = this.txtPassword.getText();

        String id = this.laID.getText();
        int id2 = Integer.valueOf(id);

        conec.actualizar(nombre, apellido_paterno, apellido_materno, edad2, curp, usuario, password, id2);

    }

    private void BuscarCita() {

        try {
            String folio = JcomboSeleccionar.getSelectedItem().toString();
            String SQL = "select fecha,hora,descripcion,folio from citas where folio='" + folio + "';";

            conec.rs = conec.sentencia.executeQuery(SQL);
            if (conec.rs.next()) {

                txtAreaCancelarC.setText("Paciente \n "
                        + " " + txtNombre.getText()
                        + " " + txtApellido.getText()
                        + " " + txtApellidoM.getText()
                        + "\n\nSu cita del dia: \n "
                        + "con fecha de " + conec.rs.getString("fecha")
                        + " alas " + "" + conec.rs.getString("hora") + " \n"
                        + "\ncon el motivo de \n" + conec.rs.getString("descripcion") + "\n"
                        + "\ncon numero de folio: " + ("" + conec.rs.getString("folio")));
            } else {
       
                
// se puede borrar pero no para ver como interactua el sistema 
//JOptionPane.showMessageDialog(null, "no tienes citas COMO VAS A CANCELAR de buscar cita"
//                        + "\n por favor agrega una cita para poder cancelar su cita");
            }
        } catch (SQLException error) {
            String folio2 = JcomboSeleccionar.getSelectedItem().toString();

            if (folio2 == "seleccionar") {
                JOptionPane.showMessageDialog(null, "Selecciona otro campo  que no sea \n "
                        + "la palabra seleccionar \n");
            }
            //JOptionPane.showMessageDialog(null,"error de buscar cita"+ error.getMessage());
        }

    }

    private void BorrarCita() {
        String folio3 = JcomboSeleccionar.getSelectedItem().toString();
        if (folio3 == "seleccionar") {
            JOptionPane.showMessageDialog(rootPane, "seleciona otro que no sea SELECCIONAR", "MENSAJE DE ADVERTENCIA", 1);

        }else{
             BuscarCita();           
     if (JOptionPane.showConfirmDialog(rootPane, "Se eliminará el registro, ¿deseas continuar?",
             "Eliminar Registro", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            
         try {
                
                String folioC = JcomboSeleccionar.getSelectedItem().toString();
                String SQL = " delete from citas where folio = '" + folioC + "';";
                conec.rs = conec.sentencia.executeQuery(SQL);
                 } catch (Exception e) {
                        System.out.println("error de borrar " + e.getMessage());
                }
                    JOptionPane.showMessageDialog(null, "OK acabas de borrar la cita");

        } else {
            System.out.println("cancelar");
        }
     }

    }

    private void estadosSwitch(String estado) {
        switch (estado) {
            case "pushInformacion":
                btnPanelInformacion.setBackground(colorPrinCuandoSePasaElmouse);
                txtInformacion.setForeground(colorBlanco);

//          regresar a default los demas botones           
                btnPanelMisCitas.setBackground(colorCuandoSaleDelMouse);
                txtMisCitas.setForeground(colorNegro);

                btnPanelNuevaCita.setBackground(colorCuandoSaleDelMouse);
                txtNuevaCita.setForeground(colorNegro);

                btnPanelCancelar.setBackground(colorCuandoSaleDelMouse);
                txtCancelarcita.setForeground(colorNegro);

                btnPanelPerfil.setBackground(colorCuandoSaleDelMouse);
                txtMiPerfil.setForeground(colorNegro);

                break;

            case "pushMisCitas":
                btnPanelInformacion.setBackground(colorCuandoSaleDelMouse);
                txtInformacion.setForeground(colorNegro);

                btnPanelMisCitas.setBackground(colorPrinCuandoSePasaElmouse);
                txtMisCitas.setForeground(colorBlanco);

                btnPanelNuevaCita.setBackground(colorCuandoSaleDelMouse);
                txtNuevaCita.setForeground(colorNegro);

                btnPanelCancelar.setBackground(colorCuandoSaleDelMouse);
                txtCancelarcita.setForeground(colorNegro);

                btnPanelPerfil.setBackground(colorCuandoSaleDelMouse);
                txtMiPerfil.setForeground(colorNegro);

                break;
            case "pushNuevaCita":
                btnPanelInformacion.setBackground(colorCuandoSaleDelMouse);
                txtInformacion.setForeground(colorNegro);

                btnPanelMisCitas.setBackground(colorCuandoSaleDelMouse);
                txtMisCitas.setForeground(colorNegro);

                btnPanelNuevaCita.setBackground(colorPrinCuandoSePasaElmouse);
                txtNuevaCita.setForeground(colorBlanco);

                btnPanelCancelar.setBackground(colorCuandoSaleDelMouse);
                txtCancelarcita.setForeground(colorNegro);

                btnPanelPerfil.setBackground(colorCuandoSaleDelMouse);
                txtMiPerfil.setForeground(colorNegro);

                break;

            case "pushCancelar":
                btnPanelInformacion.setBackground(colorCuandoSaleDelMouse);
                txtInformacion.setForeground(colorNegro);

                btnPanelMisCitas.setBackground(colorCuandoSaleDelMouse);
                txtMisCitas.setForeground(colorNegro);

                btnPanelNuevaCita.setBackground(colorCuandoSaleDelMouse);
                txtNuevaCita.setForeground(colorNegro);

                btnPanelCancelar.setBackground(colorPrinCuandoSePasaElmouse);
                txtCancelarcita.setForeground(colorBlanco);

                btnPanelPerfil.setBackground(colorCuandoSaleDelMouse);
                txtMiPerfil.setForeground(colorNegro);

                break;

            case "pushPerfil":
                btnPanelInformacion.setBackground(colorCuandoSaleDelMouse);
                txtInformacion.setForeground(colorNegro);

                btnPanelMisCitas.setBackground(colorCuandoSaleDelMouse);
                txtMisCitas.setForeground(colorNegro);

                btnPanelNuevaCita.setBackground(colorCuandoSaleDelMouse);
                txtNuevaCita.setForeground(colorNegro);

                btnPanelCancelar.setBackground(colorCuandoSaleDelMouse);
                txtCancelarcita.setForeground(colorNegro);

                btnPanelPerfil.setBackground(colorPrinCuandoSePasaElmouse);
                txtMiPerfil.setForeground(colorBlanco);
                break;
            case "default":
                System.out.println("el ottro boton");
                txtAcerca.setBackground(colorPrinCuandoSePasaElmouse);
                txtAcerca.setForeground(colorBlanco);

//          regresar a default los demas botones           
                btnPanelMisCitas.setBackground(colorCuandoSaleDelMouse);
                txtMisCitas.setForeground(colorNegro);

                btnPanelNuevaCita.setBackground(colorCuandoSaleDelMouse);
                txtNuevaCita.setForeground(colorNegro);

                btnPanelCancelar.setBackground(colorCuandoSaleDelMouse);
                txtCancelarcita.setForeground(colorNegro);

                btnPanelPerfil.setBackground(colorCuandoSaleDelMouse);
                txtMiPerfil.setForeground(colorNegro);
                break;

            default:
                System.out.println("error");
        }

    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
        //Detecta el click sobre los paneles 
        if (e.getSource() == btnPanelInformacion) {
            jTabbedPane1.setSelectedIndex(0);
            estadosSwitch("pushInformacion");

        } else if (e.getSource() == btnPanelMisCitas) {
            jTabbedPane1.setSelectedIndex(1);
            estadosSwitch("pushMisCitas");

        } else if (e.getSource() == btnPanelNuevaCita) {
            jTabbedPane1.setSelectedIndex(2);
            estadosSwitch("pushNuevaCita");

        } else if (e.getSource() == btnPanelCancelar) {
            cajaCombo();
            txtAreaCancelarC.setText("Por favor selecciona un numero de folio");
            jTabbedPane1.setSelectedIndex(3);
            estadosSwitch("pushCancelar");

        } else if (e.getSource() == btnPanelPerfil) {
            jTabbedPane1.setSelectedIndex(4);
            estadosSwitch("pushPerfil");
        } else if (e.getSource() == btnPanelInformacion) {
            //preueba del panel
            jTabbedPane1.setSelectedIndex(0);

            estadosSwitch("default");
        } else if (e.getSource() == txtAcerca) {
            jTabbedPane1.setSelectedIndex(5);

        } else if (e.getSource() == JtareDescripcion) {
            JtareDescripcion.setText("");
        }
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {
        if (e.getSource() == btnCancelarCita) {
            //color rojo
            btnCancelarCita.setBackground(new java.awt.Color(255, 26, 27));
            btnCancelarCita.setForeground(colorBlanco);
        } else if (e.getSource() == BtnAgendarCita) {
            BtnAgendarCita.setBackground(new java.awt.Color(50, 130, 102));
            BtnAgendarCita.setForeground(colorBlanco);
            //fuente  || 0=plain 1=negrita 2=cursiva 3=cursivaNegrita || tamaño
            BtnAgendarCita.setFont(new Font("Tw Cen MT", 3, 23));
        } else if (e.getSource() == btnActualizarDatos) {
            btnActualizarDatos.setBackground(colorPrinCuandoSePasaElmouse);
            btnActualizarDatos.setForeground(colorBlanco);
        } else if (e.getSource() == btnBuscarCita) {
            btnBuscarCita.setBackground(new java.awt.Color(50, 130, 102));
            btnBuscarCita.setForeground(colorBlanco);
            btnBuscarCita.setFont(new Font("Tw Cen MT", 3, 23));
        }

        /*
        //cuando pasa el mouse por encima se pone un color azul marino y letra blancas y ajusta el tamaño
        if (e.getSource() == btnPanelInformacion) {
            btnPanelInformacion.setBackground(new Color(0, 0, 72));
            txtInformacion.setForeground(new java.awt.Color(255, 255, 255));
            btnPanelInformacion.setSize(275, 40);

        } else if (e.getSource() == btnPanelMisCitas) {
            btnPanelMisCitas.setBackground(new Color(0, 0, 72));
           txtMisCitas.setForeground(new java.awt.Color(255, 255, 255));
            btnPanelMisCitas.setSize(275, 40);

        } else if (e.getSource() == btnPanelNuevaCita) {
            btnPanelNuevaCita.setBackground(new Color(0, 0, 72));
            txtNuevaCita.setForeground(new java.awt.Color(255, 255, 255));
            btnPanelNuevaCita.setSize(275, 40);

        } else if (e.getSource() == btnPanelCancelar) {
            btnPanelCancelar.setBackground(new Color(0, 0, 72));
            txtCancelarcita.setForeground(new java.awt.Color(255, 255, 255));
            btnPanelCancelar.setSize(275, 40);

        } else if (e.getSource() == btnPanelPerfil) {
            btnPanelPerfil.setBackground(new Color(0, 0, 72));
            txtMiPerfil.setForeground(new java.awt.Color(255, 255, 255));
            btnPanelPerfil.setSize(275, 40);

        }
         */
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {

        //cuando sale del mouse del area del boton se regresa a un color base
        if (e.getSource() == btnCancelarCita) {
            btnCancelarCita.setBackground(colorCuandoSaleDelMouse);
            btnCancelarCita.setForeground(colorNegro);
        } else if (e.getSource() == BtnAgendarCita) {
            BtnAgendarCita.setBackground(colorCuandoSaleDelMouse);
            BtnAgendarCita.setForeground(colorNegro);
            BtnAgendarCita.setFont(new Font("Tw Cen MT", 0, 18));
        }
        if (e.getSource() == btnActualizarDatos) {
            btnActualizarDatos.setBackground(colorCuandoSaleDelMouse);
            btnActualizarDatos.setForeground(colorNegro);
        } else if (e.getSource() == btnBuscarCita) {
            btnBuscarCita.setBackground(colorCuandoSaleDelMouse);
            btnBuscarCita.setForeground(colorNegro);
            btnBuscarCita.setFont(new Font("Tw Cen MT", 0, 18));
        }
        /*     
        if (e.getSource() == btnPanelInformacion) {
            btnPanelInformacion.setBackground(new Color(255, 255, 255));
            txtInformacion.setForeground(colorNegro);
            btnPanelInformacion.setSize(250, 40);
        } else if (e.getSource() == btnPanelMisCitas) {
            btnPanelMisCitas.setBackground(new Color(255, 255, 255));
            txtMisCitas.setForeground(colorNegro);
            btnPanelMisCitas.setSize(250, 40);
        } else if (e.getSource() == btnPanelNuevaCita) {
            btnPanelNuevaCita.setBackground(new Color(255, 255, 255));
            txtNuevaCita.setForeground(colorNegro);
            btnPanelNuevaCita.setSize(250, 40);
        } else if (e.getSource() == btnPanelCancelar) {
            btnPanelCancelar.setBackground(new Color(255, 255, 255));
            txtCancelarcita.setForeground(colorNegro);
            btnPanelCancelar.setSize(250, 40);
        } else if (e.getSource() == btnPanelPerfil) {
            btnPanelPerfil.setBackground(new Color(255, 255, 255));
            txtMiPerfil.setForeground(colorNegro);
            btnPanelPerfil.setSize(250, 40);
        }
        
         */

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        PanelLateralAzul = new javax.swing.JPanel();
        imagenSaludTap = new javax.swing.JLabel();
        btnPanelInformacion = new javax.swing.JPanel();
        txtInformacion = new javax.swing.JLabel();
        btnPanelMisCitas = new javax.swing.JPanel();
        txtMisCitas = new javax.swing.JLabel();
        btnPanelNuevaCita = new javax.swing.JPanel();
        txtNuevaCita = new javax.swing.JLabel();
        btnPanelCancelar = new javax.swing.JPanel();
        txtCancelarcita = new javax.swing.JLabel();
        btnPanelPerfil = new javax.swing.JPanel();
        txtMiPerfil = new javax.swing.JLabel();
        panelprueba2 = new javax.swing.JPanel();
        btnRegresarARegistrarse = new javax.swing.JButton();
        txtAcerca = new jtextfieldround.JTextFieldRound();
        contieneTabbed = new javax.swing.JPanel();
        panelcabecera = new javax.swing.JPanel();
        LabelNombre = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        p1Informacion = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        P2misCitas = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jPanel27 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        JtAMiCita = new javax.swing.JTextArea();
        p3NuevaCita = new javax.swing.JPanel();
        contenedor2 = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        JcomboHora = new javax.swing.JComboBox<>();
        BtnAgendarCita = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        JtareDescripcion = new javax.swing.JTextArea();
        Jfecha = new com.toedter.calendar.JDateChooser();
        jPanel29 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        JtIResultadoCita = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        labelFolio = new javax.swing.JLabel();
        p4CancelarCita = new javax.swing.JPanel();
        jPanel30 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        JcomboSeleccionar = new javax.swing.JComboBox<>();
        jPanel31 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        txtAreaCancelarC = new javax.swing.JTextArea();
        btnCancelarCita = new javax.swing.JButton();
        btnBuscarCita = new javax.swing.JButton();
        p5Perfil = new javax.swing.JPanel();
        panelPerfilDatos = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtApellidoM = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtEdad = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtCurp = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtPassword = new jtextfieldround.JPasswordFieldRound();
        btnActualizarDatos = new javax.swing.JButton();
        laID = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SALUD TAP");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(120, 192, 224));
        jPanel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel2.setInheritsPopupMenu(true);
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PanelLateralAzul.setBackground(new java.awt.Color(0, 0, 51));

        javax.swing.GroupLayout PanelLateralAzulLayout = new javax.swing.GroupLayout(PanelLateralAzul);
        PanelLateralAzul.setLayout(PanelLateralAzulLayout);
        PanelLateralAzulLayout.setHorizontalGroup(
            PanelLateralAzulLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        PanelLateralAzulLayout.setVerticalGroup(
            PanelLateralAzulLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 710, Short.MAX_VALUE)
        );

        jPanel2.add(PanelLateralAzul, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        imagenSaludTap.setBackground(new java.awt.Color(255, 255, 255));
        imagenSaludTap.setFont(new java.awt.Font("Book Antiqua", 3, 36)); // NOI18N
        imagenSaludTap.setForeground(new java.awt.Color(255, 255, 255));
        imagenSaludTap.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imagenSaludTap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/blancologosaludtapdos.png"))); // NOI18N
        imagenSaludTap.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        imagenSaludTap.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        imagenSaludTap.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel2.add(imagenSaludTap, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 280, 100));

        btnPanelInformacion.setBackground(new java.awt.Color(255, 255, 255));
        btnPanelInformacion.setForeground(new java.awt.Color(255, 255, 255));
        btnPanelInformacion.setEnabled(false);
        btnPanelInformacion.setRequestFocusEnabled(false);
        btnPanelInformacion.setVerifyInputWhenFocusTarget(false);
        btnPanelInformacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPanelInformacionMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPanelInformacionMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPanelInformacionMouseExited(evt);
            }
        });

        txtInformacion.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 36)); // NOI18N
        txtInformacion.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtInformacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/expediente.png"))); // NOI18N
        txtInformacion.setText("Informacion");
        txtInformacion.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout btnPanelInformacionLayout = new javax.swing.GroupLayout(btnPanelInformacion);
        btnPanelInformacion.setLayout(btnPanelInformacionLayout);
        btnPanelInformacionLayout.setHorizontalGroup(
            btnPanelInformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnPanelInformacionLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(txtInformacion, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnPanelInformacionLayout.setVerticalGroup(
            btnPanelInformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtInformacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel2.add(btnPanelInformacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 250, 40));

        btnPanelMisCitas.setBackground(new java.awt.Color(255, 255, 255));
        btnPanelMisCitas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPanelMisCitasMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPanelMisCitasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPanelMisCitasMouseExited(evt);
            }
        });

        txtMisCitas.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 36)); // NOI18N
        txtMisCitas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/calendario.png"))); // NOI18N
        txtMisCitas.setText("Mis citas");

        javax.swing.GroupLayout btnPanelMisCitasLayout = new javax.swing.GroupLayout(btnPanelMisCitas);
        btnPanelMisCitas.setLayout(btnPanelMisCitasLayout);
        btnPanelMisCitasLayout.setHorizontalGroup(
            btnPanelMisCitasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnPanelMisCitasLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(txtMisCitas, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(61, Short.MAX_VALUE))
        );
        btnPanelMisCitasLayout.setVerticalGroup(
            btnPanelMisCitasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMisCitas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel2.add(btnPanelMisCitas, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, -1, 40));

        btnPanelNuevaCita.setBackground(new java.awt.Color(255, 255, 255));
        btnPanelNuevaCita.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPanelNuevaCitaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPanelNuevaCitaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPanelNuevaCitaMouseExited(evt);
            }
        });

        txtNuevaCita.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 36)); // NOI18N
        txtNuevaCita.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/001-cita.png"))); // NOI18N
        txtNuevaCita.setText("Agendar  cita");

        javax.swing.GroupLayout btnPanelNuevaCitaLayout = new javax.swing.GroupLayout(btnPanelNuevaCita);
        btnPanelNuevaCita.setLayout(btnPanelNuevaCitaLayout);
        btnPanelNuevaCitaLayout.setHorizontalGroup(
            btnPanelNuevaCitaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnPanelNuevaCitaLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(txtNuevaCita, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        btnPanelNuevaCitaLayout.setVerticalGroup(
            btnPanelNuevaCitaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnPanelNuevaCitaLayout.createSequentialGroup()
                .addGap(0, 1, Short.MAX_VALUE)
                .addComponent(txtNuevaCita))
        );

        jPanel2.add(btnPanelNuevaCita, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, -1, -1));

        btnPanelCancelar.setBackground(new java.awt.Color(255, 255, 255));
        btnPanelCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPanelCancelarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPanelCancelarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPanelCancelarMouseExited(evt);
            }
        });

        txtCancelarcita.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 36)); // NOI18N
        txtCancelarcita.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/001-nombramiento.png"))); // NOI18N
        txtCancelarcita.setText("Cancelar cita");

        javax.swing.GroupLayout btnPanelCancelarLayout = new javax.swing.GroupLayout(btnPanelCancelar);
        btnPanelCancelar.setLayout(btnPanelCancelarLayout);
        btnPanelCancelarLayout.setHorizontalGroup(
            btnPanelCancelarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnPanelCancelarLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(txtCancelarcita, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(51, Short.MAX_VALUE))
        );
        btnPanelCancelarLayout.setVerticalGroup(
            btnPanelCancelarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtCancelarcita, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel2.add(btnPanelCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, -1, 40));

        btnPanelPerfil.setBackground(new java.awt.Color(255, 255, 255));
        btnPanelPerfil.setForeground(new java.awt.Color(102, 102, 255));
        btnPanelPerfil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPanelPerfilMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPanelPerfilMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPanelPerfilMouseExited(evt);
            }
        });

        txtMiPerfil.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 36)); // NOI18N
        txtMiPerfil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/002-usuario.png"))); // NOI18N
        txtMiPerfil.setText("Mi perfil");

        javax.swing.GroupLayout btnPanelPerfilLayout = new javax.swing.GroupLayout(btnPanelPerfil);
        btnPanelPerfil.setLayout(btnPanelPerfilLayout);
        btnPanelPerfilLayout.setHorizontalGroup(
            btnPanelPerfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnPanelPerfilLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(txtMiPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );
        btnPanelPerfilLayout.setVerticalGroup(
            btnPanelPerfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnPanelPerfilLayout.createSequentialGroup()
                .addComponent(txtMiPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 4, Short.MAX_VALUE))
        );

        jPanel2.add(btnPanelPerfil, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, -1, -1));

        panelprueba2.setBackground(new java.awt.Color(255, 255, 51));

        btnRegresarARegistrarse.setText("Registrarse");
        btnRegresarARegistrarse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarARegistrarseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelprueba2Layout = new javax.swing.GroupLayout(panelprueba2);
        panelprueba2.setLayout(panelprueba2Layout);
        panelprueba2Layout.setHorizontalGroup(
            panelprueba2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelprueba2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnRegresarARegistrarse, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );
        panelprueba2Layout.setVerticalGroup(
            panelprueba2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelprueba2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnRegresarARegistrarse, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.add(panelprueba2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 450, 120, 60));

        txtAcerca.setText("ACERCA DE");
        txtAcerca.setFocusable(false);
        txtAcerca.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 36)); // NOI18N
        jPanel2.add(txtAcerca, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 530, 270, 40));

        contieneTabbed.setBackground(new java.awt.Color(102, 255, 102));

        panelcabecera.setBackground(new java.awt.Color(255, 255, 255));

        LabelNombre.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 24)); // NOI18N
        LabelNombre.setText("-");

        javax.swing.GroupLayout panelcabeceraLayout = new javax.swing.GroupLayout(panelcabecera);
        panelcabecera.setLayout(panelcabeceraLayout);
        panelcabeceraLayout.setHorizontalGroup(
            panelcabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelcabeceraLayout.createSequentialGroup()
                .addGap(130, 130, 130)
                .addComponent(LabelNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelcabeceraLayout.setVerticalGroup(
            panelcabeceraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(LabelNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
        );

        jTabbedPane1.setToolTipText("");
        jTabbedPane1.setEnabled(false);
        jTabbedPane1.setVerifyInputWhenFocusTarget(false);

        jPanel24.setBackground(new java.awt.Color(255, 247, 191));
        jPanel24.setForeground(new java.awt.Color(204, 255, 255));

        jLabel20.setFont(new java.awt.Font("Verdana", 0, 24)); // NOI18N
        jLabel20.setText("Estadisticas de la tasa de mortandad");

        jLabel2.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        jLabel2.setText("Grafico de pastel muertes por covid");

        jLabel21.setText("CHIAPAS MEXICO");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/grafica (1).png"))); // NOI18N

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/COVID.png"))); // NOI18N

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/vacuna.png"))); // NOI18N
        jLabel7.setText("jLabel7");

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 354, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(40, 40, 40))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(24, 24, 24)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout p1InformacionLayout = new javax.swing.GroupLayout(p1Informacion);
        p1Informacion.setLayout(p1InformacionLayout);
        p1InformacionLayout.setHorizontalGroup(
            p1InformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p1InformacionLayout.createSequentialGroup()
                .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        p1InformacionLayout.setVerticalGroup(
            p1InformacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab1", p1Informacion);

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));

        jLabel22.setBackground(new java.awt.Color(255, 255, 255));
        jLabel22.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(102, 102, 102));
        jLabel22.setText("S  U  S     C  I  T  A  S     S  O  N:");

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(364, 364, 364))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(23, 23, 23))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        JtAMiCita.setColumns(20);
        JtAMiCita.setRows(5);
        jScrollPane3.setViewportView(JtAMiCita);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane3)
                .addGap(36, 36, 36))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(31, 31, 31))
        );

        javax.swing.GroupLayout P2misCitasLayout = new javax.swing.GroupLayout(P2misCitas);
        P2misCitas.setLayout(P2misCitasLayout);
        P2misCitasLayout.setHorizontalGroup(
            P2misCitasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(P2misCitasLayout.createSequentialGroup()
                .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        P2misCitasLayout.setVerticalGroup(
            P2misCitasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(P2misCitasLayout.createSequentialGroup()
                .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );

        jTabbedPane1.addTab("tab2", P2misCitas);

        contenedor2.setBackground(new java.awt.Color(255, 255, 255));
        contenedor2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Bienvenido saque su cita ahora", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        jPanel28.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel23.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(153, 153, 153));
        jLabel23.setText("Seleccione una fecha:");

        jLabel24.setFont(new java.awt.Font("Trebuchet MS", 1, 24)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(153, 153, 153));
        jLabel24.setText("Seleccione una hora:");

        JcomboHora.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 24)); // NOI18N
        JcomboHora.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "9am", "10am", "12pm", "1pm" }));
        JcomboHora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JcomboHoraActionPerformed(evt);
            }
        });

        BtnAgendarCita.setBackground(new java.awt.Color(255, 255, 255));
        BtnAgendarCita.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        BtnAgendarCita.setText("Agendar Cita");
        BtnAgendarCita.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BtnAgendarCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAgendarCitaActionPerformed(evt);
            }
        });

        JtareDescripcion.setColumns(20);
        JtareDescripcion.setRows(5);
        JtareDescripcion.setText("por favor escriba el\n motivo de su consulta");
        JtareDescripcion.setToolTipText("");
        JtareDescripcion.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Razon de su cita", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tw Cen MT Condensed", 0, 18))); // NOI18N
        jScrollPane1.setViewportView(JtareDescripcion);
        JtareDescripcion.getAccessibleContext().setAccessibleDescription("escriba la razon de su cita");

        Jfecha.setBackground(new java.awt.Color(51, 255, 51));
        Jfecha.setForeground(new java.awt.Color(204, 0, 51));
        Jfecha.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 16)); // NOI18N

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Jfecha, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE))
                        .addGap(23, 23, 23)))
                .addGap(73, 73, 73)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BtnAgendarCita, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                    .addComponent(JcomboHora, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(49, 49, 49))
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jLabel24))
                .addGap(18, 18, 18)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Jfecha, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JcomboHora, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13))
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(BtnAgendarCita, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setText("INFORMACIÓN DE SU CITA");

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setForeground(new java.awt.Color(255, 255, 255));

        JtIResultadoCita.setEditable(false);
        JtIResultadoCita.setColumns(20);
        JtIResultadoCita.setRows(5);
        JtIResultadoCita.setToolTipText("");
        jScrollPane2.setViewportView(JtIResultadoCita);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel29Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 197, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 102, 102));
        jLabel3.setText("Recuerde llegar 15 mins antes");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGap(0, 15, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel6.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 102, 102));
        jLabel6.setText("No pierda su número de FOLIO");

        labelFolio.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        labelFolio.setForeground(new java.awt.Color(0, 102, 102));
        labelFolio.setText("folio n°: ");

        javax.swing.GroupLayout contenedor2Layout = new javax.swing.GroupLayout(contenedor2);
        contenedor2.setLayout(contenedor2Layout);
        contenedor2Layout.setHorizontalGroup(
            contenedor2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contenedor2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(19, 19, 19))
            .addGroup(contenedor2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(contenedor2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(contenedor2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(contenedor2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)))
                    .addGroup(contenedor2Layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addComponent(labelFolio, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        contenedor2Layout.setVerticalGroup(
            contenedor2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contenedor2Layout.createSequentialGroup()
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(contenedor2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(contenedor2Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelFolio, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(172, Short.MAX_VALUE))
                    .addGroup(contenedor2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        javax.swing.GroupLayout p3NuevaCitaLayout = new javax.swing.GroupLayout(p3NuevaCita);
        p3NuevaCita.setLayout(p3NuevaCitaLayout);
        p3NuevaCitaLayout.setHorizontalGroup(
            p3NuevaCitaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contenedor2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        p3NuevaCitaLayout.setVerticalGroup(
            p3NuevaCitaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contenedor2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab3", p3NuevaCita);

        jPanel30.setBackground(new java.awt.Color(255, 255, 255));

        jLabel25.setBackground(new java.awt.Color(255, 255, 255));
        jLabel25.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(153, 153, 153));
        jLabel25.setText("Seleccione la cita que quiera cancelar busqueda por folio :");

        JcomboSeleccionar.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        JcomboSeleccionar.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(204, 255, 255), new java.awt.Color(204, 204, 255)));
        JcomboSeleccionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JcomboSeleccionarActionPerformed(evt);
            }
        });

        txtAreaCancelarC.setEditable(false);
        txtAreaCancelarC.setColumns(20);
        txtAreaCancelarC.setFont(new java.awt.Font("Microsoft JhengHei", 1, 14)); // NOI18N
        txtAreaCancelarC.setRows(5);
        txtAreaCancelarC.setText("datos de la cita seleccionada");
        jScrollPane6.setViewportView(txtAreaCancelarC);

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6)
                .addContainerGap())
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnCancelarCita.setBackground(new java.awt.Color(255, 255, 255));
        btnCancelarCita.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        btnCancelarCita.setText("BORRAR CITA");
        btnCancelarCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarCitaActionPerformed(evt);
            }
        });

        btnBuscarCita.setBackground(new java.awt.Color(255, 255, 255));
        btnBuscarCita.setFont(new java.awt.Font("Arial", 3, 14)); // NOI18N
        btnBuscarCita.setText("BUSCAR CITA");
        btnBuscarCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarCitaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel30Layout.createSequentialGroup()
                        .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(14, 14, 14))
                    .addGroup(jPanel30Layout.createSequentialGroup()
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(JcomboSeleccionar, 0, 126, Short.MAX_VALUE)
                        .addGap(14, 14, 14)
                        .addComponent(btnBuscarCita, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                        .addGap(20, 20, 20))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel30Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancelarCita, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JcomboSeleccionar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarCita, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCancelarCita, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout p4CancelarCitaLayout = new javax.swing.GroupLayout(p4CancelarCita);
        p4CancelarCita.setLayout(p4CancelarCitaLayout);
        p4CancelarCitaLayout.setHorizontalGroup(
            p4CancelarCitaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        p4CancelarCitaLayout.setVerticalGroup(
            p4CancelarCitaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab4", p4CancelarCita);

        panelPerfilDatos.setBackground(new java.awt.Color(0, 255, 204));

        jPanel23.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Actualizar datos", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Malgun Gothic Semilight", 3, 14))); // NOI18N
        jPanel23.setLayout(new javax.swing.BoxLayout(jPanel23, javax.swing.BoxLayout.PAGE_AXIS));

        jLabel13.setBackground(new java.awt.Color(204, 204, 204));
        jLabel13.setFont(new java.awt.Font("Verdana", 3, 14)); // NOI18N
        jLabel13.setText("Nombre");
        jLabel13.setOpaque(true);
        jPanel23.add(jLabel13);

        txtNombre.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 18)); // NOI18N
        txtNombre.setText("       ");
        txtNombre.setToolTipText("");
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });
        jPanel23.add(txtNombre);

        jLabel14.setBackground(new java.awt.Color(204, 204, 204));
        jLabel14.setFont(new java.awt.Font("Verdana", 3, 14)); // NOI18N
        jLabel14.setText("Apellido paterno");
        jLabel14.setOpaque(true);
        jPanel23.add(jLabel14);

        txtApellido.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 18)); // NOI18N
        txtApellido.setText("       ");
        txtApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApellidoActionPerformed(evt);
            }
        });
        jPanel23.add(txtApellido);

        jLabel15.setBackground(new java.awt.Color(204, 204, 204));
        jLabel15.setFont(new java.awt.Font("Verdana", 3, 14)); // NOI18N
        jLabel15.setText("Apellido Materno");
        jLabel15.setOpaque(true);
        jPanel23.add(jLabel15);

        txtApellidoM.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 18)); // NOI18N
        txtApellidoM.setText("       ");
        txtApellidoM.setToolTipText("");
        txtApellidoM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApellidoMActionPerformed(evt);
            }
        });
        jPanel23.add(txtApellidoM);

        jLabel16.setBackground(new java.awt.Color(204, 204, 204));
        jLabel16.setFont(new java.awt.Font("Verdana", 3, 14)); // NOI18N
        jLabel16.setText("Edad");
        jLabel16.setOpaque(true);
        jPanel23.add(jLabel16);

        txtEdad.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 18)); // NOI18N
        txtEdad.setText("       ");
        txtEdad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEdadActionPerformed(evt);
            }
        });
        jPanel23.add(txtEdad);

        jLabel17.setBackground(new java.awt.Color(204, 204, 204));
        jLabel17.setFont(new java.awt.Font("Verdana", 3, 14)); // NOI18N
        jLabel17.setText("Curp");
        jLabel17.setOpaque(true);
        jPanel23.add(jLabel17);

        txtCurp.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 18)); // NOI18N
        txtCurp.setText("       ");
        txtCurp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCurpActionPerformed(evt);
            }
        });
        jPanel23.add(txtCurp);

        jLabel18.setBackground(new java.awt.Color(204, 204, 204));
        jLabel18.setFont(new java.awt.Font("Verdana", 3, 14)); // NOI18N
        jLabel18.setText("usuario");
        jLabel18.setOpaque(true);
        jPanel23.add(jLabel18);

        txtUsuario.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 18)); // NOI18N
        txtUsuario.setText("       ");
        txtUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuarioActionPerformed(evt);
            }
        });
        jPanel23.add(txtUsuario);

        jLabel19.setBackground(new java.awt.Color(204, 204, 204));
        jLabel19.setFont(new java.awt.Font("Verdana", 3, 14)); // NOI18N
        jLabel19.setText("Contraseña");
        jLabel19.setOpaque(true);
        jPanel23.add(jLabel19);
        jPanel23.add(txtPassword);

        btnActualizarDatos.setBackground(new java.awt.Color(255, 255, 255));
        btnActualizarDatos.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 24)); // NOI18N
        btnActualizarDatos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/iniciar-sesion.png"))); // NOI18N
        btnActualizarDatos.setText("Actualizar datos");
        btnActualizarDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarDatosActionPerformed(evt);
            }
        });

        laID.setText("-");

        javax.swing.GroupLayout panelPerfilDatosLayout = new javax.swing.GroupLayout(panelPerfilDatos);
        panelPerfilDatos.setLayout(panelPerfilDatosLayout);
        panelPerfilDatosLayout.setHorizontalGroup(
            panelPerfilDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPerfilDatosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, 702, Short.MAX_VALUE)
                .addGap(18, 18, 18))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPerfilDatosLayout.createSequentialGroup()
                .addGap(271, 271, 271)
                .addComponent(laID, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(116, 116, 116)
                .addComponent(btnActualizarDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(36, 36, 36))
        );
        panelPerfilDatosLayout.setVerticalGroup(
            panelPerfilDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPerfilDatosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, 423, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelPerfilDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnActualizarDatos, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                    .addComponent(laID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout p5PerfilLayout = new javax.swing.GroupLayout(p5Perfil);
        p5Perfil.setLayout(p5PerfilLayout);
        p5PerfilLayout.setHorizontalGroup(
            p5PerfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p5PerfilLayout.createSequentialGroup()
                .addComponent(panelPerfilDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        p5PerfilLayout.setVerticalGroup(
            p5PerfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPerfilDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab5", p5Perfil);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setText("Desarroladores: Alejandro Paez Perez y Pablo Ramirez Antonio");

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/LOGOXD.png"))); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(227, Short.MAX_VALUE)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 430, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab6", jPanel3);

        javax.swing.GroupLayout contieneTabbedLayout = new javax.swing.GroupLayout(contieneTabbed);
        contieneTabbed.setLayout(contieneTabbedLayout);
        contieneTabbedLayout.setHorizontalGroup(
            contieneTabbedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelcabecera, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 735, Short.MAX_VALUE)
        );
        contieneTabbedLayout.setVerticalGroup(
            contieneTabbedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contieneTabbedLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jTabbedPane1))
            .addGroup(contieneTabbedLayout.createSequentialGroup()
                .addComponent(panelcabecera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(contieneTabbed, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(contieneTabbed, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void btnPanelInformacionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPanelInformacionMouseClicked

    }//GEN-LAST:event_btnPanelInformacionMouseClicked

    private void btnPanelMisCitasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPanelMisCitasMouseClicked

    }//GEN-LAST:event_btnPanelMisCitasMouseClicked

    private void btnPanelNuevaCitaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPanelNuevaCitaMouseClicked

    }//GEN-LAST:event_btnPanelNuevaCitaMouseClicked

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void txtApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtApellidoActionPerformed

    private void txtApellidoMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApellidoMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtApellidoMActionPerformed

    private void txtEdadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEdadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEdadActionPerformed

    private void txtCurpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCurpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCurpActionPerformed

    private void txtUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsuarioActionPerformed

    private void btnCancelarCitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarCitaActionPerformed

        BorrarCita();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelarCitaActionPerformed

    private void btnPanelCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPanelCancelarMouseClicked

    }//GEN-LAST:event_btnPanelCancelarMouseClicked

    private void btnPanelPerfilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPanelPerfilMouseClicked

    }//GEN-LAST:event_btnPanelPerfilMouseClicked

    private void btnPanelPerfilMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPanelPerfilMouseEntered

    }//GEN-LAST:event_btnPanelPerfilMouseEntered

    private void btnPanelPerfilMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPanelPerfilMouseExited

        //btnPanel5.setBackground(new Color(0, 255, 204));

    }//GEN-LAST:event_btnPanelPerfilMouseExited

    private void btnPanelInformacionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPanelInformacionMouseEntered

        // TODO add your handling code here:
    }//GEN-LAST:event_btnPanelInformacionMouseEntered


    private void btnPanelInformacionMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPanelInformacionMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPanelInformacionMouseExited

    private void btnPanelMisCitasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPanelMisCitasMouseEntered

        // TODO add your handling code here:
    }//GEN-LAST:event_btnPanelMisCitasMouseEntered

    private void btnPanelMisCitasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPanelMisCitasMouseExited

    }//GEN-LAST:event_btnPanelMisCitasMouseExited

    private void btnPanelNuevaCitaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPanelNuevaCitaMouseEntered

    }//GEN-LAST:event_btnPanelNuevaCitaMouseEntered


    private void btnPanelNuevaCitaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPanelNuevaCitaMouseExited

    }//GEN-LAST:event_btnPanelNuevaCitaMouseExited

    private void btnPanelCancelarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPanelCancelarMouseEntered

    }//GEN-LAST:event_btnPanelCancelarMouseEntered

    private void btnPanelCancelarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPanelCancelarMouseExited

    }//GEN-LAST:event_btnPanelCancelarMouseExited

    private void btnRegresarARegistrarseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarARegistrarseActionPerformed
        this.dispose();
        jframeRegistrarse registro = new jframeRegistrarse();
//        new jframeRegistrarse().setVisible(true);

        registro.setVisible(true);
    }//GEN-LAST:event_btnRegresarARegistrarseActionPerformed


    private void BtnAgendarCitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAgendarCitaActionPerformed

        AgendarCita();

    }//GEN-LAST:event_BtnAgendarCitaActionPerformed

    private void JcomboHoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JcomboHoraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JcomboHoraActionPerformed


    private void btnActualizarDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarDatosActionPerformed

        ActualizarDatos();

    }//GEN-LAST:event_btnActualizarDatosActionPerformed


    private void btnBuscarCitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarCitaActionPerformed
        // TODO add your handling code here:
        BuscarCita();

    }//GEN-LAST:event_btnBuscarCitaActionPerformed

    private void JcomboSeleccionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JcomboSeleccionarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JcomboSeleccionarActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed

        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

        conec.desconectar();
        System.exit(0);
        conec.cierraConsultas();

        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton BtnAgendarCita;
    public javax.swing.JComboBox<String> JcomboHora;
    public javax.swing.JComboBox<String> JcomboSeleccionar;
    public com.toedter.calendar.JDateChooser Jfecha;
    public javax.swing.JTextArea JtAMiCita;
    public javax.swing.JTextArea JtIResultadoCita;
    public javax.swing.JTextArea JtareDescripcion;
    public javax.swing.JLabel LabelNombre;
    public javax.swing.JPanel P2misCitas;
    public javax.swing.JPanel PanelLateralAzul;
    public javax.swing.JButton btnActualizarDatos;
    public javax.swing.JButton btnBuscarCita;
    public javax.swing.JButton btnCancelarCita;
    public javax.swing.JPanel btnPanelCancelar;
    public javax.swing.JPanel btnPanelInformacion;
    public javax.swing.JPanel btnPanelMisCitas;
    public javax.swing.JPanel btnPanelNuevaCita;
    public javax.swing.JPanel btnPanelPerfil;
    public javax.swing.JButton btnRegresarARegistrarse;
    public javax.swing.JPanel contenedor2;
    public javax.swing.JPanel contieneTabbed;
    public javax.swing.JLabel imagenSaludTap;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel10;
    public javax.swing.JLabel jLabel13;
    public javax.swing.JLabel jLabel14;
    public javax.swing.JLabel jLabel15;
    public javax.swing.JLabel jLabel16;
    public javax.swing.JLabel jLabel17;
    public javax.swing.JLabel jLabel18;
    public javax.swing.JLabel jLabel19;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel20;
    public javax.swing.JLabel jLabel21;
    public javax.swing.JLabel jLabel22;
    public javax.swing.JLabel jLabel23;
    public javax.swing.JLabel jLabel24;
    public javax.swing.JLabel jLabel25;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JLabel jLabel6;
    public javax.swing.JLabel jLabel7;
    public javax.swing.JLabel jLabel9;
    public javax.swing.JPanel jPanel2;
    public javax.swing.JPanel jPanel23;
    public javax.swing.JPanel jPanel24;
    public javax.swing.JPanel jPanel25;
    public javax.swing.JPanel jPanel26;
    public javax.swing.JPanel jPanel27;
    public javax.swing.JPanel jPanel28;
    public javax.swing.JPanel jPanel29;
    public javax.swing.JPanel jPanel3;
    public javax.swing.JPanel jPanel30;
    public javax.swing.JPanel jPanel31;
    public javax.swing.JPanel jPanel4;
    public javax.swing.JPanel jPanel5;
    public javax.swing.JPanel jPanel6;
    public javax.swing.JPanel jPanel7;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JScrollPane jScrollPane6;
    public javax.swing.JTabbedPane jTabbedPane1;
    public javax.swing.JLabel laID;
    public javax.swing.JLabel labelFolio;
    public javax.swing.JPanel p1Informacion;
    public javax.swing.JPanel p3NuevaCita;
    public javax.swing.JPanel p4CancelarCita;
    public javax.swing.JPanel p5Perfil;
    public javax.swing.JPanel panelPerfilDatos;
    public javax.swing.JPanel panelcabecera;
    public javax.swing.JPanel panelprueba2;
    public jtextfieldround.JTextFieldRound txtAcerca;
    public javax.swing.JTextField txtApellido;
    public javax.swing.JTextField txtApellidoM;
    public javax.swing.JTextArea txtAreaCancelarC;
    public javax.swing.JLabel txtCancelarcita;
    public javax.swing.JTextField txtCurp;
    public javax.swing.JTextField txtEdad;
    public javax.swing.JLabel txtInformacion;
    public javax.swing.JLabel txtMiPerfil;
    public javax.swing.JLabel txtMisCitas;
    public javax.swing.JTextField txtNombre;
    public javax.swing.JLabel txtNuevaCita;
    public jtextfieldround.JPasswordFieldRound txtPassword;
    public javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {

    }

}
