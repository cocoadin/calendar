package com.cocoadin.icalendar.core.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class ICalendar {

    private String begin;

    private String version;

    private String proUid;

    private String calScale;

    private Map<String,String> extraPro;

    private List<ICalendarEvent> children;
}
