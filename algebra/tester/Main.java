package tester;

import java.util.Scanner;

/**
 * Main
 */
public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Box[] box = new Box[3];
        for (int i = 0; i <= 2; i++) {
            box[i] = new Box();
        }
        int[] counter = new int[2];
        for (counter[0] = 0; counter[0] <= 4; counter[0]++)
            for (counter[1] = 0; counter[1] + counter[0] <= 4; counter[1]++) {
                System.out.println(counter[0] + " " + counter[1] + " " + (4 - counter[0] - counter[1]));
            }
        System.out.println("over");
    }
}

class Box {
    public int container = 0;
}