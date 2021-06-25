package com.zjk.spring.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 *         // Datetime datetime = new Datetime();
 *         Datetime datetime = new Datetime("20210625",TimeZone.GMT8);
 *
 *         datetime.monthDiff(1);
 *         System.out.println("year: " + datetime.getYear());
 *         System.out.println("month: " + datetime.getMonth());
 *         System.out.println("day: " + datetime.getDay());
 *         System.out.println("date: " + datetime.getDateAsYYYYMMDD());
 *         System.out.println("time: " + datetime.getTimeAsHH24MiSS());
 *         System.out.println("datetime: " + datetime.getDatetimeAsYYYYMMDDHH24Miss());
 *         System.out.println("datetime_dashed: " + datetime.getDateAsYYYYMMDD_dashed());
 *         System.out.println("datetime_slash: " + datetime.getDateAsYYYYMMDD_slash());
 *         System.out.println("time: " + datetime.getTimeAsHH24MiSS_colon());
 *         System.out.println("datetime: " + datetime.getDatetimeAsYYYYMMDDHH24Miss_dash());
 *         System.out.println("datetime: " + datetime.getDatetimeAsYYYYMMDDHH24Missmils_dash());
 *         System.out.println("datetime: " + datetime.getDatetimeAsYYYYMMDDHH24Miss_slash());
 *         System.out.println("datetime: " + datetime.getDatetimeAsYYYYMMDDHH24Missmils_slash());
 *         System.out.println("hour: " + datetime.getHour());
 *         System.out.println("minute: " + datetime.getMinute());
 *         System.out.println("second: " + datetime.getSecond());
 *         System.out.println("nano-second: " + datetime.getNanoSecond());
 *         System.out.println("timestamp: " + datetime.getTimestamp());
 *
 *          year: 2021
 *          month: 7
 *          day: 25
 *          date: 20210725
 *          time: 000000
 *          datetime: 20210725000000
 *          datetime_dashed: 2021-07-25
 *          datetime_slash: 2021/07/25
 *          time: 00:00:00
 *          datetime: 2021-07-25 00:00:00
 *          datetime: 2021-07-25 00:00:00
 *          datetime: 2021/07/25 00:00:00
 *          datetime: 2021/07/25 00:00:00
 *          hour: 0
 *          minute: 0
 *          second: 0
 *          nano-second: 0
 *          timestamp: 1627142400000
 *
 *
 *         long a = Datetime.daysBetween("20210304","20210308");
 *         System.out.println(a);
 *         long b = Datetime.monthsBetween("20210308","202001004");
 *         System.out.println(b);
 *         long c = Datetime.yearsBetween("202001004","20210308");
 *         System.out.println(c);
 *
 *          4
 *          -14
 *          1
 */

public class Datetime {

    private Calendar calendar = Calendar.getInstance();
    private ZoneId zoneId;

    private ZonedDateTime dateTime;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;
    private int nanoSecond;
    private long timestamp;
    private String dateAsYYYYMMDD;
    private String timeAsHH24MiSS;
    private String datetimeAsYYYYMMDDHH24Miss;
    private String dateAsYYYYMMDD_dashed;
    private String dateAsYYYYMMDD_slash;
    private String timeAsHH24MiSS_colon;
    private String datetimeAsYYYYMMDDHH24Miss_dash;
    private String datetimeAsYYYYMMDDHH24Missmils_dash;
    private String datetimeAsYYYYMMDDHH24Miss_slash;
    private String datetimeAsYYYYMMDDHH24Missmils_slash;

    public Datetime(TimeZone timeZone){
        this.zoneId = timeZone.getZoneId();

        Date current = calendar.getTime();
        this.timestamp = current.getTime();
        this.dateTime = current.toInstant().atZone(this.zoneId);
        this.init();
    }

    public Datetime(){
        this.zoneId = TimeZone.GMT8.getZoneId();  // default time-zone: GMT+8

        Date current = calendar.getTime();
        this.timestamp = current.getTime();
        this.dateTime = current.toInstant().atZone(this.zoneId);
        this.init();
    }

