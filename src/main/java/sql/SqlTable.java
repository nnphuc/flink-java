package sql;

import config.SqlTableConfig;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

public class SqlTable {
    private Table table;
    private SqlTableConfig sqlConfig;

    private SqlTable(SqlTableConfig sqlConfig) {
        this.sqlConfig = sqlConfig;
        this.table = null;
    }

    public static SqlTable submit(StreamTableEnvironment tEnv, SqlTableConfig sqlConfig) {
        SqlTable sqlTable = new SqlTable(sqlConfig);
        sqlTable.table = tEnv.sqlQuery(sqlConfig.sql);
        return sqlTable;
    }

    public Table getTable() {
        return this.table;
    }

    public SqlTableConfig getSqlConfig() {
        return this.sqlConfig;
    }
}
