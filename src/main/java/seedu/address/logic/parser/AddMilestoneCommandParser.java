package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MILESTONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RANK;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddMilestoneCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.Milestone;
import seedu.address.model.task.MilestoneDescription;
import seedu.address.model.task.Rank;

//@@author JeremyInElysium
/**
 * Parses input arguments and creates a new AddMilestoneCommand object
 */
public class AddMilestoneCommandParser implements Parser<AddMilestoneCommand> {

    /**
     * Returns true if none of the prefixes contain empty {@code Optional} values in the given
     * {@code ArgumentMultiMap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    @Override
    public AddMilestoneCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_INDEX, PREFIX_MILESTONE, PREFIX_RANK);

        if (!arePrefixesPresent(argMultimap, PREFIX_INDEX, PREFIX_MILESTONE, PREFIX_RANK)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMilestoneCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
        String milestoneDescription = ParserUtil.parseMilestoneDescription(
                argMultimap.getValue(PREFIX_MILESTONE).get());
        String rank = ParserUtil.parseRank(argMultimap.getValue(PREFIX_RANK).get());

        Milestone milestone = new Milestone(new MilestoneDescription(milestoneDescription), new Rank(rank));

        return new AddMilestoneCommand(index, milestone);
    }
}
