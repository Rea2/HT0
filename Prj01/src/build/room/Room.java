package build.room;

import build.building.Constants;
import build.building.PlaceableRoomItem;
import build.exceptions.IlluminateTooMuchException;
import build.exceptions.SpaceUsageTooMuchException;
import build.lamp.Lamp;
import build.building.ChangeableArea;
import build.exceptions.IllegalRoomException;
import build.exceptions.IlluminateTooLittleException;
import build.furniture.Furniture;

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
    private int nubmerOfWindows;
    private String title;
    private List<Furniture> listOfFurniture = new ArrayList<>();
    private List<Lamp> listOfLamps = new ArrayList<>();

    /** Constructors */
    public Room(String title, double area, int numberOfWindows) {
        id = ++countRooms;
        if (area > 1) this.area = Math.abs(area);
        else area = Constants.MIN_ROOM_AREA;
        this.nubmerOfWindows = numberOfWindows;
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

    public int getNubmerOfWindows() {
        return nubmerOfWindows;
    }

    public void setNubmerOfWindows(int nubmerOfWindows) {
        this.nubmerOfWindows = nubmerOfWindows;
    }

    public List<Furniture> getListOfFurniture() {
        return listOfFurniture;
    }

    public void setListOfFurniture(List<Furniture> listOfFurniture) {
        this.listOfFurniture = listOfFurniture;
    }

    public List<Lamp> getListOfLamps() {
        return listOfLamps;
    }

    public void setListOfLamps(List<Lamp> listOfLamps) {
        this.listOfLamps = listOfLamps;
    }

    /** Methods */
    public double getIlluminance() {
        double result   = 0;
        for(Lamp lamp : listOfLamps) {
            result+=lamp.getLuminance();
        }
        return   result+= nubmerOfWindows * Constants.ILLUMINANCE_OF_THE_WINDOW;
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

    public boolean add(PlaceableRoomItem item) {
        if(item instanceof Lamp) {
            return  add((Lamp) item);
        } else  if (item instanceof Furniture) {
            return add((Furniture) item );
        } else {
            System.out.println("Object did not add. This types of objects is not allowed.");
            return false;
        }
    }

    public boolean add(Lamp lamp) throws IlluminateTooLittleException, IlluminateTooMuchException  {
        if (!isIlluminanceCorrect()) {
            validated = false;
        }
        return listOfLamps.add(lamp);
    }

    public boolean add(Furniture furniture) throws SpaceUsageTooMuchException {
        if (!isUsageSpaceCorrect()) {

        }
        return listOfFurniture.add(furniture);
    }

    public double getUsageSpaceChanged(){
        double sumChangedAreas = 0;
        for (Furniture furniture : listOfFurniture) {
            if (furniture instanceof ChangeableArea) {
                sumChangedAreas+=((ChangeableArea) furniture).getChangedArea();
            } else sumChangedAreas += furniture.getArea();
        }
        return sumChangedAreas;
    }

    public double getUsageSpace() {
            double sumAreas = 0;
            for (Furniture furniture : listOfFurniture) {
                sumAreas+=furniture.getArea();
            }
            return sumAreas;
    }
    public void validate() throws IllegalRoomException {
        if(isIlluminanceCorrect() && isUsageSpaceCorrect() && area > 0) {
            validated = true;
        }
    }

    public String getFullDescription() {
        StringBuilder result = new StringBuilder(this.toString());
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

    public void describe() {
        System.out.println(getFullDescription());
    }

    public String getIlluminanceFullDescription(){
        StringBuilder sb = new StringBuilder ("Illuminance " + getIlluminance() + " (" +
                nubmerOfWindows + " windows("+Constants.ILLUMINANCE_OF_THE_WINDOW +" lk)");
        for (Lamp lamp: new TreeSet<Lamp>(listOfLamps)){
            sb.append(", " +Collections.frequency(listOfLamps, lamp) + " " +  lamp.toString());
        }
        sb.append(")");
        return  sb.toString();
    }

    public String getAreaDescription(){
        StringBuilder sb = new StringBuilder();
        sb.append("Area = " ).append(area).append(" м2 (usage space - ");
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
        return (title+"\n");
    }
}

