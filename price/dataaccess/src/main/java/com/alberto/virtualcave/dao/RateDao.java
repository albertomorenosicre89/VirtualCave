package com.alberto.virtualcave.dao;

import com.alberto.virtualcave.entities.RateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface RateDao extends JpaRepository<RateEntity, Integer> {

    @Query(value = "select re from RateEntity re where " +
            " re.startDate <= cast(:selectedDate as date) and re.endDate >= cast(:selectedDate as date) and "+
            " re.productId = :productId and " +
            " re.brandId = :brandId ")
    Optional<RateEntity> getRateWithFilter(@Param("productId") Integer productId , @Param("brandId") Integer brandId, @Param("selectedDate") LocalDate selectedDate);

}