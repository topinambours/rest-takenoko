package Takenoko.Joueur.Strategie;

import Takenoko.Plateau;
import Takenoko.Plot.CoordAxial;

import java.util.List;
import java.util.Random;

public class StrategieRandom implements Strategie{

    public CoordAxial getCoord(Plateau plateau) {

        List<CoordAxial> listOfPos = plateau.legalPositions();

        //TEMPORAIRE A DELETE APRES LE COMMIT DE MATHIAS !!!!!!!
        if(listOfPos==null){
            Random random = new Random();
            return new CoordAxial(random.nextInt(100),random.nextInt(100));
        }

        return listOfPos.get(0);

    }

    public StrategieRandom() {

    }
}
