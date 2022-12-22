/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

import form.ForgetPassForm;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Duy Khanh
 */
public class AppManage {

    public static Connection conn;

    public static void createDB() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;"
                    + "databaseName=BHX_Management; username=sa; password=sa;");

        } catch (Exception ex) {
        }
    }

    public boolean checkEmployee(String stname, String pass) {
        try {
            int name = Integer.parseInt(stname);
            PreparedStatement stmt = conn.prepareStatement("insert into Login(Username) values(?)");
            stmt.setInt(1, name);
            stmt.execute();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from Employee where Username =" + name);

            while (rs.next()) {
                if (rs.getString("Password").equals(pass)) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception ex) {
        }
        return false;
    }

    public boolean checkManager(String pass) {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from Manager");
            while (rs.next()) {
                if (rs.getString("Password").equals(pass)) {
                    return true;
                }
            }

        } catch (Exception ex) {
        }
        return false;
    }

    public int createAttendence(int username, String day, String shift) {
        int i = 0;
        try {
            PreparedStatement stmt = conn.prepareStatement("insert into Attendence(Username,Date, ID_Shift) values(?,?,?)");
            stmt.setInt(1, username);
            stmt.setString(2, day);
            stmt.setString(3, shift);
            i = stmt.executeUpdate();
        } catch (Exception ex) {
        }
        return i;
    }

    public HashMap totalTimeWork(int month, int year) {

        int username;
        try {
            Statement st = conn.createStatement();
            ResultSet rs1 = st.executeQuery("select Username from Employee");
            while (rs1.next()) {
                username = rs1.getInt("Username");
                Statement st1 = conn.createStatement();
                ResultSet rs = st1.executeQuery("select a.Username,Name, Date, ID_Shift, DATEDIFF(MINUTE, TimeIn, TimeOut) as TimeWork"
                        + " from Attendence  a join Employee b on a.Username= b.username \n"
                        + "where a.Username = " + username + " and [Date] between '1/" + month + "/" + year + "' and '1/" + (month == 12 ? 1 : month) + "/" + (month == 12 ? year + 1 : month) + "'\n");
                float total = 0;
                while (rs.next()) {
                    if (rs.getString("ID_Shift").equals("A") || rs.getString("ID_Shift").equals("H")) {
                        if (rs.getString("Date").equals(year + "-04-30") || rs.getString("Date").equals(year + "-05-01") || rs.getString("Date").equals(year + "-09-01")) {
                            if (rs.getFloat("TimeWork") < 0) {
                                total = total + (24 + rs.getFloat("TimeWork") / 60) * 4;
                            } else {
                                total = total + rs.getFloat("TimeWork") / 60 * 4;
                            }
                        } else {
                            if (rs.getFloat("TimeWork") < 0) {
                                total = total + (24 + rs.getFloat("TimeWork") / 60) * 2;
                            } else {
                                total = total + rs.getFloat("TimeWork") / 60 * 2;
                            }

                        }
                    } else {

                        if (rs.getString("Date").equals(year + "-04-30") || rs.getString("Date").equals(year + "-05-01") || rs.getString("Date").equals(year + "-09-01")) {
                            if (rs.getFloat("TimeWork") < 0) {
                                total = total + (24 + rs.getFloat("TimeWork") / 60) * 2;
                            } else {
                                total = total + rs.getFloat("TimeWork") / 60 * 2;
                            }
                        } else {
                            if (rs.getFloat("TimeWork") < 0) {
                                total = total + (24 + rs.getFloat("TimeWork") / 60);
                            } else {
                                total = total + rs.getFloat("TimeWork") / 60;
                            }
                        }
                    }
                    total = Math.round(total*10)/10;
                    timeWorkMap.put(username, total);

                }
            }

        } catch (Exception ex) {
        }
        return timeWorkMap;

    }
    public HashMap<Integer, Float> timeWorkMap = new HashMap<Integer, Float>();

    public String getName(int username) {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select Name from Employee where Username= " + username);
            while (rs.next()) {
                return rs.getString("Name");
            }
        } catch (Exception ex) {
        }
        return null;
    }

    public String getIDOffice(int username) {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select a.ID_Office from"
                    + " Employee a join Office b on a.ID_Office = b.ID_Office"
                    + " where Username = " + username);
            while (rs.next()) {
                return rs.getString("ID_Office");
            }
        } catch (Exception ex) {
        }
        return null;
    }

    public int getAccount() {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select Username from Login where ID= (select count(*) from Login) ");
            while (rs.next()) {
                return rs.getInt("Username");
            }
        } catch (Exception ex) {
        }
        return 0;

    }

    public double getSalaryGrade(int username) {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select Salary_grade from"
                    + " Employee a join Office b on a.ID_Office = b.ID_Office"
                    + " where Username = " + username);
            while (rs.next()) {
                return rs.getDouble("Salary_grade");
            }
        } catch (Exception ex) {
        }
        return 0;
    }

    public String showPasswordManager(String username, String phone) {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from Manager ");
            while (rs.next()) {
                if (phone.equals(rs.getString("Phone"))) {
                    return rs.getString("Name") + "'s Password is " + rs.getString("Password");
                } else {
                    return "Show Password is Successfull";
                }
            }
        } catch (Exception ex) {
        }
        return null;
    }

    public String showPasswordEmployee(int username, String phone) {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from Employee where username= " + username);
            while (rs.next()) {
                if (phone.equals(rs.getString("Phone"))) {
                    java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            new ForgetPassForm().setVisible(false);
                        }
                    });
                    return rs.getString("Name") + "'s Password is " + rs.getString("Password");
                } else {
                    return "Show Password is Successfull";
                }
            }
        } catch (Exception ex) {
        }
        return null;
    }
    ArrayList<Shift> shifts = new ArrayList<Shift>();

    public ArrayList showShift(int month, int year) {
        try {

            Statement st1 = conn.createStatement();
            ResultSet rs = st1.executeQuery("select * "
                    + " from Attendence   "
                    + "where [Date] between '1/" + month + "/" + year + "' and '1/" + (month == 12 ? 1 : month) + "/" + (month == 12 ? year + 1 : month) + "'\n");
            while (rs.next()) {
                shifts.add(new Shift(rs.getInt("Username"), rs.getString("Date"), rs.getString("ID_Shift")));
            }
            return shifts;
        } catch (Exception ex) {
        }
        return null;
    }

    public int updateShift(int username, String date, String idShift, Shift shift) {
        int i = 0;

        try {
            PreparedStatement stmt = conn.prepareStatement("update Attendence set Username= ?, Date=?, ID_Shift =?  "
                    + "where Username=? and Date =? and ID_Shift=?;");
            stmt.setInt(1, username);
            stmt.setString(2, date);
            stmt.setString(3, idShift);
            stmt.setInt(4, shift.getUsername());
            stmt.setString(5, shift.getDate());
            stmt.setString(6, idShift);
            i = stmt.executeUpdate();
            return i;
        } catch (Exception ex) {
            return i;
        }
    }

    public int deleteShift(int username, String date, String idShift) {

        try {
            PreparedStatement stmt = conn.prepareStatement("Delete Attendence "
                    + "where Username=? and Date =? and ID_Shift=?;");
            stmt.setInt(1, username);
            stmt.setString(2, date);
            stmt.setString(3, idShift);
            return stmt.executeUpdate();
        } catch (Exception ex) {
        }
        return 0;
    }

    public void AttendenceIn(int username,String date, String idShift) {
        LocalTime timeIn = LocalTime.now();
        try {
            PreparedStatement stmt = conn.prepareStatement("update Attendence set TimeIn= ?"
                    + " where Username =? and Date =? and ID_Shift =?");
            stmt.setString(1, timeIn.getHour() + ":" + timeIn.getMinute());
            stmt.setInt(2, username);
            stmt.setString(3, date);
            stmt.setString(4, idShift);
            stmt.executeQuery();
        } catch (Exception ex) {
        }
    }

    public void AttendenceOut(int username,String date, String idShift) {
        LocalTime timeIn = LocalTime.now();
        try {
            PreparedStatement stmt = conn.prepareStatement("update Attendence set TimeOut= ?"
                    + " where Username =? and Date =? and ID_Shift =?");
            stmt.setString(1, timeIn.getHour() + ":" + timeIn.getMinute());
            stmt.setInt(2, username);
            stmt.setString(3, date);
            stmt.setString(4, idShift);
            stmt.executeQuery();
        } catch (Exception ex) {
        }
    }
    
}
