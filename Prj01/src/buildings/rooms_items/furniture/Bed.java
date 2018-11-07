package buildings.rooms_items.furniture;

/**
 * Created by Raik Yauheni on 28.10.2018.
 */
public class Bed extends Furniture {
    protected BedTypes type;

    public Bed(String title, double area) {
        super(title, area);
    }

    public Bed(String title, double area, BedTypes type) {
        super(title, area);
        this.type = type;
    }

    public BedTypes getType() {
        return type;
    }

    public void setType(BedTypes type) {
        this.type = type;
    }
}



