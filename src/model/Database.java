/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author BingoAndBro
 */
package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


public class Database {
        
    // Instance tunggal dari kelas Database
    private static Database instance;
    private List<Observer> observers;
    
    // Private constructor untuk mencegah instansiasi langsung
    private Database() {
      observers = new ArrayList<>();
    }
    
    // Metode untuk mendapatkan instance tunggal dari kelas Database
    public static Database getInstance() {
        if (instance == null) {
            synchronized (Database.class) {
                if (instance == null) {
                    instance = new Database();
                }
            }
        }
        return instance;
    }
    
    public Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:./database/stis_kenangan.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    
    public String cariUsernameLucu(String identitas){
        String sql = " SELECT username FROM kenangan_lucu WHERE identitas = ?";
        
        ResultSet rs;
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, identitas);
           rs =  pstmt.executeQuery();
           if(rs.next()){
               return rs.getString("username");
           }
           else{
               return "";
           }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }
    
        public String cariUsernameHorror(String identitas){
        String sql = " SELECT username FROM kenangan_horror WHERE identitas = ?";
        
        ResultSet rs;
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, identitas);
           rs =  pstmt.executeQuery();
           if(rs.next()){
               return rs.getString("username");
           }
           else{
               return "";
           }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }
        
        public String cariUsernameRomantis(String identitas){
        String sql = " SELECT username FROM kenangan_romantis WHERE identitas = ?";
        
        ResultSet rs;
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, identitas);
           rs =  pstmt.executeQuery();
           if(rs.next()){
               return rs.getString("username");
           }
           else{
               return "";
           }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    public boolean registerUser(User user) {
        String sql = "INSERT INTO users(username, email, password) VALUES(?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    public boolean registerKenanganLucu(KenanganLucu kenanganLucu) throws SQLException {
        String sql = "INSERT INTO kenangan_lucu(username,identitas, kenangan,last_modified) VALUES(?,?,?,?)";
        String currentDate = getCurrentDate();

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, kenanganLucu.getUsername());
            pstmt.setString(2, kenanganLucu.getIdentitas());
            pstmt.setString(3, kenanganLucu.getKenangan());
            pstmt.setString(4, currentDate);
            
            pstmt.executeUpdate();
             KenanganNotifier.getInstance().kenanganCreated(kenanganLucu.getUsername());
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    public boolean registerKenanganHorror(KenanganHorror kenanganHorror) {
        String sql = "INSERT INTO kenangan_horror(username, identitas,kenangan,last_modified) VALUES(?,?,?,?)";
        String currentDate = getCurrentDate();

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, kenanganHorror.getUsername());
             pstmt.setString(2, kenanganHorror.getIdentitas());
            pstmt.setString(3, kenanganHorror.getKenangan());
             pstmt.setString(4, currentDate);
            pstmt.executeUpdate();
            KenanganNotifier.getInstance().kenanganCreated(kenanganHorror.getUsername());
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    public boolean registerKenanganRomantis(KenanganRomantis kenanganRomantis) {
        String sql = "INSERT INTO kenangan_romantis(username,identitas, kenangan,last_modified) VALUES(?,?,?,?)";
        String currentDate = getCurrentDate();

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, kenanganRomantis.getUsername());
             pstmt.setString(2, kenanganRomantis.getIdentitas());
            pstmt.setString(3, kenanganRomantis.getKenangan());
             pstmt.setString(4, currentDate);
            pstmt.executeUpdate();
            KenanganNotifier.getInstance().kenanganCreated(kenanganRomantis.getUsername());
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    
    public List<KenanganLucu> getAllKenangan_Lucu() {
        String sql = "SELECT * FROM kenangan_lucu";
        List<KenanganLucu> kenanganLucuList = new ArrayList<>();
        
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
             
            while (rs.next()) {
                KenanganLucu kenanganLucu = new KenanganLucu();
                 kenanganLucu.setUsername(rs.getString("username"));
                kenanganLucu.setIdentitas(rs.getString("identitas"));
                kenanganLucu.setKenangan(rs.getString("kenangan"));
                 kenanganLucu.setLastModified(rs.getString("last_modified"));
                kenanganLucuList.add(kenanganLucu);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return kenanganLucuList;
    }
    
        public List<KenanganHorror> getAllKenangan_Horror() {
        String sql = "SELECT * FROM kenangan_horror";
        List<KenanganHorror> kenanganHorrorList = new ArrayList<>();
        
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
             
            while (rs.next()) {
                KenanganHorror kenanganHorror = new KenanganHorror();
                kenanganHorror.setUsername(rs.getString("username"));
                 kenanganHorror.setIdentitas(rs.getString("identitas"));
                kenanganHorror.setKenangan(rs.getString("kenangan"));
                kenanganHorror.setLastModified(rs.getString("last_modified"));
                kenanganHorrorList.add(kenanganHorror);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return kenanganHorrorList;
    }
        
        public List<KenanganRomantis> getAllKenangan_Romantis() {
        String sql = "SELECT * FROM kenangan_romantis";
        List<KenanganRomantis> kenanganRomantisList = new ArrayList<>();
        
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
             
            while (rs.next()) {
                KenanganRomantis kenanganRomantis = new KenanganRomantis();
                kenanganRomantis.setUsername(rs.getString("username"));
                 kenanganRomantis.setIdentitas(rs.getString("identitas"));
                kenanganRomantis.setKenangan(rs.getString("kenangan"));
                kenanganRomantis.setLastModified(rs.getString("last_modified"));
                kenanganRomantisList.add(kenanganRomantis);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return kenanganRomantisList;
    }
           
           
    public void saveUpdatedDataLucu(String username,String originalIdentitas, String originalKenangan, String newIdentitas, String newKenangan) throws SQLException {
        String query = "UPDATE kenangan_lucu SET identitas = ?, kenangan = ?,last_modified=?  WHERE username = ? AND identitas = ? AND kenangan = ?";
        String currentDate = getCurrentDate();

        try (Connection con = this.connect();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, newIdentitas);
            pst.setString(2, newKenangan);
            pst.setString(3, currentDate);
            pst.setString(4, username);
            pst.setString(5, originalIdentitas);
             pst.setString(6, originalKenangan);

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                KenanganNotifier.getInstance().kenanganUpdated(username);
                System.out.println("Data updated successfully."); 
            } else {
                System.out.println("Update failed.");
            }

        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
            throw ex;
        }
    }
    
    public void saveUpdatedDataHorror(String username,String originalIdentitas, String originalKenangan, String newIdentitas, String newKenangan) throws SQLException {
        String query = "UPDATE kenangan_horror SET identitas = ?, kenangan = ?,last_modified=?  WHERE username = ? AND identitas = ? AND kenangan = ?";
        String currentDate = getCurrentDate();

        try (Connection con = this.connect();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, newIdentitas);
            pst.setString(2, newKenangan);
            pst.setString(3, currentDate);
            pst.setString(4, username);
            pst.setString(5, originalIdentitas);
             pst.setString(6, originalKenangan);

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                KenanganNotifier.getInstance().kenanganUpdated(username);
                System.out.println("Data updated successfully."); 
            } else {
                System.out.println("Update failed.");
            }

        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
            throw ex;
        }
    }
    
           public void saveUpdatedDataRomantis(String username,String originalIdentitas, String originalKenangan, String newIdentitas, String newKenangan) throws SQLException {
        String query = "UPDATE kenangan_romantis SET identitas = ?, kenangan = ?,last_modified=?  WHERE username = ? AND identitas = ? AND kenangan = ?";
        String currentDate = getCurrentDate();

        try (Connection con = this.connect();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, newIdentitas);
            pst.setString(2, newKenangan);
            pst.setString(3, currentDate);
            pst.setString(4, username);
            pst.setString(5, originalIdentitas);
             pst.setString(6, originalKenangan);

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                KenanganNotifier.getInstance().kenanganUpdated(username);
                System.out.println("Data updated successfully.");
            } else {
                System.out.println("Update failed.");
            }

        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
            throw ex;
        }
    }
                
         public void deleteDataLucu(String username,String identitas,String kenangan) throws SQLException {
        String deleteSQL = "DELETE FROM kenangan_lucu WHERE username = ? AND identitas=? AND kenangan = ?";
        
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
            pstmt.setString(1, username);
             pstmt.setString(2, identitas);
            pstmt.setString(3, kenangan);
            pstmt.executeUpdate();
        }
    }
         
        public void deleteDataHorror(String username, String identitas,String kenangan) throws SQLException {
        String deleteSQL = "DELETE FROM kenangan_horror WHERE username = ? AND identitas = ? AND kenangan = ?";
        
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
            pstmt.setString(1, username);
            pstmt.setString(2, identitas);
            pstmt.setString(3, kenangan);
            pstmt.executeUpdate();
        }
    }
                 
         public void deleteDataRomantis(String username,String identitas, String kenangan) throws SQLException {
        String deleteSQL = "DELETE FROM kenangan_romantis WHERE username = ? AND identitas = ?  AND kenangan = ?";
        
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
            pstmt.setString(1, username);
            pstmt.setString(2, identitas);
            pstmt.setString(3, kenangan);
            pstmt.executeUpdate();
        }
    }
        
    public User loginUser(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                return user;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public void exportTableToCSV(JTable table, File file) throws IOException {
        try (FileWriter fw = new FileWriter(file)) {
            TableModel model = table.getModel();
            for (int i = 0; i < model.getColumnCount(); i++) {
                fw.write(model.getColumnName(i) + ",");
            }
            fw.write("\n");

            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < model.getColumnCount(); j++) {
                    fw.write(model.getValueAt(i, j).toString() + ",");
                }
                fw.write("\n");
            }
        }
    }

    public void importTableFromCSV(JTable table, File file) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); // clear the existing rows

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                model.addRow(data);
            }
        }
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
    
}