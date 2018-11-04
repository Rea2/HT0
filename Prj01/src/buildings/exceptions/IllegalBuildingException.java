package buildings.exceptions;

/**
 * Created by Raik Yauheni on 26.10.2018.
 */
public class IllegalBuildingException extends RuntimeException {
    public IllegalBuildingException() {
    }
    public IllegalBuildingException(String s) {
        super(s);
    }
}

