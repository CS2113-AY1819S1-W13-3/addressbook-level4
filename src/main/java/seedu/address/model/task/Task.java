package seedu.address.model.task;

import java.util.Objects;

/**
 * Represents a Task in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Task {
    private Deadline deadline;
    private final String title;
    private final String description;
    private final PriorityLevel priorityLevel;
    private boolean isCompleted = false;
    //TODO: change to double type for hours
    private final int expectedNumOfHours;
    private int completedNumOfHours;

    public Task(Deadline deadline, String title, String description, PriorityLevel priorityLevel,
                int expectedNumOfHours) {
        this.deadline = deadline;
        this.title = title;
        this.description = description;
        this.priorityLevel = priorityLevel;
        this.expectedNumOfHours = expectedNumOfHours;
    }


    public Task(Deadline deadline, String title, String description, PriorityLevel priorityLevel,
                int expectedNumOfHours, int completedNumOfHours, boolean isCompleted) {
        this.deadline = deadline;
        this.title = title;
        this.description = description;
        this.priorityLevel = priorityLevel;
        this.expectedNumOfHours = expectedNumOfHours;
        this.completedNumOfHours = completedNumOfHours;
        this.isCompleted = isCompleted;
    }

    public Task(String title, String description, PriorityLevel priorityLevel, int expectedNumOfHours) {
        this.title = title;
        this.description = description;
        this.priorityLevel = priorityLevel;
        this.expectedNumOfHours = expectedNumOfHours;
    }

    public Deadline getDeadline() {
        return deadline;
    }

    public void setDeadline(Deadline deadline) {
        this.deadline = deadline;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public PriorityLevel getPriorityLevel() {
        return priorityLevel;
    }
    public int getExpectedNumOfHours() {
        return expectedNumOfHours;
    }
    public int getCompletedNumOfHours() {
        return completedNumOfHours;
    }
    public boolean isCompleted() {
        return isCompleted;
    }

    /**
     * Marks the task as completed by
     * setting @code {isCompleted} to true
     */
    public Task completed(int hours) {
        this.isCompleted = true;
        this.completedNumOfHours = hours;
        return this;
    }

    /**
     * Returns true if both tasks have the same deadline and title.
     * This defines a weaker notion of equality between two tasks.
     */
    public boolean isSameTask(Task otherTask) {
        if (otherTask == this) {
            return true;
        }
        return otherTask != null
                && otherTask.getDeadline().equals(getDeadline())
                && otherTask.getTitle().equals(getTitle());
    }

    /**
     * Defers the task to a later
     * @param deadline
     * @return the new Task
     */
    public Task deferred(Deadline deadline) {
        this.deadline = deadline;
        return this;
    }
    /**
     * Returns true if both tasks have the same data fields.
     * This defines a stronger notion of equality between two tasks.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Task)) {
            return false;
        }

        Task otherTask = (Task) other;
        return otherTask.getTitle().equals(getTitle())
                && otherTask.getDeadline().equals(getDeadline())
                && otherTask.getDescription().equals(getDescription())
                && otherTask.getPriorityLevel().equals(getPriorityLevel())
                && otherTask.isCompleted() == isCompleted()
                && otherTask.getExpectedNumOfHours() == getExpectedNumOfHours()
                && otherTask.getCompletedNumOfHours() == getCompletedNumOfHours();
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(deadline, title, description, priorityLevel, expectedNumOfHours,
                completedNumOfHours, isCompleted);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDeadline())
                .append(" | ")
                .append(getTitle())
                .append(" : ")
                .append(getDescription())
                .append(" Priority: ")
                .append(getPriorityLevel());
        return builder.toString();
    }
}
