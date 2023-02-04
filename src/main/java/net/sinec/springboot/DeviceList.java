package net.sinec.springboot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@Table(name="devices_list")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceList implements Serializable {

    @Id
    @Column(name="ipaddress")
    private Long ipAddress;

    @Column(name="devicetype")
    private String deviceType;

    @Column(name="devicelocation")
    private String deviceLocation;

    @Column(name="physicaladdress")
    private String physicalAddress;
}
