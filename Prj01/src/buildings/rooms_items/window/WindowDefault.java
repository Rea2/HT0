package buildings.rooms_items.window;

import buildings.building.Constants;
import buildings.interfaces.RoomsItems;

/**
 * Created by Raik Yauheni on 31.10.2018.
 */
public class WindowDefault extends Window implements RoomsItems {

    private WindowDefault() {
        super(Constants.ILLUMINANCE_OF_THE_WINDOW);
    }

    @Override
    public void setIlluminance(double illuminance) {
        System.out.println("You can not change illuminance defaultWindows. It  equals - " +Constants.ILLUMINANCE_OF_THE_WINDOW);
    }
}
