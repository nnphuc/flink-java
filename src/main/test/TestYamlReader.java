import config.ListSqlExecuteConfig;
import config.YAMLReader;
import org.junit.Test;
import util.Common;

public class TestYamlReader {


    @Test
    public void testRead() throws Exception {


        Common.debug.info(YAMLReader.convertYamlToJson("config/task/sqlExecute.yaml"));
        ListSqlExecuteConfig list = new ListSqlExecuteConfig();
        Common.debug.warn(list.listSqlTables.get(0).sql);
        Common.debug.error(list.listSqlTables.get(0).sql);
    }
}
