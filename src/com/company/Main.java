package com.company;

import java.io.*;
import java.sql.*;
import java.util.Properties;

public class Main {
    static final String NOMBRE_FICHERO_PROPIEDADES = "config.properties";
    private static final String CONSULTA_TABLA_CLIENTES = "SELECT * FROM Personas";
    private static Properties propiedadesConexion= new Properties();
    private static Connection c;
    private static Statement query = null;
    private static ResultSet resultado;
    static String DRIVER = "com.mysql.jdbc.Driver";
    static String URL = "jdbc:sqlserver://localhost:51916;database=ConexionBDD";
    static String USUARIO = "alvarolruiz";
    static String CLAVE = "mitesoro99";

    public static void main(String[] args) {


        if (!new File(NOMBRE_FICHERO_PROPIEDADES).exists()) {
            generarFicheroConfig();
        } else {
            cargarFicheroConfig();
        }
        c= establecerConexion();

        try {

            int i=1;
            while(resultado.next()){
                System.out.println("["+(i++)+"]");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private static void selectPersonas() throws SQLException {
        query = c.createStatement();
        resultado=query.executeQuery(CONSULTA_TABLA_CLIENTES);
        int count = 0;

        while (resultado.next()){
            int id = resultado.getInt("IDPersona");
            String nombre = resultado.getString(2);
            String apellido = resultado.getString("apellidosPersona");
            Date fecha = resultado.getDate("fechaNacimiento");
            int telefono = resultado.getInt("telefono");
            String direccion= resultado.getString("direccion");
            int IDDepartamento = resultado.getInt("IDDepartamento");

            String output = "User #%d: %s - %s - %s - %s";
            System.out.println(String.format(output, ++count, name, pass, fullname, email));
        }
    }

    private static void cargarFicheroConfig() {
        try {
            propiedadesConexion.load(new FileInputStream(new File(NOMBRE_FICHERO_PROPIEDADES)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Connection establecerConexion() {
        Connection c = null;
        try {
            c = DriverManager.getConnection(propiedadesConexion.getProperty("Url"),
                    propiedadesConexion.getProperty("Usuario"), propiedadesConexion.getProperty("Clave"));
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return c;

    }

    public static void generarFicheroConfig() {
        setProperties();
        saveProperties();
    }

    private static void saveProperties() {
        FileOutputStream fos= null;
        try {
            fos = new FileOutputStream(NOMBRE_FICHERO_PROPIEDADES);
            propiedadesConexion.store(fos, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (fos != null) {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setProperties(){
        propiedadesConexion = new Properties();
        propiedadesConexion.setProperty("driver", DRIVER);
        propiedadesConexion.setProperty("Url", URL);
        propiedadesConexion.setProperty("Usuario", USUARIO);
        propiedadesConexion.setProperty("Clave", CLAVE);
    }
}


