package com.rnd.springboot3.repository;

import com.rnd.springboot3.entity.ReportDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportDetailRepository extends JpaRepository<ReportDetail, Long> {

}
