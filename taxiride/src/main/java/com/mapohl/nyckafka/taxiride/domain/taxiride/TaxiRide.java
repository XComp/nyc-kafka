package com.mapohl.nyckafka.taxiride.domain.taxiride;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mapohl.nyckafka.common.Entity;
import com.mapohl.nyckafka.taxiride.domain.NYCTaxiZone;
import com.mapohl.nyckafka.taxiride.domain.utils.NYCTaxiRideUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaxiRide implements Entity<Long> {

    private Long entityId;

    private String pickupTimeStr;
    private String dropOffTimeStr;

    private int passengerCount;

    private double distance;

    private NYCTaxiZone pickupZone;
    private NYCTaxiZone dropOffZone;

    private int paymentTypeId;
    private double tollAmount;
    private double totalAmount;

    @JsonIgnore
    @Override
    public Instant getEventTime() {
        return NYCTaxiRideUtils.parse(this.getPickupTimeStr());
    }
}
