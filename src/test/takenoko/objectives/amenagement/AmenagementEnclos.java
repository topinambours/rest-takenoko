package takenoko.objectives.amenagement;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import takenoko.Game;
import takenoko.joueur.Joueur;
import takenoko.joueur.strategie.StrategieConcrete;
import takenoko.plot.Plot;
import takenoko.util.exceptions.EmptyDeckException;
import takenoko.util.exceptions.NoActionSelectedException;

import static org.junit.Assert.assertEquals;

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


        gameTest.getPlateau().putPlot(plot,1,0);


        joueur.pandaTurn(gameTest);

        assertEquals(1,gameTest.getPlateau().getPlot(1,0).getBambou());




    }

    @Test public void noEnclos() throws EmptyDeckException, NoActionSelectedException {

        plot = new Plot();
        joueur = new Joueur(1,this.strategieConcrete);

        plot.setWater(true);


        gameTest.getPlateau().putPlot(plot,1,0);


        joueur.pandaTurn(gameTest);

        assertEquals(0,gameTest.getPlateau().getPlot(1,0).getBambou());




    }
}
