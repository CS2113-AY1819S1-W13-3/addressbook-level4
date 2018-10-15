package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static UnRefactored.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static UnRefactored.testutil.TypicalTasks.getTypicalTaskBook;

import org.junit.Test;

import UnRefactored.commons.core.Messages;
import UnRefactored.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Task;

public class CompleteTaskCommandTest {

    private Model model = new ModelManager(getTypicalTaskBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    @Test
    public void execute_validIndexUnfilteredList_success() {
        Task taskToComplete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        CompleteTaskCommand completeTaskCommand = new CompleteTaskCommand(INDEX_FIRST_TASK);

        String expectedMessage = String.format(CompleteTaskCommand.MESSAGE_SUCCESS, taskToComplete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.completeTask(taskToComplete);
        expectedModel.commitTaskBook();

        assertCommandSuccess(completeTaskCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        CompleteTaskCommand completeTaskCommand = new CompleteTaskCommand(outOfBoundIndex);

        assertCommandFailure(completeTaskCommand, model, commandHistory, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }
}
