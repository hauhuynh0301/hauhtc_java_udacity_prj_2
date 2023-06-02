package com.udacity.vehicles.service.impl;

import com.udacity.vehicles.entity.Car;
import com.udacity.vehicles.exception.CarNotFoundException;
import com.udacity.vehicles.repository.CarRepository;
import java.util.List;
import java.util.Optional;

import com.udacity.vehicles.service.CarService;
import com.udacity.vehicles.service.MapsService;
import com.udacity.vehicles.service.PriceService;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository repository;
    private final PriceService priceService;
    private final MapsService mapsService;

    public CarServiceImpl(CarRepository repository, PriceService priceService, MapsService mapsService) {
        this.repository = repository;
        this.priceService = priceService;
        this.mapsService = mapsService;
    }

    @Override
    public List<Car> getCars() {
        return repository.findAll();
    }

    @Override
    public Car findById(Long id) {
        Optional<Car> optionalCar = repository.findById(id);
        if(optionalCar.isEmpty()){
            throw new CarNotFoundException();
        }
        Car car = optionalCar.get();

        car.setPrice(priceService.getPrice(id));
        car.setLocation(mapsService.getAddress(car.getLocation()));

        return car;
    }

    @Override
    public Car save(Car car) {
        if (car.getId() != null) {
            return repository.findById(car.getId())
                    .map(carToBeUpdated -> {
                        carToBeUpdated.setCarDetails(car.getCarDetails());
                        carToBeUpdated.setPrice(car.getPrice());
                        carToBeUpdated.setConditionType(car.getConditionType());
                        carToBeUpdated.setLocation(car.getLocation());
                        return repository.save(carToBeUpdated);
                    }).orElseThrow(CarNotFoundException::new);
        }

        return repository.save(car);
    }

    @Override
    public void delete(Long id) {
        Optional<Car> optionalCar = repository.findById(id);
        if(optionalCar.isEmpty()){
            throw new CarNotFoundException();
        }
        repository.deleteById(id);
    }
}
