package takenoko;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import takenoko.irrigation.CoordIrrig;
import takenoko.objectives.amenagement.Amenagement;
import takenoko.objectives.patterns.CoordCube;
import takenoko.plot.CoordAxial;
import takenoko.plot.Plot;
import takenoko.properties.Couleur;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Un plateau contenant des parcelles hexagonales. Chaque parcelles partagent donc avec ses voisines une unique arête représenté ici par une irrigation.
 * Un plateau dispose également de deux figurines pouvant se déplacer en ligne sur le plateau.
 * Une nouvelle parcelle ne peut être posé que si elle est adjacente à la parcelle de départ ou bien si elle est adjacente à deux parcellles.
 */
public class Plateau {
    /**
     * Coordonnée de départ
     */
    private final CoordAxial _STARTING_COORDINATE_ = new CoordAxial(0,0);

    /**
     * Structure contenant l'ensemble des tuiles constituantes le plateau
     */
    private HashMap<CoordAxial, Plot> plots;

    /**
     * Contient l'esembles des canaux d'irrigations déjà posés
     */
    private HashSet<CoordIrrig> irrigations;

    /**
     * Dernière tuile ayant été posée
     */
    private Plot lastPlot;

    /**
     * Position du Panda sur le plateau
     */
    private CoordAxial posPanda;

    /**
     * Position du Jardinier sur le plateau
     */
    private CoordAxial posJardinier;

    /**
     * Nombre de canaux d'irrigation restants
     */
    private int canalIrrigation;

    /**
     * Constructeur par défaut, instancie un plateau vide
     */
    public Plateau() {
        plots = new HashMap<>();
        irrigations = new HashSet<>();
    }

    public CoordAxial getPosPanda() {
        return posPanda;
    }

    public CoordAxial getPosJardinier(){return posJardinier;}

    public int getCanalIrrigation(){
        return canalIrrigation;
    }
    public void removeCanalIrrigation(){
        if (canalIrrigation > 0) {
            canalIrrigation = canalIrrigation - 1;
        }
    }

    /**
     * getter de parcelle, prend les coordonnées mises ensemble
     * @param coord coordonnées axiales de la parcelle
     * @return la parcelle placée en (coord)
     */
    public Plot getPlot(CoordAxial coord) {
        return plots.get(coord);
    }

    /**
     * getter de parcelle, prend les coordonnées séparément
     * @param q coord en axe q de la parcelle
     * @param r coord en axe r de la parcelle
     * @return la parcelle placée en (q, r)
     */
    public Plot getPlot(int q, int r) {
        return getPlot(new CoordAxial(q, r));
    }

    public Plot getLastPlot() {
        return lastPlot;
    }

    private void setLastPlot(Plot lastPlot) {
        this.lastPlot = lastPlot;
    }

    /**
     * placeur de parcelle, prend les coordonnées mises ensemble
     * @param plot une parcelle à placer
     * @param coord le couple de coordonnées où la placer
     */
    public void putPlot(Plot plot, CoordAxial coord) {
        plot.setCoord(coord.getQ(), coord.getR());
        plots.put(coord, plot);
        setLastPlot(plot);
        plot.setWater(checkPlotWater(plot.getCoord()));
        if (plot.haveWater()){
            plot.pousserBambou();
            // Extra bambou that need to be removes
            if (plot.getAmenagement() == Amenagement.ENGRAIS){
                plot.removeBambou(1);
            }
        }
    }

    /**
     * placeur de parcelle, prend les coordonnées séparément
     * @param plot une parcelle à placer
     * @param q coord en axe q où placer la parcelle
     * @param r coord en axe r où placer la parcelle
     */
    public void putPlot(Plot plot, int q, int r) {
        putPlot(plot, new CoordAxial(q, r));
    }

    public void putPlot(Plot plot){
        putPlot(plot, plot.getCoord());
    }

    public HashSet<CoordIrrig> getIrrigations() {
        return irrigations;
    }

    /**
     * ajoute une section d'irrigation au plateau
     * Modifie la propriété "irriguée" aux parcelles adjacentes à l'irrigation
     * @param coo
     */
    public void addIrrigation(CoordIrrig coo) {
        irrigations.add(coo);
        for (Plot p : getPlotsFromIrig(coo)){
            if (!p.haveWater()){
                p.setWater(true);
                // Lors de la première irigation, la parcelle reçoit un bambou
                p.pousserBambou();
            }

        }
    }

