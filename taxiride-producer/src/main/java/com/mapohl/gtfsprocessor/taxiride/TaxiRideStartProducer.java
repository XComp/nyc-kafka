package com.mapohl.gtfsprocessor.taxiride;

import com.mapohl.gtfsprocessor.taxiride.configuration.TaxiRideStartConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;

@Slf4j
public class TaxiRideStartProducer {

    public static void main(String[] args) {
        SpringApplication.run(TaxiRideStartConfiguration.class, args).close();
    }

}