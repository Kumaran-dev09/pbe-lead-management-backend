package com.kumaran.phoenix_bakery.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kumaran.phoenix_bakery.model.Settings;
import com.kumaran.phoenix_bakery.service.SettingsService;

@RestController
@RequestMapping("/api/settings")
@CrossOrigin(origins = "*")
public class SettingsController {

    private final SettingsService settingsService;

    public SettingsController(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @GetMapping
    public List<Settings> getAllSettings() {
        return settingsService.getAllSettings();
    }

    @GetMapping("/{keyName}")
    public Settings getSetting(@PathVariable String keyName) {
        return settingsService.getSetting(keyName).orElse(null);
    }

    @PostMapping
    public Settings saveSetting(@RequestBody Settings settings) {
        return settingsService.saveSetting(settings);
    }

    @PutMapping("/{keyName}")
    public Settings updateSetting(@PathVariable String keyName, @RequestBody Settings settings) {
        return settingsService.updateSetting(keyName, settings);
    }

    @DeleteMapping("/{keyName}")
    public void deleteSetting(@PathVariable String keyName) {
        settingsService.deleteSetting(keyName);
    }
}
