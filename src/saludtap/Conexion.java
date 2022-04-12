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
import javax.swing.JOptionPane;

public class Conexion {

    String user = "postgres";
    String pass = "1234"; //contraseña de postgresql
    String driver = "org.postgres.Driver";
    String url = "jdbc:postgresql://localhost:5432/saludtap";// nombre de la base de datos 
    Connection Connection;
    Statement sentencia;
    ResultSet rs;
    //ResultSet resultado;
   // Jframeprincipal framePrincipal = new Jframeprincipal();
   // Jfiniciosesion iniciosesion = new Jfiniciosesion();

    public Conexion() {

        try {
            Class.forName("org.postgresql.Driver");
            Connection = DriverManager.getConnection(url, user, pass);
            //JOptionPane.showMessageDialog(null,"base de datos conectada");
            System.out.println("Base de datos conectada conexion.java");
            sentencia = Connection.createStatement();

        } catch (Exception error) {
            System.out.println(error.getMessage());
            JOptionPane.showMessageDialog(null, "error al conectar con errror de: " + error);
            System.out.println("del error de conexion   ");
        }
    }

  
    public void registrarse(String nombre, String apellido_paterno, String apellido_materno, int edad, String curp, String usuario, String password) {
        String sql = "INSERT INTO pacientes (nombre,apellido_paterno,apellido_materno,edad,curp,usuario,password) VALUES ('" + nombre + "','" + apellido_paterno + "','" + apellido_materno
                + "'," + edad + ",'" + curp + "','" + usuario + "','" + password + "');";

        try {
            sentencia = Connection.createStatement();
            int x = sentencia.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "ingreso correcto");

        } catch (Exception error) {
            System.out.println("erro de insertar" + error.getMessage());
            JOptionPane.showMessageDialog(null, "error al usuario  = " + error);
            System.out.println("el error de de insertarusario  =" + error);

        }
    }
}
