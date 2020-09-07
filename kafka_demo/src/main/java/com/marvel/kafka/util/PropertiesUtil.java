package com.marvel.kafka.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Description PropertiesUtil
 * @Author wupeng
 * @Motto Stay Hungry, Stay Foolish !
 * @Date 2020/9/5 5:26 下午
 **/
public class PropertiesUtil {

    private static final Properties props;

    static {
        props = new Properties();
        try {
            props.load(PropertiesUtil.class.getClassLoader().getResourceAsStream("kafka"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getString(String key){
        return (String) props.get(key);
    }

    public static int getInt(String key){
        return (int) props.get(key);
    }

    public static Properties getProps(){
        return PropertiesUtil.props;
    }
}
