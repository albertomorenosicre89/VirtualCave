package com.alberto.virtualcave.converter;

import com.alberto.virtualcave.dto.Rate;
import com.alberto.virtualcave.entities.RateEntity;
import com.alberto.virtualcave.generated.model.RateNew;

public class RateConverter {

	public static RateEntity toEntity(RateNew dto) {
		if (dto == null) {
			return null;
		}

		return RateEntity.builder()
				.brandId(dto.getBrandId())
				.productId(dto.getProductId())
				.startDate(dto.getStartDate())
				.endDate(dto.getEndDate())
				.price(dto.getPrice())
				.currencyCode(dto.getCurrencyCode())
				.build();
	}

	public static Rate toRate(RateEntity entity){
		if(entity==null){
			return null;
		}

		return Rate.builder()
				.id(entity.getId())
				.brandId(entity.getBrandId())
				.productId(entity.getProductId())
				.startDate(entity.getStartDate())
				.endDate(entity.getEndDate())
				.price(entity.getPrice()+"")
				.currencyCode(entity.getCurrencyCode())
				.build();
	}

}