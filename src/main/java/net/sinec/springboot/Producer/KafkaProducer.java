package net.sinec.springboot.Producer;

import net.sinec.springboot.Dto.IPRangeDto;
import net.sinec.springboot.IPRangeRepository;
import net.sinec.springboot.IPRanges;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.apache.kafka.clients.producer.*;

import java.util.List;
import java.util.Properties;

public class KafkaProducer  {

    @Autowired
    IPRangeRepository ipRangeRepository;


    public void produceRange(IPRangeDto ipRangeDto)
    {
        Properties properties = new Properties();
       // List<IPRanges> ipRange = ipRangeRepository.findAll();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        org.apache.kafka.clients.producer.KafkaProducer<String,String> producer=new org.apache.kafka.clients.producer.KafkaProducer<>(properties);
        ProducerRecord<String, String> producerRecord =
                new ProducerRecord<>("cmd", ipRangeDto.getStartScanRange());
        ProducerRecord<String, String> producerRecord2 =
                new ProducerRecord<>("cmd", ipRangeDto.getEndScanRange());
        producer.send(producerRecord);
        producer.send(producerRecord2);
    }

}
