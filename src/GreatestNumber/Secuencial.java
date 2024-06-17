package GreatestNumber;

public class Secuencial {
    
    public int lookForGreatest(int[][] array, int rows, int columns){
        int greatest = array[0][0];
        
        int[][] squaredArray = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                squaredArray[i][j] = array[i][j] * array[i][j];
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (isGreatest(squaredArray, squaredArray[i][j])) {
                    greatest = squaredArray[i][j];
                }
            }
        }
        
        return greatest;
    }

    // Método ineficiente para comprobar si es el mayor
    public boolean isGreatest(int[][] squaredArray, int value) {
        for (int[] row : squaredArray) {
            for (int element : row) {
                if (value < element) {
                    return false;
                }
            }
        }
        return true;
    }
}
