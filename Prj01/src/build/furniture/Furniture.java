package build.furniture;


import build.building.PlaceableRoomItem;

/**
 * Created by Raik Yauheni on 26.10.2018.
 */
public abstract class Furniture implements Comparable<Furniture>, PlaceableRoomItem {

    private  int id;
    private static int countFurniture;
    private double area;
    private String title;

    public Furniture(String title, double area) {
        id = ++countFurniture;
        this.title = title;
        this.area = area;
    }

    public int getId() {
        return id;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double requiredArea) {
        this.area = requiredArea;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }


    @Override
    public int compareTo(Furniture furniture) {
        return this.title.compareTo(furniture.getTitle());
    }

    @Override
    public String toString() {
        return title + " (area " + area + " m2)";
    }

}
