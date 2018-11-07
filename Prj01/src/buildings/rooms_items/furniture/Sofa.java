package buildings.rooms_items.furniture;
/**
 * Created by Raik Yauheni on 28.10.2018.
 */
public class Sofa extends UpholsteredFurniture {
    protected int capacityOfPersonOfPerson;

    public Sofa(String title, double area) {
        super(title, area);
        capacityOfPersonOfPerson =2;
    }

    public Sofa(String title, double area, double changedArea) {
        super(title, area, changedArea);
        capacityOfPersonOfPerson =2;
    }
    public Sofa(String title, double area, double changedArea, int capacityOfPerson) {
        super(title, area, changedArea);
        this.capacityOfPersonOfPerson = capacityOfPerson;
    }

    public int getCapacityOfPersonOfPerson() {
        return capacityOfPersonOfPerson;
    }

    public void setCapasityOfPerson(int caparsityOfPerson) {
        this.capacityOfPersonOfPerson = caparsityOfPerson;
    }
}
