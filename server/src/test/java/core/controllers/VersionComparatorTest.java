package core.controllers;

import communication.container.ActionContainer;
import communication.container.PoseTuileContainer;
import communication.container.ResponseContainer;
import core.GameEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import takenoko.Couleur;
import takenoko.Plateau;
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
    public void randomTest(){
        int id = 0;
        for (int i = 0; i < 1500;i++){
            int al = i + (int)(Math.random() * ((8000- 2* i) + 1));
            int al2 = i + (int)(Math.random() * ((8000- 2*i) + 1));
            assertTrue(restTemplate.postForObject("/action/poser-tuile/", new PoseTuileContainer(new CoordAxial(al,al2),new Tuile(id, Couleur.BLEU)), ResponseContainer.class).getResponse());
            Plateau p = restTemplate.getForObject("/plateau/", Plateau.class);

            // put plot on randomly generated coordinate
            assertTrue(p.generateTuileMap().containsKey(new CoordAxial(al, al2)));
            // Let's check the id of the plot
            assertEquals(id, p.getTuileAtCoord(new CoordAxial(al, al2)).getUnique_id());

            id+=1;
            assertEquals(id+1, p.generateTuileMap().size());
        }
    }

}
