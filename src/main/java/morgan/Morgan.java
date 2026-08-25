package morgan;

import morgan.parser.Parser;
import morgan.storage.Storage;
import morgan.exception.MorganException;
import morgan.task.TaskList;
import morgan.ui.Ui;

/**
 * Represents the main entry point for the Morgan chatbox application.
 * Handles application initialization and the main execution loop.
 */

public class Morgan {
    private final Storage storage;
    private final Ui ui;
    private TaskList tasks;

    /**
     * Constructs a new Morgan instance with the specified data storage path.
     *
     * @param filePath the path where task data is persisted.
     */
    public Morgan(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (Exception e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Runs the main application loop.
     * Reads commands from user input.
     * Processes commands until the exit command is received.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit && ui.hasNextCommand()) {
            String fullCommand = ui.readCommand();
            try {
                isExit = Parser.parseAndExecute(fullCommand, tasks, ui, storage);
            } catch (MorganException e) {
                ui.showError(e.getMessage());
            } catch (Exception e) {
                ui.showError("Meow! That's not a fish!");
            } finally {
                if (!isExit) {
                    ui.showLine();
                }
            }
        }
        ui.showGoodbye();
        ui.closeScanner();
    }

    /**
     * Entry point of the application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        new Morgan("./data/morgan.txt").run();
    }
}
