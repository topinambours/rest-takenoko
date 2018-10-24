package Takenoko.Objectives.Amenagement;

/**
 * Ajout des aménagements qui sont les suivants :
 * <ul>
 *     <li>ENCLOS</li>
 *     <li>ENGRAIS</li>
 *     <li>BASSIN</li>
 * </ul>
 * Il y a aussi NON lorsque la carte n'a pas d'amenagement
 */
public enum Amenagement {

    NON("NON"),ENCLOS("ENCLOS"),ENGRAIS("ENGRAIS"),BASSIN("BASSIN");

    private String string;

    Amenagement(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }



}
