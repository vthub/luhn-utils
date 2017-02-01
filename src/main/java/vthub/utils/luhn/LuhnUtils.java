/*
 * MIT License / Copyright (c) 2017 Vasilii Trofimchuk
 */

package vthub.utils.luhn;

import org.apache.commons.lang.ArrayUtils;

import java.util.Optional;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

public class LuhnUtils
{

    private LuhnUtils()
    {
    }

    /**
     * Verifies that luhn checksum is valid for a given sequence
     * @param ints sequence of numbers in a range 0-9
     * @return true if checksum valid, false otherwise
     */
    public static boolean isLuhnChecksumValid(int... ints)
    {
        return luhnChecksum(ints) == 0;
    }

    /**
     * Calculates the Luhn checksum of the provided sequence
     * @param ints sequence of numbers in a range 0-9
     * @return Luhn checksum
     */
    public static int luhnChecksum(int... ints)
    {
        validateForLuhnCheck(ints);
        return luhnChecksum(i -> ints[i], ints.length);
    }

    public static int luhnChecksum(String number)
    {
        validateForLuhnCheck(number);
        return luhnChecksum(i -> number.charAt(i) - '0', number.length());
    }

    protected static int luhnChecksum(IntUnaryOperator provider, int length)
    {
        return IntStream.range(0, length)
                .map(condition(i -> i % 2 == 0 && length % 2 == 0, i -> provider.applyAsInt(i)*2, provider::applyAsInt))
                .map(condition(v -> v > 9, v -> v - 9))
                .sum() % 10;
    }

    /**
     * Executes specific function depending on the results of predicate evaluation
     * @param ifTrue predicate to be tested
     * @param thenDo function to be executed if predicate is evaluated to <code>true</code>
     * @param elseDo function to be executed if predicate is evaluated to <code>false</code>
     * @return result of either <code>thenDo</code> or <code>elseDo</code> function
     */
    static IntUnaryOperator condition(IntPredicate ifTrue, IntUnaryOperator thenDo, IntUnaryOperator elseDo) {
        return t -> ifTrue.test(t) ? thenDo.applyAsInt(t) : elseDo.applyAsInt(t);
    }

    static IntUnaryOperator condition(IntPredicate ifTrue, IntUnaryOperator thenDo) {
        return condition(ifTrue, thenDo, a -> a);
    }

    static void validateForLuhnCheck(int... ints) {
        if(ints == null) {
            throw new NullPointerException("Input array for Luhn check cannot be null");
        }
        if(ints.length == 0) {
            throw new IllegalArgumentException("Input array for Luhn check cannot be empty");
        }
        if(IntStream.of(ints).anyMatch(i -> i > 9 || i < 0)) {
            throw new IllegalArgumentException("Input numbers for Luhn check should be in a range [0,9]");
        }
    }

    protected static void validateForLuhnCheck(String number) {
        IntStream checkStream = Optional.ofNullable(number)
                .map(String::chars)
                .orElseThrow(() -> new NullPointerException("Input string for Luhn check cannot be null"));
        if(number.length() == 0) {
            throw new IllegalArgumentException("Input string for Luhn check cannot be empty");
        }
        if(checkStream.anyMatch(i -> i > '9' || i < '0')) {
            throw new IllegalArgumentException("Input numbers for Luhn check should be in a range [0,9]");
        }
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
