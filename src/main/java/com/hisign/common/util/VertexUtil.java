package com.hisign.common.util;

import com.google.common.collect.Sets;
import com.hisign.config.DruidConfig;
import org.apache.tinkerpop.gremlin.structure.Element;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.NoSuchElementException;
import java.util.Set;

public class VertexUtil {
    private static final Logger logger = LoggerFactory.getLogger(VertexUtil.class);

    public static <T> boolean putValue(Element element, String key, T value) {
        Set<T> values = null;
        try {
            values = element.value(key);
        } catch (Exception e) {
            logger.trace(e.getMessage());
            values = Sets.newHashSet();
            values.add(value);
            element.property(key, values);
            return false;
        }
        values.add(value);
//        element.property(key,values);
        return true;
    }

    public static <T> T getValue(Element element, String key, T defaultValue) {
        T value = defaultValue;
        try {
            value = element.value(key);
        } catch (Exception e) {
            logger.trace(e.getMessage());
        }
        return value;
    }
}
