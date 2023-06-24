package com.driver.services.impl;

import com.driver.model.Cab;
import com.driver.repository.CabRepository;
import com.driver.services.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Driver;
import com.driver.repository.DriverRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DriverServiceImpl implements DriverService {

	@Autowired
	DriverRepository driverRepository3;

	@Autowired
	CabRepository cabRepository3;

	@Override
	public void register(String mobile, String password){
		//Save a driver in the database having given details and a cab with ratePerKm as 10 and availability as True by default.
		Driver newDriver = new Driver();
		Cab driverCab = new Cab();
//		create Cab
		driverCab.setAvailable(true);
		driverCab.setPerKmRate(10);
		cabRepository3.save(driverCab);
// create driver
		newDriver.setCab(cabRepository3.save(driverCab));
		newDriver.setMobile(mobile);
		newDriver.setPassword(password);

		driverRepository3.save(newDriver);
	}

	@Override
	public void removeDriver(int driverId){
		// Delete driver without using deleteById function
		List<Driver> driverList = driverRepository3.findAll();

		for(int i=0;i<driverList.size();i++){
			Driver scopeDriver = driverList.get(i);
			if(scopeDriver.getDriverId() == driverId){
				driverRepository3.delete(scopeDriver);
			}
		}
	}

	@Override
	public void updateStatus(int driverId){
		//Set the status of respective car to unavailable
		Optional<Driver> driver = driverRepository3.findById(driverId);
		if (driver.isPresent()){
			Driver scopeDrive = driver.get();
			scopeDrive.getCab().setAvailable(false);
			driverRepository3.save(scopeDrive);
		}
	}
}
