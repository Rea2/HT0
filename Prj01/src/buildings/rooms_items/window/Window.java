package buildings.rooms_items.window;



/**
 * Created by Raik Yauheni on 31.10.2018.
 */
public class Window {
    private double Illuminance;


    public Window(double illuminance) {
        Illuminance = illuminance;
    }

    public double getIlluminance() {
        return Illuminance;
    }

    public void setIlluminance(double illuminance) {
        Illuminance = illuminance;
    }
}
