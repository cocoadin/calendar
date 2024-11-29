package com.cocoadin.icalendar.core.util;

import com.cocoadin.icalendar.core.model.Holiday;
import com.cocoadin.icalendar.core.start.StartMain;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;

import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonUtil {
    final static Logger logger = Logger.getLogger(JsonUtil.class);


    public static <T> T readJsonFromFile(String filePath, Type typeOfT) {
        try {
            // 创建 Gson 对象
            Gson gson = new Gson();

            // 从 JSON 文件读取并解析为对象
            FileReader reader = new FileReader(filePath);
            return gson.fromJson(reader, typeOfT);
        } catch (Exception e) {
            logger.error("read json error", e);
            throw new RuntimeException(e);
        }
    }

    public static <T> T readJsonFromFile(InputStream stream, Type typeOfT) {
        try {
            // 创建 Gson 对象
            Gson gson = new Gson();
            InputStreamReader reader = new InputStreamReader(stream);

            // 从 JSON 文件读取并解析为对象
            return gson.fromJson(reader, typeOfT);
        } catch (Exception e) {
            logger.error("read json error", e);
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String path="asset/2025.json";
        ClassLoader classLoader = JsonUtil.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(path);

        List<Holiday> holidays =readJsonFromFile(inputStream,new TypeToken<List<Holiday>>(){}.getType());
        logger.info(new Gson().toJson(holidays));

    }

}
