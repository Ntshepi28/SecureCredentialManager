package com.securecredentialmanager.enums;

public enum AuditAction {

    LOGIN_SUCCESS,
    LOGIN_FAILED,
    LOGOUT,

    ACCOUNT_LOCKED,
    ACCOUNT_DISABLED,

    CREDENTIAL_CREATED,
    CREDENTIAL_UPDATED,
    CREDENTIAL_DELETED,
    CREDENTIAL_VIEWED,

    CATEGORY_CREATED,
    CATEGORY_DELETED,

    PASSWORD_CHANGED,
    PASSWORD_GENERATED,

    SETTINGS_UPDATED,

    BACKUP_CREATED,
    BACKUP_FAILED,

    SESSION_CREATED,
    SESSION_EXPIRED,
    SESSION_DEACTIVATED;

    @Override
    public String toString(){
        return name();
    }
}
