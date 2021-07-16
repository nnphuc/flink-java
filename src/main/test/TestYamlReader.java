import config.ListSqlExecuteConfig;
import util.YAMLReader;
import org.junit.Test;
import util.Common;

public class TestYamlReader {


    @Test
    public void testRead() throws Exception {


        Common.logger.info(YAMLReader.convertYamlToJson("config/task/sqlExecute.yaml"));
        ListSqlExecuteConfig list = new ListSqlExecuteConfig();
        Common.logger.warn(list.listSqlExecute.get(0).sql);
        Common.logger.error(list.listSqlExecute.get(0).sql);
    }
}
