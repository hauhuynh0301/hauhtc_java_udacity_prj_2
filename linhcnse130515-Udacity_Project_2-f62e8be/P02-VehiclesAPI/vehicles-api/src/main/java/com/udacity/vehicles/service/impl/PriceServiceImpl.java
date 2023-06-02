package com.udacity.vehicles.service.impl;

import com.udacity.vehicles.dto.PriceDTO;
import com.udacity.vehicles.service.PriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

@Service
public class PriceServiceImpl implements PriceService {

    private static final Logger log = LoggerFactory.getLogger(PriceServiceImpl.class);

    private final WebClient client;

    public PriceServiceImpl(WebClient pricing) {
        this.client = pricing;
    }

    @Override
    public String getPrice(Long vehicleId) {
        try {
            PriceDTO priceDTO = client
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path("services/price/")
                            .queryParam("vehicleId", vehicleId)
                            .build()
                    )
                    .retrieve().bodyToMono(PriceDTO.class).block();
            if (Objects.nonNull(priceDTO)) {
                return String.format("%s %s", priceDTO.getCurrency(), priceDTO.getPrice());
            }
        } catch (Exception e) {
            log.error("Unexpected error retrieving price for vehicle {}", vehicleId, e);
        }
        return "(consult price)";
    }
}
