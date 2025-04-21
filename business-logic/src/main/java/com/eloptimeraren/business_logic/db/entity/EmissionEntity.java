package com.eloptimeraren.business_logic.db.entity;

import com.eloptimeraren.shared.enums.PriceZone;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@Entity
@Builder
@Table(name = "emission")
@NoArgsConstructor
@AllArgsConstructor
public class EmissionEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "source", nullable = false)
    private String source;
    
    @Column(name = "gco2e_per_kwh", nullable = false)
    private int gCO2ePerKWh;

    @Enumerated(EnumType.STRING)
    @Column(name = "price_zone", nullable = false)
    private PriceZone priceZone;

    @Column(name = "time_start", nullable = false)
    private ZonedDateTime timeStart;

    @Column(name = "time_end", nullable = false)
    private ZonedDateTime timeEnd;

    @Column(name = "normalized_at", nullable = false)
    private ZonedDateTime normalizedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private ZonedDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private ZonedDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        ZonedDateTime now = ZonedDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = ZonedDateTime.now();
    }
}
