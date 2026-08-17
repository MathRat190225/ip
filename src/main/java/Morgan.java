import java.util.Scanner;

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

    public static void main(String[] args) {
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

            //echo
            System.out.println(DIVIDER);

            if (input.equalsIgnoreCase("list")) {
                for (int i = 0; i < taskCount; i++) {
                    System.out.printf(" %d.%s\n", i + 1, tasks[i]);
                }
            } else if (input.startsWith("mark ")) {
                int idx = Integer.parseInt(input.split(" ")[1]) - 1;
                if (idx >= 0 && idx < taskCount) {
                    tasks[idx].mark();
                    System.out.println(" Meow~ Morgan has just caught a fish!\uD83C\uDFA3");
                    System.out.println("    " + tasks[idx]);
                }
            } else if (input.startsWith("unmark ")) {
                int idx = Integer.parseInt(input.split(" ")[1]) - 1;
                if (idx >= 0 && idx < taskCount) {
                    tasks[idx].unmark();
                    System.out.println(" A fish has skipped, Meow!ฅ(=T ω T=)ฅ");
                    System.out.println("    " + tasks[idx]);
                }
            } else if (input.startsWith("todo ")) {
                String name = input.substring(5).trim();
                addTask(new ToDo(name));
            } else if (input.startsWith("deadline ")) {
                String[] parts = input.substring(9).split(" /by ");
                addTask(new Deadline(parts[0].trim(), parts[1].trim()));
            } else if (input.startsWith("event ")) {
                String[] parts = input.substring(6).split(" /from | /to ");
                addTask(new Event(parts[0].trim(), parts[1].trim(), parts[2].trim()));
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
            System.out.println(" \uD83D\uDC3E A fresh fish has just come, Meow~");
            System.out.println("   " + task);
            System.out.println(" Meow~ There are " + taskCount + " fishes now!(=^･ω･^=)");
        } else {
            System.out.println(" Meow! That's too much for me! (Maximum: 100)");
        }
    }
}
