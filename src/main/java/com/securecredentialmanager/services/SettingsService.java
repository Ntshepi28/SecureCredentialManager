package com.securecredentialmanager.services;

import com.securecredentialmanager.models.UserSettings;
import com.securecredentialmanager.repositories.UserSettingsRepository;

public class SettingsService {

    private final UserSettingsRepository settingsRepository;

    public SettingsService() {
        this.settingsRepository = new UserSettingsRepository();
    }

    public boolean saveSettings(UserSettings settings) {

        if (settings == null) {
            return false;
        }

        if (settings.getUserId() <= 0) {
            return false;
        }

        if (settings.getTheme() == null
                || settings.getTheme().isBlank()) {
            return false;
        }

        return settingsRepository.saveSettings(settings);
    }

    public UserSettings getSettings(int userId) {

        if (userId <= 0) {
            return null;
        }

        return settingsRepository.findByUserId(userId);
    }

    public boolean updateSettings(UserSettings settings) {

        if (settings == null
                || settings.getUserId() <= 0) {
            return false;
        }

        return settingsRepository.updateSettings(settings);
    }

    public boolean deleteSettings(int userId) {

        if (userId <= 0) {
            return false;
        }

        return settingsRepository.deleteSettings(userId);
    }
}