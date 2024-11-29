package com.cocoadin.icalendar.core.start;

import com.cocoadin.icalendar.core.model.Holiday;
import com.cocoadin.icalendar.core.model.ICalendar;
import com.cocoadin.icalendar.core.model.ICalendarBuilder;
import com.cocoadin.icalendar.core.util.JsonUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class StartMain {
    final static Logger logger = Logger.getLogger(StartMain.class);

    public static void main(String[] args) {

        ClassLoader classLoader = JsonUtil.class.getClassLoader();
        List<Holiday> holidays=new ArrayList<>();
        for (String path : args) {
            InputStream inputStream = classLoader.getResourceAsStream(path);

            List<Holiday> childHoliday = JsonUtil.readJsonFromFile(inputStream, new TypeToken<List<Holiday>>() {
            }.getType());

            holidays.addAll(childHoliday);
            logger.info("load json file "+path);
        }
        String assetFile=System.getProperty("assetFile");
        ICalendar calendar= ICalendarBuilder.convertToCalendar(holidays);

        ICalendarBuilder.writeCalendar(calendar,assetFile);

        logger.info("save assetFile success "+assetFile);
    }
}
