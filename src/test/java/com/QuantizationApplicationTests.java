package com;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class QuantizationApplicationTests {

    @Autowired
    @Qualifier("sslRestTemplate")
    private RestTemplate sslRestTemplate;

    @Test
    public void contextLoads() {
        String str = sslRestTemplate.getForObject("https://www.google.com", String.class);
        log.info(str);
    }

}
