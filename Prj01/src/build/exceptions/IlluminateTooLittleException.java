package build.exceptions;

/**
 * Created by Raik Yauheni on 27.10.2018.
 */
public class IlluminateTooLittleException extends IllegalRoomException {

    public IlluminateTooLittleException() {
    }

    public IlluminateTooLittleException(String s, IllegalRoomException e) {
        super(s, e);
    }
}
