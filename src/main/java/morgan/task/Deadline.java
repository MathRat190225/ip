package morgan.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class Deadline extends Task {
    private LocalDate date;
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.ENGLISH);

    public Deadline(String name, String dateStr) throws DateTimeParseException {
        super(name);
        this.date = LocalDate.parse(dateStr.trim(), INPUT_FORMATTER);
    }

    public LocalDate getDate() {
        return this.date;
    }

    public String getDateForStorage() {
        return this.date.format(INPUT_FORMATTER);
    }

    @Override
    public boolean isOnDate(LocalDate date) {
        return this.date.equals(date);
    }

    @Override
    public String toString() {
        return String.format("[D]%s (by: %s)", super.toString(), date.format(OUTPUT_FORMATTER));
    }
}
