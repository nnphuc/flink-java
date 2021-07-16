package config;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import util.Common;
import util.YAMLReader;

import java.lang.reflect.Type;
import java.util.List;

public class ListSqlTableConfig {
    public List<SqlTableConfig> listSqlTable;

    public ListSqlTableConfig() {
        reloadConfig();
    }

    public void reloadConfig() {
        try {
            String json = YAMLReader.convertYamlToJson("config/task/sqlTable.yaml");
            Type type = new TypeToken<List<SqlTableConfig>>() {
            }.getType();
            listSqlTable = new Gson().fromJson(json, type);
        } catch (Exception e) {
            Common.logger.error(e);
        }
    }
}
