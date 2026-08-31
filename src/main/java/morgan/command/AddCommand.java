package morgan.command;

import morgan.storage.Storage;
import morgan.task.Task;
import morgan.task.TaskList;
import morgan.ui.Ui;

/**
 * Represents a command in the application.
 */
public class AddCommand extends Command {
    private final Task task;

    /**
     * Constructs add command with the given task.
     *
     * @param task The task that needs to be added
     */
    public AddCommand(Task task) {
        this.task = task;
    }

    /**
     * Executes the add command using the given tasks, ui, and storage.
     *
     * @param tasks The list of tasks.
     * @param ui The ui.
     * @param storage The storage file.
     * @return False.
     */
    @Override
    public boolean execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showLine();
        tasks.add(task);
        storage.save(tasks.getTasks());
        System.out.println(" A fresh fish has just come, Meow~");
        System.out.println("   " + task);
        System.out.println(" Meow~ There are " + tasks.size() + " fishes now!(=^-w-^=)");
        return false;
    }
}
