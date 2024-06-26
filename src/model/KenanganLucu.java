/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class KenanganLucu implements Kenangan {
    private String username;
    private String identitas;
    private String kenangan;
     private String lastModified;

    // Constructor
    public KenanganLucu(String identitas, String kenangan) {
        this.identitas = identitas;
        this.kenangan = kenangan;
    }

    // Default constructor
    public KenanganLucu() {}

    // Getters and setters
    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }
    
    @Override
    public String getIdentitas() {
        return identitas;
    }

    @Override
    public void setIdentitas(String identitas) {
        this.identitas = identitas;
    }

    @Override
    public String getKenangan() {
        return kenangan;
    }

    @Override
    public void setKenangan(String kenangan) {
        this.kenangan = kenangan;
    }
    
    @Override
       public String getLastModified() {
        return lastModified;
    }

    @Override
    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }
}