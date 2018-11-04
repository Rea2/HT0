package buildings.rooms_items.furniture;



/**
 * Created by Raik Yauheni on 28.10.2018.
 */
public class Sofa extends UpholsteredFurniture {
    int caparsityOfPerson;

    public Sofa(String title, double area) {
        super(title, area);
        caparsityOfPerson =2;
    }
    public Sofa(String title, double area, double changedArea) {
        super(title, area, changedArea);
        caparsityOfPerson =2;
    }
    public Sofa(String title, double area, double changedArea, int caparsityOfPerson) {
        super(title, area, changedArea);
        this.caparsityOfPerson = caparsityOfPerson;
    }


    public int getCaparsityOfPerson() {
        return caparsityOfPerson;
    }

    public void setCaparsityOfPerson(int caparsityOfPerson) {
        this.caparsityOfPerson = caparsityOfPerson;
    }
}
