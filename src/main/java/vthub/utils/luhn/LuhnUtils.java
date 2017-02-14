/*
 * MIT License / Copyright (c) 2017 Vasilii Trofimchuk
 */

package vthub.utils.luhn;

import org.apache.commons.lang.ArrayUtils;

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
     * @throws NullPointerException if provided array is a null-object
     * @throws IllegalArgumentException if provided array contains numbers that are less than 0 or greater than 9
     */
    public static boolean isLuhnChecksumValid(int... ints)
    {
        return luhnChecksum(ints) == 0;
    }

    /**
     * Verifies that luhn checksum is valid for a given sequence
     * @param number string that contains only digits. String will be validated prior to processing
     * @return true if checksum valid, false otherwise
     * @throws NullPointerException if provided string is a null-object
     * @throws IllegalArgumentException if provided string contains symbols other than digits
     */
    public static boolean isLuhnChecksumValid(String number)
    {
        return luhnChecksum(number) == 0;
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

    /**
     * Calculates the check digit that will make Luhn checksum of the result sequence zero
     * @param number string that contains only digits. String will be validated prior to processing
     * @return check digit
     */
    public static int luhnCheckDigit(String number)
    {
        int checksum = luhnChecksum(number + "0");
        return checksum == 0 ? checksum : 10 - checksum;
    }

    /**
     * Calculates the Luhn checksum of the provided sequence
     * @param ints sequence of numbers in a range 0-9
     * @return Luhn checksum
     * @throws NullPointerException if provided array is a null-object
     * @throws IllegalArgumentException if provided array contains numbers that are less than 0 or greater than 9
     */
    public static int luhnChecksum(int... ints)
    {
        validateForLuhnCheck(ints);
        return luhnChecksum(i -> ints[i], ints.length);
    }

    /**
     * Calculated the Luhn checksum for the provided string.
     * @param number string that contains only digits. String will be validated prior to processing
     * @return Luhn checksum
     * @throws NullPointerException if provided string is a null-object
     * @throws IllegalArgumentException if provided string contains symbols other than digits
     */
    public static int luhnChecksum(String number)
    {
        validateForLuhnCheck(number);
        return luhnChecksum(i -> number.charAt(i) - '0', number.length());
    }

    protected static int luhnChecksum(IntUnaryOperator provider, int length)
    {
        return IntStream.range(0, length)
                .map(condition(i -> i % 2 == length % 2, i -> provider.applyAsInt(i)*2, provider))
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

    static void validateForLuhnCheck(String number) {
        if(number == null)
        {
            throw new NullPointerException("Input string for Luhn check cannot be null");
        }
        if(number.length() == 0) {
            throw new IllegalArgumentException("Input string for Luhn check cannot be empty");
        }
        if(number.chars().anyMatch(i -> i > '9' || i < '0')) {
            throw new IllegalArgumentException("Input numbers for Luhn check should be in a range [0,9]");
        }
    }

}
