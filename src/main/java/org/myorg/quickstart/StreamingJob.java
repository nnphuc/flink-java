/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.myorg.quickstart;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.SlidingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

import java.util.Properties;

/**
 * Skeleton for a Flink Streaming Job.
 *
 * <p>For a tutorial how to write a Flink streaming application, check the
 * tutorials and examples on the <a href="https://flink.apache.org/docs/stable/">Flink Website</a>.
 *
 * <p>To package your application into a JAR file for execution, run
 * 'mvn clean package' on the command line.
 *
 * <p>If you change the name of the main class (with the public static void main(String[] args))
 * method, change the respective entry in the POM.xml file (simply search for 'mainClass').
 */
public class StreamingJob {

    public static void main(String[] args) throws Exception {
        // set up the streaming execution environment
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        final StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);


        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
        properties.setProperty("group.id", "test");
        DataStream<String> stream = env
                .addSource(new FlinkKafkaConsumer<>("test", new SimpleStringSchema(), properties));


        stream.
                flatMap(new BatchJob.Tokenizer())
                .keyBy(f -> f.f0)
                .windowAll(SlidingProcessingTimeWindows.of(Time.seconds(3), Time.seconds(3)))
//                .trigger(new Trigger<Tuple2<String, Integer>, TimeWindow>() {
//                    @Override
//                    public TriggerResult onElement(Tuple2<String, Integer> element, long timestamp, TimeWindow window, TriggerContext ctx) throws Exception {
//                        System.out.println("onElement");
//                        return TriggerResult.CONTINUE;
//                    }
//
//                    @Override
//                    public TriggerResult onProcessingTime(long time, TimeWindow window, TriggerContext ctx) throws Exception {
//                        System.out.println("onProcessingTime");
//                        return TriggerResult.CONTINUE;
//                    }
//
//                    @Override
//                    public TriggerResult onEventTime(long time, TimeWindow window, TriggerContext ctx) throws Exception {
//                        System.out.println("onEventTime");
//                        return TriggerResult.CONTINUE;
//                    }
//
//                    @Override
//                    public void clear(TimeWindow window, TriggerContext ctx) throws Exception {
//                        System.out.println("clear");
//                    }
//                })
//                .evictor(new Evictor<Tuple2<String, Integer>, TimeWindow>() {
//                    @Override
//                    public void evictBefore(Iterable<TimestampedValue<Tuple2<String, Integer>>> elements, int size, TimeWindow window, EvictorContext evictorContext) {
//                        System.out.println("evictBefore");
//                    }
//
//                    @Override
//                    public void evictAfter(Iterable<TimestampedValue<Tuple2<String, Integer>>> elements, int size, TimeWindow window, EvictorContext evictorContext) {
//                        System.out.println("evictAfter");
//                    }
//                })
                .reduce((a, b) -> new Tuple2<>(a.f0, a.f1 + b.f1))
                .map(x -> {
                    System.out.println(x.toString());
                    return x.f1;
                }).print();

        //stream.print();
        // execute program
        env.execute("Flink Streaming Java API Skeleton");
    }
}
