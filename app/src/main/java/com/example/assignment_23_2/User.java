package com.example.assignment_23_2;

public class User {

    private String username;
    private String password;
    private String date;
    private String phone;

    public User(String username, String password) {
        this.username = username;
        this.password=password;
    }
    public User(String username, String password,String date,String phone) {
        this.username = username;
        this.password=password;
        this.date=date;
        this.phone=phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
