package tester;

import java.util.Scanner;

/**
 * Main
 */
public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        for (;;) {
            String s = sc.nextLine();
            if (s.charAt(0) == '#')
                break;
            String[] rec = s.split(" ");
            int ans = 0;
            for (String x : rec) {
                if (!x.isEmpty())
                    ans++;
            }
            System.out.println(ans);
        }
    }
}