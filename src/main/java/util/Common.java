package util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;

import java.io.File;

public class Common {
    public static final String CONFIG_FOLDER = "config/";
    public static final String LOG4J_CONFIG = "config/log4j2.xml";

    public static void initConfigurationLogger() {
        LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
        File file = new File(LOG4J_CONFIG);
        context.setConfigLocation(file.toURI());
    }

    public static final LoggerManager logger = new LoggerManager("Common");
}
