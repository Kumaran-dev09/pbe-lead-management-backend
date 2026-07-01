package com.kumaran.phoenix_bakery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kumaran.phoenix_bakery.model.Worker;
import com.kumaran.phoenix_bakery.repository.WorkerRepository;

@Service
public class WorkerServiceImpl implements WorkerService {

    @Autowired
    private WorkerRepository workerRepository;

    @Override
    public Worker login(String workerId, String pin) {

        return workerRepository

                .findByWorkerIdAndPinAndActiveTrue(workerId, pin)

                .orElse(null);

    }

    @Override
    public List<Worker> getAllWorkers() {

        return workerRepository.findAll();

    }

    @Override
    public Worker saveWorker(Worker worker) {

        return workerRepository.save(worker);

    }

    @Override
    public Worker updateWorker(String workerId, Worker worker) {

        worker.setWorkerId(workerId);

        return workerRepository.save(worker);

    }

    @Override
    public void deleteWorker(String workerId) {

        workerRepository.deleteById(workerId);

    }

}