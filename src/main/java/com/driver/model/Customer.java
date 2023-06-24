package com.driver.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int customerId;

    String mobile;
    String password;

    @ManyToOne
    Admin admin;
    @OneToMany(mappedBy = "customer")
    List<TripBooking> tripBookingList = new ArrayList<>();
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}