package com.springboot3.sample.graphql;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest()
@Slf4j
public class GraphqlApplicationTests {


    @Test
    void context_load(){
        log.info("Graphql application context load successfully.");
    }
}
