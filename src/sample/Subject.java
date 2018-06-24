package sample;

import java.util.ArrayList;

public class Subject {

    private int frequency;

    private String name;
    private int studentNumber;
    private String level;
    private Teacher teacher;
    private ArrayList<Period> availablePeriods = new ArrayList<>();
    private ArrayList<Room> availableRooms = new ArrayList<>();

    public Subject(String name, String level, Teacher teacher, int studentNumber, int frequency){
        this.frequency = frequency;
        this.name = name;
        this.level = level;
        this.studentNumber = studentNumber;
        this.teacher = teacher;
    }

    public String getLevel() {
        return level;
    }

    public int getFrequency() {
        return frequency;
    }

    public String getName() {
        return name;
    }

    public int getStudentNumber() {
        return studentNumber;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public ArrayList<Period> getAvailablePeriods() {
        return availablePeriods;
    }

    public ArrayList<Room> getAvailableRooms() {
        return availableRooms;
    }
}
