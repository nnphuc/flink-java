package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerManager {
    public static final String NEW_LINE = "\n";
    private Logger logger;

    public LoggerManager(String category) {
        super();

        logger = LoggerFactory.getLogger(category);
    }

    /**
     * Log info
     */
    public void info(Object... args) {
        StringBuilder builder = new StringBuilder();
        if (args != null) {
            for (Object obj : args) {
                builder.append(obj.toString());
            }
        }

        logger.info(builder.toString());
    }

    private String toString(Object... args) {
        if (args == null || args.length == 0)
            return "";

        StringBuilder builder = new StringBuilder();

        for (Object obj : args) {
            if (obj == null)
                continue;

            if (obj instanceof Throwable) {
                Throwable t = (Throwable) obj;
                builder.append(t.getMessage()).append(NEW_LINE);

                StackTraceElement[] elements = t.getStackTrace();
                builder.append(stackTraceToString(elements));
            }
            builder.append(obj.toString());
        }

        return builder.toString();
    }

    /**
     * Log error
     */
    public void error(Object... args) {
        logger.error(toString(args));
    }

    /**
     * Log warn
     */
    public void warn(Object... args) {
        logger.warn(toString(args));
    }

    public String stackTraceToString(StackTraceElement[] elements) {
        StringBuilder builder = new StringBuilder();

        for (StackTraceElement element : elements) {
            builder.append(element).append(NEW_LINE);
        }

        return builder.toString();
    }
}
