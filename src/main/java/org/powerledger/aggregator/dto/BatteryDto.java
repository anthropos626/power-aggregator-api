package org.powerledger.aggregator.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class BatteryDto {

    private String name;

    private int postCode;

    private double wattCapacity;
}
