package net.sinec.springboot;

import org.springframework.beans.factory.annotation.Autowired;

public class saveClass {



    @Autowired
    DeviceRepository deviceRepository;

    public String saveDeviceList(DeviceList deviceList){
        deviceRepository.save(deviceList);
        return "saved";
    }
}
