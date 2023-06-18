package com.rnd.springboot3.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name="GuitarDetail")
@Table(name="guitar_detail")
public class GuitarDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="type")
    private String type;
    @Column(name="price")
    private BigDecimal price;
    @Column(name="is_deleted")
    private boolean isDeleted;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="guitar_id")
    private Guitar guitar;

    @Column(name="created_date")
    private Date createdDate;
    @Column(name="created_by")
    private String createdBy;
    @Column(name="modified_date")
    private Date modifiedDate;
    @Column(name="modified_by")
    private String modifiedBy;

}
