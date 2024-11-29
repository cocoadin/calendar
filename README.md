# calendar
calendar for china holiday

## 运行脚本
```text
mvn exec:java 
```

## 标准日历解析

### 属性说明

参考：[iCalendar](https://m.allhistory.com/detail/59105a4d55b542257a025d20)

#### 示例数据
```angular2html
BEGIN:VCALENDAR                         #日历的开始
VERSION:2.0                             #日历的版本
PRODID:icalendar-ruby                   #日历的产品信息
CALSCALE:GREGORIAN                      #RFC 2445 标准，表示历法 GREGORIAN:公历  JULIAN:儒略历
X-WR-CALNAME:中国大陆节假日               #自定义的扩展属性，日历的显示名称
X-APPLE-LANGUAGE:zh                     #自定义的扩展属性，apple 专用 语言
X-APPLE-REGION:CN                       #自定义的扩展属性，apple 专用 国家
BEGIN:VEVENT                            #事件开始
DTSTAMP;VALUE=DATE:19760401             #表示日历条目的最后修改时间
UID:6aaace09-be8b-3605-8a8c-30ee4dc10f81 #一次事件中的唯一uid
DTSTART;VALUE=DATE:20221231             # 事件的开始时间
DTEND;VALUE=DATE:20230103               # 事件的结束时间
CLASS:PUBLIC                            #事件的分类信息 PUBLIC 公开，PRIVATE 私有 CONFIDENTIAL 机密
SUMMARY;LANGUAGE=zh_CN:元旦（休）         #简介的描述，描述日历事件的内容
TRANSP:TRANSPARENT                      #指定事件是否是透明和非透明事件 OPAQUE 透明的 TRANSPARENT 非透明的会占用日历的时间
CATEGORIES:節慶                          #定义事件的分类标签
X-APPLE-SPECIAL-DAY:WORK-HOLIDAY        #WORK-HOLIDAY 工作假日 HOLIDAY 正式的假期 SPECIAL-DAY 特殊的节日或纪念日 WEEKEND 周末
X-APPLE-UNIVERSAL-ID:42902458-1dd4-5105-04d0-2dccc0194c5f  #唯一标识
END:VEVENT
END:VCALENDAR
```