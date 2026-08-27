package morgan.command;

import morgan.exception.MorganException;
import morgan.storage.Storage;
import morgan.task.TaskList;
import morgan.ui.Ui;

public class ListCommand extends Command {
    /**
     * Executes the list command using the given tasks, ui, and storage.
     *
     * @param tasks The list of tasks.
     * @param ui The ui.
     * @param storage The storage file.
     * @return False.
     */
    @Override
    public boolean execute(TaskList tasks, Ui ui, Storage storage) throws MorganException {
        ui.showLine();
        for (int i = 0; i < tasks.size(); i++) {
            System.out.printf(" %d.%s\n", i + 1, tasks.get(i));
        }
        return false;
    }
}