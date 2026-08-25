package morgan.ui;

import java.util.Scanner;

/**
 * Handles user interactions for the chatbot application.
 * Resposible for reading user inputs and displaying formatted messages,
 * error alerts and divider lines.
 */
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

    /**
     * Constructs Ui instance and initializes the input reader.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the welcome messages.
     */
    public void showWelcome() {
        System.out.println(DIVIDER);
        System.out.println(BANNER);
        System.out.println(DIVIDER);
        System.out.println(" Meow~ I'm " + CHATBOT_NAME);
        System.out.println(" What can I do for you? Meow~");
        System.out.println(DIVIDER);
    }

    /**
     * Displays the exit message.
     */
    public void showGoodbye() {
        System.out.println(" Meow~ Bye bye, human! Don't forget to feed me~");
        System.out.println(DIVIDER);
    }

    /**
     * Displays the horizontal divider line for message formating.
     */
    public void showLine() {
        System.out.println(DIVIDER);
    }

    /**
     * Displays an error message.
     *
     * @param message The error message string to be displayed.
     */
    public void showError(String message) {
        System.out.println(" " + message);
    }

    /**
     * Displays the loading error.
     */
    public void showLoadingError() {
        System.out.println(" Meow! Unable to load save file! Starting with empty task list.");
    }

    /**
     * Reads a line of command inputs from the user.
     *
     * @return The raw command string entered by the user.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Determines whether there is a next command.
     *
     * @return True if another line of user input exists, false otherwise.
     */
    public boolean hasNextCommand() {
        return scanner.hasNextLine();
    }

    /**
     * Closes the input reader.
     */
    public void closeScanner() {
        scanner.close();
    }
}