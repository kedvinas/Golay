package utils;

import java.util.Arrays;

public class StringUtils {
    public static int[] stringToBinaryArray(String input) {
        return Arrays.stream(input.replaceAll("[^01]", "").split(""))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    public static String binaryArrayToString(int[] input) {
        return Arrays.toString(input)
                .replaceAll("\\[|\\]|,|\\s", "");
    }
}
