package com.lwx.producer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

public class Producer1 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "aliwork01:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);
        Future<RecordMetadata> result = kafkaProducer.send(new ProducerRecord<>("mytopic14334", "mykey1", "myvalue1"));
        RecordMetadata recordMetadata = result.get();
        System.out.println(recordMetadata);
        System.out.println(recordMetadata.offset());
        System.out.println("Partition:" + recordMetadata.partition());
        System.out.println("timestamp:" + recordMetadata.timestamp());

    }

}
