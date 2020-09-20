package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TELEGRAM_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TELEGRAM_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TELEGRAM_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_AVERAGE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_EXPERIENCED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TELEGRAM_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_AVERAGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_EXPERIENCED;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalStudent.AMY;
import static seedu.address.testutil.TypicalStudent.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.Telegram;
import seedu.address.model.student.Student;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.StudentBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Student expectedStudent = new StudentBuilder(BOB).withTags(VALID_TAG_EXPERIENCED).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + TELEGRAM_DESC_BOB + EMAIL_DESC_BOB
                + TAG_DESC_EXPERIENCED, new AddCommand(expectedStudent));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + TELEGRAM_DESC_BOB + EMAIL_DESC_BOB
                + TAG_DESC_EXPERIENCED, new AddCommand(expectedStudent));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + TELEGRAM_DESC_AMY + TELEGRAM_DESC_BOB + EMAIL_DESC_BOB
                + TAG_DESC_EXPERIENCED, new AddCommand(expectedStudent));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + TELEGRAM_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + TAG_DESC_EXPERIENCED, new AddCommand(expectedStudent));

        // multiple tags - all accepted
        Student expectedStudentMultipleTags = new StudentBuilder(BOB).withTags(VALID_TAG_EXPERIENCED, VALID_TAG_AVERAGE)
                .build();

        assertParseSuccess(parser, NAME_DESC_BOB + TELEGRAM_DESC_BOB + EMAIL_DESC_BOB
                + TAG_DESC_AVERAGE + TAG_DESC_EXPERIENCED, new AddCommand(expectedStudentMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Student expectedStudent = new StudentBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + TELEGRAM_DESC_AMY + EMAIL_DESC_AMY,
                new AddCommand(expectedStudent));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + TELEGRAM_DESC_BOB + EMAIL_DESC_BOB ,
                expectedMessage);

        // missing telegram prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_TELEGRAM_BOB + EMAIL_DESC_BOB ,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + TELEGRAM_DESC_BOB + VALID_EMAIL_BOB ,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_TELEGRAM_BOB + VALID_EMAIL_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, INVALID_NAME_DESC + TELEGRAM_DESC_BOB + EMAIL_DESC_BOB
                + TAG_DESC_AVERAGE + TAG_DESC_EXPERIENCED, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_TELEGRAM_DESC + EMAIL_DESC_BOB
                + TAG_DESC_AVERAGE + TAG_DESC_EXPERIENCED, Telegram.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + TELEGRAM_DESC_BOB + INVALID_EMAIL_DESC
                + TAG_DESC_AVERAGE + TAG_DESC_EXPERIENCED, Email.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + TELEGRAM_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_EXPERIENCED, Tag.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + TELEGRAM_DESC_BOB + EMAIL_DESC_BOB
                + TAG_DESC_AVERAGE + TAG_DESC_EXPERIENCED,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + TELEGRAM_DESC_BOB + INVALID_EMAIL_DESC,
                Name.MESSAGE_CONSTRAINTS);
    }
}
