package com.udacity.vehicles.service;

import com.udacity.vehicles.entity.Car;

import java.util.List;

public interface CarService {

    List<Car> getCars();

    Car findById(Long id);

    Car save(Car car);

    void delete(Long id);
}
