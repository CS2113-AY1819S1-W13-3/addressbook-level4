package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_MAX_HOURS;
import static seedu.address.logic.commands.AddTaskCommand.MAX_HOURS_TO_COMPLETE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CompleteTaskCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

//@@author chelseyong
/**
 * Completes a task in the Task Book
 */
public class CompleteTaskCommand extends Command implements CommandParser {
    public static final String COMMAND_WORD = "complete";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Completes the task identified by the index number used in the displayed task list,"
            + " under a certain number of hours\n"
            + "Parameters: " + PREFIX_INDEX + " INDEX(must be a positive integer) "
            + PREFIX_HOURS + "HOURS\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_HOURS + "2";

    public static final String MESSAGE_SUCCESS = "Task completed: %1$s";

    private final Index targetIndex;
    private final int completedNumOfHours;
    public CompleteTaskCommand() {
        // Null so that it can be initialized in LogicManager
        // Check in JUnit test
        targetIndex = null;
        completedNumOfHours = 0;
    }
    /**
     * Creates an CompleteTaskCommand to add the specified {@code Task}
     */
    public CompleteTaskCommand(Index targetIndex, int completedNumOfHours) {
        this.targetIndex = targetIndex;
        this.completedNumOfHours = completedNumOfHours;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTaskList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        } else if (completedNumOfHours == 0) {
            throw new CommandException(Messages.MESSAGE_ZERO_HOURS_COMPLETION);
        } else if (completedNumOfHours >= MAX_HOURS_TO_COMPLETE) {
            throw new CommandException(MESSAGE_MAX_HOURS);
        }
        Task taskToComplete = lastShownList.get(targetIndex.getZeroBased());
        if (taskToComplete.isCompleted()) {
            throw new CommandException(Messages.MESSAGE_COMPLETED_TASK);
        }
        model.completeTask(taskToComplete, completedNumOfHours);
        model.commitTaskBook();
        Task completedTask = lastShownList.get(targetIndex.getZeroBased());
        return new CommandResult(String.format(MESSAGE_SUCCESS, completedTask));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CompleteTaskCommand // instanceof handles nulls
                && targetIndex.equals(((CompleteTaskCommand) other).targetIndex));
    }

    @Override
    public Command parse(String arguments) throws ParseException {
        return new CompleteTaskCommandParser().parse(arguments);
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
}
