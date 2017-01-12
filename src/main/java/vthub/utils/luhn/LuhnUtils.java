/*
 * MIT License / Copyright (c) 2017 Vasilii Trofimchuk
 */

package vthub.utils.luhn;

import org.apache.commons.lang.ArrayUtils;

import java.util.List;
import java.util.Map;
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
        Map<Boolean, List<Integer>> evenAndOdd = IntStream.range(0, ints.length)
                .mapToObj(Integer::valueOf)
                .collect(Collectors.partitioningBy(i -> i % 2 == 0));

        int evenSum = evenAndOdd.get(true).stream()
                .map(i -> ints[ints.length - i - 1])
                .mapToInt(Integer::intValue)
                .sum();
        int oddSum = evenAndOdd.get(false).stream().map(i -> ints[ints.length - i - 1])
            .map(i -> i * 2)
            .map(i -> i > 9 ? i - 9 : i)
            .mapToInt(Integer::intValue)
            .sum();

        return (oddSum + evenSum) % 10;
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
