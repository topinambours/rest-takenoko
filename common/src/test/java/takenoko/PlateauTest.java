package takenoko;

import org.junit.Test;
import takenoko.irrigation.CoordIrrig;
import takenoko.irrigation.Orient;
import takenoko.tuile.CoordAxial;
import takenoko.tuile.Tuile;
import takenoko.tuile.TuileNotFoundException;

import static org.junit.Assert.*;

public class PlateauTest {

    @Test
    public void toString1() {
        Plateau p = new Plateau();
    }

    @Test
    public void testInitIrrigation(){
        Plateau p = new Plateau();
        p = p.plateau_depart();

        assertEquals(p.irrigationsList().size(),6);

        p.poserTuile(new CoordAxial(0,1),new Tuile(1,Couleur.ROSE));
        assertEquals(p.getTuileAtCoord(new CoordAxial(0,1)).getHaveWater(),true);
    }

    @Test
    public void testAddIrrigation(){
        Plateau p = new Plateau();
        p.poserTuile(new CoordAxial(0,1),new Tuile(1,Couleur.ROSE));

        assertEquals(p.getTuileAtCoord(new CoordAxial(0,1)).getHaveWater(),true);

        p.poserTuile(new CoordAxial(-1,2),new Tuile(2,Couleur.ROSE));

        assertEquals(p.getTuileAtCoord(new CoordAxial(-1,2)).getHaveWater(),false);

        p.addIrrigation(new CoordIrrig(-1,2, Orient.S));

        assertTrue(p.checkPlotWater(new CoordAxial(-1,2)));
        assertEquals(p.getTuileAtCoord(new CoordAxial(-1,2)).getHaveWater(),true);
    }

    @Test
    public void movePanda() {
        Plateau p = new Plateau();
        p = p.plateau_depart();
        assertEquals(new CoordAxial(0,0), p.posPanda());

        assertEquals(Couleur.BLEU, p.movePanda(new CoordAxial(1,-1)));
        assertEquals(new CoordAxial(0,0), p.posPanda());

        p.poserTuile(new CoordAxial(1,-1),new Tuile(1, Couleur.VERT));
        p.getTuile(new CoordAxial(1,-1)).pousserBambou(); //Todo : devra être remove quand poussage auto

        // Pas de bambou donc bleu
        assertEquals(Couleur.VERT, p.movePanda(new CoordAxial(1,-1)));
        assertEquals(new CoordAxial(1,-1), p.posPanda());

        assertEquals(Couleur.BLEU, p.movePanda(new CoordAxial(0,0)));

        assertEquals(Couleur.BLEU, p.movePanda(new CoordAxial(1,-1)));
    }

    @Test
    public void testIdToTuile() throws TuileNotFoundException {
        Plateau p = new Plateau();
        p = p.plateau_depart();

        assertEquals(p.getTuileFromId(-1),p.getTuileAtCoord(new CoordAxial(0,0)));

        Tuile tuile = new Tuile(1,Couleur.JAUNE);

        p.poserTuile(new CoordAxial(1,0),tuile);

        assertEquals(p.getTuileFromId(1),tuile);
    }


}