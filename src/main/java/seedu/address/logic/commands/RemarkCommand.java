package seedu.address.logic.commands;


import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class RemarkCommand extends Command {

    public static final String COMMAND_WORD = "remark"; //required for the parser

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Remark command is not implemented yet";

    @Override  //this method is in the abstract class Command already, hence override
    public CommandResult execute(Model model, CommandHistory history)throws CommandException{
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);  //does nothing, just throw an exception upon execution
    }

}
