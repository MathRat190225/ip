package morgan.parser;

import morgan.command.*;
import morgan.exception.MorganException;
import morgan.storage.Storage;
import morgan.task.*;
import morgan.ui.Ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Parses user input into executable commands for Morgan application using HashMap lookup.
 */
public class Parser {

    @FunctionalInterface
    private interface CommandFunction {
        Command parse(String arguments) throws MorganException;
    }

    private static final Map<String, CommandFunction> COMMAND_MAP = new HashMap<>();

    static {
        COMMAND_MAP.put("bye", args -> new ExitCommand());
        COMMAND_MAP.put("list", args -> new ListCommand());
        COMMAND_MAP.put("mark", args -> new MarkCommand(parseIndex(args), true));
        COMMAND_MAP.put("unmark", args -> new MarkCommand(parseIndex(args), false));
        COMMAND_MAP.put("delete", args -> new DeleteCommand(parseIndex(args)));
        COMMAND_MAP.put("todo", Parser::parseTodo);
        COMMAND_MAP.put("deadline", Parser::parseDeadline);
        COMMAND_MAP.put("event", Parser::parseEvent);
        COMMAND_MAP.put("dates", Parser::parseDates);
        COMMAND_MAP.put("find", Parser::parseFind);
    }

    /**
     * Parses user input and returns the corresponding Command object.
     *
     * @param fullCommand Full line typed by user.
     * @return Abstract Command object to execute.
     * @throws MorganException If input is invalid.
     */
    public static Command parse(String fullCommand) throws MorganException {
        String trimmedInput = fullCommand.trim();
        if (trimmedInput.isEmpty()) {
            throw new MorganException("Meow? Is that a fish?");
        }

        String[] parts = trimmedInput.split("\\s+", 2);
        String commandWord = parts[0].toLowerCase();
        String arguments = parts.length > 1 ? parts[1].trim() : "";

        CommandFunction fn = COMMAND_MAP.get(commandWord);
        if (fn == null) {
            throw new MorganException("Meow? Is that a fish?");
        }

        return fn.parse(arguments);
    }

    /**
     * Legacy compatible parseAndExecute method.
     */
    public static boolean parseAndExecute(String input, TaskList tasks, Ui ui, Storage storage) throws MorganException {
        Command command = parse(input);
        return command.execute(tasks, ui, storage);
    }

    private static int parseIndex(String args) throws MorganException {
        if (args.isEmpty()) {
            throw new MorganException("Meow~ Please state which fish number!");
        }
        try {
            return Integer.parseInt(args) - 1;
        } catch (NumberFormatException e) {
            throw new MorganException("Meow~ That index is not a valid number!");
        }
    }

    private static Command parseTodo(String args) throws MorganException {
        if (args.isEmpty()) {
            throw new MorganException("Meow? Is that a fish?");
        }
        return new AddCommand(new ToDo(args));
    }

    private static Command parseDeadline(String args) throws MorganException {
        String[] parts = args.split(" /by ");
        if (parts.length < 2 || parts[0].trim().isEmpty()) {
            throw new MorganException("Meow? Is that a fish?");
        }
        try {
            return new AddCommand(new Deadline(parts[0].trim(), parts[1].trim()));
        } catch (DateTimeParseException e) {
            throw new MorganException("Meow! Please use date format: yyyy-MM-dd (e.g., 2026-08-31)");
        }
    }

    private static Command parseEvent(String args) throws MorganException {
        String[] parts = args.split(" /(from|to) ");
        if (parts.length < 3 || parts[0].trim().isEmpty()) {
            throw new MorganException("Meow? Is that a fish?");
        }
        try {
            return new AddCommand(new Event(parts[0].trim(), parts[1].trim(), parts[2].trim()));
        } catch (DateTimeParseException e) {
            throw new MorganException("Meow! Please use date format: yyyy-MM-dd HHmm (e.g., 2026-08-31 1400)");
        }
    }

    private static Command parseDates(String args) throws MorganException {
        if (args.isEmpty()) {
            throw new MorganException("Meow! Please use format: dates yyyy-MM-dd (e.g., dates 2026-09-09)");
        }
        try {
            LocalDate targetDate = LocalDate.parse(args, DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH));
            return new FindDateCommand(targetDate);
        } catch (DateTimeParseException e) {
            throw new MorganException("Meow! Please use format: dates yyyy-MM-dd (e.g., dates 2026-09-09)");
        }
    }

    private static Command parseFind(String args) throws MorganException {
        if (args.isEmpty()) {
            throw new MorganException("Meow~ Please state the keyword to search for!");
        }
        return new FindKeywordCommand(args);
    }
}