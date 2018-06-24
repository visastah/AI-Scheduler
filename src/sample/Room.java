package sample;

public class Room {

    private String roomName;
    private int capacity;

    public Room(String roomName, int capacity){
        this.roomName = roomName;
        this.capacity = capacity;
    }

    public String getRoomName() {
        return roomName;
    }

    public int getCapacity() {
        return capacity;
    }

}
