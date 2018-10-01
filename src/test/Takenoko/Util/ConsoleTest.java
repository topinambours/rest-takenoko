package Takenoko.Util;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class ConsoleTest {

    @BeforeClass
    public static void setUp() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void init() {
        Console.Log.init();
        
        assertFalse(Console.Log.print("test"));
        assertFalse(Console.Log.println("test"));
        assertFalse(Console.Log.debugPrint("test"));
        assertFalse(Console.Log.debugPrintln("test"));
    }

    @Test
    public void initTest() {
        Console.Log.init("test");

        assertFalse(Console.Log.print("test"));
        assertFalse(Console.Log.println("test"));
        assertFalse(Console.Log.debugPrint("test"));
        assertFalse(Console.Log.debugPrintln("test"));

    }

    @Test
    public void initRelease() {
        Console.Log.init("release");

        assertTrue(Console.Log.print("test"));
        assertTrue(Console.Log.println("test"));
        assertFalse(Console.Log.debugPrint("test"));
        assertFalse(Console.Log.debugPrintln("test"));

    }

    @Test
    public void initDebug() {
        Console.Log.init("debug");

        assertTrue(Console.Log.print("test"));
        assertTrue(Console.Log.println("test"));
        assertTrue(Console.Log.debugPrint("test"));
        assertTrue(Console.Log.debugPrintln("test"));

    }
}