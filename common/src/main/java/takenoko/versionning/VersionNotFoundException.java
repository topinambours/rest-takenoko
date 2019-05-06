package takenoko.versionning;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class VersionNotFoundException extends Exception {
    public VersionNotFoundException() {
        super("Error : Version not found");
    }
}
