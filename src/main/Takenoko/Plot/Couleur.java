package Takenoko.Plot;

/**
 * La classe couleur est une enumeration qui permet de savoir quel est la bonne couleur
 */
public enum Couleur {
    ROUGE("Rouge",1),VERT("Vert",2),BLEU("Bleu",3),JAUNE("Jaune",4);
    //BLANC("Blanc"),NOIR("Noir");

    private String name ="";
    private int id;

    Couleur(String name,int id) {
        this.name = name;
        this.id = id;

    }

    @Override
    public String toString() {
        return name;
    }

    public static Couleur getById(int id) {
        for(Couleur couleur : values()) {
            if(couleur.id == id){
                return couleur;
            }
        }
        return null;
    }

}
