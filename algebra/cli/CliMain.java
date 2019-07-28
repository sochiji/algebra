package cli;

import java.util.InputMismatchException;

import storage.MatrixManager;
import storage.MatrixStorage;
import util.Container;
import util.STDIN;

/**
 * Main
 */
public class CliMain {
    static final String cwd = ".\\MatrixData";

    public static void main(String[] args) {
        Menu.showWelcome();
        System.out.println();
        Container<String[]> messageContainer = new Container<String[]>();
        MatrixManager manager = new MatrixManager(cwd, messageContainer);
        System.out.println(manager.getSize() + " Matrices loaded.");
        if (messageContainer.data.length != 0) {
            System.out.println(messageContainer.data.length + " files failed to load:");
            for (int i = 0; i <= messageContainer.data.length - 1; i++)
                System.out.println(messageContainer.data[i]);
        }
        System.out.println();
        int command;
        do {
            Menu.showMainMenu();
            System.out.println();
            System.out.print("Your choice: ");
            command = getInteger();
            switch (command) {
            case 1:
                showListCli(manager);
                break;
            case 2:

                break;
            case 3:
            case 0:
                break;
            default:
                System.out.println("Bad command! Try again!");
                break;
            }
        } while (command != 0);
        System.out.println("Bye!");
    }

    public static int getInteger() {
        int ret;
        try {
            ret = STDIN.sc.nextInt();
        } catch (InputMismatchException e) {
            STDIN.sc.next();
            System.out.println("Invalid input! Try again.");
            ret = getInteger();
        }
        return ret;
    }

    public static String getString() {
        String ret = STDIN.sc.next();
        if (!ret.isEmpty())
            return ret;
        else
            return getString();
    }

    public static void showListCli(MatrixManager manager) {
        MatrixStorage[] list = manager.getList();
        if (list.length == 0)
            System.out.println("Empty Record!");
        else {
            int i = 0;
            for (MatrixStorage x : list) {
                System.out.println(i + " " + x.getName());
                System.out.println(x.getMat());
                System.out.println();
                i++;
            }
        }
        System.out.println();
    }

    public void deleteCli(MatrixManager manager) {
        System.out.print("Index to delete: ");
        int index = getInteger();
        if (manager.delete(index))
            System.out.println("Successfully deleted.");
        else
            System.out.println("Failed to delete.");
    }
}