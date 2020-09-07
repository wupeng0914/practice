package com.marvel.kafka;

import com.marvel.kafka.util.PropertiesUtil;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 * @Description CustomProducer
 * @Author wupeng
 * @Motto Stay Hungry, Stay Foolish !
 * @Date 2020/9/5 5:38 下午
 **/
public class CustomProducer {

    public static void main(String[] args) {
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(PropertiesUtil.getProps());
//        send(kafkaProducer);
        sendCallback(kafkaProducer);
        kafkaProducer.close();
    }

    // 不带回调函数的生产者发送消息的API
    public static void send(KafkaProducer producer){
        for (int i = 0; i < 10; i++) {
            producer.send(new ProducerRecord<String, String>("topic_demo", Integer.toString(i), Integer.toString(i)));
        }
    }

    // 带回调函数的生产者发送消息的API
    public static void sendCallback(KafkaProducer producer){
        for (int i = 0; i < 10; i++) {
            producer.send(new ProducerRecord<String, String>("topic_demo", Integer.toString(i), Integer.toString(i)),new Callback(){
                @Override
                public void onCompletion(RecordMetadata matadata, Exception e) {
                    if (e == null){
                        System.out.println("success -> " + matadata.offset());
                    } else {
                        e.printStackTrace();
                    }
                }
            });
        }
    }


}
