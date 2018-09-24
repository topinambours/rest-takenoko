package Takenoko.Joueur;

import Takenoko.Joueur.Strategie.StrategieRandom;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class JoueurComparable {
    private Joueur joueur1;
    private Joueur joueur2;

    @Before public void JoueurComparable(){
        joueur1 = new Joueur(1,new StrategieRandom());
        joueur2 = new Joueur(2,new StrategieRandom());
        joueur1.addScore1();
    }

    @Test public void comparable(){
        assertTrue(joueur1.compareTo(joueur2) > 0);
    }


    @Test public void isUpper(){
        assertTrue(joueur1.isUpper(joueur2));
    }


}
