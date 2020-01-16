package com.vtrishin.webservice;

import com.vtrishin.webservice.models.User;
import java.util.logging.Level;

public class Logger {

    protected java.util.logging.Logger logger = java.util.logging.Logger.getLogger(User.class.getName());
    protected final Level INFO    = Level.INFO;
    protected final Level OFF     = Level.OFF;
    protected final Level SEVERE  = Level.SEVERE;
    protected final Level WARNING = Level.WARNING;
    protected final Level CONFIG  = Level.CONFIG;
    protected final Level FINE    = Level.FINE;
    protected final Level FINER   = Level.FINER;
    protected final Level FINEST  = Level.FINEST;
    protected final Level ALL     = Level.ALL;

    public Logger() {
//        logger.addHandler();
    }
}
