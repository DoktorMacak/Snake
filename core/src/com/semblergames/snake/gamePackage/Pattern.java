package com.semblergames.snake.gamePackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Pattern {

    private int width;
    private int height;

    private int amount;

    private int [] xs;
    private int [] ys;

    public Pattern(int width, int height, int amount, int[] xs, int[] ys) {
        this.width = width;
        this.height = height;
        this.amount = amount;
        this.xs = xs;
        this.ys = ys;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
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

    public static ArrayList<Pattern> loadPatterns(){

        ArrayList<Pattern> patterns = new ArrayList<Pattern>();


        for(int j = 0; j < lines.length;j++){
            String [] s = lines[j].split(" ");
            int width = Integer.parseInt(s[0]);
            int height = Integer.parseInt(s[1]);
            int amount = Integer.parseInt(s[2]);

            int [] xs = new int[amount];
            int [] ys = new int[amount];

            for(int i = 0; i < amount; i++){
                String [] c = s[3+i].split(",");
                xs[i] = Integer.parseInt(c[0]);
                ys[i] = Integer.parseInt(c[1]); /********/
            }
            Pattern pattern = new Pattern(width,height,amount,xs,ys);

            patterns.add(pattern);
        }

        return patterns;

    }

    private static String [] lines = {
            "9 8 17 1,0 2,0 3,0 1,1 1,2 5,2 5,3 5,4 5,5 5,6 5,7 7,4 8,4 0,6 1,6 2,6 3,6",
            "9 8 18 3,1 4,1 5,1 3,2 4,2 5,2 3,3 8,2 8,3 8,4 1,4 1,5 1,6 2,6 3,6 4,6 5,6 6,6",
            "9 8 14 1,3 2,3 3,3 1,4 2,4 3,4 6,1 7,1 7,2 7,3 7,4 7,5 7,6 6,6",
            "9 8 17 5,1 6,1 7,1 6,2 7,2 6,3 7,3 6,4 7,4 1,3 2,3 3,3 1,4 1,5 1,6 5,6 5,7"
            };


}
