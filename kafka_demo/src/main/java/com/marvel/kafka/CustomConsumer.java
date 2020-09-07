package com.marvel.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

/**
 * @Description CustomConsumer
 * @Author wupeng
 * @Motto Stay Hungry, Stay Foolish !
 * @Date 2020/9/5 7:32 下午
 **/
public class CustomConsumer {

    public static void main(String[] args) {
        autoCommit();
    }

    public static void autoCommit(){
        Properties props = new Properties(); props.put("bootstrap.servers", "123.57.226.72:9092");
        //消费者组
        props.put("group.id", "test");
        //是否自动提交
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Arrays.asList("topic_demo"));
        while (true){
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String,String> record : records){
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
            }
        }
    }

    public static void notAutoCommit(){
        Properties props = new Properties(); props.put("bootstrap.servers", "123.57.226.72:9092");
        //消费者组
        props.put("group.id", "test");
        //关闭自动提交
        props.put("enable.auto.commit", "false");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        //消费者订阅主题
        consumer.subscribe(Arrays.asList("topic_demo"));
        while (true){
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String,String> record : records){
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
            }
            //同步提交，当前线程会阻塞到offset提交成功
            consumer.commitSync();
        }
    }

}
