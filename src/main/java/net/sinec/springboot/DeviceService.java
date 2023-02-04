package net.sinec.springboot;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    public DeviceService() {
    }

//    public String addDataToDatabase(DeviceList deviceList) {
//        System.out.println("Inside add to db");
//        DeviceList deviceList2 = deviceRepository.save(deviceList);
//        System.out.println("Data " + deviceList2 + " added to db");
//        return "Data added Successfully";
//    }

}
