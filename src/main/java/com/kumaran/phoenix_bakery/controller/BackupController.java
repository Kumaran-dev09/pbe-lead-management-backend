package com.kumaran.phoenix_bakery.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.sql.DataSource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kumaran.phoenix_bakery.repository.LeadRepository;
import com.kumaran.phoenix_bakery.repository.WorkerRepository;

@RestController
@RequestMapping("/api/backup")
@CrossOrigin(origins = "*")
public class BackupController {

    private final DataSource dataSource;
    private final LeadRepository leadRepository;
    private final WorkerRepository workerRepository;

    public BackupController(DataSource dataSource, LeadRepository leadRepository, WorkerRepository workerRepository) {
        this.dataSource = dataSource;
        this.leadRepository = leadRepository;
        this.workerRepository = workerRepository;
    }

    @GetMapping("/database")
    public ResponseEntity<byte[]> downloadDatabase() throws SQLException, IOException {
        try (Connection connection = dataSource.getConnection()) {
            String dump = "-- Phoenix Bakery CRM backup\n" + "-- Database connection available\n" + "SELECT NOW();";
            return buildZipResponse("database-backup.sql", dump.getBytes(StandardCharsets.UTF_8));
        }
    }

    @GetMapping("/leads")
    public ResponseEntity<byte[]> downloadLeads() throws IOException {
        String content = "lead_id,lead_no,customer_name,mobile,company,worker_id\n" +
                leadRepository.findAll().stream()
                        .map(lead -> String.join(",", lead.getId().toString(), lead.getLeadNo(), lead.getCustomerName(), lead.getMobile(), lead.getCompany(), lead.getWorkerId()))
                        .reduce((a, b) -> a + "\n" + b)
                        .orElse("");
        return buildZipResponse("leads.csv", content.getBytes(StandardCharsets.UTF_8));
    }

    @GetMapping("/workers")
    public ResponseEntity<byte[]> downloadWorkers() throws IOException {
        String content = "worker_id,name,mobile,target,active\n" +
                workerRepository.findAll().stream()
                        .map(worker -> String.join(",", worker.getWorkerId(), worker.getName(), worker.getMobile(), worker.getTarget() == null ? "" : worker.getTarget().toString(), String.valueOf(worker.getActive())))
                        .reduce((a, b) -> a + "\n" + b)
                        .orElse("");
        return buildZipResponse("workers.csv", content.getBytes(StandardCharsets.UTF_8));
    }

    private ResponseEntity<byte[]> buildZipResponse(String fileName, byte[] content) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ZipOutputStream zos = new ZipOutputStream(baos)) {
            ZipEntry entry = new ZipEntry(fileName);
            zos.putNextEntry(entry);
            zos.write(content);
            zos.closeEntry();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileName + ".zip");
        return ResponseEntity.ok().headers(headers).body(baos.toByteArray());
    }
}
