package com.cyy.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author CYY
 * @date 2023年02月12日 下午9:32
 * @description 配置文件工具类
 */
public class PropertiesUtil {
    /**
     * 根据文件路径，加载文件，文件不存在时返回null
     * @param path 路径
     * @return null表示文件不存在，反之返回加载后的properties
     */
    public static Properties getPropertiesByPath(String path){
        Properties properties = null;
        try (FileInputStream inputStream = new FileInputStream(path)){
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

}
