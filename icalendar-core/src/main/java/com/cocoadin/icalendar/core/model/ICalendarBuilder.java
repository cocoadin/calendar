package com.cocoadin.icalendar.core.model;


import com.cocoadin.icalendar.core.constants.CalConstants;
import com.cocoadin.icalendar.core.constants.CalScaleEnum;
import com.cocoadin.icalendar.core.start.StartMain;
import com.cocoadin.icalendar.core.util.DateUtil;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ICalendarBuilder {

    final static Logger logger = Logger.getLogger(ICalendarBuilder.class);

    final static  String format="%s:%s\n";
    final static  String specialFormat="%s;%s=%s:%s\n";

    public static ICalendar convertToCalendar(List<Holiday> holidays) {
        //今天
        String modify= DateUtil.getNowDay();
        Map<String,String> extraMap=new HashMap<>();
        extraMap.put(CalConstants.KEY_X_WR_CALNAME,CalConstants.CALNAME);
        extraMap.put(CalConstants.KEY_X_APPLE_LANGUAGE,CalConstants.LANGUAGE_ZH);
        extraMap.put(CalConstants.KEY_X_APPLE_REGION,CalConstants.REGION_CN);
        ICalendar iCalendar=ICalendar.builder()
                .begin(CalConstants.KEY_V_CALENDAR)
                .version(CalConstants.VERSION_2_0)
                .proUid("cocoa-calendar")
                .calScale(CalScaleEnum.GREGORIAN.getCode())
                .extraPro(extraMap)
                .children(new ArrayList<>())
                .build();

        for (Holiday holiday : holidays) {
            ICalendarEvent iCalendarEvent=ICalendarEvent.builder()
                    .uid(UUID.randomUUID().toString())
                    .begin(CalConstants.KEY_VEVENT)
                    .dtStamp(modify)
                    .dtStart(holiday.getStartDate())
                    .dtEnd(holiday.getEndDate())
                    .classification(CalConstants.KEY_PUBLIC)
                    .summary(holiday.getType().getDesc())
                    .transp(CalConstants.KEY_TRANSPARENT)
                    .category("节假日安排")
                    .extraPro(new HashMap<>())
                    .build();
            if (StringUtils.isNoneBlank(holiday.getDesc())){
                iCalendarEvent.setSummary(String.format("%s(%s)",holiday.getDesc(),holiday.getType().getDesc()));
            }
            if (Holiday.Type.WORK.equals(holiday.getType())){
                iCalendarEvent.getExtraPro().put(CalConstants.KEY_X_APPLE_SPECIAL_DAY,"SPECIAL-DAY");
            }else {
                iCalendarEvent.getExtraPro().put(CalConstants.KEY_X_APPLE_SPECIAL_DAY,"HOLIDAY");
            }
            iCalendarEvent.getExtraPro().put(CalConstants.KEY_X_APPLE_UNIVERSAL_ID,UUID.randomUUID().toString());
            iCalendar.getChildren().add(iCalendarEvent);
        }
        return iCalendar;
    }


    public static String writeCalendar(ICalendar iCalendar,String filePath) {
        // 开始写入 .ics 文件
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // 写入 ICS 文件头部信息
            writer.write(String.format(format,CalConstants.KEY_BEGIN,iCalendar.getBegin()));
            writer.write(String.format(format,CalConstants.KEY_VERSION,iCalendar.getVersion()));
            writer.write(String.format(format,CalConstants.KEY_PRODID,iCalendar.getProUid()));
            writer.write(String.format(format,CalConstants.KEY_CALSCALE,iCalendar.getCalScale()));
            if (MapUtils.isNotEmpty(iCalendar.getExtraPro())){
                for (Map.Entry<String, String> entry : iCalendar.getExtraPro().entrySet()) {
                    writer.write(String.format(format,entry.getKey(),entry.getValue()));
                }
            }

            // 写入大量事件
            for (ICalendarEvent event : iCalendar.getChildren()) {
                writeEvent(writer,event);
            }
            writer.write(String.format(format,CalConstants.KEY_END,iCalendar.getBegin()));
            logger.info("ICS 文件创建成功: " + filePath);
        } catch (IOException e) {
            logger.error("创建 ICS 文件失败",e);
        }
        return filePath;
    }

    /**
     * 写入单个事件
     */
    private static void writeEvent(BufferedWriter writer, ICalendarEvent event) throws IOException {
        // 定义事件数据
        writer.write(String.format(format,CalConstants.KEY_BEGIN,event.getBegin()));
        writer.write(String.format(specialFormat,CalConstants.KEY_DTSTAMP,CalConstants.KEY_VALUE,CalConstants.KEY_DATE,event.getDtStamp()));
        writer.write(String.format(specialFormat,CalConstants.KEY_DTSTAMP,CalConstants.KEY_DTSTART,CalConstants.KEY_DATE,event.getDtStart()));
        if (StringUtils.isNoneBlank(event.getDtEnd())){
            writer.write(String.format(specialFormat,CalConstants.KEY_DTEND,CalConstants.KEY_DTSTART,CalConstants.KEY_DATE,event.getDtEnd()));
        }
        writer.write(String.format(format,CalConstants.KEY_CLASS,event.getClassification()));
        writer.write(String.format(format,CalConstants.KEY_SUMMARY,event.getSummary()));
        writer.write(String.format(format,CalConstants.KEY_TRANSP,event.getTransp()));
        writer.write(String.format(format,CalConstants.KEY_CATEGORIES,event.getCategory()));

        if (MapUtils.isNotEmpty(event.getExtraPro())){
            for (Map.Entry<String, String> entry : event.getExtraPro().entrySet()) {
                writer.write(String.format(format,entry.getKey(),entry.getValue()));
            }
        }

        writer.write(String.format(format,CalConstants.KEY_END,event.getBegin()));

    }
}
