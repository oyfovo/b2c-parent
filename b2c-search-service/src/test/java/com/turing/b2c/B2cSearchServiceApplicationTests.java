package com.turing.b2c;


import com.turing.b2c.search.service.ItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = B2cSearchServiceApplication.class) //加载启动项
public class B2cSearchServiceApplicationTests {

    @Autowired(required = false)
    private ItemService itemService;

    @Test
    public void contextLoads() {
        itemService.index();
    }

}
