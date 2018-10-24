package takenoko;

import takenoko.util.exceptions.EmptyDeckException;
import takenoko.util.exceptions.NoActionSelectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

@Component
@ImportResource("classpath*:resources/default-config.xml")
@Import(Game.class)
public class GameStarter {

    @Autowired
    public GameStarter(Game game) throws EmptyDeckException, NoActionSelectedException {
        game.play();
    }

}
