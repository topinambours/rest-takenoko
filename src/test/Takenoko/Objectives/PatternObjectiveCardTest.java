package Takenoko.Objectives;

import Takenoko.Joueur.Joueur;
import Takenoko.Joueur.StrategieCoord.StrategieCoordRandom;
import Takenoko.Joueur.StrategieIrrig.StrategieIrigBase;
import Takenoko.Objectives.Patterns.CoordCube;
import Takenoko.Objectives.Patterns.Pattern;
import Takenoko.Objectives.Patterns.PatternTile;
import Takenoko.Plateau;
import Takenoko.Plot.Plot;
import Takenoko.Properties.Couleur;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

public class PatternObjectiveCardTest {

    @Test
    public void pointValueTest() {
        Pattern pattern = new Pattern(new ArrayList<PatternTile>());
        PatternObjectiveCard patternObjectiveCard = new PatternObjectiveCard(pattern, 42);
        assertEquals(42, patternObjectiveCard.getPointValue());
        assertEquals(42, patternObjectiveCard.validate());
    }

    @Test
    public void isCompleteTest() {
        Plateau plateau = new Plateau();
        PatternTile tile1 = new PatternTile(new CoordCube(0, 0), Couleur.VERT);
        PatternTile tile2 = new PatternTile(new CoordCube(0, 1), Couleur.JAUNE);
        List<PatternTile> list = new ArrayList<>();
        list.add(tile1);
        list.add(tile2);
        Pattern myPattern = new Pattern(list);
        PatternObjectiveCard patternObjectiveCard = new PatternObjectiveCard(myPattern, 1);
        patternObjectiveCard.instanciate(plateau, new Joueur(1, new StrategieCoordRandom(),new StrategieIrigBase(plateau)));

        plateau.putPlot(new Plot(0, 0, Couleur.ROSE), 0, 0);
        plateau.putPlot(new Plot(0, 1, Couleur.ROSE), 0, 1);
        plateau.putPlot(new Plot(1, 1, Couleur.VERT), 1, 1);
        plateau.putPlot(new Plot(1, 0, Couleur.JAUNE), 1, 0);

        assertEquals(true, patternObjectiveCard.isComplete());
    }
}
