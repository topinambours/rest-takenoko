package core.controllers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMapping;
import pioche.EmptyDeckException;
import pioche.PiocheTuile;
import org.springframework.web.bind.annotation.RestController;
import communication.Container.TuileContainer;

@RestController
@Import(PiocheTuile.class)
public class DeckController {

    private final PiocheTuile pTuile;

    public DeckController(@Qualifier("pioche_depart") PiocheTuile pTuile){
        this.pTuile = pTuile;
    }

    @RequestMapping("/action/piocher")
    public TuileContainer req_pioche() throws EmptyDeckException {
        return new PiocheTuile(pTuile.draw(3)).toContainer();
    }


}
