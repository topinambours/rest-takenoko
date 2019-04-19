package takenoko;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import takenoko.irrigation.CoordIrrig;
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
    private HashSet<CoordIrrig> irrigations;

    public Plateau() {
        this.tuiles = new HashMap<>();
        this.irrigations = new HashSet<>();
        this.tuiles.put(new CoordAxial(0,0), new Tuile(-1, Couleur.BLEU));
    }

    public void poserTuile(CoordAxial pos, Tuile t){
        if (!tuiles.containsKey(pos)){
            tuiles.put(pos, t);
            t.setHaveWater(checkPlotWater(pos));

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

    // -----------
    // IRRIGATION
    // -----------

    /**
     * ajoute une section d'irrigation au plateau
     * Modifie la propriété "irriguée" aux parcelles adjacentes à l'irrigation
     * @param coo
     * @return boolean true|false
     */
    public boolean addIrrigation(CoordIrrig coo) {
        boolean res = irrigations.add(coo);
        for (Tuile t : getPlotsFromIrig(coo)){
            if (!t.getHaveWater()){
                t.setHaveWater(true);
                // Lors de la première irigation, la parcelle reçoit un bambou
                t.pousserBambou();
            }

        }
        return res;
    }

    /**
     * Permet de définir si une parcelle est irriguée
     * Une parcelle est automatique irrigué si elle est adjacente à la parcelle de départ
     * @param coordAxial CoordAxial les coordonnées de la parcelle
     * @return boolean true|false
     */
    public boolean checkPlotWater(CoordAxial coordAxial){
        if (coordAxial.computeNeighborCoords().contains(new CoordAxial(0,0))){
            return true;
        }

        for (CoordIrrig c : coordAxial.computeBorderCoords()){
            if (this.irrigationsList().contains(c)){
                return true;
            }
        }
        return false;
    }

    /**
     * rend la liste des endroits où on peut placer des irrigations
     * @return List<CoordIrrig>
     */
    public List<CoordIrrig> legalIrrigPositions() {
        return irrigPositionsToTest().stream().filter(this::isIrrigPositionLegal).collect(Collectors.toList());
    }

    /**
     * fonction utilitaire pour legalIrrigPositions
     * @return
     */
    private List<CoordIrrig> irrigPositionsToTest() {
        HashSet<CoordIrrig> mySet = new HashSet<CoordIrrig>();
        for (CoordIrrig irrig : irrigations) {
            mySet.addAll(irrig.continues());
        }
        return new ArrayList<>(mySet);
    }

    /**
     * renvoie si on peut placer
     * @param coo
     * @return
     */
    public boolean isIrrigPositionLegal(CoordIrrig coo) {
        // Déjà placé
        if (irrigations.contains(coo)){
            return false;
        }

        // Si la position est la continuité d'un autre canal d'irrigation la pos est valide
        for (CoordIrrig nbc : coo.continues()) {
            if (irrigations.contains(nbc) && getPlotsFromIrig(coo).size() == 2) {
                // Un canaux ne peut être placé qu'entre deux parcelles
                return true;
            }
        }
        return false;
    }

    /**
     * Renvoie la liste des tuiles adjacentes à une coordonnée d'irrigation
     * Les tuiles renvoyées sont posées sur le plateau
     * @param coordIrrig coordonnée d'irrigation
     * @return tuiles présentent sur le plateau étant adjacente à coordIrrig passée en paramètre
     */
    public List<Tuile> getPlotsFromIrig(CoordIrrig coordIrrig){
        List<Tuile> out = new ArrayList<>();
        List<CoordAxial> coordAxials = coordIrrig.borders();

        for (CoordAxial c : coordAxials){
            if (tuiles.containsKey(c)){
                out.add(tuiles.get(c));
            }
        }
        return out;
    }


    public HashSet<CoordIrrig> irrigationsList() {
        return irrigations;
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
        CoordAxial startingCoord = new CoordAxial(0,0);
        out.poserTuile(startingCoord, new Tuile(0, Couleur.BLEU, Amenagement.NONE));
        List<CoordIrrig> borderCoords = new CoordAxial(0,0).computeBorderCoords();
        out.irrigations.addAll(borderCoords);

        return out;
    }
}
