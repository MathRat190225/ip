import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class Event extends Task {
    private LocalDateTime start;
    private LocalDateTime end;

    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm", Locale.ENGLISH);
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a", Locale.ENGLISH);

    public Event(String name, String startStr, String endStr) throws DateTimeParseException {
        super(name);
        this.start = LocalDateTime.parse(startStr.trim(), INPUT_FORMATTER);
        this.end = LocalDateTime.parse(endStr.trim(), INPUT_FORMATTER);
    }

    public LocalDateTime getStart() {
        return this.start;
    }

    public LocalDateTime getEnd() {
        return this.end;
    }

    public String getStartForStorage() {
        return this.start.format(INPUT_FORMATTER);
    }

    public String getEndForStorage() {
        return this.end.format(INPUT_FORMATTER);
    }

    @Override
    public String toString() {
        return String.format("[E]%s (from: %s to: %s)",
                super.toString(),
                start.format(OUTPUT_FORMATTER),
                end.format(OUTPUT_FORMATTER));
    }
}
