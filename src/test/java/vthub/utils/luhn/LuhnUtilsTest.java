package vthub.utils.luhn;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static vthub.utils.luhn.LuhnUtils.*;
import static vthub.utils.luhn.TestUtils.stringToInts;

public class LuhnUtilsTest
{

    private static final String VALID_LUHN = "4534987582177718";
    private static final String INVALID_LUHN = "6706386224805935";

    @Test
    public void testIsLuhnChecksumValid() throws Exception
    {
        assertThat(isLuhnChecksumValid(stringToInts(VALID_LUHN)), is(true));
        assertThat(isLuhnChecksumValid(stringToInts(INVALID_LUHN)), is(false));

        assertThat(isLuhnChecksumValid(VALID_LUHN), is(true));
        assertThat(isLuhnChecksumValid(INVALID_LUHN), is(false));
    }

    @Test
    public void testLuhnChecksum() throws Exception
    {
        assertThat(luhnChecksum(stringToInts(VALID_LUHN)), is(0));
        assertThat(luhnChecksum(stringToInts(INVALID_LUHN)), is(not(0)));

        assertThat(luhnChecksum(VALID_LUHN), is(0));
        assertThat(luhnChecksum(INVALID_LUHN), is(not(0)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLuhnChecksum_InvalidInput_10() throws Exception
    {
        luhnChecksum(0, 1, 2, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLuhnChecksum_InvalidInput_InvalidSymbol() throws Exception
    {
        luhnChecksum("6777-99");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLuhnChecksum_NullSafety() throws Exception
    {
        luhnChecksum();
    }

    @Test(expected = NullPointerException.class)
    public void testLuhnChecksum_IntArray_NullInput() throws Exception {
        luhnChecksum((int[])null);
    }

    @Test(expected = NullPointerException.class)
    public void testLuhnChecksum_String_NullInput() throws Exception {
        luhnChecksum((String)null);
    }

    @Test
    public void testLuhnCheckDigit() throws Exception
    {
        assertThat(luhnCheckDigit(stringToInts("453498758217771")), is(8));
        assertThat(luhnCheckDigit(stringToInts("601148127210898")), is(2));
        assertThat(luhnCheckDigit(stringToInts("670638622480593")), is(4));
    }

}
