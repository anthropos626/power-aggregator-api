package org.powerledger.aggregator;


import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.powerledger.aggregator.dto.BatteryDto;
import org.powerledger.aggregator.dto.BatteryPostCodeResponse;
import org.powerledger.aggregator.model.Battery;
import org.powerledger.aggregator.repository.PowerRepository;
import org.powerledger.aggregator.service.PowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class PowerServiceTest {
    @Autowired
    private PowerRepository powerRepository;
    @Autowired
    private PowerService powerService;
    @Autowired
    ModelMapper modelMapper;

    @Before
    public void before() {
        powerRepository.deleteAll();
        powerRepository.flush();
    }


    @Test
    public void testProcessBatteries() {

        BatteryDto testBatteryDto = new BatteryDto();
        testBatteryDto.setName("CherryBattery");
        testBatteryDto.setPostCode(2999);
        testBatteryDto.setWattCapacity(180);

        List<BatteryDto> batteryDtoList = new ArrayList<>();

        batteryDtoList.add(testBatteryDto);

        powerService.processBatteries(batteryDtoList);

        List<Battery> batteryList = (List<Battery>) powerRepository.findAll();

        Assert.assertEquals(testBatteryDto.getName(), batteryList.get(0).getName());
        Assert.assertEquals(testBatteryDto.getPostCode(), batteryList.get(0).getPostCode());
        Assert.assertEquals(testBatteryDto.getWattCapacity(), batteryList.get(0).getWattCapacity(), 0.001);
    }

    @Test
    public void testService() {
        BatteryDto testBattery1 = new BatteryDto();
        testBattery1.setName("SydneyBattery");
        testBattery1.setPostCode(3001);
        testBattery1.setWattCapacity(185);

        BatteryDto testBattery2 = new BatteryDto();
        testBattery1.setName("BarryBattery");
        testBattery1.setPostCode(3002);
        testBattery1.setWattCapacity(189);


        BatteryDto testBattery3 = new BatteryDto();
        testBattery1.setName("PerthBattery");
        testBattery1.setPostCode(3003);
        testBattery1.setWattCapacity(199);

        List<BatteryDto> batteryDtoList = new ArrayList<>();
        batteryDtoList.add(testBattery1);
        batteryDtoList.add(testBattery2);
        batteryDtoList.add(testBattery3);

        powerService.processBatteries(batteryDtoList);

        List<Battery> batteryList = (List<Battery>) powerRepository.findAll();
        Assert.assertNotNull(batteryList);
        Assert.assertEquals(batteryList.size(), 3);
    }


}
