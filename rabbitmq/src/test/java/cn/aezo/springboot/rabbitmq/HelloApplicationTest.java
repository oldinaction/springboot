package cn.aezo.springboot.rabbitmq;

import cn.aezo.springboot.rabbitmq.provider.Provider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by smalle on 2017/7/23.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloApplicationTest {

    @Autowired
    private Provider provider;

    @Test
    public void provide() {
        this.provider.send();
    }

}
