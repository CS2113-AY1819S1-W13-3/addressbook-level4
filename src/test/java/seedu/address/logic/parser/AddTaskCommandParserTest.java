package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.EMPTY_MODULE_CODE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.HOURS_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.HOURS_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_HOURS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_HOURS_OVERFLOW;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MODULE_CODE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRIORITY_LEVEL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TITLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MODULE_CODE_CG2271_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MODULE_CODE_CS2113_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_LEVEL_DESC_HIGH;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_LEVEL_DESC_LOW;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_1_HOUR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_1ST_JAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CODE_CS2113;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_LEVEL_LOW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_1;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccessWithDate;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_HOURS;
import static seedu.address.testutil.TypicalTasks.CS2113_TASK_2;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.ModuleCode;
import seedu.address.model.task.PriorityLevel;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

//@@author chelseyong
public class AddTaskCommandParserTest {
    private static final Logger logger = LogsCenter.getLogger(AddTaskCommandParserTest.class);
    private AddTaskCommandParser parser = new AddTaskCommandParser();
    @Test
    public void parse_allFieldsPresent_success() {
        ParserWithDate parser = new ParserWithDate();
        Deadline selectedDeadline = new Deadline(VALID_DEADLINE_1ST_JAN);
        Task expectedTask = new TaskBuilder(CS2113_TASK_2).withDeadline(VALID_DEADLINE_1ST_JAN).build();
        //AddTaskCommand commandWithDate = new AddTaskCommand(expectedTask);
        // whitespace only preamble
        assertParseSuccessWithDate(parser, selectedDeadline, PREAMBLE_WHITESPACE
                        + MODULE_CODE_CS2113_DESC + TITLE_DESC_2 + DESCRIPTION_DESC_2
                        + PRIORITY_LEVEL_DESC_HIGH + HOURS_DESC_1,
                new AddTaskCommand(expectedTask));

        // multiple module codes - last module code accepted
        assertParseSuccessWithDate(parser, selectedDeadline, MODULE_CODE_CG2271_DESC + MODULE_CODE_CS2113_DESC
                + TITLE_DESC_2 + DESCRIPTION_DESC_2 + PRIORITY_LEVEL_DESC_HIGH + HOURS_DESC_1,
                new AddTaskCommand(expectedTask));

        // multiple titles - last title accepted
        assertParseSuccessWithDate(parser, selectedDeadline, MODULE_CODE_CS2113_DESC + TITLE_DESC_1 + TITLE_DESC_2
                + DESCRIPTION_DESC_2 + PRIORITY_LEVEL_DESC_HIGH + HOURS_DESC_1, new AddTaskCommand(expectedTask));

        // multiple descriptions - last description accepted
        assertParseSuccessWithDate(parser, selectedDeadline, MODULE_CODE_CS2113_DESC + TITLE_DESC_2
                + DESCRIPTION_DESC_1 + DESCRIPTION_DESC_2 + PRIORITY_LEVEL_DESC_HIGH + HOURS_DESC_1,
                new AddTaskCommand(expectedTask));

        // multiple priorities - last priority accepted
        assertParseSuccessWithDate(parser, selectedDeadline, MODULE_CODE_CS2113_DESC + TITLE_DESC_2
                + DESCRIPTION_DESC_2 + PRIORITY_LEVEL_DESC_LOW + PRIORITY_LEVEL_DESC_HIGH + HOURS_DESC_1,
                new AddTaskCommand(expectedTask));

        // multiple hours - last hour accepted
        assertParseSuccessWithDate(parser, selectedDeadline, MODULE_CODE_CS2113_DESC + TITLE_DESC_2
                        + DESCRIPTION_DESC_2 + PRIORITY_LEVEL_DESC_HIGH + HOURS_DESC_2 + HOURS_DESC_1,
                new AddTaskCommand(expectedTask));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE);

        // missing module code prefix
        assertParseFailure(parser, VALID_MODULE_CODE_CS2113 + VALID_TITLE_1 + DESCRIPTION_DESC_1
                + PRIORITY_LEVEL_DESC_LOW + HOURS_DESC_1, expectedMessage);

        // missing title prefix
        assertParseFailure(parser, MODULE_CODE_CS2113_DESC + VALID_TITLE_1 + DESCRIPTION_DESC_1
                + PRIORITY_LEVEL_DESC_LOW + HOURS_DESC_1, expectedMessage);

        // missing description prefix
        assertParseFailure(parser, MODULE_CODE_CS2113_DESC + TITLE_DESC_1 + VALID_DESCRIPTION_1
                + PRIORITY_LEVEL_DESC_LOW + HOURS_DESC_1, expectedMessage);

        // missing priority prefix
        assertParseFailure(parser, MODULE_CODE_CS2113_DESC + TITLE_DESC_1 + DESCRIPTION_DESC_1
                + VALID_PRIORITY_LEVEL_LOW + HOURS_DESC_1, expectedMessage);

