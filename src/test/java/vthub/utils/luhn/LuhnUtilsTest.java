package vthub.utils.luhn;

import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static vthub.utils.luhn.LuhnUtils.*;

public class LuhnUtilsTest
{

    @Test
    public void testIsLuhnChecksumValid() throws Exception
    {
        assertThat(isLuhnChecksumValid(stringToInts("4534987582177718")), is(true));
        assertThat(isLuhnChecksumValid(stringToInts("6011481272108982")), is(true));
        assertThat(isLuhnChecksumValid(stringToInts("6706386224805934")), is(true));
        assertThat(isLuhnChecksumValid(stringToInts("6706386224805935")), is(false));
    }

    @Test
    public void testLuhnChecksum() throws Exception
    {
        assertThat(luhnChecksum(stringToInts("4534987582177718")), is(0));
        assertThat(luhnChecksum(stringToInts("6011481272108982")), is(0));
        assertThat(luhnChecksum(stringToInts("6706386224805934")), is(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLuhnChecksum_InvalidInput() throws Exception
    {
        luhnChecksum(0, 1, 2, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLuhnChecksum_NullSafety() throws Exception
    {
        luhnChecksum();
    }

    @Test(expected = NullPointerException.class)
    public void testLuhnChecksum_NullInput() throws Exception {
        luhnChecksum(null);
    }

    @Test
    public void testLuhnCheckDigit() throws Exception
    {
        assertThat(luhnCheckDigit(stringToInts("453498758217771")), is(8));
        assertThat(luhnCheckDigit(stringToInts("601148127210898")), is(2));
        assertThat(luhnCheckDigit(stringToInts("670638622480593")), is(4));
    }

    int[] stringToInts(String s)
    {
        return Optional.ofNullable(s).orElse("")
                .chars()
                .map(c -> Integer.valueOf(Character.toString((char) c)))
                .toArray();
    }
}
