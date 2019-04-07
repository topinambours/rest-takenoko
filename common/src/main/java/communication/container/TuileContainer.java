package communication.container;

import takenoko.tuile.Tuile;

import java.util.List;

/**
 * Utilisé pour transporter les objets de type tuile
 */
public class TuileContainer extends Container<Tuile> {

    public TuileContainer(){
        super();
    }

    public TuileContainer(Tuile single){
        super(single);
    }

    public TuileContainer(List<Tuile> multiple){
        super(multiple);
    }
}
