package com.driver.model;

import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity

public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int adminId;
   private String userName;

   private String password;

   @OneToMany(mappedBy = "admin")
   List<Driver> driverList = new ArrayList<>();

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Driver> getDriverList() {
        return driverList;
    }

    public void setDriverList(List<Driver> driverList) {
        this.driverList = driverList;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    @OneToMany(mappedBy = "admin")
   List<Customer> customerList = new ArrayList<>();
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}