package alok.trials;

import java.util.Arrays;

/**
 * Created by allal on 7/23/16.
 */
public class StringSplit {
    public static void main(String[] args) {
        String input = " abc xyz 123 ";
        System.out.println(input);
        String[] result = input.split(" +");
        System.out.println(Arrays.toString(result));
    }
}