    /**
     * rend la liste des parcelles adjacentes
     * @param coo
     * @return
     */
    public List<Plot> getNeighbors(CoordAxial coo) {
        ArrayList<Plot> res = new ArrayList<>();
        for (CoordAxial nbc : coo.getNeighborCoords()) {
            Plot myPlot = getPlot(nbc);
            if (myPlot != null) {
                res.add(myPlot);
            }
        }
        return res;
    }

    /**
     * rend la liste des positions légales pour jouer
     * @return
     */
    public List<CoordAxial> legalPositions() {

        HashSet<CoordAxial> out = new HashSet<>();

        for (CoordAxial coo : plots.keySet()){
            out.addAll(coo.getNeighborCoords().stream().filter(this::isPositionLegal).collect(Collectors.toList()));
        }

        return new ArrayList<>(out);

    }

    /**
     * vérifie si une position est jouable
     * @param coo
     * @return
     */
    private boolean isPositionLegal(CoordAxial coo) {
        // Si une tuile est déjà présente, coo n'est pas un placement légal
        if (getPlot(coo) != null) {
            return false;
        }
        if (coo.equals(_STARTING_COORDINATE_)){
            return false;
        }
        // Si la position est adjacente à celle de départ
        if (_STARTING_COORDINATE_.getNeighborCoords().contains(coo)){
            return true;
        }

        int nbAdj = 0;
        for (CoordAxial coord : coo.getNeighborCoords()){
            if (this.getPlot(coord) != null){
                nbAdj += 1;
            }
        }
        return nbAdj >= 2;
    }

    /**
     * rend le nombre de parcelles adjacentes
     * @param coo
     * @return
     */
    public int nbAdajcent(CoordAxial coo){
        return getNeighbors(coo).size();
    }

    /**
     * rend la liste des endroits où on peut placer des irrigations
     * @return
     */
    public List<CoordIrrig> legalIrrigPositions() {
        return irrigPositionsToTest().stream().filter(this::isIrrigPositionLegal).collect(Collectors.toList());
    }

