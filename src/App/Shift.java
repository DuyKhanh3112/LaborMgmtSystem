/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

/**
 *
 * @author Duy Khanh
 */
public class Shift {
    
    int username;
    String date;
    String idShift;

    public Shift() {
    }

    public Shift(int username, String date, String idShift) {
        this.username = username;
        this.date = date;
        this.idShift = idShift;
    }

    public int getUsername() {
        return username;
    }

    public void setUsername(int username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIdShift() {
        return idShift;
    }

    public void setIdShift(String idShift) {
        this.idShift = idShift;
    }

   
    
}
