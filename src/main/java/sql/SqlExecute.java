package sql;

import config.SqlExecuteConfig;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class SqlExecute {
    private TableResult tableResult;
    private SqlExecuteConfig sqlConfig;

    private SqlExecute(SqlExecuteConfig sqlConfig) {
        this.sqlConfig = sqlConfig;
        this.tableResult = null;
    }

    public static SqlExecute submit(StreamTableEnvironment tEnv, SqlExecuteConfig sqlConfig) {
        SqlExecute executeSql = new SqlExecute(sqlConfig);
        executeSql.tableResult = tEnv.executeSql(sqlConfig.sql);
        return executeSql;
    }

    public TableResult getTableResult() {
        return this.tableResult;
    }

    public SqlExecuteConfig getSqlConfig() {
        return this.sqlConfig;
    }
}
