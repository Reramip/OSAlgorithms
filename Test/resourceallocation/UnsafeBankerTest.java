package resourceallocation;

import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UnsafeBankerTest {
    int[][] unsafeClaim;
    int[][] unsafeAllocation;
    int[] unsafeResource;

    @Before
    public void setUp() {
        unsafeResource = new int[]{9, 3, 6};
        unsafeClaim = new int[][]{
                {3, 2, 2},
                {6, 1, 3},
                {3, 1, 4},
                {4, 2, 2}
        };
        unsafeAllocation = new int[][]{
                {2, 0, 1},
                {5, 1, 1},
                {2, 1, 1},
                {0, 0, 2}
        };
    }

    @Test
    public void testUnsafeBanker() {
        assertFalse(new Banker(unsafeResource, unsafeClaim, unsafeAllocation).judgeSafe());
    }
}
