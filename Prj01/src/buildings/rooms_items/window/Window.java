package buildings.rooms_items.window;

import buildings.interfaces.RoomsItems;

/**
 * Created by Raik Yauheni on 31.10.2018.
 */
public class Window implements RoomsItems {
    protected double illuminance;

    public Window() {
    }
    public Window(double illuminance) {
        this.illuminance = illuminance;
    }

    public double getIlluminance() {
        return illuminance;
    }

    public void setIlluminance(double illuminance) {
        this.illuminance = illuminance;
    }
}
