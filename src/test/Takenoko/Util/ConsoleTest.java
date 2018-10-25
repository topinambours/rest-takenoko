package takenoko.util;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ConsoleTest {

    /**
     * Redirection du flux standard pour une éxécution silencieuse des tests
     */
    @BeforeClass
    public static void setUp() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    /**
     * Console en mode par défaut -> pas d'affichage
     */
    @Test
    public void init() {
        assertTrue(Console.Log.init());
        assertFalse(Console.Log.print("test"));
        assertFalse(Console.Log.println("test"));
        assertFalse(Console.Log.debugPrint("test"));
        assertFalse(Console.Log.debugPrintln("test"));
    }

    /**
     * Console en mode non référencé -> erreur
     */
    @Test (expected = IllegalArgumentException.class)
    public void initBadArgument() {
        Console.Log.init("bambou");
    }

    /**
     * Console en mode test -> pas d'afficage
     */
    @Test
    public void initTest() {
        assertTrue(Console.Log.init("test"));

        assertFalse(Console.Log.print("test"));
        assertFalse(Console.Log.println("test"));
        assertFalse(Console.Log.debugPrint("test"));
        assertFalse(Console.Log.debugPrintln("test"));
    }

    /**
     * Console en mode release -> affichage des messages non debug
     */
    @Test
    public void initRelease() {
        assertTrue(Console.Log.init("release"));

        assertTrue(Console.Log.print("test"));
        assertTrue(Console.Log.println("test"));
        assertFalse(Console.Log.debugPrint("test"));
        assertFalse(Console.Log.debugPrintln("test"));
    }

    /**
     * Console en mode debug -> affichage de tous les messages
     */
    @Test
    public void initDebug() {
        assertTrue(Console.Log.init("debug"));

        assertTrue(Console.Log.print("test"));
        assertTrue(Console.Log.println("test"));
        assertTrue(Console.Log.debugPrint("test"));
        assertTrue(Console.Log.debugPrintln("test"));
    }

    @Test
    public void closedStdOut() throws IOException {
        assertTrue(Console.Log.init("release"));
        Console.Log.close();
        Console.Log.println("NO");
    }
}