        // missing hour prefix
        assertParseFailure(parser, MODULE_CODE_CS2113_DESC + TITLE_DESC_1 + DESCRIPTION_DESC_1
                + PRIORITY_LEVEL_DESC_LOW + VALID_1_HOUR, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid Priority Level
        assertParseFailure(parser, MODULE_CODE_CS2113_DESC + TITLE_DESC_1 + DESCRIPTION_DESC_1
                + INVALID_PRIORITY_LEVEL_DESC + HOURS_DESC_1, PriorityLevel.MESSAGE_PRIORITY_CONSTRAINTS);
        // invalid module code
        assertParseFailure(parser, INVALID_MODULE_CODE_DESC + TITLE_DESC_1 + DESCRIPTION_DESC_1
                + PRIORITY_LEVEL_DESC_LOW + HOURS_DESC_1, ModuleCode.MESSAGE_MODULE_CODE_CONSTRAINTS);
        // empty title
        assertParseFailure(parser, MODULE_CODE_CS2113_DESC + INVALID_TITLE_DESC + DESCRIPTION_DESC_1
                + PRIORITY_LEVEL_DESC_LOW + HOURS_DESC_1, ParserUtil.MESSAGE_EMPTY_TITLE);
        // empty description
        assertParseFailure(parser, MODULE_CODE_CS2113_DESC + TITLE_DESC_1 + INVALID_DESCRIPTION_DESC
                + PRIORITY_LEVEL_DESC_LOW + HOURS_DESC_1, ParserUtil.MESSAGE_EMPTY_DESCRIPTION);
        // empty module code
        assertParseFailure(parser, EMPTY_MODULE_CODE_DESC + TITLE_DESC_1 + DESCRIPTION_DESC_1
                + PRIORITY_LEVEL_DESC_LOW + HOURS_DESC_1, ParserUtil.MESSAGE_EMPTY_MODULE_CODE);
        // invalid Hours
        assertParseFailure(parser, MODULE_CODE_CS2113_DESC + TITLE_DESC_1 + DESCRIPTION_DESC_1
                        + PRIORITY_LEVEL_DESC_LOW + INVALID_HOURS_DESC, MESSAGE_INVALID_HOURS);
        // hours > INT_MAX
        assertParseFailure(parser, MODULE_CODE_CS2113_DESC + TITLE_DESC_1 + DESCRIPTION_DESC_1
                        + PRIORITY_LEVEL_DESC_LOW + INVALID_HOURS_OVERFLOW, MESSAGE_INVALID_HOURS);
        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + MODULE_CODE_CS2113_DESC + TITLE_DESC_1
                        + DESCRIPTION_DESC_1 + PRIORITY_LEVEL_DESC_LOW,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));

    }
    /**
     * Since AddTaskCommand can only work with ModelManager
     * which sets the deadline, parsing has to do the adding
     * of deadline here.
     */
    public static class ParserWithDate extends AddTaskCommandParser {
        private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
        /**
         * An overloading method that parses
         * @param userInput with task inputs to add
         * @param date will be set in the task
         * @return AddTaskCommand
         * @throws ParseException if parsing is invalid
         */
        public Command parse(String userInput, Deadline date) throws ParseException {
            //logger.info(userInput);
            ArgumentMultimap argMultimap =
                    ArgumentTokenizer.tokenize(userInput, PREFIX_MODULE_CODE, PREFIX_TITLE, PREFIX_DESCRIPTION,
                            PREFIX_PRIORITY, PREFIX_HOURS);
            if (!arePrefixesPresent(argMultimap, PREFIX_TITLE, PREFIX_DESCRIPTION,
                    PREFIX_PRIORITY, PREFIX_HOURS) || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
            }

            String title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE).get());
            String description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
            PriorityLevel priority = ParserUtil.parsePriorityLevel(argMultimap.getValue(PREFIX_PRIORITY).get());
            int expectedNumOfHours = ParserUtil.parseHours(argMultimap.getValue(PREFIX_HOURS).get());
            ModuleCode moduleCode = null;
            if (argMultimap.getValue(PREFIX_MODULE_CODE).orElse(null) != null) {
                moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE_CODE).get());
            }
            Task task = new Task(moduleCode, title, description, priority, expectedNumOfHours);
            task.setDeadline(date);
            return new AddTaskCommand(task);
        }
        /**
         * Parses user input with deadline into command for execution.
         *
         * @param userInput full user input string
         * @return the command based on the user input
         * @throws ParseException if the user input does not conform the expected format
         */
        public Command parseCommand(String userInput, Deadline deadline) throws ParseException {
            final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
            if (!matcher.matches()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
            }
            final String commandWord = matcher.group("commandWord");
            final String arguments = matcher.group("arguments");
            return parse(arguments, deadline);
        }
    }
}
