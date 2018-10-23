package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SortTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@ChanChunCheong
/**
 * Parses input arguments and creates a new SortTaskCommand object
 */
public class SortTaskCommandParser implements Parser<SortTaskCommand>{
    public SortTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SORT);
        /*
        try {
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SortTaskCommand.MESSAGE_USAGE), ive);
        }
        */
        String method = argMultimap.getValue(PREFIX_SORT).orElse("");
        //cannot have blank method
        if (method == "") {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SortTaskCommand.MESSAGE_USAGE));
        }

        return new SortTaskCommand(method);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
