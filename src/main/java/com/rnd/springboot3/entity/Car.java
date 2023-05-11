package com.rnd.springboot3.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "Car")
@Table(name="car")
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="manufactur")
    private String manufactur;
    @Column(name="model")
    private String model;
    @Column(name="type")
    private String type;
    @Column(name="created_date")
    private Date createdDate;

}
