package com.hisign.common.resource;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by zhouyi1 on 2016/1/20 0020.
 * aware的方式比listener更早获得applicatioinContext
 */
@Component
public class ASpringContextInit implements ApplicationContextAware  {
    private static ApplicationContext applicationContext;

    public static InputStream getResource(String resourcePath) throws IOException {
        Resource resource = applicationContext.getResource(resourcePath);
        return resource.getInputStream();
    }

    public static ApplicationContext getContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        System.out.println("set appcontext!");
        this.applicationContext = applicationContext;
    }
}

