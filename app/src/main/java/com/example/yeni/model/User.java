package com.example.yeni.model;

public class User {

    private String PhoneNumber;
    private String Password;
    private String Name;
    private String Address;
    private String Email;


    public User() {
    }

    public User(String phoneNumber, String password, String name, String address, String email) {
        PhoneNumber = phoneNumber;
        Password = password;
        Name = name;
        Address = address;
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
