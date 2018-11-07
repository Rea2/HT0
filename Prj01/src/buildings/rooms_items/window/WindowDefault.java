package buildings.rooms_items.window;

import buildings.building.Constants;
import buildings.interfaces.RoomsItems;

/**
 * Created by Raik Yauheni on 31.10.2018.
 */
public class WindowDefault extends Window {
    private static WindowDefault windowDefault = new WindowDefault();

    private  WindowDefault() {
        illuminance =  Constants.ILLUMINANCE_OF_THE_DEFAULT_WINDOW;
    }

    public static WindowDefault getInstance(){
       return windowDefault;
    }

    @Override
    public void setIlluminance(double illuminance) {
        System.err.println("You can not change illuminance of defaultWindows. It equals - "
                + Constants.ILLUMINANCE_OF_THE_DEFAULT_WINDOW + " lk");
    }


}
