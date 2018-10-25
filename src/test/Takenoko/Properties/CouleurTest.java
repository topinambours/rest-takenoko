package takenoko.properties;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CouleurTest {

    @Test
    public void toStringTest() {
        assertEquals("Vert", Couleur.VERT.toString());
        assertEquals("Jaune", Couleur.JAUNE.toString());
        assertEquals("Rose", Couleur.ROSE.toString());
        assertEquals("Bleu(Lac)", Couleur.BLEU.toString());
    }

    @Test
    public void getById() {
        assertEquals(Couleur.VERT,Couleur.getById(0));
        assertEquals( Couleur.JAUNE,Couleur.getById(2));
        assertEquals(Couleur.ROSE,Couleur.getById(1));
        assertEquals(Couleur.BLEU,Couleur.getById(3));
        assertEquals(null,Couleur.getById(4));
    }
}
