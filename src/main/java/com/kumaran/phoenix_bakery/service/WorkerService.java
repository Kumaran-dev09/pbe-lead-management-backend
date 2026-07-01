package com.kumaran.phoenix_bakery.service;

import java.util.List;

import com.kumaran.phoenix_bakery.model.Worker;

public interface WorkerService {

    Worker login(String workerId, String pin);

    List<Worker> getAllWorkers();

    Worker saveWorker(Worker worker);

    Worker updateWorker(String workerId, Worker worker);

    void deleteWorker(String workerId);

}