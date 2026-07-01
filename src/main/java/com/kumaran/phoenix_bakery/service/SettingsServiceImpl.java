package com.kumaran.phoenix_bakery.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kumaran.phoenix_bakery.model.Settings;
import com.kumaran.phoenix_bakery.repository.SettingsRepository;

@Service
public class SettingsServiceImpl implements SettingsService {

    private final SettingsRepository repository;

    public SettingsServiceImpl(SettingsRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Settings> getAllSettings() {
        return repository.findAll();
    }

    @Override
    public Optional<Settings> getSetting(String keyName) {
        return repository.findByKeyName(keyName);
    }

    @Override
    public Settings saveSetting(Settings settings) {
        return repository.save(settings);
    }

    @Override
    public Settings updateSetting(String keyName, Settings settings) {
        return repository.findByKeyName(keyName)
                .map(existing -> {
                    existing.setSettingValue(settings.getSettingValue());
                    existing.setDescription(settings.getDescription());
                    return repository.save(existing);
                })
                .orElseGet(() -> {
                    settings.setKeyName(keyName);
                    return repository.save(settings);
                });
    }

    @Override
    public void deleteSetting(String keyName) {
        repository.findByKeyName(keyName).ifPresent(repository::delete);
    }
}
