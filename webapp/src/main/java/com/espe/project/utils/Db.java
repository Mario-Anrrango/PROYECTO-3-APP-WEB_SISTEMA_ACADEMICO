package com.espe.project.utils;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;

public final class Db {

    // URL CORREGIDA - agregar useUnicode=true
    private static final String URL = "jdbc:mysql://localhost:3306/SISTEMA_ACADEMICO"
                                    + "?useUnicode=true"
                                    + "&characterEncoding=UTF-8"
                                    + "&useSSL=false"
                                    + "&serverTimezone=UTC";
    private static final String USER = "dbeaver";
    private static final String PASS = "Esfera24@215!";

    static {
        try {
            // Registrar el driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error al cargar el driver de MySQL", e);
        }
    }

    private Db() {
    }

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USER, PASS);
        
        // Configurar la conexión para usar UTF-8
        conn.createStatement().execute("SET NAMES 'utf8mb4'");
        conn.createStatement().execute("SET CHARACTER SET utf8mb4");
        
        return conn;
    }

    public static void closeConnection(Connection conn){
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}