    public Datetime(String yyyymmddhh24miss, TimeZone timeZone){
        this.zoneId = timeZone.getZoneId();
        this.setTime(yyyymmddhh24miss);
    }

    public void setTime(String yyyymmddhh24miss){
        if (yyyymmddhh24miss.length() != 14 && yyyymmddhh24miss.length() != 8){
            throw new RuntimeException("Datetime format should be yyyymmdd or yyyymmddhh24miss");
        }

        String timestamp_;
        switch (yyyymmddhh24miss.length()){
            case 8:
                this.dateTime = this.yyyymmdd2date(yyyymmddhh24miss);

                break;
            case 14:
                this.dateTime = this.yyyymmddhh24miss2date(yyyymmddhh24miss);
                break;
        }

        timestamp_ = dateTime.toEpochSecond()+"000";
        this.timestamp = Long.parseLong(timestamp_);
        this.init();
    }

    private ZonedDateTime yyyymmdd2date(String yyyymmdd){
        int yyyy = Integer.parseInt(yyyymmdd.substring(0,4));
        int mm = Integer.parseInt(yyyymmdd.substring(4,6));
        int dd = Integer.parseInt(yyyymmdd.substring(6));
        return ZonedDateTime.of(yyyy,mm,dd,00,00,00,00, this.zoneId);
    }

    private ZonedDateTime yyyymmddhh24miss2date(String yyyymmddhh24miss){
        int yyyy = Integer.parseInt(yyyymmddhh24miss.substring(0,4));
        int mm = Integer.parseInt(yyyymmddhh24miss.substring(4,6));
        int dd = Integer.parseInt(yyyymmddhh24miss.substring(6,8));
        int hh24 = Integer.parseInt(yyyymmddhh24miss.substring(8,10));
        int mi = Integer.parseInt(yyyymmddhh24miss.substring(10,12));
        int ss = Integer.parseInt(yyyymmddhh24miss.substring(12));
        return ZonedDateTime.of(yyyy,mm,dd,hh24,mi,ss,00, this.zoneId);
    }

