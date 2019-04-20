package communication.container;


import takenoko.Couleur;

import java.util.List;

public class ColorContainer extends Container<Couleur> {

    public ColorContainer() {
    }

    public ColorContainer(Couleur single) {
        super(single);
    }

    public ColorContainer(List<Couleur> multipleElement) {
        super(multipleElement);
    }
}
