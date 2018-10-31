package build.building;

import build.exceptions.IllegalBuildingException;
import build.exceptions.IllegalRoomException;
import build.room.Room;
import java.util.*;

/**
 * Created by Raik Yauheni on 26.10.2018.
 */
public class Building {
    private String title;
    private boolean validated;
    private Map<Integer,Room> rooms = new HashMap<>();

    public Building(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isValidated() {
        return validated;
    }

    public Map<Integer,Room> getRooms() {
        return rooms;
    }

    public boolean addRoom(Room room) {

        try {
            rooms.put(room.getId(),room);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean addAllRooms(Collection<Room> bunchOfRooms) {
        boolean result = true;
        for(Room room : bunchOfRooms) {
        if(this.addRoom(room) == false) result = false;
        }
        return result;
    }

    public Room getRoom(int iD) {
        return rooms.get(iD);
    }

    public Room removeRoom(int iD){
        return rooms.remove(iD);
    }

    public String getFullDesctiprion() {
        StringBuilder result = new StringBuilder(this.toString() + "\n");

        for (Map.Entry<Integer, Room> entry : rooms.entrySet()) {
           result.append(Constants.TAB + entry.getValue().getFullDescription());

        }
        return result.toString();
    }

    public void describe(){
        System.out.println(getFullDesctiprion());
    }

    public double getTotalArea() {
        double sumAreas = 0;
        for (Map.Entry<Integer, Room> entry : rooms.entrySet()) {
            sumAreas +=  entry.getValue().getArea();
        }
        return sumAreas;
    }

    public int getNumberOfRooms(){
        return rooms.size();
    }

    public void validate() throws IllegalBuildingException {
        try {
            for (Map.Entry<Integer, Room> entry : rooms.entrySet()) {
                entry.getValue().validate();
            }
            validated = true;
        } catch (IllegalRoomException e) {
           throw new IllegalBuildingException("Illegal room", e);
        }
    }

    @Override
    public String toString() {
        return title + "(Rooms: " + getNumberOfRooms() + ", Total Area: " + getTotalArea() +" m2)";
    }
}


