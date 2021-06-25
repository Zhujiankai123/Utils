package com.zjk.spring.utils;

import java.time.ZoneId;

public enum TimeZone {
    GMT("GMT","Greenwich Mean Time / 格林威治时间"),
    GMT8("GMT+8","Greenwich Mean Time + 8h / 东八区"),
    UTC("UTC","Universal Time Coordinated / 协调世界时"),
    UTC8("UTC+8","Universal Time Coordinated + 8h / 东八区"),
    AsiaShanghai("Asia/Shanghai","中国 - 上海");



    private final String timeZone;
    private final String comment;
    private final ZoneId zoneId;

    TimeZone(String timezone, String comment){
        this.timeZone = timezone;
        this.comment = comment;
        this.zoneId = ZoneId.of(timezone);
    }

    public String getTimeZone() {
        return timeZone;
    }

    public String getComment() {
        return comment;
    }

    public ZoneId getZoneId() {
        return zoneId;
    }
}
