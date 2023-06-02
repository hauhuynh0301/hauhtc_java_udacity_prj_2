package com.udacity.vehicles.api;

import com.udacity.vehicles.entity.Car;
import com.udacity.vehicles.entity.CarDetails;
import com.udacity.vehicles.entity.Location;
import com.udacity.vehicles.entity.Manufacturer;
import com.udacity.vehicles.entity.enumerate.ConditionType;
import com.udacity.vehicles.service.CarService;
import com.udacity.vehicles.service.impl.MapsServiceImpl;
import com.udacity.vehicles.service.impl.PriceServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Implements testing of the CarController class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class CarControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<Car> json;

    @MockBean
    private CarService carService;

    @MockBean
    private PriceServiceImpl priceServiceImpl;

    @MockBean
    private MapsServiceImpl mapsServiceImpl;

    /**
     * Creates pre-requisites for testing, such as an example car.
     */
    @Before
    public void setup() {
        Car car = getCar();
        car.setId(1L);
        given(carService.save(any())).willReturn(car);
        given(carService.findById(any())).willReturn(car);
        given(carService.getCars()).willReturn(Collections.singletonList(car));
    }

    /**
     * Tests for successful creation of new car in the system
     *
     * @throws Exception when car creation fails in the system
     */
    @Test
    public void createCar() throws Exception {
        Car car = getCar();
        mvc.perform(
                post(new URI("/cars"))
                        .content(json.write(car).getJson())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(status().isCreated());
    }

    /**
     * Tests if the read operation appropriately returns a list of vehicles.
     *
     * @throws Exception if the read operation of the vehicle list fails
     */
    @Test
    public void listCars() throws Exception {
        createCar();
        createCar();
        createCar();
        mvc.perform(
                get(new URI("/cars"))
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(status().isOk());
    }

    /**
     * Tests the read operation for a single car by ID.
     *
     * @throws Exception if the read operation for a single car fails
     */
    @Test
    public void findCar() throws Exception {
        createCar();
        mvc.perform(
                get(new URI("/cars/1"))
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(status().isOk());
    }

    /**
     * Tests the deletion of a single car by ID.
     *
     * @throws Exception if the delete operation of a vehicle fails
     */
    @Test
    public void deleteCar() throws Exception {
        createCar();
        mvc.perform(
                delete(new URI("/cars/1"))
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(status().isNoContent());
    }

    @Test
    public void updateCar() throws Exception {
        Car car = getCar();
        createCar();
        mvc.perform(
                put(new URI("/cars/1"))
                        .content(json.write(car).getJson())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(status().isOk());
    }

    /**
     * Creates an example Car object for use in testing.
     *
     * @return an example Car object
     */
    private Car getCar() {
        Car car = new Car();
        car.setLocation(new Location(40.730610, -73.935242));
        CarDetails carDetails = new CarDetails();
        Manufacturer manufacturer = new Manufacturer(101, "Chevrolet");
        carDetails.setManufacturer(manufacturer);
        carDetails.setModel("Impala");
        carDetails.setMileage(32280);
        carDetails.setExternalColor("white");
        carDetails.setBody("sedan");
        carDetails.setEngine("1L V1");
        carDetails.setFuelType("Gasoline");
        carDetails.setModelYear(2018);
        carDetails.setProductionYear(2018);
        carDetails.setNumberOfDoors(4);
        car.setCarDetails(carDetails);
        car.setConditionType(ConditionType.USED);
        return car;
    }
}