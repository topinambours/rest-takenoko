package Takenoko.Plot;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CouleurTest {
    private Plot plot;

    @Before public void CouleurTest(){
        plot = new Plot();
    }

    @Test public void setCouleur(){
        plot.setCouleur(Couleur.JAUNE);
        assertTrue(plot.getCouleur().toString() == "Jaune");
    }
}
