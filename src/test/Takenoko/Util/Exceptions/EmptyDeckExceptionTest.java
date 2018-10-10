package Takenoko.Util.Exceptions;

import org.junit.Test;

public class EmptyDeckExceptionTest {

    @Test (expected = EmptyDeckException.class)
    public void throwException() throws EmptyDeckException {
        throw new EmptyDeckException();
    }
}