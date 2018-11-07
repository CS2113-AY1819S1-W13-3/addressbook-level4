package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandParser;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddTagCommandParser;
import seedu.address.logic.parser.RemoveTagCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;
import seedu.address.model.tag.Tag;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

//@@author ChanChunCheong
/**
 * Adds a tag to a task in the taskbook
 */
public class RemoveTagCommand extends Command implements CommandParser {
    public static final String COMMAND_WORD = "remove_tag";
    public static final String MESSAGE_SUCCESS = "tag removed from task [%1$s]: %2$s";
    //public static final String MESSAGE_SUCCESS_1 = "tag removed from all tasks: %1$s ";
    public static final String MESSAGE_TASK_NOT_FOUND = "This task does not exist in the task book";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Remove tag(s) from selected task. "
            + "Parameters: "
            + PREFIX_INDEX + "INDEX "
            + PREFIX_TAG + "TAG\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INDEX + "1 "
            + PREFIX_TAG + "Sport";

    private final Index index;
    private final Tag tag;

    /**
     * Creates a AddTagCommand to serve the purpose of the LogicManager
     */
    public RemoveTagCommand() {
        index = null;
        tag = null;
    }

    /**
     * Creates a AddTagCommand to add the specified {@code Tag}
     */
    public RemoveTagCommand(Index index, Tag tag) {
        requireNonNull(index);
        requireNonNull(tag);
        this.index = index;
        this.tag = tag;
    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTaskList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_TASK_NOT_FOUND);
        }

        Task taskToRemove = lastShownList.get(index.getZeroBased());
        model.removeTag(taskToRemove, tag);
        model.commitTaskBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, taskToRemove.getTitle(), tag.toString()));
    }

    @Override
    public Command parse(String arguments) throws ParseException {
        return new RemoveTagCommandParser().parse(arguments);
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
}
