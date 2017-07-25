package com.semblergames.snake.gamePackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Pattern {

    private static ArrayList <Pattern> patterns;

    private int amount;

    private int [] xs;
    private int [] ys;

    public Pattern(int amount, int[] xs, int[] ys) {
        this.amount = amount;
        this.xs = xs;
        this.ys = ys;
    }

    public int getAmount() {
        return amount;
    }

    public int[] getXs() {
        return xs;
    }

    public int[] getYs() {
        return ys;
    }

    public static void loadPatterns(){

        patterns = new ArrayList<Pattern>();


        for(int j = 0; j < lines.length;j++){
            String [] s = lines[j].split(" ");
            int amount = Integer.parseInt(s[0]);

            int [] xs = new int[amount];
            int [] ys = new int[amount];

            for(int i = 0; i < amount; i++){
                String [] c = s[1+i].split(",");
                xs[i] = Integer.parseInt(c[0]);
                ys[i] = Integer.parseInt(c[1]); /********/
            }
            Pattern pattern = new Pattern(amount,xs,ys);

            patterns.add(pattern);
        }

    }

    private static String [] lines = {
            "17 1,0 2,0 3,0 1,1 1,2 5,2 5,3 5,4 5,5 5,6 5,7 7,4 8,4 0,6 1,6 2,6 3,6",
            "18 3,1 4,1 5,1 3,2 4,2 5,2 3,3 8,2 8,3 8,4 1,4 1,5 1,6 2,6 3,6 4,6 5,6 6,6",
            "14 1,3 2,3 3,3 1,4 2,4 3,4 6,1 7,1 7,2 7,3 7,4 7,5 7,6 6,6",
            "17 5,1 6,1 7,1 6,2 7,2 6,3 7,3 6,4 7,4 1,3 2,3 3,3 1,4 1,5 1,6 5,6 5,7",
            "11 1,0 1,1 1,2 1,3 3,2 4,2 5,2 6,2 6,3 7,3 8,3",
            "13 2,3 2,4 4,6 5,6 6,6 7,6 8,6 7,5 8,5 7,4 8,4 7,3 8,3",
            "16 2,2 2,3 2,4 1,4 2,5 1,5 5,1 5,2 5,3 5,4 6,4 8,1 8,2 6,7 7,7 8,7",
            "13 0,6 1,6 1,7 0,7 5,1 5,2 5,3 4,3 4,4 4,5 4,6 8,0 8,1",
            "12 1,3 1,4 2,4 2,5 6,1 6,2 6,3 7,3 6,4 7,4 6,5 6,6",
            "6 1,0 2,0 3,0 4,0 7,5 7,6",
            "16 1,1 2,1 3,1 4,1 5,1 6,1 7,1 2,2 3,2 2,3 3,3 2,4 3,4 5,6 6,6 7,6",
            "11 1,2 2,2 3,2 5,0 6,0 7,0 7,1 7,2 7,3 5,6 5,7",
            "6 1,0 2,0 4,3 5,3 4,4 4,5"
            };


}