    private void init(){
        this.year = dateTime.getYear();
        this.month = dateTime.getMonth().getValue();
        this.day = dateTime.getDayOfMonth();
        this.hour = dateTime.getHour();
        this.minute = dateTime.getMinute();
        this.second = dateTime.getSecond();
        this.nanoSecond = dateTime.getNano();
        this.dateAsYYYYMMDD = dateTime.format(DateTimeFormatter.BASIC_ISO_DATE).split("\\+")[0];
        this.timeAsHH24MiSS = dateTime.format(DateTimeFormatter.ISO_LOCAL_TIME).split("\\.")[0].replace(":","");
        this.datetimeAsYYYYMMDDHH24Miss = this.dateAsYYYYMMDD+this.timeAsHH24MiSS;
        this.dateAsYYYYMMDD_dashed = dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE);
        this.dateAsYYYYMMDD_slash = dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE).replace("-","/");
        this.timeAsHH24MiSS_colon = dateTime.format(DateTimeFormatter.ISO_LOCAL_TIME).split("\\.")[0];
        this.datetimeAsYYYYMMDDHH24Miss_dash= dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).split("\\.")[0].replace("T"," ");
        this.datetimeAsYYYYMMDDHH24Missmils_dash= dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).replace("T"," ");
        this.datetimeAsYYYYMMDDHH24Miss_slash= dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).split("\\.")[0].replace("T"," ").replace("-","/");
        this.datetimeAsYYYYMMDDHH24Missmils_slash= dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).replace("T"," ").replace("-","/");
    }

    public void dateDiff(int day){
        assert day != 0;

        if (day > 0)
            this.dateTime = dateTime.plusDays(day);
        else
            this.dateTime = dateTime.minusDays(Math.abs(day));
        this.timestamp = this.getTimestampFromDateTime();
        this.init();
    }

    public  void monthDiff(int month){
        assert month != 0;
        if (month > 0)
            this.dateTime = dateTime.plusMonths(month);
        else
            this.dateTime = dateTime.minusMonths(Math.abs(month));
        this.timestamp = this.getTimestampFromDateTime();
        this.init();
    }

    public  void yearDiff(int year){
        assert year != 0;
        if (year > 0)
            this.dateTime = dateTime.plusYears(year);
        else
            this.dateTime = dateTime.minusYears(Math.abs(year));
        this.timestamp = this.getTimestampFromDateTime();
        this.init();
    }

    private Long getTimestampFromDateTime(){
        String nanoSec = (this.dateTime.getNano() +"000000").substring(0,3);
        String sec = String.valueOf(this.dateTime.toEpochSecond());
        return Long.parseLong(sec + nanoSec);
    }

    static long daysBetween(String A,String B){
        if (A.length() != 8 && B.length() != 8)
            throw new RuntimeException("datetime format should be yyyymmdd");

        return (getDateTimeFromString(B).toEpochSecond() - getDateTimeFromString(A).toEpochSecond()) / (60*60*24);

    }

    static long monthsBetween(String A,String B){
        if (A.length() != 8 && B.length() != 8)
            throw new RuntimeException("datetime format should be yyyymmdd");
        ZonedDateTime timeB = getDateTimeFromString(B);
        ZonedDateTime timeA = getDateTimeFromString(A);

        int yearA = timeA.getYear();
        int yearB = timeB.getYear();
        int monthA = timeA.getMonthValue();
        int monthB = timeB.getMonthValue();

        return ( yearB - yearA ) * 12 + ( monthB - monthA );
    }

    static long yearsBetween(String A,String B){
        if (A.length() != 8 && B.length() != 8)
            throw new RuntimeException("datetime format should be yyyymmdd");
        ZonedDateTime timeB = getDateTimeFromString(B);
        ZonedDateTime timeA = getDateTimeFromString(A);

        int yearA = timeA.getYear();
        int yearB = timeB.getYear();

        return ( yearB - yearA );
    }

    static private ZonedDateTime getDateTimeFromString(String yyyymmdd){
        int yyyy_a = Integer.parseInt(yyyymmdd.substring(0,4));
        int mm_a = Integer.parseInt(yyyymmdd.substring(4,6));
        int dd_a = Integer.parseInt(yyyymmdd.substring(6));
        return ZonedDateTime.of(yyyy_a, mm_a, dd_a,
                            00,00,00,00,
                                TimeZone.GMT8.getZoneId());
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public ZoneId getZoneId() {
        return zoneId;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    public int getNanoSecond() {
        return nanoSecond;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getDateAsYYYYMMDD() {
        return dateAsYYYYMMDD;
    }

    public String getTimeAsHH24MiSS() {
        return timeAsHH24MiSS;
    }

    public String getDatetimeAsYYYYMMDDHH24Miss() {
        return datetimeAsYYYYMMDDHH24Miss;
    }

    public String getDateAsYYYYMMDD_dashed() {
        return dateAsYYYYMMDD_dashed;
    }

    public String getDateAsYYYYMMDD_slash() {
        return dateAsYYYYMMDD_slash;
    }

    public String getTimeAsHH24MiSS_colon() {
        return timeAsHH24MiSS_colon;
    }

    public String getDatetimeAsYYYYMMDDHH24Miss_dash() {
        return datetimeAsYYYYMMDDHH24Miss_dash;
    }

    public String getDatetimeAsYYYYMMDDHH24Missmils_dash() {
        return datetimeAsYYYYMMDDHH24Missmils_dash;
    }

    public String getDatetimeAsYYYYMMDDHH24Miss_slash() {
        return datetimeAsYYYYMMDDHH24Miss_slash;
    }

    public String getDatetimeAsYYYYMMDDHH24Missmils_slash() {
        return datetimeAsYYYYMMDDHH24Missmils_slash;
    }
}


