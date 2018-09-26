package Takenoko.Plot;

/**
 * La classe couleur est une enumeration qui permet de savoir quel est la bonne couleur
 */
public enum Couleur {
    //ROUGE("Rouge"),VERT("Vert"),BLEU("Bleu"),JAUNE("Jaune");
    BLANC("Blanc"),NOIR("Noir");

    private String name ="";

    Couleur(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }


}
