/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.SQLException;
import model.Database;
import model.KenanganHorror;
import model.KenanganLucu;
import model.KenanganRomantis;

/**
 *
 * @author BingoAndBro
 */
public class RegisterKenanganController {
    private Database database;

    public RegisterKenanganController() {
        this.database = Database.getInstance();
    }
  

    public boolean registerLucuKenangan(String identitas, String kenangan) throws SQLException {
        if (!isValidIdentitas(identitas)) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }
        if (!isValidKenangan(kenangan)) {
            throw new IllegalArgumentException("Kenangan cannot be empty");
        }
        KenanganLucu kenanganLucu= new KenanganLucu(identitas, kenangan);
        return database.registerKenanganLucu(kenanganLucu);
    }
    
      public boolean registerHorrorKenangan(String username, String kenangan) {
        if (!isValidUsername(username)) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }
        if (!isValidKenangan(kenangan)) {
            throw new IllegalArgumentException("Kenangan cannot be empty");
        }
        KenanganHorror kenanganHorror= new KenanganHorror(username, kenangan);
        return database.registerKenanganHorror(kenanganHorror);
    }
      
      public boolean registerRomantisKenangan(String username, String kenangan) {
        if (!isValidUsername(username)) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }
        if (!isValidKenangan(kenangan)) {
            throw new IllegalArgumentException("Kenangan cannot be empty");
        }
        KenanganRomantis kenanganRomantis= new KenanganRomantis(username, kenangan);
        return database.registerKenanganRomantis(kenanganRomantis);
    }
      
          public boolean insertKenanganLucu(KenanganLucu kenanganLucu) throws SQLException {
        if (!isValidIdentitas(kenanganLucu.getIdentitas())) {
            throw new IllegalArgumentException("Identitas cannot be empty.");
        }
        if (!isValidKenangan(kenanganLucu.getKenangan())) {
            throw new IllegalArgumentException("Kenangan cannot be empty");
        }
        return database.registerKenanganLucu(kenanganLucu);
    }
          
              public boolean insertKenanganHorror(KenanganHorror kenanganHorror) {
        if (!isValidUsername(kenanganHorror.getUsername())) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }
        if (!isValidKenangan(kenanganHorror.getKenangan())) {
            throw new IllegalArgumentException("Kenangan cannot be empty");
        }
        return database.registerKenanganHorror(kenanganHorror);
    }
              
        public boolean insertKenanganRomantis(KenanganRomantis kenanganRomantis) {
        if (!isValidUsername(kenanganRomantis.getUsername())) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }
        if (!isValidKenangan(kenanganRomantis.getKenangan())) {
            throw new IllegalArgumentException("Kenangan cannot be empty");
        }
        return database.registerKenanganRomantis(kenanganRomantis);
    }
        

    private boolean isValidIdentitas(String identitas) {
        return identitas != null && !identitas.trim().isEmpty();
    }
    
    private boolean isValidUsername(String username) {
        return username != null && !username.trim().isEmpty();
    }
    
     private boolean isValidKenangan(String username) {
        return username != null && !username.trim().isEmpty();
    }  
}
