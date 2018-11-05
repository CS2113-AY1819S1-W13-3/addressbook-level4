package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
//@@author emobeany

/**
 * Represents a deadline in the task book.
 * Guarantees: field values are validated, immutable, details are present and not null.
 */

public class Deadline {
    public static final String MESSAGE_DEADLINE_CONSTRAINTS =
        "Deadline can only have dd/mm/yyyy format";

    private final String day;
    private final String month;
    private final String year;

    public Deadline(String day, String month, String year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Deadline(String deadline) {
        requireNonNull(deadline);
        checkArgument(isValidFormat(deadline), MESSAGE_DEADLINE_CONSTRAINTS);
        String[] entries = deadline.split("/");
        this.day = entries[0];
        this.month = entries[1];
        this.year = entries[2];
    }

    public static boolean isValidFormat(String deadline) {
        return deadline.split("/").length == 3;
    }

    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    /**
     * Returns false if any fields are not within the limits (not a valid date).
     */
    public static boolean isValidDeadline(String deadline) {
        String[] entries = deadline.split("/");
        if (entries.length != 3) {
            return false;
        }
        String day = entries[0];
        String month = entries[1];
        String year = entries[2];

        ArrayList<Integer> monthsWith30Days = new ArrayList<>(Arrays.asList(4, 6, 9, 11));
        ArrayList<Integer> monthsWith31Days = new ArrayList<>(Arrays.asList(1, 3, 5, 7, 8, 10, 12));

        if (!isNumeric(day) || !isNumeric(month) || !isNumeric(year)) {
            return false;
        } else if (Integer.parseInt(month) < 1 || Integer.parseInt(month) > 12) {
            return false;
        } else if (Integer.parseInt(year) < 2018 || Integer.parseInt(year) > 9999) {
            return false;
        } else if (monthsWith30Days.contains(Integer.parseInt(month))) {
            return (Integer.parseInt(day) > 0 && Integer.parseInt(day) < 31);
        } else if (monthsWith31Days.contains(Integer.parseInt(month))) {
            return (Integer.parseInt(day) > 0 && Integer.parseInt(day) < 32);
        } else {
            if (isLeapYear(Integer.parseInt(year))) {
                return (Integer.parseInt(day) > 0 && Integer.parseInt(day) < 30);
            } else {
                return (Integer.parseInt(day) > 0 && Integer.parseInt(day) < 29);
            }
        }
    }

    @Override
    public int hashCode() {
        // custom fields hashing
        return Objects.hash(day, month, year);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDay())
                .append("/")
                .append(getMonth())
                .append("/")
                .append(getYear());
        return builder.toString();
    }

    /**
     * Referenced online: Checking if String is numeric
     * @param s
     * @return true if String is completely numeric
     */
    public static boolean isNumeric(String s) {
        //s.matches("[-+]?\\d*\\.?\\d+");
        return s != null && s.matches("-?\\d+(\\.\\d+)?");
    }

    /**
     * Referenced online: Checking if year is a leap year
     * @param year selected
     * @return true if year is a leap year
     */
    public static boolean isLeapYear(Integer year) {
        return ((year % 400 == 0 || year % 100 != 0) && year % 4 == 0);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        } else if (object instanceof Deadline) {
            Deadline otherDeadline = (Deadline) object;
            return otherDeadline.day.equals(this.day) && otherDeadline.month.equals(this.month)
                    && otherDeadline.year.equals(this.year);
        }
        return false;
    }
}
