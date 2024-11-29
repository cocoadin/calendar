package com.cocoadin.icalendar.core.model;

import lombok.Data;

@Data
public class Holiday {
    private String startDate;
    private String endDate;
    private Type type;
    private String desc;
    public static enum Type{
        NO_WORK("休"),
        WORK("班"),
        ;


        private String desc;

        private Type(String desc){
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }
    }
}
