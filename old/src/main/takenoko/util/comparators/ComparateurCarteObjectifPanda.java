package takenoko.util.comparators;

import takenoko.objectives.PandaObjectiveCard;

import java.util.Comparator;

public class ComparateurCarteObjectifPanda implements Comparator<PandaObjectiveCard> {

    private int yellowInBank;
    private int greenInBank;
    private int pinkInBank;

    public ComparateurCarteObjectifPanda(int yellowInBank, int greenInBank, int pinkInBank){
        this.yellowInBank = yellowInBank;
        this.greenInBank = greenInBank;
        this.pinkInBank = pinkInBank;
    }

    @Override
    public int compare(PandaObjectiveCard o1, PandaObjectiveCard o2) {
        int totalNeeded1 = o1.getJauneRequis() + o1.getRoseRequis() + o1.getVertRequis() - yellowInBank - greenInBank - pinkInBank;

        int totalNeeded2 = o2.getJauneRequis() + o2.getRoseRequis() + o2.getVertRequis() - yellowInBank - greenInBank - pinkInBank;

        return totalNeeded1 - totalNeeded2;
    }
}
