package Takenoko.Joueur;

import Takenoko.Joueur.Strategie.StrategieConcrete;
import Takenoko.Joueur.Strategie.StrategieCoord.StrategieCoordRandom;
import Takenoko.Joueur.Strategie.StrategieIrrig.StrategieIrrigBase;
import Takenoko.Plateau;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class JoueurComparable {
    private Joueur joueur1;
    private Joueur joueur2;
    private Plateau plateau;

    @Before public void JoueurComparable(){
        plateau = new Plateau();
        joueur1 = new Joueur(1, new StrategieConcrete(new StrategieCoordRandom(),new StrategieIrrigBase(plateau)));
        joueur2 = new Joueur(2, new StrategieConcrete(new StrategieCoordRandom(),new StrategieIrrigBase(plateau)));
        joueur1.addScore1();
    }

    @Test public void comparable(){
        assertTrue(joueur1.compareTo(joueur2) > 0);
    }


    @Test public void isUpper(){
        assertTrue(joueur1.isUpper(joueur2));
    }


}
