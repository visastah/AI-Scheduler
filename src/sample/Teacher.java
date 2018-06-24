package sample;

import java.util.ArrayList;

public class Teacher {

    private String name;
    private ArrayList<UnavailableTime> unavailable = new ArrayList<>();

    public Teacher(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<UnavailableTime> getUnavailable() {
        return unavailable;
    }
}
