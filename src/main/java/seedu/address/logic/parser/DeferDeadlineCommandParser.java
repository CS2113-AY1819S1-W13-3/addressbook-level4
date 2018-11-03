package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.stream.Stream;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeferDeadlineCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.Deadline;
//import seedu.address.commons.exceptions.IllegalValueException;

//@@ChanChunCheong
/**
 * Parses input arguments and creates a new DeferDeadlineCommand object
 */
public class DeferDeadlineCommandParser implements Parser<DeferDeadlineCommand> {
    /*
    ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(userInput, PREFIX_DAY, PREFIX_MONTH, PREFIX_YEAR);

    if (!arePrefixesPresent(argMultimap, PREFIX_DAY, PREFIX_MONTH, PREFIX_YEAR)
            || !argMultimap.getPreamble().isEmpty()) {
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
    }

    String title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_DAY).get());
    String description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_MONTH).get());
    PriorityLevel priority = ParserUtil.parsePriorityLevel(argMultimap.getValue(PREFIX_YEAR).get());
    */

    /**
     * Parses the given {@code String} of arguments in the context of the {@code DeferDeadlineCommand}
     * and returns a {@code DeferDeadlineCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeferDeadlineCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_INDEX, PREFIX_DAY, PREFIX_MONTH,
                PREFIX_YEAR);
        if (!arePrefixesPresent(argMultimap, PREFIX_INDEX, PREFIX_DAY, PREFIX_MONTH, PREFIX_YEAR)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeferDeadlineCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).orElse(""));
        String day = argMultimap.getValue(PREFIX_DAY).orElse("");
        String month = argMultimap.getValue(PREFIX_MONTH).orElse("");
        String year = argMultimap.getValue(PREFIX_YEAR).orElse("");
        Deadline deadline = new Deadline(day, month, year);

        return new DeferDeadlineCommand(index, deadline);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
