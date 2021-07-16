import com.google.gson.Gson;
import config.ListSqlTableConfig;
import config.YAMLReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.junit.Test;
import util.Common;

import java.io.File;

public class TestYamlReader {


    @Test
    public void testRead() throws Exception {


        Common.debug.info(YAMLReader.convertYamlToJson("config/task/init.yaml"));
        ListSqlTableConfig list = new ListSqlTableConfig();
        Common.debug.warn(list.listSqlTables.get(0).sql);
        Common.debug.error(list.listSqlTables.get(0).sql);
    }
}
