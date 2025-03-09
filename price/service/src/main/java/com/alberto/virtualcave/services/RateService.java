package com.alberto.virtualcave.services;

import com.alberto.virtualcave.converter.RateConverter;
import com.alberto.virtualcave.currency.generated.model.Currency;
import com.alberto.virtualcave.dao.RateDao;
import com.alberto.virtualcave.dto.Rate;
import com.alberto.virtualcave.entities.RateEntity;
import com.alberto.virtualcave.generated.model.RateFilter;
import com.alberto.virtualcave.generated.model.RateNew;
import com.alberto.virtualcave.generated.model.RateUpdated;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RateService {

    private final RateDao rateDao;
    private final ExternalCurrencyService externalCurrencyService;

    public RateService(RateDao rateDao, ExternalCurrencyService externalCurrencyService) {
        this.rateDao = rateDao;
        this.externalCurrencyService = externalCurrencyService;
    }

    public Rate getRate(int id) {
        Optional<RateEntity> rateEntity = rateDao.findById(id);
        return getRateFormatted(rateEntity);
    }

    public Optional<RateEntity> getRateId(int id) {
        return rateDao.findById(id);
    }

    public Rate getRateWithFilter(RateFilter filter) {
        Optional<RateEntity> rateEntity = rateDao.getRateWithFilter(filter.getProductId(), filter.getBrandId(), filter.getDate());
        return getRateFormatted(rateEntity);
    }

    public Rate addRate(RateNew rate) {
        return RateConverter.toRate(rateDao.save(RateConverter.toEntity(rate)));
    }

    public Rate updateRate(RateUpdated rateUpdated) {
        RateEntity entity = rateDao.findById(rateUpdated.getId()).get();
        entity.setPrice(rateUpdated.getPrice());
        return RateConverter.toRate(rateDao.save(entity));
    }

    public boolean deleteRate(int id) {
        Optional<RateEntity> rate = rateDao.findById(id);
        if (rate.isPresent()) {
            rateDao.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    private Rate getRateFormatted(Optional<RateEntity> rateEntity) {
        if(rateEntity.isPresent()){
            Currency currency = externalCurrencyService.getCurrencyCode(rateEntity.get().getCurrencyCode()).getBody();
            Rate rate = RateConverter.toRate(rateEntity.get());
            double factor = Math.pow(10, currency.getDecimals());
            rate.setPrice(Math.round(rateEntity.get().getPrice() * factor) / factor +currency.getSymbol());
            return rate;
        }
        return null;
    }

}