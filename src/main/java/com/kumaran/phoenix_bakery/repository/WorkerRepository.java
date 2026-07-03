package com.kumaran.phoenix_bakery.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kumaran.phoenix_bakery.model.Worker;

public interface WorkerRepository extends JpaRepository<Worker,String>{

    Optional<Worker> findByWorkerIdAndActiveTrue(String workerId);

    Optional<Worker> findByWorkerIdAndPinAndActiveTrue(

            String workerId,

            String pin

    );

}
