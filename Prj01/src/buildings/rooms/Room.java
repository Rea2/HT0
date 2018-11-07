package buildings.rooms;

import buildings.building.Constants;
import buildings.exceptions.*;
import buildings.interfaces.RoomsItems;
import buildings.rooms_items.lamp.Lamp;
import buildings.interfaces.ChangeableUsageSpace;
import buildings.rooms_items.furniture.Furniture;
import buildings.rooms_items.window.Window;
import buildings.rooms_items.window.WindowDefault;
import java.util.*;

/**
 * Created by Raik Yauheni on 26.10.2018.
 */
public class Room  {
    private static int countRooms = 0;
    private int id;
    private double area;
    private boolean validated;
    private String title;
    private final List<Furniture> furnitures = new ArrayList<>();
    private final List<Lamp> lamps =  new ArrayList<>();
    private final List<Window> windows =  new ArrayList<>();
    /** Constructors*/
    public Room(String title, double area, int numberOfDefaultWindows) throws IllegalArgumentException {
        if (title.length() < 2) throw new IllegalArgumentException("Rooms did not created. Rooms title has to consist at least two characters ");
        id = ++countRooms;
        if (area < Constants.MIN_ROOM_AREA) {
           throw new IllegalArgumentException("Rooms did not created. Rooms area has to be at least "
                   + Constants.MIN_ROOM_AREA + "m2");
        } else {
            this.area = area;
        }
        for(int i = 0; i < numberOfDefaultWindows; i++) {
            windows.add(WindowDefault.getInstance());
        }
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

    public List<Window> getWindows() {
        return windows;
    }

    public List<Furniture> getFurnitures() {
        return furnitures;
    }

    public List<Lamp> getLamps() {
        return lamps;
    }

    /** Methods */
    public double getIlluminance() {
        double result = 0;
        for(Lamp lamp : lamps) {
            result+=lamp.getLuminance();
        }
        for(Window window : windows){
            result+=window.getIlluminance();
        }
        return result;
    }

    public double getUsageSpace() {
        double sumAreas = 0;
        for (Furniture furniture : furnitures) {
            sumAreas+=furniture.getArea();
        }
        return sumAreas;
    }

    public double getUsageSpaceChanged(){
        double sumChangedAreas = 0;
        for (Furniture furniture : furnitures) {
            if (furniture instanceof ChangeableUsageSpace) {
                sumChangedAreas+=((ChangeableUsageSpace) furniture).getChangedArea();
            } else sumChangedAreas += furniture.getArea();
        }
        return sumChangedAreas;
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
            throw new SpaceUsageTooMuchException();
        } else return true;
    }

    public boolean add(RoomsItems item) {
        if(item instanceof Lamp) {
            return  add((Lamp) item);
        } else  if (item instanceof Furniture) {
            return add((Furniture) item );
        } else {
            System.err.println("Object did not add. This type of objects is not supported.");
            return false;
        }
    }

    private boolean add(Lamp lamp)  {
        boolean result = lamps.add(lamp);
        try {
            isIlluminanceCorrect();
        } catch (IllegalRoomException e) {
            validated = false;
            System.err.println("Room " + title + "(ID " + id + "): illuminance is too much");
        } finally {
            return result;
        }
    }

    private boolean add(Furniture furniture) {
        boolean result = furnitures.add(furniture);
        try {
            isUsageSpaceCorrect();
        } catch (SpaceUsageTooMuchException e) {
            validated = false;
            System.err.println("Room " + title + "(ID " + id + "): Space usage is too much");
        } finally {
            return result;
        }
    }

    public boolean remove(RoomsItems item) {
        if(item instanceof Lamp) {
            return lamps.remove((Lamp) item);
        } else  if (item instanceof Furniture) {
            return furnitures.remove((Furniture) item );
        } else {
            System.out.println("Object did not remove. This type of objects is not allowed for rooms.");
            return false;
        }
    }

    private boolean remove(Lamp lamp)  {
        boolean result = lamps.remove(lamp);
        try {
            isIlluminanceCorrect();
        } catch (IllegalRoomException e) {
            validated = false;
            System.err.println("Room " + title + "(ID " + id + "): illuminance is not suitable");
        } finally {
            return result;
        }
    }

    private boolean remove(Furniture furniture) {
        boolean result = furnitures.remove(furniture);
        try {
            isUsageSpaceCorrect();
        } catch (IllegalRoomException e) {
            validated = false;
            System.err.println("Room " + title + "(ID " + id + "): Space usage is still too much");
        } finally {
            return result;
        }
    }

    public void validate() throws SpaceUsageTooMuchException, IlluminateTooLittleException, IlluminateTooMuchException {
        if(isIlluminanceCorrect() && isUsageSpaceCorrect()) {
            validated = true;
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
            if (furnitures.size() ==0) {
                result.append(" None   " + "\n");
            } else  {
                result.append("\n");
                for (Furniture furniture : furnitures) {
                    result.append(Constants.TAB + Constants.TAB + furniture.toString() + "\n");
                }
            }
        return result.toString();
    }

    public String getIlluminanceFullDescription(){
        StringBuilder sb = new StringBuilder ("illuminance " + getIlluminance() + " lk (" +
                windows.size() + " windows("+Constants.ILLUMINANCE_OF_THE_DEFAULT_WINDOW +" lk)");
        for (Lamp lamp: new TreeSet<Lamp>(lamps)){
            sb.append(", " +Collections.frequency(lamps, lamp) + " " +  lamp.toString());
        }
        sb.append(")");
        return  sb.toString();
    }

    public String getAreaDescription(){
        StringBuilder sb = new StringBuilder();
        sb.append("Area = " ).append(area).append(" м2, ");
        if(Math.max(getUsageSpaceChanged(),getUsageSpace()) > area ) {
            return sb.append("the furniture does not fit in the room. Lack of space  - "+
                    + (Math.max(getUsageSpaceChanged(),getUsageSpace()) - area) + " м2\n").toString();
        }else {
            sb.append("usage space - ");
        }
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

