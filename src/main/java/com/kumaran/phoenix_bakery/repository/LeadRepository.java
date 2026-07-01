package com.kumaran.phoenix_bakery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kumaran.phoenix_bakery.model.Lead;

public interface LeadRepository extends JpaRepository<Lead, Long> {

    List<Lead> findTop6ByOrderByCreatedAtDesc();
}