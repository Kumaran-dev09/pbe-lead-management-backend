package com.kumaran.phoenix_bakery.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kumaran.phoenix_bakery.model.Admin;
import com.kumaran.phoenix_bakery.model.Settings;
import com.kumaran.phoenix_bakery.model.Worker;
import com.kumaran.phoenix_bakery.repository.AdminRepository;
import com.kumaran.phoenix_bakery.repository.SettingsRepository;
import com.kumaran.phoenix_bakery.repository.WorkerRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(AdminRepository adminRepository,
                               WorkerRepository workerRepository,
                               SettingsRepository settingsRepository) {
        return args -> {
            Admin admin = adminRepository.findByUsername("PHXBE01");
            if (admin == null) {
                admin = new Admin();
                admin.setUsername("PHXBE01");
            }
            admin.setAdminId("ADMIN001");
            admin.setPassword("Phoenix@222");
            admin.setName("DEEPANA");
            admin.setRole("SUPER_ADMIN");
            admin.setStatus("Active");
            admin.setMobile("9600910222");
            admin.setEmail("admin@phoenixbakeryequipments.in");
            adminRepository.save(admin);

            Worker worker = workerRepository.findById("PHX001").orElseGet(Worker::new);
            worker.setWorkerId("PHX001");
            worker.setName("Kumaran");
            worker.setMobile("8838772761");
            worker.setPin("5879");
            worker.setTarget(50);
            worker.setActive(true);
            worker.setLoginRequired(true);
            workerRepository.save(worker);

            if (settingsRepository.findByKeyName("app.name").isEmpty()) {
                Settings appName = new Settings();
                appName.setKeyName("app.name");
                appName.setSettingValue("Phoenix Bakery CRM");
                appName.setDescription("Application name");
                settingsRepository.save(appName);
            }

            if (settingsRepository.findByKeyName("session.duration.hours").isEmpty()) {
                Settings sessionHours = new Settings();
                sessionHours.setKeyName("session.duration.hours");
                sessionHours.setSettingValue("5");
                sessionHours.setDescription("Login session duration in hours");
                settingsRepository.save(sessionHours);
            }
        };
    }
}
