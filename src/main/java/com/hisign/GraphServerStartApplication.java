package com.hisign;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ImportResource;

//@EnableFeignClients
@ServletComponentScan
@SpringBootApplication
@ImportResource("classpath:applicationContext-graph.xml")
public class GraphServerStartApplication {

    public static void main(String[] strs) {
        new SpringApplicationBuilder(GraphServerStartApplication.class).web(true).run(strs);
    }
}
