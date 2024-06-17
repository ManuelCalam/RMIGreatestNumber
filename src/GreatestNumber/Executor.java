/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GreatestNumber;

/**
 *
 * @author DELL
 */
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Executor {
    public int lookForGreatest(int[][] array, int rows, int columns) {
        int greatest = Integer.MIN_VALUE;

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        // Hacer el cálculo y buscar el mayor valor en la misma iteración
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int squaredValue = array[i][j] * array[i][j];
                if (squaredValue > greatest) {
                    greatest = squaredValue;
                }
            }
        }

        executorService.shutdown();
        try {
            // Esperar a que se completen todos los cálculos
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }

        return greatest;
    }
}

