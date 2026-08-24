import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Morgan {
    private static final String DIVIDER = "____________________________________________________________";
    private static final String CHATBOT_NAME = "Morgan";
    private static final String BANNER =
            " __  __                            \n"
                    + "|  \\/  | ___  _ __ __ _  __ _ _ __  \n"
                    + "| |\\/| |/ _ \\| '__/ _` |/ _` | '_ \\ \n"
                    + "| |  | | (_) | | | (_| | (_| | | | |\n"
                    + "|_|  |_|\\___/|_|  \\__, |\\__,_|_| |_|\n"
                    + "                  |___/             \n";
    private static int taskCount = 0;
    private static Task[] tasks = new Task[100];

    private static Storage storage = new Storage("./data/morgan.txt");

    public static void main(String[] args) {
        List<Task> loadedTasks = storage.load();
        for (Task task : loadedTasks) {
            if (taskCount < tasks.length) {
                tasks[taskCount] = task;
                taskCount++;
            }
        }

        //1.Greet
        System.out.println(DIVIDER);
        System.out.println(BANNER);
        System.out.println(DIVIDER);
        System.out.println(" Meow~ I'm " + CHATBOT_NAME + "\uD83D\uDC3E");
        System.out.println(" What can I do for you? Meow~");
        System.out.println(DIVIDER);

        Scanner sc = new Scanner(System.in);

        //2.Echo
        while (sc.hasNextLine()) {
            String input = sc.nextLine();

            //exit
            if (input.equalsIgnoreCase("bye")) {
                break;
            }

            System.out.println(DIVIDER);

            //echo
            try {
                if (input.equalsIgnoreCase("list")) {
                    for (int i = 0; i < taskCount; i++) {
                        System.out.printf(" %d.%s\n", i + 1, tasks[i]);
                    }
                } else if (input.startsWith("mark ")) {
                    int idx = Integer.parseInt(input.split(" ")[1]) - 1;
                    if (idx >= 0 && idx < taskCount) {
                        tasks[idx].mark();
                        saveTasks();
                        System.out.println(" Meow~ Morgan has just caught a fish!\uD83C\uDFA3");
                        System.out.println("    " + tasks[idx]);
                    } else {
                        throw new MorganException(" Meow~ I cannot catch a fish that doesn't exist!(=ಠωಠ=)");
                    }
                } else if (input.startsWith("unmark ")) {
                    int idx = Integer.parseInt(input.split(" ")[1]) - 1;
                    if (idx >= 0 && idx < taskCount) {
                        tasks[idx].unmark();
                        saveTasks();
                        System.out.println(" A fish has skipped, Meow!ฅ(=T ω T=)ฅ");
                        System.out.println("    " + tasks[idx]);
                    } else {
                        throw new MorganException(" Meow~ I haven't seen that fish yet.");
                    }
                } else if (input.startsWith("delete")) {
                    int idx = Integer.parseInt(input.split("\\s+")[1]) - 1;
                    if (idx >= 0 && idx < taskCount) {
                        Task removedTask = tasks[idx];
                        for (int i = idx; i < taskCount - 1; i++) {
                            tasks[i] = tasks[i + 1];
                        }
                        tasks[taskCount - 1] = null;
                        taskCount--;
                        saveTasks();

                        System.out.println(" Meow~ Alright, I will set this fish free.");
                        System.out.println("    " + removedTask);
                        System.out.println(" Meow~ There are only " + taskCount + " fishes now.ฅ(>﹏<ฅ)");
                    } else {
                        throw new MorganException("Meow~ That fish doesn't exist in our tank!");
                    }
                } else if (input.startsWith("todo ")) {
                    String name = input.substring(5).trim();
                    if (name.isEmpty()){
                        throw new MorganException("Meow? Is that a fish?(=ﾟωﾟ=)");
                    } else {
                        addTask(new ToDo(name));
                    }
                } else if (input.startsWith("deadline ")) {
                    String[] parts = input.substring(9).split(" /by ");
                    if (parts.length < 2 || parts[0].trim().isEmpty()){
                        throw new MorganException("Meow? Is that a fish?(=ﾟωﾟ=)");
                    } else {
                        try {
                            addTask(new Deadline(parts[0].trim(), parts[1].trim()));
                        } catch (java.time.format.DateTimeParseException e) {
                            throw new MorganException("Meow! Please use the date format: yyyy-MM-dd (e.g., 2026-08-31)");
                        }
                    }
                } else if (input.startsWith("event ")) {
                    String[] parts = input.substring(6).split(" /(from|to) ");
                    if (parts.length < 3 || parts[0].trim().isEmpty()){
                        throw new MorganException("Meow? Is that a fish?(=ﾟωﾟ=)");
                    } else {
                        try {
                            addTask(new Event(parts[0].trim(), parts[1].trim(), parts[2].trim()));
                        } catch (java.time.format.DateTimeParseException e) {
                            throw new MorganException("Meow! Please use date format: yyyy-MM-dd HHmm (e.g., 2026-08-31 1400)");
                        }
                    }
                } else {
                    throw new MorganException("Meow? Is that a fish?(=ﾟωﾟ=)");
                }
            } catch (MorganException e) {
                System.out.println(" " + e.getMessage());
            } catch (Exception e){
                System.out.println(" Meow! That's not a fish!(=｀ω´=)");
            }finally{
                System.out.println(DIVIDER);
            }
        }

        //3.Exit
        System.out.println(DIVIDER);
        System.out.println(" Meow~ Bye bye, human! Don't forget to feed me~");
        System.out.println(DIVIDER);

        sc.close();
    }

    private static void addTask(Task task) {
        if (taskCount < tasks.length) {
            tasks[taskCount] = task;
            taskCount++;
            saveTasks();
            System.out.println(" \uD83D\uDC3E A fresh fish has just come, Meow~");
            System.out.println("   " + task);
            System.out.println(" Meow~ There are " + taskCount + " fishes now!(=^･ω･^=)");
        } else {
            System.out.println(" Meow! That's too much for me! (Maximum: 100)");
        }
    }

    private static void saveTasks() {
        List<Task> currentTasks = new ArrayList<>(Arrays.asList(tasks).subList(0, taskCount));
        storage.save(currentTasks);
    }
}
