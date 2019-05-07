package takenoko.irrigation;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OrientTest {

    @Test
    public void textToEnumTest(){
        String nord = "N";
        assertEquals(Orient.N,Orient.valueOf(nord));

        String sud = "S";
        assertEquals(Orient.S,Orient.valueOf(sud));

        String west = "W";
        assertEquals(Orient.W,Orient.valueOf(west));

    }
}
