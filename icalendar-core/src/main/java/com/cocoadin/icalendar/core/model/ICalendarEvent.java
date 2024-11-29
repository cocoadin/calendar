package com.cocoadin.icalendar.core.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ICalendarEvent {

    private String begin;


    private String dtStamp;


    private String uid;


    private String dtStart;


    private String dtEnd;

    private String classification;

    private String summary;

    private String transp;

    private String rrule;

    private String category;

    private Map<String, String> extraPro;
}
