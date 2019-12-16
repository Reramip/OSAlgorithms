package resourceallocation;

import java.util.Arrays;

public class Banker {
    private final int[] resource;
    private final int[][] claim;
    private final int[][] allocation;

    public Banker(int[] resource, int[][] claim, int[][] allocation) {
        this.resource = resource;
        this.claim = claim;
        this.allocation = allocation;
    }

    public boolean judgeSafe() {
        final int INF = 65535;

        int processNum = claim.length;
        int resourceNum = resource.length;

        int[] available = Arrays.copyOf(resource, resourceNum);
        for (int[] alloc : allocation) {
            for (int i = 0; i < resourceNum; ++i) {
                available[i] -= alloc[i];
            }
        }
        int[][] request = new int[processNum][resourceNum];
        for (int i = 0; i < processNum; ++i) {
            for (int j = 0; j < resourceNum; ++j) {
                request[i][j] = claim[i][j] - allocation[i][j];
            }
        }

        int processesToAllocate = processNum;
        boolean safe;
        do {
            safe = false;
            for (int i = 0; i < processNum; ++i) {
                boolean ableToAllocate = true;
                for (int j = 0; j < resourceNum; ++j) {
                    if (available[j] < request[i][j]) {
                        ableToAllocate = false;
                        break;
                    }
                }
                if (ableToAllocate) {
                    for (int j = 0; j < resourceNum; ++j) {
                        available[j] += allocation[i][j];
                        request[i][j] = INF;
                    }
                    safe = true;
                    processesToAllocate--;
                    break;
                }
            }
        } while (safe);

        return processesToAllocate == 0;
    }
}
