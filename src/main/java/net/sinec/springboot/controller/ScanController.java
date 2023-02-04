package net.sinec.springboot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sinec.springboot.*;
import net.sinec.springboot.Dto.IPRangeDto;
import net.sinec.springboot.Producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/scan")
public class ScanController {

    @Autowired
    IPRangeRepository ipRangeRepository;

    @Autowired
    DeviceRepository deviceRepository;
    @PostMapping("/saveScanRange")
    public Mono<String> saveIPRange(@RequestBody IPRangeDto ipRangeDto)  {
        IPRanges ipRanges=new IPRanges();
        ipRanges.setStartScanRange(ipRangeDto.getStartScanRange());
        ipRanges.setEndScanRange(ipRangeDto.getEndScanRange());
       ipRangeRepository.save(ipRanges);
                KafkaProducer kafkaProducer=new KafkaProducer();
        ExecutorService executorService= Executors.newFixedThreadPool(1);
        CompletableFuture.runAsync(()-> kafkaProducer.produceRange(ipRangeDto),executorService);

       return Mono.just("Success");
    }


    @GetMapping("/getScanRange")
    public Mono<IPRangeDto> getScanRanges(){
        List<IPRanges> ipRanges = ipRangeRepository.findAll();
        System.out.println("ip"+ipRanges);
       IPRangeDto ipRangeDto=new IPRangeDto();
       ipRangeDto.setStartScanRange(ipRanges.get(0).getStartScanRange());
       ipRangeDto.setEndScanRange(ipRanges.get(0).getEndScanRange());
        return Mono.just(ipRangeDto);
    }


    @GetMapping("/startScan")
    public Mono<String> startScan(){
        KafkaProcessor processor = new KafkaProcessor();
        ExecutorService executorService=Executors.newFixedThreadPool(1);
        try {
            CompletableFuture.runAsync(()-> {
                try {
                    processor.init(20);
                } catch (ExecutionException | InterruptedException | JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            },executorService);
          //  processor.init(20);
        } catch (Exception exp) {
            processor.shutdown();
        }
    return Mono.just("Started Scanning Devices");
    }


//    @GetMapping("/getDeviceList")
//    public Flux<DeviceList> get(){
//        return   deviceRepository.findAll();
//    }
}
