package Takenoko.Objectives;

import Takenoko.Irrigation.CoordIrrig;
import Takenoko.Irrigation.Orient;
import Takenoko.Joueur.Joueur;
import Takenoko.Joueur.Strategie.StrategieCoord.StrategieCoordRandom;
import Takenoko.Joueur.Strategie.StrategieIrrig.StrategieIrrigBase;
import Takenoko.Joueur.Strategie.StrategieSansPions;
import Takenoko.Objectives.Patterns.CoordCube;
import Takenoko.Objectives.Patterns.Pattern;
import Takenoko.Objectives.Patterns.PatternTile;
import Takenoko.Plateau;
import Takenoko.Plot.Plot;
import Takenoko.Properties.Couleur;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class PatternObjectiveCardTest {

    Plateau plateau;
    Pattern myPattern;
    PatternObjectiveCard patternObjectiveCard;
    List<PatternTile> list;

    @Before
    public void setup(){
        plateau = new Plateau();
        PatternTile tile1 = new PatternTile(new CoordCube(0, 0), Couleur.VERT);
        PatternTile tile2 = new PatternTile(new CoordCube(0, 1), Couleur.JAUNE);
        list = new ArrayList<>();
        list.add(tile1);
        list.add(tile2);
        myPattern = new Pattern(list);
        patternObjectiveCard = new PatternObjectiveCard(myPattern, 1);
        patternObjectiveCard.instanciate(plateau, new Joueur(1, new StrategieSansPions(new StrategieCoordRandom(),new StrategieIrrigBase(plateau))));
    }

    @Test
    public void pointValueTest() {
        Pattern pattern = new Pattern(new ArrayList<PatternTile>());
        PatternObjectiveCard patternObjectiveCard = new PatternObjectiveCard(pattern, 42);
        assertEquals(42, patternObjectiveCard.getPointValue());
        assertEquals(42, patternObjectiveCard.validate());
    }

    @Test
    public void isCompleteTest() {


        plateau.putPlot(new Plot(0, 0, Couleur.ROSE), 0, 0);
        plateau.putPlot(new Plot(0, 1, Couleur.ROSE), 0, 1);
        plateau.putPlot(new Plot(1, 1, Couleur.VERT), 1, 1);

        plateau.putIrrigation(new CoordIrrig(1,0, Orient.S));
        plateau.putIrrigation(new CoordIrrig(1,1, Orient.S));

        assertTrue(!patternObjectiveCard.isComplete());

        plateau.putPlot(new Plot(1, 0, Couleur.JAUNE), 1, 0);

        assertTrue(patternObjectiveCard.isComplete());
    }

    @Test
    public void getPointValue(){
        assertEquals(1, patternObjectiveCard.getPointValue());

        PatternObjectiveCard patternObjectiveCard2;
        patternObjectiveCard2 = new PatternObjectiveCard(myPattern, 2);

        assertEquals(2, patternObjectiveCard2.getPointValue());

        PatternObjectiveCard patternObjectiveCard3;
        patternObjectiveCard3 = new PatternObjectiveCard(myPattern, 5);

        assertEquals(5, patternObjectiveCard3.getPointValue());
    }
}
