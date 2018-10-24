package takenoko;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WeatherDiceTest {

    /**
     * On test pour un million de lancer.
     * Facteur de confiance, 1‰ pour 1 million de lancers.
     */
    @Test
    public void throwDice() {
        int[] trowsById = new int[6];

        for (int i = 0; i < 1000000; i++){
            WeatherDice value = WeatherDice.throwDice();
            trowsById[value.getId()] += 1;
        }

        int trowsSum = 0;

        for (int i = 0; i < 6; i++) {
            trowsSum += trowsById[i];
            assertEquals(1.0 / 6, trowsById[i] / 1000000.0 , 0.001);
        }

        assertEquals(1000000, trowsSum);
    }

    @Test
    public void getId() {
        assertEquals(0, WeatherDice.SUN.getId());
        assertEquals(1, WeatherDice.RAIN.getId());
        assertEquals(2, WeatherDice.WIND.getId());
        assertEquals(3, WeatherDice.THUNDER.getId());
        assertEquals(4, WeatherDice.CLOUD.getId());
        assertEquals(5, WeatherDice.PLAYER_DECIDE.getId());
    }

    @Test
    public void toStringTest() {
        assertEquals("Soleil", WeatherDice.SUN.toString());
        assertEquals("Pluie", WeatherDice.RAIN.toString());
        assertEquals("Vent", WeatherDice.WIND.toString());
        assertEquals("Orage", WeatherDice.THUNDER.toString());
        assertEquals("Nuages", WeatherDice.CLOUD.toString());
        assertEquals("?", WeatherDice.PLAYER_DECIDE.toString());
    }
}