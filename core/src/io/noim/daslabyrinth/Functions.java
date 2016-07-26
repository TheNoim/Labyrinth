package io.noim.daslabyrinth;


import com.badlogic.gdx.utils.Array;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Functions {

    public static Array<GameField> gameFields = new Array<GameField>();
	//dsdsaasd
    public static void generateRandomeField() {
        int x = 1;
        int y = 1;
        int treasurecountermin = 3;
        int treasurcountermax = 5;
        for (int i = 0; i < 20; i++) {
            GameField gf = new GameField(null, randomBooleanT(), x, y, i, randomWithRange(0, 3), randomWithRange(0, 3));
            if (gf.isTreasure) {
                gf.treasure = new Treasure();
            }
            gameFields.add(gf);
            if (y == 5){
                y = 1;
                x++;
            } else {
                y++;
            }
        }
        int acounter = 0;
        Array<GameField> atreasures = new Array<GameField>();
        for (int i = 0; i < gameFields.size; i++){
            if (gameFields.get(i).isTreasure){
                acounter++;
                atreasures.add(gameFields.get(i));
            }
        }
        if (acounter > treasurcountermax){
            int b = acounter - treasurcountermax;
            Array<GameField> btreasure = new Array<GameField>();
            for (int i = 0; i < b; i++){
                btreasure.add(atreasures.get(i));
            }
            System.out.println("Trues: " + acounter);
            System.out.println("Btreasure size: " + btreasure.size);
            for (int i = 0; i < btreasure.size; i++){
                System.out.println("-> " + btreasure.get(i).isTreasure);
            }
            for (int i = 0; i < gameFields.size; i++){
                for (int ii = 0; ii < btreasure.size; ii++){
                    if (gameFields.get(i).index == btreasure.get(ii).index){
                        System.out.println("Change GameField with Index " + gameFields.get(i).index + " from isTreasure true to false.");
                        gameFields.get(i).isTreasure = false;
                    }
                }
            }
        }
        if (acounter < treasurecountermin){
            int b = treasurecountermin - acounter;
            makeMoreTreasures(b);
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

    public static void makeMoreTreasures(int b){
        Array<GameField> bgamefields = new Array<GameField>();
        for (int i = 0; i < b; i++){
            int rnd = randomWithRange(0, gameFields.size);
            if (gameFields.get(rnd).isTreasure){
                makeMoreTreasures(b - i);
            } else {
                gameFields.get(rnd).isTreasure = true;
            }
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
        int truecount = 0;
        for (int i = 0; i < gameFields.size; i++){
            if (gameFields.get(i).isTreasure){
                truecount++;
            }
        }
        System.out.println("Trues -> " + truecount);

    }

}
