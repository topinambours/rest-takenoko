package takenoko;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import takenoko.tuile.Amenagement;
import takenoko.tuile.CoordAxial;
import takenoko.tuile.Tuile;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class Plateau {

    private HashMap<CoordAxial, Tuile> tuiles;

    public Plateau() {
        this.tuiles = new HashMap<>();
        this.tuiles.put(new CoordAxial(0,0), new Tuile(-1, Couleur.BLEU));
    }

    public void poserTuile(CoordAxial pos, Tuile t){
        if (!tuiles.containsKey(pos)){
            tuiles.put(pos, t);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Plateau : taille = ");
        sb.append(tuiles.size());
        sb.append("\n");
        for (Map.Entry<CoordAxial, Tuile> t : tuiles.entrySet()){
            sb.append(t.getKey().toString());
            sb.append(" ");
            sb.append(t.getValue().toString());
            sb.append("\n");
        }

        return sb.toString();
    }

    @Bean(name = "plateau_vide")
    public Plateau plateau_vide() {
        return new Plateau();
    }

    @Bean(name = "plateauTakenoko")
    @Scope("singleton")
    public Plateau plateau_depart() {
        Plateau out = new Plateau();
        out.poserTuile(new CoordAxial(0,0), new Tuile(0, Couleur.BLEU, Amenagement.NONE));
        return out;
    }
}
