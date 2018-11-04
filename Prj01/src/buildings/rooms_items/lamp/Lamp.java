package buildings.rooms_items.lamp;

import buildings.interfaces.RoomsItems;

/**
 * Created by Raik Yauheni on 26.10.2018.
 */
public class Lamp  implements Comparable <Lamp>, RoomsItems {
    private int luminance;

    public Lamp(int luminance) {
        this.luminance = luminance;
    }

    public int getLuminance() {
        return luminance;
    }

    @Override
    public String toString() {
        return "lamp " + luminance + " lk";
    }


    @Override
    public int compareTo(Lamp o) {
        return this.getLuminance() - o.getLuminance();
    }
}

