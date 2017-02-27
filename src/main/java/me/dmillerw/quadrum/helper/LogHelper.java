package me.dmillerw.quadrum.helper;

import me.dmillerw.quadrum.lib.ModInfo;
import org.apache.logging.log4j.*;

public class LogHelper {

    public static final boolean DEBUG = true;

    public static final Marker MARKER = MarkerManager.getMarker(ModInfo.MOD_NAME);
    private static final Logger LOGGER = LogManager.getLogger(ModInfo.MOD_NAME);

    public static void log(Level logLevel, String format, Object... data) {
        format = "[Quadrum]: " + format;

        if (DEBUG)
            LOGGER.log(Level.INFO, MARKER, format, data);
        else
            LOGGER.log(logLevel, MARKER, format, data);
    }

    public static void trace(String format, Object... data) {
        log(Level.TRACE, format, data);
    }

    public static void debug(String format, Object... data) {
        log(Level.DEBUG, format, data);
    }

    public static void info(String format, Object... data) {
        log(Level.INFO, format, data);
    }

    public static void warn(String format, Object... data) {
        log(Level.WARN, format, data);
    }

    public static void error(String format, Object... data) {
        log(Level.ERROR, format, data);
    }

    public static void fatal(String format, Object... data) {
        log(Level.FATAL, format, data);
    }
}