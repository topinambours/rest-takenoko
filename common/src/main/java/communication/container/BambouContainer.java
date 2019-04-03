package communication.container;

import takenoko.Couleur;
import takenoko.tuile.Tuile;

import java.util.ArrayList;
import java.util.List;

/**
 * Un bambouContainer est une classe permettant de transporter les 3 types de bambous (par couleur)
 */
public class BambouContainer extends Container<Integer> {

    public BambouContainer(){
        super();
    }

    public BambouContainer(int nbVert, int nbJaune, int nbRose){
        super(nbVert, nbJaune, nbRose);
    }

    public BambouContainer(Couleur c, int n){
        this(
                c == Couleur.VERT ? n : 0,
                c == Couleur.JAUNE ? n : 0,
                c == Couleur.ROSE ? n : 0
                );
    }

    public int getBambouByColor(Couleur couleur){
        switch (couleur){
            case VERT: return content.get(0);
            case JAUNE: return content.get(1);
            case ROSE: return content.get(2);
        }
        return 0;
    }

}
