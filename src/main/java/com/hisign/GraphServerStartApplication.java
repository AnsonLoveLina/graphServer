package com.hisign;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;

//@EnableFeignClients
@ServletComponentScan
@SpringBootApplication
@ImportResource("classpath:applicationContext-graph.xml")
public class GraphServerStartApplication {

    public static void main(String[] strs) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(GraphServerStartApplication.class).web(true).run(strs);
//        SpringContextInit.setStaticApplicationContext(applicationContext);
    }
}
