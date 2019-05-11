package takenoko;

import org.junit.Test;
import takenoko.irrigation.CoordIrrig;
import takenoko.irrigation.Orient;
import takenoko.tuile.Amenagement;
import takenoko.tuile.CoordAxial;
import takenoko.tuile.Tuile;
import takenoko.tuile.TuileNotFoundException;

import java.util.List;

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
        p.getTuileAtCoord(new CoordAxial(1,-1)).pousserBambou(); //Todo : devra être remove quand poussage auto

        // Pas de bambou donc bleu
        assertEquals(Couleur.VERT, p.movePanda(new CoordAxial(1,-1)));
        assertEquals(new CoordAxial(1,-1), p.posPanda());

        assertEquals(Couleur.BLEU, p.movePanda(new CoordAxial(0,0)));

        assertEquals(Couleur.VERT, p.movePanda(new CoordAxial(1,-1)));
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


    @Test
    public void poseTuileTest(){
        Plateau p = new Plateau();
        p = p.plateau_depart();


        p.poserTuile(new CoordAxial(1,0), new Tuile(1, Couleur.BLEU));
        p.poserTuile(new CoordAxial(-1,0), new Tuile(1,Couleur.VERT));

        List<CoordAxial> legalMovesPanda = p.computePandaLegalPositions();

        assertTrue(legalMovesPanda.contains(new CoordAxial(1,0)));
        assertTrue(legalMovesPanda.contains(new CoordAxial(-1,0)));

        p.poserTuile(new CoordAxial(2,0), new Tuile(1,Couleur.VERT));
    }


    @Test
    public void equalsHashTest(){
        Plateau p1 = new Plateau().plateau_depart();
        Plateau p2 = new Plateau().plateau_depart();

        assertEquals(p1, p2);
        assertEquals(p1.hashMD5(), p2.hashMD5());

        p1.poserTuile(new CoordAxial(1,0), new Tuile(1, Couleur.VERT, Amenagement.NONE));

        assertNotEquals(p1, p2);
        assertNotEquals(p1.hashMD5(), p2.hashMD5());

        p2.poserTuile(new CoordAxial(1,0), new Tuile(1, Couleur.VERT, Amenagement.NONE));

        assertEquals(p1,p2);
        assertEquals(p1.hashMD5(), p2.hashMD5());

        // Let's move panda on p1

        p1.movePanda(new CoordAxial(1,0));

        assertNotEquals(p1,p2);
        assertNotEquals(p1.hashMD5(), p2.hashMD5());

        p2.movePanda(new CoordAxial(1,0));
        assertEquals(p1,p2);
        assertEquals(p1.hashMD5(), p2.hashMD5());

        // Place new plot with differents id

        p1.poserTuile(new CoordAxial(-1,0), new Tuile(2, Couleur.VERT, Amenagement.NONE));

        assertNotEquals(p1, p2);
        assertNotEquals(p1.hashMD5(), p2.hashMD5());

        p2.poserTuile(new CoordAxial(-1,0), new Tuile(3, Couleur.VERT, Amenagement.NONE));

        assertNotEquals(p1,p2);
        assertNotEquals(p1.hashMD5(), p2.hashMD5());

    }
    @Test
    public void hasStraightPath() {
        Plateau p = new Plateau().plateau_depart();
        Tuile inLine = new Tuile(1, Couleur.VERT, Amenagement.NONE);
        Tuile inLineNotStraight = new Tuile(2, Couleur.VERT, Amenagement.NONE);
        p.poserTuile(new CoordAxial(1,0), inLine);
        p.poserTuile(new CoordAxial(3,0), inLineNotStraight);

        assertTrue(p.hasStraightPath(new CoordAxial(0,0), p.getCoordFromTuile(inLine)));

        assertFalse(p.hasStraightPath(new CoordAxial(0,0), p.getCoordFromTuile(inLineNotStraight)));

    }

    @Test
    public void getLinePlots() {
        Plateau p = new Plateau().plateau_depart();
        Tuile inLine = new Tuile(1, Couleur.VERT);
        Tuile inLine2 = new Tuile(2, Couleur.VERT);
        Tuile notInLine = new Tuile(3, Couleur.VERT);
        p.poserTuile(new CoordAxial(-1,1), inLine);
        p.poserTuile(new CoordAxial(-2,2),inLine2);
        p.poserTuile(new CoordAxial(-1,2),notInLine);

        assertTrue(p.getLineCoord(new CoordAxial(0,0)).contains(p.getCoordFromTuile(inLine)));
        assertTrue(p.getLineCoord(new CoordAxial(0,0)).contains(p.getCoordFromTuile(inLine2)));
        assertFalse(p.getLineCoord(new CoordAxial(0,0)).contains(p.getCoordFromTuile(notInLine)));
    }

}