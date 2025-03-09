package com.alberto.virtualcave.controller;

import com.alberto.virtualcave.dto.Rate;
import com.alberto.virtualcave.generated.api.RatesApi;
import com.alberto.virtualcave.generated.model.RateFilter;
import com.alberto.virtualcave.generated.model.RateNew;
import com.alberto.virtualcave.generated.model.RateUpdated;
import com.alberto.virtualcave.services.RateService;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
public class RateController implements RatesApi {

    private final RateService rateService;

    public RateController(RateService rateService) {
        this.rateService = rateService;
    }

    @Override
    public ResponseEntity<Object> addRate(RateNew rateNew) {
        try {
            Rate rate = rateService.addRate(rateNew);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(rate.getId()).toUri();
            return ResponseEntity.created(location).body(rate);
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "error", "Validation failed",
                    "message", e.getMessage()
            ));
        }
    }

    @Override
    public ResponseEntity<Object> getRate(RateFilter rateFilter) {
        Rate rate = rateService.getRateWithFilter(rateFilter);
        if (rate != null) {
            return new ResponseEntity<>(rate, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rate not found with the parameters selected");
    }

    @Override
    public ResponseEntity<Object> getRate(Integer id) {
        Rate rate = rateService.getRate(id);
        if (rate != null) {
            return new ResponseEntity<>(rate, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rate not found with the id selected");
    }

    @Override
    public ResponseEntity<Object> removeRate(Integer id) {
        if (rateService.deleteRate(id)) {
            return ResponseEntity.ok("Rate delete correctly  with the id selected");
        } else {
            return new ResponseEntity<>("Rate not found with the id selected", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Object> updateRate(RateUpdated rateUpdated) {
        if(rateService.getRateId(rateUpdated.getId()).isPresent() ){
            try {
                Rate rate = rateService.updateRate( rateUpdated);
                return ResponseEntity.ok(rate);
            } catch (ValidationException e) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                        "error", "Validation failed",
                        "message", e.getMessage()
                ));
            }
        }
        return new ResponseEntity<>("Rate not found with the id selected", HttpStatus.OK);
    }

}