package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.DeferDeadlineCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

//@@author ChanChunCheong
/**
 * Defer deadline of a specific task in the taskbook.
 */

public class DeferDeadlineCommand extends Command implements CommandParser {

    public static final String COMMAND_WORD = "defer";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Defers the deadline of the selected task in the taskbook. \n"
            + "Parameters: "
            + PREFIX_INDEX + "INDEX (must be a positive integer) "
            + PREFIX_DAY + "DAY \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INDEX + "1 "
            + PREFIX_DAY + "04 ";

    public static final String MESSAGE_NONEXISTENT_TASK = "This task does not exist in the task book";
    public static final String MESSAGE_DUPLICATE_TASK = "Deadline for task can't be deferred as "
            + "a same task already exists in the Task book";
    public static final String MESSAGE_SUCCESS = "Date deferred for task: %1$s";
    public static final String MESSAGE_INVALID_DEFERRED_DEADLINE = "The deferred deadline is not valid";

    private final Index taskIndex;
    private final int deferredDays;

    public DeferDeadlineCommand() {
        // Null so that it can be initialized in LogicManager
        // Check in JUnit test
        taskIndex = null;
        deferredDays = 0;
    }

    /**
     * Creates an DeferDeadlineCommand to add the specified {@code Task & @code Deadline}
     */
    public DeferDeadlineCommand(Index taskIndex, int deferredDays) {
        requireNonNull(taskIndex);
        requireNonNull(deferredDays);
        this.taskIndex = taskIndex;
        this.deferredDays = deferredDays;
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTaskList();

        if (taskIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_NONEXISTENT_TASK);
        }

        Task taskToDefer = lastShownList.get(taskIndex.getZeroBased()); // get the task from the filteredtasklist;
        Task deferredTask = new Task(taskToDefer);
        deferredTask = deferredTask.deferred(deferredDays);
        String deferredTaskDeadline = deferredTask.getDeadline().toString();

        if (!(deferredTask.getDeadline().isValidDeadline(deferredTaskDeadline))) {
            throw new CommandException(MESSAGE_INVALID_DEFERRED_DEADLINE);
        }
        if (model.hasTask(deferredTask)) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        model.updateTask(taskToDefer, deferredTask);
        model.commitTaskBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, taskToDefer));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeferDeadlineCommand // instanceof handles nulls
                && taskIndex.equals(((DeferDeadlineCommand) other).taskIndex)
                && deferredDays == (((DeferDeadlineCommand) other).deferredDays));
    }

    @Override
    public Command parse(String arguments) throws ParseException {
        return new DeferDeadlineCommandParser().parse(arguments);
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
}
