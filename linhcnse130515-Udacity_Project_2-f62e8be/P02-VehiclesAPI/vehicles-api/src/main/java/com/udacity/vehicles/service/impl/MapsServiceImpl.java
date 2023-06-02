package com.udacity.vehicles.service.impl;

import com.udacity.vehicles.dto.AddressDTO;
import com.udacity.vehicles.entity.Location;
import com.udacity.vehicles.service.MapsService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

@Service
public class MapsServiceImpl implements MapsService {

    private static final Logger log = LoggerFactory.getLogger(MapsServiceImpl.class);

    private final WebClient client;
    private final ModelMapper mapper;

    public MapsServiceImpl(WebClient maps,
                           ModelMapper mapper) {
        this.client = maps;
        this.mapper = mapper;
    }

    @Override
    public Location getAddress(Location location) {
        try {
            AddressDTO addressDTO = client
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/maps/")
                            .queryParam("lat", location.getLat())
                            .queryParam("lon", location.getLon())
                            .build()
                    )
                    .retrieve().bodyToMono(AddressDTO.class).block();

            mapper.map(Objects.requireNonNull(addressDTO), location);

            return location;
        } catch (Exception e) {
            log.warn("Map service is down");
            return location;
        }
    }
}
