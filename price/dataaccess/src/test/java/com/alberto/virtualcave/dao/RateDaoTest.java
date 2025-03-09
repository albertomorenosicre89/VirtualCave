package com.alberto.virtualcave.dao;

import com.alberto.virtualcave.ApplicationNotFound;
import com.alberto.virtualcave.entities.RateEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(classes= ApplicationNotFound.class)
public class RateDaoTest {

	@Autowired
    private RateDao rateDao;

    @Test
    public void whenGetRateWithFilter_withDate20220101_returnRateEntityWithId1() {
        LocalDate startDate = LocalDate.of(2022,1,1);
        LocalDate endDate = LocalDate.of(2022,5,31);
        int productId = 1;
        int brandId = 1;
        RateEntity result = rateDao.getRateWithFilter(productId , brandId , startDate).get();

        assertEquals(1, result.getBrandId());
        assertEquals(1, result.getProductId());
        assertEquals(1550, result.getPrice());
        assertEquals("EUR", result.getCurrencyCode());
        assertEquals(startDate, result.getStartDate());
        assertEquals(endDate, result.getEndDate());
    }

    @Test
    public void whenGetRateWithFilter_withDate20220701_returnRateEntityWithId2() {

        LocalDate date = LocalDate.of(2022,7,1);
        LocalDate startDate = LocalDate.of(2022,6,1);
        LocalDate endDate = LocalDate.of(2022,12,31);
        int productId = 1;
        int brandId = 1;
        RateEntity result = rateDao.getRateWithFilter(productId , brandId , date).get();

        assertEquals(1, result.getBrandId());
        assertEquals(1, result.getProductId());
        assertEquals(1850, result.getPrice());
        assertEquals("USD", result.getCurrencyCode());
        assertEquals(startDate, result.getStartDate());
        assertEquals(endDate, result.getEndDate());
    }

}