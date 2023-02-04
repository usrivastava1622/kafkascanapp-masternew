package net.sinec.springboot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.annotation.TopicPartition;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

public class KafkaProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProcessor.class);
    private static final String KEY="DEVICE";


    //@KafkaListener(topicPartitions = {@TopicPartition(topic = "${kafka.topic.orders}", partitions = "0")})
    private final KafkaConsumer<String, String> myConsumer;
    private ExecutorService executor;

    private ExecutorService executor2;

    private ExecutorService executor3;

    @Autowired
    DeviceRepository deviceRepository;
    private static final Properties KAFKA_PROPERTIES = new Properties();
    static {
        KAFKA_PROPERTIES.put("bootstrap.servers", "localhost:9092");
        KAFKA_PROPERTIES.put("group.id", "myGroup");
        KAFKA_PROPERTIES.put("enable.auto.commit", "true");
        KAFKA_PROPERTIES.put("auto.commit.interval.ms", "1000");
        KAFKA_PROPERTIES.put("session.timeout.ms", "30000");
        KAFKA_PROPERTIES.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KAFKA_PROPERTIES.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    }


    TopicPartition partitionToReadFrom=new TopicPartition("sinec",0);


    public KafkaProcessor() {
        this.myConsumer = new KafkaConsumer<>(KAFKA_PROPERTIES);
        this.myConsumer.subscribe(Arrays.asList("subscribeResponse1"));
    }



    //@KafkaListener(topicPartitions = {@TopicPartition(topic = "sinec", partitions = "0")})
    public void init(int numberOfThreads) throws ExecutionException, InterruptedException, JsonProcessingException {
        //Create a threadpool
        executor = Executors.newFixedThreadPool(numberOfThreads);
        executor2=Executors.newSingleThreadExecutor();
        executor3=Executors.newSingleThreadExecutor();
        KafkaRecordHandler kafkaRecordHandler = new KafkaRecordHandler();
        saveClass saveclass=new saveClass();
       // Queue<ConsumerRecord<String, String>> queue = new ArrayBlockingQueue<>(100);
        while (true) {
            CompletableFuture<ConsumerRecords<String, String>> records1 =
                    CompletableFuture.supplyAsync(() -> myConsumer.poll(10000), executor3);
            for (ConsumerRecord<String, String> record : records1.get()) {
                LOGGER.info("Key: " + record.key() + ", Value: " + record.value());
                LOGGER.info("Partition: " + record.partition() + ", Offset:" + record.offset());
                DeviceList deviceList = helper(record.value());
                CompletableFuture.runAsync(()-> saveclass.saveDeviceList(deviceList),executor2);
            //    deviceRepository.save(deviceList);
//             CompletableFuture.runAsync(new KafkaRecordHandler(record),executor)
//                     .thenRunAsync(()->
//                     {
//                         try {
//                             kafkaRecordHandler.addToDataBase();
//                         } catch (JsonProcessingException e) {
//                             throw new RuntimeException(e);
//                         }
//                     },executor2);
//            }
            }
        }
    }

    //public void consumer()


    private static DeviceList helper(String deviceList) throws JsonProcessingException {

        return new ObjectMapper().readValue(deviceList, DeviceList.class);
        // deviceList;

    }

    public void addtodatabase()
    {

    }



    public void shutdown() {
        if (myConsumer != null) {
            myConsumer.close();
        }
        if (executor != null) {
            executor.shutdown();
        }
        try {
            if (executor != null && !executor.awaitTermination(60, TimeUnit.MILLISECONDS)) {
                executor.shutdownNow();
            }
        }catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }}

