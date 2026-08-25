package morgan;

import morgan.parser.Parser;
import morgan.storage.Storage;
import morgan.exception.MorganException;
import morgan.task.TaskList;
import morgan.ui.Ui;

public class Morgan {
    private final Storage storage;
    private final Ui ui;
    private TaskList tasks;

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

    public static void main(String[] args) {
        new Morgan("./data/morgan.txt").run();
    }
}
