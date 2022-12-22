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
public class Attendence {
    private int username;
    private String name;
    private float timework;

    public Attendence() {
    }

    public Attendence(int username, String name, float timework) {
        this.username = username;
        this.name = name;
        this.timework = timework;
    }

    public float getTimework() {
        return timework;
    }

    public void setTimework(float timework) {
        this.timework = timework;
    }

    public int getUsername() {
        return username;
    }

    public void setUsername(int username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
