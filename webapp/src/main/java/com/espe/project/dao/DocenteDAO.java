package com.espe.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.espe.project.model.Docente;
import com.espe.project.utils.Db;

public class DocenteDAO {
    private static final Object lock = new Object();

    public List<Docente> findAll(){
        String sql = "SELECT id_docente, nombre_docente, apellido_docente, correo FROM Docente;";
        List<Docente> list = new ArrayList<>();
        Connection conn = null;
        try {
            conn = Db.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(!rs.next()){
                return list;
            }else{
                do{
                    Docente docenteTemp = new Docente();
                    docenteTemp.setId_docente(rs.getLong("id_docente"));
                    docenteTemp.setNombre_docente(rs.getString("nombre_docente"));
                    docenteTemp.setApellido_docente(rs.getString("apellido_docente"));
                    docenteTemp.setCorreo(rs.getString("correo"));
                    list.add(docenteTemp);
                }while(rs.next());
            }                
            System.out.println(list);
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener la lista de docentes: " + e.getMessage(), e);
        }finally{
            Db.closeConnection(conn);
        }
    }

    public Docente findById(Long id_docente){
        String sql = "SELECT id_docente, nombre_docente, apellido_docente, correo FROM Docente WHERE id_docente = ?;";
        Connection conn = null;
        try {
            conn = Db.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, id_docente);
            ResultSet rs = ps.executeQuery();

            if(!rs.next())
                return null;
            return new Docente(rs.getLong("id_docente"),
                               rs.getString("nombre_docente"),
                               rs.getString("apellido_docente"),
                               rs.getString("correo"));
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar docente con ID " + id_docente + ": " + e.getMessage(),e);
        }finally{
            Db.closeConnection(conn);
        }
    }

    public Docente createDocente(Docente docente) {
        synchronized(lock) {
            if (findById(docente.getId_docente()) != null) {
                throw new RuntimeException("El ID de docente " + docente.getId_docente() + " ya existe");
            }
            
            if (docente.getId_docente() == null || docente.getId_docente() <= 0) {
                throw new RuntimeException("El ID de docente debe ser un número positivo");
            }
            
            String sql = "INSERT INTO Docente (id_docente, nombre_docente, apellido_docente, correo) VALUES (?, ?, ?, ?);";
            Connection conn = null;
            try {
                conn = Db.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);

                ps.setLong(1, docente.getId_docente());
                ps.setString(2, docente.getNombre_docente());
                ps.setString(3, docente.getApellido_docente());
                ps.setString(4, docente.getCorreo());
                
                int affectedRows = ps.executeUpdate();
                
                if (affectedRows > 0) {
                    return docente;
                } else {
                    throw new RuntimeException("No se pudo crear el docente");
                }
            } catch (SQLException e) {
                String errorMsg = e.getMessage().toLowerCase();
                if (errorMsg.contains("duplicate")) {
                    throw new RuntimeException("El ID de docente " + docente.getId_docente() + " ya existe", e);
                } else if (errorMsg.contains("cannot be null")) {
                    throw new RuntimeException("Todos los campos son requeridos", e);
                } else if (errorMsg.contains("data too long")) {
                    throw new RuntimeException("Los datos ingresados son demasiado largos", e);
                } else {
                    throw new RuntimeException("Error al crear docente: " + e.getMessage(), e);
                }
            } finally {
                Db.closeConnection(conn);
            }
        }
    }

    public void deleteDocente (long id_docente){
        String sql = "DELETE FROM Docente WHERE id_docente = ?;";
        Connection conn = null;
        try {
            conn = Db.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setLong(1, id_docente);
            ps.executeUpdate();
        } catch (SQLException e) {
            String errorMsg = e.getMessage().toLowerCase();
            if (errorMsg.contains("foreign key") || errorMsg.contains("integrity constraint")) {
                throw new RuntimeException("No se puede eliminar el docente porque tiene asignaturas relacionadas", e);
            } else {
                throw new RuntimeException("Error al eliminar docente: " + e.getMessage(), e);
            }
        } finally {
            Db.closeConnection(conn);
        }
    }

    public void updateDocente(Docente docente){
        String sql = "UPDATE Docente SET nombre_docente = ?, apellido_docente = ?, correo = ? WHERE id_docente = ?;";
        Connection conn = null;
        try {
            conn = Db.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, docente.getNombre_docente());
            ps.setString(2, docente.getApellido_docente());
            ps.setString(3, docente.getCorreo());
            ps.setLong(4, docente.getId_docente());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar docente: " + e.getMessage(), e);
        }finally{
            Db.closeConnection(conn);
        }
    }

}
