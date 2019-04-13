package core.controller;

import core.Joueur;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class TurnHandler {

    private final Joueur joueur;

    public TurnHandler(Joueur joueur) {
        this.joueur = joueur;
    }

    @Async
    @Scheduled(fixedRate = 250)
    public void doNotify() {
        if (joueur.myTurn){
            joueur.myTurn = false;
            joueur.turn();
        }
    }
}
