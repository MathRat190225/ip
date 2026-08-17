import java.util.Scanner;

public class Morgan {
    private static final String DIVIDER = "____________________________________________________________";
    private static final String CHATBOT_NAME = "Morgan";
    private static final String BANNER = " __  __                            \n"
            + "|  \\/  | ___  _ __ __ _  __ _ _ __  \n"
            + "| |\\/| |/ _ \\| '__/ _` |/ _` | '_ \\ \n"
            + "| |  | | (_) | | | (_| | (_| | | | |\n"
            + "|_|  |_|\\___/|_|  \\__, |\\__,_|_| |_|\n"
            + "                  |___/             \n";
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
            if (input.equals("bye")) {
                break;
            }

            //echo
            System.out.println(DIVIDER);
            System.out.println(" " + input + " \uD83D\uDC3E");
            System.out.println(DIVIDER);
        }

        //3.Exit
        System.out.println(" Meow~ Bye bye, human! Don't forget to feed me~");
        System.out.println(DIVIDER);

        sc.close();
    }
}
