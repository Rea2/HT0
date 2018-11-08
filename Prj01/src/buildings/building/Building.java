package buildings.building;

import buildings.exceptions.IllegalBuildingException;
import buildings.exceptions.IllegalRoomException;
import buildings.rooms.Room;
import java.util.*;

/**
 * Created by Raik Yauheni on 26.10.2018.
 */
public class Building {
    private String title;
    private boolean validated =false;
    private Map<Integer,Room> rooms = new HashMap<>();
    private Map<Integer,Room> invalidRooms = new HashMap<>();

    public Building(String title) {
        this.title = title;
    }

    public Map<Integer, Room> getInvalidRooms() {
        return invalidRooms;
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

    public Room getRoom(int iD) throws IllegalArgumentException {
        if(! rooms.containsKey(iD)) {
            return null;
        }
        return rooms.get(iD);
    }

    public Room removeRoom(int iD){
        return rooms.remove(iD);
    }

    public String getFullDescription() {
        StringBuilder result = new StringBuilder(this.toString() + "\n");
        for (Map.Entry<Integer, Room> entry : rooms.entrySet()) {
           result.append(Constants.TAB + entry.getValue().getFullDescription());
        }
        return result.toString();
    }

    public void describe(){
        System.out.println(getFullDescription());
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
        boolean isOk = true;
        Map<Integer, Room> map = new HashMap();
        if(rooms.size() == 0) {
            throw new IllegalBuildingException("There are not any rooms in the building");
        }
        for (Map.Entry<Integer, Room> entry : rooms.entrySet()) {
            try {
               entry.getValue().validate();
            } catch (IllegalRoomException e) {
               isOk =false;
               map.put(entry.getValue().getId(), entry.getValue());
            }
        }
        if (isOk) {
            validated = true;
            invalidRooms = null;
        }
        else {
            validated = false;
            invalidRooms = map;
            throw new IllegalBuildingException("Illegal room(s).");
        }
    }

    @Override
    public String toString() {
        return title + "(Rooms: " + getNumberOfRooms() + ", Total Area: " + getTotalArea() +" m2)";
    }
}


