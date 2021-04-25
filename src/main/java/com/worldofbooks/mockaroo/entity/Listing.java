package com.worldofbooks.mockaroo.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@Entity
@Table(name = "listing")
public class Listing {

    @Id
    @NotNull
    private UUID id;

    @Column(columnDefinition = "text")
    @NotNull
    private String title;

    @Column(columnDefinition = "text")
    @NotNull
    private String description;

    @NotNull
    @OneToOne(cascade = CascadeType.PERSIST)
    private Location location;

    @NotNull
    private double listing_price;

    @Column(columnDefinition = "text")
    @NotNull
    private String currency;

    @NotNull
    private double quantity;

    @NotNull
    @OneToOne(cascade = CascadeType.PERSIST)
    private ListingStatus listingStatus;

    @NotNull
    @OneToOne(cascade = CascadeType.PERSIST)
    private MarketPlace marketPlace;

    @NotNull
    private Date upload_time;

    @Column(columnDefinition = "text")
    @NotNull
    private String owner_email_address;
}
