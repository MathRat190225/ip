package morgan.parser;

import morgan.exception.MorganException;
import morgan.storage.Storage;
import morgan.task.*;
import morgan.ui.Ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * Parses user input into executable commands for Morgan application.
 * Responsible for the validating command syntax and extracting relevant arguments.
 */
public class Parser {
    /**
     * Parse the full command string entered by the user into specific command objects.
     * @param input The raw input typed by the user.
     * @param tasks The list of the tasks.
     * @param ui The Ui of the chatbox.
     * @param storage The auto storage texts.
     * @return True if received exit message, otherwise false.
     * @throws MorganException If the command word is unrecognized or parameters are invalid.
     */
    public static boolean parseAndExecute(String input, TaskList tasks, Ui ui, Storage storage) throws MorganException {
        String trimmedInput = input.trim();
        if (trimmedInput.isEmpty()) {
            return false;
        }

        if (trimmedInput.equalsIgnoreCase("bye")) {
            return true;
        }

        ui.showLine();

        if (trimmedInput.equalsIgnoreCase("list")) {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.printf(" %d.%s\n", i + 1, tasks.get(i));
            }
        } else if (trimmedInput.startsWith("mark")) {
            int idx = parseIndex(trimmedInput);
            Task task = tasks.mark(idx);
            storage.save(tasks.getTasks());
            System.out.println(" Meow~ Morgan has just caught a fish!");
            System.out.println("    " + task);
        } else if (trimmedInput.startsWith("unmark")) {
            int idx = parseIndex(trimmedInput);
            Task task = tasks.unmark(idx);
            storage.save(tasks.getTasks());
            System.out.println(" A fish has skipped, Meow!ฅ(=T ω T=)ฅ");
            System.out.println("    " + task);
        } else if (trimmedInput.startsWith("delete")) {
            int idx = parseIndex(trimmedInput);
            Task removedTask = tasks.delete(idx);
            storage.save(tasks.getTasks());
            System.out.println(" Meow~ Alright, I will set this fish free.");
            System.out.println("    " + removedTask);
            System.out.println(" Meow~ There are only " + tasks.size() + " fishes now.(>_<)");
        } else if (trimmedInput.startsWith("todo ")) {
            String name = trimmedInput.substring(5).trim();
            if (name.isEmpty()) {
                throw new MorganException("Meow? Is that a fish?");
            }
            Task todo = new ToDo(name);
            tasks.add(todo);
            storage.save(tasks.getTasks());
            showAddTaskMessage(todo, tasks.size());
        } else if (trimmedInput.startsWith("deadline ")) {
            String[] parts = trimmedInput.substring(9).split(" /by ");
            if (parts.length < 2 || parts[0].trim().isEmpty()) {
                throw new MorganException("Meow? Is that a fish?");
            }
            try {
                Task deadline = new Deadline(parts[0].trim(), parts[1].trim());
                tasks.add(deadline);
                storage.save(tasks.getTasks());
                showAddTaskMessage(deadline, tasks.size());
            } catch (DateTimeParseException e) {
                throw new MorganException("Meow! Please use date format: yyyy-MM-dd (e.g., 2026-08-31)");
            }
        } else if (trimmedInput.startsWith("event ")) {
            String[] parts = trimmedInput.substring(6).split(" /(from|to) ");
            if (parts.length < 3 || parts[0].trim().isEmpty()) {
                throw new MorganException("Meow? Is that a fish?");
            }
            try {
                Task event = new Event(parts[0].trim(), parts[1].trim(), parts[2].trim());
                tasks.add(event);
                storage.save(tasks.getTasks());
                showAddTaskMessage(event, tasks.size());
            } catch (DateTimeParseException e) {
                throw new MorganException("Meow! Please use date format: yyyy-MM-dd HHmm (e.g., 2026-08-31 1400)");
            }
        } else if (trimmedInput.startsWith("dates ")) {
            String dateStr = trimmedInput.substring(6).trim();
            try {
                LocalDate targetDate = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH));
                System.out.println(" Meow~ Here are the fish swimming on " + targetDate + ":");
                var matchingTasks = tasks.findTasksOnDate(targetDate);
                for (int i = 0; i < matchingTasks.size(); i++) {
                    System.out.printf("   %d.%s\n", i + 1, matchingTasks.get(i));
                }
                if (matchingTasks.isEmpty()) {
                    System.out.println("   No fish caught on this day, Meow! (=T w T=)");
                }
            } catch (DateTimeParseException e) {
                throw new MorganException("Meow! Please use format: dates yyyy-MM-dd (e.g., dates 2026-09-09)");
            }
        } else {
            throw new MorganException("Meow? Is that a fish?");
        }

        return false;
    }

    private static int parseIndex(String input) throws MorganException {
        String[] parts = input.split("\\s+");
        if (parts.length < 2) {
            throw new MorganException("Meow~ Please state which fish number!");
        }
        try {
            return Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException e) {
            throw new MorganException("Meow~ That index is not a valid number!");
        }
    }

    private static void showAddTaskMessage(Task task, int totalTasks) {
        System.out.println(" A fresh fish has just come, Meow~");
        System.out.println("   " + task);
        System.out.println(" Meow~ There are " + totalTasks + " fishes now!(=^-w-^=)");
    }
}