import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Storage {
    private final String filePath;
    
    public Storage(String filePath) {
        this.filePath = filePath;
    }
    
    //Load
    public List<Task> load() {
        List<Task> tasks = new ArrayList<>();
        File file = new File(filePath);
        
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Meow! Unable to create file!");
            }
            return tasks;
        }
        
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.isEmpty()) {
                    continue;
                }

                Task task = getTask(line);
                tasks.add(task);
            } 
        } catch (Exception e) {
            System.out.println("Meow! Unable to load file!");
        }
        return tasks;
    }

    private Task getTask(String line) {
        String[] parts = line.split("\\s*\\|\\s*");
        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String desc =  parts[2];
        Task task;

        if (type.equals("T")) {
            task = new ToDo(desc);
        } else if (type.equals("D")) {
            task = new Deadline(desc, parts[3]);
        } else {
            task = new Event(desc, parts[3], parts[4]);
        }

        if (isDone) {
            task.mark();
        }
        return task;
    }

    //Save
    private String toFileFormat(Task task) {
        String isDoneStr = task.isDone() ? "1" : "0";
        if (task instanceof ToDo) {
            return "T | " + isDoneStr + " | " + task.getName();
        } else if (task instanceof Deadline) {
            Deadline d = (Deadline) task;
            return "D | " + isDoneStr + " | " + d.getName() + " | " + d.getDateForStorage();
        } else if (task instanceof Event) {
            Event e = (Event) task;
            return "E | " + isDoneStr + " | " + e.getName() + " | " + e.getStartForStorage() + " | " + e.getEndForStorage();
        }
        return "";
    }

    public void save(List<Task> tasks) {
        try (FileWriter fw = new FileWriter(filePath)) {
            for (Task task : tasks) {
                String line = toFileFormat(task);
                fw.write(line + System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Meow! Unable to save file!");
        }
    }
}
