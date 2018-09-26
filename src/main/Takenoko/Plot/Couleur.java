package Takenoko.Plot;

/**
 * La classe couleur est une enumeration qui permet de savoir quel est la bonne couleur
 */
public enum Couleur {
    VERT("Vert",0,11),ROSE("Rose",1,7),JAUNE("Jaune",2,9),BLEU("Bleu(Lac)",3,1);
    //BLANC("Blanc"),NOIR("Noir");

    private String name ="";
    private int id;
    private int nb;

    Couleur(String name,int id,int nb) {
        this.name = name;
        this.id = id;
        this.nb = nb;

    }

    public static int getnb(int id) {
        for(Couleur couleur : values()) {
            if(couleur.id == id){
                return couleur.nb;
            }
        }
        return 0;
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
