package com.example.sql_inject_demo;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Employee implements Serializable {
    private String name, password, ssn, nickname, phone, email, address;
    private int salary,id;
    private Date birthday;

    public Employee(){}

    public Employee(int id, String name, String password, String ssn, String nickname, String phone, String email, String address, int salary, String birthday) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.id = id;
        this.name = name;
        this.password = password;
        this.ssn = ssn;
        this.nickname = nickname;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.salary = salary;
        try {
            this.birthday = dateFormat.parse(birthday);
        }
        catch (Exception e)
        {
            this.birthday = new Date();
        }

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
