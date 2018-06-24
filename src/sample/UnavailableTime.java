package sample;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class UnavailableTime {

    private DayOfWeek day;
    private LocalTime start;
    private LocalTime end;

    public UnavailableTime(DayOfWeek day){

        this.day = day;
        this.start = LocalTime.MIN;
        this.end = LocalTime.MAX;

    }

    public UnavailableTime(DayOfWeek day, LocalTime start){

        this.day = day;
        this.start = start;
        this.end = LocalTime.MAX;

    }

    public UnavailableTime(DayOfWeek day, LocalTime start, LocalTime end){

        this.day = day;
        this.start = start;
        this.end = end;

    }

    public DayOfWeek getDay() {
        return day;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

}
