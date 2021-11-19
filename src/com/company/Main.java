package com.company;

import java.io.*;
import java.sql.*;
import java.util.Properties;

public class Main {
    static final String NOMBRE_FICHERO_PROPIEDADES = "config.properties";
    static Properties propiedadesConexion = new Properties();
    static OutputStream os = null;

    static Connection miConexion;

    static  String DRIVER = "com.mysql.jdbc.Driver";
    static  String URL = "jdbc:mysql://localhost/alvarolruiz";
    static String USUARIO = "alvarolruiz";
    static String CLAVE = "mitesoro99";
    public static void main(String[] args) {
        // write your code here
        ResultSet resultado;
        Statement query=null;


        if (!new File (NOMBRE_FICHERO_PROPIEDADES).exists()) {
            generarFicheroConfig();
        }else{
            try {
                propiedadesConexion.load(new FileInputStream(new File(NOMBRE_FICHERO_PROPIEDADES)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            establecerConexion();
        }

        try {
            query=miConexion.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private static void establecerConexion() {
        try {
            miConexion = DriverManager.getConnection(propiedadesConexion.getProperty("Url"),
                    propiedadesConexion.getProperty("Usuario"),propiedadesConexion.getProperty("Contraseña"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void generarFicheroConfig (){
            propiedadesConexion.setProperty("driver", DRIVER);
            propiedadesConexion.setProperty("Url", URL);
            propiedadesConexion.setProperty("Usuario", USUARIO);
            propiedadesConexion.setProperty("Contraseña", CLAVE);
            try {
                os = new FileOutputStream(NOMBRE_FICHERO_PROPIEDADES);
                propiedadesConexion.store(os, null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            }

}


