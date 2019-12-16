package resourceallocation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SafeBankerTest {
    int[][] safeClaim;
    int[][] safeAllocation;
    int[] safeResource;

    @Before
    public void setUp() {
        safeResource = new int[]{9, 3, 6};
        safeClaim = new int[][]{
                {3, 2, 2},
                {6, 1, 3},
                {3, 1, 4},
                {4, 2, 2}
        };
        safeAllocation = new int[][]{
                {1, 0, 0},
                {6, 1, 2},
                {2, 1, 1},
                {0, 0, 2}
        };
    }

    @Test
    public void testSafeBanker() {
        assertTrue(new Banker(safeResource, safeClaim, safeAllocation).judgeSafe());
    }
}
