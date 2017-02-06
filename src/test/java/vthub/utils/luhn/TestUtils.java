package vthub.utils.luhn;

import java.util.Optional;

class TestUtils
{

    static int[] stringToInts(String s)
    {
        return Optional.ofNullable(s).orElse("")
                .chars()
                .map(c -> Integer.valueOf(Character.toString((char) c)))
                .toArray();
    }

}
