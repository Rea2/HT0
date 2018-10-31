package buildings;

/**
 * Created by Raik Yauheni on 27.10.2018.
 */
import buildings.building.Building;
import buildings.exceptions.IlluminateTooLittleException;
import buildings.exceptions.IlluminateTooMuchException;
import buildings.exceptions.SpaceUsageTooMuchException;
import buildings.furniture.Armchair;
import buildings.furniture.Bed;
import buildings.furniture.BedTypes;
import buildings.furniture.Sofa;
import buildings.lamp.Lamp;
import buildings.room.Room;
import java.util.ArrayList;
import java.util.List;

public class TestClass {
    static List<Room> rooms = new ArrayList<>();

    public static void main(String[] args) {
        try {
            Building building = new Building("Building 1");


            rooms.add(new Room("Комната1", 100, 1));
            rooms.add(new Room("Комната2", 200, 2));
            rooms.add(new Room("Комната3", 32, 3));
            rooms.add(new Room("Комната4", -200, 5));

            rooms.get(0).add(new Lamp(150));
            rooms.get(0).add(new Lamp(150));
            rooms.get(0).add(new Lamp(250));

            rooms.get(0).add(new Armchair("Armchair",1,2));
            rooms.get(0).add(new Sofa("Sofa",3,4));
            rooms.get(1).add(new Bed("Bed",2, BedTypes.SINGLE));
            rooms.get(1).add(new Sofa("Sofaaaa",2,3));
            rooms.get(1).add(new Sofa("Sofaa",2,3));
            rooms.get(2).add(new Bed("Beeeed",4,BedTypes.QUEEN_SIZE));
            System.out.println(building.addAllRooms(rooms));

            building.describe();
            building.getRoom(4).add(new Lamp(500));
            building.describe();
            System.out.println(building.isValidated());
        } catch (IlluminateTooLittleException e) {
            e.printStackTrace();
        } catch (IlluminateTooMuchException e) {
            e.printStackTrace();
        } catch (SpaceUsageTooMuchException e) {
            e.printStackTrace();
        }


    }


}

