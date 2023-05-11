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
@Table(name = "ReportDetail")
@Entity(name="report_detail")
public class ReportDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="file_name")
    private String fileName;

    @Column(name="file_path", length = 10000)
    private String filePath;

    @Column(name="created_date")
    private Date createdDate;

    @Column(name="status")
    private String status;

}
