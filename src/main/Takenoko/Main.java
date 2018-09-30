package Takenoko;

import Takenoko.Util.Console;

public class Main {
    public static void main(String[] args){
        // On initialise la console avec le mode souhaité {release, debug, test}
        String consoleModeLabel = args[0];
        Console.Log.init(consoleModeLabel);

        Game partie = new Game();

        partie.play();
    }
}
