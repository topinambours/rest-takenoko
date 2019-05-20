package client.controller;

import client.Joueur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class TurnHandler {

    @Autowired
    @Qualifier("joueur_1")
    private final Joueur joueur;

    public TurnHandler(Joueur joueur) {
        this.joueur = joueur;
    }
}