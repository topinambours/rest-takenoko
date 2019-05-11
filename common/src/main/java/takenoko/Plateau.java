package takenoko;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.util.DigestUtils;
import takenoko.irrigation.CoordIrrig;
import takenoko.objectives.patterns.CoordCube;
import takenoko.tuile.Amenagement;
import takenoko.tuile.CoordAxial;
import takenoko.tuile.Tuile;
import takenoko.tuile.TuileNotFoundException;

import java.security.MessageDigest;
import java.util.*;
import java.util.stream.Collectors;


@Data
public class Plateau {

    private List<CoordAxial> coordAxialList;
    private List<Tuile> tuiles;

    private List<CoordIrrig> irrigations;

    private CoordAxial posPanda;

    public Plateau() {
        this.tuiles = new ArrayList<>();
        this.coordAxialList = new ArrayList<>();
        this.irrigations = new ArrayList<>();
        this.poserTuile(new CoordAxial(0,0), new Tuile(-1, Couleur.BLEU));
        this.posPanda = new CoordAxial(0,0);
    }

    public void poserTuile(CoordAxial pos, Tuile t){
        if (!coordAxialList.contains(pos)){
            tuiles.add(t);
            coordAxialList.add(pos);
            t.setHaveWater(checkPlotWater(pos));
            if (t.getHaveWater()){
                t.pousserBambou();
            }
        }
    }

    public List<CoordIrrig> getIrrigations() {
        return irrigations;
    }

    /**
     * getter de tuile, prend les coordonnées mises ensemble
     * @param coord coordonnées axiales de la parcelle
     * @return la parcelle placée en (coord)
     */
    public Tuile getTuileAtCoord(CoordAxial coord) {
        if (coordAxialList.indexOf(coord) != -1) {
            return tuiles.get(coordAxialList.indexOf(coord));
        }
        return null;
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
        if (coordAxialList.contains(coo)) {
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
            if (coordAxialList.contains(coord)){
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

        for (CoordAxial coo : coordAxialList){
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
            if (coordAxialList.contains(c)){
                out.add(getTuileAtCoord(c));
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
        if (coordAxialList.contains(coord) && coord.isInLine(posPanda) && hasStraightPath(coord, posPanda)){
            posPanda = coord;
            Tuile current = getTuileAtCoord(coord);
            if (current.getNbBambous() > 0 && !(current.getAmenagement().equals(Amenagement.ENCLOS))) { //Ne mange pas en cas d'enclos
                current.enleverBambou();

                return current.getCouleur();
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
                    if (getTuileAtCoord(new CoordAxial(coo1.getQ(), coo1.getR() + i)) == null) return false;
                }
                return true;
            } else {
                for (int i = 0; i < diffCub.getS(); i++) {
                    if (getTuileAtCoord(new CoordAxial(coo1.getQ(), coo1.getR() - i)) == null) return false;
                }
                return true;
            }
        } else if (diffCub.getR() == 0) {
            if (diffCub.getQ() >= 0) {
                for (int i = 0; i < diffCub.getQ(); i++) {
                    if (getTuileAtCoord(new CoordAxial(coo1.getQ() + i, coo1.getR())) == null) return false;
                }
                return true;
            } else {
                for (int i = 0; i < diffCub.getS(); i++) {
                    if (getTuileAtCoord(new CoordAxial(coo1.getQ() - i, coo1.getR())) == null) return false;
                }
                return true;
            }
        } else if (diffCub.getS() == 0) {
            if (diffCub.getQ() >= 0) {
                for (int i = 0; i < diffCub.getQ(); i++) {
                    if (getTuileAtCoord(new CoordAxial(coo1.getQ() + i, coo1.getR() - i)) == null) return false;
                }
                return true;
            } else {
                for (int i = 0; i < diffCub.getR(); i++) {
                    if (getTuileAtCoord(new CoordAxial(coo1.getQ() - i, coo1.getR() + i)) == null) return false;
                }
                return true;
            }
        } else return false;
    }



    public Set<CoordIrrig> irrigationsList() {
        return new HashSet<>(irrigations);
    }

    public CoordAxial posPanda() {
        return posPanda;
    }


    public Map<CoordAxial, Tuile> generateTuileMap() {
        HashMap<CoordAxial, Tuile> out = new HashMap<>();
        for (CoordAxial pos : coordAxialList){
            out.put(pos, getTuileAtCoord(pos));
        }
        return out;
    }

    /**
     * Permet d'avoir un id unique vers une tuile
     * @param id int
     * @return Tuile
     */
    public Tuile getTuileFromId(int id) throws TuileNotFoundException {
        List<Tuile> tuile = tuiles.stream().filter(t -> t.getUnique_id() == id).collect(Collectors.toList());
        if (tuile.size() == 0){
            throw new TuileNotFoundException();
        }else{
            return tuile.get(0);
        }
    }

    /**
     * Retrieve plot coordinate from plot object
     * plot must be on the board to retrieve the coordinate (obvious)
     * @param tuile
     * @return {@link CoordAxial} position of the plot on the board
     * @throws TuileNotFoundException
     */
    public CoordAxial getCoordFromTuile(Tuile tuile) {
        if (tuiles.contains(tuile)) {
            return coordAxialList.get(tuiles.indexOf(tuile));
        }
        try {
            throw new TuileNotFoundException();
        } catch (TuileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Permet d'avoir le couple CoordAxial,Tuile via un id
     * @param id int
     * @return Map.Entry<CoordAxial,Tuile>
     */
    public Map.Entry<CoordAxial,Tuile> getTuileFormId(int id) throws TuileNotFoundException {
        int index = 0;
        for (index = 0; index < tuiles.size(); index++){
            if (tuiles.get(index).getUnique_id() == id){
                return Map.entry(coordAxialList.get(index), tuiles.get(index));
            }
        }
        throw new TuileNotFoundException();
    }

    public List<Tuile> getLine(CoordAxial coord){
        return tuiles.stream().filter(p -> hasStraightPath(coord, getCoordFromTuile(p))).collect(Collectors.toList());
    }

    public List<CoordAxial> getLineCoord(CoordAxial coordAxial){
        return getLine(coordAxial).stream().map(this::getCoordFromTuile).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Plateau{" +
                "tuiles=" + generateTuileMap() +
                ", irrigations=" + irrigations +
                ", posPanda=" + posPanda +
                '}';
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

    public String hashMD5(){
        return DigestUtils.md5DigestAsHex(this.toString().getBytes());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plateau plateau = (Plateau) o;
        return coordAxialList.equals(plateau.coordAxialList) &&
                tuiles.equals(plateau.tuiles) &&
                irrigations.equals(plateau.irrigations) &&
                posPanda.equals(plateau.posPanda);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordAxialList, tuiles, irrigations, posPanda);
    }
}
