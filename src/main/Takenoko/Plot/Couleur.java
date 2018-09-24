package Takenoko.Plot;

/**
 * en prévision ...
 */
public enum Couleur {
    ROUGE("Rouge"),VERT("Vert"),BLEU("Bleu"),JAUNE("Jaune");

    private String name ="";

    Couleur(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
