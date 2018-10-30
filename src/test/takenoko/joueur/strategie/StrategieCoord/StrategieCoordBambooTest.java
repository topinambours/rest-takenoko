package takenoko.joueur.strategie.StrategieCoord;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import takenoko.Plateau;
import takenoko.plot.CoordAxial;
import takenoko.plot.Plot;
import takenoko.util.comparators.ComparateurCoordAxial;
import takenoko.util.comparators.ComparateurPosBambooAdj;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


/**
 * tests concernant le placement des parcelles
 * Cette stratégie cherche à trouver le meilleur placement en maximisant le nombre de bambous déjà présent
 * sur les parcelles adjacentes.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategieCoordBambooTest {

    StrategieCoordBamboo st;
    @Autowired
    @Qualifier("plateauTakenoko")
    Plateau p;

    @Before
    public void setUp() throws Exception {
        st = new StrategieCoordBamboo(false);
    }




    /**
     * Au premier tour, toute les positions sont possibles
     */
    @Test
    public void getCoordSinglePlot() {

        ComparateurCoordAxial cpCoord = new ComparateurCoordAxial();

        List<CoordAxial> legalPos = p.legalPositions();
        legalPos.sort(cpCoord);

        List<CoordAxial> computePos = st.getCoords(p);
        computePos.sort(cpCoord);

        assertArrayEquals(legalPos.toArray(), computePos.toArray());

    }

    /**
     * On ajoute successivement 500 parcelles au plateau
     * On compare alors la liste de positions calculé par la stratégie, on vérifie également que la méthode ne demandant qu'un seul point
     * renvoie bien une position avec un nombre de bambous adjacents maximale.
     *
     */
    @Test
    public void getCoord500PlotsAdded(){

        ComparateurPosBambooAdj cpPosBamboo = new ComparateurPosBambooAdj(p);
        ComparateurCoordAxial cpCoord = new ComparateurCoordAxial();

        /**
         * initialisation du plateau
         */
        StrategieCoordRandom randomStrat = new StrategieCoordRandom();

        List<CoordAxial> legalPos;
        List<CoordAxial> computePos;

        for (int i = 0 ; i < 500; i++){
            p.putPlot(new Plot(randomStrat.getCoord(p)));
            legalPos = p.legalPositions();

            /**
             * Nombres de bambous proposé par la stratégie
             */
            int bestChoiceBambou = p.getNeighbors(st.getCoord(p)).stream().mapToInt(Plot::getBambou).sum();

            /**
             * Nombre de bambous calculé à la volé
             */
            int bestChoiceInPlate = p.getNeighbors(legalPos.stream().max(cpPosBamboo).get()).stream().mapToInt(Plot::getBambou).sum() ;

            /** On compare */
            assertEquals(bestChoiceBambou, bestChoiceInPlate);

        }
    }

    @Test
    public void getStrategieLabel() {
        assertEquals("max adj bamboo", st.getStrategieCoordLabel());
    }
}