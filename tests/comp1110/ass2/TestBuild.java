package comp1110.ass2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

@Timeout(value = 2000, unit = TimeUnit.MILLISECONDS)
public class TestBuild {
    Player playerForTest = new Player();

    @Test
    public void testIncorrectFormat() {
        playerForTest.board = "";
        playerForTest.resource = new int[]{10, 10, 10, 10, 10, 10};
        playerForTest.build("");
        Assertions.assertEquals("", playerForTest.board, "Incorrect format action does not remain the board same as before");

        playerForTest.build("gfdsf");
        Assertions.assertEquals("", playerForTest.board, "Incorrect format action does not remain the board same as before");

        playerForTest.build("Build R0");
        Assertions.assertEquals("", playerForTest.board, "Incorrect format action does not remain the board same as before");

        playerForTest.build("Build R0,R1,R2,S3");
        Assertions.assertEquals("", playerForTest.board, "Incorrect format action does not remain the board same as before");
        playerForTest.build("Build R0R1R2S3");
        Assertions.assertEquals("", playerForTest.board, "Incorrect format action does not remain the board same as before");
    }

    @Test
    public void testLackResources() {
        playerForTest.board = "";
        playerForTest.resource = new int[]{0, 0, 0, 1, 0, 0};
        playerForTest.build("build R0");
        Assertions.assertEquals("", playerForTest.board, "lack resources situation does not remain the board same as before");

        playerForTest.board = "";
        playerForTest.resource = new int[]{0, 0, 0, 0, 1, 0};
        playerForTest.build("build R0");
        Assertions.assertEquals("", playerForTest.board, "lack resources situation does not remain the board same as before");

        playerForTest.board = "";
        playerForTest.resource = new int[]{0, 0, 8, 0, 1, 0};
        playerForTest.build("build S3");
        Assertions.assertEquals("", playerForTest.board, "lack resources situation does not remain the board same as before");
    }

    @Test
    public void testNotAvailable() {
        playerForTest.board = "";
        playerForTest.resource = new int[]{0, 0, 0, 1, 1, 0};
        playerForTest.build("build R2");
        Assertions.assertEquals("", playerForTest.board, "Not available situation does not remain the board same as before");

        playerForTest.board = "R0,R1";
        playerForTest.resource = new int[]{1, 1, 1, 1, 1, 1};
        playerForTest.build("build S4");
        Assertions.assertEquals("R0,R1", playerForTest.board, "Not available situation does not remain the board same as before");

        playerForTest.board = "R0,R2";
        playerForTest.resource = new int[]{1, 1, 1, 1, 1, 1};
        playerForTest.build("build S7");
        Assertions.assertEquals("R0,R2", playerForTest.board, "Not available situation does not remain the board same as before");
    }

    @Test
    public void testSuccess() {
        playerForTest.board = "";
        playerForTest.resource = new int[]{0, 0, 0, 1, 1, 0};
        Assertions.assertTrue(playerForTest.build("build R0"), "Build failed.");
    }
}

