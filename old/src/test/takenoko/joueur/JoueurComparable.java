package takenoko.joueur;

import org.junit.Before;
import org.junit.Test;
import takenoko.Plateau;
import takenoko.joueur.strategie.StrategieCoord.StrategieCoordRandom;
import takenoko.joueur.strategie.StrategieIrrig.StrategieIrrigBase;
import takenoko.joueur.strategie.StrategieSansPions;

import static org.junit.Assert.assertTrue;

public class JoueurComparable {
    private Joueur joueur1;
    private Joueur joueur2;
    private Plateau plateau;

    @Before public void JoueurComparable(){
        plateau = new Plateau();
        joueur1 = new Joueur(1, new StrategieSansPions(new StrategieCoordRandom(),new StrategieIrrigBase(plateau)));
        joueur2 = new Joueur(2, new StrategieSansPions(new StrategieCoordRandom(),new StrategieIrrigBase(plateau)));
        joueur1.addScore1();
    }

    @Test public void comparable(){
        assertTrue(joueur1.compareTo(joueur2) > 0);
    }


    @Test public void isUpper(){
        assertTrue(joueur1.isUpper(joueur2));
    }


}
