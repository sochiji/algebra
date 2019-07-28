package cli;

/**
 * Menu
 */
public class Menu {

    final static String quitMenu = "0. Quit.";
    final static String cancelMenu = "0. Cancel.";

    public static void showMainMenu() {// Auto save new Matrices
        final String[] mainMenu = { "Show Matrices list.", "Reload all from file.",
                "Operations to a specified Matrix." };
        int len = mainMenu.length;
        for (int i = 0; i <= len - 1; i++) {
            System.out.print(i + 1);
            System.out.println(" " + mainMenu[i]);
        }
        System.out.println(quitMenu);
    }

    public static void showFileMenu() {
        // final String[] fileMenu = { "1. Reload from file manually.", "2. Save to file
        // manually.", "3. Delete from both memory and file.", "4. Rename.", };
    }

    public static void showCalculateMenu() {
    }

    public static void showWelcome() {
        String author = "Sochiji";
        String version = "Alpha 0.0.1";
        System.out.println("Welcome to try our best work");
        System.out.println("This application is developed by " + author + " and his team.");
        System.out.println("Current version: " + version);
    }
}