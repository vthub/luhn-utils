/*
 * MIT License / Copyright (c) 2017 Vasilii Trofimchuk
 */

package vthub.utils.luhn;

import org.apache.commons.lang.ArrayUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LuhnUtils
{

    private LuhnUtils()
    {
    }

    /**
     * Calculates the Luhn checksum of the provided sequence
     * @param ints sequence of numbers in a range 0-9
     * @return Luhn checksum
     */
    public static int luhnChecksum(int... ints)
    {
        return IntStream.range(0, ints.length)
                .map(i -> luhn(i, ints))
                .sum() % 10;
    }

    protected static int luhn(int i, int[] numbers) {
        int o;
        int ri = numbers.length - i - 1;
        if (ri % 2 == 0) {
            o = numbers[numbers.length - ri -1];
        } else {
            o = numbers[numbers.length - ri -1] * 2;
            o = o > 9 ? o - 9 : o;
        }
        return o;
    }

    /**
     * Calculates the check digit that will make Luhn checksum of the result sequence zero
     * @param ints sequence the check digit to be calculated for
     * @return check digit
     */
    public static int luhnCheckDigit(int... ints)
    {
        int checksum = luhnChecksum(ArrayUtils.add(ints, 0));
        return checksum == 0 ? checksum : 10 - checksum;
    }

}
