package com.mapohl.gtfsprocessor.gtfsloader.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "stops")
public class Stop extends AbstractEntity {

    @Id
    @Column(name = "stop_id", nullable = false)
    String id;

    @Column(name = "stop_code")
    String stopCode;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "description")
    String description;

    @Column(name = "latitude")
    double latitude;

    @Column(name = "longitude")
    double longitude;

    @Column(name = "location_type")
    int locationType;

    @Column(name = "parent_station")
    String parentStation;

    @Column(name = "wheelchair_boarding", nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    boolean wheelchairBoarding;

    @Column(name = "platform_code")
    String platformCode;

    @Column(name = "zone_id")
    String zoneId;
}
