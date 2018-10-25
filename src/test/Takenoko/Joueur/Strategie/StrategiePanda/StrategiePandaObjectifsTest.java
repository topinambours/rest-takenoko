package takenoko.joueur.strategie.StrategiePanda;

import org.junit.Test;
import takenoko.Plateau;
import takenoko.Plot.CoordAxial;
import takenoko.Plot.Plot;
import takenoko.joueur.Joueur;
import takenoko.joueur.strategie.StrategieAction.StrategieActionBasique;
import takenoko.joueur.strategie.StrategieAmenagement.StrategieAmenagementBasique;
import takenoko.joueur.strategie.StrategieConcrete;
import takenoko.joueur.strategie.StrategieCoord.StrategieCoordAdjacent;
import takenoko.joueur.strategie.StrategieIrrig.StrategieIrrigComparator;
import takenoko.joueur.strategie.StrategieJardinier.StrategieJardinierBasique;
import takenoko.joueur.strategie.StrategieSansPions;
import takenoko.objectives.PandaObjectiveCard;
import takenoko.objectives.amenagement.Amenagement;
import takenoko.properties.Couleur;

import static org.junit.Assert.*;

public class StrategiePandaObjectifsTest {

    @Test
    public void getPandaMove() {
        StrategiePandaObjectifs st = new StrategiePandaObjectifs();
        Plateau p = new Plateau();

        Joueur j = new Joueur(1, new StrategieConcrete(
                new StrategieCoordAdjacent(),
                new StrategieIrrigComparator(p),
                new StrategiePandaObjectifs(),
                new StrategieJardinierBasique(),
                new StrategieActionBasique(),
                new StrategieAmenagementBasique()));

        p.putPlot(new Plot(1,-1, Couleur.VERT));

        j.addPandaObjectiveCard(new PandaObjectiveCard(0,0,1,5));
        j.addPandaObjectiveCard(new PandaObjectiveCard(2,0,0,5));
        j.addPandaObjectiveCard(new PandaObjectiveCard(0,3,0, 5));

        p.getPlot(1,-1).pousserBambou();

        p.putPlot(new Plot(-1,0, Couleur.JAUNE));

        assertEquals(new CoordAxial(1,-1), st.getPandaMove(p, j));

        p.putPlot(new Plot(2,-2,Couleur.ROSE, Amenagement.BASSIN));
        p.getPlot(2,-2).pousserBambou();
        
        assertEquals(new CoordAxial(2,-2), st.getPandaMove(p,j));

    }

    @Test
    public void getStrategiePandaLabel() {
        StrategiePandaObjectifs st = new StrategiePandaObjectifs();
        assertEquals("", st.getStrategiePandaLabel());
    }
}