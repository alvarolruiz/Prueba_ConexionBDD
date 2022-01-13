package com.company;

import java.io.*;
import java.sql.*;
import java.util.Properties;

public class Main {
    static final String NOMBRE_FICHERO_PROPIEDADES = "config.properties";
    private static final String CONSULTA_TABLA_CLIENTES = "SELECT * FROM Personas";
    private static Properties propiedadesConexion=null;
    static String DRIVER = "com.mysql.jdbc.Driver";
    static String URL = "jdbc:sqlserver://localhost:51916";
    static String USUARIO = "alvarolruiz";
    static String CLAVE = "mitesoro99";

    public static void main(String[] args) {

        ResultSet resultado;
        Statement query = null;
        Connection c;

        if (!new File(NOMBRE_FICHERO_PROPIEDADES).exists()) {
            generarFicheroConfig();
        } else {
            try {
                propiedadesConexion.load(new FileInputStream(NOMBRE_FICHERO_PROPIEDADES));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            c= establecerConexion();
            query = c.createStatement();
            resultado=query.executeQuery(CONSULTA_TABLA_CLIENTES);
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

    private static Connection establecerConexion() {
        Connection c = null;
        try {
            c = DriverManager.getConnection(propiedadesConexion.getProperty("Url"),
                    propiedadesConexion.getProperty("Usuario"), propiedadesConexion.getProperty("Contraseña"));
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return c;

    }

    public static void generarFicheroConfig() {
        propiedadesConexion = setProperties();
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

    public static Properties setProperties(){
        Properties propiedadesConexion = new Properties();
        propiedadesConexion.setProperty("driver", DRIVER);
        propiedadesConexion.setProperty("Url", URL);
        propiedadesConexion.setProperty("Usuario", USUARIO);
        propiedadesConexion.setProperty("Contraseña", CLAVE);
        return propiedadesConexion;
    }
}


