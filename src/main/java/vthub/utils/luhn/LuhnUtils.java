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
                .map(i -> ints.length - i - 1) // reverse the order of iteration
                .map(ri -> {
                    int o = ints[ints.length - ri -1];
                    if (ri % 2 != 0) {
                        o += o;
                        o = o > 9 ? o - 9 : o;
                    }
                    return o;
                })
                .sum() % 10;
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
