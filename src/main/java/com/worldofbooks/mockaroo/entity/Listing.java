package com.worldofbooks.mockaroo.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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

    /** TODO
     * This is going to be the reference to location table
     */
    @NotNull
    private UUID location_id;

    @Column(columnDefinition = "Decimal(10,2)")
    @Min(value = 1)
    @NotNull
    private double listing_price;

    @Column(columnDefinition = "text", length = 3)
    @Size(min = 3, max = 3)
    @NotNull
    private String currency;

    @Min(value = 1)
    @NotNull
    private double quantity;

    /** TODO
     * This is going to be the reference to listing status table
     */
    @NotNull
    private double listing_status;

    /** TODO
     * This is going to be the reference to marketplace table
     */
    @NotNull
    private double marketplace;

    @Column(columnDefinition = "text")
    @NotNull
    private Date upload_time;

    @Column(columnDefinition = "text")
    @NotNull
    @Email
    private String owner_email_address;
}
