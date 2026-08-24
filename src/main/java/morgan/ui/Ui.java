package morgan.ui;

import java.util.Scanner;

public class Ui {
    private static final String DIVIDER = "____________________________________________________________";
    private static final String CHATBOT_NAME = "Morgan";
    private static final String BANNER =
            " __  __                            \n"
                    + "|  \\/  | ___  _ __ __ _  __ _ _ __  \n"
                    + "| |\\/| |/ _ \\| '__/ _` |/ _` | '_ \\ \n"
                    + "| |  | | (_) | | | (_| | (_| | | | |\n"
                    + "|_|  |_|\\___/|_|  \\__, |\\__,_|_| |_|\n"
                    + "                  |___/             \n";

    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        System.out.println(DIVIDER);
        System.out.println(BANNER);
        System.out.println(DIVIDER);
        System.out.println(" Meow~ I'm " + CHATBOT_NAME + "\uD83D\uDC3E");
        System.out.println(" What can I do for you? Meow~");
        System.out.println(DIVIDER);
    }

    public void showGoodbye() {
        System.out.println(" Meow~ Bye bye, human! Don't forget to feed me~");
        System.out.println(DIVIDER);
    }

    public void showLine() {
        System.out.println(DIVIDER);
    }

    public void showError(String message) {
        System.out.println(" " + message);
    }

    public void showLoadingError() {
        System.out.println(" Meow! Unable to load save file! Starting with empty task list.");
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public boolean hasNextCommand() {
        return scanner.hasNextLine();
    }

    public void closeScanner() {
        scanner.close();
    }
}