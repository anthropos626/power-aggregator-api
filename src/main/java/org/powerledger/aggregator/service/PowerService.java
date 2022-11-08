package org.powerledger.aggregator.service;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.powerledger.aggregator.dto.BatteryDto;
import org.powerledger.aggregator.dto.BatteryPostCodeResponse;
import org.powerledger.aggregator.model.Battery;
import org.powerledger.aggregator.repository.PowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PowerService {

    @Autowired
    PowerRepository powerRepository;

    @Autowired
    ModelMapper modelMapper;

    public void processBatteries(List<BatteryDto> batteryList) {
        for (BatteryDto batteryDto : batteryList) {
            Battery battery = this.modelMapper.map(batteryDto, Battery.class);
            powerRepository.save(battery);
        }
    }

    public String getBatteriesforPostcode(int postcodeFrom, int postcodeTo) {

        List<Battery> batteryList = (List<Battery>) powerRepository.findBatteriesForPostcode(postcodeFrom, postcodeTo);

        List<BatteryDto> batteryDtoList  = batteryList.stream().map(m -> this.modelMapper.map(m, BatteryDto.class)).collect(Collectors.toList());

        BatteryPostCodeResponse batteryPostCodeResponse = null;

        if (!CollectionUtils.isEmpty(batteryDtoList)) {

            double totalWattcapacity = batteryDtoList.stream()
                    .map(m -> m.getWattCapacity())
                    .reduce(0.0 , Double::sum);

            batteryPostCodeResponse = BatteryPostCodeResponse.builder()
                    .batteryNames(batteryDtoList.stream().map(m -> m.getName()).sorted().collect(Collectors.toList()))
                    .totalWattCapacity(totalWattcapacity)
                    .averageWattCapacity(totalWattcapacity / batteryDtoList.size())
                    .build();
        }

        if (batteryPostCodeResponse != null) {
            Gson gson = new Gson();
            return gson.toJson(batteryPostCodeResponse);

        }

        return null;
    }


}
