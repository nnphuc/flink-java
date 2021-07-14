package org.myorg.quickstart;

import org.apache.flink.api.common.eventtime.*;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.types.Row;
import org.apache.flink.util.CloseableIterator;
import org.apache.flink.util.Collector;

import java.time.Duration;
import java.util.Properties;

import static org.apache.flink.table.api.Expressions.$;

public class TestKafka {
    public static void main(String[] args) throws Exception {
        // set up the streaming execution environment
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        final StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);


//        Properties properties = new Properties();
//        properties.setProperty("bootstrap.servers", "localhost:9092");
//        properties.setProperty("group.id", "test");
//        DataStream<String> stream = env
//                .addSource(new FlinkKafkaConsumer<>("test", new SimpleStringSchema(), properties));
//
//        DataStream<String[]> splitter = stream.flatMap(new FlatMapFunction<String, String[]>() {
//
//            @Override
//            public void flatMap(String value, Collector<String[]> out) throws Exception {
//                out.collect(value.split("\\|"));
//            }
//        });

        tEnv.executeSql("CREATE TABLE log (\n" +
                "  `message` Array<string>,\n" +
                "  `rowtime` TIMESTAMP(3) METADATA FROM 'timestamp',\n" +
                "  WATERMARK FOR `rowtime` AS `rowtime` - INTERVAL '1' SECOND\n" +
                ") WITH (\n" +
                "  'connector' = 'kafka',\n" +
                "  'topic' = 'test',\n" +
                "  'csv.array-element-delimiter' = '|',\n" +
                "  'properties.bootstrap.servers' = 'localhost:9092',\n" +
                "  'format' = 'csv'\n" +
                ")");


        tEnv.executeSql("create view l1 as select rowtime, message[1] as tmp, message[1] as userId, message[2] as actionId, cast(message[11] as bigint) as gold,\n" +
                "proctime() as proctime\n" +
                "from log");



        Table r1 = tEnv.sqlQuery("SELECT HOP_START(rowtime, INTERVAL '1' SECOND, INTERVAL '10' SECOND) as startTime, actionId, count(distinct userId) as cntUser,\n" +
                " count(*) as action\n" +
                "FROM l1\n" +
                "GROUP BY HOP(rowtime, INTERVAL '1' SECOND, INTERVAL '10' SECOND), actionId");
        CloseableIterator<Row> iter = r1.execute().collect();
        while (iter.hasNext()) {
            Row row = iter.next();
            System.out.println(row.getField(0));
        }

    }

}
