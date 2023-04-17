package com.rnd.completablefuture.repository;

import com.rnd.completablefuture.entity.ReportDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportDetailRepository extends JpaRepository<ReportDetail, Long> {

}
