package Takenoko;

import java.util.Random;

public enum WeatherDice {
    SUN(0), RAIN(1), WIND(2), THUNDER(3), CLOUD(4), PLAYER_DECIDE(5);

    private final int id;

    WeatherDice(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static WeatherDice throwDice(){
        int returnId = new Random().nextInt(6);
        return WeatherDice.values()[returnId];
    }

    @Override
    public String toString() {
        switch (this) {
            case SUN: return "Soleil";
            case RAIN: return "Pluie";
            case WIND: return "Vent";
            case THUNDER: return "Orage";
            case CLOUD: return "Nuages";
            default: return "?";
        }
    }
}
