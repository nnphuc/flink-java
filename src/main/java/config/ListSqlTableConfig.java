package config;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;
import util.Common;

import java.lang.reflect.Type;
import java.util.List;

public class ListSqlTableConfig {
    public List<SqlTableConfig> listSqlTables;

    public ListSqlTableConfig() {
        reloadConfig();
    }

    public void reloadConfig() {
        try {
            String json = YAMLReader.convertYamlToJson("config/task/init.yaml");
            Type type = new TypeToken<List<SqlTableConfig>>() {
            }.getType();
            listSqlTables = new Gson().fromJson(json, type);
        } catch (Exception e) {
            Common.debug.error(e);
        }
    }
}
