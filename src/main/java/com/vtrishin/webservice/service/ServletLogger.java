package com.vtrishin.webservice.service;

import com.vtrishin.webservice.models.User;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class ServletLogger {

    public java.util.logging.Logger logger = Logger.getLogger(User.class.getName());
    public final Level INFO = Level.INFO;
    public final Level OFF = Level.OFF;
    public final Level SEVERE = Level.SEVERE;
    public final Level WARNING = Level.WARNING;
    public final Level CONFIG = Level.CONFIG;
    public final Level FINE = Level.FINE;
    public final Level FINER = Level.FINER;
    public final Level FINEST = Level.FINEST;
    public final Level ALL = Level.ALL;

    public static ServletLogger getInstance() {

        if (instance == null) {
            instance = new ServletLogger();
        }
        return instance;
    }

    public void log(Level level, String logData) {

        logger.log(level, logData);
    }

    private static ServletLogger instance = null;

    private ServletLogger() {
        // TODO log to file
//        logger.addHandler();
    }

}
