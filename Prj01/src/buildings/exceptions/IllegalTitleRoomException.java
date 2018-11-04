package buildings.exceptions;

import buildings.room.Room;

/**
 * Created by Raik Yauheni on 31.10.2018.
 */
public class IllegalTitleRoomException extends IllegalRoomException {
    public IllegalTitleRoomException(String s) {
        super(s);
    }
}
