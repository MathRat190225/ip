import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class TaskList {
    private final List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return this.tasks;
    }

    public int size() {
        return tasks.size();
    }

    public Task get(int index) throws MorganException {
        if (index < 0 || index >= tasks.size()) {
            throw new MorganException("Meow~ That fish doesn't exist in our tank!(=ಠωಠ=)");
        }
        return tasks.get(index);
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task delete(int index) throws MorganException {
        Task removed = get(index);
        tasks.remove(index);
        return removed;
    }

    public Task mark(int index) throws MorganException {
        Task task = get(index);
        task.mark();
        return task;
    }

    public Task unmark(int index) throws MorganException {
        Task task = get(index);
        task.unmark();
        return task;
    }

    public List<Task> findTasksOnDate(LocalDate date) {
        List<Task> result = new ArrayList<>();
        for (Task task : tasks) {
            if (task.isOnDate(date)) {
                result.add(task);
            }
        }
        return result;
    }
}
