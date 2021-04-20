package com.worldofbooks.mockaroo.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@Entity
@Table(name = "location")
public class Location {

    @Id
    @NotNull
    private UUID id;

    @OneToOne(mappedBy = "location")
    private Listing listing;

    @Column(columnDefinition = "text")
    private String manager_name;

    @Column(columnDefinition = "text")
    private String phone;

    @Column(columnDefinition = "text")
    private String address_primary;

    @Column(columnDefinition = "text")
    private String address_secondary;

    @Column(columnDefinition = "text")
    private String country;

    @Column(columnDefinition = "text")
    private String town;

    @Column(columnDefinition = "text")
    private String postal_code;
}
