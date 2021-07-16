package org.myorg.quickstart;

import config.ListSqlExecuteConfig;
import config.ListSqlTableConfig;
import config.SqlExecuteConfig;
import config.SqlTableConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.types.Row;
import org.apache.flink.util.CloseableIterator;
import sql.SqlExecute;
import sql.SqlTable;
import util.Common;
import util.Printer;

import java.io.PrintWriter;

public class TestKafka2 {
    public static void main(String[] args) throws Exception {
        // set up the streaming execution environment
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        final StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);

        ListSqlExecuteConfig listSqlExecuteConfig = new ListSqlExecuteConfig();
        for (SqlExecuteConfig config : listSqlExecuteConfig.listSqlExecute) {
            try {
                Common.logger.debug("Submit SQL:", config.sql);
                SqlExecute.submit(tEnv, config);
            } catch (Exception e) {
                Common.logger.error(e);
            }
        }

        ListSqlTableConfig listSqlTableConfig = new ListSqlTableConfig();
        for (SqlTableConfig config : listSqlTableConfig.listSqlTable) {
            try {
                Common.logger.debug("Submit SQL:", config.sql);
                SqlTable sqlTable = SqlTable.submit(tEnv, config);
                CloseableIterator<Row> iter = sqlTable.getTable()
                    .execute()
                    .collect();
                new Thread(() -> {
                    while (iter.hasNext()) {
                        Row row = iter.next();
                        Printer.printAsTableauForm(sqlTable.getTable().getSchema(), row, new PrintWriter(System.out));
                    }
                }).start();
                Common.logger.warn("Done Submit SQL:", config.sql);
            } catch (Exception e) {
                Common.logger.error(e);
            }
        }
    }
}
