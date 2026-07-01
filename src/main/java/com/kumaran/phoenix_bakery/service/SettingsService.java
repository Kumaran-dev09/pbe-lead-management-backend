package com.kumaran.phoenix_bakery.service;

import java.util.List;
import java.util.Optional;

import com.kumaran.phoenix_bakery.model.Settings;

public interface SettingsService {
    List<Settings> getAllSettings();
    Optional<Settings> getSetting(String keyName);
    Settings saveSetting(Settings settings);
    Settings updateSetting(String keyName, Settings settings);
    void deleteSetting(String keyName);
}
