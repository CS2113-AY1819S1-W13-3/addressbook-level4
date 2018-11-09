package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MILESTONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RANK;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddMilestoneCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.task.Milestone;
import seedu.address.model.task.Task;

//@@author JeremyInElysium
/**
 * Adds a milestone to a task in the taskbook
 */
public class AddMilestoneCommand extends Command implements CommandParser {
    public static final String COMMAND_WORD = "add_milestone";
    public static final String MESSAGE_SUCCESS = "New milestone added: %1$s";
    public static final String MESSAGE_TASK_NOT_FOUND = "This task does not exist in the task book";
    public static final String MESSAGE_DUPLICATE_RANK = "Duplicate rank entered.";
    public static final String MESSAGE_DUPLICATE_MILESTONEDESCRIPTION = "Duplicate milestone entered.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds milestone(s) to selected task. "
            + "Parameters: "
            + PREFIX_INDEX + "INDEX "
            + PREFIX_MILESTONE + "MILESTONE "
            + PREFIX_RANK + "RANK \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INDEX + "1 "
            + PREFIX_MILESTONE + "Complete Sections 8.1 to 8.5 "
            + PREFIX_RANK + "1";

    private final Index index;
    private final Milestone toAdd;

    /**
     * Creates a AddMilestoneCommand to serve the purpose of the LogicManager
     */
    public AddMilestoneCommand() {
        index = null;
        toAdd = null;
    }

    /**
     * Creates a AddMilestoneCommand to add the specified {@code Milestone}
     */
    public AddMilestoneCommand(Index index, Milestone milestone) {
        requireNonNull(index);
        requireNonNull(milestone);
        this.index = index;
        toAdd = milestone;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTaskList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_TASK_NOT_FOUND);
        }

        //else if (!Rank.isValidRank(toAdd.getRank().toString())) {
        //    throw new CommandException(Rank.MESSAGE_RANK_CONSTRAINTS);
        //}

        Task taskToEdit = lastShownList.get(index.getZeroBased());

        for (Milestone temp: taskToEdit.getMilestoneList()) {
            if (temp.getRank().equals(toAdd.getRank())) {
                throw new CommandException(MESSAGE_DUPLICATE_RANK);
            }
            if (temp.getMilestoneDescription().equals(toAdd.getMilestoneDescription())) {
                throw new CommandException(MESSAGE_DUPLICATE_MILESTONEDESCRIPTION);
            }
        }

        Task editedTask = taskToEdit.addMilestone(toAdd);
        model.updateTask(taskToEdit, editedTask);
        //model.addMilestone(toAdd);
        model.commitTaskBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddMilestoneCommand // instanceof handles nulls
                && toAdd.equals(((AddMilestoneCommand) other).toAdd));
    }

    @Override
    public Command parse(String arguments) throws ParseException {
        return new AddMilestoneCommandParser().parse(arguments);
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

}
