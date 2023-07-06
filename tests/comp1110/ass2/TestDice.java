package comp1110.ass2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Timeout(value = 2000, unit = TimeUnit.MILLISECONDS)
public class TestDice {
    Player playerRollDice = new Player();

    public String toString(int[] resource) {
        String output = "";
        for (int i = 0; i < resource.length; i++) {
            if (i != resource.length -1)
                output += resource[i] + ", ";
            if (i == resource.length -1)
                output += resource[i];
        }
        return output;
    }

    @Test
    public void testIncorrectSelection() {
        boolean result = true;
        playerRollDice.resource = new int[]{1, 0, 1, 2, 0, 2};
        String resourceBefore = toString(playerRollDice.resource);
        playerRollDice.re_roll(new int[]{2, 0, 0, 0, 0, 0});
        String resourceAfter = toString(playerRollDice.resource);
        result = !Objects.equals(resourceBefore, resourceAfter);
        Assertions.assertEquals("false", String.valueOf(result), "Incorrect Selection");

        playerRollDice.resource = new int[]{1, 0, 1, 2, 0, 2};
        resourceBefore = toString(playerRollDice.resource);
        playerRollDice.re_roll(new int[]{0, 2, 0, 2, 0, 0});
        resourceAfter = toString(playerRollDice.resource);
        result = !Objects.equals(resourceBefore, resourceAfter);
        Assertions.assertEquals("false", String.valueOf(result), "Incorrect Selection");

        playerRollDice.resource = new int[]{1, 0, 1, 2, 0, 2};
        resourceBefore = toString(playerRollDice.resource);
        playerRollDice.re_roll(new int[]{0, 0, 0, 2, 1, 0});
        resourceAfter = toString(playerRollDice.resource);
        result = !Objects.equals(resourceBefore, resourceAfter);
        Assertions.assertEquals("false", String.valueOf(result), "Incorrect Selection");
    }

    @Test
    public void testCorrectSelection() {
        boolean result = true;
        playerRollDice.resource = new int[]{1, 0, 1, 2, 0, 2};
        String resourceBefore = toString(playerRollDice.resource);
        playerRollDice.re_roll(new int[]{1, 0, 0, 0, 0, 0});
        String resourceAfter = toString(playerRollDice.resource);
        result = !Objects.equals(resourceBefore, resourceAfter);
        System.out.println(resourceAfter);
        Assertions.assertEquals("true", String.valueOf(result), "Incorrect resource");

        playerRollDice.resource = new int[]{1, 0, 1, 2, 0, 2};
        resourceBefore = toString(playerRollDice.resource);
        playerRollDice.re_roll(new int[]{0, 0, 0, 2, 0, 1});
        resourceAfter = toString(playerRollDice.resource);
        System.out.println(resourceAfter);
        result = !Objects.equals(resourceBefore, resourceAfter);
        Assertions.assertEquals("true", String.valueOf(result), "Incorrect resource");

        playerRollDice.resource = new int[]{1, 0, 1, 2, 0, 2};
        resourceBefore = toString(playerRollDice.resource);
        playerRollDice.re_roll(new int[]{1, 0, 1, 2, 0, 2});
        resourceAfter = toString(playerRollDice.resource);
        System.out.println(resourceAfter);
        result = !Objects.equals(resourceBefore, resourceAfter);
        Assertions.assertEquals("true", String.valueOf(result), "Incorrect resource");
    }
}
