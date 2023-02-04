package net.sinec.springboot;

//import net.sinec.springboot.Service.DeviceService;
import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import net.sinec.springboot.RedisService.DeviceRedisRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Queue;
import java.util.concurrent.*;
import java.util.function.Supplier;

@Slf4j
public class KafkaRecordHandler implements Runnable {
    private ConsumerRecord<String, String> record;

    // @Autowired
    // private DeviceService deviceService;
    private static Queue<String> queue = new ArrayBlockingQueue<>(1000);

    private static final String KEY = "DeviceList";
    ExecutorService executor = Executors.newFixedThreadPool(40);

     @Autowired
     private RedisTemplate redisTemplate;
    // @Autowired

    @Autowired
    private DeviceService deviceService;
    @Autowired
    private DeviceRedisRepository deviceRedisRepository;
    // @Autowired
    // public KafkaRecordHandler(DeviceService deviceService) {
    // this.deviceService = deviceService;
    // }
    JedisConnectionFactory redisConnectionFactory1() {
        JedisConnectionFactory jedisConFactory = new JedisConnectionFactory();
        // jedisConFactory.setHostName("localhost");
        // jedisConFactory.setPort(6379);
        return jedisConFactory;
    }

    public KafkaRecordHandler(ConsumerRecord<String, String> record) {
        this.record = record;
    }

    public KafkaRecordHandler() {
    }

    public void run() { // this is where further processing happens
        // System.out.println("Inside run method value = "+record.value());
        System.out.println("Thread name = "+ Thread.currentThread().getName());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // queue.add(record.value());
//        executorService.execute(() -> {
//            System.out.println("adding to the queue " + record.value());
//            queue.add(record.value());
//        });

        System.out.println("adding to the queue " + record.value()+"with thread "+Thread.currentThread().getName());
        queue.add(record.value());
    }
    //RedisTemplate redisTemplate = new RedisTemplate();


//    public void addToDataBase() throws JsonProcessingException {
//
//        while (!queue.isEmpty()) {
//            System.out.println("Inside db");
//            DeviceList deviceList = helper(queue.remove());
//     //       String ipAddress = deviceList.ipAddress;
//             //redisTemplate.setEnableTransactionSupport(true);
//
//            executor.submit(() -> {
//                try {
//                    System.out.println("Inside Executor");
////                    redisTemplate.setConnectionFactory(redisConnectionFactory1());
////                    redisTemplate.setKeySerializer(new StringRedisSerializer());
////                    redisTemplate.setHashKeySerializer(new StringRedisSerializer());
////                    redisTemplate.setHashKeySerializer(new JdkSerializationRedisSerializer());
////                    redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
////                    redisTemplate.setEnableTransactionSupport(true);
////                    redisTemplate.afterPropertiesSet();
//                    HashOperations hashOperations = redisTemplate.opsForHash();
//                    hashOperations.put(KEY, ipAddress, deviceList);
//                } catch (Exception e) {
//                    //Logger.info("Error while storing data in Redis", e);
//                    System.out.println("Error while storing data in Redis "+e);
//                }
//            });
////            System.out.println("Data "+redisTemplate.opsForHash().values(KEY));
//            System.out.println("At the end of template");
//        }
//    }

    public void removeFromKafKaQueue() {
        // removing from kafka queue
    }

    private static DeviceList helper(String deviceList) throws JsonProcessingException {

        return new ObjectMapper().readValue(deviceList, DeviceList.class);
        // deviceList;

    }






}