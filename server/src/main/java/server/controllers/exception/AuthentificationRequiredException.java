package server.controllers.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AuthentificationRequiredException extends Exception {
    public AuthentificationRequiredException() {
        super("Error : Authentification Required");
    }

    public AuthentificationRequiredException(String message) {
        super(message);
    }
}
