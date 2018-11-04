package buildings.exceptions;

/**
 * Created by Raik Yauheni on 27.10.2018.
 */
public class IllegalRoomException extends IllegalBuildingException {
    public IllegalRoomException() {
    }
    public IllegalRoomException(String s) {
        super(s);
    }
}
