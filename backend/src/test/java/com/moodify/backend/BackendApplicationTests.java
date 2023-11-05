package com.moodify.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;


@SpringBootTest(classes = BackendApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ComponentScan(basePackages = "com.moodify.backend")
class BackendApplicationTests {


}