    /**
     * Renvoie la liste des tuiles adjacentes à une coordonnée d'irrigation
     * Les tuiles renvoyées sont posées sur le plateau
     * @param coordIrrig coordonnée d'irrigation
     * @return tuiles présentent sur le plateau étant adjacente à coordIrrig passée en paramètre
     */
    public List<Plot> getPlotsFromIrig(CoordIrrig coordIrrig){
        List<Plot> out = new ArrayList<>();
        List<CoordAxial> coordAxials = coordIrrig.borders();

        for (CoordAxial c : coordAxials){
            if (plots.containsKey(c)){
                out.add(plots.get(c));
            }
        }
        return out;
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

    public HashMap<CoordAxial, Plot> getPlots() {
        return plots;
    }

    /**
     * Permet de définir si une parcelle est irriguée
     * Une parcelle est automatique irrigué si elle est adjacente à la parcelle de départ
     * @param coordAxial CoordAxial les coordonnées de la parcelle
     * @return boolean true|false
     */
    public boolean checkPlotWater(CoordAxial coordAxial){
        if (coordAxial.getNeighborCoords().contains(_STARTING_COORDINATE_)){
            return true;
        }

        for (CoordIrrig c : coordAxial.getBorderCoords()){
            if (this.getIrrigations().contains(c)){
                return true;
            }
        }
        return false;
    }

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
                    if (getPlot(new CoordAxial(coo1.getQ(), coo1.getR() + i)) == null) return false;
                }
                return true;
            } else {
                for (int i = 0; i < diffCub.getS(); i++) {
                    if (getPlot(new CoordAxial(coo1.getQ(), coo1.getR() - i)) == null) return false;
                }
                return true;
            }
        } else if (diffCub.getR() == 0) {
            if (diffCub.getQ() >= 0) {
                for (int i = 0; i < diffCub.getQ(); i++) {
                    if (getPlot(new CoordAxial(coo1.getQ() + i, coo1.getR())) == null) return false;
                }
                return true;
            } else {
                for (int i = 0; i < diffCub.getS(); i++) {
                    if (getPlot(new CoordAxial(coo1.getQ() - i, coo1.getR())) == null) return false;
                }
                return true;
            }
        } else if (diffCub.getS() == 0) {
            if (diffCub.getQ() >= 0) {
                for (int i = 0; i < diffCub.getQ(); i++) {
                    if (getPlot(new CoordAxial(coo1.getQ() + i, coo1.getR() - i)) == null) return false;
                }
                return true;
            } else {
                for (int i = 0; i < diffCub.getR(); i++) {
                    if (getPlot(new CoordAxial(coo1.getQ() - i, coo1.getR() + i)) == null) return false;
                }
                return true;
            }
        } else return false;
    }

    /**
     * RELATIF AU PANDA
     */

    /**
     * Modifie la position du panda uniquement si pour la nouvelle position, il existe une parcelle sur le plateau
     * @param coord nouvelle coordonnée de la figurine panda
     * @return la couleur du bambou récupéré après le passage du panda
     */
    public Couleur movePanda(CoordAxial coord){
        if (plots.containsKey(coord) && coord.isInLine(posPanda) && hasStraightPath(coord, posPanda)){
            posPanda = coord;
            Plot current = plots.get(coord);
            if (current.getBambou() > 0 && !(current.getAmenagement().equals(Amenagement.ENCLOS))) { //Ne mange pas en cas d'enclos
                plots.get(coord).removeBambou(1);

                return plots.get(coord).getCouleur();
            }else{

                return Couleur.BLEU;
            }
        }
        // Couleur de la tuile de départ, ne rapporte pas de section au joueur.
        return Couleur.BLEU;
    }


    /**
     * RELATIF AU JARDINIER
     */

    /**
     * Fonction permettant de bouger le jardinier.
     * @param coord une coordonnée
     * @return true si le jardinier est bien bougé sinon false
     */
    public boolean moveJardinier(CoordAxial coord){
        if(coord.isInLine(posJardinier) && hasStraightPath(coord, posJardinier)&& plots.containsKey(coord)){
            posJardinier = coord;
            getPlot(coord).pousserBambou();
            for(CoordAxial coordAxial : neighborColor(coord)){
                getPlot(coordAxial).pousserBambou();
                if (getPlot(coordAxial).getAmenagement() == Amenagement.ENGRAIS){
                    getPlot(coordAxial).pousserBambou();
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Renvoie la liste de coordonnées pour lequel la couleur est identique à la tuile aux coordonnées passé en argument
     * @param coord
     * @return
     */
    public List<CoordAxial> neighborColor(CoordAxial coord){
        List<CoordAxial> adj = coord.getNeighborCoords();
        Couleur color = getPlot(coord).getCouleur();
        List<CoordAxial> res = new ArrayList<>();
        for(CoordAxial coordAxial : adj){
            if(plots.containsKey(coordAxial)) {
                if (color == getPlot(coordAxial).getCouleur()) {
                    res.add(coordAxial);
                }
            }
        }
        return res;
    }

    public List<Plot> getLinePlots(CoordAxial coord){
        return plots.values().stream().filter(p -> hasStraightPath(coord, p.getCoord())).collect(Collectors.toList());
    }

    public boolean isMotifInAll(Couleur color, int tower){
        int iterTower = 0;

        Collection<Plot> plot = plots.values();

        if(tower == 1) {
            for (Plot plot1 : plot) {
                if (plot1.getCouleur() == color && plot1.getBambou() == 4) {
                    return true;
                }
            }
            return false;
        }
        else if(tower <= 4 && tower > 1) {
            for (Plot plot1 : plot) {
                if (plot1.getCouleur() == color && plot1.getBambou() == 3) {
                    iterTower += 1;
                    if (iterTower == tower) {
                        return true;
                    }
                }
            }
            return false;
        }
        else {
            return false;
        }
    }

    @Bean
    @Scope("prototype")
    public Plateau plateauTakenoko(){
        Plateau out = new Plateau();

        Plot startingPlot = new Plot(_STARTING_COORDINATE_, Couleur.BLEU);
        out.putPlot(startingPlot);
        out.lastPlot = startingPlot;

        // On ajoute aux irrigation, le contour de la tuile de départ.
        List<CoordIrrig> borderCoords = _STARTING_COORDINATE_.getBorderCoords();
        out.irrigations.addAll(borderCoords);

        out.posPanda = _STARTING_COORDINATE_;
        out.posJardinier = _STARTING_COORDINATE_;

        out.canalIrrigation = 20;

        return out;
    }


}
