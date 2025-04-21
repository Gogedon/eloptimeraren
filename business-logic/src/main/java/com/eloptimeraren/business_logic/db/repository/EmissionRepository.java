package com.eloptimeraren.business_logic.db.repository;

import com.eloptimeraren.business_logic.db.entity.EmissionEntity;
import com.eloptimeraren.shared.enums.PriceZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

@Repository
public interface EmissionRepository extends JpaRepository<EmissionEntity, Long> {

    @Query("""
                SELECT e 
                FROM EmissionEntity e 
                WHERE e.priceZone = :priceZone 
                AND e.timeStart IN :startTimes
            """)
    List<EmissionEntity> findByPriceZoneAndStartTimes(
            @Param("priceZone") PriceZone priceZone,
            @Param("startTimes") Set<ZonedDateTime> startTimes
    );
}
