import config.YAMLReader;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

public class TestYamlReader {

    @Test
    public void testRead() throws Exception {

        System.out.println(YAMLReader.convertYamlToJson("config/test.yaml"));
    }
}
