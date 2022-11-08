package org.powerledger.aggregator.integration;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powerledger.aggregator.Application;
import org.powerledger.aggregator.model.Battery;
import org.powerledger.aggregator.repository.PowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class DatabaseIntegrationTest {

    @Autowired
    private PowerRepository powerRepository;

    @Before
    public void before(){
        powerRepository.deleteAll();
        powerRepository.flush();
    }

    @Test
    public void testSaveAndRetrieve() {
        Battery testBattery = new Battery();
        testBattery.setName("HenryBattery");
        testBattery.setPostCode(3008);
        testBattery.setWattCapacity(180);

        Battery savedbatteryEntity = powerRepository.save(testBattery);
        Battery foundBatteryEntity = powerRepository.findById(savedbatteryEntity.getId()).orElse(null);

        Assert.assertNotNull(foundBatteryEntity);
        Assert.assertEquals(savedbatteryEntity.getName(), foundBatteryEntity.getName());
        Assert.assertEquals(savedbatteryEntity.getPostCode(), foundBatteryEntity.getPostCode());
        Assert.assertEquals(savedbatteryEntity.getWattCapacity(), foundBatteryEntity.getWattCapacity(), 0.001);
    }


}
