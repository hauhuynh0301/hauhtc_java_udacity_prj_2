package com.udacity.vehicles;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udacity.vehicles.entity.Car;
import com.udacity.vehicles.entity.CarDetails;
import com.udacity.vehicles.entity.Location;
import com.udacity.vehicles.entity.Manufacturer;
import com.udacity.vehicles.entity.enumerate.ConditionType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class VehiclesApiApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() {
    }

    @Test
    public void createCar() throws Exception {
        Car car = new Car();
        car.setConditionType(ConditionType.USED);

        CarDetails detail = new CarDetails();
        detail.setBody("sedan");
        detail.setModel("Impala");

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setCode(101);
        manufacturer.setName("Chevrolet");

        detail.setManufacturer(manufacturer);
        detail.setNumberOfDoors(4);
        detail.setFuelType("Gasoline");
        detail.setEngine("3.6L V6");
        detail.setMileage(32280);
        detail.setModelYear(2018);
        detail.setProductionYear(2018);
        detail.setExternalColor("white");
        car.setCarDetails(detail);

        Location location = new Location();
        location.setLat(40.73061);
        location.setLon(-73.935242);
        car.setLocation(location);

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(car);

        mockMvc.perform(post("/cars")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/cars/1")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(put("/cars/1")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/cars/1")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
