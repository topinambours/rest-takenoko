package server.takenoko.pioche;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmptyDeckException extends Exception {

    /**
     * Renvoie une exception si le Container est vide et qu'un joueur
     * essaye de piocher une parcelle
     */
    public EmptyDeckException() {
        super("Draw on empty Container");
    }

}