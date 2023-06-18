package com.rnd.springboot3.entity.custommapping;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@SqlResultSetMapping(name="GuitarCustom",
    entities = {
        @EntityResult(entityClass = GuitarCustom.class,
        fields = {
                @FieldResult(name = "id", column = "id"),
                @FieldResult(name = "brandName", column = "brand_name"),
                @FieldResult(name = "country", column = "country"),
                @FieldResult(name = "type", column = "type"),
                @FieldResult(name = "price", column = "price"),
                @FieldResult(name = "isDeleted", column = "is_deleted"),
                @FieldResult(name = "guitarId", column = "guitar_id"),
                @FieldResult(name = "modifiedDate", column = "modified_date"),
                @FieldResult(name = "modifiedBy", column = "modified_by")
        })
    })
public class GuitarCustom {

    @Id
    private long id;
    private String brandName;
    private String country;
    private String type;
    private BigDecimal price;
    private Boolean isDeleted;
    private long guitarId;
    private Date modifiedDate;
    private String modifiedBy;

}
