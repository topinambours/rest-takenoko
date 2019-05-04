package takenoko;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import takenoko.irrigation.CoordIrrig;
import takenoko.objectives.patterns.CoordCube;
import takenoko.tuile.Amenagement;
import takenoko.tuile.CoordAxial;
import takenoko.tuile.Tuile;
import takenoko.tuile.TuileNotFoundException;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Data
@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class Plateau {

    private HashMap<CoordAxial, Tuile> tuiles;
    private HashSet<CoordIrrig> irrigations;
    private CoordAxial posPanda;

    public Plateau() {
        this.tuiles = new HashMap<>();
        this.irrigations = new HashSet<>();
        this.tuiles.put(new CoordAxial(0,0), new Tuile(-1, Couleur.BLEU));
        this.posPanda = new CoordAxial(0,0);
    }

    public void poserTuile(CoordAxial pos, Tuile t){
        if (!tuiles.containsKey(pos)){
            tuiles.put(pos, t);
            t.setHaveWater(checkPlotWater(pos));
            if (t.getHaveWater()){
                t.pousserBambou();
            }
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

    /**
     * getter de parcelle, prend les coordonnées mises ensemble
     * @param coord coordonnées axiales de la parcelle
     * @return la parcelle placée en (coord)
     */
    public Tuile getTuile(CoordAxial coord) {
        return tuiles.get(coord);
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


    // -------
    // PANDA
    // -------


    /**
     * Modifie la position du panda uniquement si pour la nouvelle position, il existe une parcelle sur le plateau
     * @param coord nouvelle coordonnée de la figurine panda
     * @return la couleur du bambou récupéré après le passage du panda
     */
    public Couleur movePanda(CoordAxial coord){
        if (tuiles.containsKey(coord) && coord.isInLine(posPanda) && hasStraightPath(coord, posPanda)){
            posPanda = coord;
            Tuile current = tuiles.get(coord);
            if (current.getNbBambous() > 0 && !(current.getAmenagement().equals(Amenagement.ENCLOS))) { //Ne mange pas en cas d'enclos
                tuiles.get(coord).enleverBambou();

                return tuiles.get(coord).getCouleur();
            }else{

                return Couleur.BLEU;
            }
        }
        // Couleur de la tuile de départ, ne rapporte pas de section au joueur.
        return Couleur.BLEU;
    }

    public List<CoordAxial> computePandaLegalPositions(){
        List<CoordAxial> legalPos = getLineCoord(posPanda);
        legalPos.remove(posPanda);
        return legalPos;
    }



    // ------
    // UTILS
    // ------

    /**
     * Vérifie si deux parcelles sont alignées et ont un chemin droit ininterrompu entre elles
     * @param coo1 la coordonnée de la première parcelle
     * @param coo2 la coordonnée de la seconde parcelle
     * @return vrai s'il y a un chemin, faux sinon
     */
    public boolean hasStraightPath(CoordAxial coo1, CoordAxial coo2) {
        CoordCube diffCub = new CoordCube(coo2.getQ() - coo1.getQ(), coo2.getR() - coo1.getR());
        if (diffCub.getQ() == 0) {
            if (diffCub.getR() >= 0) {
                for (int i = 0; i < diffCub.getR(); i++) {
                    if (getTuile(new CoordAxial(coo1.getQ(), coo1.getR() + i)) == null) return false;
                }
                return true;
            } else {
                for (int i = 0; i < diffCub.getS(); i++) {
                    if (getTuile(new CoordAxial(coo1.getQ(), coo1.getR() - i)) == null) return false;
                }
                return true;
            }
        } else if (diffCub.getR() == 0) {
            if (diffCub.getQ() >= 0) {
                for (int i = 0; i < diffCub.getQ(); i++) {
                    if (getTuile(new CoordAxial(coo1.getQ() + i, coo1.getR())) == null) return false;
                }
                return true;
            } else {
                for (int i = 0; i < diffCub.getS(); i++) {
                    if (getTuile(new CoordAxial(coo1.getQ() - i, coo1.getR())) == null) return false;
                }
                return true;
            }
        } else if (diffCub.getS() == 0) {
            if (diffCub.getQ() >= 0) {
                for (int i = 0; i < diffCub.getQ(); i++) {
                    if (getTuile(new CoordAxial(coo1.getQ() + i, coo1.getR() - i)) == null) return false;
                }
                return true;
            } else {
                for (int i = 0; i < diffCub.getR(); i++) {
                    if (getTuile(new CoordAxial(coo1.getQ() - i, coo1.getR() + i)) == null) return false;
                }
                return true;
            }
        } else return false;
    }



    public HashSet<CoordIrrig> irrigationsList() {
        return irrigations;
    }

    public CoordAxial posPanda() {
        return posPanda;
    }

    public HashMap<CoordAxial, Tuile> getTuiles() {
        return tuiles;
    }

    /**
     * Permet d'avoir un id unique vers une tuile
     * @param id int
     * @return Tuile
     */
    public Tuile getTuileFromId(int id) throws TuileNotFoundException {
        List<Tuile> tuile = tuiles.values().stream().filter(t -> t.getUnique_id() == id).collect(Collectors.toList());
        if (tuile.size() == 0){
            throw new TuileNotFoundException();
        }else{
            return tuile.get(0);
        }

    }

    /**
     * Permet d'avoir le couple CoordAxial,Tuile via un id
     * @param id int
     * @return Map.Entry<CoordAxial,Tuile>
     */
    public Map.Entry<CoordAxial,Tuile> getTuileFormId(int id) throws TuileNotFoundException {
        List<Map.Entry<CoordAxial,Tuile>> entries = tuiles.entrySet().stream().filter(t -> t.getValue().getUnique_id() == id).collect(Collectors.toList());
        if (entries.size() == 0){
            throw new TuileNotFoundException();
        }else{
            return entries.get(0);
        }

    }

    public List<Map.Entry<CoordAxial,Tuile>> getLine(CoordAxial coord){
        List<Map.Entry<CoordAxial,Tuile>> entries = tuiles.entrySet().stream().filter(p -> hasStraightPath(coord, p.getKey())).collect(Collectors.toList());
        return entries;
    }

    public List<Tuile> getLinePlots(CoordAxial coordAxial){
        return getLine(coordAxial).stream().map(Map.Entry::getValue).collect(Collectors.toList());
    }

    public List<CoordAxial> getLineCoord(CoordAxial coordAxial){
        return getLine(coordAxial).stream().map(Map.Entry::getKey).collect(Collectors.toList());
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

        out.posPanda = startingCoord;

        return out;
    }

}
