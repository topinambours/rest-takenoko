package takenoko.tuile;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TuileNotFoundException extends Exception {

    public TuileNotFoundException() {
        super("Tuile Not Found");
    }
}
