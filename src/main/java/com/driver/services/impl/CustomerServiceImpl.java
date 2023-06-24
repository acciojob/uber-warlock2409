package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.CabRepository;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;

	@Autowired
	CabRepository cabRepository2;

	@Override
	public void register(Customer customer) {
		//Save the customer in database
		customerRepository2.save(customer);
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		// Delete customer without using deleteById function
		Optional<Customer> customer = customerRepository2.findById(customerId);
		if(customer.isPresent()){
			customerRepository2.deleteById(customerId);
		}

	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query
		List<Driver> driverList = driverRepository2.findAll();

		for(int i=0;i<driverList.size();i++){
			Driver scoperDrive = driverList.get(i);
			if(!scoperDrive.getCab().isAvailable()){
				driverList.remove(i);
			}
		}
		int id =Integer.MAX_VALUE;
		Driver d = null;
		for(int i=0;i<driverList.size();i++){
			Driver scopeDriver = driverList.get(i);
			if(scopeDriver.getDriverId() < id){
				d=scopeDriver;
				id=scopeDriver.getDriverId();
			}
		}
		if(d == null){
			throw new Exception("No cab available!");
		}
		TripBooking tripBooking = new TripBooking();
		Optional<Customer> scopeCustomer = customerRepository2.findById(customerId);
		if(scopeCustomer.isPresent()){
			Customer c = scopeCustomer.get();
			tripBooking.setCustomer(c);
		}
		tripBooking.setDriver(d);
		tripBooking.setBill(d.getCab().getPerKmRate()*distanceInKm);
		tripBooking.setDistanceInKm(distanceInKm);
		tripBooking.setFromLocation(fromLocation);
		tripBooking.setToLocation(toLocation);
		tripBooking.setTripStatus(TripStatus.CONFIRMED);


		return tripBookingRepository2.save(tripBooking);

	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly
		Optional<TripBooking> tripBooking = tripBookingRepository2.findById(tripId);
		if(tripBooking.isPresent()){
			TripBooking scopeTrip= tripBooking.get();
			scopeTrip.setTripStatus(TripStatus.CANCELED);
			Driver driver = scopeTrip.getDriver();
			Cab cab = driver.getCab();
			cab.setAvailable(true);
			cabRepository2.save(cab);
			tripBookingRepository2.save(scopeTrip);
		}
	}

	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingly
		Optional<TripBooking> tripBooking= tripBookingRepository2.findById(tripId);
		if(tripBooking.isPresent()){
		TripBooking scopeTrip=tripBooking.get();
		scopeTrip.setTripStatus(TripStatus.COMPLETED);
		tripBookingRepository2.save(scopeTrip);
		}

	}
}
