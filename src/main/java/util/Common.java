package util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;

import java.io.File;

public class Common {
    static {
        LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
        File file = new File("config/log4j2.xml");
        context.setConfigLocation(file.toURI());
    }

    public static final LoggerManager debug = new LoggerManager("Common");
}
