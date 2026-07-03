package com.kumaran.phoenix_bakery.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kumaran.phoenix_bakery.model.Lead;

public interface LeadRepository extends JpaRepository<Lead, Long> {

    List<Lead> findTop6ByOrderByCreatedAtDesc();

    @Query("SELECT l FROM Lead l WHERE " +
            "(:company IS NULL OR l.company = :company) AND " +
            "(:leadType IS NULL OR l.leadType = :leadType) AND " +
            "(:worker IS NULL OR l.workerId = :worker OR l.workerName LIKE %:worker%) AND " +
            "(:startDate IS NULL OR l.createdAt >= :startDate) AND " +
            "(:endDate IS NULL OR l.createdAt <= :endDate) AND " +
            "(:search IS NULL OR " +
            "l.customerName LIKE %:search% OR " +
            "l.mobile LIKE %:search% OR " +
            "l.leadNo LIKE %:search%)"
    )
    List<Lead> findLeads(@Param("company") String company,
                         @Param("leadType") String leadType,
                         @Param("worker") String worker,
                         @Param("startDate") LocalDate startDate,
                         @Param("endDate") LocalDate endDate,
                         @Param("search") String search,
                         Pageable pageable);

    long countByCreatedAtAfter(LocalDateTime date);

    @Query("SELECT l.company as name, count(l) as count FROM Lead l GROUP BY l.company")
    List<Map<String, Object>> countLeadsByCompany();

    @Query("SELECT l.leadType as name, count(l) as count FROM Lead l GROUP BY l.leadType")
    List<Map<String, Object>> countLeadsByLeadType();

    @Query("SELECT l.workerName as name, count(l) as count FROM Lead l WHERE l.workerName IS NOT NULL GROUP BY l.workerName")
    List<Map<String, Object>> countLeadsByWorker();
}