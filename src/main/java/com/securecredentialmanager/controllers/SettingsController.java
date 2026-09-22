package com.securecredentialmanager.controllers;


import com.securecredentialmanager.models.UserSettings;
import com.securecredentialmanager.services.SettingsService;

public class SettingsController {

    private final SettingsService settingsService;

    public SettingsController(){
        this.settingsService = new SettingsService();
    }

    public UserSettings getSettings(int userId) {

        if (userId <= 0) {
            return null;
        }

        return settingsService.getSettings(userId);
    }

    public boolean saveSettings(UserSettings userSettings){
        if (userSettings == null){
            return false;
        }

        return settingsService.saveSettings(userSettings);
    }

    public boolean updatingSettings(UserSettings userSettings){
        if (userSettings == null){
            return false;
        }

        return settingsService.saveSettings(userSettings);
    }

    public boolean deleteSettings(int userId){
        if (userId <= 0){
            return false;
        }

        return settingsService.deleteSettings(userId);
    }

}
