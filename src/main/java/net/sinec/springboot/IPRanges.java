package net.sinec.springboot;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlIDREF;

@Entity
@Table(name="ip_ranges")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class IPRanges {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(name="start_scan_range")
    private String startScanRange;


    @Column(name="end_scan_range")
    private String endScanRange;
}
