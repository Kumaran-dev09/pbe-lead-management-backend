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

        Worker worker = workerRepository

                .findByWorkerIdAndActiveTrue(workerId)

                .orElse(null);

        if (worker == null) {
            return null;
        }

        if (Boolean.FALSE.equals(worker.getLoginRequired())) {
            return worker;
        }

        if (pin == null || !pin.equals(worker.getPin())) {
            return null;
        }

        return worker;

    }

    @Override
    public List<Worker> getAllWorkers() {

        return workerRepository.findAll();

    }

    @Override
    public Worker saveWorker(Worker worker) {

        normalizeLoginFields(worker);

        return workerRepository.save(worker);

    }

    @Override
    public Worker updateWorker(String workerId, Worker worker) {

        worker.setWorkerId(workerId);

        normalizeLoginFields(worker);

        return workerRepository.save(worker);

    }

    private void normalizeLoginFields(Worker worker) {
        if (worker.getLoginRequired() == null) {
            worker.setLoginRequired(true);
        }

        if (Boolean.FALSE.equals(worker.getLoginRequired())) {
            worker.setPin(null);
        }
    }

    @Override
    public void deleteWorker(String workerId) {

        workerRepository.deleteById(workerId);

    }

}
