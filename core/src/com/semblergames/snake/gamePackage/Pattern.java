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

    public static Map<Integer, ArrayList<Pattern>> loadPatterns(){

        Map<Integer, ArrayList<Pattern>> patterns = new HashMap<Integer, ArrayList<Pattern>>();


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
                ys[i] = Integer.parseInt(c[1]);
            }
            Pattern pattern = new Pattern(width,height,amount,xs,ys);

            if(patterns.containsKey(width)){
                patterns.get(width).add(pattern);
            }else{
                ArrayList<Pattern> newPatterns = new ArrayList<Pattern>();
                newPatterns.add(pattern);
                patterns.put(width, newPatterns);
            }
        }

        return patterns;

    }

    private static String [] lines = {
            "1 3 3 0,0 0,1 0,2 ",
            "2 3 4 1,0 1,1 0,1 0,2 \n",
            "3 2 4 0,0 1,0 2,0 0,1 \n",
            "4 3 6 0,0 1,0 2,0 3,0 2,1 2,2 \n",
            "5 4 8 0,0 1,0 2,0 3,0 4,0 0,1 0,2 0,3 \n",
            "6 2 7 0,0 1,0 2,0 3,0 4,0 5,0 0,1 \n"
            };


}
