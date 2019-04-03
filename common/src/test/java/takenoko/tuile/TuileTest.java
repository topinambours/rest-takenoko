package takenoko.tuile;

import communication.container.BambouContainer;
import org.junit.Test;
import takenoko.Couleur;

import static org.junit.Assert.*;
import static takenoko.Couleur.VERT;

public class TuileTest {

    Tuile t;

    @Test
    public void poussePossible() {
        t = new Tuile(1, Couleur.BLEU);

        // pas de pousse sur une tuile bleu
        assertFalse(t.poussePossible());


        // On test toute les combinaisons de couleurs / aménagement
        for (Couleur c : Couleur.values()) {
            t = new Tuile(1, c);
            assertFalse(t.poussePossible());

            for (Amenagement a : Amenagement.values()) {
                t = new Tuile(1, c, a);
                if (a != Amenagement.BASSIN) {
                    assertFalse(t.poussePossible());

                    // On irrigue
                    t.irrigate();
                    if (t.getCouleur() != Couleur.BLEU) {
                        assertTrue(t.poussePossible());
                    }else{
                        // peut import son aménagement une tuile bleu ne peut pas recevoir de bambou
                        assertFalse(t.poussePossible());
                    }
                } else {
                    if (t.getCouleur() != Couleur.BLEU) {
                        assertTrue(t.poussePossible());
                    }else{
                        // peut import son aménagement une tuile bleu ne peut pas recevoir de bambon même BASSIN
                        assertFalse(t.poussePossible());
                    }
                }
            }
        }
    }

    @Test
    public void pousserBambou() {
        t = new Tuile(1, Couleur.BLEU);

        // On essaye de faire pousser un bambou sur une tuile bleu
        assertEquals(0, t.getNbBambous());
        t.pousserBambou();
        assertTrue(t.getHaveWater());
        // Aucun bambou n'a poussé
        assertEquals(0, t.getNbBambous());

        // Testons les aménagements pour toute les couleurs

        for (Couleur c : Couleur.values()) {
            if (c != Couleur.BLEU) {
                t = new Tuile(1, c);

                // On essaye de faire pousser un bambou (parcel non irrigé sans aménagement)
                assertEquals(0, t.getNbBambous());
                t.pousserBambou();
                assertFalse(t.getHaveWater());
                // Aucun bambou n'a poussé
                assertEquals(0, t.getNbBambous());

                // On irrigue
                t.irrigate();
                assertEquals(0, t.getNbBambous());
                t.pousserBambou();
                assertTrue(t.getHaveWater());
                // 1 bambou a poussé
                assertEquals(1, t.getNbBambous());



                for (Amenagement a : Amenagement.values()) {
                    t = new Tuile(1, c, a);

                    if (a == Amenagement.BASSIN){
                        assertEquals(0, t.getNbBambous());
                        t.pousserBambou();
                        assertTrue(t.getHaveWater());
                        // 1 bambou a poussé
                        assertEquals(1, t.getNbBambous());
                    }else{

                        assertEquals(0, t.getNbBambous());
                        t.pousserBambou();
                        assertFalse(t.getHaveWater());
                        // Aucun bambou n'a poussé
                        assertEquals(0, t.getNbBambous());

                        // On irrigue
                        t.irrigate();
                        assertEquals(0, t.getNbBambous());
                        t.pousserBambou();
                        assertTrue(t.getHaveWater());

                        if (a == Amenagement.ENGRAIS) {
                            // 2 bambou on poussé
                            assertEquals(2, t.getNbBambous());
                        }else{
                            // 1 bambou a poussé
                            assertEquals(1, t.getNbBambous());
                        }

                    }

                }
            }
        }


    }

    @Test
    public void bambousRangeTest(){
        t = new Tuile(1, VERT);
        // Pas d'aménagement, on irrigue
        t.irrigate();

        assertEquals(0, t.getNbBambous());
        t.pousserBambou();
        assertEquals(1, t.getNbBambous());

        t.pousserBambou();
        assertEquals(2, t.getNbBambous());

        t.pousserBambou();
        assertEquals(3, t.getNbBambous());

        t.pousserBambou();
        assertEquals(4, t.getNbBambous());

        t.pousserBambou();
        assertEquals(4, t.getNbBambous());


        // Autre test avec engrais (x2)
        t = new Tuile(1, VERT, Amenagement.ENGRAIS);
        t.irrigate();
        assertEquals(0, t.getNbBambous());
        t.pousserBambou();
        assertEquals(2, t.getNbBambous());

        t.pousserBambou();
        assertEquals(4, t.getNbBambous());

        t.pousserBambou();
        assertEquals(4, t.getNbBambous());

    }

    @Test
    public void enleverBambou() {

        t = new Tuile(1, VERT);
        // Pas d'aménagement, on irrigue
        t.irrigate();

        assertEquals(0, t.getNbBambous());
        t.pousserBambou();
        assertEquals(1, t.getNbBambous());

        assertEquals(new BambouContainer(VERT, 1) , t.enleverBambou());
        assertEquals(0, t.getNbBambous());

        // On essaye d'enlever un bambou en plus
        assertEquals(new BambouContainer(VERT, 0), t.enleverBambou());
        assertEquals(0, t.getNbBambous());

    }
}