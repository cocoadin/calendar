package com.cocoadin.icalendar.core.constants;

public enum CalScaleEnum {
    GREGORIAN("GREGORIAN","公历"),
    JULIAN("JULIAN","儒略历"),
    ;
    private String code;
    private String desc;
    private CalScaleEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public String getCode() {
        return code;
    }
}
