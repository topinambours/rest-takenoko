package Takenoko;

import Takenoko.Util.Exceptions.EmptyDeckException;
import Takenoko.Util.Exceptions.NoActionSelectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Component
@Import(Game.class)
public class GameStarter {

    @Autowired
    public GameStarter(Game game) throws EmptyDeckException, NoActionSelectedException {
        game.play();
    }

}
