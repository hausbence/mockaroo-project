package com.worldofbooks.mockaroo.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@Entity
@Table(name = "listing_status")
public class ListingStatus {

    @Id
    @NotNull
    private Long id;

    @Column(columnDefinition = "text")
    private String status_name;

}
