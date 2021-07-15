import com.google.gson.Gson;
import config.ListSqlTableConfig;
import config.YAMLReader;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

public class TestYamlReader {

    @Test
    public void testRead() throws Exception {

        System.out.println(YAMLReader.convertYamlToJson("config/task/init.yaml"));
        ListSqlTableConfig list = new ListSqlTableConfig();
        System.out.println(list.listSqlTables.get(0).sql);
    }
}
