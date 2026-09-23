package com.securecredentialmanager.config;

public final class DatabaseConfig {

    private static final String DEFAULT_HOST = "localhost";
    private static final String DEFAULT_PORT = "3306";
    private static final String DEFAULT_DATABASE = "secure_credential_manager";
    private static final String DEFAULT_USERNAME = "scm_user";

    private DatabaseConfig() {
    }

    public static String getHost() {
        return getEnvironmentValue("SCM_DB_HOST", DEFAULT_HOST);
    }

    public static String getPort() {
        return getEnvironmentValue("SCM_DB_PORT", DEFAULT_PORT);
    }

    public static String getDatabase() {
        return getEnvironmentValue("SCM_DB_NAME", DEFAULT_DATABASE);
    }

    public static String getUsername() {
        return getEnvironmentValue("SCM_DB_USERNAME", DEFAULT_USERNAME);
    }

    public static String getPassword() {
        String password = System.getenv("SCM_DB_PASSWORD");

        if (password == null || password.isBlank()) {
            throw new IllegalStateException(
                    "Database password is not configured. " +
                            "Set the SCM_DB_PASSWORD environment variable."
            );
        }

        return password;
    }

    public static String getJdbcUrl() {
        return "jdbc:mysql://"
                + getHost()
                + ":"
                + getPort()
                + "/"
                + getDatabase()
                + "?useSSL=false"
                + "&serverTimezone=UTC"
                + "&allowPublicKeyRetrieval=true";
    }

    private static String getEnvironmentValue(
            String variableName,
            String defaultValue) {

        String value = System.getenv(variableName);

        if (value == null || value.isBlank()) {
            return defaultValue;
        }

        return value;
    }
}