package Takenoko.Plot;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CouleurTest {
    private Plot plot;
    private Couleur couleur1;
    private Couleur couleur2;

    @Before public void CouleurTest(){
        CoordAxial coordAxial = new CoordAxial(1,0);
        couleur1 = Couleur.JAUNE;
        couleur2 = Couleur.VERT;

        plot = new Plot(coordAxial,couleur1);
    }

    @Test public void test(){
        assertEquals(couleur1,plot.getCouleur());

        plot.setCouleur(couleur2);
        assertEquals(couleur2,plot.getCouleur());
    }
}
