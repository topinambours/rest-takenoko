package Takenoko.Joueur.Strategie;

import Takenoko.Plateau;
import Takenoko.Plot.ComparateurCoordAxial;
import Takenoko.Plot.CoordAxial;
import Takenoko.Plot.Plot;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;


/**
 * tests concernant le placement des parcelles
 * Cette stratégie cherche à trouver le meilleur placement en maximisant le nombre de bambous déjà présent
 * sur les parcelles adjacentes.
 */
public class StrategieBambooTest {

    StrategieBamboo st;
    Plateau p;

    @Before
    public void setUp() throws Exception {
        st = new StrategieBamboo();
        p = new Plateau();
    }




    /**
     * Au premier tour, toute les positions sont possibles
     */
    @Test
    public void getCoordSinglePlot() {

        p.addStartingPlot(new Plot());

        ComparateurCoordAxial cpCoord = new ComparateurCoordAxial();

        List<CoordAxial> legalPos = p.legalPositions();
        legalPos.sort(cpCoord);

        List<CoordAxial> computePos = st.getCoords(p, null);
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
        p.addStartingPlot(new Plot());
        StrategieRandom randomStrat = new StrategieRandom();

        List<CoordAxial> legalPos;
        List<CoordAxial> computePos;

        for (int i = 0 ; i < 500; i++){
            p.putPlot(new Plot(randomStrat.getCoord(p, null)));
            legalPos = p.legalPositions();


            computePos = st.getCoords(p, null);

            /**
             * Nombres de bambous proposé par la stratégie
             */
            int bestChoiceBambou = p.getNeighbors(st.getCoord(p, null)).stream().mapToInt(Plot::getBambou).sum();

            /**
             * Nombre de bambous calculé à la volé
             */
            int bestChoiceInPlate = p.getNeighbors(legalPos.stream().max(cpPosBamboo).get()).stream().mapToInt(Plot::getBambou).sum() ;

            /** On compare */
            assertEquals(bestChoiceBambou, bestChoiceInPlate);


            /**
             * On trie ensuite toute les possibilités de pose, afin de déterminé si l'enssemble des positions est présente dans la solution
             * proposé par la stratégie.
             */
            computePos.sort(cpCoord);
            legalPos.sort(cpCoord);
            assertArrayEquals(legalPos.toArray(), computePos.toArray());
        }
    }

    @Test
    public void getStrategieLabel() {
        assertEquals("max adj bamboo", st.getStrategieLabel());
    }
}