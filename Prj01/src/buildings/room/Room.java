package buildings.room;

import buildings.building.Constants;
import buildings.exceptions.*;
import buildings.interfaces.RoomsItems;
import buildings.rooms_items.lamp.Lamp;
import buildings.interfaces.ChangeableUsageSpace;
import buildings.rooms_items.furniture.Furniture;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by Raik Yauheni on 26.10.2018.
 */
public class Room  {
    private static int countRooms;
    private int id;
    private double area;
    private boolean validated;
    private int numberOfWindows;
    private String title;
    private final List<Furniture> listOfFurniture = new ArrayList<>();
    private final List<Lamp> listOfLamps = new ArrayList<>();


    /** Constructors*/
    public Room(String title, double area, int numberOfWindows) throws IllegalTitleRoomException {
        if (title.length() < 2) throw new IllegalTitleRoomException("Rooms did not created. Rooms title has to consist at least two characters ");
        id = ++countRooms;
        if (area > Constants.MIN_ROOM_AREA || area <-Constants.MIN_ROOM_AREA ) {
            this.area = Math.abs(area);
        }
        else this.area = Constants.MIN_ROOM_AREA;
        this.numberOfWindows = Math.abs(numberOfWindows);
        this.title = title;
    }

    /** Getters and Setters */
    public int getId() {
        return id;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = Math.abs(area);
    }

    public boolean isValidated() {
        return validated;
    }

    public int getNumberOfWindows() {
        return numberOfWindows;
    }

    public void setNumberOfWindows(int numberOfWindows) {
        this.numberOfWindows = numberOfWindows;
    }

    public List<Furniture> getListOfFurniture() {
        return listOfFurniture;
    }

    public List<Lamp> getListOfLamps() {
        return listOfLamps;
    }

       /** Methods */
    public double getIlluminance() {
        double result   = 0;
        for(Lamp lamp : listOfLamps) {
            result+=lamp.getLuminance();
        }
        return   result+= numberOfWindows * Constants.ILLUMINANCE_OF_THE_WINDOW;
    }

    public boolean isIlluminanceCorrect() throws IlluminateTooLittleException, IlluminateTooMuchException {
        if (getIlluminance()> Constants.MAX_ILLUMINANCE) {
            throw new IlluminateTooMuchException();
        } else if (getIlluminance()< Constants.MIN_ILLUMINANCE) {
            throw new IlluminateTooLittleException();
        } else return true;
    }

    public boolean isUsageSpaceCorrect() throws SpaceUsageTooMuchException {
        if (getIlluminance()> Constants.LIMIT_OF_SPACE_USAGE) {
            throw new IlluminateTooLittleException();
        } else return true;
    }

    public boolean add(RoomsItems item) {
        if(item instanceof Lamp) {
            return  add((Lamp) item);
        } else  if (item instanceof Furniture) {
            return add((Furniture) item );
        } else {
            System.out.println("Object did not add. This type of object is not allowed.");
            return false;
        }
    }

    private boolean add(Lamp lamp)  {
        try {
            isIlluminanceCorrect();
        } catch (IllegalRoomException e) {
            validated = false;
            System.out.println("Room" + title + "(ID " + id + "): Illuminance is too much");
        } finally {
            return listOfLamps.add(lamp);
        }
    }

    private boolean add(Furniture furniture) {
        try {
            isUsageSpaceCorrect();
        } catch (SpaceUsageTooMuchException e) {
            validated = false;
            System.out.println("Room" + title + "(ID " + id + "): Space usage to much");
        } finally {
            return listOfFurniture.add(furniture);
        }
    }

    public boolean remove(RoomsItems item) {
        if(item instanceof Lamp) {
            return  listOfLamps.remove((Lamp) item);
        } else  if (item instanceof Furniture) {
            return listOfFurniture.remove((Furniture) item );
        } else {
            System.out.println("Object did not add. This type of objects is not allowed for adding into room.");
            return false;
        }
    }

    private boolean remove(Lamp lamp)  {
        try {
            isIlluminanceCorrect();
        } catch (IllegalRoomException e) {
            validated = false;
            System.out.println("Room" + title + "(ID " + id + "): Illuminance is too little");
        } finally {
            return listOfLamps.remove(lamp);
        }
    }

    private boolean remove(Furniture furniture) {
         return listOfFurniture.add(furniture);
    }

    public double getUsageSpace() {
        double sumAreas = 0;
        for (Furniture furniture : listOfFurniture) {
            sumAreas+=furniture.getArea();
        }
        return sumAreas;
    }

    public double getUsageSpaceChanged(){
        double sumChangedAreas = 0;
        for (Furniture furniture : listOfFurniture) {
            if (furniture instanceof ChangeableUsageSpace) {
                sumChangedAreas+=((ChangeableUsageSpace) furniture).getChangedArea();
            } else sumChangedAreas += furniture.getArea();
        }
        return sumChangedAreas;
    }

    public void validate() throws SpaceUsageTooMuchException, IlluminateTooLittleException, IlluminateTooMuchException {
        if(isIlluminanceCorrect() && isUsageSpaceCorrect()) {
        validated = true;
        System.out.println("validated ");
        }
    }

    public void describe() {
        System.out.println(getFullDescription());
    }

    public String getFullDescription() {
        StringBuilder result = new StringBuilder(this.toString() + "\n");
            result.append(Constants.TAB)
                    .append(Constants.TAB)
                    .append(getIlluminanceFullDescription() + "\n")
                    .append(Constants.TAB)
                    .append(Constants.TAB)
                    .append(getAreaDescription())
                    .append(Constants.TAB)
                    .append(Constants.TAB)
                    .append("Furniture:");
            if (listOfFurniture.size() ==0) {
                result.append(" None   " + "\n");
            } else  {
                result.append("\n");
                for (Furniture furniture : listOfFurniture) {
                    result.append(Constants.TAB + Constants.TAB + furniture.toString() + "\n");
                }
            }
        return result.toString();
    }

    public String getIlluminanceFullDescription(){
        StringBuilder sb = new StringBuilder ("Illuminance " + getIlluminance() + " lk (" +
                numberOfWindows + " windows("+Constants.ILLUMINANCE_OF_THE_WINDOW +" lk)");
        for (Lamp lamp: new TreeSet<Lamp>(listOfLamps)){
            sb.append(", " +Collections.frequency(listOfLamps, lamp) + " " +  lamp.toString());
        }
        sb.append(")");
        return  sb.toString();
    }

    public String getAreaDescription(){
        StringBuilder sb = new StringBuilder();
        sb.append("Area = " ).append(area).append(" м2, ");
        if(Math.max(getArea(),getUsageSpace()) > area ) {
            return sb.append("the furniture does not fit in the room \n").toString();
        }else  sb.append("usage space - ");
        if(getUsageSpace() == getUsageSpaceChanged()) {
            sb.append(getUsageSpace()).append(" m2 or ").append(100*getUsageSpace()/area).append("%) \n");
        } else {
            double guaranteedAvailableSpace = area - Math.max(getUsageSpace(), getUsageSpaceChanged());
            sb.append("from ")
                   .append(Math.min(getUsageSpace(), getUsageSpaceChanged()))
                   .append(" м2 to ")
                   .append(Math.max(getUsageSpace(), getUsageSpaceChanged()))
                   .append(" м2, guaranteed available ")
                   .append(guaranteedAvailableSpace)
                   .append(" м2 or ").append((guaranteedAvailableSpace*100 / area) )
                   .append("% \n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return (title+ "(ID: " +id+")");
    }
}

