package build.lamp;

import build.building.PlaceableRoomItem;

/**
 * Created by Raik Yauheni on 26.10.2018.
 */
public class Lamp  implements Comparable <Lamp>, PlaceableRoomItem {
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

