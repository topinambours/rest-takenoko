package takenoko;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class Plateau {

    private ArrayList<Tuile> tuiles;

    public Plateau() {
        this.tuiles = new ArrayList<>();
        this.tuiles.add(new Tuile(0, Couleur.BLEU));
    }

    public Tuile getFirst() {
        return tuiles.get(0);
    }

    @Bean(name = "plateau_vide")
    public Plateau plateau_vide() {
        return new Plateau();
    }

}
