package Takenoko;

import Takenoko.Parcel.Parcel;
import org.junit.Test;

import static org.junit.Assert.*;

public class ParcelTest {

    @Test
    public void getq() {
        Parcel p = new Parcel(10,15);
        assertEquals(10, p.getq());
    }

    @Test
    public void getr() {
        Parcel p = new Parcel(10,15);
        assertEquals(15, p.getr());
    }

    @Test
    public void setCoord() {
        Parcel p = new Parcel(10,15);
        assertEquals(10, p.getq());
        assertEquals(15, p.getr());

        p.setCoord(15,10);
        assertEquals(15, p.getq());
        assertEquals(10, p.getr());
    }
}