package client.strategie;

import client.Joueur;
import communication.container.TuileContainer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import takenoko.Couleur;
import takenoko.irrigation.CoordIrrig;
import takenoko.irrigation.Orient;
import takenoko.tuile.Amenagement;
import takenoko.tuile.CoordAxial;
import takenoko.tuile.Tuile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RandomStrategieTest {

    @Autowired
    Joueur joueur;

    @Test
    public void selectTuileFromSingle() {

        TuileContainer tuiles = new TuileContainer(new Tuile(1, Couleur.ROSE, Amenagement.NONE));

        Tuile selected = joueur.getStrategie().selectTuile(tuiles.getContent());

        assertEquals(new Tuile(1, Couleur.ROSE, Amenagement.NONE), selected);

    }

    @Test
    public void selectTuileFrom() {

        List<Tuile> tuiles = new ArrayList<>();
        tuiles.add(new Tuile(1, Couleur.ROSE, Amenagement.NONE));
        tuiles.add(new Tuile(2, Couleur.JAUNE, Amenagement.BASSIN));


        // Random strategy, player can choose id 1 or 2
        for (int i = 0; i < 500; i++){
            Tuile selected = joueur.getStrategie().selectTuile(tuiles);

            if (selected.getUnique_id() != 2){
                assertEquals(new Tuile(1, Couleur.ROSE, Amenagement.NONE), selected);
            }else{
                assertEquals(new Tuile(2, Couleur.JAUNE, Amenagement.BASSIN), selected);
            }
        }

    }

    @Test
    public void selectEmplacementSingle() {
        List<CoordAxial> coordAxials = new ArrayList<>();
        coordAxials.add(new CoordAxial(1,0));

        assertEquals(new CoordAxial(1,0), joueur.getStrategie().selectEmplacement(coordAxials));
    }

    @Test
    public void selectEmplacement() {
        List<CoordAxial> coordAxials = new ArrayList<>();
        coordAxials.add(new CoordAxial(1,0));
        coordAxials.add(new CoordAxial(-1,0));

        // Random strategy, player can choose id 1 or 2
        for (int i = 0; i < 500; i++){
            CoordAxial selected = joueur.getStrategie().selectEmplacement(coordAxials);

            if (selected.equals(new CoordAxial(1,0))){
                assertEquals(new CoordAxial(1,0), selected);
            }else{
                assertEquals(new CoordAxial(-1,0), selected);
            }
        }
    }

    @Test
    public void selectIrrigationEmplacementSingle() {
        List<CoordIrrig> coordIrrigs = new ArrayList<>();
        coordIrrigs.add(new CoordIrrig(1,0, Orient.S));

        assertEquals(new CoordIrrig(1,0, Orient.S), joueur.getStrategie().selectIrrigationEmplacement(coordIrrigs));
    }

    @Test
    public void selectIrrigationEmplacement() {
        List<CoordIrrig> coordIrrigs = new ArrayList<>();
        coordIrrigs.add(new CoordIrrig(1,0, Orient.S));
        coordIrrigs.add(new CoordIrrig(1,0, Orient.N));

        // Random strategy, player can choose id 1 or 2
        for (int i = 0; i < 500; i++){
            CoordIrrig selected = joueur.getStrategie().selectIrrigationEmplacement(coordIrrigs);

            if (selected.equals(new CoordIrrig(1,0, Orient.S))){
                assertEquals(new CoordIrrig(1,0, Orient.S), selected);
            }else{
                assertEquals(new CoordIrrig(1,0, Orient.N), selected);
            }
        }
    }

    @Test
    public void selectPandaEmplacementSingle() {
        List<CoordAxial> coordAxials = new ArrayList<>();
        coordAxials.add(new CoordAxial(1,0));

        assertEquals(new CoordAxial(1,0), joueur.getStrategie().selectPandaEmplacement(coordAxials));
    }

    @Test
    public void selectPandaEmplacement() {
        List<CoordAxial> coordAxials = new ArrayList<>();
        coordAxials.add(new CoordAxial(1,0));
        coordAxials.add(new CoordAxial(-1,0));

        // Random strategy, player can choose id 1 or 2
        for (int i = 0; i < 500; i++){
            CoordAxial selected = joueur.getStrategie().selectPandaEmplacement(coordAxials);

            if (selected.equals(new CoordAxial(1,0))){
                assertEquals(new CoordAxial(1,0), selected);
            }else{
                assertEquals(new CoordAxial(-1,0), selected);
            }
        }
    }
}