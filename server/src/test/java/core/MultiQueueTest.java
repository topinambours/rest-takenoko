package core;

import communication.HTTPClient;
import org.junit.Test;

import static org.junit.Assert.*;

public class MultiQueueTest {

    @Test
    public void MainTest() {

        for (int i = 0; i < 1000; i++) {
            MultiQueue t = new MultiQueue();
            // On génére un nombre aléatoire de client [10000 - 15000]
            int nbClient = 10000 + (int) (Math.random() * ((15000 - 10000) + 1));

            for (int j = 1; j <= nbClient ; j++){

                int gameSize = 2 + (int) (Math.random() * ((4 - 2) + 1));
                t.addClient(gameSize, new HTTPClient(j));
            }

            // Selon une loi probabiliste inconnue, je devrais avoir le nombre de joueurs requis dans chacune des queues.

            assertTrue(t.enoughPlayerForGame(2));
            assertTrue(t.enoughPlayerForGame(3));
            assertTrue(t.enoughPlayerForGame(4));

            // Je vide les files jusqu'à ce que je ne puisse plus assigner des joueurs
            int nbPlayersAssigned = 0;
            while (t.enoughPlayerForGame(2)){
                assertEquals(2,t.removeFromQueue(2,2).size());
                nbPlayersAssigned += 2;
            }
            while (t.enoughPlayerForGame(3)){
                assertEquals(3,t.removeFromQueue(3,3).size());
                nbPlayersAssigned += 3;
            }
            while (t.enoughPlayerForGame(4)){
                assertEquals(4,t.removeFromQueue(4,4).size());
                nbPlayersAssigned += 4;
            }

            // On test les joueurs restant
            assertTrue(t.getQueue(2).size() < 2);
            assertTrue(t.getQueue(2).size() < 3);
            assertTrue(t.getQueue(2).size() < 4);

            // Joueurs en attente
            int joueurEnAttente = t.getQueue(2).size()
                    + t.getQueue(3).size()
                    + t.getQueue(4).size();

            assertEquals(nbClient, joueurEnAttente + nbPlayersAssigned);
        }

    }
}