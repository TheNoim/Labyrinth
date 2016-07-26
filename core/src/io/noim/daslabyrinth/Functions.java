package io.noim.daslabyrinth;


import com.badlogic.gdx.utils.Array;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by nilsbergmann on 25.07.16.
 */
public class Functions {

    public static Array<GameField> gameFields = new Array<GameField>();
	//dsdsaasd
    public static void generateRandomeField() {
        int x = 1;
        int y = 1;
        int treasurecountermin = 1;
        int treasurcountermax = 3;
        for (int i = 0; i < 20; i++) {
            GameField gf = new GameField(null, randomBooleanT(), x, y, i, randomWithRange(0, 3), randomWithRange(0, 3));
            if (gf.isTreasure) {
                gf.treasure = new Treasure();
            }
            gameFields.add(gf);
            System.out.println("GF X: " + x);
            System.out.println("GF Y: " + y);
            System.out.println("GF Index: " + i);
            if (y == 5){
                y = 1;
                x++;
            } else {
                y++;
            }
        }
        int acounter = 0;
        int c = randomWithRange(0, gameFields.size);
        int cc = randomWithRange(0, gameFields.size);
        int ccc = randomWithRange(0, gameFields.size);
        int cccc = randomWithRange(0, gameFields.size);
        gameFields.get(c).isTreasure = true;
        gameFields.get(c).isTreasure = true;
        gameFields.get(ccc).isTreasure = true;
        gameFields.get(cccc).isTreasure = true;
        Array<GameField> atreasures = new Array<GameField>();
        for (int i = 0; i < gameFields.size; i++){
            if (gameFields.get(i).isTreasure){
                acounter++;
                atreasures.add(gameFields.get(i));
            }
        }
        Array<GameField> btreasure = new Array<GameField>();
        if (acounter > treasurcountermax){
            int b = acounter - treasurcountermax;
            /*
            long seed = System.nanoTime();
            Collections.shuffle((List) atreasures, new Random(seed));*/
            for (int i = 0; i < b; i++){
                btreasure.add(atreasures.get(i));
                atreasures.removeIndex(i);
            }
            for (int i = 0; i < gameFields.size; i++){
                for (int ii = 0; i < atreasures.size; i++){
                    if (gameFields.get(i) == btreasure.get(ii)){
                        System.out.println("Change GameField with Index " + gameFields.get(i).index + " from isTreasure true to false.");
                        gameFields.get(i).isTreasure = false;
                    }
                }
            }
        }
        if (acounter < treasurecountermin){

        }
    }

    public static int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }

    public static boolean randomBoolean() {
        int ii = randomWithRange(0, 1);
        if (ii == 1) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean randomBooleanT() {
        int ii = randomWithRange(0, 100);
        if (ii <= 15) {
            return true;
        } else {
            return false;
        }
    }

    public static void printField(){
        int in = 0;
        System.out.println(gameFields.size);
        System.out.println("YX| 1  | 2  | 3  | 4  |");
        System.out.println("--|----|----|----|----|");
        Array<String> int5 = new Array<String>();
        Array<String> int4 = new Array<String>();
        Array<String> int3 = new Array<String>();
        Array<String> int2 = new Array<String>();
        Array<String> int1 = new Array<String>();
        for (int i = 0; i < gameFields.size; i++){
            switch (gameFields.get(i).y){
                case 5:
                    if (gameFields.get(i).type < 10){
                        int5.add(gameFields.get(i).isTreasure + " ");
                    } else {
                        int5.add(gameFields.get(i).isTreasure + "");
                    }
                    break;
                case 4:
                    if (gameFields.get(i).type < 10){
                        int4.add(gameFields.get(i).isTreasure + " ");
                    } else {
                        int4.add(gameFields.get(i).isTreasure + "");
                    }
                    break;
                case 3:
                    if (gameFields.get(i).type < 10){
                        int3.add(gameFields.get(i).isTreasure + " ");
                    } else {
                        int3.add(gameFields.get(i).isTreasure + "");
                    }
                    break;
                case 2:
                    if (gameFields.get(i).type < 10){
                        int2.add(gameFields.get(i).isTreasure + " ");
                    } else {
                        int2.add(gameFields.get(i).isTreasure + "");
                    }
                    break;
                case 1:
                    if (gameFields.get(i).type < 10){
                        int1.add(gameFields.get(i).isTreasure + " ");
                    } else {
                        int1.add(gameFields.get(i).isTreasure + "");
                    }
                    break;
            }
        }
        System.out.println("5 | " + int5.get(0) + " | " + int5.get(1) + " | " + int5.get(2) + " | " + int5.get(3) + " |");
        System.out.println("4 | " + int4.get(0) + " | " + int4.get(1) + " | " + int4.get(2) + " | " + int4.get(3) + " |");
        System.out.println("3 | " + int3.get(0) + " | " + int3.get(1) + " | " + int3.get(2) + " | " + int3.get(3) + " |");
        System.out.println("2 | " + int2.get(0) + " | " + int2.get(1) + " | " + int2.get(2) + " | " + int2.get(3) + " |");
        System.out.println("1 | " + int1.get(0) + " | " + int1.get(1) + " | " + int1.get(2) + " | " + int1.get(3) + " |");

    }

}
