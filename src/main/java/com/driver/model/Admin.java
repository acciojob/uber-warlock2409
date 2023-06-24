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
   private String name;

   private String password;

   @OneToMany(mappedBy = "admin")
   List<Driver> driverList = new ArrayList<>();

   @OneToMany(mappedBy = "admin")
   List<Customer> customerList = new ArrayList<>();
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}