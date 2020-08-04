package com.github.prasun.kafkamain;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

public class consumerDemo {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(consumerDemo.class);
        String bootstrapServer = "127.0.0.1:9092";
        String groupId = "my_firstconsumer_group";

        //Create Consumer Properties
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServer);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG,groupId);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");//or latest

        //Create Consumer
        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(properties);
        consumer.subscribe(Collections.singleton("myfirst_topic"));
        // (for multiple topics)consumer.subscribe(Arrays.asList("myfirst_topic"));

        //poll for new data
        while(true){
            //deprecated: consumer.poll(100) new in kafka 2.0.0 needs java 1.8+
            ConsumerRecords<String,String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String,String> record: records){
                logger.info("Key:"+record.key()+" Value:"+record.value());
                logger.info("Partition:"+record.partition()+", Offset:"+record.offset());
            }
        }


    }
}