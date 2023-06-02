package com.udacity.vehicles.service.impl;

import com.udacity.vehicles.controller.CarController;
import com.udacity.vehicles.entity.Car;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Service
public class CarResourceAssembler implements ResourceAssembler<Car, Resource<Car>> {

    @Override
    public Resource<Car> toResource(Car car) {
        return new Resource<>(car,
                linkTo(methodOn(CarController.class).getOneById(car.getId())).withSelfRel(),
                linkTo(methodOn(CarController.class).getCars()).withRel("cars"));

    }
}
