package com.alberto.virtualcave.converter;

import com.alberto.virtualcave.dto.Rate;
import com.alberto.virtualcave.entities.RateEntity;
import com.alberto.virtualcave.generated.model.RateNew;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class RateConverterTest {

    @Test
    public void whenToEntityRate() {
        LocalDate startDate = LocalDate.of(2020, Month.of(6),14);
        LocalDate endDate = LocalDate.of(2020, Month.of(6),14);

        RateEntity entity = RateEntity.builder().
                id(1).
                productId(1).
                brandId(1).
                price(30.2).
                startDate(startDate).
                endDate(endDate).
                currencyCode("EUR").
                build();

        Rate result = RateConverter.toRate(entity);
        assertEquals(1, result.getId());
        assertEquals(1, result.getBrandId());
        assertEquals(1, result.getProductId());
        assertEquals("30.2", result.getPrice());
        assertEquals(startDate, result.getStartDate());
        assertEquals(endDate, result.getEndDate());
        assertEquals("EUR", result.getCurrencyCode());
    }

    @Test
    public void whenToEntityRateNew() {
        LocalDate startDate = LocalDate.of(2020, Month.of(6),14);
        LocalDate endDate = LocalDate.of(2020, Month.of(6),14);

        RateNew rateNew = RateNew.builder().
                productId(1).
                brandId(1).
                price(30.2).
                startDate(startDate).
                endDate(endDate).
                currencyCode("EUR").
                build();

        RateEntity result = RateConverter.toEntity(rateNew);
        assertEquals(1, result.getBrandId());
        assertEquals(1, result.getProductId());
        assertEquals(30.2, result.getPrice());
        assertEquals(startDate, result.getStartDate());
        assertEquals(endDate, result.getEndDate());
        assertEquals("EUR", result.getCurrencyCode());
    }

    @Test
    public void whenToRate() {
        LocalDate startDate = LocalDate.of(2020, Month.of(6),14);
        LocalDate endDate = LocalDate.of(2020, Month.of(6),14);

        RateNew rate = RateNew.builder().
                productId(1).
                brandId(1).
                price(30.2).
                startDate(startDate).
                endDate(endDate).
                currencyCode("EUR").
                build();

        RateEntity result = RateConverter.toEntity(rate);
        assertEquals(1, result.getBrandId());
        assertEquals(1, result.getProductId());
        assertEquals(30.2, result.getPrice());
        assertEquals(startDate, result.getStartDate());
        assertEquals(endDate, result.getEndDate());
        assertEquals("EUR", result.getCurrencyCode());
    }
}
