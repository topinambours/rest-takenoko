package takenoko;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import takenoko.irrigation.CoordIrrig;
import takenoko.irrigation.Orient;
import takenoko.joueur.Joueur;
import takenoko.joueur.strategie.StrategieCoord.StrategieCoordRandom;
import takenoko.joueur.strategie.StrategieIrrig.StrategieIrrigBase;
import takenoko.joueur.strategie.StrategieSansPions;
import takenoko.objectives.amenagement.Amenagement;
import takenoko.plot.CoordAxial;
import takenoko.plot.Plot;
import takenoko.properties.Couleur;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlateauTest {

    @Autowired
    @Qualifier("plateauTakenoko")
    Plateau p;

    @Test
    public void isMotifInAll(){

        p.putPlot(new Plot(Couleur.VERT), 0, 1);
        p.getPlot(0,1).pousserBambou();
        p.getPlot(0,1).pousserBambou();
        assertFalse(p.isMotifInAll(Couleur.VERT, 1));
        p.getPlot(0,1).pousserBambou();
        assertTrue(p.isMotifInAll(Couleur.VERT, 1));

        p.putPlot(new Plot(Couleur.JAUNE), 1, 1);
        p.getPlot(1,1).setWater(true);
        p.getPlot(1,1).pousserBambou();
        p.getPlot(1,1).pousserBambou();
        p.getPlot(1,1).pousserBambou();

        p.putPlot(new Plot(Couleur.JAUNE), -1, 1);
        p.getPlot(-1,1).setWater(true);
        p.getPlot(-1,1).pousserBambou();

        assertFalse(p.isMotifInAll(Couleur.JAUNE, 2));
        p.getPlot(-1,1).pousserBambou();

        assertTrue(p.isMotifInAll(Couleur.JAUNE, 2));

        p.putPlot(new Plot(Couleur.JAUNE), 1, 2);
        p.getPlot(1,2).setWater(true);
        p.getPlot(1,2).pousserBambou();
        p.getPlot(1,2).pousserBambou();
        p.getPlot(1,2).pousserBambou();

        assertTrue(p.isMotifInAll(Couleur.JAUNE, 3));


        p.putPlot(new Plot(Couleur.JAUNE), 1, 3);
        p.getPlot(1,3).setWater(true);
        p.getPlot(1,3).pousserBambou();
        p.getPlot(1,3).pousserBambou();
        assertFalse(p.isMotifInAll(Couleur.JAUNE, 4));
        p.getPlot(1,3).pousserBambou();

        assertTrue(p.isMotifInAll(Couleur.JAUNE, 4));

        assertFalse(p.isMotifInAll(Couleur.JAUNE, 6));

    }

    @Test
    public void getPosPanda() {
    }

    @Test
    public void getPosJardinier() {
    }

    @Test
    public void getCanalIrrigation() {
    }

    @Test
    public void removeCanalIrrigation() {
    }

    @Test
    public void getPlot() {
    }

    @Test
    public void getPlot1() {
    }

    @Test
    public void getLastPlot() {
    }

    @Test
    public void putPlot() {
    }

    @Test
    public void getIrrigations() {
    }

    @Test
    public void addIrrigation() {
    }

    @Test
    public void getNeighbors() {
    }

    @Test
    public void legalPositions() {
    }

    @Test
    public void nbAdajcent() {
    }

    @Test
    public void legalIrrigPositions() {
    }

    @Test
    public void getPlotsFromIrig() {
    }

    @Test
    public void isIrrigPositionLegal() {
    }

    @Test
    public void checkPlotWater() {

        assertTrue(p.checkPlotWater(new CoordAxial(-1,1)));

        assertFalse(p.checkPlotWater(new CoordAxial(-1,2)));

        p.addIrrigation(new CoordIrrig(-1,2, Orient.S));

        assertTrue(p.checkPlotWater(new CoordAxial(-1,2)));

    }

    @Test
    public void hasStraightPath() {
        Plot inLine = new Plot(-1,1, Couleur.VERT);
        Plot inLineNotStraight = new Plot(-3,3, Couleur.VERT);
        p.putPlot(inLine);
        p.putPlot(inLineNotStraight);

        assertTrue(p.hasStraightPath(new CoordAxial(0,0), inLine.getCoord()));

        assertFalse(p.hasStraightPath(new CoordAxial(0,0), inLineNotStraight.getCoord()));

    }

    @Test
    public void movePanda() {
        assertEquals(new CoordAxial(0,0), p.getPosPanda());

        assertEquals(Couleur.BLEU, p.movePanda(new CoordAxial(1,-1)));
        assertEquals(new CoordAxial(0,0), p.getPosPanda());

        p.putPlot(new Plot(1,-1, Couleur.VERT));

        // Pas de bambou donc bleu
        assertEquals(Couleur.VERT, p.movePanda(new CoordAxial(1,-1)));
        assertEquals(new CoordAxial(1,-1), p.getPosPanda());

        assertEquals(Couleur.BLEU, p.movePanda(new CoordAxial(0,0)));

        assertEquals(Couleur.BLEU, p.movePanda(new CoordAxial(1,-1)));
    }

    @Test
    public void moveJardinier() {
        assertEquals(new CoordAxial(0,0), p.getPosJardinier());

        assertFalse(p.moveJardinier(new CoordAxial(1,-1)));
        assertEquals(new CoordAxial(0,0), p.getPosJardinier());

        p.putPlot(new Plot(1,-1, Couleur.VERT));

        assertTrue(p.moveJardinier(new CoordAxial(1,-1)));
        assertEquals(new CoordAxial(1,-1), p.getPosJardinier());

        p.putPlot(new Plot(-1,1, Couleur.JAUNE, Amenagement.ENGRAIS));

        assertEquals(1, p.getPlot(-1,1).getBambou());

        assertTrue(p.moveJardinier(new CoordAxial(-1,1)));

        assertEquals(3, p.getPlot(-1,1).getBambou());
    }

    @Test
    public void neighborColor() {
        Plot baseColor = new Plot(-1,1, Couleur.VERT);
        Plot sameColor = new Plot(-2,2, Couleur.VERT);
        Plot notSame = new Plot(-1,2, Couleur.JAUNE);
        p.putPlot(baseColor);
        p.putPlot(sameColor);
        p.putPlot(notSame);

        assertTrue(p.neighborColor(new CoordAxial(0,0)).isEmpty());

        assertTrue(p.neighborColor(new CoordAxial(-1,1)).contains(sameColor.getCoord()));
        assertFalse(p.neighborColor(new CoordAxial(-1,1)).contains(notSame.getCoord()));
    }

    @Test
    public void getLinePlots() {

        Plot inLine = new Plot(-1,1, Couleur.VERT);
        Plot inLine2 = new Plot(-2,2, Couleur.VERT);
        Plot notInLine = new Plot(-1,2, Couleur.VERT);
        p.putPlot(inLine);
        p.putPlot(inLine2);
        p.putPlot(notInLine);

        assertTrue(p.getLinePlots(new CoordAxial(0,0)).contains(inLine));
        assertTrue(p.getLinePlots(new CoordAxial(0,0)).contains(inLine2));
        assertFalse(p.getLinePlots(new CoordAxial(0,0)).contains(notInLine));
    }

    @Test
    public void plateauTakenoko() {

        assertEquals(new CoordAxial(0,0), p.getPosPanda());
        assertEquals(new CoordAxial(0,0), p.getPosJardinier());

        assertEquals(6, p.getIrrigations().size());
        assertEquals(20, p.getCanalIrrigation());

        assertEquals(new Plot(0,0, Couleur.BLEU), p.getLastPlot());
        assertEquals(new Plot(0,0, Couleur.BLEU), p.getPlot(0,0));

        assertEquals(1, p.getPlots().size());
    }
}
