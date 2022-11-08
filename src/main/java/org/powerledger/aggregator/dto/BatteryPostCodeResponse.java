package org.powerledger.aggregator.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class BatteryPostCodeResponse {
    private List<String> batteryNames;
    private double averageWattCapacity;
    private double totalWattCapacity;
}
