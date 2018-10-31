package buildings.exceptions;


/**
 * Created by Raik Yauheni on 26.10.2018.
 */
public class IlluminateTooMuchException extends IllegalRoomException {
    public IlluminateTooMuchException() {
    }
    public IlluminateTooMuchException(String s, IllegalRoomException e) {
        super(s, e);
    }
}
