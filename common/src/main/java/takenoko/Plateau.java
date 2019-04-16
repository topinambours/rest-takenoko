package takenoko;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import takenoko.tuile.Amenagement;
import takenoko.tuile.CoordAxial;
import takenoko.tuile.Tuile;

import java.util.*;
import java.util.stream.Collectors;

@Data
@Configuration
public class Plateau {

    public HashMap<CoordAxial, Tuile> getTuiles() {
        return tuiles;
    }

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

    /**
     * getter de tuile, prend les coordonnées mises ensemble
     * @param coord coordonnées axiales de la parcelle
     * @return la parcelle placée en (coord)
     */
    public Tuile getTuileAtCoord(CoordAxial coord) {
        return tuiles.get(coord);
    }

    /**
     * rend la liste des parcelles adjacentes
     * @param coo
     * @return
     */
    public List<Tuile> getNeighbors(CoordAxial coo) {
        ArrayList<Tuile> res = new ArrayList<>();
        for (CoordAxial nbc : coo.computeNeighborCoords()) {
            Tuile myPlot = getTuileAtCoord(nbc);
            if (myPlot != null) {
                res.add(myPlot);
            }
        }
        return res;
    }

    /**
     * vérifie si une position est jouable
     * @param coo
     * @return
     */
    public boolean isPositionLegal(CoordAxial coo) {
        // Si une tuile est déjà présente, coo n'est pas un placement légal
        if (getTuileAtCoord(coo) != null) {
            return false;
        }
        if (coo.equals(new CoordAxial(0,0))){
            return false;
        }
        // Si la position est adjacente à celle de départ
        if (new CoordAxial(0,0).computeNeighborCoords().contains(coo)){
            return true;
        }

        int nbAdj = 0;
        for (CoordAxial coord : coo.computeNeighborCoords()){
            if (this.getTuileAtCoord(coord) != null){
                nbAdj += 1;
            }
        }
        return nbAdj >= 2;
    }

    /**
     * rend la liste des positions légales pour jouer
     * @return
     */
    public List<CoordAxial> legalPositions() {

        HashSet<CoordAxial> out = new HashSet<>();

        for (CoordAxial coo : tuiles.keySet()){
            out.addAll(coo.computeNeighborCoords().stream().filter(this::isPositionLegal).collect(Collectors.toList()));
        }

        return new ArrayList<>(out);

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
