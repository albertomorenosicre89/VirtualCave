package com.alberto.virtualcave.services;

import com.alberto.virtualcave.currency.generated.model.Currency;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="external-currency", url="http://localhost:8081")
public interface ExternalCurrencyService {

    @GetMapping("/v1/currencies")
    ResponseEntity<List<Currency>> getCurrencies();

    @GetMapping("/v1/currencies/{currencyCode}")
    ResponseEntity<Currency> getCurrencyCode(@PathVariable("currencyCode") String currencyCode);

}
