package org.powerledger.aggregator.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import lombok.extern.slf4j.Slf4j;
import org.powerledger.aggregator.dto.BatteryDto;
import org.powerledger.aggregator.dto.BatteryPostCodeResponse;
import org.powerledger.aggregator.model.Battery;
import org.powerledger.aggregator.service.PowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/power")
@Slf4j
public class PowerController {

    private final PowerService powerService;

    @Autowired
    PowerController(PowerService powerService) {
        this.powerService = powerService;
    }


    @RequestMapping(value = "/batteries", method = RequestMethod.POST, produces = APPLICATION_JSON_VALUE)
    public String postBattery(@RequestBody List<BatteryDto> batteryList) {
        powerService.processBatteries(batteryList);
        return "REACHED API";
    }


//    @RequestMapping(value = "/batteries", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
//    public List<BatteryDto> getAllBatteries() {
//       return powerService.getAllBatteries();
//    }

    @RequestMapping(value = "/batteries", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public String getBatteriesforPostCodeRange(@RequestParam int postcodeFrom, @RequestParam int postcodeTo) {
        return powerService.getBatteriesforPostcode(postcodeFrom, postcodeTo);
    }

}
