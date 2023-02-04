package net.sinec.springboot;

//import org.springframework.boot.CommandLineRunner;
import net.sinec.springboot.Producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
// @ComponentScan(basePackages = "")
public class SpringBootScanApplication {

    public static void main(String[] args) {


        SpringApplication.run(SpringBootScanApplication.class);
//        KafkaProducer kafkaProducer=new KafkaProducer();
//        ExecutorService executorService= Executors.newFixedThreadPool(1);
//        CompletableFuture.runAsync(()-> kafkaProducer.produceRange(),executorService);
//        KafkaProcessor processor = new KafkaProcessor();
//            try {
//                processor.init(20);
//            } catch (Exception exp) {
//                processor.shutdown();
//            }
    }
    //Scan :-1)to produce two range 2)To consume json device list

    //different consumer group from same partition-me
    // Actor Model--Vamsi
    //host this kafka in kubernetes cluster (strimzi framework for kub operator)-->>host around IEM--me
    //document making for issue facing and resolved -(Vamsi,me)
    //Autowired --left as now
}