/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saludtap;

import java.sql.*;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Conectar {

    String user = "postgres";
    String pass = "1234"; //contraseña de postgresql
    String driver = "org.postgres.Driver";
    String url = "jdbc:postgresql://localhost:5432/saludtap";// nombre de la base de datos 
    Connection Connection;
    Statement sentencia;
    ResultSet rs;

    public Conectar() {

        try {
            Class.forName("org.postgresql.Driver");
            Connection = DriverManager.getConnection(url, user, pass);
            //JOptionPane.showMessageDialog(null,"base de datos conectada");
            System.out.println("Base de datos conectada de CONECTAR");
            sentencia = Connection.createStatement();

        } catch (Exception error) {
            System.out.println(error.getMessage());
            JOptionPane.showMessageDialog(null, "error al conectar con errror de: " + error);
            System.out.println("del error de conexion   ");
        }
    }

    public void registrarse(String nombre, String apellido_paterno, String apellido_materno, int edad, String curp, String usuario, String password) {

        try {
            String sql = "INSERT INTO pacientes2 (nombre,apellido_paterno,apellido_materno,edad,curp,usuario,password) VALUES ('" + nombre + "','" + apellido_paterno + "','" + apellido_materno
                    + "'," + edad + ",'" + curp + "','" + usuario + "','" + password + "');";

            //sentencia = Connection.createStatement();
            int x = sentencia.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "ingreso correcto");

        } catch (Exception error) {
            System.out.println("erro de insertar" + error.getMessage());
            JOptionPane.showMessageDialog(null, "error al usuario  = " + error);
            System.out.println("el error de de insertarusario  =" + error);

        }
    }

    public void actualizar(String nombre, String apellido_paterno, String apellido_materno, int edad, String curp, String usuario, String password, int id) {
        String sql2 = "UPDATE pacientes2 SET nombre ='" + nombre + "',apellido_paterno ='" + apellido_paterno + "',apellido_materno ='" + apellido_materno + "',edad="
                + edad + ",curp ='" + curp + "',usuario ='" + usuario + "',password ='" + password + "' where id='" + id + "';";

        try {
           // sentencia = Connection.createStatement();
            int x = sentencia.executeUpdate(sql2);
            JOptionPane.showMessageDialog(null, "actualizacion correcta de datos");

        } catch (Exception error) {
            System.out.println("error de actualizar" + error.getMessage());
            JOptionPane.showMessageDialog(null, "error al actualizar  = " + error);
            System.out.println("el error de de insertarusario  =" + error);

        }

    }

    public void registrarCita(String fecha2, String jhora, String descripcion, int idPaciente) {
        String SQL = "INSERT INTO citas (fecha,hora,descripcion,id_cita_p) VALUES('" + fecha2 + "','" + jhora + "','" + descripcion + "'," + idPaciente + ");";

        try {
           // sentencia = Connection.createStatement();
            int y = sentencia.executeUpdate(SQL);
            JOptionPane.showMessageDialog(null, "cita agendada correctamente");

        } catch (Exception error) {
            System.out.println("erro de insertar de la clase conectar: " + error.getMessage());
            JOptionPane.showMessageDialog(null, "error al agendar cita= " + error);

        }

    }

    public boolean getEstado() throws SQLException {
        return Connection.isClosed();//true-> conexion esta desconectada || false esta conectada
    }

    public void cierraConsultas() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (sentencia != null) {
                sentencia.close();
            }
            if (Connection != null) {
                Connection.close();
            }

            System.out.println("desconectar bd ");
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, "Error cerrando la conexion!", "Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(Conectar.class.getName()).log(Level.SEVERE, null, sqle);
        }
    }

    public void desconectar() {
        try {
            if (Connection != null) {
                 Connection.close();
                 
                 
                if (sentencia != null) {
                    sentencia.close();
                    System.out.println("statement desconectar");
                 
                
                if (rs != null) {
                    rs.close();
                    System.out.println("resultSet desconectar");
                }
                }
               
                 System.out.println("conecttion desconectar");
            }

            if (getEstado() == false) {
                desconectar();
            }
        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(null, "cerrado");

        }

    }

}
