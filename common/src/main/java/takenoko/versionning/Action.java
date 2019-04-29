package takenoko.versionning;

/**
 * Anumeration des différentes actions possibles
 * <ul>
 *     <li>PUTPLOT : Ajout de plot sur le plateau</li>
 *     <li>ADDIRRIG : Ajout d'irrigation</li>
 *     <li>MOOVEPENDA : Deplacement du panda a une position donnée</li>
 *     <li>BAMBOOGROW : faire pousser un bambou</li>
 *     <li>EATBAMBOO : la panda mange un bambou</li>
 * </ul>
 */
public enum Action {
    PUTPLOT,
    ADDIRRIG,
    MOOVEPANDA,
    MOOVEGARDENER,
    BAMBOOGROW,
    EATBAMBOO;
}
