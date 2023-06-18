package com.rnd.springboot3.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name="Guitar")
@Table(name="guitar")
public class Guitar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="brand_name")
    private String brandName;
    @Column(name="country")
    private String country;
    @Column(name="is_deleted")
    private boolean isDeleted;

    @Column(name="created_date")
    private Date cretedDate;
    @Column(name="created_by")
    private String createdBy;
    @Column(name="modified_date")
    private Date modifiedDate;
    @Column(name="modified_by")
    private String modifiedBy;
}
