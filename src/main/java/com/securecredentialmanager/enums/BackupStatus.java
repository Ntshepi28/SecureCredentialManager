package com.securecredentialmanager.enums;

public enum BackupStatus {

    SUCCESS,
    FAILED;

    @Override
    public String toString(){
        return name();
    }
}
