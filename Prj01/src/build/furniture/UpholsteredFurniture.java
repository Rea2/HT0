package build.furniture;

import build.building.ChangeableArea;

/**
 * Created by Raik Yauheni on 28.10.2018.
 */
public abstract class UpholsteredFurniture extends Furniture implements ChangeableArea {
    private double changedArea;

    public UpholsteredFurniture(String title, double area) {
        super(title, area);
    }
    public UpholsteredFurniture(String title, double area, double changedArea) {
        this(title, area);
        this.changedArea = changedArea;
    }

    public double getChangedArea() {
         return changedArea;
    }

    public void setChangedArea(double changedArea) {
        this.changedArea = changedArea;
    }

    @Override
    public String toString() {
            return this.getTitle() + " (area from " + this.getArea() + " m2 to " + this.getChangedArea() +" m2)";
    }
}
