package vthub.utils.luhn;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.function.Consumer;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static vthub.utils.luhn.LuhnUtils.isLuhnChecksumValid;
import static vthub.utils.luhn.LuhnUtils.luhnCheckDigit;
import static vthub.utils.luhn.LuhnUtils.luhnChecksum;
import static vthub.utils.luhn.TestUtils.stringToInts;

public class LuhnUtilsExtendedTest
{

    private static final String VALID = "valid.txt";
    private static final String INVALID = "invalid.txt";

    @Test
    public void testIsLuhnChecksumValid_IntArray_Extended() throws Exception {
        forEachInFile(VALID, s -> assertThat(s + " is valid luhn number but check failed", isLuhnChecksumValid(stringToInts(s)), is(true)));
        forEachInFile(INVALID, s -> assertThat(s + " is invalid luhn number but check was successful", isLuhnChecksumValid(stringToInts(s)), is(false)));
    }

    @Test
    public void testIsLuhnChecksumValid_String_Extended() throws Exception {
        forEachInFile(VALID, s -> assertThat(s + " is valid luhn number but check failed", isLuhnChecksumValid(s), is(true)));
        forEachInFile(INVALID, s -> assertThat(s + " is invalid luhn number but check was successful", isLuhnChecksumValid(s), is(false)));
    }

    @Test
    public void testLuhnChecksum_IntArray_Extended() throws Exception {
        forEachInFile(VALID, s -> assertThat("Luhn check sum for " + s + " is expected to be 0", luhnChecksum(stringToInts(s)), is(0)));
        forEachInFile(INVALID, s -> assertThat("Luhn check sum for " + s + " is expected not to be 0", luhnChecksum(stringToInts(s)), is(not(0))));
    }

    @Test
    public void testLuhnChecksum_String_Extended() throws Exception {
        forEachInFile(VALID, s -> assertThat("Luhn check sum for " + s + " is expected to be 0", luhnChecksum(s), is(0)));
        forEachInFile(INVALID, s -> assertThat("Luhn check sum for " + s + " is expected not to be 0", luhnChecksum(s), is(not(0))));
    }

    @Test
    public void testLuhnCheckDigit_IntArray_Extended() throws Exception {
        Files.lines(Paths.get(ClassLoader.getSystemResource("valid.txt").toURI()))
                .map(s -> new AbstractMap.SimpleEntry<>(s.substring(0, s.length() - 1), s.charAt(s.length() - 1) - '0'))
                .forEach(e -> assertThat(e.getKey() + " is expected to have " + e.getValue() + " luhn check digit", luhnCheckDigit(stringToInts(e.getKey())), is(e.getValue())));
    }

    @Test
    public void testLuhnCheckDigit_String_Extended() throws Exception {
        Files.lines(Paths.get(ClassLoader.getSystemResource("valid.txt").toURI()))
                .map(s -> new AbstractMap.SimpleEntry<>(s.substring(0, s.length() - 1), s.charAt(s.length() - 1) - '0'))
                .forEach(e -> assertThat(e.getKey() + " is expected to have " + e.getValue() + " luhn check digit", luhnCheckDigit(e.getKey()), is(e.getValue())));
    }

    private void forEachInFile(String file, Consumer<String> consumer) throws Exception{
        Files.lines(Paths.get(ClassLoader.getSystemResource(file).toURI())).filter(StringUtils::isNotEmpty).forEach(consumer);
    }

}
