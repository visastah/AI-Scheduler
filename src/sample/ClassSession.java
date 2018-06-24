package sample;

import javafx.beans.property.SimpleStringProperty;

public class ClassSession {

    private Subject subject;
    private Room room;
    private Period period;
    private final SimpleStringProperty description;

    public ClassSession(Subject subject, Period period, Room room){
        this.subject = subject;
        this.room = room;
        this.period = period;
        this.description = new SimpleStringProperty(period.toString()+"\n "+
                subject.getName()+"\n "+subject.getTeacher().getName()+"\n"+room.getRoomName()+" \n");
    }

    public Room getRoom() {
        return room;
    }

    public Period getPeriod() {
        return period;
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String toString(){
        return"\n"+ period.toString()+"\n "+
                subject.getName()+"\n "+subject.getTeacher().getName()+"\n"+room.getRoomName()+" \n";
    }
}
