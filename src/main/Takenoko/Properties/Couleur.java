package takenoko.properties;

/**
 * La classe couleur est une enumeration qui permet de savoir quel est la bonne couleur
 * Les couleurs disponibles étant :
 * <ul>
 *     <li>Vert(id : 0)</li>
 *     <li>Rose(id : 1)</li>
 *     <li>Jaune(id : 2)</li>
 *     <li>Bleu(id : 3) Uniquement pour le lac</li>
 * </ul>
 */
public enum Couleur {
    VERT("Vert",0),
    ROSE("Rose",1),
    JAUNE("Jaune",2),
    BLEU("Bleu(Lac)",3);
    //BLANC("Blanc"),NOIR("Noir");

    private String name;
    private int id;

    Couleur(String name,int id) {
        this.name = name;
        this.id = id;

    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Permet d'avoir l'id d'une couleur
     * @param id int l'id
     * @return Couleur la couleur correspondante à l'id
     */
    public static Couleur getById(int id) {
        for(Couleur couleur : values()) {
            if(couleur.id == id){
                return couleur;
            }
        }
        return null;
    }


}
