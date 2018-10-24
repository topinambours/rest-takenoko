package Takenoko.Objectives.Amenagement;


import Takenoko.Game;
import Takenoko.Joueur.Joueur;
import Takenoko.Joueur.Strategie.StrategieConcrete;
import Takenoko.Joueur.Strategie.StrategiePanda.StrategiePandaBasique;
import Takenoko.Plot.Plot;
import Takenoko.Util.Exceptions.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(StrategieConcrete.class)
public class AmenagementEnclos {

    @Autowired
    private Game gameTest;

    private Joueur joueur;
    private Plot plot;

    @Autowired
    @Qualifier("createStratRandom")
    private StrategieConcrete strategieConcrete;



    @Test public void enclos() throws EmptyDeckException, NoActionSelectedException {

        plot = new Plot();
        joueur = new Joueur(1,this.strategieConcrete);


        plot.setAmenagement(Amenagement.ENCLOS);
        plot.setWater(true);
        plot.pousserBambou();


        gameTest.getPlateau().putPlot(plot,1,0);


        joueur.pandaTurn(gameTest);


        System.out.println(gameTest.getPlateau().getPlot(1,0).getBambou());
        assertEquals(1,gameTest.getPlateau().getPlot(1,0).getBambou());




    }

    @Test public void noEnclos() throws EmptyDeckException, NoActionSelectedException {

        plot = new Plot();
        joueur = new Joueur(1,this.strategieConcrete);

        plot.setWater(true);
        plot.pousserBambou();


        gameTest.getPlateau().putPlot(plot,1,0);


        joueur.pandaTurn(gameTest);


        System.out.println(gameTest.getPlateau().getPlot(1,0).getBambou());
        assertEquals(0,gameTest.getPlateau().getPlot(1,0).getBambou());




    }
}
