package com.mapohl.gtfsprocessor.gtfsloader.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
@Table(name = "shape_points")
public class ShapePoint extends AbstractEntity {

    @Id
    @Column(name = "shape_point_id", nullable = false)
    long id;

    @Column(name = "shape_id", nullable = false)
    int shapeId;

    @Column(name = "latitude", nullable = false)
    double latitude;

    @Column(name = "longitude", nullable = false)
    double longitude;

    @Column(name = "sequence", nullable = false)
    int order;
}
