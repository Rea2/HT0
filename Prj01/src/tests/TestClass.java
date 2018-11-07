package tests;

/**
 * Created by Raik Yauheni on 27.10.2018.
 */
import buildings.building.Building;
import buildings.exceptions.IlluminateTooLittleException;
import buildings.exceptions.IlluminateTooMuchException;
import buildings.exceptions.SpaceUsageTooMuchException;
import buildings.rooms_items.furniture.Armchair;
import buildings.rooms_items.furniture.Bed;
import buildings.rooms_items.furniture.BedTypes;
import buildings.rooms_items.furniture.Sofa;
import buildings.rooms_items.lamp.Lamp;
import buildings.rooms.Room;
import buildings.rooms_items.window.WindowDefault;

import java.util.ArrayList;
import java.util.List;

public class TestClass {
    static List<Room> rooms = new ArrayList<>();
    static Building building;
    public static void main(String[] args) {
        try {
            building = new Building("Building 1");
            rooms.add(new Room("Комната1", 4, 2));
            rooms.add(new Room("Комната3", 32, 3));
            rooms.add(new Room("Комната4", 6, 5));

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
            building.getRoom(3).add(new Lamp(500));
            building.describe();
            System.out.println(building.isValidated());
            building.validate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Building("townHouse").describe();
        new Room("Bedroom", 8,3).describe();

        System.out.println(building.getInvalidRooms());
    }


}

