package com.espe.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.espe.project.model.Carrera;
import com.espe.project.utils.Db;

public class CarreraDAO {

    private static final Object lock = new Object();

    public List<Carrera> findAll(){
        String sql = "SELECT id_carrera, nombre_carrera FROM Carrera;";
        List<Carrera> list = new ArrayList<>();
        Connection conn = null;
        try {
            conn = Db.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(!rs.next()){
                return list;
            }else{
                do{
                    Carrera carreraTemp = new Carrera();
                    carreraTemp.setId_carrera(rs.getLong("id_carrera"));
                    carreraTemp.setNombre_carrera(rs.getString("nombre_carrera"));
                    list.add(carreraTemp);
                }while(rs.next());
            }
            System.out.println(list);
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener la lista de carreras: " + e.getMessage(), e);
        }finally{
            Db.closeConnection(conn);
        }
    }

    public Carrera findById(Long id_carrera){
        String sql = "SELECT id_carrera, nombre_carrera FROM Carrera WHERE id_carrera = ?;";
        Connection conn = null;
        try {
            conn = Db.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, id_carrera);
            ResultSet rs = ps.executeQuery();

            if(!rs.next())
                return null;
            return new Carrera(rs.getLong("id_carrera"),
                                  rs.getString("nombre_carrera"));
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar carrera con ID " + id_carrera + ": " + e.getMessage(),e);
        }finally{
            Db.closeConnection(conn);
        }
    }

    public Carrera createCarrera(Carrera carrera) {
        synchronized(lock) {
            if (findById(carrera.getId_carrera()) != null) {
                throw new RuntimeException("El ID de carrera " + carrera.getId_carrera() + " ya existe");
            }
            
            if (carrera.getId_carrera() == null || carrera.getId_carrera() <= 0) {
                throw new RuntimeException("El ID de carrera debe ser un número positivo");
            }
            
            String sql = "INSERT INTO Carrera (id_carrera, nombre_carrera) VALUES (?, ?);";
            Connection conn = null;
            try {
                conn = Db.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);

                ps.setLong(1, carrera.getId_carrera());
                ps.setString(2, carrera.getNombre_carrera());
                
                int affectedRows = ps.executeUpdate();
                
                if (affectedRows > 0) {
                    return carrera;
                } else {
                    throw new RuntimeException("No se pudo crear la carrera");
                }
            } catch (SQLException e) {
                String errorMsg = e.getMessage().toLowerCase();
                if (errorMsg.contains("duplicate")) {
                    throw new RuntimeException("El ID de carrera " + carrera.getId_carrera() + " ya existe", e);
                } else if (errorMsg.contains("cannot be null")) {
                    throw new RuntimeException("Todos los campos son requeridos", e);
                } else if (errorMsg.contains("data too long")) {
                    throw new RuntimeException("Los datos ingresados son demasiado largos", e);
                } else {
                    throw new RuntimeException("Error al crear carrera: " + e.getMessage(), e);
                }
            } finally {
                Db.closeConnection(conn);
            }
        }
    }

    public void deleteCarrera (long id_carrera){
        String sql = "DELETE FROM Carrera WHERE id_carrera = ?;";
        Connection conn = null;
        try {
            conn = Db.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setLong(1, id_carrera);
            ps.executeUpdate();
        } catch (SQLException e) {
            String errorMsg = e.getMessage().toLowerCase();
            if (errorMsg.contains("foreign key") || errorMsg.contains("integrity constraint")) {
                throw new RuntimeException("No se puede eliminar la carrera porque tiene asignaturas relacionadas", e);
            } else {
                throw new RuntimeException("Error al eliminar carrera: " + e.getMessage(), e);
            }
        } finally {
            Db.closeConnection(conn);
        }
    }

    public void updateCarrera(Carrera carrera){
        String sql = "UPDATE Carrera SET nombre_carrera = ? WHERE id_carrera = ?;";
        Connection conn = null;
        try {
            conn = Db.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, carrera.getNombre_carrera());
            ps.setLong(2, carrera.getId_carrera());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar docente: " + e.getMessage(), e);
        }finally{
            Db.closeConnection(conn);
        }
    }

}
