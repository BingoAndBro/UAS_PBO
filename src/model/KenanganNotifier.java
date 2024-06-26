/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author BingoAndBro
 */
public class KenanganNotifier  extends MessageObserver{
     private static KenanganNotifier instance = new KenanganNotifier();

    private KenanganNotifier() {}

    public static KenanganNotifier getInstance() {
        return instance;
    }

    public void kenanganCreated(String username) {
        notifyObservers("Kenangan Baru Ditambahkan oleh : " + username);
    }

    public void kenanganUpdated(String username) {
        notifyObservers("Ada kenangan yang diperbarui oleh : " + username);
    }
    
      public void clearObservers() {
        observers.clear();
    }

}
