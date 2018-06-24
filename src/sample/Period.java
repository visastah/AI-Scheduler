package sample;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class Period {

    private DayOfWeek day;
    private LocalTime startTime;
    private LocalTime endTime;
    private String level;

    public Period(DayOfWeek day, LocalTime startTime, LocalTime endTime, String level){
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.level = level;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public String timeToString(LocalTime time){
        String hour = ""+time.getHour();
        String minute = ""+time.getMinute();
        if(hour.length() == 1)
            hour = "0"+hour;
        if(minute.length() == 1)
            minute = "0"+minute;
        return hour+":"+minute;
    }

    public String getLevel() {
        return level;
    }

    @Override
    public String toString(){
        return timeToString(startTime)+"-"+timeToString(endTime);
    }
}
