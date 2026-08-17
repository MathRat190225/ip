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
    private static final String[] tasks = new String[100];
    private static int taskCount = 0;

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
                    System.out.printf(" \uD83C\uDFA3%d.%s\n", i + 1, tasks[i]);
                }
            } else {
                if (taskCount < tasks.length) {
                    tasks[taskCount] = input;
                    taskCount++;
                    System.out.println(" \uD83D\uDC3Eadded: " + input);
                } else {
                    System.out.println("Meow! That's too much for me!(Maximum: 100)");
                }
            }
        }

        //3.Exit
        System.out.println(DIVIDER);
        System.out.println(" Meow~ Bye bye, human! Don't forget to feed me~");
        System.out.println(DIVIDER);

        sc.close();
    }
}
