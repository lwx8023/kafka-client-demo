package com.lwx.producer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

public class Producer2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "aliwork01:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG,"myID1");
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);
        kafkaProducer.initTransactions();

        try {
            kafkaProducer.beginTransaction();
            Future<RecordMetadata> result = kafkaProducer.send(new ProducerRecord<>("mytopic1", "mykey1", "myvalue1"));

            Future<RecordMetadata> result2 = kafkaProducer.send(new ProducerRecord<>("mytopic2", "mykey2", "myvalue2"));
            resultPrint(result.get());
            resultPrint(result2.get());

            kafkaProducer.commitTransaction();
        } catch (Exception exception) {
            System.out.println(exception);
            kafkaProducer.abortTransaction();
        } finally {
            kafkaProducer.close();
        }

    }

    private static void resultPrint(RecordMetadata recordMetadata){
        System.out.println("offset:"+recordMetadata.offset());
        System.out.println("Partition:" + recordMetadata.partition());
        System.out.println("timestamp:" + recordMetadata.timestamp());
    }
}
