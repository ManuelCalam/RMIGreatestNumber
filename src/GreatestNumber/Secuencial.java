package GreatestNumber;

public class Secuencial {

    public int lookForGreatest(int[][] array, int rows, int columns) {
        int greatest = array[0][0];

        // Step 1: Square each element in the array
        int[][] squaredArray = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                squaredArray[i][j] = array[i][j] * array[i][j];
            }
        }

        // Step 2: Find the greatest value with moderate redundancy
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int currentValue = squaredArray[i][j];

                // Compare with the greatest found so far
                if (currentValue > greatest) {
                    greatest = currentValue;
                }

                if (i > 0 && squaredArray[i - 1][j] > greatest) {
                    greatest = squaredArray[i - 1][j];
                }
                if (j > 0 && squaredArray[i][j - 1] > greatest) {
                    greatest = squaredArray[i][j - 1];
                }

                for (int k = 0; k < columns; k++) {
                    if (squaredArray[i][k] > greatest) {
                        greatest = squaredArray[i][k];
                    }
                }
                for (int l = 0; l < rows; l++) {
                    if (squaredArray[l][j] > greatest) {
                        greatest = squaredArray[l][j];
                    }
                }
            }
        }

        return greatest;
    }
}
