package com.securecredentialmanager.config;

public final class AppConfig {

    private static final String APPLICATION_NAME =
            "Secure Credential Manager";

    private static final String APPLICATION_VERSION =
            "1.0.0";

    private static final int DEFAULT_WINDOW_WIDTH = 1100;
    private static final int DEFAULT_WINDOW_HEIGHT = 700;

    private AppConfig() {
    }

    public static String getApplicationName() {
        return APPLICATION_NAME;
    }

    public static String getApplicationVersion() {
        return APPLICATION_VERSION;
    }

    public static int getDefaultWindowWidth() {
        return DEFAULT_WINDOW_WIDTH;
    }

    public static int getDefaultWindowHeight() {
        return DEFAULT_WINDOW_HEIGHT;
    }

    public static String getApplicationTitle() {
        return APPLICATION_NAME + " v" + APPLICATION_VERSION;
    }
}