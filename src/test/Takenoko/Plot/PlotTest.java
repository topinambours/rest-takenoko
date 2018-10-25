package takenoko.Plot;

import org.junit.Test;
import takenoko.objectives.amenagement.Amenagement;
import takenoko.properties.Couleur;

import java.util.Random;

import static org.junit.Assert.*;

public class PlotTest {

    @Test
    public void getq() {
        Plot p = new Plot(10,15);
        assertEquals(10, p.getq());
    }

    @Test
    public void getr() {
        Plot p = new Plot(10,15);
        assertEquals(15, p.getr());
    }

    @Test
    public void getCoord(){
        Plot p = new Plot(new CoordAxial(0,0));
        Plot p2 = new Plot(new CoordAxial(1,1));
        assertEquals(new CoordAxial(0,0), p.getCoord());
        assertEquals(new CoordAxial(1,1), p2.getCoord());
    }

    @Test
    public void setCoord() {
        Plot p = new Plot(10,15);
        assertEquals(10, p.getq());
        assertEquals(15, p.getr());

        p.setCoord(15,10);
        assertEquals(15, p.getq());
        assertEquals(10, p.getr());
    }

    @Test
    public void haveWater(){
        Plot p = new Plot(0, 0);
        assertFalse(p.haveWater());
        p.setWater(true);
        assertTrue(p.haveWater());

        Plot p2 = new Plot(10,50,Couleur.JAUNE, Amenagement.BASSIN);
        assertTrue(p2.haveWater());

    }

    @Test
    public void setWater(){
        Plot p = new Plot(0, 0);
        p.setWater(true);
        assertTrue(p.haveWater());
        p.setWater(false);
        assertFalse(p.haveWater());
    }

    @Test public void haveBambouWater(){
        Plot p = new Plot();
        assertFalse(p.haveBambou());
        p.setWater(true);

        p.pousserBambou();

        assertTrue(p.haveBambou());
        assertEquals(1,p.getBambou());
    }

    @Test public void getBambou(){
        Plot p = new Plot();
        assertEquals(0, p.getBambou());
        p.setWater(true);
        p.pousserBambou();
        assertEquals(1,p.getBambou());
    }

    /**
     * On test 1000 fois avec un nombre aléatoire de bambous à placer
     * Placement des bambous, suppression des bambous.
     * On vérifie le nombre de bambous collectés
     */
    @Test public void removeBambou(){
        Plot p = new Plot();
        p.setWater(true);
        Random rd = new Random();
        for (int i = 0; i < 1000; i++){
            int amount = rd.nextInt(4);
            for (int j = 0; j < amount; j++){
                assertTrue(p.pousserBambou());
            }
            assertEquals(amount, p.getBambou());
            assertEquals(amount, p.removeAllBambou());
            assertEquals(0, p.removeAllBambou());
            assertFalse(p.haveBambou());
        }

        p.pousserBambou();
        p.removeBambou(5000);
        assertFalse(p.haveBambou());
    }

    /**
     * Sachant qu'une parcelle ne peut contenir que 4 sections de bambous maximum si irriguée,
     * on test la pousse sur une parcelle non iriguée. On irrigue et on ajoute des sections jusqu'au maximum admis.
     * On essaye alors d'ajouter une section sur une parcelle pleine.
     */
    @Test public void pousserBambou(){
        Plot p = new Plot();
        // pas de pousse si non irrigué
        assertFalse(p.pousserBambou());

        // On irrigue et on fait pousser 4 bambous
        p.setWater(true);
        for (int i = 1; i <= 4 ; i++){
            assertTrue(p.pousserBambou());
            assertEquals(i, p.getBambou());
        }
        // impossible de faire pousser sur une parcelle pleine.
        assertFalse(p.pousserBambou());


        // Test de l'aménagement engrais (x2 bambou)
        p = new Plot(50,50, Couleur.VERT, Amenagement.ENGRAIS);
        p.setWater(true);

        assertEquals(0, p.getBambou());

        assertTrue(p.pousserBambou());

        assertEquals(2, p.getBambou());

    }

    @Test
    public void getSetAmenagementCouleur() {
        Plot p = new Plot(0,0, Couleur.VERT);
        assertEquals(Amenagement.NON, p.getAmenagement());

        p.setAmenagement(Amenagement.ENGRAIS);
        assertEquals(Couleur.VERT, p.getCouleur());
        p.setCouleur(Couleur.ROSE);
        assertEquals(Couleur.ROSE, p.getCouleur());

        assertEquals(Amenagement.ENGRAIS, p.getAmenagement());


    }

    @Test
    public void equalsTest(){
        Plot p = new Plot(0,0,Couleur.BLEU, Amenagement.ENGRAIS);
        Plot p2 = new Plot(0,0,Couleur.BLEU, Amenagement.ENCLOS);
        assertFalse(p.equals(p2));

        assertNotEquals(p.hashCode(), p2.hashCode());
    }
}