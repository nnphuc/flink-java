package config;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import util.Common;

import java.lang.reflect.Type;
import java.util.List;

public class ListSqlExecuteConfig {
    public List<SqlExecuteConfig> listSqlTables;

    public ListSqlExecuteConfig() {
        reloadConfig();
    }

    public void reloadConfig() {
        try {
            String json = YAMLReader.convertYamlToJson("config/task/sqlExecute.yaml");
            Type type = new TypeToken<List<SqlExecuteConfig>>() {
            }.getType();
            listSqlTables = new Gson().fromJson(json, type);
        } catch (Exception e) {
            Common.debug.error(e);
        }
    }
}
