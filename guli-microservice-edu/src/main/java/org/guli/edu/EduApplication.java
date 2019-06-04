package org.guli.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by leyi on 2019/6/4.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"org.guli.edu", "org.guli.common"})
public class EduApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }

}
