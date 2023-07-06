package comp1110.ass2;

import org.junit.jupiter.api.Test;

import java.util.StringJoiner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestRe_roll {

    /**
     * Interpret the resource to the form of string, to let the resource be able to be read.
     * @param rv resource in the form of integer array.
     * @return resource in the form of string.
     */
    public String resourceVectorToString(int[] rv) {
        StringJoiner j = new StringJoiner(",");
        for (int i = 0; i < 6; i++)
            j.add(Integer.toString(rv[i]));
        return "{" + j + "}";
    }

    /**
     * Show the error when it occurs.
     * @param rv input resource
     * @return The information of the error.
     */
    private String errorPrefix(int[] rv) {
        return "Player.re_roll(" + resourceVectorToString(rv) + ")";
    }

    /**
     * Check if the return of the function is in the right form
     * @param in the input resource
     * @param out the resource bar after rolling
     */
    private void checkReturn(int[] in, int[] out) {
        String prefix = errorPrefix(in);
        int n_in = 0;
        int n_out = 0;
        for (int i = 0; i < 6; i++) {
            n_in += in[i];
            n_out += out[i];
        }
        assertEquals(n_in, n_out, prefix);
    }

    /**
     * Test if the result goes right
     * @param name the player's name
     * @param in the input resource
     */
    private void test(Player name, int[] in) {
        int[] copyOfIn = name.resource;
        name.re_roll(in);
        checkReturn(copyOfIn, name.resource);
    }

    /**
     * Test the function for 100 times to insure the function goes right.
     */
    @Test
    public void rollAgain() {
        Player name = new Player();
        name.resource = new int[]{1, 1, 1, 1, 1, 1};
        for (int k = 0; k < 100; k++) {
            int[] resource_replaced = new int[6];
            for (int i = 0; i < 6; i++) {
                if (name.resource[i] != 0) {
                    resource_replaced[i] = 1;
                }
                else {
                    resource_replaced[i] = 0;
                }
            }
            test(name, resource_replaced);
        }
    }
    public static void main(String[] args) {
        TestRe_roll tests = new TestRe_roll();
        System.out.println("testing...");
        tests.rollAgain();
        System.out.println("all done!");
    }
}
