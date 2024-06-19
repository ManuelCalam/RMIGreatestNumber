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
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoin extends RecursiveTask<Integer> {
    private int[][] array;
    private int start;
    private int end;

    public ForkJoin(int[][] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if (end - start <= 2000) {
            int greatest = array[start][0];
            for (int i = start; i < end; i++) {
                for (int j = 0; j < array[i].length; j++) {
                    int squared = array[i][j] * array[i][j];
                    if (squared > greatest) {
                        greatest = squared;
                    }
                }
            }
            return greatest;
        } else {
            int mid = (start + end) / 2;
            ForkJoin left = new ForkJoin(array, start, mid);
            ForkJoin right = new ForkJoin(array, mid, end);
            left.fork();
            return Math.max(right.compute(), left.join());
        }
    }

    public static int lookForGreatest(int[][] array) {
        ForkJoinPool pool = new ForkJoinPool();
        return pool.invoke(new ForkJoin(array, 0, array.length));
    }
}


