package buildings.interfaces;
/**
 * This interface describes furniture (items of rooms), which can change size.
 *
 * Created by Raik Yauheni on 29.10.2018.
 */
public interface ChangeableUsageSpace extends RoomsItems {
    double getChangedArea();
}
