package Takenoko.Objectives;

import Takenoko.Joueur.Joueur;
import Takenoko.Plateau;
import Takenoko.Properties.Couleur;

import java.util.EnumMap;
import java.util.Objects;

public class PandaObjectiveCard extends ObjectiveCard {
    private int vertRequis;
    private int jauneRequis;
    private int roseRequis;

    private int pointValue;
    private Joueur owner;

    /**
     * getter pour la valeur en points de la carte
     * @return la valeur en points de la carte
     */
    @Override
    public int getPointValue() {
        return pointValue;
    }

    /**
     * vérifie si les conditions pour compléter l'objectif sont remplies
     * @return vrai si l'objectif est achevé, faux sinon
     */
    @Override
    public boolean isComplete() {
        return (owner.getBambousVerts() >= vertRequis && owner.getBambousJaunes() >= jauneRequis && owner.getBambousRoses() >= roseRequis);
    }

    /**
     * valide un objectif, effectue les changements à l'état du jeu correspondant à la validation de la carte
     * @return la valeur en points de la carte
     */
    @Override
    public int validate() {
        owner.setBambousVerts(owner.getBambousVerts() - vertRequis);
        owner.setBambousJaunes(owner.getBambousJaunes() - jauneRequis);
        owner.setBambousRoses(owner.getBambousRoses() - roseRequis);
        return pointValue;
    }

    public EnumMap<Couleur, Integer> countRequired(){
        EnumMap<Couleur, Integer> out = new EnumMap<Couleur, Integer>(Couleur.class);

        out.put(Couleur.VERT, vertRequis);
        out.put(Couleur.JAUNE, jauneRequis);
        out.put(Couleur.ROSE, roseRequis);

        return out;
    }

    /**
     * Constructeur, prend le nombre de bambous de chaque couleur correspondant à l'objectif
     * ainsi que sa valeur en points
     * @param vert le nombre de bambous verts
     * @param jaune le nombre de bambous jaunes
     * @param rose le nombre de bambous roses
     * @param value la valeur en points
     */
    public PandaObjectiveCard(int vert, int jaune, int rose, int value) {
        vertRequis = vert;
        jauneRequis = jaune;
        roseRequis = rose;
        pointValue = value;
    }

    /**
     * met à jour la carte lorsqu'elle est piochée pour qu'elle ait une référence au plateau et à son propriétaire
     * @param plateau le plateau de jeu
     * @param joueur le joueur propriétaire de la carte
     */
    @Override
    public void instanciate(Plateau plateau, Joueur joueur) {
        owner = joueur;
        joueur.addPandaObjectiveCard(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PandaObjectiveCard that = (PandaObjectiveCard) o;
        return vertRequis == that.vertRequis &&
                jauneRequis == that.jauneRequis &&
                roseRequis == that.roseRequis &&
                pointValue == that.pointValue &&
                Objects.equals(owner, that.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertRequis, jauneRequis, roseRequis, pointValue, owner);
    }
}
