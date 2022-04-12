# Salud-tap-Terminado ⛑

## La base de datos esta en la carpeta BaseDAtos 
## para poder poner la base de datos abra pgADMIN y cree una base de datos y con RESTORE para poder tener la base de datos

#### sistema creado para la materia de interfaces donde se crea un sistema para poder sacar cita, consultar lista y borrar citas con una base de datos realizadas en POSTGRESQL

```java
      if (conec.rs.next()) {
           this.dispose();
           framePrincipal.LabelNombre.setText("Bienvenido " + conec.rs.getString("nombre")+" "+conec.rs.getString("apellido_paterno"));
           framePrincipal.txtNombre.setText("" + conec.rs.getString("nombre"));
           framePrincipal.txtApellido.setText("" + conec.rs.getString("apellido_paterno"));
            framePrincipal.txtApellidoM.setText("" + conec.rs.getString("apellido_materno"));
// usuario: ale 
//Password: 123

   ```

   
   ## Aqui se muestra la pantalla de inicio de sesion bien bonita si o no xd
   
  ![por_si_acaso](pantalla_login.png)
