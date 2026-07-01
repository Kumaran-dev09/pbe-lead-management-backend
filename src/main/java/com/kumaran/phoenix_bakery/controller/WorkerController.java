package com.kumaran.phoenix_bakery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.kumaran.phoenix_bakery.model.Worker;
import com.kumaran.phoenix_bakery.service.WorkerService;

@RestController
@RequestMapping("/api/workers")
@CrossOrigin(origins = "*")
public class WorkerController {

    @Autowired
    private WorkerService workerService;

    @PostMapping("/login")
    public Worker login(@RequestBody Worker request) {

        return workerService.login(

                request.getWorkerId(),

                request.getPin()

        );

    }

    @GetMapping
    public List<Worker> getAllWorkers() {

        return workerService.getAllWorkers();

    }

    @PostMapping
    public Worker addWorker(@RequestBody Worker worker) {

        return workerService.saveWorker(worker);

    }

    @PutMapping("/{workerId}")
    public Worker updateWorker(

            @PathVariable String workerId,

            @RequestBody Worker worker

    ) {

        return workerService.updateWorker(workerId, worker);

    }

    @DeleteMapping("/{workerId}")
    public void deleteWorker(

            @PathVariable String workerId

    ) {

        workerService.deleteWorker(workerId);

    }

}