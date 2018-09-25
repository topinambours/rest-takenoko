package Takenoko;

import Takenoko.Util.Console;

public class Main {
    public static void main(String[] args){
        Console.Mode m = Console.Log.getModeFromLabel(args[0]);
        Console.Log.init(m);

        Game partie = new Game();

        partie.play();
    }
}
