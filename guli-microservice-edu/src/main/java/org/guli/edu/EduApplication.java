package org.guli.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by leyi on 2019/6/4.
 */
/*
@ComponentScan(basePackages={"com.guli.edu","com.guli.common"})
如果 CommonMetaObjectHandler 是在 guli-common 项目中，需要添加这个注解去扫描 guli-common 工程中的包。
*/
@SpringBootApplication
public class EduApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }

}
