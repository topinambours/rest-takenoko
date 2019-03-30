package takenoko;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import takenoko.tuile.CoordAxial;
import takenoko.tuile.Tuile;
import java.util.HashMap;

@Configuration
public class Plateau {

    private HashMap<CoordAxial, Tuile> tuiles;

    public Plateau() {
        this.tuiles = new HashMap<>();
        this.tuiles.put(new CoordAxial(0,0), new Tuile(-1, Couleur.BLEU));
    }

    @Bean(name = "plateau_vide")
    public Plateau plateau_vide() {
        return new Plateau();
    }

}
