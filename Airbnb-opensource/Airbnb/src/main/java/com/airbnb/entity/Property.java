package com.airbnb.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity

@Table(name = "property")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "property_name", nullable = false)
    private String propertyName;

    @Column(name = "guests", nullable = false)
    private Integer guests;

    @Column(name = "bedrooms", nullable = false)
    private Integer bedrooms;

    @Column(name = "beds", nullable = false)
    private Integer beds;

    @Column(name = "bathrooms", nullable = false)
    private Integer bathrooms;

    @Column(name = "nightly_price")
    private Double nightlyPrice;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

}