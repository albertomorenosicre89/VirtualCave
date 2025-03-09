package com.alberto.virtualcave.services;

import com.alberto.virtualcave.converter.RateConverter;
import com.alberto.virtualcave.dao.RateDao;
import com.alberto.virtualcave.dto.Rate;
import com.alberto.virtualcave.entities.RateEntity;
import com.alberto.virtualcave.generated.model.RateNew;
import com.alberto.virtualcave.generated.model.RateUpdated;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(SpringExtension.class)
public class RateServiceTest {

    @InjectMocks
    private RateService rateService;

    @Mock
    private RateDao rateDao;

    @Test
    public void whenAddRate_withNoRateInSameDates_success() {
        //GIVEN
        LocalDate startDate = LocalDate.of(2020, Month.of(6),14);
        LocalDate endDate = LocalDate.of(2020, Month.of(7),14);
        RateEntity entity = RateEntity.builder().
                id(1).
                productId(1).
                brandId(1).
                price(30.2).
                startDate(startDate).
                endDate(endDate).
                currencyCode("EUR").
                build();

        RateNew rateNew = RateNew.builder().
                productId(1).
                brandId(1).
                price(30.2).
                startDate(startDate).
                endDate(endDate).
                currencyCode("EUR").
                build();

        when(rateDao.save(RateConverter.toEntity(rateNew))).thenReturn(entity);

        //WHEN
        Rate result = rateService.addRate(rateNew);

        //THEN
        assertEquals(1, result.getId());
        assertEquals(1, result.getBrandId());
        assertEquals(1, result.getProductId());
        assertEquals("30.2", result.getPrice());
        assertEquals(startDate, result.getStartDate());
        assertEquals(endDate, result.getEndDate());
        assertEquals("EUR", result.getCurrencyCode());
        verify(rateDao,times(1)).save(RateConverter.toEntity(rateNew));
    }

    @Test
    public void whenUpdateRate_withNoRateInSameDates_success() {
        //GIVEN
        LocalDate startDate = LocalDate.of(2020, Month.of(6),14);
        LocalDate endDate = LocalDate.of(2020, Month.of(7),14);
        RateEntity entity = RateEntity.builder().
                id(1).
                productId(1).
                brandId(1).
                price(30.2).
                startDate(startDate).
                endDate(endDate).
                currencyCode("EUR").
                build();

        RateUpdated rateUpdated = RateUpdated.builder().
                id(1).
                price(33.2).
                build();

        RateEntity entityResult = RateEntity.builder().
                id(1).
                productId(1).
                brandId(1).
                price(33.2).
                startDate(startDate).
                endDate(endDate).
                currencyCode("EUR").
                build();

        //WHEN
        when(rateDao.findById(rateUpdated.getId())).thenReturn(Optional.of(entity));
        when(rateDao.save(entity)).thenReturn(entityResult);
        Rate result = rateService.updateRate(rateUpdated);

        //THEN
        assertEquals(1, result.getId());
        assertEquals(1, result.getBrandId());
        assertEquals(1, result.getProductId());
        assertEquals("33.2", result.getPrice());
        assertEquals(startDate, result.getStartDate());
        assertEquals(endDate, result.getEndDate());
        assertEquals("EUR", result.getCurrencyCode());
        verify(rateDao,times(1)).save(entityResult);
        verify(rateDao,times(1)).findById(rateUpdated.getId());
    }

    @Test
    public void whenDeleteRate_isDeleted() {
        //GIVEN
        int id = 100;
        LocalDate startDate = LocalDate.of(2020, Month.of(6),14);
        LocalDate endDate = LocalDate.of(2020, Month.of(7),14);
        RateEntity entity = RateEntity.builder().
                id(id).
                productId(1).
                brandId(1).
                price(30.2).
                startDate(startDate).
                endDate(endDate).
                currencyCode("EUR").
                build();

        when(rateDao.findById(id)).thenReturn(Optional.of(entity));
        doNothing().when(rateDao).deleteById(id);

        //WHEN
        boolean result = rateService.deleteRate(id);

        //THEN
        assertTrue(result);
        verify(rateDao, Mockito.times(1)).deleteById(id);
    }

    @Test
    public void whenDeleteRate_withNotExisting_notDeleted() {
        //GIVEN
        int id = 100;
        when(rateDao.findById(id)).thenReturn(Optional.empty());
        doNothing().when(rateDao).deleteById(id);

        //WHEN
        boolean result = rateService.deleteRate(id);

        //THEN
        assertFalse(result);
        verify(rateDao, Mockito.times(0)).deleteById(id);
    }
}