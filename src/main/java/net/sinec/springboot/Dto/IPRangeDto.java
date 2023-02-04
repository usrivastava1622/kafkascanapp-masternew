package net.sinec.springboot.Dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IPRangeDto {

    private String startScanRange;

    private String endScanRange;
}
