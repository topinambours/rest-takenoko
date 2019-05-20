package server.controllers;

import communication.container.ActionContainer;
import communication.container.PoseTuileContainer;
import communication.container.ResponseContainer;
import org.junit.Ignore;
import server.GameEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import takenoko.Couleur;
import takenoko.Plateau;
import takenoko.tuile.Amenagement;
import takenoko.tuile.CoordAxial;
import takenoko.tuile.Tuile;
import takenoko.versionning.Action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class VersionComparatorTest {

    @Autowired
    private GameEngine gameEngine;

    @Autowired
    PlateauController pl;

    @Autowired
    VersionController vc;

    @Autowired
    private TestRestTemplate restTemplate;

    private final String server_url = "http://localhost:8080";
    private final int id = 0;

    @Test
    public void basicTest(){
        Plateau plateau = restTemplate.getForObject("/version/0/plateau",Plateau.class);

        assertEquals(new Plateau().plateau_depart(),plateau);
    }

    @Test
    public void addPlot(){
        CoordAxial coordAxial = new CoordAxial(0,1);
        Tuile tuile = new Tuile();
        assertTrue(restTemplate.postForObject("/action/poser-tuile/", new PoseTuileContainer(coordAxial,tuile), ResponseContainer.class).getResponse());

        Integer versionID = restTemplate.getForEntity("/version/latest/id", Integer.class).getBody();

        Plateau plateau = restTemplate.getForObject("/version/"+versionID+"/plateau",Plateau.class);

        ActionContainer actionContainer = restTemplate.getForEntity("/version/latest/",ActionContainer.class).getBody();

        Plateau expected = new Plateau().plateau_depart();
        expected.poserTuile(coordAxial,tuile);

        Action.applyAction(actionContainer.getContent().get(0),plateau);

        assertEquals(expected, plateau);
    }

    @Test
    @Ignore
    public void randomTest(){

        assertEquals(true,restTemplate.postForObject("/action/poser-tuile/", new PoseTuileContainer(new CoordAxial(0, 1),new Tuile(-2, Couleur.VERT, Amenagement.NONE)), ResponseContainer.class).getResponse());
        Plateau p = restTemplate.getForObject("/plateau/", Plateau.class);

        assertTrue(p.generateTuileMap().containsKey(new CoordAxial(0, 1)));
        // Let's check the id of the plot
        assertEquals(-2, p.getTuileAtCoord(new CoordAxial(0, 1)).getUnique_id());

        for (int i = 1; i <= 500;i++){
            assertEquals(true,restTemplate.postForObject("/action/poser-tuile/", new PoseTuileContainer(new CoordAxial(i,0),new Tuile(i, Couleur.JAUNE, Amenagement.NONE)), ResponseContainer.class).getResponse());
            p = restTemplate.getForObject("/plateau/", Plateau.class);

            assertTrue(p.generateTuileMap().containsKey(new CoordAxial(i, 0)));
            // Let's check the id of the plot
            assertEquals(i, p.getTuileAtCoord(new CoordAxial(i, 0)).getUnique_id());




            assertTrue(restTemplate.postForObject("/action/poser-tuile/", new PoseTuileContainer(new CoordAxial(i,1),new Tuile(3001 + i, Couleur.ROSE, Amenagement.NONE)), ResponseContainer.class).getResponse());
            p = restTemplate.getForObject("/plateau/", Plateau.class);

            assertTrue(p.generateTuileMap().containsKey(new CoordAxial(i, 1)));
            // Let's check the id of the plot
            assertEquals(3001 + i, p.getTuileAtCoord(new CoordAxial(i, 1)).getUnique_id());

            assertEquals(i * 2 + 2, p.generateTuileMap().size());
            assertEquals(gameEngine.getPlateau(), restTemplate.getForObject("/plateau/", Plateau.class));
        }
    }

}
