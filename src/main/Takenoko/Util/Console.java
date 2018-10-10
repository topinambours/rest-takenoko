package Takenoko.Util;

import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Console encadrant l'affichage des messages en fonction du mode de lancement de l'application.
 *
 * /!\ Singleton, instancié lors du premier appel les champs ont une porté statique global /!\
 */
public enum Console {
    Log;

    /**
     * Les différents modes de consoles
     * release  -> affichage dédié à l'utilisateur final
     * debug    -> affichage pour les développeurs, messages masqués à l'utilisateur final
     * test     -> aucun affichage
     */
    public enum Mode{
        PROD, DEBUG, TEST;
    }

    // mode test par défaut pour éviter une classe runner pour les tests unitaires
    private Mode mode = Mode.TEST;

    // Objet qui écrit sur le flux standard
    private OutputStreamWriter out;

    /**
     * Initalisation de la console
     * @param modeLabel mode dans lequel sera la console
     * @return vrai si la console a été initalisée
     */
    public boolean init(String modeLabel){
        switch (modeLabel){
            case "release" : this.mode = Mode.PROD; break;
            case "debug" : this.mode = Mode.DEBUG;break;
            case "test" : this.mode = Mode.TEST;break;
            default: throw new IllegalArgumentException("Wrong console mode given must be one of {release, debug, test}");
        }
        this.out = new OutputStreamWriter(System.out);
        return true;
    }

    /**
     * Initalisation par défaut en mode test
     * @return vrai si la console a été initialisée
     */
    public boolean init(){
        return init("test");
    }

    /**
     * Affiche sur l'entrée standard le message str
     * En mode test, aucun affichage.
     * @param str message à afficher
     * @return vrai si le message a été affiché
     */
    public boolean print(String str){
        if (this.mode != Mode.TEST) {
                try {
                    out.append(str);
                    out.flush();
                } catch (IOException e) {
                    debugPrint(e.getMessage());
                }
                return true;
            }
            return false;
    }

    /**
     * Comportement identique à la méthode {@link #print(String) print(String str)}
     * Ajoute un retour chariot à la fin du message str
     * @param str
     * @return vrai si le message a été affiché
     */
    public boolean println(String str){
        return print(str + "\n");
    }

    /**
     * N'affiche le message que si la console est en mode debug
     * Aucun affichage en mode test
     * @param str message à afficher
     * @return vrai si le message a été affiché
     */
    public boolean debugPrint(String str){
        if (this.mode == Mode.DEBUG) {
            return print("[DEBUG]" + str);
        }
        return false;
    }

    /**
     * Comportement identique à la méthode {@link #debugPrint(String)} (String) debugPrint(String str)}
     * Ajoute un retour chariot à la fin du message str
     * @param str message à afficher
     * @return vrai si le message a été affiché
     */
    public boolean debugPrintln(String str){
        return debugPrint(str + "\n");
    }

}
