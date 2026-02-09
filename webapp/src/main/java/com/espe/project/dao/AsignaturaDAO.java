package com.espe.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.espe.project.model.Asignatura;
import com.espe.project.utils.Db;

public class AsignaturaDAO {

    private static final Object lock = new Object();

    public List<Asignatura> findAll(){
    System.out.println("=== EJECUTANDO AsignaturaDAO.findAll() ===");
    
    String sql = "SELECT id_asignatura, id_docente, id_carrera, nombre_asignatura, creditos FROM Asignatura;";
    List<Asignatura> list = new ArrayList<>();
    Connection conn = null;
    
    try {
        System.out.println("Obteniendo conexión...");
        conn = Db.getConnection();
        System.out.println("Conexión obtenida: " + (conn != null));
        System.out.println("SQL: " + sql);
        
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        System.out.println("Query ejecutada, procesando resultados...");
        
        int count = 0;
        while(rs.next()){
            Asignatura asignaturaTemp = new Asignatura();
            asignaturaTemp.setId_asignatura(rs.getLong("id_asignatura"));
            asignaturaTemp.setId_docente(rs.getLong("id_docente"));
            asignaturaTemp.setId_carrera(rs.getLong("id_carrera"));
            asignaturaTemp.setNombre_asignatura(rs.getString("nombre_asignatura"));
            asignaturaTemp.setCreditos(rs.getInt("creditos"));
            list.add(asignaturaTemp);
            count++;
            
            System.out.println("Asignatura " + count + ": " + 
                asignaturaTemp.getId_asignatura() + " - " + 
                asignaturaTemp.getNombre_asignatura());
        }
        
        System.out.println("Total asignaturas encontradas: " + count);
        return list;
        
    } catch (SQLException e) {
        System.out.println("ERROR SQL en AsignaturaDAO.findAll: " + e.getMessage());
        System.out.println("SQL State: " + e.getSQLState());
        System.out.println("Error Code: " + e.getErrorCode());
        e.printStackTrace();
        throw new RuntimeException("Error al obtener la lista de asignaturas: " + e.getMessage(), e);
    } finally {
        Db.closeConnection(conn);
        System.out.println("Conexión cerrada");
    }
}

    public Asignatura findById(Long id_asignatura){
        String sql = "SELECT id_asignatura, id_docente, id_carrera, nombre_asignatura, creditos FROM Asignatura WHERE id_asignatura = ?;";
        Connection conn = null;
        try {
            conn = Db.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, id_asignatura);
            ResultSet rs = ps.executeQuery();

            if(!rs.next())
                return null;
            return new Asignatura(rs.getLong("id_asignatura"),
                                  rs.getLong("id_docente"),
                                  rs.getLong("id_carrera"),
                                  rs.getString("nombre_asignatura"),
                                  rs.getInt("creditos"));
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar asignatura con ID " + id_asignatura + ": " + e.getMessage(),e);
        }finally{
            Db.closeConnection(conn);
        }
    }

    public Asignatura createAsignatura(Asignatura asignatura) {
        synchronized(lock) {
            if (findById(asignatura.getId_asignatura()) != null) {
                throw new RuntimeException("El ID de asignatura " + asignatura.getId_asignatura() + " ya existe");
            }
            
            if (asignatura.getId_asignatura() == null || asignatura.getId_asignatura() <= 0) {
                throw new RuntimeException("El ID de asignatura debe ser un número positivo");
            }
            
            String sql = "INSERT INTO Asignatura (id_asignatura, id_docente, id_carrera, nombre_asignatura, creditos) VALUES (?, ?, ?, ?, ?);";
            Connection conn = null;
            try {
                conn = Db.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);

                ps.setLong(1, asignatura.getId_asignatura());
                ps.setLong(2, asignatura.getId_docente());
                ps.setLong(3, asignatura.getId_carrera());
                ps.setString(4, asignatura.getNombre_asignatura());
                ps.setInt(5, asignatura.getCreditos());
                
                int affectedRows = ps.executeUpdate();
                
                if (affectedRows > 0) {
                    return asignatura;
                } else {
                    throw new RuntimeException("No se pudo crear la asignatura");
                }
            } catch (SQLException e) {
                String errorMsg = e.getMessage().toLowerCase();
                if (errorMsg.contains("duplicate")) {
                    throw new RuntimeException("El ID de asignatura " + asignatura.getId_asignatura() + " ya existe", e);
                } else if (errorMsg.contains("cannot be null")) {
                    throw new RuntimeException("Todos los campos son requeridos", e);
                } else if (errorMsg.contains("foreign key")) {
                    throw new RuntimeException("El docente o carrera seleccionado no existe", e);
                } else if (errorMsg.contains("data too long")) {
                    throw new RuntimeException("Los datos ingresados son demasiado largos", e);
                } else {
                    throw new RuntimeException("Error al crear asignatura: " + e.getMessage(), e);
                }
            } finally {
                Db.closeConnection(conn);
            }
        }
    }

    public void deleteAsignatura (long id_asignatura){
        String sql = "DELETE FROM Asignatura WHERE id_asignatura = ?;";
        Connection conn = null;
        try {
            conn = Db.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setLong(1, id_asignatura);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar asignatura: " + e.getMessage(), e);
        } finally {
            Db.closeConnection(conn);
        }
    }

    public void updateAsignatura(Asignatura asignatura){
        String sql = "UPDATE Asignatura SET id_docente = ?, id_carrera = ?, nombre_asignatura = ?, creditos = ? WHERE id_asignatura = ?;";
        Connection conn = null;
        try {
            conn = Db.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setLong(1, asignatura.getId_docente());
            ps.setLong(2, asignatura.getId_carrera());
            ps.setString(3, asignatura.getNombre_asignatura());
            ps.setInt(4, asignatura.getCreditos());
            ps.setLong(5, asignatura.getId_asignatura());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar asignatura: " + e.getMessage(), e);
        } finally {
            Db.closeConnection(conn);
        }
    }
}
