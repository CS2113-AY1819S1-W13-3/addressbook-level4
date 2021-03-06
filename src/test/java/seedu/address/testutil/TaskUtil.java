package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.model.task.Task;

//@@author chelseyong
/**
 * A utility class for Task.
 */
public class TaskUtil {
    /**
     * Returns an add command string for adding the {@code task}.
     */
    public static String getAddTaskCommand(Task task) {
        return AddTaskCommand.COMMAND_WORD + " " + getTaskDetails(task);
    }

    /**
     * Returns the part of command string for the given {@code task}'s details.
     */
    public static String getTaskDetails(Task task) {
        StringBuilder sb = new StringBuilder();
        if (task.getModuleCode() != null) {
            sb.append(PREFIX_MODULE_CODE + task.getModuleCode().toString() + " ");
        }
        sb.append(PREFIX_TITLE + task.getTitle() + " ");
        sb.append(PREFIX_DESCRIPTION + task.getDescription() + " ");
        sb.append(PREFIX_PRIORITY + task.getPriorityLevel().priorityLevel + " ");
        sb.append(PREFIX_HOURS + Integer.toString(task.getExpectedNumOfHours()) + " ");
        return sb.toString();
    }

}
