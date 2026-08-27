package edith.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/** Parses and formats the date and time text used by Edith's dated tasks. */
public final class DateTimeParser {
    private static final DateTimeFormatter DATE_TIME_INPUT = DateTimeFormatter.ofPattern("d/M/uuuu HHmm");
    private static final DateTimeFormatter DATE_INPUT = DateTimeFormatter.ofPattern("uuuu-MM-dd")
            .withResolverStyle(ResolverStyle.STRICT);
    private static final DateTimeFormatter DATE_OUTPUT = DateTimeFormatter.ofPattern("MMM d uuuu");
    private static final DateTimeFormatter TIME_OUTPUT = DateTimeFormatter.ofPattern("h:mma");

    private DateTimeParser() {
        // Utility class; do not instantiate.
    }

    /** Returns null for legacy free-form text that is not a supported date format. */
    public static LocalDateTime parse(String text) {
        try {
            return LocalDateTime.parse(text);
        } catch (DateTimeParseException ignored) {
            try {
                return LocalDateTime.parse(text, DATE_TIME_INPUT);
            } catch (DateTimeParseException ignoredTime) {
                try {
                    return LocalDate.parse(text, DATE_INPUT).atStartOfDay();
                } catch (DateTimeParseException ignoredDate) {
                    return null;
                }
            }
        }
    }

    /** Formats a parsed date-time for display. */
    public static String format(LocalDateTime dateTime) {
        String date = dateTime.format(DATE_OUTPUT);
        return dateTime.toLocalTime().equals(LocalTime.MIDNIGHT)
                ? date : date + " " + dateTime.format(TIME_OUTPUT);
    }
}
