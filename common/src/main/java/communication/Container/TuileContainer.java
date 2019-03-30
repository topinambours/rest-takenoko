package communication.Container;

import takenoko.tuile.Tuile;

import java.util.ArrayList;
import java.util.List;

/**
 * Utilisé pour transporter les objets de type tuile
 */
public class TuileContainer {

    /**
     * Contenu du container, liste contenant des Tuiles
     */
    private List<Tuile> content;

    public TuileContainer() {
        this.content = new ArrayList<>();
    }

    public TuileContainer(List<Tuile> tuiles) {
        this.content = new ArrayList<>(tuiles);
    }

    public List<Tuile> getContent() {
        return content;
    }

    public void setContent(List<Tuile> content) {
        this.content = content;
    }
}
